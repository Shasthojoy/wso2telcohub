package com.dialog.psi.api;

import ie.omk.smpp.message.DeliverSM;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.dialog.psi.api.entity.Application;
import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.entity.SmppIdeamart;
import com.dialog.psi.api.entity.SmppReceiptSubs;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessage;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessageNotification;
import com.dialog.psi.api.responsebean.sms.MessageNotification;
import com.dialog.psi.api.service.SmsService;
import com.dialog.psi.util.FileUtil;
import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.db.HSQLManager;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;


public class DRProcessor extends Thread {

	private static final Logger LOG = Logger.getLogger(DRProcessor.class.getName());
	
    private int m_sleepTime = 100;
    private HSQLManager mgr = null;
    private SmsService sm;

    public DRProcessor() {
        try {
            mgr = new HSQLManager();
            sm = new SmsService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    	ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(FileUtil.getApplicationProperty("sendPoolSize")));

        while (true) {
            try {
                ArrayList<PacketObject> msgs = mgr.getDeliveredMessages(50);

                if ((msgs == null) || (msgs.isEmpty())) {
                    if (m_sleepTime > 0) {
                        sleep(m_sleepTime);
                    }
                    continue;
                }
                
                Gson gson=new Gson();
                String msgGson=gson.toJson(msgs);
                //SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ msgGson :" + msgGson);

               for (PacketObject pObj : msgs) {
                    if (pObj == null) {
                        continue;
                    }
                    DeliverSM msg = (DeliverSM) pObj.getPacket();
                    
                    //SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ pObj.getSmscRef() :" + pObj.getSmscRef());
                    //SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ pObj.getMessageID() :" + pObj.getMessageID());
                    //Update Delivery status in Table
                    Integer messageId = (int) (long) pObj.getMessageID();
                    //SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ messageId :" + messageId);
                    String response=new SmsService().updateDeliverInfo(messageId);
                    if (new SmsService().seqExistsForMsisdn(messageId)) {
                    	
                    	SMSSubscription smsSubscription =new SmsService().getSubscription(messageId);
                    	String notifyUrlPostRequest= "";
                    	if (smsSubscription.getOperatorCode()!=null) {
                    		notifyUrlPostRequest = "{\"deliveryInfoNotification\": {"
			                    	  +"  \"callbackData\": \""+smsSubscription.getCallbackData()+"\","
			                    	  +"  \"deliveryInfo\": {"
			                    	  +"  \"operatorCode\": \""+smsSubscription.getOperatorCode()+"\","//ADDED
									  +"  \"filterCriteria\": \""+smsSubscription.getFilterCriteria()+"\","//ADDED
			                    	  +"      \"address\": \"tel:+"+smsSubscription.getResAddress().replaceAll("\\[|\\]", "").replace("tel:+", "").replace("\"", "")+"\","
			                    	  +"      \"deliveryStatus\": \"DeliveredToNetwork\"}"
			                    	  +"  }}";
						}else {						
							notifyUrlPostRequest = "{\"deliveryInfoNotification\": {"
							                    	  +"  \"callbackData\": \""+smsSubscription.getCallbackData()+"\","
							                    	  +"  \"deliveryInfo\": {"
							                    	  +"      \"address\": \"tel:+"+smsSubscription.getResAddress().replaceAll("\\[|\\]", "").replace("tel:+", "").replace("\"", "")+"\","
							                    	  +"      \"deliveryStatus\": \"DeliveredToNetwork\"}"
							                    	  +"  }}";
						}

                    	//SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ smsSubscription.getNotifyUrl() :" + smsSubscription.getNotifyUrl());
                    	//SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ notifyUrlPostRequest :" + notifyUrlPostRequest);
                    	//SystemLog.getInstance().getLogger("smscomp_event").info("################DRPROCESSOR################ smsSubscription.getResAddress() :" + smsSubscription.getResAddress());
                    	if (smsSubscription.getNotifyUrl()!=null) {
                    		executor.submit( new HTTPPoster(smsSubscription.getNotifyUrl(), notifyUrlPostRequest,false,smsSubscription.getResAddress()));
						}
                    	
					}
                    
                }
                mgr.deleteBatch(msgs);
            } catch (Exception e) {
                e.printStackTrace();
                //SystemLog.getInstance().getErrorLog().error("CHEETA-ERR,CheetaMsgListen",e);
            }
        }
    }
}
