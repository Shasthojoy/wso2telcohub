/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller.response;

import java.util.List;

/**
 *
 * @author Hiranya
 */
public class DeleteACRBatchResponse {

    private String batchSize;
    private List acrInfo;

    /**
     * @return the batchSize
     */
    public String getBatchSize() {
        return batchSize;
    }

    /**
     * @param batchSize the batchSize to set
     */
    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * @return the acrInfo
     */
    public List getAcrInfo() {
        return acrInfo;
    }

    /**
     * @param acrInfo the acrInfo to set
     */
    public void setAcrInfo(List acrInfo) {
        this.acrInfo = acrInfo;
    }

    public static class AcrDeleteInfo {

        private String appID;
        private String serviceProviderID;
        private String acr;
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
}
