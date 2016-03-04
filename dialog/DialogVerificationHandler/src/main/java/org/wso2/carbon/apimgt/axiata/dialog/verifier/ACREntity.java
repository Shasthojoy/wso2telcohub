/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.apimgt.axiata.dialog.verifier;

/**
 *
 * @author tharanga_07219
 */
public class ACREntity {
    private DecodeAcrRequest decodeAcrRequest;
    
    public ACREntity() {
    }


    public DecodeAcrRequest getdecodeAcr() {
            return decodeAcrRequest;
    }


    public void setdecodeAcr(DecodeAcrRequest decodeAcrRequest) {
            this.decodeAcrRequest = decodeAcrRequest;
    }
    
}
