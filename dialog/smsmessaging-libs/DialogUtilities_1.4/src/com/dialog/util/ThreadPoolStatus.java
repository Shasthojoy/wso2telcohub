package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:Thread pool status class
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */

public class ThreadPoolStatus {
	public int maxThreads;
	public int minThreads;
	public int maxIdleTime;
	public int numThreads;
	public int pendingJobs;
	public int jobsInProgress;

	public String toString() {
		java.lang.StringBuffer sb =
			new java.lang.StringBuffer();
		String strMax = (maxThreads==-1)
			? "No limit" : new Integer(maxThreads).toString();
		String strMin = (minThreads==-1)
			? "No limit" : new Integer(minThreads).toString();
		sb.append("maxThreads = " + strMax + "\r\n");
		sb.append("minThreads = " + strMin + "\r\n");
		sb.append("maxIdleTime = " + maxIdleTime + "\r\n");
		sb.append("numThreads = " + numThreads + "\r\n");
		sb.append("pendingJobs = " + pendingJobs + "\r\n");
		sb.append("jobsInProgress = " + jobsInProgress + "\r\n");
		return(sb.toString());
	}
}