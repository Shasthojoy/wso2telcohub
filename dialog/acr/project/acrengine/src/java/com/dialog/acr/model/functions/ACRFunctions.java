package com.dialog.acr.model.functions;

import com.dialog.acr.controller.response.CreateACRResponse;
import com.dialog.acr.controller.response.CreateACRResponse.ACRInfo;
import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ACR;
import com.dialog.acr.model.entities.ACRHistory;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ACRFunctions {

    public static final int ACR_DELETED = 101;
    public static final int ACR_ACTIVE = 1;
    public static final int ACR_DEACTIVE = 0;

    public static boolean saveACR(ACR acr) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.save(acr);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveACR: " + e);
            try {
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex) {
                System.out.println("Transaction rollback failed! " + ex);
            }
        }finally{
            s.close();
        }
        return actionState;
    }

    public static boolean saveACRList(List<ACR> acrList) {

        boolean returnState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            for (int x = 0; x < acrList.size(); x++) {
                ACR acr = acrList.get(x);
                s.save(acr);
            }
            tx.commit();
            returnState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveACRList: " + e);
            try {
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex) {
                System.out.println("Transaction rollback failed! " + ex);
            }
        }finally{
            s.close();
        }        
        return returnState;
    }

    public static boolean updateACR(ACR acr) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            ACR old_acr = (ACR) s.get(ACR.class, acr.getAcrId());

            ACRHistory acr_histry = new ACRHistory();
            acr_histry.setAcrObj(acr);
            acr_histry.setDate(new Date());
            acr_histry.setAcr(old_acr.getAcr());
            acr_histry.setAcrStatus(old_acr.getStatus());
            acr_histry.setMsisdn(old_acr.getMsisdn());
            acr_histry.setAcrVersion(old_acr.getVersion());

            tx = s.beginTransaction();
            s.merge(acr);
            s.save(acr_histry);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateACR: " + e);
            try {
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex) {
                System.out.println("Transaction rollback failed! " + ex);
            }
        }finally{
            s.close();
        }
        return actionState;
    }

    public static boolean updateACRStatus(int acrId, int status) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            ACR acr = (ACR) s.get(ACR.class, acrId);

            ACRHistory acr_histry = new ACRHistory();
            acr_histry.setAcrObj(acr);
            acr_histry.setDate(new Date());
            acr_histry.setAcr(acr.getAcr());
            acr_histry.setAcrStatus(acr.getStatus());
            acr_histry.setMsisdn(acr.getMsisdn());
            acr_histry.setAcrVersion(acr.getVersion());

            if (status == 1) {
                acr.setStatus(ACR_ACTIVE);
            } else if (status == 0) {
                acr.setStatus(ACR_DEACTIVE);
            } else if (status == 101) {
                acr.setStatus(ACR_DELETED);
            } else {
                System.out.println("ACR Status invalid!");
            }

            tx = s.beginTransaction();
            s.update(acr);
            s.save(acr_histry);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateACRStatus: " + e);
            try {
                s.getTransaction().rollback();
                System.out.println("Transaction rollbacked!");
            } catch (RuntimeException ex) {
                System.out.println("Transaction rollback failed! " + ex);
            }
        }finally{
            s.close();
        }
        return actionState;
    }
}
