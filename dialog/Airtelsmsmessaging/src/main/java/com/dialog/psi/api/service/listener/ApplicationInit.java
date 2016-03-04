/*
 * ApplicationInit.java
 * May 2, 2013  1:33:54 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.dialog.psi.api.service.listener;

import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import org.hibernate.Session;
//import com.dialog.psi.util.HibernateUtil;

/**
 * Initialization <code>class</code> for the SMPP wrapper 
 * Implementation module.
 * <code>ApplicationInit</code>
 *
 * @version $Id: ApplicationInit.java,v 1.00.000
 */
public class ApplicationInit implements ServletContextListener {

    //private static final Logger log = Logger.getLogger(ApplicationInit.class.getName());
    
    public void contextInitialized(ServletContextEvent evt) {

        ServletContext sc = evt.getServletContext();

        try {
//            SystemConfig.DEFAULT_FILE = sc.getInitParameter("config_path") + "config.xml";
//            SystemConfig.init();
            SystemLog.DEFAULT_LOG4J_FILE = sc.getInitParameter("config_path") + "log4j.xml";
            SystemLog.getInstance().init();

//            SMSCPoolManager.getInstance().init("config.smsc");
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                public void run() {
//                    SMSCPoolManager.getInstance().shutdown();
//                }
//            });
//
//            MOProcessor processor = new MOProcessor();
//            processor.start();
//
//            DRProcessor processor2 = new DRProcessor();
//            processor2.start();
//
//            Session sess = HibernateUtil.getSession();
//            sess.close();

            SystemLog.getInstance().getLogger("smscomp_event").info(Utility.formatToScreen("Application Initialized", 75) + "[OK]");
        } catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").info(Utility.formatToScreen("Application Initialized", 75) + "[ERR]");
            e.printStackTrace();
        }


    }

    public void contextDestroyed(ServletContextEvent sce) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }
}