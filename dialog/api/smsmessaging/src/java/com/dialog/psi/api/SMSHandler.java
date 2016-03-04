package com.dialog.psi.api;

import ie.omk.smpp.Address;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.tlv.Tag;

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

import com.dialog.psi.api.entity.SMSSubscription;
import com.dialog.psi.api.exception.RequestError;
import com.dialog.psi.api.exception.ServiceException;
import com.dialog.psi.api.responsebean.sms.ISMSresponse;
import com.dialog.psi.api.responsebean.sms.SMSRequest;
import com.dialog.psi.api.responsebean.sms.SendSMSRequest;
import com.dialog.psi.api.responsebean.sms.StartSubscription;
import com.dialog.psi.api.responsebean.sms.SubscribeRequest;
import com.dialog.psi.api.service.SmsService;
import com.dialog.psi.util.FileUtil;
import com.dialog.psi.util.NumberGenerator;
import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/")
public class SMSHandler {

    //private static final Logger log = Logger.getLogger(SMSHandler.class.getName());
    
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
    public Response sendSMS(@PathParam("senderAddress") String senderAddress, String jsonBody) {

        SystemLog.getInstance().getLogger("smscomp_event").info("send sms :" + jsonBody);
        boolean isRegistered = false;

        Gson gson = new GsonBuilder().serializeNulls().create();

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

        Gson gsonResp = new Gson();
        String resAddress = gsonResp.toJson(sendReq.getOutboundSMSMessageRequest().getAddress());


        int msgNum = NumberGenerator.next();

        String msgId = " ";

        //Send sms
        try {
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

			
			//PATCH for SMPP
			if (sender.startsWith("tel:")){
				sender = sender.split(":")[1];
				sender.replace("+","").trim();
			}
			//PATCH for SMPP
			
            for (String to : list) {
                to = to.replace("tel:+", "").trim();

                //Unique Message ID
                msgId = senderAddress + "" + to + "" + msgNum;

                if (to.length() >= 9 && to.startsWith("9477")) {
                    if (portList.contains(sender)) {
                        sendMessage(sender, to, message, false, msgId, msgNum, isRegistered);
                    } else {
                        String portSend = FileUtil.getApplicationProperty("smpp_port_1");
                        portSend = portSend.substring(1, portSend.length() - 1);
                        sendMessage(portSend, to, message, false, msgId, msgNum, isRegistered);
                    }
                }

            }

        } catch (Exception e) {
            //e.printStackTrace();
            SystemLog.getInstance().getLogger("smscomp_event").error("[SMSHandler ],sendSMS, " + e.getMessage());
            return Response.status(400).entity("{Internal Server Error}").build();
        }

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
        	callbackData=sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
        	
        	String senderMsisdn=senderAddress.replace("tel:+", "");
        	
        	//String response=new SmsService().updateSmppSubscription(smscRefNumber,deliveryStatus,resAddress,callbackData,senderMsisdn,clientCorrelator);
        	String response=new SmsService().insertSmppSubscriptionIfExists(smscRefNumber,deliveryStatus,resAddress,callbackData,senderMsisdn,clientCorrelator);
				/*
					smsc_ref_number,deliveryStatus,address,callbackData,senderAddress,clientCorrelator
				 */
        	
            jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                    + "\"address\":" + resAddress + ","
                    + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "/deliveryInfos" + "\"" + "}" + ","
                    + "\"senderAddress\":\"" + sender + "\","
                    + "\"outboundSMSTextMessage\":\"" + message + "\","
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
                    + "\"outboundSMSTextMessage\":\"" + message + "\","
                    + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                    + "\"senderName\":\"" + senderName + "\","
                    + "\"resourceURL\":\"" + locationHeader + "\""
                    + "}}";
        }
        return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(jsonPayload).build();
    }

    //ideamart
    @POST
    @Path("ideamart")
    @Consumes("application/json")
    @Produces("application/json")
    public String ideamartSend(String jsonBody) {

        SystemLog.getInstance().getLogger("smscomp_event").info("ideamart :" + jsonBody);

        Gson gson = new GsonBuilder().serializeNulls().create();
        SMSRequest smsReq = gson.fromJson(jsonBody, SMSRequest.class);

        int msgNum = NumberGenerator.next();
        if (new SmsService().ideaMartSend(smsReq, msgNum)) {

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
    public String registrations(@QueryParam("maxBatchSize") String maxBatchSize,
            @PathParam("registrationId") String registrationId, @HeaderParam("authorization") String authorization) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        SystemLog.getInstance().getLogger("smscomp_event").info("retrive message :" + registrationId + maxBatchSize);

        try {
            ISMSresponse inboundresp = new SmsService().retriveSMS(registrationId, maxBatchSize);

            jsonreturn = new Gson().toJson(inboundresp);

            if (inboundresp instanceof RequestError) {
                RequestError requesterror = (RequestError) inboundresp;
                return jsonreturn;
            }

        } catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[Inbound Request], Error + " + "registrations " + e.getMessage());
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return jsonreturn;
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        return jsonreturn;
    }

    @POST
    @Path("inbound/subscriptions")
    @Consumes("application/json")
    @Produces("application/json")
    public Response subscriptions(@HeaderParam("authorization") String authorization, String jsonBody) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        //System.out.println("registration :" + registrationId);

        try {

            Gson gson = new GsonBuilder().serializeNulls().create();
            SubscribeRequest subsrequst = gson.fromJson(jsonBody, SubscribeRequest.class);

            ISMSresponse notifyresp = new SmsService().subscribeToNotify(subsrequst, subsrequst.getSubscription().getDestinationAddress().replace("tel:+", "").trim());

            jsonreturn = new Gson().toJson(notifyresp);

            if (notifyresp instanceof RequestError) {
                RequestError requesterror = (RequestError) notifyresp;

                //return jsonreturn;
                return Response.status(400).entity(jsonreturn).build();
            }

        } catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[Inbound Request], Error + " + "subscriptions " + e.getMessage());
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            //return jsonreturn;
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

            ISMSresponse notifyresp = new SmsService().removeSubsNotify(subsid);

            jsonreturn = new Gson().toJson(notifyresp);

            if (notifyresp instanceof RequestError) {
                RequestError requesterror = (RequestError) notifyresp;

                //return jsonreturn;
                return Response.status(400).entity(jsonreturn).build();
            }

        } catch (Exception e) {
            SystemLog.getInstance().getLogger("smscomp_event").error("[Inbound Request], Error + " + "removeSubscriptions " + e.getMessage());
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            //return jsonreturn;
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

    public void sendMessage(String port, String phoneNo, String msg, boolean highPriority, String msgId, int msgRefNum, boolean isRegistered) {
        ConnectionPool pool = SMSCPoolManager.getInstance().getConnectionPool(port.toUpperCase());
        if (msg.length() > 160) {
            ArrayList<String> msgs = splitMessage(msg, 150);
            for (int i = 0; i < msgs.size(); i++) {
                SubmitSM sm = new SubmitSM();
                sm.setSource(new Address(pool.getSrcTon(), pool.getSrcNpi(), port));
                sm.setDestination(new Address(pool.getTon(), pool.getNpi(), phoneNo));

                if (isRegistered == true) {
                    sm.setRegistered((byte) 1);
                } else {
                    sm.setRegistered((byte) 0);
                }
                sm.setMessageText(msgs.get(i));
                //Set Message ID
                sm.setSequenceNum(Integer.valueOf(msgId));
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

            sm.setMessageText(msg);
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
	
/*		POST http://example.com/smsmessaging/v1/outbound/tel%3A%2B12345678/subscriptions 
 * 			HTTP/1.1 Host: example.com:80
			Content-Type: application/json
			Accept: application/json

			{"deliveryReceiptSubscription": {
			    "callbackReference": {
			        "callbackData": "12345",
			        "notifyURL": "http://application.example.com/notifications/DeliveryInfoNotification"
			    },
			    "filterCriteria": "GIGPICS"
			}}
*/		String response="";
		senderAddress=senderAddress.replace("tel:+", "");
		String jsonreturn="";
		String result="0";
		String locationHeader;
	     try {
	        Gson gson = new GsonBuilder().serializeNulls().create();
	        StartSubscription subscription = gson.fromJson(jsonBody, StartSubscription.class);
	        Integer smscRefNumber=0;
	        String deliveryStatus="None";
	        String resAddress="None";
	        String clientCorrelator="None";
	        result=new SmsService().insertSmppSubscription(smscRefNumber,deliveryStatus,resAddress,subscription.getDeliveryReportSubscription().getCallbackReference().getCallbackData(),senderAddress,clientCorrelator,subscription.getDeliveryReportSubscription().getCallbackReference().getNotifyURL());

	        locationHeader= FileUtil.getApplicationProperty("startSubscriptionResourceURL").replace("\"", "")+"sub"+result;
	        
	        response="{\"deliveryReceiptSubscription\": {"
			        		+ "\"callbackReference\": {"
			        		+ "\"callbackData\":  \""+subscription.getDeliveryReportSubscription().getCallbackReference().getCallbackData()+"\","
			        		+ "\"notifyURL\": \""+subscription.getDeliveryReportSubscription().getCallbackReference().getNotifyURL()+"\"},"
			        		+ "\"filterCriteria\": \""+ subscription.getDeliveryReportSubscription().getFilterCriteria() +"\"," 
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

/*	     HTTP/1.1 201 Created
	     Content-Type: application/json
	     Location: http://example.com/smsmessaging/v1/outbound/subscriptions/sub789
	     Date: Thu, 04 Jun 2009 02:51:59 GMT
	     {"deliveryReceiptSubscription": {
	         "callbackReference": {
	             "callbackData": "12345",
	             "notifyURL": "http://application.example.com/notifications/DeliveryInfoNotification"},    
	             "filterCriteria": "GIGPICS",
	         "resourceURL": "http://example.com/smsmessaging/v1/
	     outbound/subscriptions/sub789 "}}	     
*/	    
	    return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(response).build();
	    //return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(jsonPayload).build();
	}
	
	
	
	
    @GET
    @Path("outbound/{senderAddress}/requests/{requestId}/deliveryInfos")
    @Consumes("application/json")
    @Produces("application/json")
    public String queryDeliveryStatus(@PathParam("senderAddress") String senderAddress,@PathParam("requestId") Integer requestId) {
		/*
		GET http://example.com/smsmessaging/v1/outbound/tel%3A%2B12345678/requests/abc123/deliveryInfos HTTP/1.1
		Accept: application/json
		 */
        SystemLog.getInstance().getLogger("smscomp_event").info("senderAddress :" + senderAddress);
        SystemLog.getInstance().getLogger("smscomp_event").info("requestId :" + requestId);

        List<SMSSubscription> smsSubscriptions=new SmsService().messageSubscribedAndDelivered(senderAddress, requestId); 
        String deliverInfoList="";
        String address="";
        for (SMSSubscription smsSubscription : smsSubscriptions) {
        	deliverInfoList+= "{ \"address\" : \""+smsSubscription.getSenderAddress()+"\",\"deliveryStatus\" : \""+smsSubscription.getDeliveryStatus()+"\" }";
        	address=smsSubscription.getResAddress();
        	address=address.replaceAll("\\[|\\]", "").replace("tel:+", "").replace("\"", "");
        }
        String resourceURL = FileUtil.getApplicationProperty("sendSMSResourceURL");
        String ret = "{\"deliveryInfoList\" : {"
	            		+ "\"deliveryInfo\" : ["+deliverInfoList.replace("}{", "},{")+"],"
	            		+ "\"resourceURL\": \""+resourceURL.replace("\"", "")+""+address+"/requests/abc123/deliveryInfos\"}}";
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