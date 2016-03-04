package com.dialog.rnd.iris.smpp.core;

import java.util.Vector;

/**
 * Title		: SmppCompLogica	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Jul 21, 2009
 * @author 		: charith
 * Comments		: 
 */
public class ConnectionList extends Vector<SMPPSession> {

    /**
     * 
     */
    public ConnectionList() {
    }

    /**
     * @param initialCapacity
     */
    public ConnectionList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public synchronized SMPPSession remove(int index) {
       SMPPSession sess = super.remove(index);
       if(sess==null) {
           try {
            wait();
            sess = super.remove(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       }
       return sess;
    }
    
    @Override
    public synchronized boolean add(SMPPSession o) {
        notify();
        return super.add(o);
    }    
}