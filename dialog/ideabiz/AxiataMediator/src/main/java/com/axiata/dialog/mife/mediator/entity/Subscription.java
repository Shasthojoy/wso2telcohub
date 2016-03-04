/*
 * Subscription.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog.mife.mediator.entity;

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
    private String criteria;

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    private String destinationAddress;

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    private String notificationFormat;

    public String getNotificationFormat() {
        return notificationFormat;
    }

    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
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