package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:System log controller
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;

public class SystemLog {
	private static SystemLog instance_s = null;
	/**
	 * Initialize Loggers
	 */	
	public static String DEFAULT_LOG4J_FILE = "log4j.xml";

	public final static String LOG_ERROR = "error";
	public final static String LOG_ROOT = "root";
	
	public Logger getLogger(String logName){
		if ( logName.equalsIgnoreCase(LOG_ROOT) ){
			return Logger.getRootLogger();
		}
		return Logger.getLogger(logName);
	}

	public Logger getRootLog(){
		return Logger.getRootLogger();
	}

	public Logger getErrorLog(){
		return Logger.getLogger(LOG_ERROR);
	}

	private SystemLog() { 
	    init();
	}
	
	public static void init(){
		try {
			// Perform initialisation of the class here...
			System.out.print(Utility.formatToScreen("Initializing loggers"));
			String fileName = "";
			if (!(new File(DEFAULT_LOG4J_FILE)).exists()) {
				fileName = !(new File("./" + DEFAULT_LOG4J_FILE)).exists() ? "./conf/"
						+ DEFAULT_LOG4J_FILE
						: "./" + DEFAULT_LOG4J_FILE;
			} else {
				fileName = DEFAULT_LOG4J_FILE;
			}
			if( fileName.substring(fileName.lastIndexOf(".")).trim().equalsIgnoreCase(".xml") ) {
				//configure log4j with XML configuration file
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				Document doc = builder.parse(new File(fileName));
				DOMConfigurator.configure(doc.getDocumentElement());
				System.out.println("[OK]");	
			}
			else {
				//configure log4j with properties configuration file
				PropertyConfigurator.configure(fileName);		
				System.out.println("[OK]");	    
			}
			System.out.println("[OK]");			
		} catch (Exception e) {
			System.out.println("SystemLog initilzing failed. ["+e+"]");
		}	    
	}
	
	public static SystemLog getInstance(){
	    if ( instance_s == null ){
	        instance_s = new SystemLog();
	    }
		return instance_s;
	}
}
