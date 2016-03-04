/*
 * ApplicationManager.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.dialog.psi.api.service;

import com.dialog.psi.api.entity.SmsInbound;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dialog.psi.api.entity.Application;
import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.entity.SmppIdeamart;
import com.dialog.psi.api.entity.SmppReceiptSubs;
import com.dialog.psi.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

/**
 * Database Utility
 * <code>class</code> for the SMS messaging API implementation
 * <code>ApplicationManager</code>
 *
 * @version $Id: ApplicationManager.java,v 1.00.000
 */
public class ApplicationManager {

    private static final Logger LOG = Logger.getLogger(SmsService.class.getName());
    
    public ApplicationManager() {
    }

    /**
     *
     * @param appID
     * @return
     */
    public Application getApplication(String regid) {
        Session sess = null;
        Transaction tx = null;
        Application app = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            app = (Application) sess.createQuery("from Application where regId = ?").setString(0, regid).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            LOG.error("[ApplicationManager], Error + " + "getApplication " + e.getMessage());
        } finally {
            tx.commit();
            sess.close();
        }

        return app;
    }
    
   public Application getApplicationbyapp(String appid) {
        Session sess = null;
        Transaction tx = null;
        Application app = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            app = (Application) sess.createQuery("from Application where appId = ?").setString(0, appid).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            LOG.error("[ApplicationManager], Error + " + "getApplication " + e.getMessage());
        } finally {
            tx.commit();
            sess.close();
        }

        return app;
    }

    public SmppReceiptSubs getSMSReceiptsub(String destAddr,String keyword) {
        Session sess = null;
        Transaction tx = null;
        SmppReceiptSubs receiptsub = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            receiptsub = (SmppReceiptSubs) sess.createQuery("from SmppReceiptSubs where destaddress = ? and criteria = ?").setString(0, destAddr).setString(1, keyword).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getSMSReceiptsub " + e.getMessage());
            //e.printStackTrace();
            
        } finally {
            tx.commit();
            sess.close();
        }

        return receiptsub;
    }

    public SmppReceiptSubs getSMSReceiptsub(String destAddr) {
        Session sess = null;
        Transaction tx = null;
        SmppReceiptSubs receiptsub = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            receiptsub = (SmppReceiptSubs) sess.createQuery("from SmppReceiptSubs where destaddress = ?").setString(0, destAddr).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getSMSReceiptsub " + e.getMessage());
            //e.printStackTrace();
            
        } finally {
            tx.commit();
            sess.close();
        }

        return receiptsub;
    }
    
    /**
     *
     * @param appID
     * @return
     */
    public SmppIdeamart getApplicationByKeywordAndSender(String appname, String sender) {
        Session sess = null;
        Transaction tx = null;
        SmppIdeamart app = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            app = (SmppIdeamart) sess.createQuery("from SmppIdeamart where appname = ? and sender = ?").setString(0, appname).setString(1, sender).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            LOG.error("[ApplicationManager], Error + " + "getApplicationByKeywordAndSender " + e.getMessage());
        } finally {
            tx.commit();
            sess.close();
        }

        return app;
    }

    /**
     *
     * @param keyword
     * @param sender
     * @return
     */
    public SmppIdeamart getApplicationByKeywordAndOOSender(String appname, String sender) {
        Session sess = null;
        Transaction tx = null;
        SmppIdeamart app = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            app = (SmppIdeamart) sess.createQuery("from SmppIdeamart where appname = ? and ooSender = ?").setString(0, appname).setString(1, sender).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getApplicationByKeywordAndOOSender " + e.getMessage());
            //e.printStackTrace();
        } finally {
            tx.commit();
            sess.close();
        }

        return app;
    }

    public List<SmsInbound> getMessageInbound(String regid, Integer maxresult) {
        Session sess = null;
        SmsInbound smsinbound = null;
        List<SmsInbound> listmsg = null;

        try {
            sess = HibernateUtil.getSession();
            listmsg = sess.createQuery("from SmsInbound where appid = ? ").setString(0, regid).list();
        } catch (Exception e) {
            LOG.error("[ApplicationManager], getMessageInbound + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }

        return listmsg;
    }

    public boolean saveSMSInbound(Object DeliveryTime, Object MsgEncoding, int Errorcode,
            String MessageId, int MessageStatus, String destAddress, String sourceAddress,
            String smscRef, String message, Integer regid) {

        SmsInbound smsin = new SmsInbound();
        if(DeliveryTime==null) {
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
            LOG.error("[ApplicationManager], saveSMSInbound + " + "Error " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            tx.commit();
            sess.close();
        }
        return true;
    }

    public boolean unsubsNotify(String subStr) {
        Session sess = null;
        Transaction tx = null;
        SmppReceiptSubs app = null;
        try {

            if ((subStr == null) || (subStr.isEmpty())) {
                return false;
            }

            Integer subid = Integer.parseInt(subStr.replaceFirst("sub", ""));

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            // Query q = (SmppReceiptSubs) sess.createQuery("delete from SmppReceiptSubs where id = ?").setString(0, appID).uniqueResult();
            Query q = sess.createQuery("delete from SmppReceiptSubs where id = :subId");
            q.setInteger("subId", subid);
            q.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            //e.printStackTrace();
            LOG.error("[ApplicationManager], unsubsNotify + " + "Error " + e.getMessage());
            return false;
        } finally {
            sess.close();
        }

        return true;
    }

    public boolean RemveInbound(List<SmsInbound> smsin) {
        Session sess = null;
        Transaction tx = null;
        SmppReceiptSubs app = null;
        try {

            if (smsin == null) {
                return false;
            }

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();

            for (SmsInbound sm : smsin) {

                Query q = sess.createQuery("delete from SmsInbound where id = :msgId");
                q.setInteger("msgId", sm.getId());
                q.executeUpdate();
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], RemveInbound + " + "Error " + e.getMessage());
            //e.printStackTrace();
            return false;
        } finally {
            sess.close();
        }

        return true;
    }

    public String saveSubscription(String criteria, String callbackData, String notifyurl, String destAddr,
            String notifyfmt, String clientcorel, Application regid) {

        Session sess = null;
        Transaction tx = null;

        SmppReceiptSubs sub = new SmppReceiptSubs();
        sub.setCriteria((criteria == null) ? "" : criteria);
        sub.setCallbackdata(callbackData);
        sub.setNotifyurl(notifyurl);
        sub.setDestaddress(destAddr);
        sub.setNotificationformat(notifyfmt);
        sub.setClientcorrelator(clientcorel);

        try {

            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();

            sub.setRegId(regid);
            sub.setCreated("smsapi");
            sub.setCreatedDate(new Date());
            sub.setLastupdated("smsapi");
            sub.setLastupdatedDate(new Date());
            sess.save(sub);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], saveSubscription + " + "Error " + e.getMessage());
            //e.printStackTrace();
            return null;
        } finally {
            sess.close();
        }
        return String.valueOf(sub.getId());
    }
//-----------------------------------PRIYANKA_06608-------------------------------------------------
    
	@SuppressWarnings("unused")
	public String insertSmppSubscription(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator,String notifyUrl) {
        Session sess = null;
        Transaction tx = null;
        SMSSubscription smsSubscription=new SMSSubscription();
        try {
        	sess = HibernateUtil.getSession();
    		tx = sess.beginTransaction();
    			smsSubscription.setNotifyUrl(notifyUrl);
        		smsSubscription.setCallbackData(callbackData);
	            smsSubscription.setClientCorrelator(clientCorrelator);
	            smsSubscription.setDeliveryStatus(deliveryStatus);
	            smsSubscription.setResAddress(resAddress);
	            smsSubscription.setSenderAddress(senderAddress);
	            smsSubscription.setSmscRefNumber(smscRefNumber);
	            smsSubscription.setCreated("smsapi");
	            smsSubscription.setCreatedDate(new Date());
	            smsSubscription.setLastupdated("smsapi");
	            smsSubscription.setLastupdatedDate(new Date());
	            sess.save(smsSubscription);
	            tx.commit();
            
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getApplication " + e.getMessage());
            return null;
        } finally {
            sess.close();
        }
	        if (smsSubscription.getId()!=null) {
	        	return String.valueOf(smsSubscription.getId());
			}else{
				return null;
			}
        
	}
	
	public String updateSmppSubscription(SMSSubscription smsSubscription) {

        Session sess = null;
        Transaction tx = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            	sess.update(smsSubscription);
	            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getApplication " + e.getMessage());
            return null;
        } finally {
            sess.close();
        }
        return String.valueOf(smsSubscription.getId());
	}
	
	public String insertSmppSubscriptionIfExists(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator) {
        Session sess = null;
        Transaction tx = null;
        SMSSubscription smsSubscription=new SMSSubscription();
        try {
        	sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            	smsSubscription.setCallbackData(callbackData);
	            smsSubscription.setClientCorrelator(clientCorrelator);
	            smsSubscription.setDeliveryStatus(deliveryStatus);
	            smsSubscription.setResAddress(resAddress);
	            smsSubscription.setSenderAddress(senderAddress);
	            smsSubscription.setSmscRefNumber(smscRefNumber);
	            smsSubscription.setLastupdatedDate(new Date());
	            smsSubscription.setCreated("smsapi");
	            smsSubscription.setCreatedDate(new Date());
	            smsSubscription.setLastupdated("smsapi");
	            smsSubscription.setLastupdatedDate(new Date());
	            sess.save(smsSubscription);
	            tx.commit();
            
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[ApplicationManager], Error + " + "getApplication " + e.getMessage());
            return null;
        } finally {
            sess.close();
        }
        return String.valueOf(smsSubscription.getId());
	}
	
    public SMSSubscription selectFromSmsId(Integer smscRefNumber,String senderAddress) {
        Session sess = null;
        SMSSubscription smsSubscription = null;
        try {
            sess = HibernateUtil.getSession();
            smsSubscription = (SMSSubscription)sess.createQuery("from SMSSubscription s where s.smscRefNumber = ? and s.senderAddress = ? ").setInteger(0, smscRefNumber).setString(1, senderAddress).uniqueResult();
        } catch (Exception e) {
            LOG.error("[SMSSubscription] + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        return smsSubscription;
    }
    
	@SuppressWarnings("unchecked")
	public boolean seqExistsForMsisdn(Integer seqNo) {
        Session sess = null;
        List<Integer> idList = null;
        try {
            sess = HibernateUtil.getSession();
            idList = sess.createQuery("select s.id from SMSSubscription s where s.smscRefNumber = ? and s.deliveryStatus='DeliveredToNetwork' ").setInteger(0, seqNo).list();
        } catch (Exception e) {
            LOG.error("[SMSSubscription] + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        return !(idList.isEmpty()||idList==null);
    }
    
	@SuppressWarnings("unchecked")
	public boolean idExistsForMsisdn(String Msisdn) {
        Session sess = null;
        List<Integer> idList = null;
        try {
            sess = HibernateUtil.getSession();
            idList = sess.createQuery("select s.id from SMSSubscription s where s.senderAddress = ? ").setString(0, Msisdn).list();
        } catch (Exception e) {
            LOG.error("[SMSSubscription] + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        return !(idList.isEmpty()||idList==null);
    }

	public String updateDeliverInfo(Integer messageId) {
        Session sess = null;
        SMSSubscription smsSubscription= new SMSSubscription();
        try {
            sess = HibernateUtil.getSession();
            smsSubscription = (SMSSubscription)sess.createQuery("from SMSSubscription s where s.smscRefNumber = ? ").setInteger(0, messageId).uniqueResult();
            if (smsSubscription!=null) {
            	smsSubscription.setDeliveryStatus("DeliveredToNetwork");
                sess.update(smsSubscription);
			}
            
        } catch (Exception e) {
            LOG.error("[SMSSubscription] + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        if (smsSubscription!=null) {
        	return String.valueOf(smsSubscription.getId());
		}else{
			return null;
		}
    }

	@SuppressWarnings("unchecked")
	public List<SMSSubscription> messageSubscribedAndDelivered(String senderAddress,Integer requestId) {
		Session sess = null;
        List<SMSSubscription> smsSubscriptionList= new ArrayList<SMSSubscription>();
        try {
            sess = HibernateUtil.getSession();
            smsSubscriptionList = sess.createQuery("from SMSSubscription s where s.smscRefNumber = ? and s.senderAddress = ? ").setInteger(0, requestId).setString(1, senderAddress).list();
            
        } catch (Exception e) {
            LOG.error("[smsSubscriptionList] + " + "Error " + e.getMessage());
            e.printStackTrace();
        } finally {
            sess.close();
        }
        if (smsSubscriptionList!=null) {
        	return smsSubscriptionList;
		}else{
			return null;
		}
	}

	public SMSSubscription getSubscription(int sequenceNum) {
        Session sess = null;
        Transaction tx = null;
        SMSSubscription subscription = null;
        try {
            sess = HibernateUtil.getSession();
            tx = sess.beginTransaction();
            subscription = (SMSSubscription) sess.createQuery("from SMSSubscription s where s.smscRefNumber = ?").setInteger(0, sequenceNum).uniqueResult();
        } catch (Exception e) {
            tx.rollback();
            LOG.error("[SMSSubscription], Error + " + "getsubscription " + e.getMessage());
        } finally {
            tx.commit();
            sess.close();
        }

        return subscription;
    }
	

}
