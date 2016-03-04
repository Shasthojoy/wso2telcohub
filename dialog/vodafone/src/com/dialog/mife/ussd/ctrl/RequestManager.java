package com.dialog.mife.ussd.ctrl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.dialog.mife.ussd.api.UssdAPI;
import com.dialog.mife.ussd.dto.Application;
import com.dialog.mife.ussd.dto.InboundUSSDMessageRequest;
import com.dialog.mife.ussd.dto.NIMsisdn;
import com.dialog.mife.ussd.dto.OutboundRequest;
import com.dialog.mife.ussd.dto.OutboundUSSDMessageRequest;
import com.dialog.mife.ussd.dto.USSDAction;
import com.dialog.mife.ussd.util.HibernateUtil;

public class RequestManager {
	
	final static Logger log = Logger.getLogger(RequestManager.class);

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
	public String provisionNotifyUrl(Long appId) {
        Session sess = null;
        Transaction tx = null; 
        String response = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            response = (String)sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.id=?").setLong(0, appId).uniqueResult();
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
        //NotifyURL Retrive by Shortcode
     public String getNotifyUrlByShortCode(String shortcode) {
        Session sess = null;
        Transaction tx = null; 
        String response = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            response = (String)sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.shortcode=?").setString(0, shortcode).uniqueResult();
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
     
     //NotifyURL Retrive by Keyword
     public String getNotifyUrlByKeyword(String keyWord) {
        Session sess = null;
        Transaction tx = null; 
        String response = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();            
            response = (String)sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.keyword=?").setString(0, keyWord).uniqueResult();
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
     
     /**
      * 
      * @param msisdn
      * @return
      * @throws Exception
      */
     public UrlProvisionEntity getNotifyURLByLastRequest(String msisdn) throws Exception {
         Session sess = HibernateUtil.getSession();
         Transaction tx = sess.beginTransaction();
         UrlProvisionEntity urlProvisionEntity = null;
         
         log.info("msisdn : "+msisdn);
         
         try {
        	 DetachedCriteria maxId = DetachedCriteria.forClass(OutboundUSSDMessageRequest.class).
        			 add(Restrictions.eq("address", msisdn)).
        			 setProjection(Projections.max("id"));
        	 log.info("maxId : "+maxId);
        	 OutboundRequest outboundRequest = (OutboundRequest)sess.createCriteria(OutboundRequest.class).
        			 createAlias("outboundUSSDMessageRequest", "o").
        			 add(Property.forName("o.id").eq(maxId)).
        			 uniqueResult();
        	 log.info("outboundRequest-getAddress : "+outboundRequest.getOutboundUSSDMessageRequest().getAddress());
        	 urlProvisionEntity = (UrlProvisionEntity)sess.createQuery("from UrlProvisionEntity where application.id=?").
             		setLong(0, outboundRequest.getApplication().getId()).
             		uniqueResult();
        	 
        	 log.info("urlProvisionEntity-getKeyWord : "+urlProvisionEntity.getKeyWord());
             return urlProvisionEntity;
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             tx.commit();
             sess.close();
         }
         return urlProvisionEntity;
     }
     
     
     public InboundUSSDMessageRequest getResponseBodyByLastRequest(String msisdn) {
    	 Session sess = HibernateUtil.getSession();
    	 Transaction tx = sess.beginTransaction();
    	 InboundUSSDMessageRequest inboundUSSDMessageRequest=null;
    	 try {
    	 DetachedCriteria maxId = DetachedCriteria.forClass(OutboundUSSDMessageRequest.class).
    	 add(Restrictions.eq("address", msisdn)).
    	 setProjection(Projections.max("id"));

    	 OutboundRequest outboundRequest = (OutboundRequest)sess.createCriteria(OutboundRequest.class).
    	 createAlias("outboundUSSDMessageRequest", "o").
    	 add(Property.forName("o.id").eq(maxId)).
    	 uniqueResult();
    	 inboundUSSDMessageRequest=new InboundUSSDMessageRequest();

    	 inboundUSSDMessageRequest.setAddress(outboundRequest.getOutboundUSSDMessageRequest().getAddress());
    	 inboundUSSDMessageRequest.setClientCorrelator(outboundRequest.getOutboundUSSDMessageRequest().getClientCorrelator());
    	 //inboundUSSDMessageRequest.setInboundUSSDMessage(inboundUSSDMessage);
    	 inboundUSSDMessageRequest.setKeyword(outboundRequest.getOutboundUSSDMessageRequest().getKeyword());
    	 inboundUSSDMessageRequest.setResponseRequest(outboundRequest.getOutboundUSSDMessageRequest().getResponseRequest());
    	 inboundUSSDMessageRequest.setSessionID(outboundRequest.getOutboundUSSDMessageRequest().getSessionID());
    	 inboundUSSDMessageRequest.setShortCode(outboundRequest.getOutboundUSSDMessageRequest().getShortCode());
    	 inboundUSSDMessageRequest.setUssdAction(USSDAction.mocont);

    	 return inboundUSSDMessageRequest;

    	 } catch (Exception e) {
    	 e.printStackTrace();
    	 } finally {
    	 tx.commit();
    	 sess.close();
    	 }
    	 return inboundUSSDMessageRequest;
    	 }
     
}
