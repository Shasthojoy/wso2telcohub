/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dialog.acr.controller.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shashika Wijayasekera
 */
public class CreateACRResponse {
    
    private String appID;
    private String serviceProviderID;
    private List<ACRInfo> acrInfo = new ArrayList<ACRInfo>();

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
     * @return the acrInfo
     */
    public List<ACRInfo> getAcrInfo() {
        return acrInfo;
    }

    /**
     * @param acrInfo the acrInfo to set
     */
    public void setAcrInfo(List<ACRInfo> acrInfo) {
        this.acrInfo = acrInfo;
    }
    
    public static class ACRInfo {
        
        private String msisdn;
        private String acr;
        private String expiry;

        /**
         * @return the msisdn
         */
        public String getMsisdn() {
            return msisdn;
        }

        /**
         * @param msisdn the msisdn to set
         */
        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
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
}
