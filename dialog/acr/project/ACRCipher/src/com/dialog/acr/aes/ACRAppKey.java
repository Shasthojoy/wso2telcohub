/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes;

import com.dialog.acr.aes.key.AppSaltKey;

/**
 *
 * @author Shashika Wijayasekera
 */
public final class ACRAppKey {

    public ACRAppKey(){
        
    }
    
    public ACRAppKey(int appID) throws Exception {

        if(appID != 0){
            
            this.setAppID(Integer.toString(appID));
            this.setAppSaltKey(AppSaltKey.getAppSaltKey(this.getAppID()));
        }       
    }

    private String appID;
    private String appSaltKey;

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
     * @return the appSaltKey
     */
    public String getAppSaltKey() throws Exception {
        return appSaltKey;
    }

    /**
     * @param appSaltKey the appSaltKey to set
     */
    private void setAppSaltKey(String appSaltKey) {
        this.appSaltKey = appSaltKey;
    }
}
