package com.dialog.psi.api;


import ie.omk.smpp.Address;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.tlv.Tag;
import ie.omk.smpp.util.UCS2Encoding;
import ie.omk.smpp.util.UTF16Encoding;

import com.google.common.base.CharMatcher;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import lk.dialog.ideabiz.library.JWT;
import lk.dialog.ideabiz.logger.DirectLogger;
import lk.dialog.ideabiz.logger.model.impl.SMSOutbound;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateSBRetrieveSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateSBSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.responsebean.sms.ISMSresponse;
import com.dialog.psi.api.responsebean.sms.SMSRequest;
import com.dialog.psi.api.responsebean.sms.SendSMSRequest;
import com.dialog.psi.api.responsebean.sms.StartSubscription;
import com.dialog.psi.api.responsebean.sms.StartSubscriptionMultipleOperators;
import com.dialog.psi.api.responsebean.sms.SubscribeRequest;
import com.dialog.psi.api.service.SmsService;
import com.dialog.psi.util.FileUtil;
import com.dialog.psi.util.NumberGenerator;
import com.dialog.psi.util.UTF8Encoding;
import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/")
public class SMSHandler {

public static	DirectLogger directLogger=null;
	
	public SMSHandler() {
		try {
			directLogger=new DirectLogger();
		} catch (Exception e) {
		}
	}


	//private static final Logger log = Logger.getLogger(SMSHandler.class.getName());
    boolean isvalidate = true;
    @Context
    private UriInfo context;
//    private ApplicationManager appMgr;
//
//    public SMSHandler() {
//        appMgr = new ApplicationManager();
//    }

//    @POST
//    @Path("outbound/{senderAddress}")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public String sayHello(String jsonBody, @PathParam("senderAddress") String senderAddress) {
//        System.out.println("hello :" + jsonBody);
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        SMSRequest smsReq = gson.fromJson(jsonBody, SMSRequest.class);
//
//        Application app = appMgr.getApplication(smsReq.getApplicationId());
//
//        int msgNum = NumberGenerator.next();
//
//        try {
//            List<String> list = smsReq.getDestinationAddresses();
//            for (String to : list) {
//                to = to.replace("tel:", "").trim();
//                if (to.length() >= 9 && to.startsWith("9477")) {
//                    sendMessage(app.getSender(), to, smsReq.getMessage(), false, msgNum);
//                } else {
//                    sendMessage(app.getOtherOperatorSender(), to, smsReq.getMessage(), false, msgNum);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("-------------before send Response -------------");
//       String test = "";
//        String ret = "{"
//                + "\"statusCode\":\"S1000\","
//                + "\"statusDetail\":\"Success\","
//                + "\"requestId\":\"MSG_" + test + "\","
//                + "\"version\":\"1.0\""
//                + "}";
//        System.out.println(ret);
//        return ret;
//   }
    @POST
    @Path("outbound/{senderAddress}/axiataport")
    @Consumes("application/json")
    @Produces("application/json")
    public Response axiataPort(@PathParam("senderAddress") String senderAddress, String jsonBody) {

        SystemLog.getInstance().getLogger("smscomp_event").info("axiata port :" + jsonBody);
        Gson gson = new GsonBuilder().serializeNulls().create();

        SendSMSRequest sendReq = gson.fromJson(jsonBody, SendSMSRequest.class);

        // String callbackData = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
        //Retriving outbound text message
        String message = sendReq.getOutboundSMSMessageRequest().getOutboundTextMessage().getMessage();
        //Retriving NotifyURL
        String notifyURL = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getNotifyURL();
        //Retriving Sender Address
        String sender = sendReq.getOutboundSMSMessageRequest().getSenderAddress();
        //Retriving clientCorrelator
        String clientCorrelator = sendReq.getOutboundSMSMessageRequest().getClientCorrelator();
        //Retriving senderName
        String senderName = sendReq.getOutboundSMSMessageRequest().getSenderName();
        //Retriving destinationAddress
        List<String> address = sendReq.getOutboundSMSMessageRequest().getAddress();

        Gson gsonResp = new Gson();
        String resAddress = gsonResp.toJson(sendReq.getOutboundSMSMessageRequest().getAddress());


        Gson gsonRes = new Gson();

        String receiptRequest = gsonRes.toJson(sendReq.getOutboundSMSMessageRequest().getReceiptRequest());

        int msgNum = NumberGenerator.next();

        String locationHeader = FileUtil.getApplicationProperty("sendSMSResourceURL") + senderAddress + "/requests/" + "D" + msgNum;

        //Delivary Info String
        String deliveryInfo = "[";
        for (String temp : address) {
            deliveryInfo = deliveryInfo + "{" + "\"address\":\"" + temp + "\"," + "\"deliveryStatus\":" + "\"MessageWaiting\"" + "},";
        }

        String subDelivary = deliveryInfo.substring(0, deliveryInfo.length() - 1);
        subDelivary = subDelivary + "]";

        String jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                + "\"address\":" + resAddress + ","
                + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "\"" + "}" + ","
                + "\"senderAddress\":\"" + sender + "\","
                + "\"outboundSMSTextMessage\":\"" + message + "\","
                + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                + "\"receiptRequest\":" + receiptRequest + ","
                + "\"senderName\":\"" + senderName + "\","
                + "\"resourceURL\":\"" + locationHeader + "\""
                + "}}";

        return Response.status(201).entity(jsonPayload).build();

    }

    //Sending SMS
    @POST
    @Path("outbound/{senderAddress}/requests")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response sendSMS(@PathParam("senderAddress") String senderAddress,@HeaderParam("X-JWT-Assertion") String headerParamJWT,  String jsonBody) {

    	SystemLog.getInstance().getLogger("smscomp_event").info("send sms :" + jsonBody);
        boolean isRegistered = false;
		Integer applicationId=0;
        try {

            if (isvalidate) {
                new ValidateSendSms().validate(jsonBody);
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            
            try {
            	applicationId = JWT.getApplicationIdInt(headerParamJWT);
			} catch (Exception e) {
				SystemLog.getInstance().getLogger("smscomp_event").info("JWT ERROR " + e.getMessage() + "|" + headerParamJWT );
			}
            
            //Gson gson = new GsonBuilder().serializeNulls().create();

        SendSMSRequest sendReq = gson.fromJson(jsonBody, SendSMSRequest.class);

        // String callbackData = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
        //Retriving outbound text message
        String message = sendReq.getOutboundSMSMessageRequest().getOutboundTextMessage().getMessage();
        String notifyURL;
        String callbackData;
        String receiptRequest = "";

        if (sendReq.getOutboundSMSMessageRequest().getReceiptRequest() != null) {
            Gson gsonRes = new Gson();
            receiptRequest = gsonRes.toJson(sendReq.getOutboundSMSMessageRequest().getReceiptRequest());

            //Retriving NotifyURL
            notifyURL = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getNotifyURL();
            callbackData = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();

            if (!notifyURL.isEmpty()) {
                isRegistered = true;
            }
        }
        //Retriving Sender Address
        String sender = sendReq.getOutboundSMSMessageRequest().getSenderAddress();
        //Retriving clientCorrelator
        String clientCorrelator = sendReq.getOutboundSMSMessageRequest().getClientCorrelator();
        //Retriving senderName
        String senderName = sendReq.getOutboundSMSMessageRequest().getSenderName();
        //Retriving destinationAddress
        List<String> address = sendReq.getOutboundSMSMessageRequest().getAddress();

            //String sender address reading from sender name
            if (senderName != null && senderName.length() > 0) {
                SystemLog.getInstance().getLogger("smscomp_event").info("Sender Replace :" + senderName);
                sender = senderName;
            }

        Gson gsonResp = new Gson();
        String resAddress = gsonResp.toJson(sendReq.getOutboundSMSMessageRequest().getAddress());


        int msgNum = NumberGenerator.next();

        String msgId = " ";

        //Send sms
            //try {
            List<String> list = sendReq.getOutboundSMSMessageRequest().getAddress();

            String listPorts = FileUtil.getApplicationProperty("smpp_port_list");
            String port;
            List<String> portList = new ArrayList<String>();

            listPorts = listPorts.substring(1, listPorts.length() - 1);

            int iterator = 1;

            do {
                port = str_piece(listPorts, ';', iterator);
                iterator++;
                if (port.isEmpty() == false) {
                    portList.add(port);
                }

            } while (port.isEmpty() == false);

//            if (sender.startsWith("tel:")) {
//                sender = sender.split(":")[1];
//                sender.replace("+", "").trim();
//            }
            sender = sender.replace("tel:+", "").trim();
            sender = sender.replace("tel:", "").trim();
            sender = sender.replace("+", "").trim();
            
            String finalSender = null;
            for (String to : list) {
                to = to.replace("tel:+", "").trim();
                to = to.replace("tel:", "").trim();
                to = to.replace("+", "").trim();

                //Unique Message ID
                
                senderAddress=senderAddress.replace("tel:", "").replaceAll("\\+", "");
                
                msgId = senderAddress + "" + to + "" + msgNum;

                SystemLog.getInstance().getLogger("smscomp_event").info("Sending : ReqSender " + sender );

                //if (to.length() >= 9 && to.startsWith("9477")) {
                    if (portList.contains(sender)) {
                    SystemLog.getInstance().getLogger("smscomp_event").info("Sending :" + sender + " > " + to);
                        sendMessage(sender, to, message, false, msgId, msgNum, isRegistered);
                        finalSender = sender;
                    } else {
                        String portSend = FileUtil.getApplicationProperty("smpp_port_1");
                        portSend = portSend.substring(1, portSend.length() - 1);
                    SystemLog.getInstance().getLogger("smscomp_event").info("Sending : Default " + portSend + " > " + to);
                        sendMessage(portSend, to, message, false, msgId, msgNum, isRegistered);
                        finalSender = portSend;
                    }
                //}

            }
            try {


					
				//directLogger.addLog(new SMSOutbound("v2", applicationId, list, senderName , sender,finalSender, clientCorrelator, msgId, "MessageWaiting", message));
            	DirectLogger.addLog(new SMSOutbound("v2", applicationId, list, senderName, sender, finalSender, clientCorrelator, msgId, "MessageWaiting", message));
			} catch (Exception e) {
				
			}
            //} catch (Exception e) {
            //     //e.printStackTrace();
            //     SystemLog.getInstance().getLogger("smscomp_event").error("[SMSHandler ],sendSMS, " + e.getMessage());
            //    return Response.status(400).entity("{Internal Server Error}").build();
            // }

        String resourceURL = FileUtil.getApplicationProperty("sendSMSResourceURL");
        resourceURL = resourceURL.substring(1, resourceURL.length() - 1);


        String locationHeader = resourceURL + senderAddress + "/requests/" + "D" + msgId;

        //Delivary Info String
        String deliveryInfo = "[";
        for (String temp : address) {
            deliveryInfo = deliveryInfo + "{" + "\"address\":\"" + temp + "\"," + "\"deliveryStatus\":" + "\"MessageWaiting\"" + "},";
        }

        String subDelivary = deliveryInfo.substring(0, deliveryInfo.length() - 1);
        subDelivary = subDelivary + "]";

        String jsonPayload;
        if (sendReq.getOutboundSMSMessageRequest().getReceiptRequest() != null) {
            
                Integer smscRefNumber=msgNum;
                String deliveryStatus="MessageWaiting";
                
                //SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   smscRefNumber :" + smscRefNumber);
                callbackData=sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
                //SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   callbackData :" + callbackData);
                String senderMsisdn=senderAddress.replace("tel:+", "");
                String notifyUrl="";

                String response=new SmsService().insertSmppSubscriptionIfExists(smscRefNumber,deliveryStatus,resAddress,callbackData,senderMsisdn,clientCorrelator,notifyUrl,msgId);
        	
            jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                    + "\"address\":" + resAddress + ","
                    + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "/deliveryInfos" + "\"" + "}" + ","
                    + "\"senderAddress\":\"" + sender + "\","
                    + "\"outboundSMSTextMessage\":" + "{" + "\"message\":\"" +  message + "\"},"
                    + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                    + "\"receiptRequest\":" + receiptRequest + ","
                    + "\"senderName\":\"" + senderName + "\","
                    + "\"resourceURL\":\"" + locationHeader + "\""
                    + "}}";
        } else {
            jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                    + "\"address\":" + resAddress + ","
                    + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "/deliveryInfos" + "\"" + "}" + ","
                    + "\"senderAddress\":\"" + sender + "\","
                    + "\"outboundSMSTextMessage\":" + "{" + "\"message\":\"" +  message + "\"},"
                    + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                    + "\"senderName\":\"" + senderName + "\","
                    + "\"resourceURL\":\"" + locationHeader + "\""
                    + "}}";
        }
        return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(jsonPayload).build();
        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }   
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new com.axiata.dialog.oneapi.validation.ServiceException("SVC0001", "Internal Server Error", ""));
            String jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }
    }

    //ideamart
    @POST
    @Path("ideamart")
    @Consumes("application/json")
    @Produces("application/json")
    public String ideamartSend(@HeaderParam("X-JWT-Assertion") String headerParamJWT,String jsonBody) {

        SystemLog.getInstance().getLogger("smscomp_event").info("ideamart :" + jsonBody);

        Gson gson = new GsonBuilder().serializeNulls().create();
        SMSRequest smsReq = gson.fromJson(jsonBody, SMSRequest.class);

        
        Integer applicationId=0;
       
            try {
            	applicationId = JWT.getApplicationIdInt(headerParamJWT);
			} catch (Exception e) {
				SystemLog.getInstance().getLogger("smscomp_event").info("JWT ERROR " + e.getMessage() + "|" + headerParamJWT );
			}
        
        
        int msgNum = NumberGenerator.next();
        if (new SmsService().ideaMartSend(smsReq, msgNum,applicationId)) {

            SystemLog.getInstance().getLogger("smscomp_event").info("-------------before send Response -------------");
            String ret = "{"
                    + "\"statusCode\":\"S1000\","
                    + "\"statusDetail\":\"Success\","
                    + "\"requestId\":\"MSG_" + msgNum + "\","
                    + "\"version\":\"1.0\""
                    + "}";
            SystemLog.getInstance().getLogger("smscomp_event").info("ideaMart send: "+ ret);
            return ret;
        }
        return "Error!";
    }

    @POST
    @Path("RequestError/{message}")
    @Consumes("application/json")
    @Produces("application/json")
    public String registrations(@QueryParam("maxBatchSize") String maxBatchSize,
            @HeaderParam("authorization") String authorization, @HeaderParam("excep") String errmsg,
            @HeaderParam("excepid") String errorcode) {

        SystemLog.getInstance().getLogger("smscomp_event").info("error request :" + errorcode + ":" + errmsg);

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        RequestError requesterror = new RequestError();
        requesterror.setServiceException(new ServiceException(errorcode, errmsg, ""));
        jsonreturn = new Gson().toJson(requesterror);
        return jsonreturn;

    }

    @GET
    @Path("inbound/registrations/{registrationId}/messages")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response registrations(@QueryParam("maxBatchSize") String maxBatchSize,
            @PathParam("registrationId") String registrationId, @HeaderParam("authorization") String authorization) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        SystemLog.getInstance().getLogger("smscomp_event").info("retrive message :" + registrationId + maxBatchSize);

        try {
            if (registrationId.isEmpty()) {
                throw new AxiataException("SVC0002", "Invalid input value for message part %1", new String[]{"Missing mandatory parameter: registrationId"});
            } 
                      
            if (isvalidate) {
                String vali[] = {registrationId, maxBatchSize};
                new ValidateSBRetrieveSms().validate(vali);
            }
            ISMSresponse inboundresp = new SmsService().retriveSMS(registrationId, maxBatchSize);

            jsonreturn = new Gson().toJson(inboundresp);

            /*if (inboundresp instanceof RequestError) {
                RequestError requesterror = (RequestError) inboundresp;
                return jsonreturn;
            } */

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
             jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        return Response.status(200).entity(jsonreturn).build();
    }

    
    //===========================CRITERIA ADDED====================================PRIYANKA_06608====================================
    @GET
    @Path("inbound/registrations/{registrationId}/{criteria}/messages")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response registrationsWithCriteria(@QueryParam("maxBatchSize") String maxBatchSize,
            @PathParam("registrationId") String registrationId,@PathParam("criteria") String criteria, @HeaderParam("authorization") String authorization) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        SystemLog.getInstance().getLogger("smscomp_event").info("retrive message :" + registrationId + maxBatchSize);

        try {
            if (registrationId.isEmpty()) {
                throw new AxiataException("SVC0002", "Invalid input value for message part %1", new String[]{"Missing mandatory parameter: registrationId"});
            } 
                      
            if (isvalidate) {
                String vali[] = {registrationId,criteria, maxBatchSize};
                new ValidateSBRetrieveSms().validate(vali);
            }
            ISMSresponse inboundresp = new SmsService().retriveSMS(registrationId, maxBatchSize,criteria);

            jsonreturn = new Gson().toJson(inboundresp);

            /*if (inboundresp instanceof RequestError) {
                RequestError requesterror = (RequestError) inboundresp;
                return jsonreturn;
            } */

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        return Response.status(200).entity(jsonreturn).build();
    }
    //===========================CRITERIA ADDED====================================PRIYANKA_06608    
    
    @POST
    @Path("inbound/subscriptions")
    @Consumes("application/json")
    @Produces("application/json")
    public Response subscriptions(@HeaderParam("authorization") String authorization, String jsonBody) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        //System.out.println("registration :" + registrationId);

        try {

            if (isvalidate) {
                new ValidateSBSubscription().validate(jsonBody);
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            SubscribeRequest subsrequst = gson.fromJson(jsonBody, SubscribeRequest.class);

            ISMSresponse notifyresp = new SmsService().subscribeToNotify(subsrequst, subsrequst.getSubscription().getDestinationAddress().replace("tel:+", "").trim());

            jsonreturn = new Gson().toJson(notifyresp);

            if (notifyresp instanceof RequestError) {
                RequestError requesterror = (RequestError) notifyresp;

                //return jsonreturn;
                return Response.status(400).entity(jsonreturn).build();
            }

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        //return jsonreturn;
        return Response.status(200).entity(jsonreturn).build();
    }

    @DELETE
    @Path("inbound/subscriptions/{subsid}")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response removeSubscriptions(@PathParam("subsid") String subsid, @HeaderParam("authorization") String authorization, String jsonBody) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        SystemLog.getInstance().getLogger("smscomp_event").info("remove subscription :" + subsid);

        try {

            if (isvalidate) {
                String sub[] = {subsid};
                new ValidateCancelSubscription().validate(sub);
            }
            ISMSresponse notifyresp = new SmsService().removeSubsNotify(subsid);

            jsonreturn = new Gson().toJson(notifyresp);

            if (notifyresp instanceof RequestError) {
                RequestError requesterror = (RequestError) notifyresp;

                //return jsonreturn;
                return Response.status(400).entity(jsonreturn).build();
            }

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        //return jsonreturn;
        return Response.status(204).entity(jsonreturn).build();
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

    private String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                count++;
                if (count == index) {
                    break;
                }
            } else {
                if (count == index - 1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }

    public void sendMessage(String port, String phoneNo, String msg, boolean highPriority, String msgId, int msgRefNum, boolean isRegistered) throws UnsupportedEncodingException {
        ConnectionPool pool = SMSCPoolManager.getInstance().getConnectionPool(port.toUpperCase());
        
        /*CharsetDetector detector=new CharsetDetector();
        detector.setText(msg.getBytes());
        String unicodeType=detector.detect().getName();*/
        int messageLength=160;
        int splitLength=150;
        
        if (CharMatcher.ASCII.matchesAllOf(msg)) {
        	SystemLog.getInstance().getLogger("smscomp_event").info("CharMatcher : ASCII");
        } else {
        	messageLength=70;
            splitLength=67;
            SystemLog.getInstance().getLogger("smscomp_event").info("CharMatcher : UTF8");
        }
        
        SystemLog.getInstance().getLogger("smscomp_event").info("CharMatcher 2. phoneNo : "+ phoneNo);
        
        if (msg.length() > messageLength) {
            ArrayList<String> msgs = splitMessage(msg, splitLength);
            for (int i = 0; i < msgs.size(); i++) {
                SubmitSM sm = new SubmitSM();
                sm.setSource(new Address(pool.getSrcTon(), pool.getSrcNpi(), port));
                sm.setDestination(new Address(pool.getTon(), pool.getNpi(), phoneNo));

                if (isRegistered == true) {
                    sm.setRegistered((byte) 1);
                } else {
                    sm.setRegistered((byte) 0);
                }
                
                if (CharMatcher.ASCII.matchesAllOf(msg)) {
                	sm.setMessageText(msgs.get(i));
                }else{
                	sm.setAlphabet(new UTF8Encoding()); 
                	//sm.setAlphabet(new UTF16Encoding(false));
                	sm.setMessage(new UTF8Encoding().encodeString(msgs.get(i)));
                }  
                //Set Message ID
                sm.setMessageId(msgId);
                sm.setOptionalParameter(Tag.SAR_TOTAL_SEGMENTS, new Integer(msgs.size()));
                sm.setOptionalParameter(Tag.SAR_SEGMENT_SEQNUM, new Integer(i + 1));
                sm.setOptionalParameter(Tag.SAR_MSG_REF_NUM, msgRefNum);

                pool.addMessage(sm, highPriority);
            }

        } else {
            SubmitSM sm = new SubmitSM();
            sm.setSequenceNum(msgRefNum);
            sm.setDestination(new Address(pool.getTon(), pool.getNpi(), phoneNo));
            sm.setSource(new Address(pool.getSrcTon(), pool.getSrcNpi(), port));
            if (isRegistered == true) {
                sm.setRegistered((byte) 1);
            } else {
                sm.setRegistered((byte) 0);
            }
            
            if (CharMatcher.ASCII.matchesAllOf(msg)) {
            	sm.setMessageText(msg);
            }else{
            	sm.setAlphabet(new UTF8Encoding());
            	sm.setMessage(new UTF8Encoding().encodeString(msg));
            }            
            sm.setMessageId(msgId);
            pool.addMessage(sm, highPriority);
        }
    }
    
  //================================================================================PRIYANKA_06608================================================================================

  	@POST
  	@Path("outbound/{senderAddress}/subscriptions")
  	@Consumes("application/json")
  	@Produces("application/json")
  	public Response outboundSubscriptions(@PathParam("senderAddress") String senderAddress, String jsonBody) {
  		String response="";
  		senderAddress=senderAddress.replace("tel:+", "");
  		String jsonreturn="";
  		String result="0";
  		String locationHeader;
  	    try {
  	        Gson gson = new GsonBuilder().serializeNulls().create();
  	        StartSubscription subscription = gson.fromJson(jsonBody, StartSubscription.class);
  	        Integer smscRefNumber=-1;
  	        String deliveryStatus="None";
  	        String resAddress="None";
  	        String clientCorrelator="None";
  	        result=new SmsService().insertSmppSubscription(smscRefNumber,deliveryStatus,resAddress,subscription.getDeliveryReceiptSubscription().getCallbackReference().getCallbackData(),senderAddress,clientCorrelator,subscription.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL());

  	        locationHeader= FileUtil.getApplicationProperty("startSubscriptionResourceURL").replace("\"", "")+"sub"+result;
  	        
  	        response="{\"deliveryReceiptSubscription\": {"
  			        		+ "\"callbackReference\": {"
  			        		+ "\"callbackData\":  \""+subscription.getDeliveryReceiptSubscription().getCallbackReference().getCallbackData()+"\","
  			        		+ "\"notifyURL\": \""+subscription.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL()+"\"},"
  			        		+ "\"filterCriteria\": \""+ subscription.getDeliveryReceiptSubscription().getFilterCriteria() +"\"," 
  			        		+ "\"clientCorrelator\": \""+ subscription.getDeliveryReceiptSubscription().getClientCorrelator() +"\"," 
  			        		+ "\"resourceURL\": \""+locationHeader+"\"}"
  			        		+ "}";  
  	        jsonreturn = new Gson().toJson(response);
  	    } catch (Exception e) {
  	        SystemLog.getInstance().getLogger("smscomp_event").error("[Outbound Request], Error + " + "subscriptions " + e.getMessage());
  	        RequestError requesterror = new RequestError();
  	        requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
  	        jsonreturn = new Gson().toJson(requesterror);
  	        return Response.status(400).entity(jsonreturn).build();
  	    }
  	    return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(response).build();
  	}
  	

  	@POST
  	@Path("outbound/{senderAddress}/subscriptionsMultipleOperators")
  	@Consumes("application/json")
  	@Produces("application/json")
  	public Response outboundSubscriptionsMultipleOperators(@PathParam("senderAddress") String senderAddress, String jsonBody) {
  		String response="";
  		senderAddress=senderAddress.replace("tel:+", "");
  		String jsonreturn="";
  		String result="0";
  		String locationHeader;
  	    try {
  	        Gson gson = new GsonBuilder().serializeNulls().create();
  	        StartSubscriptionMultipleOperators subscription = gson.fromJson(jsonBody, StartSubscriptionMultipleOperators.class);
  	        Integer smscRefNumber=-1;
  	        String deliveryStatus="None";
  	        String resAddress="None";
  	        String clientCorrelator="None";
  	        result=new SmsService().insertSmppSubscriptionMultipleOperators(smscRefNumber,deliveryStatus,resAddress,subscription.getDeliveryReceiptSubscription().getCallbackReference().getCallbackData(),senderAddress,clientCorrelator,subscription.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL(),subscription.getDeliveryReceiptSubscription().getFilterCriteria(),subscription.getDeliveryReceiptSubscription().getOperatorCode());
  	        locationHeader= FileUtil.getApplicationProperty("startSubscriptionResourceURL").replace("\"", "")+"sub"+result;  	        
  	        response="{\"deliveryReceiptSubscription\": {"
  			        		+ "\"callbackReference\": {"
  			        		+ "\"callbackData\":  \""+subscription.getDeliveryReceiptSubscription().getCallbackReference().getCallbackData()+"\","
  			        		+ "\"notifyURL\": \""+subscription.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL()+"\"},"
  			        		+ "\"filterCriteria\": \""+ subscription.getDeliveryReceiptSubscription().getFilterCriteria() +"\","
  			        		+ "\"clientCorrelator\": \""+ subscription.getDeliveryReceiptSubscription().getClientCorrelator() +"\","
  			        		+ "\"operatorCode\": \""+ subscription.getDeliveryReceiptSubscription().getOperatorCode() +"\","
  			        		+ "\"resourceURL\": \""+locationHeader+"\"}"
  			        		+ "}";  
  	        jsonreturn = new Gson().toJson(response);
  	    } catch (Exception e) {
  	        SystemLog.getInstance().getLogger("smscomp_event").error("[Outbound Request], Error + " + "subscriptions " + e.getMessage());
  	        RequestError requesterror = new RequestError();
  	        requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
  	        jsonreturn = new Gson().toJson(requesterror);
  	        return Response.status(400).entity(jsonreturn).build();
  	    }
  	    return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(response).build();
  	}  	
  	
  	
      @GET
      @Path("outbound/{senderAddress}/requests/{requestId}/deliveryInfos")
      @Consumes("application/json")
      @Produces("application/json")
      public String queryDeliveryStatus(@PathParam("senderAddress") String senderAddress,@PathParam("requestId") String requestId) {
  		/*
  		GET http://example.com/smsmessaging/v1/outbound/tel%3A%2B12345678/requests/abc123/deliveryInfos HTTP/1.1
  		Accept: application/json
  		 */
          SystemLog.getInstance().getLogger("smscomp_event").info("senderAddress :" + senderAddress);
          SystemLog.getInstance().getLogger("smscomp_event").info("requestId :" + requestId);
          senderAddress=senderAddress.replace("tel:+", "").replace("tel:", "");
          List<SMSSubscription> smsSubscriptions=new SmsService().messageSubscribedAndDelivered(senderAddress, requestId); 
          String deliverInfoList="";
          String address="";
          for (SMSSubscription smsSubscription : smsSubscriptions) {
          	deliverInfoList+= "{ \"address\" : \""+smsSubscription.getResAddress().replaceAll("\\[|\\]", "").replace("\"", "")+"\",\"deliveryStatus\" : \""+smsSubscription.getDeliveryStatus()+"\" }";
          	SystemLog.getInstance().getLogger("smscomp_event").info("deliverInfoList :" + deliverInfoList);
          	address=smsSubscription.getResAddress();
          	SystemLog.getInstance().getLogger("smscomp_event").info("smsSubscription.getSenderAddress() :" + smsSubscription.getSenderAddress());
          	address=address.replaceAll("\\[|\\]", "").replace("tel:+", "").replace("\"", "");
          	SystemLog.getInstance().getLogger("smscomp_event").info("address :" + address);
          }
          String resourceURL = FileUtil.getApplicationProperty("sendSMSResourceURL");
          String ret = "{\"deliveryInfoList\" : {"
  	            		+ "\"deliveryInfo\" : ["+deliverInfoList.replace("}{", "},{")+"],"
  	            		+ "\"resourceURL\": \""+resourceURL.replace("\"", "")+""+address+"/requests/"+requestId+"/deliveryInfos\"}}";
          SystemLog.getInstance().getLogger("smscomp_event").info("response json : "+ ret);
          return ret;
              

  /*
  HTTP/1.1 200 OK
  Content-Type: application/json
  Date: Thu, 04 Jun 2009 02:51:59 GMT

  {"deliveryInfoList" : {
      "deliveryInfo" : [
          { "address" : "tel:+13500000991",
            "deliveryStatus" : "MessageWaiting" },
          { "address" : "tel:+13500000992",
            "deliveryStatus" : "MessageWaiting" },
      ],
      "resourceURL": "http://example.com/smsmessaging/v1/outbound/
          tel%3A%2B12345678/requests/abc123/deliveryInfos"
  }}        
   */
          
      }
        
}
