/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.entity;

/**
 *
 * @author dialog Axiata
 */
public class UserRegistrationStatus {
    
    private String registrationURL = "";
    private String address = "";
    private String status = "";
    private String authenticator = "";
    
    
    public String getRegistartionURL() {
            return registrationURL;
    }

    public void setRegistartionURL(String registrationURL) {
            this.registrationURL = registrationURL;
    }
    public String getAddress() {
            return address;
    }

    public void setAddress(String address) {
            this.address = address;
    }
    
    public String getStatus() {
            return status;
    }

    public void setStatus(String status) {
            this.status = status;
    }
    public String getAuthenticator() {
            return authenticator;
    }

    public void setAuthenticator(String authenticator) {
            this.authenticator = authenticator;
    }
    
}
