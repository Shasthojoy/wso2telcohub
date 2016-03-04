/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.utils;

import com.mycompany.mavenproject1.entity.ProvisionAuthenticator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProvisionAuthenticator")
/**
 *
 * @author tharanga_07219
 */
public class AuthenticatorProvisioner {
    
    private ProvisionAuthenticator provisionedAuthenticator;
    private String callbackURL;
    
    public AuthenticatorProvisioner() {
    }


    public ProvisionAuthenticator getProvisionedAuthenticator() {
            return provisionedAuthenticator;
    }


    public void setProvisionedAuthenticator(ProvisionAuthenticator provisionedAuthenticator) {
            this.provisionedAuthenticator = provisionedAuthenticator;
    }
    
    
    public String getCallbackURL(){
        return this.callbackURL;
        
    }
    
    public void setCallbackURL(String callbackURL){
        this.callbackURL = callbackURL;
    }
    
    
}