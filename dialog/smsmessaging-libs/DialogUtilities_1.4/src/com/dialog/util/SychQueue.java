/**
 * Title		:DialogUtilities
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Jul 20, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

package com.dialog.util;

import java.util.LinkedList;

public class SychQueue {
    LinkedList queue = new LinkedList();

    public synchronized void addObject(Object o) {
        queue.addLast(o);
        notify();
    }

    public synchronized void addObject2Front(Object o) {
        queue.addFirst(o);
        notify();
    }

    public synchronized Object getObject() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }

    public synchronized Object getLastObject() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeLast();
    }

    public synchronized void clear() {
        queue.clear();
    }
    
    public int getSize() {
        return queue.size();
    }
}
