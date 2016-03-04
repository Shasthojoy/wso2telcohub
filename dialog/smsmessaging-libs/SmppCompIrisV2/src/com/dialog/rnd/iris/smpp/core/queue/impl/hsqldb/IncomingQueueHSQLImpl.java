/**
 * 
 */
package com.dialog.rnd.iris.smpp.core.queue.impl.hsqldb;

import java.util.ArrayList;

import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.rnd.iris.smpp.db.EPFHSQLManager;
import com.dialog.rnd.iris.smpp.db.HSQLManager;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2010
 * Company		: Dialog Telekom PLC.
 * Created on	: Apr 2, 2010
 * @author 		: charith
 * Comments		: 
 */
public class IncomingQueueHSQLImpl<T> implements MessageQueue<T> {

    private HSQLManager manager;

    /**
     * 
     */
    public IncomingQueueHSQLImpl() {
        manager = new HSQLManager();        
    }

    public void addPacket(T packet) {
        manager.saveObject(packet);
    }

    /**
     * 
     * @param rowCount
     * @return
     */
    public ArrayList<PacketObject> getMessages(int rowCount) {
        return manager.getMessages(rowCount);
    }
    
    /**
     * 
     * @param list
     */
    public void deleteBatch(ArrayList<PacketObject> list) {
        manager.deleteIncomingBatch(list);
    }
    
    public void clearAll() {        
    }

    public T getPacket() {       
        return null;
    }

    public long getSize() {
        return 0;
    }

    public void remove(T packet) {
        
    }
}
