/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dialog.acr.controller.response;

/**
 *
 * @author User
 */
public class RefreshACRResponse {
    
    private String appID;
    private String serviceProviderID;
    private String acr;
    private String expiry;

    /**
     * @return the appID
     */
    public String getAppID() {
        return appID;
    }

    /**
     * @param appID the appID to set
     */
    public void setAppID(String appID) {
        this.appID = appID;
    }

    /**
     * @return the serviceProviderID
     */
    public String getServiceProviderID() {
        return serviceProviderID;
    }

    /**
     * @param serviceProviderID the serviceProviderID to set
     */
    public void setServiceProviderID(String serviceProviderID) {
        this.serviceProviderID = serviceProviderID;
    }

    /**
     * @return the acr
     */
    public String getAcr() {
        return acr;
    }

    /**
     * @param acr the acr to set
     */
    public void setAcr(String acr) {
        this.acr = acr;
    }

    /**
     * @return the expiry
     */
    public String getExpiry() {
        return expiry;
    }

    /**
     * @param expiry the expiry to set
     */
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
