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
public class errorRoute {
    
    private requestError requestError;
    
    public errorRoute() {
        
    }
    public requestError getErrorResponse() {
            return requestError;
    }


    public void setErrorResponse(requestError requestError) {
            this.requestError = requestError;
    }
    
}
