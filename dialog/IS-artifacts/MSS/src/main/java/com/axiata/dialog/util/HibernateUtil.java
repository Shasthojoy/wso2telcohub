package com.axiata.dialog.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created by nilan on 11/25/14.
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private final static Logger log = Logger.getLogger(Config.class);

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();

        }
        catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed:"+ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
