/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ServiceProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ServiceProviderFunctions {
    
    public static boolean saveServiceProvider(ServiceProvider provider){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.save(provider);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveServiceProvider: " + e);
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
    
    public static boolean updateServiceProvider(ServiceProvider provider){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.update(provider);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateServiceProvider: " + e);
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
