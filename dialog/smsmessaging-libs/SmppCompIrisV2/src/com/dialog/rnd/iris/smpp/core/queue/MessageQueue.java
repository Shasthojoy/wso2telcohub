package com.dialog.rnd.iris.smpp.core.queue;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2010
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 24, 2010
 * @author 		: charith
 * Comments		: 
 */
public interface MessageQueue<T> {
    public void addPacket(T packet);
    public T getPacket();
    public long getSize();
    public void clearAll();
    public void remove(T packet);
}
