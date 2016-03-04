/*
 * BalResponse.java
 * Oct 14, 2014  10:03:33 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package lk.dialog.cg.service;

/**
 * <TO-DO> <code>BalResponse</code>
 * @version $Id: BalResponse.java,v 1.00.000
 */
public class BalResponse {

    double availablebal =0;
    String accountType = null;
    String accountStatus = null;
    double creditLimit = 0;
    private double outstanding = 0;

    public BalResponse(double availablebal,String accountType, String accontStatus,double creditLimit,double outstanding ) {
        this.availablebal = availablebal;
        this.accountType = accountType;
        this.accountStatus = accontStatus;  
        this.creditLimit = creditLimit;
        this.outstanding = outstanding;
    }

    public double getAvailablebal() {
        return availablebal;
    }

    public void setAvailablebal(double availablebal) {
        this.availablebal = availablebal;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }   

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * @return the creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * @param creditLimit the creditLimit to set
     */
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * @return the outstanding
     */
    public double getOutstanding() {
        return outstanding;
    }

    /**
     * @param outstanding the outstanding to set
     */
    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
    }
    
    
    
    
}
