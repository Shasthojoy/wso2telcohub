/*
 * SmsService.java
 * Apr 2, 2013  11:20:38 AM
 **
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.uninor.ussd.api.service;

//import com.axiata.dialog.entity.CurrentLocation;
//import com.axiata.dialog.entity.LocationRequest;
//import com.axiata.dialog.entity.TerminalLocation;
//import com.axiata.dialog.entity.TerminalLocationList;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.uninor.ussd.api.responsebean.sms.InboundSMSMessage;
import com.uninor.ussd.api.responsebean.sms.ISMSresponse;
import com.uninor.ussd.api.responsebean.sms.InboundSMSResponse;
import com.uninor.ussd.api.responsebean.sms.InboundSMSMessageList;
import com.uninor.ussd.api.entity.SmppIdeamart;
import com.uninor.ussd.api.entity.SmsInbound;
import com.uninor.ussd.api.exception.RequestError;
import com.uninor.ussd.api.exception.ServiceException;
import com.uninor.ussd.api.responsebean.sms.SMSRequest;
import com.uninor.ussd.api.responsebean.sms.SubscribeRequest;
import com.uninor.ussd.util.FileUtil;
import com.uninor.ussd.util.NumberGenerator;
import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.google.gson.Gson;
import com.uninor.ussd.api.responsebean.ussd.Application;
import com.uninor.ussd.api.responsebean.ussd.InboundUSSDMessageRequest;

import ie.omk.smpp.Address;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.tlv.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    private RequestManager am;

    public SmsService() {
        this.am = new RequestManager();
    }

    public ISMSresponse subscribeToNotify(SubscribeRequest req, String registrationId) throws Exception {
        //TO-DO - Validations

        SubscribeRequest msgresponse = req;
        boolean errorentry = false;
        String subsid = null;
        
            subsid = am.saveSubscription(req.getSubscription().getKeyword(), req.getSubscription().getCallbackReference().getCallbackData(),
                    req.getSubscription().getCallbackReference().getNotifyURL(), req.getSubscription().getShortCode().replace("tel:", "").replaceAll("\\+", "").trim(),
                    "", req.getSubscription().getClientCorrelator());
            //TO-DO - Subs null error
            if (subsid == null) {
                errorentry = true;
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

    public boolean saveSMSInbound(Object DeliveryTime, Object MsgEncoding, int Errorcode,
            String MessageId, int MessageStatus, String destAddress, String sourceAddress,
            String smscRef, String message, Integer subid) {

        return am.saveSMSInbound(DeliveryTime, MsgEncoding, Errorcode, MessageId, MessageStatus, destAddress, sourceAddress, smscRef, message, subid);
    }

    public Application getUniquesub(String destAddr) throws Exception {
        return am.getUniqueApplication(destAddr);
    }
    
    public Application getReceiptsub(String destAddr,String keyword) throws Exception {
        return am.getApplication(destAddr,keyword);
    }
    
    
    public InboundUSSDMessageRequest getResponseBodyByLastRequest(String msisdn) {
        return am.getResponseBodyByLastRequest(msisdn);
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

}
