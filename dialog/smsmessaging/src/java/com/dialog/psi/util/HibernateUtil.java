package com.dialog.psi.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * 
 * @author Charith_02380
 *
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure().            
                    buildSessionFactory();
        } catch (Throwable ex) {
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
