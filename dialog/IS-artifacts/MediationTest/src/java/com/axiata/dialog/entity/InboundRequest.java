/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tharanga_07219
 */
@XmlRootElement(name = "inboundSMSMessageNotification")
public class InboundRequest {
    private inboundSMSMessageNotification inboundSMSMessageNotification;
    
    public InboundRequest() {
    }


    public inboundSMSMessageNotification getInboundSMSMessageRequest() {
            return inboundSMSMessageNotification;
    }


    public void setInboundSMSMessageRequest(inboundSMSMessageNotification inboundSMSMessageNotification) {
            this.inboundSMSMessageNotification = inboundSMSMessageNotification;
    }
    
}
