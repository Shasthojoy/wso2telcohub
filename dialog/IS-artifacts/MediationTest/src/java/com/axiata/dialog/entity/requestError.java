/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

/**
 *
 * @author tharanga_07219
 */
public class requestError {
    
     private policyException policyException;
     
    
     public requestError () {
         
     }
     
    public policyException getPolicyException() {
            return policyException;
    }


    public void setPolicyException(policyException policyException) {
            this.policyException= policyException;
    }
    
}
