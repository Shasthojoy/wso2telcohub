/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.entity;

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
