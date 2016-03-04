package com.dialog.rnd.iris.smpp.main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.core.HeartBeat;
import com.dialog.rnd.iris.smpp.core.SMPPSession;
import com.dialog.rnd.iris.smpp.db.HSQLManager;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

/**
 * Title		:SMSCPoolManager.java
 * Description	:SMPPComponent
 *  				
 * Copyright	:Copyright (c) 2008
 * Company		:Dialog Telekom PLC
 * Created on	:Jul 9, 2008
 * @author 		:Chandimal
 * @version 	:1.0
 */

public class SMSCPoolManager {	
    HashMap<String,ConnectionPool>     m_list      = null;
    HashMap<String,SMSCPoolSelector>    m_map       = null;
    private static String s_configKey;
    public static SMSCPoolManager s_instance 	= null;	
	
	public static SMSCPoolManager getInstance(){
		if ( s_instance == null ){
			s_instance = new SMSCPoolManager();
		}
		return s_instance;
	}
		
	public void init(String configKey){
		String hsqlString     = SystemConfig.getInstance().getStr(configKey+".hsqlneeded");
		String logSizeString  = SystemConfig.getInstance().getStr(configKey+".hsqllogsize");
		boolean isHSQLNeeded = false;
		if(hsqlString!=null) isHSQLNeeded = Boolean.parseBoolean(hsqlString);
		if(isHSQLNeeded){
		    int logSize = 10;
		    if(Utility.isValid(logSizeString)) {
		        try {
		            logSize = Integer.parseInt(logSizeString);
		        }		    
		        catch (NumberFormatException e) {}
		    }
		    final HSQLManager manager = new HSQLManager( logSize );;    
            manager.setup();   
		    Runtime.getRuntime().addShutdownHook(new Thread(){
		       @Override
	    	    public void run() {
		           manager.shutdown();
	    	    } 
		    });
		}
	    
	    s_configKey = configKey;
		m_map = new HashMap<String, SMSCPoolSelector>();
		// Load mapping				
		Vector<String> v = SystemConfig.getInstance().getNextKeys(configKey + ".portMapping.port");
		for (Iterator<String> iterator = v.iterator(); iterator.hasNext();) {
			String m_configKey = iterator.next();
			
			// Get connection list
			SMSCPoolSelector spc = new SMSCPoolSelector();
			
			String constr = SystemConfig.getInstance().getStr(configKey + ".portMapping.port."+m_configKey+".connection");
			StringTokenizer st = new StringTokenizer(constr,",");
			while ( st.hasMoreElements() ){
				String conID = (String)st.nextElement();
				spc.add(conID);
			}
			m_map.put(m_configKey, spc);
		}		
		
		// Load all
		m_list = new HashMap<String,ConnectionPool>();
		v = SystemConfig.getInstance().getElements(configKey + ".connections.connection");
		for (Iterator<String> iterator = v.iterator(); iterator.hasNext();) {
			String c_configKey   = iterator.next();
			String c_id          = SystemConfig.getInstance().getStr(c_configKey+".id");
			String strWokrPool   = SystemConfig.getInstance().getStr(c_configKey+".workerPool");			
			int workerPool       = 1;
			try {
			    workerPool = Integer.parseInt(strWokrPool);
			}
			catch (NumberFormatException e) {
            }
			ConnectionPool scon = new ConnectionPool(c_configKey, c_id );
			for(int i=0;i<workerPool;i++) {
			    Messenger m = new Messenger(scon);
			    m.start();
			}			
			m_list.put(c_id,scon);
		}	
		
		if(!HeartBeat.getInstance().isStarted()) {
		    SystemLog.getInstance().getLogger("smscomp_event").debug(Utility.formatToScreen("Starting Heart Beat", 75)+"[OK]");
            HeartBeat.getInstance().start();
        } else {
            SystemLog.getInstance().getLogger("smscomp_event").debug(Utility.formatToScreen("Starting Heart Beat", 75)+"[ALREADY RUNNING]");
        }
	}
	
	/**
	 * 
	 */
	public void shutdown() {
	    Iterator<ConnectionPool> collection = SMSCPoolManager.getInstance().getConnectionPools().iterator();
        while (collection.hasNext()) {
            try {
                ConnectionPool conPool = collection.next();                
                Vector<SMPPSession> cons = conPool.getAllConnections();
                for(SMPPSession sess : cons) {               
                	sess.unbind();
                }                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        m_list = null;
	}
	/**
	 * 
	 * @return
	 */
	public Collection<ConnectionPool> getConnectionPools(){
        try {            
            return m_list.values();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 
	 * @param portNumber
	 * @return
	 */
	public ConnectionPool getConnectionPool(String portNumber) {
	    ConnectionPool pool = null;
		try {		    
            SMSCPoolSelector sel = m_map.get(portNumber);
            int size  = sel.getListSize();            
            for(int i=0;i<size;i++) {
                String conID = sel.getNext();
                pool = m_list.get(conID);
                if(pool.getBindMode()!=SMPP34Data.BIND_MODE_RX) {
                    break;
                }   
            }  
            if(pool==null) {
                throw new Exception("No pool capable of transmitting msg found!");
            }            
        } catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("getConnectionPool",e);
        }
        return pool;
	}

    /**
     * @return the m_configKey
     */
    public static String getConfigKey() {
        return s_configKey;
    }

    /**
     * @param m_configKey the m_configKey to set
     */
    public static void setConfigKey(String configKey) {
        s_configKey = configKey;
    }
}
