/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mifeapitest.api.requestbeans;

/**
 *
 * @author Shashika Wijayasekera
 */
public class SendSMSRequest {
    
    private OutboundSMSMessageRequest outboundSMSMessageRequest;

    /**
     * @return the outboundSMSMessageRequest
     */
    public OutboundSMSMessageRequest getOutboundSMSMessageRequest() {
        return outboundSMSMessageRequest;
    }

    /**
     * @param outboundSMSMessageRequest the outboundSMSMessageRequest to set
     */
    public void setOutboundSMSMessageRequest(OutboundSMSMessageRequest outboundSMSMessageRequest) {
        this.outboundSMSMessageRequest = outboundSMSMessageRequest;
    }
}
