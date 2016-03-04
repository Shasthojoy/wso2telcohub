/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.apimgt.axiata.dialog.verifier;

/**
 *
 * @author Tharanga_07219
 */

//Dummy ACR module 
public class ACRModule {
    
    public static String getMSISDNFromACR(String acrNumber){
        acrNumber= acrNumber.replace("tel%3A%2B", "");
        acrNumber= acrNumber.replace("tel:+", "");
        acrNumber= acrNumber.replace("tel:", "");

        return  acrNumber;
    }
    
}
