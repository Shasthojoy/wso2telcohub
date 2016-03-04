/*
 * InboundSMSResponse.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.dialog.psi.api.responsebean.sms;

/**
 * SMS Message response container
 * <code>InboundSMSResponse</code>
 *
 * @version $Id: InboundSMSResponse.java,v 1.00.000
 */
public class InboundSMSResponse implements ISMSresponse {

    private InboundSMSMessageList inboundSMSMessageList;

    public InboundSMSMessageList getInboundSMSMessageList() {
        return inboundSMSMessageList;
    }

    public void setInboundSMSMessageList(InboundSMSMessageList inboundSMSMessageList) {
        this.inboundSMSMessageList = inboundSMSMessageList;
    }
}