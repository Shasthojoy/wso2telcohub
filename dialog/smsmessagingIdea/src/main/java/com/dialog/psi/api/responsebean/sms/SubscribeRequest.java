/*
 * SubscribeRequest.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.dialog.psi.api.responsebean.sms;

/**
 * Response for register SMS receipt notification request
 * <code>SubscribeRequest</code>
 *
 * @version $Id: SubscribeRequest.java,v 1.00.000
 */
public class SubscribeRequest implements ISMSresponse {

    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}