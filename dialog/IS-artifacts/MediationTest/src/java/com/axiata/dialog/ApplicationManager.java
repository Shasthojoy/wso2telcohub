/*
 * ApplicationManager.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.entity.Operatorsubs;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

/**
 * Database Utility
 * <code>class</code> for the SMS messaging API implementation
 * <code>ApplicationManager</code>
 *
 * @version $Id: ApplicationManager.java,v 1.00.000
 */
public class ApplicationManager {

    public ApplicationManager() {
    }

    public List<Operatorsubs> getdomainsubs(String axiataid) {
        Session sess = null;
        List<Operatorsubs> listmsg = null;

        try {
            sess = HibernateUtil.getSession();
            listmsg = sess.createQuery("from Operatorsubs where axiataid = ? ").setString(0, axiataid).list();
        } catch (Exception e) {
            //LOG.error("[SubscriptionService], getMessageInbound + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }

        return listmsg;
    }

    public Integer savedomainsubs(List<Operatorsubs> subs) {

        Session sess = null;
        Transaction tx = null;
        int maxID =0;

        try {

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();

            List list = sess.createQuery("select max(axiataid) from Operatorsubs").list();
             maxID = ( (Integer)list.get(0) ).intValue()+ 1; 
                        
            for (Operatorsubs sub : subs) {
                sub.setAxiataid(String.valueOf(maxID));
                sub.setCreated("smsapi");
                sub.setCreatedDate(new Date());
                sub.setLastupdated("smsapi");
                sub.setLastupdatedDate(new Date());
                sess.save(sub);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            //LOG.error("[SubscriptionService], saveSMSInbound + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        return maxID;
    }
}
