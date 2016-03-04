/**
 * 
 */
package com.dialog.rnd.iris.smpp.core.queue.impl.simplemq;

import java.io.Serializable;

import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.npstrandberg.simplemq.Message;
import com.npstrandberg.simplemq.MessageInput;
import com.npstrandberg.simplemq.MessageQueueService;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2010
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 25, 2010
 * @author 		: charith
 * Comments		: 
 */
public class SimpleMQImpl<T> implements MessageQueue<T> {
    private com.npstrandberg.simplemq.MessageQueue m_queue;

    /**
     * 
     */
    public SimpleMQImpl(String queueName) {        
        m_queue = MessageQueueService.getMessageQueue(queueName);
    }

    public void addPacket(T packet) {
        MessageInput mi = new MessageInput();
        mi.setObject((Serializable)packet);
        m_queue.send(mi);
    }
    
    /**
     * Feature not supported
     */
    public void clearAll() {
    }

    public T getPacket() {
        Message m = m_queue.receiveAndDelete();
        if(m==null)
            return null;
        else            
            return (T)m.getObject();
    }

    public long getSize() {
        return m_queue.messageCount();
    }

    /**
     * Feature not supported
     */
    public void remove(T packet) {
    }
}
