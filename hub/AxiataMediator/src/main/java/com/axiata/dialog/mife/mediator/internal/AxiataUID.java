package com.axiata.dialog.mife.mediator.internal;

/*
 * AxiataUID.java
 * Aug 7, 2014  4:53:40 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

/**
 * <TO-DO> <code>AxiataUID</code>
 * @version $Id: AxiataUID.java,v 1.00.000
 */

import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import org.apache.synapse.MessageContext;

public class AxiataUID {
    //private static long startTime = System.currentTimeMillis();
    private static long id;

    public static synchronized String getUniqueID(String axtype, MessageContext context,String appid) {       
        String requestId = System.currentTimeMillis()+axtype+appid+"0"+ id++;
        context.setProperty(SouthboundPublisherConstants.REQUEST_ID, requestId);
        return requestId;
    }
    
    public static String resourceURL(String resUrl,String reqid) {        
       return resUrl.substring(0,resUrl.lastIndexOf("/")+1)+reqid;
    }
    
    public static String resourceURLWithappend(String resUrl,String reqid, String msg) {        
       return resUrl.substring(0,resUrl.lastIndexOf("/")+1)+reqid+"/"+msg;
    }
    
}


