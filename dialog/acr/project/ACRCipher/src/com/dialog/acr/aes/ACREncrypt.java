/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes;

import com.dialog.acr.aes.encrypt.Encrypt;
import java.net.URLEncoder;

/**
 *
 * @author Shashika Wijayasekera
 */
public final class ACREncrypt {

    public ACREncrypt() {

    }

    public ACREncrypt(String appID, String serviceProviderID, String msisdn, String appSaltKey) throws Exception {

        if (appID != "" && appID != null && serviceProviderID != "" && serviceProviderID != null && msisdn != "" && msisdn != null && appSaltKey != "" && appSaltKey != null && appSaltKey.length() == 16) {

            this.setAppID(appID);
            this.setServiceProviderID(serviceProviderID);
            this.setMsisdn(msisdn);
            this.setAppSaltKey(appSaltKey);
            this.setEncryptedACR(Encrypt.acrEncrypt(this.getAppID(), this.getServiceProviderID(), this.getMsisdn(), this.getAppSaltKey()));
        }
    }

    private String appID;
    private String serviceProviderID;
    private String msisdn;
    private String appSaltKey;
    private String encryptedACR;

    /**
     * @return the appID
     */
    private String getAppID() {
        return appID;
    }

    /**
     * @param appID the appID to set
     */
    private void setAppID(String appID) {
        this.appID = appID;
    }

    /**
     * @return the serviceProviderID
     */
    private String getServiceProviderID() {
        return serviceProviderID;
    }

    /**
     * @param serviceProviderID the serviceProviderID to set
     */
    private void setServiceProviderID(String serviceProviderID) {
        this.serviceProviderID = serviceProviderID;
    }

    /**
     * @return the msisdn
     */
    private String getMsisdn() {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    private void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the appSaltKey
     */
    private String getAppSaltKey() {
        return appSaltKey;
    }

    /**
     * @param appSaltKey the appSaltKey to set
     */
    private void setAppSaltKey(String appSaltKey) {
        this.appSaltKey = appSaltKey;
    }

    /**
     * @return the encryptedACR
     */
    public String getEncryptedACR() throws Exception {
        //return URLEncoder.encode(encryptedACR, "UTF-8");
        return encryptedACR;
    }

    /**
     * @param encryptedACR the encryptedACR to set
     */
    private void setEncryptedACR(String encryptedACR) {
        this.encryptedACR = encryptedACR;
    }

}
