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
public class NBRetrieveRequest {
    
    private InboundSMSMessages inboundSMSMessages;

    /**
     * @return the inboundSMSMessages
     */
    public InboundSMSMessages getInboundSMSMessages() {
        return inboundSMSMessages;
    }

    /**
     * @param inboundSMSMessages the inboundSMSMessages to set
     */
    public void setInboundSMSMessages(InboundSMSMessages inboundSMSMessages) {
        this.inboundSMSMessages = inboundSMSMessages;
    }
}
