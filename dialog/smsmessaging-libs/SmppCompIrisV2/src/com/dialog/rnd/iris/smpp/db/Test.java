/**
 * 
 */
package com.dialog.rnd.iris.smpp.db;

import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;


/**
 * Title : SmppCompIrisV2 Description : Copyright : Copyright(c) 2010 Company :
 * Dialog Telekom PLC. Created on : Mar 24, 2010
 * 
 * @author : charith Comments :
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SystemConfig.DEFAULT_FILE = "D:/Office/Development/workspace/SmppCompIrisV2/config.xml";
        SystemConfig.init();
        SystemLog.DEFAULT_LOG4J_FILE = "D:/Office/Development/workspace/SmppCompIrisV2/log4j.properties";
        SystemLog.init();
        SMSCPoolManager.getInstance().init("scf.comm.smsc");
        
        
    }
}
