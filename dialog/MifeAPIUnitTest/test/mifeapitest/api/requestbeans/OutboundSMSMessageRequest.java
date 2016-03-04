/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mifeapitest.api.requestbeans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shashika Wijayasekera
 */
public class OutboundSMSMessageRequest {

    private String senderAddress;
    private String senderName;
    private String clientCorrelator;
    private List<String> address = new ArrayList<String>();
    private OutboundSMSTextMessage outboundSMSTextMessage;
    private ReceiptRequest receiptRequest;

    /**
     * @return the senderAddress
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * @param senderAddress the senderAddress to set
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the clientCorrelator
     */
    public String getClientCorrelator() {
        return clientCorrelator;
    }

    /**
     * @param clientCorrelator the clientCorrelator to set
     */
    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    /**
     * @return the address
     */
    public List<String> getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(List<String> address) {
        this.address = address;
    }

    /**
     * @return the outboundSMSTextMessage
     */
    public OutboundSMSTextMessage getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    /**
     * @param outboundSMSTextMessage the outboundSMSTextMessage to set
     */
    public void setOutboundSMSTextMessage(OutboundSMSTextMessage outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

    /**
     * @return the receiptRequest
     */
    public ReceiptRequest getReceiptRequest() {
        return receiptRequest;
    }

    /**
     * @param receiptRequest the receiptRequest to set
     */
    public void setReceiptRequest(ReceiptRequest receiptRequest) {
        this.receiptRequest = receiptRequest;
    }
    
    public static class OutboundSMSTextMessage {

        private String message;

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ReceiptRequest {

        private String notifyURL;
        private String callbackData;

        /**
         * @return the notifyURL
         */
        public String getNotifyURL() {
            return notifyURL;
        }

        /**
         * @param notifyURL the notifyURL to set
         */
        public void setNotifyURL(String notifyURL) {
            this.notifyURL = notifyURL;
        }

        /**
         * @return the callbackData
         */
        public String getCallbackData() {
            return callbackData;
        }

        /**
         * @param callbackData the callbackData to set
         */
        public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
        }
    }
}
