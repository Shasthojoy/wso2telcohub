package com.dialog.psi.api;

import ie.omk.smpp.message.DeliverSM;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.dialog.psi.api.entity.Application;
import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.entity.SmppIdeamart;
import com.dialog.psi.api.entity.SmppReceiptSubs;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessage;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessageNotification;
import com.dialog.psi.api.responsebean.sms.MessageNotification;
import com.dialog.psi.api.service.SmsService;
import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.db.HSQLManager;
import com.dialog.rnd.iris.smpp.util.SMPP34Data;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;


public class MOProcessor extends Thread {

	private static final Logger LOG = Logger.getLogger(MOProcessor.class.getName());
	
    private int m_sleepTime = 100;
    private HSQLManager mgr = null;
    private SmsService sm;

    public MOProcessor() {
        try {
            mgr = new HSQLManager();
            sm = new SmsService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
	@Override
    public void run() {
        while (true) {
            try {
                ArrayList<PacketObject> msgs = mgr.getMessages(50);
                //SystemLog.getInstance().getLogger("smscomp_event").info("in loop :"+ msgs);
                if ((msgs == null) || (msgs.isEmpty())) {
                    if (m_sleepTime > 0) {
                        sleep(m_sleepTime);
                    }
                    continue;
                }

               for (PacketObject pObj : msgs) {
                    if (pObj == null) {
                        continue;
                    }
                    DeliverSM msg = (DeliverSM) pObj.getPacket();
                    
                    //Update Delivery status in Table
                    SystemLog.getInstance().getLogger("smscomp_event").info("msg.getMessageId :" + msg.getMessageId());
                    SystemLog.getInstance().getLogger("smscomp_event").info("msg.getMessage :" + msg.getMessage());
                    SystemLog.getInstance().getLogger("smscomp_event").info("msg.getMessageText :" + msg.getMessageText());
                    
                    String response=new SmsService().updateDeliverInfo(msg.getSequenceNum());
                    if (new SmsService().seqExistsForMsisdn(msg.getSequenceNum())) {
                    	
                    	SMSSubscription smsSubscription =new SmsService().getSubscription(msg.getSequenceNum());
                    	
                    	String notifyUrlPostRequest = "{\"deliveryInfoNotification\": {"
							                    	  +"  \"callbackData\": \""+smsSubscription.getCallbackData()+"\","
							                    	  +"  \"deliveryInfo\": {"
							                    	  +"      \"address\": \"tel:+"+smsSubscription.getResAddress().replaceAll("\\[|\\]", "").replace("tel:+", "").replace("\"", "")+"\","
							                    	  +"      \"deliveryStatus\": \"DeliveredToNetwork\"},"
							                    	  +"  }}";

                        SystemLog.getInstance().getLogger("smscomp_event").info("notifyUrlPostRequest :" + notifyUrlPostRequest);

                        String httppostout = HTTPPoster.excutePost(smsSubscription.getNotifyUrl(), notifyUrlPostRequest,false);
					}
                    
                    String phoneNo = msg.getSource().getAddress();
                    String message = "";
                    boolean isideamart = false;
                    try {
                        message = new String(msg.getMessage());
                    } catch (Exception e) {
                        message = "";
                    }
                    String port = msg.getDestination().getAddress();
                    //String port = "7555";//JUST FOR TEST.NEED TO REMOVE_PRIYANKA_06608
                    String smscRef = "" + msg.getSequenceNum();

                    if (message == null) {
                        continue;
                    }
                    
                    // trim message
                    message = message.trim();
                    int pos = message.indexOf(" ");
                    String keyword = "";

                    if (pos == -1) {
                        keyword = message;
                    } else {
                        keyword = message.substring(0, pos);
                    }

                    SystemLog.getInstance().getLogger("smscomp_event").info("keywork: port :" + keyword + ":" + port);
//                    Application app = sm.getApplicationByKeywordAndSender(keyword, port);
//                    if (app == null) {
//                        app = sm.getApplicationByKeywordAndOOSender(keyword, port);
//                    }
                    
                    //unique port change 24/04/2014 
                    
                    SmppReceiptSubs sub = null;
                    Integer regid = 0;
                    Application regapp = sm.getApplicationbyapp(port);
                    //String idregistrion = regapp.getRegId();
                    
                    if (regapp == null) {
                        //probable an ideamart app
                        SmppIdeamart app = sm.getApplicationByKeywordAndSender(keyword, port);
                        if (app == null) {
                            app = sm.getApplicationByKeywordAndOOSender(keyword, port);
                        }
                    
                        if (app == null) {
                            SystemLog.getInstance().getLogger("smscomp_event").info("Application Not Found :" + port);
                            continue;
                        } else {
                            regapp = sm.getApplication(app.getAppId());
                            isideamart = true;
                        }                        
                    }                   
                    
                    if (regapp.getUniport().equalsIgnoreCase("Y")) {
                        keyword ="";
                        sub = sm.getSMSReceiptsub(port);
                        regid = regapp.getId();
                    } else {                    
                        sub = sm.getSMSReceiptsub(port,keyword);
                    }

                    message = message.trim();

                    if ((sub != null) && (sub.getRegId() != null)) {
                        regid = ((Application) sub.getRegId()).getId();
                    }

                    try {
                        if (sm.saveSMSInbound(msg.getDeliveryTime(), msg.getDataCoding(), msg.getErrorCode(),
                                msg.getMessageId(), msg.getMessageStatus(), msg.getDestination().getAddress(),
                                msg.getSource().getAddress(), "" + msg.getSequenceNum(), message, regid)) {
                            //LOG.info("[MOProcessor], Save + " + "Recode Saved Successfully " + msg.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //send message notification
                    if (sub != null) {

                        //SystemLog.getInstance().getLogger("smscomp_event").info("regid:" + regid);
                        //check for ideamart apps
                        //SmppIdeamart app = sm.getApplicationByKeywordAndSender(sub.getCriteria(), port);
                        //if (app == null) {
                        //    app = sm.getApplicationByKeywordAndOOSender(sub.getCriteria(), port);
                        // }

                       // SystemLog.getInstance().getLogger("smscomp_event").info("app:" + app);
                        
                        if (isideamart) {
                            String mo = "{"
                                    + "\"message\":\"" + message + "\","
                                    + "\"requestId\":\"" + msg.getSequenceNum() + "\","
                                    + "\"encoding\":\"0\","
                                    + "\"applicationId\":\"" + regapp.getAppId() + "\","
                                    + "\"sourceAddress\":\"tel:" + msg.getSource().getAddress() + "\","
                                    + "\"version\":\"1.0\""
                                    + "}";

                            SystemLog.getInstance().getLogger("smscomp_event").info("mo msg :" + mo);

                            String httppostout = HTTPPoster.excutePost(sub.getNotifyurl(), mo,false);

                        } else {

                            InboundSMSMessage msginbound = new InboundSMSMessage();
                            msginbound.setDateTime((msg.getDeliveryTime() == null) ? "" : msg.getDeliveryTime().toString());
                            msginbound.setDestinationAddress(msg.getDestination().getAddress());
                            msginbound.setMessage(message);
                            msginbound.setMessageId(msg.getMessageId());
                            msginbound.setSenderAddress(msg.getSource().getAddress());

                            MessageNotification notify = new MessageNotification();
                            InboundSMSMessageNotification inbnotify = new InboundSMSMessageNotification();
                            inbnotify.setInboundSMSMessage(msginbound);
                            notify.setInboundSMSMessageNotification(inbnotify);

                            String httppostout = HTTPPoster.excutePost(sub.getNotifyurl(), new Gson().toJson(notify),true);
                        }

                    }
                    //}
                }
                mgr.deleteIncomingBatch(msgs);
            } catch (Exception e) {
                e.printStackTrace();
                //SystemLog.getInstance().getErrorLog().error("CHEETA-ERR,CheetaMsgListen",e);
            }
        }
    }
}
