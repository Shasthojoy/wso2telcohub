/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tharanga_07219
 */
@XmlRootElement(name = "requestError")
public class errorServiceException {
    
    private requestErrorService requestError;
    
    public errorServiceException() {
        
    }
    public requestErrorService getErrorResponse() {
            return requestError;
    }


    public void setErrorResponse(requestErrorService requestError) {
            this.requestError = requestError;
    }
    
}
