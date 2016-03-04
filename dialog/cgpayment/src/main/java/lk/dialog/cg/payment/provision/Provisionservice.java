/*
 * Provisionservice.java
 * Feb 26, 2014  10:24:20 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package lk.dialog.cg.payment.provision;

import com.axiata.dialog.dbutils.PaymentDbService;
import org.apache.log4j.Logger;

/**
 * <TO-DO> <code>Provisionservice</code>
 * @version $Id: Provisionservice.java,v 1.00.000
 */
public class Provisionservice {
    private static final Logger LOG = Logger.getLogger(Provisionservice.class.getName());
    
    public void provisionapp(Provisionreq provisionreq) throws Exception {
           Integer applicationid = Integer.parseInt(provisionreq.getProvisioninfo().getApplicationid());
           double amount = Double.parseDouble(provisionreq.getProvisioninfo().getMaxamount());
           String[] reasons = (String[])(provisionreq.getProvisioninfo().getResoncode()).toArray(new String[(provisionreq.getProvisioninfo().getResoncode()).size()]);
           
           new PaymentDbService().reasonEntry(applicationid, amount, reasons);
    }
    
    public void removeapp(String appid) throws Exception {
           Integer applicationid = Integer.parseInt(appid);
           new PaymentDbService().reasonDelete(applicationid);
    }
    
    public String queryapp(String appid) throws Exception {
           Integer applicationid = Integer.parseInt(appid);
           return new PaymentDbService().reasonQuery(applicationid);
    }
    
    
}
