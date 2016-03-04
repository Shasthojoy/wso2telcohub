/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.axiata.dialog.mife.mediator.entity.nb;

/**
 *
 * @author User
 */
public class InboundSMSMessages {
    
    private Registrations [] registrations;

    /**
     * @return the registrations
     */
    public Registrations[] getRegistrations() {
        return registrations;
    }
    
    /**
     * @param registrations the registrations to set
     */
    public void setRegistrations(Registrations[] registrations) {
        this.registrations = registrations;
    }

    private String maxBatchSize;

    /**
     * @return the maxBatchSize
     */
    public String getMaxBatchSize() {
        return maxBatchSize;
    }

    /**
     * @param maxBatchSize the maxBatchSize to set
     */
    public void setMaxBatchSize(String maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
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
