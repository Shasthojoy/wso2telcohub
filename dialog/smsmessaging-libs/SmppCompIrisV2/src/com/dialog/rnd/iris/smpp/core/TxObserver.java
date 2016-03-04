package com.dialog.rnd.iris.smpp.core;

import ie.omk.smpp.Connection;
import ie.omk.smpp.event.ConnectionObserver;
import ie.omk.smpp.event.SMPPEvent;
import ie.omk.smpp.message.DeliverSM;
import ie.omk.smpp.message.SMPPPacket;
import ie.omk.smpp.message.SubmitMultiResp;
import ie.omk.smpp.message.SubmitSMResp;
import ie.omk.smpp.util.PacketStatus;

import org.apache.log4j.Logger;

import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

public class TxObserver implements ConnectionObserver {
    private Logger m_log                = SystemLog.getInstance().getLogger("smscomp_event");
    private Logger m_incoming           = SystemLog.getInstance().getLogger("smscomp_incoming");
    private MessageQueue<Object> m_inQue                 = null;
    private MessageQueue<PacketObject> m_deliverSMinQue  = null;
    private MessageQueue<PacketObject> m_submitSMinQue   = null;
    private boolean m_trackStatus = true;

    /**
     * 
     * @param inQue
     * @param deliverSMinQue
     * @param submitSMinQue
     */
    public TxObserver(MessageQueue<Object> inQue, MessageQueue<PacketObject> deliverSMinQue, MessageQueue<PacketObject> submitSMinQue) {
        m_inQue             = inQue;
        m_deliverSMinQue    = deliverSMinQue;
        m_submitSMinQue     = submitSMinQue; 
    }
    
    /**
     * 
     * @param inQue
     * @param deliverSMinQue
     * @param submitSMinQue
     * @param hsqlNeeded
     * @param trackStatus
     */
    public TxObserver(MessageQueue<Object> inQue, MessageQueue<PacketObject> deliverSMinQue, MessageQueue<PacketObject> submitSMinQue, boolean trackStatus) {
        m_inQue             = inQue;
        m_deliverSMinQue    = deliverSMinQue;
        m_submitSMinQue     = submitSMinQue; 
        m_trackStatus       = trackStatus;
    }
    
    public void packetReceived(Connection conn, SMPPPacket packet) {
        ((SMPPSession)(conn)).setTimeStamp(System.currentTimeMillis());
        int id = packet.getCommandId();
        int status = packet.getCommandStatus(); 
        
        if( (id == SMPPPacket.BIND_TRANSCEIVER_RESP) || (id == SMPPPacket.BIND_TRANSMITTER_RESP) || (id == SMPPPacket.BIND_RECEIVER_RESP) ){            
            if(conn.isBound()) {
                m_log.debug(Utility.formatToScreen("Connection bound", 75)+"[OK]");             
            }
            else {
                m_log.debug(Utility.formatToScreen("Connection bound", 75)+"[ERR]");    
            }           
            return;
        }       
        else if( (id == SMPPPacket.SUBMIT_SM_RESP) || (id == SMPPPacket.SUBMIT_MULTI_RESP) ) {
            String smscRef   =  "";
            long messageID   = -1;
            if(packet instanceof SubmitSMResp) {            
                smscRef   = ((SubmitSMResp)(packet)).getMessageId();
                messageID   = ((SubmitSMResp)(packet)).getSequenceNum();
            }
            else if(packet instanceof SubmitMultiResp) {
                smscRef   = ((SubmitMultiResp)(packet)).getMessageId();
                messageID   = ((SubmitMultiResp)(packet)).getSequenceNum();
            }
            m_incoming.info("SUBMIT_SM_QUEUE"+","+"\""+"SMPP(l:" + Integer.toString(packet.getLength()) + ", c:0x" + Integer.toHexString(packet.getCommandId()) + ", s:" + Integer.toString(packet.getCommandStatus()) + ", n:" + messageID +", smscref:"+smscRef+")"+"\"");
            if(status == PacketStatus.THROTTLING_ERROR) {
                m_log.info("throttling error,"+packet.getSequenceNum());
            } else {
                if(m_trackStatus)
                    m_submitSMinQue.addPacket(new PacketObject(smscRef, messageID));
            }
            return;
        }
        else if(id == SMPPPacket.DELIVER_SM) {
            DeliverSM dsm = (DeliverSM)packet;         
            if(status == SMPPPacket.ESME_ROK) {
                if(dsm.getEsmClass() == SMPP34Data.SM_SMSC_DLV_RCPT_TYPE) {
                    String msgTxt  = dsm.getMessageText();
                    String smscRef = msgTxt.substring(msgTxt.indexOf(":")+1);
                    smscRef        = smscRef.substring(0,smscRef.indexOf(" "));
                    m_incoming.info("DELIVER_REPORT_QUEUE"+","+"\""+"SMPP(l:" + Integer.toString(packet.getLength()) + ", c:0x" + Integer.toHexString(packet.getCommandId()) + ", s:" + Integer.toString(packet.getCommandStatus()) + ", n:" + smscRef + ")"+"\"");
                    if(m_trackStatus)
                        m_deliverSMinQue.addPacket(new PacketObject(smscRef));
                } else {
                    m_incoming.info("DELIVER_SM_QUEUE"+","+"\""+"SMPP(l:" + Integer.toString(packet.getLength()) + ", c:0x" + Integer.toHexString(packet.getCommandId()) + ", s:" + Integer.toString(packet.getCommandStatus()) + ", n:" + packet.getMessageId() + ")"+"\"");
                    m_inQue.addPacket(packet);
                }
            } 
            else {
                m_log.debug("DELIVER_SM,"+packet.getSource()+","+packet.getDestination()+","+packet.getMessageText()+","+packet.getMessageStatus());                                
            }
        }       
    }
    
    public void update(Connection conn, SMPPEvent event) {
        
    }
}
