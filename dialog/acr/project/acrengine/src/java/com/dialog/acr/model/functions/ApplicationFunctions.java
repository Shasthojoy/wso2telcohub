package com.dialog.acr.model.functions;

import com.dialog.acr.aes.ACRAppKey;
import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.Application;
import com.dialog.acr.model.entities.ApplicationKey;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ApplicationFunctions {

    public static final int APP_ACTIVE = 1;
    public static final int APP_DEACTIVE = 0;

    public static boolean saveApplication(Application application) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;

        try {
            tx = s.beginTransaction();
            s.save(application);

            int generatedAppId = application.getAppId();
            //String acrAppKey = new ACRAppKey(String.valueOf(generatedAppId)).getAppSaltKey();
            String acrAppKey = new ACRAppKey(generatedAppId).getAppSaltKey();

            ApplicationKey appKey = new ApplicationKey();
            appKey.setAppKey(acrAppKey);       //Set app Key
            appKey.setApplication(application);
            appKey.setCreatedOn(new Date());
            appKey.setVersion("1.0");
            appKey.setDuration("31556952000");

            s.save(appKey);

            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in saveApplication: " + e);
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

    public static boolean updateApplication(Application application) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            tx = s.beginTransaction();
            s.update(application);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateApplication: " + e);
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

    public static boolean updateAppStatus(int appid, int status) {
        boolean actionState = false;
        Session s = HibernateUtil.getSession();
        Transaction tx;
        try {
            Application a = (Application) s.get(Application.class, appid);
            if (status == 1) {
                a.setStatus(APP_ACTIVE);
            } else if (status == 0) {
                a.setStatus(APP_DEACTIVE);
            } else {
                System.out.println("App Status invalid!");
            }
            tx = s.beginTransaction();
            s.update(a);
            tx.commit();
            actionState = true;
        } catch (Exception e) {
            System.out.println("Exception in updateAppStatus: " + e);
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
