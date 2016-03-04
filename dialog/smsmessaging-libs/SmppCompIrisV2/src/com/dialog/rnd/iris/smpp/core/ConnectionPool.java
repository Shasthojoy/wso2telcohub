package com.dialog.rnd.iris.smpp.core;

import ie.omk.smpp.Connection;
import ie.omk.smpp.message.SMPPRequest;
import ie.omk.smpp.net.SmscLink;
import ie.omk.smpp.net.TcpLink;
import ie.omk.smpp.version.SMPPVersion;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.rnd.iris.smpp.core.queue.impl.hsqldb.DeliverSMinQueHSQLDBImpl;
import com.dialog.rnd.iris.smpp.core.queue.impl.hsqldb.IncomingQueueHSQLImpl;
import com.dialog.rnd.iris.smpp.core.queue.impl.hsqldb.SubmitSMinQueHSQLDBImpl;
import com.dialog.rnd.iris.smpp.core.queue.impl.memqueue.MemoryBasedQueueImpl;
import com.dialog.rnd.iris.smpp.exception.PoolLimitExceededException;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;

/**
 * Title		: SmppCompLogica	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Jul 16, 2009
 * @author 		: charith
 * Comments		: 
 */
public class ConnectionPool {
    public final static String NAME_IN_QUEUE        = "inQueue";
    public final static String NAME_DELIVERSM_QUEUE = "deliverSMinQue";
    public final static String NAME_SUBMITSM_QUEUE  = "submitSMinQue";
    
    private int bindMode                                = SMPP34Data.BIND_MODE_TXRX;
    private String m_configKey                          = "";
    private String m_ID                                 = "";
    private MessageQueue<Object> m_inQue                = null;
    private MessageQueue<PacketObject> m_deliverSMinQue = null;
    private MessageQueue<PacketObject> m_submitSMinQue  = null;
    private Vector<Object> m_outQue                     = null;
    private static int poolSize                         = 0;
    private boolean m_isDebug                           = false;
    private Logger m_log                                = SystemLog.getInstance().getLogger("smscomp_event");
    private ConnectionList connectionList               = new ConnectionList(0);    
    private int m_poolMin;
    private int m_poolMax;
    private int m_port;
    private int m_socketTimeout;
    private int m_srStat;
    private byte m_bindMode;
    private byte m_srcTon;
    private byte m_srcNpi;
    private byte m_ton;
    private byte m_npi;
    private String m_ip;    
    private String m_systemID;
    private String m_password;
    private String m_sysType;
    private TxObserver observer; 
    
    /**
     * 
     */
    public ConnectionPool(String configKey, String id) {
        try {
            m_configKey     = configKey;
            m_ID            = id == null ? SystemConfig.getInstance().getStr(m_configKey+".id") : id;
            m_srStat        = SystemConfig.getInstance().getInt(m_configKey+".smsc.srstat");
            m_srcTon        = (byte)SystemConfig.getInstance().getInt(m_configKey+".smsc.srcTON");        
            m_srcNpi        = (byte)SystemConfig.getInstance().getInt(m_configKey+".smsc.srcNPI");
            m_ton           = (byte)SystemConfig.getInstance().getInt(m_configKey+".smsc.defualtTON");
            m_poolMin       = SystemConfig.getInstance().getInt(m_configKey+".pool.min");
            m_poolMax       = SystemConfig.getInstance().getInt(m_configKey+".pool.max");
            m_npi           = (byte)SystemConfig.getInstance().getInt(m_configKey+".smsc.defaultNPI");
            m_bindMode      = (byte)SystemConfig.getInstance().getInt(m_configKey+".smsc.mode");
            m_ip            = SystemConfig.getInstance().getStr(m_configKey+".comm.ip");
            m_port          = SystemConfig.getInstance().getInt(m_configKey+".comm.port");
            m_systemID      = SystemConfig.getInstance().getStr(m_configKey+".smsc.systemId");
            m_password      = SystemConfig.getInstance().getStr(m_configKey+".smsc.password");
            m_sysType       = SystemConfig.getInstance().getStr(m_configKey+".smsc.sysType");
            m_socketTimeout = SystemConfig.getInstance().getInt(m_configKey+".comm.socketTimeout");
            m_outQue        = new Vector<Object>(0);
            
            boolean isHSQLNeeded    = true;
            boolean trackMsgStatus  = true;
            String hsqlString   = SystemConfig.getInstance().getStr(m_configKey+".hsqlneeded");
            String statusString = SystemConfig.getInstance().getStr(m_configKey+".trackStatus");
    		if(hsqlString!=null) isHSQLNeeded     = Boolean.parseBoolean(hsqlString);
    		if(statusString!=null) trackMsgStatus = Boolean.parseBoolean(statusString);

    		if(isHSQLNeeded) {    		
    		    m_inQue          = new IncomingQueueHSQLImpl<Object>();
    		    m_deliverSMinQue = new DeliverSMinQueHSQLDBImpl();
    		    m_submitSMinQue  = new SubmitSMinQueHSQLDBImpl();
    		} 
    		else {
    		    m_inQue          = new MemoryBasedQueueImpl<Object>();
                m_deliverSMinQue = new MemoryBasedQueueImpl<PacketObject>();
                m_submitSMinQue  = new MemoryBasedQueueImpl<PacketObject>();
    		}
    		
            observer        = new TxObserver(m_inQue, m_deliverSMinQue, m_submitSMinQue);
            
            for(int i=1;(i<=m_poolMin);i++){                
                connectionList.add(createConnection());
            }
        } catch (Exception e) {
            m_log.error("SMSCP-" + m_ID + ",CreateConnection,"+m_ID,e);
        }
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    public SMPPSession getConnection() throws Exception {
        if(!connectionList.isEmpty()) {
            for(SMPPSession sess : connectionList) {
                if(!sess.isInUse()) {
                    sess.setInUse(true);
                    return sess;
                }
            }
        }
        if(poolSize<m_poolMax) {
            SMPPSession newSession = createConnection();
            newSession.setInUse(true);
            connectionList.add(newSession);
            return newSession;
        }
        else {
            throw new PoolLimitExceededException("Connection pool max reached");
        }
    }  
    /**
     * 
     * @param session
     */
    public void releaseConnection(SMPPSession session) {
        if( (session == null) && (poolSize<m_poolMax) ) {
            session = createConnection();            
        }
        session.setInUse(false);
    }
    
    /**
     * 
     * @param sess
     */
    public void addConnection(SMPPSession sess) {
        connectionList.add(sess);
    }
    /**
     * 
     * @throws Exception
     */
    public SMPPSession createConnection() {
        poolSize++;
        String connID = m_ID+"."+poolSize;
        try {
            SmscLink smscLinkTx = new TcpLink(m_ip, m_port);
            smscLinkTx.setTimeout(m_socketTimeout);        
            smscLinkTx.open();
            
            SMPPSession session = new SMPPSession(smscLinkTx,true);
            session.setVersion(SMPPVersion.V34);
            session.setInterfaceVersion(SMPPVersion.V34);
            session.addObserver(observer);
            session.autoAckMessages(true);        
            session.autoAckLink(true);
            
            if (m_bindMode == SMPP34Data.BIND_MODE_TXRX)
                session.bind(Connection.TRANSCEIVER, m_systemID, m_password, m_sysType);
            else if (m_bindMode == SMPP34Data.BIND_MODE_TX)
                session.bind(Connection.TRANSMITTER, m_systemID, m_password, m_sysType);
            else if (m_bindMode == SMPP34Data.BIND_MODE_RX)
                session.bind(Connection.RECEIVER, m_systemID, m_password, m_sysType);
    
            if (m_isDebug)
                m_log.debug("SMSCP-" + m_ID + ",CreateConnection," + m_ID);
            session.setInUse(false);
            return session;
        } catch (Exception e) {
            poolSize--;
            m_log.error("SMSCP-" + m_ID + ",CreateConnection,"+connID,e);
            return null;
        }
    }       
    /**
     * 
     * @param data
     * @param highPriority
     */
    public void addMessage(SMPPRequest data, boolean highPriority) {
        if(highPriority) {
            m_outQue.add(0, data);
        } else {
            m_outQue.add(data);
        }       
    }
    /**
     * 
     * @return
     */
    public Vector<SMPPSession> getAllConnections() {
        return connectionList;
    }
    /**
     * @return the bindMode
     */
    public int getBindMode() {
        return bindMode;
    }
    /**
     * @param bindMode the bindMode to set
     */
    public void setBindMode(int bindMode) {
        this.bindMode = bindMode;
    }
    /**
     * @return the m_inQue
     */
    public MessageQueue<Object> getInQue() {
        return m_inQue;
    }
    /**
     * @param mInQue the m_inQue to set
     */
    public void setInQue(MessageQueue<Object> inQue) {
        m_inQue = inQue;
    }
    /**
     * @return the m_outQue
     */
    public Vector<Object> getOutQue() {
        return m_outQue;
    }
    /**
     * @param mOutQue the m_outQue to set
     */
    public void setOutQue(Vector<Object> outQue) {
        m_outQue = outQue;
    }
    /**
     * @return the m_srStat
     */
    public int getSrStat() {
        return m_srStat;
    }
    /**
     * @param m_srStat the m_srStat to set
     */
    public void setSrStat(int srStat) {
        this.m_srStat = srStat;
    }
    /**
     * @return the srcTon
     */
    public byte getSrcTon() {
        return m_srcTon;
    }
    /**
     * @param srcTon the srcTon to set
     */
    public void setSrcTon(byte srcTon) {
        this.m_srcTon = srcTon;
    }
    /**
     * @return the srcNpi
     */
    public byte getSrcNpi() {
        return m_srcNpi;
    }
    /**
     * @param srcNpi the srcNpi to set
     */
    public void setSrcNpi(byte srcNpi) {
        this.m_srcNpi = srcNpi;
    }
    /**
     * @return the ton
     */
    public byte getTon() {
        return m_ton;
    }
    /**
     * @param ton the ton to set
     */
    public void setTon(byte ton) {
        this.m_ton = ton;
    }
    /**
     * @return the npi
     */
    public byte getNpi() {
        return m_npi;
    }
    /**
     * @param npi the npi to set
     */
    public void setNpi(byte npi) {
        this.m_npi = npi;
    }
    /**
     * @return the m_deliverSMinQue
     */
    public MessageQueue<PacketObject> getDeliverSMinQue() {
        return m_deliverSMinQue;
    }
    /**
     * @param mDeliverSMinQue the m_deliverSMinQue to set
     */
    public void setDeliverSMinQue(MessageQueue<PacketObject> mDeliverSMinQue) {
        m_deliverSMinQue = mDeliverSMinQue;
    }
    /**
     * @return the m_submitSMinQue
     */
    public MessageQueue<PacketObject> getSubmitSMinQue() {
        return m_submitSMinQue;
    }
    /**
     * @param mSubmitSMinQue the m_submitSMinQue to set
     */
    public void setSubmitSMinQue(MessageQueue<PacketObject> mSubmitSMinQue) {
        m_submitSMinQue = mSubmitSMinQue;
    }
    /**
     * @return the m_poolMin
     */
    public int getPoolMin() {
        return m_poolMin;
    }
    /**
     * @return the m_poolMax
     */
    public int getPoolMax() {
        return m_poolMax;
    }
    /**
     * @return the m_configKey
     */
    public String getConfigKey() {
        return m_configKey;
    }
}
