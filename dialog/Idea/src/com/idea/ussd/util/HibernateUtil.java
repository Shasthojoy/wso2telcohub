package com.idea.ussd.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Charith_02380
 *
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().
                    buildSessionFactory();
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * 
     */
    public HibernateUtil() {
        
    }
    /**
     * 
     * @return
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
