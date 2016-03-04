package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:Unique ID generator 
 * 				 Assuming that objects wont be created faster than the timer counter !!
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public class UniqueID {
  static long current= System.currentTimeMillis();
  
  static public synchronized long getNext(){
    //return current++;
    return System.currentTimeMillis();
    }
}
