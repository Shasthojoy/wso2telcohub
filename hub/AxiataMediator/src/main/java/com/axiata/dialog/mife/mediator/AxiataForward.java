package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateNBRetrieveSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateNBSubscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;

import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import com.axiata.dialog.mife.mediator.internal.APICall;
import com.axiata.dialog.mife.mediator.internal.ApiUtils;
import com.axiata.dialog.mife.mediator.internal.ResourceURLUtil;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.engine.AxisEngine;
import org.apache.axis2.util.MessageContextBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import com.axiata.dialog.mife.mediator.entity.sb.SBSubscribeRequest;
import com.axiata.dialog.mife.mediator.entity.InboundRequest;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;


/**
 *
 * @author Charith_02380
 *
 */
public class AxiataForward /*extends AxiataRequestExecutor*/ {

    private static Log log = LogFactory.getLog(AxiataRoute.class);
    private OriginatingCountryCalculatorIDD occi;
    private ResourceURLUtil urlUtil;
    private ApiUtils apiUtil;
    private AxiataDbService dbservice;
    private boolean validate = true;
    private static final String sendError = "http://localhost:18080/MediationTest/tnspoints/enpoint/RequestError/error";
    private static final String sendResponse = "http://localhost:18080/MediationTest/tnspoints/enpoint/Response/dummy";

    /**
     *
     */
   /* public AxiataForward() {
        occi = new OriginatingCountryCalculatorIDD();
        urlUtil = new ResourceURLUtil();
        apiUtil = new ApiUtils();
        dbservice = new AxiataDbService();
    }

    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws AxiataException {

        //<TO-DO> validate the mesasge payload
        return true;
    }

    //@Override
    public boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception {


        //Get valid operators
        String applicationid = getApplicationid();
        String requestid = null;
        
        if (applicationid == null) {
            throw new AxiataException("SVC0001", "",new String[]{"Requested service is not provisioned"});
        }
        validoperators = dbservice.applicationOperators(Integer.valueOf(applicationid));
        if (validoperators.isEmpty()) {
            throw new AxiataException("SVC0001", "",new String[]{"Requested service is not provisioned"});
        }


        JSONObject jsonBody = null;
        String httpMethod = (String) context.getProperty("REST_METHOD");


        String jsonPayloadToString = JsonUtil.jsonPayloadToString(((Axis2MessageContext) context).getAxis2MessageContext());
        jsonBody = new JSONObject(jsonPayloadToString);

        IServiceValidate validator;

        if (apiType.equalsIgnoreCase("sms_inbound_notifications")) {

            requestid = AxiataUID.getUniqueID(AxiataType.ALERTINBOUND.getCode(), context, applicationid);
            
            String axiataid = getResourceUrl().substring(getResourceUrl().lastIndexOf("/") + 1);
            String notifyurl = dbservice.subscriptionNotifi(Integer.valueOf(axiataid));

            //Date Time issue 
            Gson gson = new GsonBuilder().serializeNulls().create();

            InboundRequest inboundRequest = gson.fromJson(jsonBody.toString(), InboundRequest.class);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //get current date time with Date()
            Date date = new Date();

            String currentDate = dateFormat.format(date);

            String formattedDate = currentDate.replace(' ', 'T');

            inboundRequest.getInboundSMSMessageRequest().getInboundSMSMessage().setdateTime(formattedDate);

            String formattedString = gson.toJson(inboundRequest);

            String notifyret = makeRequest(new OperatorEndpoint(new EndpointReference(notifyurl), null), notifyurl, formattedString, true, context);
            
            
            if (notifyret == null) {
                throw new AxiataException("POL0299", "",new String[]{"Error invoking Endpoint"});
            }
            //replyGracefully(null, context);
//                routeToEndPoint(context, sendResponse, "", "", notifyret);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            setResponse(context, new JSONObject(notifyret).toString());
        } else if (apiType.equalsIgnoreCase("retrive_sms_subscriptions")) {

            if (httpMethod.equalsIgnoreCase("POST")) {
                
                requestid = AxiataUID.getUniqueID(AxiataType.RETRIVSUB.getCode(), context,applicationid);
                
                if (validate) {
                    validator = new ValidateNBSubscription();
                    
                    validator.validateUrl((String) context.getProperty("REST_SUB_REQUEST_PATH"));
                    validator.validate(jsonPayloadToString);
                }

                Gson gson = new GsonBuilder().serializeNulls().create();

                JSONObject jsondstaddr = jsonBody.getJSONObject("subscription");
                String addr = (String) jsondstaddr.getJSONArray("destinationAddress").get(0);
                String orgclientcl = jsondstaddr.getString("clientCorrelator");
                
                //if (addr.startsWith("[")) {                
                jsondstaddr.put("destinationAddress", addr.replaceAll("\\[|\\]", ""));
                //}
                SubscribeRequest subsrequst = gson.fromJson(jsonBody.toString(), SubscribeRequest.class);
                String origNotiUrl = subsrequst.getSubscription().getCallbackReference().getNotifyURL();

                String appID = subsrequst.getSubscription().getDestinationAddress();
                List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(apiType, getResourceUrl(), validoperators);

                Integer axiataid = dbservice.subscriptionEntry(subsrequst.getSubscription().getCallbackReference().getNotifyURL());
                Util.getPropertyFile();
                String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
                jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

                //JSONObject clientclr = jsonBody.getJSONObject("outboundSMSMessageRequest");                
                jsondstaddr.put("clientCorrelator", orgclientcl+":"+requestid);
                
                List<Operatorsubs> domainsubs = new ArrayList();
                SubscribeRequest subsresponse = null;
                for (OperatorEndpoint endpoint : endpoints) {

                    String notifyres = makeRequest(endpoint, endpoint.getEndpointref().getAddress(), jsonBody.toString(), true, context);
                    if (notifyres == null) {
                        throw new AxiataException("POL0299","", new String[]{"Error registering subscription"});
                    } else {
                        //plugin exception handling
                        subsresponse = gson.fromJson(notifyres, SubscribeRequest.class);
                        if (subsresponse.getSubscription() == null) {                        
                            handlePluginException(notifyres);
                        }
                        domainsubs.add(new Operatorsubs(endpoint.getOperator(), subsresponse.getSubscription().getResourceURL()));
                    }
                }

                boolean issubs = dbservice.operatorsubsEntry(domainsubs, axiataid);
                //String ResourceUrlPrefix = (String) context.getProperty("REST_URL_PREFIX");
                //modified due to load balancer url issue
                String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");
                subsresponse.getSubscription().setResourceURL(ResourceUrlPrefix + getResourceUrl() + "/" + axiataid);
                //replyGracefully(new JSONObject(subsresponse), context);

                JSONObject replyobj = new JSONObject(subsresponse);
                JSONObject replysubs = replyobj.getJSONObject("subscription");
                String replydest = replysubs.getString("destinationAddress");
                
                //if (!addr.startsWith("[")) {                
                JSONArray arradd = new JSONArray();
                arradd.put(replydest);
                replysubs.put("destinationAddress", arradd);
                replysubs.put("clientCorrelator",orgclientcl);
                
                replysubs.getJSONObject("callbackReference").put("notifyURL", origNotiUrl);
                
                //}                      
                jsondstaddr.put("resourceURL", ResourceUrlPrefix + getResourceUrl() + "/" + axiataid);
                // routeToEndPoint(new OperatorEndpoint(new EndpointReference(sendResponse),null),context, sendResponse, "", "", replyobj.toString());                    
                ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
                setResponse(context, replyobj.toString());

            } else if (httpMethod.equalsIgnoreCase("DELETE")) {
                //before send delete
                String axiataid = getResourceUrl().substring(getResourceUrl().lastIndexOf("/") + 1);
                
                requestid = AxiataUID.getUniqueID(AxiataType.DELRETSUB.getCode(), context,applicationid);
                
                if (validate) {
                    String[] params = {axiataid};
                    validator = new ValidateCancelSubscription();
                    validator.validateUrl((String) context.getProperty("REST_SUB_REQUEST_PATH"));
                    validator.validate(params);
                }
                List<Operatorsubs> domainsubs = (dbservice.subscriptionQuery(Integer.valueOf(axiataid)));
                if (domainsubs.isEmpty()) {
                    throw new AxiataException("POL0001", "",new String[]{"SMS Receipt Subscription Not Found: "+axiataid});
                }
                
                String resStr = "";
                for (Operatorsubs subs : domainsubs) {
                    resStr = makeDeleteRequest(new OperatorEndpoint(new EndpointReference(subs.getDomain()), subs.getOperator()), subs.getDomain(), null, true, context);
                }
                new AxiataDbService().subscriptionDelete(Integer.valueOf(axiataid));

                //JSONObject reply = new JSONObject();
                //reply.put("response", "No content");

                //replyGracefully(reply, context);
                //routeToEndPoint(new OperatorEndpoint(new EndpointReference(sendResponse),null),context, sendResponse, "", "", "");
                ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 204);

            } else {
                     ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
                     throw new Exception("Method not allowed");
            }

        } else if (apiType.equalsIgnoreCase("retrive_sms")) {
            
            if (!httpMethod.equalsIgnoreCase("GET")) {
                     ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
                     throw new Exception("Method not allowed");
            }
            
            String appID = apiUtil.getAppID(context, apiType);
            requestid = AxiataUID.getUniqueID(AxiataType.SMSRETRIVE.getCode(), context,applicationid);

            if (validate) {
//                //get registrationId from URL eg- https://host/smsmessaging/v1/inbound/registrations/{registrationId}/messages?maxBatchSize=2
//                int lastSlash = getResourceUrl().lastIndexOf('/');
//                String registrationId = getResourceUrl().substring(getResourceUrl().lastIndexOf('/', lastSlash-1)+1, lastSlash);
                String[] params = {appID,""};
                validator = new ValidateSBRetrieveSms();
                validator.validateUrl((String) context.getProperty("REST_SUB_REQUEST_PATH"));
                validator.validate(params);
            }

            SOAPBody body = context.getEnvelope().getBody();
            //SOAPHeader soapHeader = context.getEnvelope().getHeader();
            //System.out.println(soapHeader.toString());

            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(
                    apiType, getResourceUrl(), validoperators);
            Collections.shuffle(endpoints);
            int batchSize = 100;
            int perOpCoLimit = batchSize / (endpoints.size());

            JSONArray results = new JSONArray();

            // TODO FIX

            int count = 0;
            // TODO FIX

            ArrayList<String> responses = new ArrayList<String>();
            while (results.length() < batchSize) {
                OperatorEndpoint aEndpoint = endpoints.remove(0);
                endpoints.add(aEndpoint);
                String url = aEndpoint.getEndpointref().getAddress();
                APICall ac = apiUtil.setBatchSize(url, body.toString(),
                        apiType, perOpCoLimit);
                //String url = aEndpoint.getAddress()+getResourceUrl().replace("test_api1/1.0.0/", "");//aEndpoint.getAddress() + ac.getUri();
                JSONObject obj = ac.getBody();
                String retStr = null;
                if (context.isDoingGET()) {
                    retStr = makeGetRequest(aEndpoint, ac.getUri(), null, true, context);
                } else {
                    retStr = makeRequest(aEndpoint, ac.getUri(), obj.toString(), true, context);
                }
                if (retStr == null) {
                    count++;
                    if (count == endpoints.size()) {
                        break;
                    } else {
                        continue;
                    }
                }
                JSONArray resList = apiUtil.getResults(apiType, retStr);
                if (resList != null) {
                    for (int i = 0; i < resList.length(); i++) {
                        results.put(resList.get(i));
                    }
                    responses.add(retStr);
                }

                count++;
                if (count == (endpoints.size() * 2)) {
                    break;
                }
            }

            JSONObject paylodObject = apiUtil.generateResponse(context, apiType, results, responses, requestid);

            String strjsonBody = paylodObject.toString();
            
            setResponse(context, strjsonBody);
//                routeToEndPoint(context, sendResponse, "", "", strjsonBody);

            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");
        }

        return true;

    }

    private void routeToError(MessageContext context, String EndpointRef, String errorcode, String errormsg) {

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            //  headersMap.put("Authorization", getAccessToken());
            headersMap.put("excep", errormsg);
            headersMap.put("excepid", errorcode);
        }
        EndpointReference epr = new EndpointReference(EndpointRef);

        // Setting the end point of the message context
        context.setTo(epr);
        context.setProperty("REST_URL_POSTFIX", "");


        log.info("Message routed to new end point ::" + epr.getAddress());
//        }
    }

    private void routeToEndPoint(OperatorEndpoint operatorendpoint, MessageContext context, String EndpointRef, String errorcode, String errormsg, String Json) throws Exception {

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            headersMap.put("Authorization", "Bearer " + getAccessToken(operatorendpoint.getOperator()));
            if (Json.equals("error")) {
                headersMap.put("excep", errormsg);
                headersMap.put("excepid", errorcode);
            } else {
                headersMap.put("jsonData", Json);
            }
        }
        EndpointReference epr = new EndpointReference(EndpointRef);

        // Setting the end point of the message context
        context.setTo(epr);
        context.setProperty("REST_URL_POSTFIX", "");


        log.info("Message routed to new end point ::" + epr.getAddress());
    }

//    private void setResponse(MessageContext mc, String responseStr) throws AxisFault {
//
//        JsonUtil.newJsonPayload(((Axis2MessageContext) mc).getAxis2MessageContext(), responseStr, true, true);
//    }

    private void replyGracefully(JSONObject repobj, MessageContext context) throws JSONException, XMLStreamException, AxisFault {

        String payloadXML = (repobj == null) ? "{\":\":\"No content\"}" : XML.toString(repobj);
        String soapEnvelopeStart = "<soapenv:Body xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\">";
        String soapEnvelopeEnd = "</soapenv:Body>";

        payloadXML = soapEnvelopeStart + payloadXML + soapEnvelopeEnd;
        context.setResponse(true);
        OMElement om = AXIOMUtil.stringToOM(payloadXML);

        SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
        SOAPEnvelope env = factory.getDefaultEnvelope();

        env.getBody().addChild(om.getFirstOMChild());
        context.setEnvelope(env);
        //EndpointReference reply = context.getReplyTo(); 
        context.setTo(null);
        context.setResponse(true);
        org.apache.axis2.context.MessageContext con = ((Axis2MessageContext) context).getAxis2MessageContext();
        con.setProperty("messageType", "application/json");
        con.setEnvelope(env);
        con.setTo(null);

        org.apache.axis2.context.MessageContext mo = MessageContextBuilder.createOutMessageContext(con);
        mo.setEnvelope(env);
        try {
            AxisEngine.send(mo);
        } catch (AxisFault e) {
            e.printStackTrace();
        }

    }

    public void handlePluginException(String requestErr) throws AxiataException {

        Gson gson = new GsonBuilder().serializeNulls().create();
        RequestError reqerror = gson.fromJson(requestErr, RequestError.class);
        String messagid = null;
        String variables = null;
        if (reqerror.getPolicyException() != null) {
            messagid = reqerror.getPolicyException().getMessageId();
            variables = reqerror.getPolicyException().getVariables();
        } else {
            messagid = reqerror.getServiceException().getMessageId();
            variables = reqerror.getServiceException().getVariables();
        }
        throw new AxiataException(messagid,"", new String[]{variables});

    }     */
}