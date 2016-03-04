package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:Monitor element with updateTime interface
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 29, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public abstract class MonitorElement {
	private long m_lastUpdatetime;

	/**
	 * Constructor for MonitorElement.
	 */
	public MonitorElement() {
		updateTime();
	}

	public long getLastUpdatetime() {
		return m_lastUpdatetime;
	}
	
	/**
	 * Sets the lastUpdatetime.
	 * @param lastUpdatetime The lastUpdatetime to set
	 */
	public void updateTime() {
		this.m_lastUpdatetime = System.currentTimeMillis();
	}}
