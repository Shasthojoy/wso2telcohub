/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

/**
 *
 * @author tharanga_07219
 */
public class policyException {
    private String messageId;
    private String text;
    private String variables;
    
    public String getMessageID() {
            return messageId;
    }

    public void setMessageID(String messageId) {
            this.messageId = messageId;
    }
    
    public String getText() {
            return text;
    }

    public void setText(String text) {
            this.text = text;
    }
    
    public String getVariables() {
            return variables;
    }

    public void setVariables(String variables) {
            this.variables = variables;
    }
    
    
}
