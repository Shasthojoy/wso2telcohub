/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.psi.api.responsebean.sms;

/**
 *
 * @author tharanga_07219
 */
public class deliveryReceiptSubscription {
    private String filterCriteria = "";
    private CallbackReference callbackReference;
    
    public deliveryReceiptSubscription() {
    }
    
    public String getFilterCriteria() {
            return filterCriteria;
    }


    public void setFilterCriteria(String filterCriteria) {
            this.filterCriteria = filterCriteria;
    }
    
    public CallbackReference getCallbackReference() {
            return callbackReference;
    }


    public void setCallbackReference(CallbackReference callbackReference) {
            this.callbackReference = callbackReference;
    }
    
    
}
