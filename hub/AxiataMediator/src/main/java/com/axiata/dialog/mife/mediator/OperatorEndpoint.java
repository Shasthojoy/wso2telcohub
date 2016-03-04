/*
 * OperatorEndpoint.java
 * Mar 3, 2014  4:00:31 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.axiata.dialog.mife.mediator;

import org.apache.axis2.addressing.EndpointReference;

/**
 * <TO-DO> <code>OperatorEndpoint</code>
 * @version $Id: OperatorEndpoint.java,v 1.00.000
 */
public class OperatorEndpoint {

    EndpointReference endpointref;
    String operator;

    public OperatorEndpoint(EndpointReference endpointref, String operator) {
        this.endpointref = endpointref;
        this.operator = operator;
    }
    
    public EndpointReference getEndpointref() {
        return endpointref;
    }

    public void setEndpointref(EndpointReference endpointref) {
        this.endpointref = endpointref;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
}
