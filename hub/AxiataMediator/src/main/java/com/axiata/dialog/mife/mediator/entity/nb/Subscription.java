/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.axiata.dialog.mife.mediator.entity.nb;

import com.axiata.dialog.mife.mediator.entity.CallbackReference;
import com.axiata.dialog.mife.mediator.entity.CallbackReference;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Subscription {
    private CallbackReference callbackReference;

    public CallbackReference getCallbackReference() {
        return callbackReference;
    }

    public void setCallbackReference(CallbackReference callbackReference) {
        this.callbackReference = callbackReference;
    }
   
    private DestinationAddresses[] destinationAddresses;

    public DestinationAddresses[] getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(DestinationAddresses[] destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
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
