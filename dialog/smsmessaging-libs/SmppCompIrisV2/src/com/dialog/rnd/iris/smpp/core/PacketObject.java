package com.dialog.rnd.iris.smpp.core;

import java.io.Serializable;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Oct 28, 2009
 * @author 		: charith
 * Comments		: 
 */
public class PacketObject implements Serializable {    
    private Object m_packet   = null;
    private int m_retryCount  = 0;
    private String m_smscRef    = "";
    private long m_messageID;
    
    /**
     * 
     */
    public PacketObject() {
    }
    
    /**
     * @param m_smscRef
     * @param m_messageID
     */
    public PacketObject(String smscRef, long messageID) {
        this.m_smscRef = smscRef;
        this.m_messageID = messageID;
    }

    /**
     * @param mPacket
     */
    public PacketObject(Object mPacket) {
        m_packet = mPacket;
    }
    /**
     * 
     * @param smscref
     */
    public PacketObject(String smscref) {
        m_smscRef = smscref;        
    }
    /**
     * @return the m_packet
     */
    public Object getPacket() {
        return m_packet;
    }

    /**
     * @param m_packet the m_packet to set
     */
    public void setPacket(Object packet) {
        this.m_packet = packet;
    }

    /**
     * @return the m_retryCount
     */
    public int getRetryCount() {
        return m_retryCount;
    }

    /**
     * @param m_retryCount the m_retryCount to set
     */
    public void setRetryCount(int retryCount) {
        this.m_retryCount = retryCount;
    }    
    /**
     * 
     */
    public void updateRetryCount() {
        m_retryCount = (m_retryCount+1);
    }

    /**
     * @return the m_smscRef
     */
    public String getSmscRef() {
        return m_smscRef;
    }

    /**
     * @param m_smscRef the m_smscRef to set
     */
    public void setSmscRef(String smscRef) {
        this.m_smscRef = smscRef;
    }

    /**
     * @return the m_messageID
     */
    public long getMessageID() {
        return m_messageID;
    }

    /**
     * @param m_messageID the m_messageID to set
     */
    public void setMessageID(long messageID) {
        this.m_messageID = messageID;
    }
}
