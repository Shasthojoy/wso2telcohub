/*
 * Errorreturn.java
 * Dec 5, 2013  2:41:42 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package lk.dialog.utils;

/**
 * <TO-DO> <code>Errorreturn</code>
 * @version $Id: Errorreturn.java,v 1.00.000
 */
public class Errorreturn {

    private String errcode;
    private String[] errvar;

    public Errorreturn(String errcode, String[] errvar) {
        this.errcode = errcode;
        this.errvar = errvar;
    }    
    
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String[] getErrvar() {
        return errvar;
    }

    public void setErrvar(String[] errvar) {
        this.errvar = errvar;
    }
    
}
