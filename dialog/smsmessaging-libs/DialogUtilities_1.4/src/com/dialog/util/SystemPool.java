package com.dialog.util;

import com.dialog.util.ThreadPool;
import com.dialog.util.Utility;

/**
 * Title		:Entertaintment Platform Version 3
 * Description	:System Thread pool
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public class SystemPool extends ThreadPool {
	private static SystemPool instance_s;
	
	static {	
		String nodeName = SystemConfig.getInstance().getNodeName();
		int max = SystemConfig.getInstance().getInt(nodeName + ".threadPool.max");
		int min = SystemConfig.getInstance().getInt(nodeName + ".threadPool.min");
		int timeout = SystemConfig.getInstance().getInt(nodeName + ".threadPool.timeout");
		int isDebug = SystemConfig.getInstance().getInt("scf.threadPool.debug");
		instance_s = new SystemPool(max,min,timeout,(isDebug == 1 ? true:false));	
		System.out.println(Utility.formatToScreen("Initializing thread pool:")+"[OK]");
	}	
	
	private SystemPool(int maxThreads,int minThreads, int maxIdleTime, boolean debug) { 
		// Perform initialisation of the class here...
		super(maxThreads,minThreads, maxIdleTime, debug);
	}

	public static SystemPool getInstance() {
		return instance_s;
	}

}
