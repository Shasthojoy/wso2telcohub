/*
 * RequestError.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package lk.dialog.utils;

/**
 * <TO-DO>
 * <code>RequestError</code>
 *
 * @version $Id: RequestError.java,v 1.00.000
 */

public class RequestError{

    private Errorreturn errorreturn;

    public RequestError(Errorreturn errorreturn) {
        this.errorreturn = errorreturn;
    }
        
    public Errorreturn getErrorreturn() {
        return errorreturn;
    }

    public void setErrorreturn(Errorreturn errorreturn) {
        this.errorreturn = errorreturn;
    }

    
}