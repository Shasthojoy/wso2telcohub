/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tharanga_07219
 */
@XmlRootElement(name = "outboundSMSMessageRequest")
public class SendSMSRequest implements ISMSresponse {
    private outboundSMSMessageRequest outboundSMSMessageRequest;
    
    public SendSMSRequest() {
    }


    public outboundSMSMessageRequest getOutboundSMSMessageRequest() {
            return outboundSMSMessageRequest;
    }


    public void setOutboundSMSMessageRequest(outboundSMSMessageRequest receiptRequest) {
            this.outboundSMSMessageRequest = receiptRequest;
    }
    
}
