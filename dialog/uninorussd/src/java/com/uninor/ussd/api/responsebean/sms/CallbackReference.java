/*
 * CallbackReference.java
 * Apr 2, 2013  11:20:38 AM
 **
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.uninor.ussd.api.responsebean.sms;

/**
 * SMS Inbound Response <code>CallbackReference</code> container object
 *
 * @version $Id: CallbackReference.java,v 1.00.000
 */
public class CallbackReference {

    private String callbackData;

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }
    private String notifyURL;

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }
}