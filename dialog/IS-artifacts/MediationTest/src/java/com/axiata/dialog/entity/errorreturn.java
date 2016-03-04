/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.am.axiata.dialog.mediator.entity;

/**
 *
 * @author tharanga_07219
 */
public class errorreturn {
    private String[] errvar;
    private String errcode;
    
    public errorreturn() {
        
    }

    public String getErrorReturnCode() {
            return errcode;
    }
    
    public void setErrorReturnCode() {
            this.errcode = errcode;
    }
    
    public String[] getErrorVar() {
            return errvar;
    }
    
    public void setErrorVar() {
            this.errvar = errvar;
    }
    
    
}
