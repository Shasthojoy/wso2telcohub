/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.am.axiata.dialog.mediator.entity;

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
