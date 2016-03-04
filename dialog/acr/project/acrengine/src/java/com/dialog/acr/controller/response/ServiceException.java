package com.dialog.acr.controller.response;

/**
 *
 * @author Hiranya
 */
public class ServiceException {
    private String messageId;
    private String text;
    private String[] variables;

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the variables
     */
    public String[] getVariables() {
        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(String[] variables) {
        this.variables = variables;
    }
    
    
}
