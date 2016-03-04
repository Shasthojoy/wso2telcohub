package com.dialog.mife.ussd.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.dialog.mife.ussd.dto.*;
import lk.dialog.ideabiz.library.JWT;
import lk.dialog.ideabiz.logger.DirectLogger;
import lk.dialog.ideabiz.logger.model.impl.USSDRequest;
import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidateUssdSend;
import com.dialog.mife.ussd.ctrl.RequestManager;
import com.dialog.mife.ussd.ctrl.UrlProvisionEntity;
import com.dialog.mife.ussd.dto.Vxml.Form;
import com.dialog.mife.ussd.dto.Vxml.Form.Block;
import com.dialog.mife.ussd.dto.Vxml.Form.Field;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled.Assign;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled.Goto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

/**
 * @author Charith_02380
 */
@Path("/")
public class UssdAPI {
    private static final String userDirectory = System.getProperty("user.dir");
    private static final File file = new File("/home/dialog" + "/mediator.log");

    private static Log log = LogFactory.getLog(UssdAPI.class);
    final static Logger logger = Logger.getLogger(UssdAPI.class);
    private RequestManager manager;
    private Properties settings;
    private Gson gson;
    DirectLogger directLogger = null;

    boolean isvalidate = true;

    /**
     *
     */
    public UssdAPI() {
        try {
            gson = new GsonBuilder().serializeNulls().create();

            manager = new RequestManager();
            settings = new Properties();
            settings.loadFromXML(UssdAPI.class.getResourceAsStream("settings.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            directLogger = new DirectLogger();
        } catch (Exception e) {

        }
    }


    @POST
    @Path("v1/outbound/{senderAddress}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response outboundRequest(@Context HttpServletRequest hsr, @PathParam("senderAddress") String senderAddress, @HeaderParam("X-JWT-Assertion") String jwt, String jsonBody) {
        log("outboundRequest");

        Integer applicationId = 0;

        try {
            applicationId = JWT.getApplicationIdInt(jwt);
        } catch (Exception e) {
            log("Cant read JWT Token");
        }
        try {
            if (isvalidate) {
                new ValidateUssdSend().validate(jsonBody);
            }

            OutboundMessage outboundMsg = gson.fromJson(jsonBody, OutboundMessage.class);
            String msisdn = outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", "");
            Application application = null;

            log("USSD NI:" + applicationId + "|" + outboundMsg.getOutboundUSSDMessageRequest().getShortCode() + "|" + outboundMsg.getOutboundUSSDMessageRequest().getKeyword());

            if (outboundMsg.getOutboundUSSDMessageRequest().getKeyword() == null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                application = manager.getUniqueApplication(
                        outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
            } else {
                application = manager.getApplication(
                        outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                        outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
            }


            OutboundRequest request = new OutboundRequest();
            request.setApplication(application);
            request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());

            Long id = manager.saveRequest(request);

            if (application == null) {
                addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "ERROR", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "APP_NOT_FOUND", id);
                throw new Exception("Application does not exist");
            }
            if (applicationId < application.getId().intValue()) {
                log("JWT APP IT NOT MATCH WITH APPLICATION ID:" + applicationId + "-JWT:" + application.getAMId() + "-USSD AM ID");
            }


            log("object exists check");
            NIMsisdn msisdnObj = manager.getMSISDN(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""));
            log("object is retrived");
            if (msisdnObj != null) {
                log("object exists");
                manager.deleteMSISDN(msisdnObj);
            }
            NIMsisdn msisdnObjSave = new NIMsisdn();
            msisdnObjSave.setMSISDN(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""));
            log("saving MSISDN");
            manager.saveMSISDN(msisdnObjSave);
            if (id != null) {
                /**
                 * When an NI USSD is pushed, the gateway calls the registered url for the APP.
                 * Which is ussd/route/{msisdn}
                 * There this key will be used to identify the Application & NI USSD Request
                 * Key format <USSD Action>:<Keyword of the App>:<Request ID>
                 */
                String inputKey =
                        USSDAction.mtinit.toString() + ":" +
                                outboundMsg.getOutboundUSSDMessageRequest().getKeyword() + ":" +
                                id;

                String niURL = settings.getProperty("ni_ussd_url");
                Object[] messageparams = {
                        outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),
                        outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replaceAll("tel:", "").replaceAll("\\+", ""),
                        inputKey};
                niURL = MessageFormat.format(niURL, messageparams);
                HttpGet get = new HttpGet(niURL);

                CloseableHttpClient httpclient = null;
                CloseableHttpResponse response = null;
                try {
                    httpclient = HttpClients.createDefault();/*HttpClients.custom()
                            .setConnectionManager(connectionManager)
			                .build();*/

                    response = httpclient.execute(get);
                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        InputStream instream = entity.getContent();
                        try {
                            StringWriter writer = new StringWriter();
                            IOUtils.copy(new InputStreamReader(instream), writer, 1024);
                            String body = writer.toString();

                            if (body.equals("SENT")) {
                                outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("SENT");
                                addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "", id);
                                return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
                            } else {
                                System.out.println(body);
                                outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
                                addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "NOT_SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "NI_ERROR", id);
                                return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
                            addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "NOT_SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "ERROR", id);
                            return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
                        } finally {
                            instream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "NOT_SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "ERROR", id);
                    outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
                } finally {
                    if (response != null)
                        response.close();
                    httpclient.close();
                }
            } else {
                outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
                /*JAXBContext jc = JAXBContext.newInstance(OutboundMessage.class);
                Marshaller m = jc.createMarshaller();
				m.m*/
                addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "NOT_SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, "EMPTY_ID", id);

                return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
                //TODO
                //Handle error
            }
            addLog("1", applicationId, msisdn, outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), outboundMsg.getOutboundUSSDMessageRequest().getKeyword(), outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), "NOT_SENT", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), null, null, id);
            outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
            return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr = "";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                //retstr = new Gson().toJson(requesterror);
                retstr = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                //retstr = new Gson().toJson(requesterror);
                retstr = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
                return Response.status(400).entity(retstr).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return Response.serverError().build();

            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new com.axiata.dialog.oneapi.validation.ServiceException("SVC0001", "Internal Server Error", ""));
            //String jsonreturn = new Gson().toJson(requesterror);
            String jsonreturn = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
            return Response.status(400).entity(jsonreturn).build();
        }
    }

    /**
     * Default landing page for all USSD Requests, the page displayed will depend on the type of the request
     * If a previous mtinit request, the request will be fetched and pushed to the phone.
     * If a moinit, or a mocont, will be forwarded to the apps notify URL
     * In the latter scenario, the app is identified by the keyword, which what the user will dial after the short code.
     * E.g.: #<short code>*<keyword># -> #1721*123#
     *
     * @return
     */
    //TODO
    //SHOULD BE RENAMED TO route/{msisdn}
    @GET
    @Path("route/{msisdn}")
    @Produces("application/xml")
    public Response ussdRoute(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @QueryParam("resp") String resp, @QueryParam("reqID") String reqID) {
        log("ussdRoute");
        try {
            String pattern = settings.getProperty("ussd_ni_match");
            String callbackURI = settings.getProperty("ussd_cont");
            if (resp.matches(pattern)) {//Is a call from platform for a previous ni ussd
                log("NI USSD MSISDN = " + msisdn);
                NIMsisdn msisdnObj = new NIMsisdn();
                msisdnObj.setMSISDN(msisdn);
                manager.deleteMSISDN(msisdnObj);
                String params[] = resp.split(":");
                OutboundRequest request = manager.getRequest(Long.parseLong(params[2]));


                Object[] messageparams = {
                        request.getOutboundUSSDMessageRequest().getAddress(),
                        String.valueOf(request.getId())};
                callbackURI = MessageFormat.format(callbackURI, messageparams);

                request.getOutboundUSSDMessageRequest().setSessionID(hsr.getSession().getId());
                manager.updateRequest(request);

                String xml = createVXMLResponsePage(
                        request.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(),
                        callbackURI);
                System.out.println("ResponseNormal=" + resp);
                return Response.ok().entity(xml).build();
            } else {
                log("User Response = " + resp);
                log("Request ID = " + reqID);
                String xml = null;
                OutboundRequest request = manager.getRequest(Long.valueOf(reqID));

                InboundMessage inboundMessage = createNotifyURLRequest(reqID, msisdn, resp);
                String jsonObj = gson.toJson(inboundMessage);
                log("Post to notifyURL jsonbody = " + jsonObj);

                Application application = null;


                try {
                    if (request.getOutboundUSSDMessageRequest().getKeyword() == null || request.getOutboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                        application = manager.getUniqueApplication(
                                request.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
                    } else {
                        application = manager.getApplication(
                                request.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                                request.getOutboundUSSDMessageRequest().getKeyword());
                    }
                } catch (Exception e) {

                }
                Integer appId = 0;
                if (application != null) {
                    appId = application.getAMId().intValue();
                }

                addLog("1", appId, msisdn, inboundMessage.getInboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), inboundMessage.getInboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "SUCCESS", "INBOUND", inboundMessage.getInboundUSSDMessageRequest().getInboundUSSDMessage(), null, "", Long.valueOf(reqID));

                OutboundMessage outboundmessage = postToNotifyURL(jsonObj, request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL());

                if (outboundmessage != null) {
                    addLog("1", appId, outboundmessage.getOutboundUSSDMessageRequest().getAddress(), outboundmessage.getOutboundUSSDMessageRequest().getShortCode(), outboundmessage.getOutboundUSSDMessageRequest().getKeyword(), outboundmessage.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundmessage.getOutboundUSSDMessageRequest().getUssdAction().toString(), outboundmessage.getOutboundUSSDMessageRequest().getSessionID(), "SUCCESS", "OUTBOUND", outboundmessage.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "SUCCESS", "", Long.valueOf(reqID));
                } else {
                    addLog("1", appId, inboundMessage.getInboundUSSDMessageRequest().getAddress(), inboundMessage.getInboundUSSDMessageRequest().getShortCode(), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), inboundMessage.getInboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "ERROR", "OUTBOUND", inboundMessage.getInboundUSSDMessageRequest().getInboundUSSDMessage(), "FAILED", request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL(), Long.valueOf(reqID));
                }

                if (outboundmessage.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                    xml = createVXMLFinalPage(outboundmessage.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
                } else {
                    Object[] messageparams = {
                            request.getOutboundUSSDMessageRequest().getAddress(),
                            String.valueOf(request.getId())};
                    callbackURI = MessageFormat.format(callbackURI, messageparams);
                    xml = createVXMLResponsePage(
                            outboundmessage.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(),
                            callbackURI);
                    System.out.println("Response=" + resp);
                }
                return Response.ok().entity(xml).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    //TODO
    //SHOULD BE RENAMED TO default/{msisdn}
    @GET
    @Path("mo-init/{port}/{msisdn}")
    @Produces("application/xml")
    public Response ussdDefault(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @PathParam("port") String port, @QueryParam("Keyword") String keyword) {
        log("ussdDefault");
        log(port + ":" + msisdn);
        Long id = null;
        Application application = null;
        Integer AMID = 0;

        try {
            String callbackURI = settings.getProperty("ussd_callback");
            NIMsisdn msisdnObj = manager.getMSISDN(msisdn);
            String vxml = null;

            if (msisdnObj != null) {
                Object[] messageparams = {msisdn, String.valueOf(System.currentTimeMillis())};
                callbackURI = MessageFormat.format(callbackURI, messageparams);
                log("MSISDN is not null");
                manager.deleteMSISDN(msisdnObj);
                vxml = createVXMLResponsePage("Ideabiz", callbackURI);
            } else {
                log("MSISDN is null - Mobile originated |" + port + "|" + keyword + "|" + msisdn);

                log("Short code from settings:" + settings.getProperty("shortcode"));

                //String provisionedNotifyUrl = manager.getNotifyUrlByShortCode(settings.getProperty("shortcode"));
                String provisionedNotifyUrl = null;

                if (keyword != null && keyword.length() > 0 && !keyword.isEmpty()) {
                    provisionedNotifyUrl = manager.getNotifyUrlByShortCode(port);
                    application = manager.getUniqueApplication(port);
                } else {
                    try {
                        provisionedNotifyUrl = manager.getNotifyUrlByKeyword(port, keyword);
                        application = manager.getApplication(port, keyword);

                    } catch (Exception ex) {
                        log("No  port:keyword individual URL : try to sending to wildcard");
                    }

                    if (provisionedNotifyUrl == null) {
                        provisionedNotifyUrl = manager.getNotifyUrlByKeyword(port, "*");
                        if (application == null)
                            application
                                    = manager.getApplication(port, "*");

                    }
                }

                log("provisioned URL = " + provisionedNotifyUrl);

                //String notifyUrl = "http://10.62.96.187:9764/mavenproject1-1.0-SNAPSHOT/webresources/endpoint/ussd/init" +"?msisdn="+ msisdn;

//                String notifyUrl = provisionedNotifyUrl + "?msisdn=" + msisdn;
//
//                log("notifyURL = " + notifyUrl);

//                OutboundMessage outboundMsg = notifyApp(notifyUrl, msisdn);

                String session = null;
                try {
                    session = hsr.getSession().getId();
                } catch (Exception e) {
                }


                OutboundMessage outboundMsg = initNotify(msisdn, port, keyword, provisionedNotifyUrl, session);


         /*       Application application = null;
                if (outboundMsg.getOutboundUSSDMessageRequest().getKeyword() == null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                    application = manager.getUniqueApplication(
                            outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
                } else {
                    application = manager.getApplication(
                            outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                            outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
                }*/
                if (application != null && application.getAMId() != null)
                    AMID = application.getAMId().intValue();

                if (outboundMsg == null) {
                    addLog("1", AMID, msisdn, port, keyword, "", "mo-init", session, "SUCCESS", "INBOUND", keyword, "SUCCESS", "", id);
                    addLog("1", AMID, msisdn, port, keyword, "", "mo-init", session, "ERROR", "OUTBOUND", "", "HTTP", provisionedNotifyUrl, id);
                }

                OutboundRequest request = new OutboundRequest();
                request.setApplication(application);
                request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
                id = manager.saveRequest(request);

                if (outboundMsg != null) {
                    addLog("1", AMID, msisdn, port, keyword, "", "mo-init", session, "SUCCESS", "INBOUND", keyword, "SUCCESS", "", id);
                    addLog("1", AMID, msisdn, port, keyword, outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), session, "SUCCESS", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "SUCCESS", "", id);
                }

                String reqID = id.toString();
                Object[] messageparams = {msisdn, reqID};
                callbackURI = MessageFormat.format(callbackURI, messageparams);
                log("Callback URL: " + callbackURI);
                String message = "";
                message = request.getOutboundUSSDMessageRequest().getOutboundUSSDMessage();
                vxml = createVXMLResponsePage(message, callbackURI);
            }
            return Response.ok().entity(vxml).build();
        } catch (Exception e) {
            e.printStackTrace();
            addLog("1", AMID, msisdn, port, keyword, "", "mo-init", "", "ERROR", "OUTBOUND", "", "EXCEPTION", "", id);

            return Response.serverError().build();
        }
    }

    @GET
    @Path("mt-cont/{msisdn}")
    @Produces("application/xml")
    public Response mtContUSSD(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @QueryParam("resp") String resp, @QueryParam("reqID") String reqID) {
        log("mtContUSSD:" + msisdn + ":" + resp + reqID);
        Application app = null;
        Integer AMID = 0;


        try {
            OutboundRequest request = manager.getRequest(Long.valueOf(reqID));


            InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
            inboundUSSDMessageRequest.setAddress("tel:+" + msisdn);
            inboundUSSDMessageRequest.setSessionID(request.getOutboundUSSDMessageRequest().getSessionID());
            inboundUSSDMessageRequest.setClientCorrelator(request.getOutboundUSSDMessageRequest().getClientCorrelator());
            inboundUSSDMessageRequest.setInboundUSSDMessage(resp);
            inboundUSSDMessageRequest.setKeyword(request.getOutboundUSSDMessageRequest().getKeyword());
            inboundUSSDMessageRequest.setShortCode(request.getOutboundUSSDMessageRequest().getShortCode());
            inboundUSSDMessageRequest.setResponseRequest(request.getOutboundUSSDMessageRequest().getResponseRequest());
            inboundUSSDMessageRequest.setUssdAction(USSDAction.mtcont);

            InboundMessage inboundMessage = new InboundMessage();
            inboundMessage.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);


            if (inboundMessage.getInboundUSSDMessageRequest().getKeyword() == null || inboundMessage.getInboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                app = manager.getUniqueApplication(
                        inboundMessage.getInboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
            } else {
                app = manager.getApplication(
                        inboundMessage.getInboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                        inboundMessage.getInboundUSSDMessageRequest().getKeyword());

                if (app == null) {
                    app = manager.getApplication(
                            inboundMessage.getInboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), "*");
                }
            }


            if (app != null && app.getAMId() != null)
                AMID = app.getAMId().intValue();

            addLog("1", AMID, inboundMessage.getInboundUSSDMessageRequest().getAddress(), inboundMessage.getInboundUSSDMessageRequest().getShortCode(), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), inboundMessage.getInboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "SUCCESS", "INBOUND", inboundMessage.getInboundUSSDMessageRequest().getInboundUSSDMessage(), "SUCCESS", "", Long.valueOf(reqID));


            if (app == null) {
                throw new Exception("Application does not exist");
            }

            HttpPost post = new HttpPost(request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL());
            //post.setConfig(requestConfig);

            String jsonObj = gson.toJson(inboundMessage);

            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;
            try {
                httpclient = HttpClients.createDefault();/*HttpClients.custom()
                        .setConnectionManager(connectionManager)
		                .build();*/

                StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
                strEntity.setContentType("application/json");
                post.setEntity(strEntity);

                response = httpclient.execute(post);
                HttpEntity entity = response.getEntity();

                String xml = createVXMLFinalPage("Thank You");

                if (entity != null) {
                    InputStream instream = entity.getContent();
                    try {
                        StringWriter writer = new StringWriter();
                        IOUtils.copy(new InputStreamReader(instream), writer, 1024);
                        String body = writer.toString();
                        OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class);

                        addLog("1", AMID, inboundMessage.getInboundUSSDMessageRequest().getAddress(), inboundMessage.getInboundUSSDMessageRequest().getShortCode(), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), outboundMsg.getOutboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "SUCCESS", "OUTBOUND", outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "SUCCESS", "", Long.valueOf(reqID));
                        if (outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtcont) {
                            OutboundRequest outBoundRequest = new OutboundRequest();
                            outBoundRequest.setApplication(app);
                            outBoundRequest.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());

                            Long id = manager.saveRequest(outBoundRequest);

                            if (id != null) {
                                String callbackURI = settings.getProperty("ussd_cont");
                                Object[] messageparams = {
                                        request.getOutboundUSSDMessageRequest().getAddress(),
                                        String.valueOf(request.getId())};
                                callbackURI = MessageFormat.format(callbackURI, messageparams);
                                xml = createVXMLResponsePage(outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), callbackURI);
                            }
                        }
                        if (outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                            xml = createVXMLFinalPage(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        addLog("1", AMID, inboundMessage.getInboundUSSDMessageRequest().getAddress(), inboundMessage.getInboundUSSDMessageRequest().getShortCode(), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), inboundMessage.getInboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "ERROR", "OUTBOUND", "", "EXCEPTION", request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL(), Long.valueOf(reqID));

                    }
                }
                return Response.ok().entity(xml).build();
            } catch (Exception e) {
                addLog("1", AMID, inboundMessage.getInboundUSSDMessageRequest().getAddress(), inboundMessage.getInboundUSSDMessageRequest().getShortCode(), inboundMessage.getInboundUSSDMessageRequest().getKeyword(), inboundMessage.getInboundUSSDMessageRequest().getClientCorrelator(), inboundMessage.getInboundUSSDMessageRequest().getUssdAction().toString(), inboundMessage.getInboundUSSDMessageRequest().getSessionID(), "ERROR", "OUTBOUND", "", "EXCEPTION", request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL(), Long.valueOf(reqID));
                e.printStackTrace();
                return Response.serverError().build();
            } finally {
                response.close();
                httpclient.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    private OutboundMessage initNotify(String msisdn, String port, String keyword, String url, String sessionId) {

        if (msisdn != null && !msisdn.startsWith("94"))
            msisdn = "94" + msisdn;
        InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
        inboundUSSDMessageRequest.setAddress("tel:+" + msisdn);
        inboundUSSDMessageRequest.setSessionID(sessionId);
        inboundUSSDMessageRequest.setClientCorrelator(null);
        inboundUSSDMessageRequest.setInboundUSSDMessage(null);
        inboundUSSDMessageRequest.setKeyword(keyword);
        inboundUSSDMessageRequest.setShortCode(port);
        inboundUSSDMessageRequest.setResponseRequest(new ResponseRequest());
        inboundUSSDMessageRequest.setUssdAction(USSDAction.moinit);
        InboundMessage inboundMessage = new InboundMessage();
        inboundMessage.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);
        String jsonObj = gson.toJson(inboundMessage);

        return postToNotifyURL(jsonObj, url);

    }

    private InboundMessage createNotifyURLRequest(String reqID, String msisdn, String resp) {
        OutboundRequest request = manager.getRequest(Long.valueOf(reqID));
        InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
        inboundUSSDMessageRequest.setAddress("tel:+" + msisdn);
        inboundUSSDMessageRequest.setSessionID(request.getOutboundUSSDMessageRequest().getSessionID());
        inboundUSSDMessageRequest.setClientCorrelator(request.getOutboundUSSDMessageRequest().getClientCorrelator());
        inboundUSSDMessageRequest.setInboundUSSDMessage(resp);
        inboundUSSDMessageRequest.setKeyword(request.getOutboundUSSDMessageRequest().getKeyword());
        inboundUSSDMessageRequest.setShortCode(request.getOutboundUSSDMessageRequest().getShortCode());
        inboundUSSDMessageRequest.setResponseRequest(request.getOutboundUSSDMessageRequest().getResponseRequest());
        inboundUSSDMessageRequest.setUssdAction(USSDAction.mtcont);
        InboundMessage inboundMessage = new InboundMessage();
        inboundMessage.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);


        return inboundMessage;
    }

    private OutboundMessage postToNotifyURL(String jsonObj, String notifyURL) {
        try {
            HttpPost post = new HttpPost(notifyURL);

            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;

            httpclient = HttpClients.createDefault();/*HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build();*/

            StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
            strEntity.setContentType("application/json");
            post.setEntity(strEntity);

            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            StringWriter writer = new StringWriter();
            IOUtils.copy(new InputStreamReader(instream), writer, 1024);
            String body = writer.toString();
            log("Return from notifyURL = " + body);

            OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class);
            return outboundMsg;

        } catch (IOException ex) {
            log(ex.getMessage());
            return null;
        }

    }

    private OutboundMessage notifyApp(String notifyURL, String msisdn) throws IOException {

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        httpclient = HttpClients.createDefault();
        String body = null;
        //notify URL should be retrived from APP
        // String appNotifyURL = "" + msisdn;

        HttpGet get = new HttpGet(notifyURL);

        try {

            response = httpclient.execute(get);

            HttpEntity entity = response.getEntity();


            if (entity != null) {
                InputStream instream = entity.getContent();

                StringWriter writer = new StringWriter();

                IOUtils.copy(new InputStreamReader(instream), writer, 1024);
                body = writer.toString();
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        }

        log("Json Body from APP = " + body);
        OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class);


        return outboundMsg;
    }

    private static void log(String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            out.println(text);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * @param prompt
     * @param callbackURI
     * @return
     */
    private String createVXMLResponsePage(String prompt, String callbackURI) throws JAXBException, PropertyException {
        return createVXMLPage(prompt, callbackURI, "vResponse", "oc_message", "#responseMsg");
    }

    /**
     * @param prompt
     * @return
     * @throws JAXBException
     * @throws PropertyException
     */
    private String createVXMLFinalPage(String prompt) throws JAXBException, PropertyException {
        /*VXMLFactory factory = new VXMLFactory();
        Vxml vxml = factory.createVxml();
		
		//<field name="oc_final">
		//	<prompt>END</prompt>
		//</field>
		Field field = factory.createVxmlFormField();
		field.setName("oc_final");
		field.setPrompt(prompt);
		
		//<filled>
		//	<assign name="" expr="oc_final"/>
		//	<goto next=""/>
		//</filled>
		Assign assign = factory.createVxmlFormFilledAssign();
		assign.setName("");
		assign.setExpr("oc_final");
		Goto gotoElem = factory.createVxmlFormFilledGoto();
		gotoElem.setNext("");			
		Filled filled = factory.createVxmlFormFilled();
		filled.setAssign(assign);
		filled.setGoto(gotoElem);
		
		//<form id="final" name="Final Form">
		//	<field/>
		//	<filled/>
		//</form>
		Form form1 = factory.createVxmlForm();
		form1.setId("final");
		form1.setName("Final Form");
		form1.setField(field);
		form1.setFilled(filled);

		List<Form> formList = vxml.getForm();
		formList.add(0, form1);

		JAXBContext context = JAXBContext.newInstance(Vxml.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

		StringWriter writer = new StringWriter();
		m.marshal(vxml, writer);

		String xml = writer.toString();*/
        /**
         * This is a dirty hack
         * FIX this properly
         */
        String xml =
                "<vxml>" +
                        "	<form id=\"Menu Item1\" name=\"Menu Item1 ID\">" +
                        "		<field name=\"var1\">" +
                        "			<prompt><![CDATA[" + prompt + "]]></prompt>" +
                        "		</field>" +
                        "		<property name=\"oc_bIsFinal\" value=\"1\"/>" +
                        "	</form>" +
                        "</vxml>";

        return xml;
    }

    /**
     * @param prompt
     * @param callbackURI
     * @return
     * @throws JAXBException
     * @throws PropertyException
     */
    private String createVXMLPage(String prompt, String callbackURI, String filledName, String filledExpr, String gotoURL) throws JAXBException, PropertyException {
        VXMLFactory factory = new VXMLFactory();
        Vxml vxml = factory.createVxml();

        //<field name="oc_message">
        //	<prompt>Hello 94777335365!\n1. Continue\n2. Exit</prompt>
        //</field>
        Field field = factory.createVxmlFormField();
        field.setName("oc_message");
        field.setPrompt(prompt);

        //<filled>
        //	<assign name="vResponse" expr="oc_message"/>
        //	<goto next="#responseMsg"/>
        //</filled>
        Assign assign = factory.createVxmlFormFilledAssign();
        assign.setName(filledName);// "vResponse");
        assign.setExpr(filledExpr);// "oc_message");
        Goto gotoElem = factory.createVxmlFormFilledGoto();
        gotoElem.setNext(gotoURL);// "#responseMsg");
        Filled filled = factory.createVxmlFormFilled();
        filled.setAssign(assign);
        filled.setGoto(gotoElem);

        //<form id="MenuID Here" name="Menu Name Here">
        //	<field/>
        //	<filled/>
        //</form>
        Form form1 = factory.createVxmlForm();
        form1.setId("MainMenu");
        form1.setName("Main Menu");
        form1.setField(field);
        form1.setFilled(filled);

        //<block name="oc_ActionUrl">
        //	<goto next="http://172.22.163.88:8080/ussd/route/{0}?resp=%vResponse%&reqID=%vReqID%" />
        //</block>
        com.dialog.mife.ussd.dto.Vxml.Form.Block.Goto blockGoto = factory.createVxmlFormBlockGoto();
        blockGoto.setNext(callbackURI);
        Block block = factory.createVxmlFormBlock();
        block.setName("oc_ActionUrl");
        block.setGoto(blockGoto);

        //<form id="responseMsg" name="responseMsg">
        //<block/>
        //</form>
        Form form2 = factory.createVxmlForm();
        form2.setId("responseMsg");
        form2.setName("responseMsg");
        form2.setBlock(block);

        List<Form> formList = vxml.getForm();
        formList.add(0, form1);
        formList.add(1, form2);

        JAXBContext context = JAXBContext.newInstance(Vxml.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

        StringWriter writer = new StringWriter();
        m.marshal(vxml, writer);

        String xml = writer.toString();
        return xml;
    }

    @POST
    @Path("routeNotifyUrl/{routeNotifyUrl}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response provisionNotifyUrl(@Context HttpServletRequest servletRequest, @PathParam("routeNotifyUrl") String routeNotifyUrl) {
        try {
            UrlProvisionEntity urlProvisionEntity = gson.fromJson(routeNotifyUrl, UrlProvisionEntity.class);
            String provisionNotifyUrl = manager.provisionNotifyUrl(urlProvisionEntity.getApplication().getId());
            String jsonObj = gson.toJson(provisionNotifyUrl);
            return Response.ok().entity(jsonObj).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    public void addLog(String version, Integer appid, String address, String shortCode, String keyword, String clientCorrelator, String ussdAction, String sessionId, String status, String direction, String message, String applicationStatus, String description, Long requestId) {
        directLogger.addLog(new USSDRequest(version, appid, address, shortCode, keyword, clientCorrelator, ussdAction, sessionId, status, direction, message, applicationStatus, description, requestId));
    }
}
