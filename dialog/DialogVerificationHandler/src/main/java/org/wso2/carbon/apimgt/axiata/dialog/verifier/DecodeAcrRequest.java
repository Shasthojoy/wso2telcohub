/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.apimgt.axiata.dialog.verifier;

/**
 *
 * @author tharanga_07219
 */
public class DecodeAcrRequest {
    private String acr;
    
    public DecodeAcrRequest() {
    }


    public String getAcr() {
            return acr;
    }


    public void setACR(String acr) {
            this.acr = acr;
    }
}
