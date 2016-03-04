/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ApplicationKey;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ApplicationKeyFunctions {
    
    public static boolean saveApplicationKey(ApplicationKey key){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.save(key);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveApplicationKey: " + e);
            try{
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex){
                System.out.println("Transaction rollback failed! "+ex);
            }
        }finally{
            s.close();
        }
        return actionState;
    }
    
    public static boolean updateApplicationKey(ApplicationKey key){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.update(key);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateApplicationKey: " + e);
            try{
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex){
                System.out.println("Transaction rollback failed! "+ex);
            }
        }finally{
            s.close();
        }
        return actionState;
    }
}
