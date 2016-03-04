/*
 * SmsService.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;


import com.axiata.dialog.entity.ISMSresponse;
import com.axiata.dialog.entity.Operatorsubs;
import com.axiata.dialog.entity.OperatorsubsRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * Intermediate Service Class to handle service implementations for API
 * requests.
 *
 * <code>SmsService</code>
 *
 * @version $Id: SmsService.java,v 1.00.000
 */
public class SmsService {

    private static final Logger LOG = Logger.getLogger(SmsService.class.getName());
    private ApplicationManager am;

    public SmsService() {
        this.am = new ApplicationManager();
    }
    
    
    
    
    /**
     *
     * @param msg
     * @param length
     * @return
     */
    public Integer subsentry(List<Operatorsubs> subs) {
        
        Integer axiatid = am.savedomainsubs(subs);        
        return axiatid;
    }
    
    public List<Operatorsubs> subsquery(String axiataid) {
        
        List<Operatorsubs> operatorsubs = am.getdomainsubs(axiataid);
        return operatorsubs;
    }
    
    
}
