package com.aircel.ussd.api;

import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateRetrieveSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateSubscription;

import java.util.ArrayList;
import java.util.List;

import ie.omk.smpp.Address;
import ie.omk.smpp.message.SubmitSM;
import ie.omk.smpp.message.tlv.Tag;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;

import com.aircel.ussd.api.responsebean.sms.SMSRequest;
import com.aircel.ussd.api.responsebean.sms.ISMSresponse;
import com.aircel.ussd.api.responsebean.sms.SendSMSRequest;
import com.aircel.ussd.api.responsebean.sms.StartSubscription;
import com.aircel.ussd.api.responsebean.sms.SubscribeRequest;
import com.aircel.ussd.api.responsebean.ussd.Application;
import com.aircel.ussd.api.responsebean.ussd.InboundMessage;
import com.aircel.ussd.api.responsebean.ussd.InboundUSSDMessageRequest;
import com.aircel.ussd.api.responsebean.ussd.OutboundMessage;
import com.aircel.ussd.api.responsebean.ussd.OutboundRequest;
import com.aircel.ussd.api.responsebean.ussd.USSDAction;

import com.aircel.ussd.api.service.SmsService;
import com.aircel.ussd.util.FileUtil;
import com.aircel.ussd.util.NumberGenerator;
import com.dialog.rnd.iris.smpp.core.ConnectionPool;
import com.dialog.rnd.iris.smpp.main.SMSCPoolManager;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.aircel.ussd.api.responsebean.ussd.ServiceProvider;
import com.aircel.ussd.api.service.RequestManager;
import java.util.Properties;
import java.util.Random;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

@Path("/")
public class USSDHandler {

    //private static final Logger log = Logger.getLogger(USSDHandler.class.getName());
    boolean isvalidate = false;
    @Context
    private UriInfo context;
    private Gson gson;
    private RequestManager manager;

    public USSDHandler() {
        try {
            gson = new GsonBuilder().serializeNulls().create();
            manager = new RequestManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("v1/outbound/{senderAddress}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response outboundRequest(@PathParam("senderAddress") String senderAddress, String jsonBody) {

        SystemLog.getInstance().getLogger("smscomp_event").info("request ussd :" + jsonBody);
        boolean isRegistered = false;
        String jsonreturn = "";

        try {

            OutboundMessage outboundMsg = gson.fromJson(jsonBody, OutboundMessage.class);
            Application application = null;

            if (outboundMsg.getOutboundUSSDMessageRequest().getKeyword() == null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                application = manager.getUniqueApplication(
                        outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
            } else {
                application = manager.getApplication(
                        outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                        outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
            }
            if (application == null) {
                throw new AxiataException("POL1009", "User has not been provisioned for %1", new String[]{"USSD Request Service\" %1"});
            }

            OutboundRequest request = new OutboundRequest();
            request.setApplication(application);
            String replyAddress = outboundMsg.getOutboundUSSDMessageRequest().getAddress();

            outboundMsg.getOutboundUSSDMessageRequest().setAddress(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replace("tel:", "").replaceAll("\\+", ""));

            if (outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtinit) {
                String UniqueID = application.getShortCode() + System.currentTimeMillis()+"N" + NumberGenerator.getDialogId();
                outboundMsg.getOutboundUSSDMessageRequest().setSessionID(UniqueID);
            }

            request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
            Long id = manager.saveRequest(request);
            
            boolean isEndsession = false;
            if (outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                isEndsession = true;
            }
            
            //workaround for default port
            ServiceProvider sp = application.getServiceProvider();
            String sender = null;
            if ( (sp.getDefaultport() == null) || (sp.getDefaultport().isEmpty()) ) {
                sender = outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", "");
            } else {
                sender = sp.getDefaultport();
            }
            
            sendMessage(sender, outboundMsg.getOutboundUSSDMessageRequest().getAddress(), outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), false, outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), id.intValue(), isRegistered, isEndsession);

            outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("SENT");
            outboundMsg.getOutboundUSSDMessageRequest().setId(null);
            outboundMsg.getOutboundUSSDMessageRequest().getResponseRequest().setId(null);
            outboundMsg.getOutboundUSSDMessageRequest().setAddress(replyAddress);
            jsonreturn = new Gson().toJson(outboundMsg);

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr = "";
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
            SystemLog.getInstance().getLogger("smscomp_event").info("request ussd :" + e.getMessage());
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }

        //LOG.info("Inbound json response: " + jsonreturn);
        //return jsonreturn;
        return Response.status(200).entity(jsonreturn).build();


    }

    @POST
    @Path("v1/inbound/subscriptions")
    @Consumes("application/json")
    @Produces("application/json")
    public Response subscriptions(@HeaderParam("authorization") String authorization, String jsonBody) {

        String jsonreturn = "";
        String tmp = context.getRequestUri().toString();

        try {

            if (isvalidate) {
                new ValidateSubscription().validate(jsonBody);
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            SubscribeRequest subsrequst = gson.fromJson(jsonBody, SubscribeRequest.class);

            ISMSresponse notifyresp = new SmsService().subscribeToNotify(subsrequst, subsrequst.getSubscription().getShortCode().replace("tel:", "").replaceAll("\\+", "").trim());

            jsonreturn = new Gson().toJson(notifyresp);

            if (notifyresp instanceof RequestError) {
                RequestError requesterror = (RequestError) notifyresp;

                //return jsonreturn;
                return Response.status(400).entity(jsonreturn).build();
            }

        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr = "";
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
    @Path("v1/inbound/subscriptions/{subsid}")
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
            String retstr = "";
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
            SystemLog.getInstance().getLogger("smscomp_event").info("send request :" + e.getMessage());
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

    public void sendMessage(String port, String phoneNo, String msg, boolean highPriority, String msgId, int msgRefNum, boolean isRegistered, boolean isEndSession) {

        //replacing send SMS with ideamart way
        //new SmsService().sendMessage(port, phoneNo, msg, highPriority, msgId);
        //return;

        SystemLog.getInstance().getLogger("smscomp_event").info("send message :" + port + ":" + phoneNo);

        ConnectionPool pool = SMSCPoolManager.getInstance().getConnectionPool(port.toUpperCase());
        byte dialogId = Byte.valueOf(msgId.split("N")[1]);
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
                sm.setMessageId(msgId);
                sm.setOptionalParameter(Tag.SAR_TOTAL_SEGMENTS, new Integer(msgs.size()));
                sm.setOptionalParameter(Tag.SAR_SEGMENT_SEQNUM, new Integer(i + 1));
                sm.setOptionalParameter(Tag.SAR_MSG_REF_NUM, msgRefNum);

                //ussd req
                sm.setOptionalParameter(Tag.USSD_SERVICE_OP, new Byte((byte) 0x02));
                if (isEndSession) {
                    sm.setOptionalParameter(Tag.ITS_SESSION_INFO, new byte[]{dialogId, (byte) 0x01});
                } else {
                    sm.setOptionalParameter(Tag.ITS_SESSION_INFO, new byte[]{dialogId, (byte) 0x00});
                }
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
            //sm.setUssdServiceOp((byte) 0x11);
            //request.setItsSessionInfo((short)0);

            sm.setOptionalParameter(Tag.USSD_SERVICE_OP, new byte[]{(byte) 0x02});
            if (isEndSession) {
                sm.setOptionalParameter(Tag.ITS_SESSION_INFO, new byte[]{dialogId, (byte) 0x01});
            } else {
                sm.setOptionalParameter(Tag.ITS_SESSION_INFO, new byte[]{dialogId, (byte) 0x00});
            }
            pool.addMessage(sm, highPriority);
        }
    }    
}
