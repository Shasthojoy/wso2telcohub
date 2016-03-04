/**
 * Title		:Rating
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Jun 1, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

package com.dialog.util;

import java.io.File;

public class SystemConfig extends XMLConfig{

	public static String DEFAULT_FILE = "config.xml";
	public static char SEPARATOR = '.';

	private static SystemConfig instance_s = null;	
	
	public static SystemConfig getInstance(){
		if ( instance_s == null ){
		    init();
		}
		return instance_s;
	}		
	
	private SystemConfig(String fileName) throws XMLConfigException {
		super(fileName, SEPARATOR);		
	}    
	
	public static void init(){
		try {
		    String fileName = "";
		    if ( !(new File(DEFAULT_FILE)).exists() ){
		        fileName = !(new File("./" + DEFAULT_FILE)).exists() 
								? "./conf/"+ DEFAULT_FILE 
								: "." + DEFAULT_FILE;
			}else{
			    fileName = DEFAULT_FILE;
			}
			instance_s = new SystemConfig(fileName);
			System.out.println(Utility.formatToScreen("Initializing configuration:")+"[OK]");
		} catch (GenException e) {
			System.out.println(Utility.formatToScreen("Initializing configuration:")+"[ERR]");
			e.printStackTrace();
		}// end try
	}
	
	public static void main(String[] args) {
	    //SystemConfig.DEFAULT_FILE = "app.xml";
		System.out.println(SystemConfig.getInstance());
	}
}

