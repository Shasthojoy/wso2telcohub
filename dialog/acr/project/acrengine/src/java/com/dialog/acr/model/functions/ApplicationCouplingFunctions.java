/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ApplicationCoupling;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ApplicationCouplingFunctions {
    
    public static boolean saveAppCoupling(ApplicationCoupling acc){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.save(acc);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveAppCoupling: " + e);
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
    
    public static boolean deleteCoupling(ApplicationCoupling acc){
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.delete(acc);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in deleteCoupling: " + e);
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
