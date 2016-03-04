/*
 * Subscription.java
 * Apr 2, 2013  11:20:38 AM
 **
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.aircel.ussd.api.responsebean.sms;

/**
 * SMS receipt notification Subscription detail container
 * <code>Subscription</code>
 *
 * @version $Id: Subscription.java,v 1.00.000
 */
public class Subscription {

    private CallbackReference callbackReference;

    public CallbackReference getCallbackReference() {
        return callbackReference;
    }

    public void setCallbackReference(CallbackReference callbackReference) {
        this.callbackReference = callbackReference;
    }
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
   
    private String shortCode;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    private String clientCorrelator;

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }
    private String resourceURL;

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
}