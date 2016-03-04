package com.dialog.mife.ussd.ctrl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dialog.mife.ussd.dto.Application;
import com.dialog.mife.ussd.dto.NIMsisdn;
import com.dialog.mife.ussd.dto.OutboundRequest;
import com.dialog.mife.ussd.util.HibernateUtil;

public class RequestManager {

	public RequestManager() {
	}
	
	/**
	 * 
	 * @param shortCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public Application getApplication(String shortCode, String keyword) throws Exception {
        Session sess = HibernateUtil.getSession();
        Transaction tx = sess.beginTransaction();
        Application application = null;
        try {            
            application = (Application)sess.createQuery("from Application where shortCode=? and keyword=?").
            		setString(0, shortCode).
            		setString(1, keyword).
            		uniqueResult();
            return application;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return application;
    }
	
	/**
	 * 
	 * @param shortCode
	 * @return
	 * @throws Exception
	 */
	public Application getUniqueApplication(String shortCode) throws Exception {
        Session sess = HibernateUtil.getSession();
        Transaction tx = sess.beginTransaction();
        Application application = null;
        try {            
            application = (Application)sess.createQuery("from Application where shortCode=? and unique=?").
            		setString(0, shortCode).
            		setBoolean(1, Boolean.TRUE).
            		uniqueResult();
            return application;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return application;
    }
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public Long saveRequest(OutboundRequest request) {
        Session sess = null;
        Transaction tx = null; 
        Long id = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            id = (Long)sess.save(request);
            return id;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    }
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public boolean updateRequest(OutboundRequest request) {
        Session sess = null;
        Transaction tx = null; 
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            sess.update(request);
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return false;
    }
	
        
        
    public Long saveMSISDN(NIMsisdn msisdn) {
        Session sess = null;
        Transaction tx = null; 
        Long id = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            id = (Long)sess.save(msisdn);
            return id;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    }   
        
    
    public NIMsisdn getMSISDN(String msisdn) throws Exception {
        Session sess = HibernateUtil.getSession();
        Transaction tx = sess.beginTransaction();
        NIMsisdn msisdnObj = null;
        try {            
            msisdnObj = (NIMsisdn)sess.createQuery("from NIMsisdn where msisdn=?").
            		setString(0, msisdn).
            	
            		uniqueResult();
            return msisdnObj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return msisdnObj;
    }
        
    public NIMsisdn deleteMSISDN(NIMsisdn msisdn) throws Exception {
        Session sess = null;
        Transaction tx = null; 
        Long id = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            sess.delete(msisdn);
            
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    }
    
	/**
	 * 
	 * @param id
	 * @return
	 */
	public OutboundRequest getRequest(Long id) {
        Session sess = null;
        Transaction tx = null; 
        OutboundRequest request = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            request = (OutboundRequest)sess.createQuery("from OutboundRequest where id=?").
            		setLong(0, id).
            		uniqueResult();
            return request;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    }

	public Application getNotifyUrlByApplication(String appCode) {
        Session sess = null;
        Transaction tx = null; 
        Application response = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            response = (Application)sess.createQuery("select a from Application a where a.keyword=?").setString(0, appCode).uniqueResult();
            return response;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    }

	public Long	provisionUrl(UrlProvisionEntity entity) {
        Session sess = null;
        Transaction tx = null; 
        Long id = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            id = (Long)sess.save(entity);
            return id;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return null;
    
	}
	
}
