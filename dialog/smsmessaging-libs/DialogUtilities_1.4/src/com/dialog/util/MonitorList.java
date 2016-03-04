package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:Monitor list is a general implementation of a timeout object removal
 * 				 mechanism
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 29, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
import java.util.*;


public class MonitorList extends HashMap implements Runnable{

	/**
	 * Constructor for MonitorList.
	 */
	private String m_monitorName;
	private long m_sleepDuration;
	private long m_idleDelay;
	
	/**
	 * sleep duration 
	 */	
	public MonitorList(String name,long sleepDuration, long idleDelay) {
		this.m_monitorName = name;
		this.m_sleepDuration = sleepDuration;
		this.m_idleDelay = idleDelay;
	}
	
	/**
	 * Remove elements that are older that "idleDelay"
	 */
	public void run(){
		while(true){
			try {
				Thread.sleep(this.m_sleepDuration);
				
				SystemLog.getInstance().getRootLog().debug(this.m_monitorName+":Start-Remove");
				// Go through the list and remove non active MonitorElements
				synchronized( this ){
					Set set = this.keySet() ;
					for( Iterator i =  set.iterator() ; i.hasNext(); ){
						String key = (String)i.next();
						MonitorElement me = (MonitorElement)get(key);
						long dur = System.currentTimeMillis() - me.getLastUpdatetime();
					
						if ( dur > this.m_idleDelay ){
							remove(key);
							SystemLog.getInstance().getRootLog().debug(this.m_monitorName+":Remove-element:"+key);
						}
					}
				}
				SystemLog.getInstance().getRootLog().debug(this.m_monitorName+":End-Remove");
			} catch (InterruptedException e) {
				
			}
		}// Do while
	}	

	/**
	 * Update the current system time
	 */
	public void updateUser(String key){
		synchronized(this){
			MonitorElement me = (MonitorElement)get(key);
			me.updateTime();			
		}		
	}

	public String getMonitorName() {
		return m_monitorName;
	}

	public void setMonitorName(String monitorName) {
		m_monitorName = monitorName;
	}

}

