/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.entity;

/**
 *
 * @author Tharanga_07219
 */
public class inboundSMSMessage {
    
    private String dateTime = "";
    private String destinationAddress = "";
    private String messageId = "";
    private String message = "";
    private String senderAddress = "";
    
    
    public inboundSMSMessage() {
    }
    
    public String getdateTime() {
            return dateTime;
    }

    public void setdateTime(String dateTime) {
            this.dateTime = dateTime;
    }
    
    public String getdestinationAddress() {
            return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
            this.destinationAddress= destinationAddress;
    }
    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getSenderAddress() {
            return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
            this.senderAddress= senderAddress;
    }
}
