/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.psi.api.responsebean.sms;

/**
 *
 * @author tharanga_07219
 */
public class SendSMSRequest {
    private outboundSMSMessageRequest outboundSMSMessageRequest;
    
    public SendSMSRequest() {
    }


    public outboundSMSMessageRequest getOutboundSMSMessageRequest() {
            return outboundSMSMessageRequest;
    }


    public void setOutboundSMSMessageRequest(String receiptRequest) {
            this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }
    
}
