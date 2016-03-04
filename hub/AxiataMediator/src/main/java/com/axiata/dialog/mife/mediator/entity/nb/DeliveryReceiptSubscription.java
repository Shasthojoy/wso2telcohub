/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.axiata.dialog.mife.mediator.entity.nb;

import com.axiata.dialog.mife.mediator.entity.CallbackReference;

/**
 *
 * @author User
 */
public class DeliveryReceiptSubscription {
    
    private CallbackReference callbackReference;

    /**
     * @return the callbackReference
     */
    public CallbackReference getCallbackReference() {
        return callbackReference;
    }

    /**
     * @param callbackReference the callbackReference to set
     */
    public void setCallbackReference(CallbackReference callbackReference) {
        this.callbackReference = callbackReference;
    }
    
    private SenderAddresses[] senderAddresses;

    /**
     * @return the senderAddresses
     */
    public SenderAddresses[] getSenderAddresses() {
        return senderAddresses;
    }

    /**
     * @param senderAddresses the senderAddresses to set
     */
    public void setSenderAddresses(SenderAddresses[] senderAddresses) {
        this.senderAddresses = senderAddresses;
    }
    
    private String resourceURL;

    /**
     * @return the resourceURL
     */
    public String getResourceURL() {
        return resourceURL;
    }

    /**
     * @param resourceURL the resourceURL to set
     */
    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
    
    private String clientCorrelator;

    /**
     * @return the clientCorrelator
     */
    public String getClientCorrelator() {
        return clientCorrelator;
    }

    /**
     * @param clientCorrelator the clientCorrelator to set
     */
    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }
}
