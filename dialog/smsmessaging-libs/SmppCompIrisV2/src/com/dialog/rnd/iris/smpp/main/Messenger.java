package com.dialog.rnd.iris.smpp.main;

import java.util.Vector;

import ie.omk.smpp.message.SMPPRequest;
import ie.omk.smpp.message.SubmitMulti;
import ie.omk.smpp.message.SubmitSM;

import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.core.SMPPSession;
import com.dialog.rnd.iris.smpp.exception.PoolLimitExceededException;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;

public class Messenger extends Thread {    
    private ConnectionPool m_pool       = null;
    private Vector<Object> m_outQueue   = null;
    private int throttleDown;   
    
    /**
     * 
     * @param queue
     */
    public Messenger(ConnectionPool pool) {
        m_pool          = pool;
        m_outQueue      = m_pool.getOutQue();
        String throttle = (String)SystemConfig.getInstance().getStr(m_pool.getConfigKey()+".comm.throttleDownTime");        
        try {
            throttleDown = Integer.parseInt(throttle);
        } catch (NumberFormatException e) {
            throttleDown = 5;
        }
        SystemLog.getInstance().getLogger("smscomp_event").info("Throttling down to :"+(60000/throttleDown)+" msgs per minute");
    }
    
    @Override
    public void run() {
        while(true) {
            SMPPRequest req = null;
            try {                
                if(m_outQueue.isEmpty()) {
                    throttleDown();
                    continue;
                }
                req = (SMPPRequest)m_outQueue.remove(0);
                SMPPSession conn = m_pool.getConnection();
                if(req instanceof SubmitMulti) {
                    conn.sendRequest((SubmitMulti)req);
                } else {
                    conn.sendRequest((SubmitSM)req);   
                }                
                conn.setTimeStamp(System.currentTimeMillis());
                m_pool.releaseConnection(conn);                
            } 
            catch (PoolLimitExceededException e) {
                SystemLog.getInstance().getLogger("smscomp_unsubmitted").info("UnsubmttedMessage:"+req.getMessageId()+","+req.getMessageText());
                m_pool.getOutQue().add(req);                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            throttleDown();
        }
    }
    /*public void run() {
        while(true) {
            SMPPRequest req = null;
            try {
                SMPPSession conn = m_pool.getConnection();
                while(!m_outQueue.isEmpty()) {
                    req = (SMPPRequest)m_outQueue.remove(0);                
                    conn.sendRequest((SubmitSM)req);
                    conn.setTimeStamp(System.currentTimeMillis());    
                }
                m_pool.releaseConnection(conn);
            } 
            catch (PoolLimitExceededException e) {
                SystemLog.getInstance().getLogger("smscomp_unsubmitted").info("UnsubmttedMessage:"+((SubmitSM)req).getMessageId()+","+((SubmitSM)req).getMessageText());
                m_pool.getOutQue().add(req);                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            throttleDown();
        }
    }*/    

    /**
     * 
     */
    private void throttleDown() {
        try {
            sleep(throttleDown);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
