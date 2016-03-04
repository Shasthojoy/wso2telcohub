package com.uninor.ussd.api.service;

import com.uninor.ussd.api.entity.SmsInbound;
import com.uninor.ussd.api.responsebean.ussd.Application;
import com.uninor.ussd.api.responsebean.ussd.ApplicationStatus;
import com.uninor.ussd.api.responsebean.ussd.InboundUSSDMessageRequest;
import com.uninor.ussd.api.responsebean.ussd.OutboundRequest;
import com.uninor.ussd.api.responsebean.ussd.OutboundUSSDMessageRequest;
import com.uninor.ussd.api.responsebean.ussd.ServiceProvider;
import com.uninor.ussd.api.responsebean.ussd.USSDAction;
import com.uninor.ussd.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import org.hibernate.Query;

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
            application = (Application) sess.createQuery("from Application where shortCode=? and keyword=?").
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
            application = (Application) sess.createQuery("from Application where shortCode=? and unique=?").
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
            id = (Long) sess.save(request);
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
            request = (OutboundRequest) sess.createQuery("from OutboundRequest where id=?").
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
            response = (String) sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.id=?").setLong(0, appId).uniqueResult();
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
            response = (String) sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.shortcode=?").setString(0, shortcode).uniqueResult();
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
            response = (String) sess.createQuery("select u.notifyUrl from UrlProvisionEntity u where u.keyword=?").setString(0, keyWord).uniqueResult();
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

    public boolean saveSMSInbound(Object DeliveryTime, Object MsgEncoding, int Errorcode,
            String MessageId, int MessageStatus, String destAddress, String sourceAddress,
            String smscRef, String message, Integer regid) {

        SmsInbound smsin = new SmsInbound();
        if (DeliveryTime == null) {
            DeliveryTime = String.valueOf(System.currentTimeMillis());
        }
        smsin.setDeliverytime(DeliveryTime.toString());
        smsin.setMsgencoding(MsgEncoding.toString());
        smsin.setErrocode(Errorcode);
        smsin.setMessageid((String) MessageId);
        smsin.setMsgstatus(MessageStatus);
        smsin.setDestaddr(destAddress);
        smsin.setSenderaddr(sourceAddress);
        smsin.setMsgref(smscRef);
        smsin.setMessage(message);
        smsin.setCreated("smsapi");
        smsin.setCreatedDate(new Date());
        smsin.setLastupdated("smsapi");
        smsin.setLastupdatedDate(new Date());
        smsin.setAppid(regid);

        Session sess = null;
        Transaction tx = null;

        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            sess.save(smsin);
        } catch (Exception e) {
            tx.rollback();
            log.error("[ApplicationManager], saveSMSInbound + " + "Error " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            tx.commit();
            sess.close();
        }
        return true;
    }

    public InboundUSSDMessageRequest getResponseBodyByLastRequest(String msisdn) {
        Session sess = HibernateUtil.getSession();
        Transaction tx = sess.beginTransaction();
        InboundUSSDMessageRequest inboundUSSDMessageRequest = null;
        try {
            DetachedCriteria maxId = DetachedCriteria.forClass(OutboundUSSDMessageRequest.class).
                    add(Restrictions.eq("address", msisdn)).
                    setProjection(Projections.max("id"));

            OutboundRequest outboundRequest = (OutboundRequest) sess.createCriteria(OutboundRequest.class).
                    createAlias("outboundUSSDMessageRequest", "o").
                    add(Property.forName("o.id").eq(maxId)).
                    uniqueResult();

            if (outboundRequest != null) {
                inboundUSSDMessageRequest = new InboundUSSDMessageRequest();

                inboundUSSDMessageRequest.setAddress(outboundRequest.getOutboundUSSDMessageRequest().getAddress());
                inboundUSSDMessageRequest.setClientCorrelator(outboundRequest.getOutboundUSSDMessageRequest().getClientCorrelator());
                //inboundUSSDMessageRequest.setInboundUSSDMessage(inboundUSSDMessage);
                inboundUSSDMessageRequest.setKeyword(outboundRequest.getOutboundUSSDMessageRequest().getKeyword());
                inboundUSSDMessageRequest.setResponseRequest(outboundRequest.getOutboundUSSDMessageRequest().getResponseRequest());
                inboundUSSDMessageRequest.setSessionID(outboundRequest.getOutboundUSSDMessageRequest().getSessionID());
                inboundUSSDMessageRequest.setShortCode(outboundRequest.getOutboundUSSDMessageRequest().getShortCode());
                inboundUSSDMessageRequest.setUssdAction(USSDAction.mocont);
            }

            return inboundUSSDMessageRequest;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }
        return inboundUSSDMessageRequest;
    }

    public String saveSubscription(String criteria, String callbackData, String notifyurl, String destAddr,
            String notifyfmt, String clientcorel) {

        Session sess = null;
        Transaction tx = null;

        long subId = 0;

        ServiceProvider sp = getServiceProvider();        
        
        try {

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            
            Application sub = new Application();
            sub.setKeyword((criteria == null) ? "" : criteria);
            sub.setCallbackdata(callbackData);
            sub.setNotifyurl(notifyurl);
            sub.setApplicationStatus(ApplicationStatus.ACTIVE);
            sub.setShortCode(destAddr);
            sub.setClientcorrelator(clientcorel);
            sub.setServiceProvider(sp);

            sess.save(sub);
            subId = sub.getId();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            log.error("[ApplicationManager], saveSubscription + " + "Error " + e.getMessage());
            //e.printStackTrace();
            return null;
        } finally {
            sess.close();
        }
        return String.valueOf(subId);
    }

    public boolean unsubsNotify(String subStr) {
        Session sess = null;
        Transaction tx = null;

        try {

            if ((subStr == null) || (subStr.isEmpty())) {
                return false;
            }

            Integer subid = Integer.parseInt(subStr.replaceFirst("sub", ""));

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            // Query q = (SmppReceiptSubs) sess.createQuery("delete from SmppReceiptSubs where id = ?").setString(0, appID).uniqueResult();
            Query q = sess.createQuery("delete from Application where id = :subId");
            q.setInteger("subId", subid);
            q.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            log.error("[ApplicationManager], unsubsNotify + " + "Error " + e.getMessage());
            return false;
        } finally {
            sess.close();
        }

        return true;
    }
    
    public ServiceProvider getServiceProvider() {
        Session sess = null;
        Transaction tx = null;
        ServiceProvider sp = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            sp = (ServiceProvider) sess.createQuery("from ServiceProvider").uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            log.error("[ApplicationManager], Error + " + "getServiceProvider " + e.getMessage());
        } finally {
            tx.commit();
            sess.close();
        }

        return sp;
    }
}
