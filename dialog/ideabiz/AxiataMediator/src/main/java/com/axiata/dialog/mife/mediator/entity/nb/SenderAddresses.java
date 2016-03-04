/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.axiata.dialog.mife.mediator.entity.nb;

/**
 *
 * @author User
 */
public class SenderAddresses {
    
    private String senderAddress;

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
    
    private String operatorCode; 

    /**
     * @return the operatorCode
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * @param operatorCode the operatorCode to set
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    
    private String filterCriteria;

    /**
     * @return the filterCriteria
     */
    public String getFilterCriteria() {
        return filterCriteria;
    }

    /**
     * @param filterCriteria the filterCriteria to set
     */
    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }
    
    private String status;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
