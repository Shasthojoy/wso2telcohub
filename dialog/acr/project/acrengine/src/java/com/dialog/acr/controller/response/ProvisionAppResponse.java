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
public class ProvisionAppResponse {
    
    private String appID;
    private String serviceProviderID;
    private String appName;
    private String serviceProviderAppId;
    private String description;
    private String status;

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
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

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

    /**
     * @return the serviceProviderAppId
     */
    public String getServiceProviderAppId() {
        return serviceProviderAppId;
    }

    /**
     * @param serviceProviderAppId the serviceProviderAppId to set
     */
    public void setServiceProviderAppId(String serviceProviderAppId) {
        this.serviceProviderAppId = serviceProviderAppId;
    }
}
