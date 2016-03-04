package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:General implementation for the Object pool
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public abstract class ObjectPool {
	private Object[] m_list;
	private int m_size=0;
	protected int m_index=0;

	/**
	 * @param size Minimum object pool size
	 */
	public ObjectPool(int size) throws Exception{
		
		m_size = size;
		m_index = m_size-1;
		m_list = new Object[m_size];
			// Fill array
			for ( int x=0; x< m_size ; x++){
				Object obj = create();
				if ( obj == null ) throw new Exception("ERR:Object pool init"); 
				m_list[x] = obj; 
			}
		
	}
	
	protected synchronized String getStat(){
		return "ObjectPool:Size="+m_size+",index="+m_index + "\n";
	}
	/**
	 * @param obj : Check in an old object after use
	 */
	public synchronized boolean checkIn(Object obj) {
		if (m_index >= (m_size-1)){
			return false;
		}else{
			m_index++;
			m_list[m_index] = obj;
		}
		return true;
	}

	/**
	 * @return Object Checkout a new object from the list
	 */
	public synchronized Object checkOut() {
		Object l_obj=null;		
		if ( m_index < 0 ) {
			l_obj = create();
			m_index = -1;
		}else{
			l_obj = m_list[m_index];
			m_index--;
		}
		return l_obj;
	}

	public abstract Object create();	
}

