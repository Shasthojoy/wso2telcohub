/*
 * ApplicationInit.java
 * May 2, 2013  1:33:54 PM
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.aircel.ussd.api.service.listener;

import com.aircel.ussd.api.MOProcessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dialog.util.*;
import com.dialog.util.exception.SystemError;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;

import org.hibernate.Session;

import com.aircel.ussd.util.HibernateUtil;
import com.dialog.util.web.HttpPosterCommons;

import org.apache.log4j.Logger;

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
            SystemConfig.DEFAULT_FILE = sc.getInitParameter("config_path") + "config.xml";
            SystemConfig.init();
            SystemLog.DEFAULT_LOG4J_FILE = sc.getInitParameter("config_path") + "log4j.xml";
            SystemLog.getInstance().init();

            SMSCPoolManager.getInstance().init("config.smsc");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    SMSCPoolManager.getInstance().shutdown();
                }
            });

            MOProcessor processor = new MOProcessor();
            processor.start();

            Session sess = HibernateUtil.getSession();
            sess.close();

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