/*
 * SmsService.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.dialog.psi.api.service;

//import com.axiata.dialog.entity.CurrentLocation;
//import com.axiata.dialog.entity.LocationRequest;
//import com.axiata.dialog.entity.TerminalLocation;
//import com.axiata.dialog.entity.TerminalLocationList;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.dialog.psi.api.SMSHandler;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessage;
import com.dialog.psi.api.responsebean.sms.ISMSresponse;
import com.dialog.psi.api.responsebean.sms.InboundSMSResponse;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessageList;
import com.dialog.psi.api.responsebean.sms.MultiOperatorBean;
import com.dialog.psi.api.entity.Application;
import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.entity.SmppIdeamart;
import com.dialog.psi.api.entity.SmppReceiptSubs;
import com.dialog.psi.api.entity.SmsInbound;
import com.dialog.psi.api.exception.RequestError;
import com.dialog.psi.api.exception.ServiceException;
import com.dialog.psi.api.responsebean.sms.SMSRequest;
import com.dialog.psi.api.responsebean.sms.SubscribeRequest;
import com.dialog.psi.util.FileUtil;
import com.dialog.psi.util.NumberGenerator;
import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;

import ie.omk.smpp.Address;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.tlv.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import lk.dialog.ideabiz.logger.model.impl.SMSOutbound;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * Intermediate Service Class to handle service implementations for API
 * requests.
 *
 * <code>SmsService</code>
 *
 * @version $Id: SmsService.java,v 1.00.000
 */
public class SmsService {

    //private static final Logger LOG = Logger.getLogger(SmsService.class.getName());
    private ApplicationManager am;

    public SmsService() {
        this.am = new ApplicationManager();
    }

    public ISMSresponse retriveSMS(String registrationId, String maxBatchSize) throws Exception {
        //TO-DO - Validations
        
        Application app = null;
        
            app =  am.getApplication(registrationId);
           if (app == null) {
               throw new AxiataException("SVC0004","Registration Not Found", new String[]{registrationId});               
           }
        
                
        Integer maxresult = Integer.valueOf(maxBatchSize);
        List<SmsInbound> smsin = am.getMessageInbound(String.valueOf(app.getId()), maxresult);
        List<InboundSMSMessage> messages = new ArrayList();

        int i = 0;
        for (SmsInbound sm : smsin) {
            InboundSMSMessage tmsg = new InboundSMSMessage();
            tmsg.setDateTime(sm.getDeliverytime());
            tmsg.setDestinationAddress(sm.getDestaddr());
            tmsg.setMessageId(sm.getMessageid());
            tmsg.setMessage(sm.getMessage());
            tmsg.setResourceURL("Not Available");
            tmsg.setSenderAddress(sm.getSenderaddr());
            messages.add(tmsg);
            i++;
            if (i >= maxresult) {
                break;
            }
        }

        InboundSMSMessageList msglist = new InboundSMSMessageList();
        msglist.setInboundSMSMessage(messages);
        msglist.setNumberOfMessagesInThisBatch(String.valueOf(messages.size()));
        msglist.setTotalNumberOfPendingMessages(String.valueOf(smsin.size()));
        msglist.setResourceURL("Not Available");

        InboundSMSResponse msgresponse = new InboundSMSResponse();
        msgresponse.setInboundSMSMessageList(msglist);
        
        //if all okey remove messages from inbound queue
        
        am.RemveInbound(smsin);
                
        return msgresponse; //sb.getMessageInbound(msgid, smsref);
    }

    public ISMSresponse subscribeToNotify(SubscribeRequest req, String registrationId) throws Exception {
        //TO-DO - Validations

        SubscribeRequest msgresponse = req;
        boolean errorentry = false;
        String subsid = null;

        
        Application app = am.getApplication(registrationId);
        if (app == null) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("POL1009", "User has not been provisioned for %1", "Subscribe to notifications of messages sent to your application"));
            return requesterror;            
        }

        //unique subscription 2014/04/23
        if (app.getUniport().equalsIgnoreCase("Y")) {
            if ((req.getSubscription().getCriteria() != null) && (req.getSubscription().getCriteria().length() > 0)) {
                errorentry = true;
            }
            SmppReceiptSubs sub = am.getSMSReceiptsub(registrationId);
            if (sub != null) {
                errorentry = true;
            }
        }

        if (!errorentry) {
            subsid = am.saveSubscription(req.getSubscription().getCriteria(), req.getSubscription().getCallbackReference().getCallbackData(),
                    req.getSubscription().getCallbackReference().getNotifyURL(), req.getSubscription().getDestinationAddress().replace("tel:+", "").trim(),
                    req.getSubscription().getNotificationFormat(), req.getSubscription().getClientCorrelator(), app);
            //TO-DO - Subs null error
            if (subsid == null) {
                errorentry = true;
            }
        }

        if (errorentry) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0008", "Overlapped criteria %1", req.getSubscription().getCriteria()));
            return requesterror;
        }
        msgresponse.getSubscription().setResourceURL((FileUtil.getApplicationProperty("subscription.resourceurl")) + "/sub" + subsid);

        return msgresponse; //sb.getMessageInbound(msgid, smsref);
    }

    public ISMSresponse removeSubsNotify(String subid) throws Exception {
        //TO-DO - Validations

        SubscribeRequest msgresponse = new SubscribeRequest();

        if (!am.unsubsNotify(subid)) {
            throw new Exception("Subscription Not Found");
        }
        return msgresponse; //sb.getMessageInbound(msgid, smsref);
    }

    public boolean ideaMartSend(SMSRequest smsReq, int msgNum,Integer AMID) {
        //TO-DO - Validations

        try {
            Application app = am.getApplication(smsReq.getApplicationId());

            List<String> list = smsReq.getDestinationAddresses();
            for (String to : list) {
                to = to.replace("tel:+", "").trim();
                to = to.replace("tel:", "").trim();

                if (to.length() >= 9 && ((to.startsWith("9477") )|| (to.startsWith("9476") ) )) {
                    sendMessage(app.getSender(), to, smsReq.getMessage(), false, msgNum);
                } else {
                    sendMessage(app.getOoSender(), to, smsReq.getMessage(), false, msgNum);
                }
            }
        
            SMSHandler.directLogger.addLog(new SMSOutbound("IM", AMID, list, app.getSender(), app.getOoSender(), app.getSender(), smsReq.getApplicationId(), "", "SENT", smsReq.getMessage()));
        } catch (Exception e) {
            return false;
        }
        return true; //sb.getMessageInbound(msgid, smsref);
    }

    public boolean saveSMSInbound(Object DeliveryTime, Object MsgEncoding, int Errorcode,
            String MessageId, int MessageStatus, String destAddress, String sourceAddress,
            String smscRef, String message, Integer subid, Integer subscriptionId) {

        return am.saveSMSInbound(DeliveryTime, MsgEncoding, Errorcode, MessageId, MessageStatus, destAddress, sourceAddress, smscRef, message, subid,subscriptionId);
    }

    public Application getApplication(String appID) {
        return am.getApplication(appID);
    }

    public Application getApplicationbyapp(String appID) {
        return am.getApplicationbyapp(appID);
    }
    public SmppReceiptSubs getSMSReceiptsub(String destAddr,String keyword) {
        return am.getSMSReceiptsub(destAddr,keyword);
    }

    public SmppReceiptSubs getSMSReceiptsub(String destAddr) {
        return am.getSMSReceiptsub(destAddr);
    }
    
    public SmppIdeamart getApplicationByKeywordAndSender(String keyword, String sender) {
        return am.getApplicationByKeywordAndSender(keyword, sender);
    }

    public SmppIdeamart getApplicationByKeywordAndOOSender(String keyword, String sender) {
        return am.getApplicationByKeywordAndOOSender(keyword, sender);
    }

    public void sendMessage(String port, String phoneNo, String msg, boolean highPriority, int msgNum) {
        ConnectionPool pool = SMSCPoolManager.getInstance().getConnectionPool(port.toUpperCase());
        if (msg.length() > 160) {
            ArrayList<String> msgs = splitMessage(msg, 150);
            for (int i = 0; i < msgs.size(); i++) {
                SubmitSM sm = new SubmitSM();
                sm.setSource(new Address(pool.getSrcTon(), pool.getSrcNpi(), port));
                sm.setDestination(new Address(pool.getTon(), pool.getNpi(), phoneNo));
                sm.setRegistered((byte) 0);
                sm.setMessageText(msgs.get(i));

                sm.setOptionalParameter(Tag.SAR_TOTAL_SEGMENTS, new Integer(msgs.size()));
                sm.setOptionalParameter(Tag.SAR_SEGMENT_SEQNUM, new Integer(i + 1));
                sm.setOptionalParameter(Tag.SAR_MSG_REF_NUM, new Integer(msgNum));

                pool.addMessage(sm, highPriority);
            }

        } else {
            SubmitSM sm = new SubmitSM();
            sm.setSequenceNum(msgNum);
            sm.setDestination(new Address(pool.getTon(), pool.getNpi(), phoneNo));
            sm.setSource(new Address(pool.getSrcTon(), pool.getSrcNpi(), port));
            sm.setRegistered((byte) 0);
            sm.setMessageText(msg);
            pool.addMessage(sm, highPriority);
        }
    }

    /**
     *
     * @param msg
     * @param length
     * @return
     */
    private ArrayList<String> splitMessage(String msg, int length) {
        ArrayList<String> msgs = new ArrayList<String>(0);
        int start = 0;
        while (msg.length() > 0) {
            if (msg.length() > length) {
                msgs.add(msg.substring(start, length));
                msg = msg.substring(length);
            } else {
                msgs.add(msg.substring(start));
                msg = "";
            }
        }
        return msgs;
    }
    
	public String insertSmppSubscriptionMultipleOperators(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator, String notifyUrl, String filterCriteria, String operatorCode) {
		String result="";
		if(!am.idExistsForMsisdnInSub(senderAddress)){
			result=am.insertSmppSubscriptionMultipleOperators( smscRefNumber, deliveryStatus,  resAddress,  callbackData, senderAddress,  clientCorrelator,notifyUrl,filterCriteria,operatorCode);
		}
		return result;
	}
	
	public String insertSmppSubscription(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator, String notifyUrl) {
		String result="";
		if(!am.idExistsForMsisdnInSub(senderAddress)){
			result=am.insertSmppSubscription( smscRefNumber, deliveryStatus,  resAddress,  callbackData, senderAddress,  clientCorrelator,notifyUrl);
		}
		return result;
	}  
	
	public String updateSmppSubscription(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator) {
		String result="";
		if(am.idExistsForMsisdn(senderAddress)){
			SMSSubscription smsSubscription=null;
			smsSubscription=am.selectFromSmsId(smscRefNumber,senderAddress);
			smsSubscription.setCallbackData(callbackData);
            smsSubscription.setClientCorrelator(clientCorrelator);
            smsSubscription.setDeliveryStatus(deliveryStatus);
            smsSubscription.setResAddress(resAddress);
            smsSubscription.setSmscRefNumber(smscRefNumber);
            smsSubscription.setLastupdatedDate(new Date());
			result=am.updateSmppSubscription(smsSubscription);
		}		
		return result;
	}
	
	public String insertSmppSubscriptionIfExists(Integer smscRefNumber,String deliveryStatus, String resAddress, String callbackData,String senderAddress, String clientCorrelator,String notifyUrl,String msgId) {
		String result="";
		SystemLog.getInstance().getLogger("smscomp_event").info("################SMSSERVICE################   insertSmppSubscriptionIfExists Method Triggered");
			MultiOperatorBean bean=am.getNotifyUrlBysenderMsisdn(senderAddress);
			SystemLog.getInstance().getLogger("smscomp_event").info("################SMSSERVICE################   getFilterCriteria : "+bean.getFilterCriteria());
			SystemLog.getInstance().getLogger("smscomp_event").info("################SMSSERVICE################   getNotifyUrl : "+bean.getNotifyUrl());
			SystemLog.getInstance().getLogger("smscomp_event").info("################SMSSERVICE################   getOperatorCode : "+bean.getOperatorCode());
			result=am.insertSmppSubscriptionIfExists( smscRefNumber, deliveryStatus,  resAddress,  callbackData, senderAddress,  clientCorrelator,bean.getNotifyUrl(),msgId,bean.getFilterCriteria(),bean.getOperatorCode());
			SystemLog.getInstance().getLogger("smscomp_event").info("################SMSSERVICE################   idExistsForMsisdn Returns True & AM Called");
		//}
		return result;
	}

	public String updateDeliverInfo(Integer messageId) {
		String result=am.updateDeliverInfo(messageId);
		return result;
	}

	public boolean seqExistsForMsisdn(Integer msisdn) {
		return am.seqExistsForMsisdn(msisdn);
	}


	public List<SMSSubscription> messageSubscribedAndDelivered(String senderAddress,String requestId) {
		return am.messageSubscribedAndDelivered(senderAddress,requestId);
	}

	public SMSSubscription getSubscription(int sequenceNum) {
		return am.getSubscription(sequenceNum);
	}

	public MultiOperatorBean getNotifyUrlBysenderMsisdn(String senderMsisdn) {
		return am.getNotifyUrlBysenderMsisdn(senderMsisdn);
	}

	public ISMSresponse retriveSMS(String registrationId, String maxBatchSize, String criteria) throws Exception {
        
        Application app = null;
        
            app =  am.getApplication(registrationId);
           if (app == null) {
               throw new AxiataException("SVC0004","Registration Not Found", new String[]{registrationId});               
           }
        
                
        Integer maxresult = Integer.valueOf(maxBatchSize);
        List<SmsInbound> smsin = am.getMessageInbound(String.valueOf(app.getId()), maxresult, criteria, registrationId);
        List<InboundSMSMessage> messages = new ArrayList();

        int i = 0;
        for (SmsInbound sm : smsin) {
            InboundSMSMessage tmsg = new InboundSMSMessage();
            tmsg.setDateTime(sm.getDeliverytime());
            tmsg.setDestinationAddress(sm.getDestaddr());
            tmsg.setMessageId(sm.getMessageid());
            tmsg.setMessage(sm.getMessage());
            tmsg.setResourceURL("Not Available");
            tmsg.setSenderAddress(sm.getSenderaddr());
            messages.add(tmsg);
            i++;
            if (i >= maxresult) {
                break;
            }
        }

        InboundSMSMessageList msglist = new InboundSMSMessageList();
        msglist.setInboundSMSMessage(messages);
        msglist.setNumberOfMessagesInThisBatch(String.valueOf(messages.size()));
        msglist.setTotalNumberOfPendingMessages(String.valueOf(smsin.size()));
        msglist.setResourceURL("Not Available");

        InboundSMSResponse msgresponse = new InboundSMSResponse();
        msgresponse.setInboundSMSMessageList(msglist);
        
        am.RemveInbound(smsin);
        return msgresponse; 
    }

  
}
