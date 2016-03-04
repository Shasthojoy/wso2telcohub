/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

/**
 *
 * @author tharanga_07219
 */
public class requestErrorService {
    
    private serviceException serviceException;
     
    
     public requestErrorService () {
         
     }
     
    public serviceException getServiceException() {
            return serviceException;
    }


    public void setServiceException(serviceException serviceException) {
            this.serviceException= serviceException;
    }
    
}
