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
public class InboundSMSMessageList {
    
    private InboundSMSMessage [] inboundSMSMessage;

    /**
     * @return the inboundSMSMessage
     */
    public InboundSMSMessage[] getInboundSMSMessage() {
        return inboundSMSMessage;
    }

    /**
     * @param inboundSMSMessage the inboundSMSMessage to set
     */
    public void setInboundSMSMessage(InboundSMSMessage[] inboundSMSMessage) {
        this.inboundSMSMessage = inboundSMSMessage;
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
    
    private String totalNumberOfPendingMessages;

    /**
     * @return the totalNumberOfPendingMessages
     */
    public String getTotalNumberOfPendingMessages() {
        return totalNumberOfPendingMessages;
    }

    /**
     * @param totalNumberOfPendingMessages the totalNumberOfPendingMessages to set
     */
    public void setTotalNumberOfPendingMessages(String totalNumberOfPendingMessages) {
        this.totalNumberOfPendingMessages = totalNumberOfPendingMessages;
    }
    
    private String numberOfMessagesInThisBatch;

    /**
     * @return the numberOfMessagesInThisBatch
     */
    public String getNumberOfMessagesInThisBatch() {
        return numberOfMessagesInThisBatch;
    }

    /**
     * @param numberOfMessagesInThisBatch the numberOfMessagesInThisBatch to set
     */
    public void setNumberOfMessagesInThisBatch(String numberOfMessagesInThisBatch) {
        this.numberOfMessagesInThisBatch = numberOfMessagesInThisBatch;
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
