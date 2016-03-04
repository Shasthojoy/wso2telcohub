package com.dialog.rnd.iris.smpp.core;

import ie.omk.smpp.net.SmscLink;

import java.net.UnknownHostException;

public class SMPPSession extends ie.omk.smpp.Connection {
    private boolean inUse               = false;
    private String connID               = "";    
    private long timeStamp              = System.currentTimeMillis();
    private boolean m_markedForCleenUp  = false;
    
    /**
     * @param link
     * @param async
     */
    public SMPPSession(SmscLink link, boolean async) {
        super(link, async);
    }

    /**
     * @param link
     */
    public SMPPSession(SmscLink link) {
        super(link);
    }

    /**
     * @param host
     * @param port
     * @param async
     * @throws UnknownHostException
     */
    public SMPPSession(String host, int port, boolean async) throws UnknownHostException {
        super(host, port, async);
    }

    /**
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    public SMPPSession(String host, int port) throws UnknownHostException {
        super(host, port);
    }
    /**
     * @return the inUse
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * @param inUse the inUse to set
     */
    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * @return the connID
     */
    public String getConnID() {
        return connID;
    }

    /**
     * @param connID the connID to set
     */
    public void setConnID(String connID) {
        this.connID = connID;
    }

    /**
     * @return the timeStamp
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the m_markedForCleenUp
     */
    public boolean isMarkedForCleenUp() {
        return m_markedForCleenUp;
    }

    /**
     * @param m_markedForCleenUp the m_markedForCleenUp to set
     */
    public void setMarkedForCleenUp(boolean markedForCleenUp) {
        this.m_markedForCleenUp = markedForCleenUp;
    }    
}
