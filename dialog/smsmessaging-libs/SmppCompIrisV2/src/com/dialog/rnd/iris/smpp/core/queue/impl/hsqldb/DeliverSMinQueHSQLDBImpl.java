package com.dialog.rnd.iris.smpp.core.queue.impl.hsqldb;

import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.core.queue.MessageQueue;
import com.dialog.rnd.iris.smpp.db.HSQLManager;

/**
 * Title		: SmppCompIrisV2	
 * Description	: 
 * Copyright	: Copyright(c) 2010
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 24, 2010
 * @author 		: charith
 * Comments		: 
 */
public class DeliverSMinQueHSQLDBImpl implements MessageQueue<PacketObject> {
    private HSQLManager m_hsql;
    /**
     * 
     */
    public DeliverSMinQueHSQLDBImpl() {
        m_hsql = new HSQLManager();
    }

    /* (non-Javadoc)
     * @see com.dialog.rnd.iris.smpp.core.queue.MessageQueue#addPacket(com.dialog.rnd.iris.smpp.core.PacketObject)
     */
    public void addPacket(PacketObject packet) {
        m_hsql.saveDeliveryResp(packet);
    }

    /* (non-Javadoc)
     * @see com.dialog.rnd.iris.smpp.core.queue.MessageQueue#getPacket()
     */
    public PacketObject getPacket() {
        return null;
    }

    public void clearAll() {
        
    }

    public long getSize() {
        return 0;
    }

    public void remove(PacketObject packet) {
        
    }
}
