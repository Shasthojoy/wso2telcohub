/*
 * InboundSMSMessageList.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.dialog.psi.api.responsebean.sms;

import java.util.List;

/**
 * List containing Inbound SMS Messages
 * <code>InboundSMSMessageList</code>
 *
 * @version $Id: InboundSMSMessageList.java,v 1.00.000
 */
public class InboundSMSMessageList {

    private List<InboundSMSMessage> inboundSMSMessage;

    public List<InboundSMSMessage> getInboundSMSMessage() {
        return inboundSMSMessage;
    }

    public void setInboundSMSMessage(List<InboundSMSMessage> inboundSMSMessage) {
        this.inboundSMSMessage = inboundSMSMessage;
    }
    private String numberOfMessagesInThisBatch;

    public String getNumberOfMessagesInThisBatch() {
        return numberOfMessagesInThisBatch;
    }

    public void setNumberOfMessagesInThisBatch(String numberOfMessagesInThisBatch) {
        this.numberOfMessagesInThisBatch = numberOfMessagesInThisBatch;
    }
    private String resourceURL;

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
    private String totalNumberOfPendingMessages;

    public String getTotalNumberOfPendingMessages() {
        return totalNumberOfPendingMessages;
    }

    public void setTotalNumberOfPendingMessages(String totalNumberOfPendingMessages) {
        this.totalNumberOfPendingMessages = totalNumberOfPendingMessages;
    }
}