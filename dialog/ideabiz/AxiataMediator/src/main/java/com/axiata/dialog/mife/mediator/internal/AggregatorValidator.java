/*
 * AggregatorValidator.java
 * Oct 17, 2014  1:41:34 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package com.axiata.dialog.mife.mediator.internal;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.oneapi.validation.AxiataException;

/**
 * <TO-DO> <code>AggregatorValidator</code>
 * @version $Id: AggregatorValidator.java,v 1.00.000
 */
public class AggregatorValidator {

    private AxiataDbService DbService;
    
    public AggregatorValidator() {
        DbService = new AxiataDbService();        
    }
    
    public void validateMerchant(int appid,String operatorid, String subscriber, String merchant) throws Exception {
         
        String merchantid =  DbService.blacklistedmerchant(appid, operatorid, subscriber, merchant);
        if ( merchantid != null) {
            throw new AxiataException("SVC0001", "", new String[]{merchant +" Not provisioned for "+operatorid});
        }                      
    }    
    
}
