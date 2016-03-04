/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.axiata.dialog.mife.mediator.entity.nb;

import com.axiata.dialog.mife.mediator.entity.sb.*;

/**
 *
 * @author User
 */
public class NBRetrieveResponse {
    
    private InboundSMSMessageList inboundSMSMessageList;

    /**
     * @return the inboundSMSMessageList
     */
    public InboundSMSMessageList getInboundSMSMessageList() {
        return inboundSMSMessageList;
    }

    /**
     * @param inboundSMSMessageList the inboundSMSMessageList to set
     */
    public void setInboundSMSMessageList(InboundSMSMessageList inboundSMSMessageList) {
        this.inboundSMSMessageList = inboundSMSMessageList;
    }
}
