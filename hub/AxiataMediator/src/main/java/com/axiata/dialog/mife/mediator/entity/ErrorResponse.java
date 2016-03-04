/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tharanga_07219
 */

@XmlRootElement(name = "errorreturn")
public class ErrorResponse {
    
    private errorreturn errorreturn;
    
    public ErrorResponse() {
        
    }
    public errorreturn getErrorResponse() {
            return errorreturn;
    }


    public void setErrorResponse(errorreturn errorreturn) {
            this.errorreturn = errorreturn;
    }
    
}
