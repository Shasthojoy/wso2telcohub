/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.entity;

import com.mycompany.mavenproject1.entity.UserRegistrationStatus;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserRegistrationStatus")
/**
 *
 * @author tharanga_07219
 */
public class UserRegistrator {
    
    private UserRegistrationStatus userRegistration;
    
    public UserRegistrator() {
    }


    public UserRegistrationStatus getOutboundSMSMessageRequest() {
            return userRegistration;
    }


    public void setOutboundSMSMessageRequest(UserRegistrationStatus userRegistration) {
            this.userRegistration = userRegistration;
    }
    
    
    
}
