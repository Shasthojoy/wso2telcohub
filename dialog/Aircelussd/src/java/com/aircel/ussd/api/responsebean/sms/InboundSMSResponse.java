/*
 * InboundSMSResponse.java
 * Apr 2, 2013  11:20:38 AM
 **
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.aircel.ussd.api.responsebean.sms;

import java.util.List;

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