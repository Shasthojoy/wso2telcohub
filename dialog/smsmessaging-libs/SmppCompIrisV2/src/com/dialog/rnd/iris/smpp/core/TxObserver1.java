package com.dialog.rnd.iris.smpp.core;

import ie.omk.smpp.Connection;
import ie.omk.smpp.event.ConnectionObserver;
import ie.omk.smpp.event.SMPPEvent;
import ie.omk.smpp.message.DeliverSM;
import ie.omk.smpp.message.SMPPPacket;

import org.apache.log4j.Logger;

import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

public class TxObserver1 implements ConnectionObserver {
    private Logger m_log                = SystemLog.getInstance().getLogger("smscomp_event");
    private Logger m_incoming           = SystemLog.getInstance().getLogger("smscomp_incoming");
    private MessageQueue<Object> m_inQue                 = null;

    public TxObserver1(MessageQueue<Object> inQue) {
        m_inQue             = inQue;
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
        else if(id == SMPPPacket.DELIVER_SM) {
            DeliverSM dsm = (DeliverSM)packet;         
            if(status == SMPPPacket.ESME_ROK) {
                if(dsm.getEsmClass() != SMPP34Data.SM_SMSC_DLV_RCPT_TYPE) {                 
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