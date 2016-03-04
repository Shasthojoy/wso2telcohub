/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

/**
 *
 * @author tharanga_07219
 */
public class USSDReceive {
    
    private String message = "";
    private String ussdOperation = "";
    private String requestId = "";
    private String sessionId = "";
    private String encoding = "";
    private String sourceAddress = "";
    private String applicationId = "";
    private String version = "";
    
    
    public USSDReceive() {
    }
    
    public String getUssdOperation() {
            return ussdOperation;
    }

    public void setUssdOperation(String ussdOperation) {
            this.ussdOperation = ussdOperation;
    }
    
    
    public String getRequestID() {
            return requestId;
    }

    public void setRequestID(String requestID) {
            this.requestId = requestID;
    }
    
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public String getEncoding() {
            return encoding;
    }

    public void setEncoding(String encoding) {
            this.encoding= encoding;
    }
    
    
    public String getSourceAddress() {
            return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
            this.sourceAddress= sourceAddress;
    }
    
    
    public String getApplicationId() {
            return applicationId;
    }

    public void setApplicationID(String applicationId) {
            this.applicationId = applicationId;
    }
    
    public String getVersion() {
            return version;
    }

    public void setVersion(String version) {
            this.version= version;
    }
    
}
