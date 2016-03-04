/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharanga_07219
 */
@XmlRootElement(name = "inboundSMSMessageNotification")
public class inboundSMSMessageNotification {
    private String callbackData = "";
    
    private inboundSMSMessage inboundSMSMessage;
    
    public inboundSMSMessageNotification() {
    }
    
    public String getcallbackData() {
            return callbackData;
    }

    public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
    }
    
    public inboundSMSMessage getInboundSMSMessage() {
            return inboundSMSMessage;
    }
    
    public void setInboundSMSMessage (inboundSMSMessage inboundSMSMessage) {
            this.inboundSMSMessage = inboundSMSMessage;
    }
    
}
