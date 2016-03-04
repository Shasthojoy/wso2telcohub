package com.dialog.rnd.iris.smpp.main;

import java.util.ArrayList;

/**
 * Title		:SMSCPoolSelector.java
 * Description	:SMPPComponent
 *  				
 * Copyright	:Copyright (c) 2008
 * Company		:Dialog Telekom PLC
 * Created on	:Jul 9, 2008
 * @author 		:Chandimal
 * @version 	:1.0
 */

public class SMSCPoolSelector {
	ArrayList<String> 	m_connectionIDList = null;
	int					m_next = -1;
	
	public SMSCPoolSelector() {
		m_connectionIDList = new ArrayList<String>(); 
	}
	/**
	 * 
	 */
	public int getListSize() {
	    return m_connectionIDList.size();
	}
	/**
	 * 
	 * @param id
	 */
	public void add(String id){
		m_connectionIDList.add(id);
	}	
	/**
	 * 
	 * @return
	 */
	public synchronized String getNext(){		
		if ( m_next >= m_connectionIDList.size()-1 ){
			m_next = -1;
		}
		m_next++;		
		return m_connectionIDList.get(m_next);
	}
}
