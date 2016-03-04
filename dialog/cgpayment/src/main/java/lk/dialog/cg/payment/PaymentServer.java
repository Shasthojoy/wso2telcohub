
/*
 * PaymentServer.java
 * Feb 25, 2014  1:24:33 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package lk.dialog.cg.payment;

import java.util.HashMap;
import lk.dialog.cg.service.pool.PaymentSessionPool;
import lk.dialog.utils.FileUtil;

/**
 * <TO-DO>
 * <code>PaymentServer</code>
 *
 * @version $Id: PaymentServer.java,v 1.00.000
 */
public class PaymentServer {

    private final HashMap<String, PaymentSessionPool> payments = new HashMap<String, PaymentSessionPool>();
    private static PaymentServer s_instance = null;
    String configpath = FileUtil.getApplicationProperty("cg.config");

    public static PaymentServer getInstance() {
        if (s_instance == null) {
            s_instance = new PaymentServer();
        }
        return s_instance;
    }

    public PaymentSessionPool getCGPayment(String domain, boolean create) throws Exception {

        synchronized (payments) {
            for (String pair : payments.keySet()) {

                if (domain.equals(pair)) {

                    PaymentSessionPool session = payments.get(pair);
                    return session;
                }
            }
        }
        
        if (create) {
            //String pair = domain;
            PaymentSessionPool session = new PaymentSessionPool(configpath, domain, create);
            put(domain, session);

            return session;

        } else {
            return null;
        }
    }

    private void put(String pair, PaymentSessionPool session) {
        synchronized (payments) {
            payments.put(pair, session);
        }
    }
}
