package com.dialog.psi.api;

import ie.omk.smpp.message.DeliverSM;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.json.HTTP;

import lk.dialog.ideabiz.logger.DirectLogger;
import lk.dialog.ideabiz.logger.model.impl.SMSInbound;

import com.dialog.psi.api.entity.Application;
import com.dialog.psi.api.entity.SmppIdeamart;
import com.dialog.psi.api.entity.SmppReceiptSubs;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessage;
import com.dialog.psi.api.responsebean.sms.InboundSMSMessageNotification;
import com.dialog.psi.api.responsebean.sms.MessageNotification;
import com.dialog.psi.api.service.SmsService;
import com.dialog.psi.util.FileUtil;
import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.db.HSQLManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;

public class MOProcessor extends Thread {

    private int m_sleepTime = 100;
    private HSQLManager mgr = null;
    private SmsService sm;
    private DirectLogger directLogger = null;

    public MOProcessor() {
        try {
            mgr = new HSQLManager();
            sm = new SmsService();
            directLogger = new DirectLogger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {

        ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(FileUtil.getApplicationProperty("sendPoolSize")));

        while (true) {
            try {
                ArrayList<PacketObject> msgs = mgr.getMessages(50);

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


                    String phoneNo = msg.getSource().getAddress();
                    String message = "";
                    boolean isideamart = false;
                    try {
                        message = new String(msg.getMessage());
                    } catch (Exception e) {
                        message = "";
                    }
                    String port = msg.getDestination().getAddress();

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
                                msg.getSource().getAddress(), "" + msg.getSequenceNum(), message, regid,sub.getId())) {
                            //LOG.info("[MOProcessor], Save + " + "Recode Saved Successfully " + msg.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Integer Id = 0;
                        if (sub.getAMID() != null)
                            Id = sub.getAMID();
                        directLogger.addLog(new SMSInbound("v2", Id, msg.getDestination().getAddress(), String.valueOf(msg.getSequenceNum()), msg.getSource().getAddress(), "", message, ""));
                    } catch (Exception e) {}
                    
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

                            //String httppostout = HTTPPoster.excutePost(sub.getNotifyurl(), mo,false);
                            executor.submit(new HTTPPoster(sub.getNotifyurl(), mo, false, msg.getSource().getAddress()));

                        } else {

                            InboundSMSMessage msginbound = new InboundSMSMessage();
                            msginbound.setDateTime((msg.getDeliveryTime() == null) ? "" : msg.getDeliveryTime().toString());
                            msginbound.setDestinationAddress(msg.getDestination().getAddress());
                            msginbound.setMessage(message);
                            //msginbound.setMessageId(msg.getMessageId());
                            msginbound.setMessageId(smscRef);
                            msginbound.setSenderAddress(msg.getSource().getAddress());

                            MessageNotification notify = new MessageNotification();
                            InboundSMSMessageNotification inbnotify = new InboundSMSMessageNotification();
                            inbnotify.setInboundSMSMessage(msginbound);
                            inbnotify.setCallbackData(sub.getClientcorrelator());
                            notify.setInboundSMSMessageNotification(inbnotify);

                            //String httppostout = HTTPPoster.excutePost(sub.getNotifyurl(), new Gson().toJson(notify),true);
                            executor.submit(new HTTPPoster(sub.getNotifyurl(), new Gson().toJson(notify),true,msg.getSource().getAddress()));
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
