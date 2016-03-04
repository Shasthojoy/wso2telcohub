package com.dialog.rnd.iris.smpp.core.queue.impl.memqueue;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.util.SystemLog;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2010
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 24, 2010
 * @author 		: charith
 * Comments		: 
 */
public class MemoryBasedQueueImpl<T> implements MessageQueue<T> {    
    private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();

    /**
     * 
     */
    public MemoryBasedQueueImpl() {
        super();
    }

    /**
     * @param capacity
     */
    public MemoryBasedQueueImpl(int capacity) {
        queue = new LinkedBlockingQueue<T>(capacity);
    }

    /**
     * @param c
     */
    public MemoryBasedQueueImpl(Collection c) {
        queue = new LinkedBlockingQueue<T>(c);
    }

    /* (non-Javadoc)
     * @see com.dialog.rnd.iris.smpp.core.queue.MessageQueue#addPacket(com.dialog.rnd.iris.smpp.core.PacketObject)
     */
    public void addPacket(T packet) {
        try {
            queue.put(packet);
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_incoming").error("error adding packet to queue,"+packet.toString(),e);
        }
    }

    /* (non-Javadoc)
     * @see com.dialog.rnd.iris.smpp.core.queue.MessageQueue#getPacket()
     */
    public T getPacket() {
        try {
            return queue.take();
        }
        catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("error getting packet from queue",e);
            return null;
        }
    }

    public void clearAll() {
        clearAll();
    }

    /**
     * 
     */
    public long getSize() {
        return queue.size();
    }
    
    /**
     * 
     */
    public void remove(T packet) {
        queue.remove(packet);
    }
}
