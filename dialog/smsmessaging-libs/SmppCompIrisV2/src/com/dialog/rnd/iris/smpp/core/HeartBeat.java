package com.dialog.rnd.iris.smpp.core;

import ie.omk.smpp.NotBoundException;

import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;

/**
 * Title		: SmppCompLogica	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Jul 17, 2009
 * @author 		: charith
 * Comments		: 
 */
public class HeartBeat extends Thread {
    private static boolean m_started = false;
    private int tickInterval;
	private boolean doRun;
    private static HeartBeat instance = null; 
    
    /**
     * @return
     */
    public synchronized static HeartBeat getInstance() {
        if(instance == null) {
            instance = new HeartBeat();
        }
        return instance;
    }
    
    /**
     * 
     */
    private HeartBeat() {
        setDaemon(true);
        String strTickInterval = SystemConfig.getInstance().getStr(SMSCPoolManager.getConfigKey() + ".tickInterval");
        tickInterval = Integer.parseInt(strTickInterval);
        doRun = true;
    }
    
    @Override
    public void run() {
        setStarted(true);
        while(doRun) {
            try {
                try {
                    sleep(tickInterval);
                    System.gc();
                }catch (Exception e) {
                }              
                
                Collection<ConnectionPool> cp = SMSCPoolManager.getInstance().getConnectionPools();
                if(cp==null){
                    continue;
                }            
                Iterator<ConnectionPool> collection = cp.iterator();
                while (collection.hasNext()) {
                    try {
                        ConnectionPool conPool = collection.next();
                        Vector<SMPPSession> conns = conPool.getAllConnections();
                        
                        if(conns.size()<conPool.getPoolMin()) {
                            SMPPSession newSession = conPool.createConnection();
                            if(newSession!=null) {
                                conns.add(newSession);
                            }
                        }
                                                
                        for (int i = 0; i < conns.size(); i++) {
                            SMPPSession sess = conns.get(i);
                            if(sess==null) {
                                conns.remove(i);
                                SMPPSession newSession = conPool.createConnection();
                                conPool.releaseConnection(newSession);
                                continue;
                            }                            
                            long lastActivity = sess.getTimeStamp();
                            if ( ((System.currentTimeMillis() - lastActivity) >= tickInterval) && (!sess.isInUse()) ){
                                try {
                                    sess.enquireLink();
                                    sess.setTimeStamp(System.currentTimeMillis());
                                } catch (SocketException e) {
                                    SystemLog.getInstance().getLogger("smscomp_event").error("Heart beat failed:Connection Error",e);
                                    conns.remove(sess);
                                    SMPPSession newSession = conPool.createConnection();
                                    if(newSession!=null) {
                                        conns.add(newSession);
                                    }
                                } catch (NotBoundException e) {
                                    SystemLog.getInstance().getLogger("smscomp_event").error("Heart beat failed:Connection Error",e);
                                    conns.remove(sess);
                                    SMPPSession newSession = conPool.createConnection();
                                    if(newSession!=null) {
                                        conns.add(newSession);
                                    }
                                }
                            }
                        }                        
                    } catch (Exception e) {
                        e.printStackTrace();                        
                    }
                }
            } catch (Exception e) {
                SystemLog.getInstance().getLogger("smscomp_event").error("Heart beat failed",e);
            }
        }
    }

    /**
     * @return the m_started
     */
    public boolean isStarted() {
        return m_started;
    }

    /**
     * @param m_started the m_started to set
     */
    public void setStarted(boolean started) {
        m_started = started;
    }
    
    /**
     * 
     */
    public boolean kill() throws Exception {
    	doRun = false;    	   		
    	while(getState() == State.TIMED_WAITING) {
    		continue;
    	}
    	m_started = false;
    	instance  = null;
    	return true;
    }
}
