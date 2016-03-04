package com.tatadocomo.ussd.api;

import ie.omk.smpp.message.DeliverSM;

import java.util.ArrayList;

import com.tatadocomo.ussd.api.entity.SmppIdeamart;
import com.tatadocomo.ussd.api.responsebean.sms.InboundSMSMessage;
import com.tatadocomo.ussd.api.responsebean.sms.InboundSMSMessageNotification;
import com.tatadocomo.ussd.api.responsebean.sms.MessageNotification;
import com.tatadocomo.ussd.api.service.SmsService;
import com.tatadocomo.ussd.util.FileUtil;
import com.dialog.rnd.iris.smpp.core.PacketObject;
import com.dialog.rnd.iris.smpp.db.HSQLManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;
import com.tatadocomo.ussd.api.responsebean.ussd.Application;
import com.tatadocomo.ussd.api.responsebean.ussd.InboundMessage;
import com.tatadocomo.ussd.api.responsebean.ussd.InboundUSSDMessageRequest;
import com.tatadocomo.ussd.api.responsebean.ussd.USSDAction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.HTTP;

public class MOProcessor extends Thread {

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
                    //String port = "7555";
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

                    //unique port change 24/04/2014 

                    Integer regid = 0;

                    //TEST 
                    //phoneNo = "94773335976";
                    //port = "7555";
                    Application regapp = sm.getUniquesub(port.replace("tel:", "").replaceAll("\\+", ""));

                    if (regapp == null) {
                        regapp = sm.getReceiptsub(port, keyword);
                    }

                    message = message.trim();

                    if ((regapp != null) && (regapp.getId() != null)) {
                        regid = regapp.getId().intValue();
                    }

                    try {
                        if (sm.saveSMSInbound(msg.getDeliveryTime(), msg.getDataCoding(), msg.getErrorCode(),
                                msg.getMessageId(), msg.getMessageStatus(), msg.getDestination().getAddress(),
                                msg.getSource().getAddress(), "" + msg.getSequenceNum(), message, regid)) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //send message notification
                    if ((regapp != null) && (msg.getDataCoding() > 0)) {

                        InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
                        inboundUSSDMessageRequest = sm.getResponseBodyByLastRequest(phoneNo);

                        String moAddress, moNotifyURL, mocallBackData, moclientCorrelator, moshortCode, mokeyWord, sessionID;

                        if (inboundUSSDMessageRequest == null) {
                            //MO ussd request doesn't handle yet
                            /*moAddress = phoneNo;
                             moNotifyURL = regapp.getNotifyurl();
                             mocallBackData = regapp.getCallbackdata();
                             moclientCorrelator = regapp.getClientcorrelator();
                             moshortCode = port;
                             mokeyWord = keyword;
                             sessionID = moshortCode+System.currentTimeMillis();       */
                            continue;
                        } else {
                            moNotifyURL = inboundUSSDMessageRequest.getResponseRequest().getNotifyURL();
                            //==================================================================================
                            if (message.equalsIgnoreCase("1") || message.equalsIgnoreCase("2")) {
                                moNotifyURL = FileUtil.getApplicationProperty("ni_ussd_notify");
                            }
                            //==================================================================================

                            moAddress = inboundUSSDMessageRequest.getAddress();
                            mocallBackData = inboundUSSDMessageRequest.getResponseRequest().getCallbackData();
                            moclientCorrelator = inboundUSSDMessageRequest.getClientCorrelator();
                            moshortCode = inboundUSSDMessageRequest.getShortCode();
                            mokeyWord = inboundUSSDMessageRequest.getKeyword();
                            sessionID = inboundUSSDMessageRequest.getSessionID();
                        }

                        /*String jsonObj = "{" + "\"inboundUSSDMessageRequest\":" + "{"
                         + "\"address\":" + "\"tel:+tel:+" + moAddress + "\","
                         + "\"responseRequest\":" + "{" + "\"notifyURL\":\"" + moNotifyURL + "\"," + "\"callbackData\":\""
                         + mocallBackData + "\"" + "}" + ","
                         + "\"inboundUSSDMessage\":\"" + message + "\","
                         + "\"clientCorrelator\":\"" + moclientCorrelator + "\","
                         + "\"sessionID\":\"" + sessionID + "\","
                         + "\"shortCode\":\"" + moshortCode + "\","
                         + "\"ussdAction\":\"" + USSDAction.mocont + "\","
                         + "\"keyword\":\"" + inboundUSSDMessageRequest.getKeyword() + "\""
                         + "}}";
                         */

                        inboundUSSDMessageRequest.setInboundUSSDMessage(message);
                        inboundUSSDMessageRequest.setAddress("tel:+tel:+" + moAddress);
                        InboundMessage notifyResp = new InboundMessage();
                        notifyResp.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);

                        //String httppostout = HTTPPoster.excutePost(sub.getNotifyurl(), new Gson().toJson(notify),true);
                        executor.submit(new HTTPPoster(moNotifyURL, new Gson().toJson(notifyResp), true, msg.getSource().getAddress()));
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
