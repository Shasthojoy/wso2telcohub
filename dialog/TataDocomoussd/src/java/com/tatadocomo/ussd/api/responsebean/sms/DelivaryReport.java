/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatadocomo.ussd.api.responsebean.sms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tharanga_07219
 */
@Entity
@Table(name = "DELIVARYREPORT")
public class DelivaryReport {
    @Id
    @Column(name = "MESSAGE_ID", unique = true)
    private String messageID = "";
    
    @Column(name = "APP_ID", nullable = true)
    private String appID = "";
    
    @Column(name = "ADDRESS", nullable = true)
    private String address = "";
    
    @Column(name = "MESSAGESTATUS", nullable = true)
    private String msgStatus = "";
 
    @Column(name = "MESSAGETEXT", nullable = true)
    private String msgText = "";
    
    
    
    
    public String getAPPID() {
        return appID;
    }
    
    public void setAPPID(String appID) {
        this.appID = appID;
    }
    
    public String getMessageID(){
        return messageID;
    }
    
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
    
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public String getMessageText() {
        return msgText;
    }
    public void setMessageText(String msgText){
        this.msgText = msgText;
    }
    
    public String getMessageStatus() {
        return msgStatus;
    }
    public void setMessageStatus(String msgStatus){
        this.msgStatus = msgStatus;
    }
    
    public DelivaryReport() {
    }
}
