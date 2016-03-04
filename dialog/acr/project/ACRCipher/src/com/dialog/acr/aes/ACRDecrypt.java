/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes;

import com.dialog.acr.aes.decrypt.Decrypt;
import com.dialog.acr.aes.util.SplitString;
import java.net.URLDecoder;

/**
 *
 * @author Shashika Wijayasekera
 */
public final class ACRDecrypt {

    public ACRDecrypt() {

    }

    public ACRDecrypt(String encryptedACR, String appSaltKey) throws Exception {

        if (encryptedACR != "" && encryptedACR != null && appSaltKey != "" && appSaltKey != null && appSaltKey.length() == 16) {

            this.setAppSaltKey(appSaltKey);
            this.setEncryptedACR(encryptedACR);
            this.setDecryptedACR(Decrypt.acrDecrypt(URLDecoder.decode(this.getEncryptedACR(), "UTF-8"), this.getAppSaltKey()));

            String[] splittedACR = SplitString.getSplittedString(this.getDecryptedACR());

            this.setAppID(splittedACR[0].toString());
            this.setServiceProviderID(splittedACR[1].toString());
            this.setMsisdn(splittedACR[2].toString());
            this.setAcrCreatedDateTime(splittedACR[3].toString());

            /*System.out.println("\n");
            System.out.println("App ID: " + this.getAppID());
            System.out.println("Service Provider ID: " + this.getServiceProviderID());
            System.out.println("MSISDN: " + this.getMsisdn());
            System.out.println("ACR Created Time: " + this.getAcrCreatedDateTime());
            System.out.println("\n");*/
        }
    }

    private String encryptedACR;
    private String decryptedACR;
    private String appSaltKey;
    private String appID;
    private String serviceProviderID;
    private String msisdn;
    private String acrCreatedDateTime;

    /**
     * @return the encryptedACR
     */
    private String getEncryptedACR() {
        return encryptedACR;
    }

    /**
     * @param encryptedACR the encryptedACR to set
     */
    private void setEncryptedACR(String encryptedACR) {
        this.encryptedACR = encryptedACR;
    }

    /**
     * @return the decryptedACR
     */
    private String getDecryptedACR() {
        return decryptedACR;
    }

    /**
     * @param decryptedACR the decryptedACR to set
     */
    private void setDecryptedACR(String decryptedACR) {
        this.decryptedACR = decryptedACR;
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
     * @return the appID
     */
    public String getAppID() throws Exception {
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
    public String getServiceProviderID() throws Exception {
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
    public String getMsisdn() throws Exception {
        return msisdn;
    }

    /**
     * @param msisdn the msisdn to set
     */
    private void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * @return the acrCreatedDateTime
     */
    public String getAcrCreatedDateTime() throws Exception {
        return acrCreatedDateTime;
    }

    /**
     * @param acrCreatedDateTime the acrCreatedDateTime to set
     */
    private void setAcrCreatedDateTime(String acrCreatedDateTime) {
        this.acrCreatedDateTime = acrCreatedDateTime;
    }
}
