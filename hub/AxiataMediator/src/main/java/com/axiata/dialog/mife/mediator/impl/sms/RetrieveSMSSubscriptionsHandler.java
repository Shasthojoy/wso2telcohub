package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.CallbackReference;
import com.axiata.dialog.mife.mediator.entity.nb.DestinationAddresses;
import com.axiata.dialog.mife.mediator.entity.nb.NBSubscribeRequest;
import com.axiata.dialog.mife.mediator.entity.sb.SBSubscribeRequest;
import com.axiata.dialog.mife.mediator.entity.sb.Subscription;
import com.axiata.dialog.mife.mediator.internal.ApiUtils;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateNBSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateSBSubscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;
import org.json.JSONException;

public class RetrieveSMSSubscriptionsHandler implements SMSHandler {

    private static Log log = LogFactory.getLog(RetrieveSMSSubscriptionsHandler.class);
    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;
    private ApiUtils apiUtils;

    public RetrieveSMSSubscriptionsHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
        apiUtils = new ApiUtils();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {
        if (executor.getHttpMethod().equalsIgnoreCase("POST")) {
            return createSubscriptions(context);
        } else if (executor.getHttpMethod().equalsIgnoreCase("DELETE")) {
            return deleteSubscriptions(context);
        }
        return false;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        context.setProperty("mife.prop.operationType", 205);
        IServiceValidate validator;
        if (httpMethod.equalsIgnoreCase("POST")) {

            JSONObject jsondstaddr = jsonBody.getJSONObject("subscription");

            if (!jsondstaddr.isNull("criteria")) {
                validator = new ValidateSBSubscription();
                validator.validateUrl(requestPath);
                validator.validate(jsonBody.toString());
            } else {
                validator = new ValidateNBSubscription();
                validator.validateUrl(requestPath);
                validator.validate(jsonBody.toString());
            }
            return true;
        } else if (httpMethod.equalsIgnoreCase("DELETE")) {
            String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);
            String[] params = {axiataid};
            validator = new ValidateCancelSubscription();
            validator.validateUrl(requestPath);
            validator.validate(params);
            return true;
        } else {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }
    }

    private boolean createSubscriptions(MessageContext context) throws Exception {

        String requestid = AxiataUID.getUniqueID(AxiataType.RETRIVSUB.getCode(), context, executor.getApplicationid());
        Gson gson = new GsonBuilder().serializeNulls().create();

        HashMap<String, String> jwtDetails = apiUtils.getJwtTokenDetails(context);
        JSONObject jsonBody = executor.getJsonBody();
        JSONObject jsondstaddr = jsonBody.getJSONObject("subscription");
        /*String addr = (String) jsondstaddr.getJSONArray("destinationAddress").get(0);*/
        String orgclientcl = "";
        if (!jsondstaddr.isNull("clientCorrelator")) {
            orgclientcl = jsondstaddr.getString("clientCorrelator");
        }

        String serviceProvider = jwtDetails.get("subscriber");
        log.debug("Subscriber Name : " + serviceProvider);

        if (!jsondstaddr.isNull("criteria")) {
            /*String addr = (String) jsondstaddr.getJSONArray("destinationAddress").get(0);*/

            //if (addr.startsWith("[")) {
            /*jsondstaddr.put("destinationAddress", addr.replaceAll("\\[|\\]", ""));*/
            //}
            SBSubscribeRequest subsrequst = gson.fromJson(jsonBody.toString(), SBSubscribeRequest.class);
            String origNotiUrl = subsrequst.getSubscription().getCallbackReference().getNotifyURL();

//        String appID = subsrequst.getSubscription().getDestinationAddress();
            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),
                    executor.getValidoperators());

            Integer axiataid = dbservice.subscriptionEntry(subsrequst.getSubscription().getCallbackReference()
                    .getNotifyURL(), serviceProvider);
            Util.getPropertyFile();
            String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
            jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

            //JSONObject clientclr = jsonBody.getJSONObject("outboundSMSMessageRequest");
            jsondstaddr.put("clientCorrelator", orgclientcl + ":" + requestid);

            List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
            SBSubscribeRequest subsresponse = null;
            for (OperatorEndpoint endpoint : endpoints) {

                String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), jsonBody
                        .toString(), true, context);
                if (notifyres == null) {
                    throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
                } else {
                    //plugin exception handling
                    subsresponse = gson.fromJson(notifyres, SBSubscribeRequest.class);
                    if (subsresponse.getSubscription() == null) {
                        executor.handlePluginException(notifyres);
                    }
                    domainsubs.add(new Operatorsubs(endpoint.getOperator(), subsresponse.getSubscription().getResourceURL()));
                }
            }

            boolean issubs = dbservice.operatorsubsEntry(domainsubs, axiataid);
            //String ResourceUrlPrefix = (String) context.getProperty("REST_URL_PREFIX");
            //modified due to load balancer url issue
            String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");
            subsresponse.getSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            //replyGracefully(new JSONObject(subsresponse), context);

            JSONObject replyobj = new JSONObject(subsresponse);
            JSONObject replysubs = replyobj.getJSONObject("subscription");
            /*String replydest = replysubs.getString("destinationAddress");*/

            //if (!addr.startsWith("[")) {
            /*JSONArray arradd = new JSONArray();
             arradd.put(replydest);
             replysubs.put("destinationAddress", arradd);*/
            replysubs.put("clientCorrelator", orgclientcl);

            replysubs.getJSONObject("callbackReference").put("notifyURL", origNotiUrl);

            //}
            jsondstaddr.put("resourceURL", ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            // routeToEndPoint(new OperatorEndpoint(new EndpointReference(sendResponse),null),context, sendResponse, "",
            // "", replyobj.toString());
            executor.removeHeaders(context);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            executor.setResponse(context, replyobj.toString());
        } else {

            NBSubscribeRequest nbSubsrequst = gson.fromJson(jsonBody.toString(), NBSubscribeRequest.class);
            String origNotiUrl = nbSubsrequst.getSubscription().getCallbackReference().getNotifyURL();

            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),
                    executor.getValidoperators());

            Integer axiataid = dbservice.subscriptionEntry(nbSubsrequst.getSubscription().getCallbackReference()
                    .getNotifyURL(), serviceProvider);
            Util.getPropertyFile();
            String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
            jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

            jsondstaddr.put("clientCorrelator", orgclientcl + ":" + requestid);

            log.debug("Subscription northbound request body : " + gson.toJson(nbSubsrequst));

            List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
            SBSubscribeRequest sbSubsresponse = null;

            DestinationAddresses[] destinationAddresses = nbSubsrequst.getSubscription().getDestinationAddresses();

            for (OperatorEndpoint endpoint : endpoints) {

                /*create subscription for southbound operators*/
                for (int i = 0; i < destinationAddresses.length; i++) {
                    if (destinationAddresses[i].getOperatorCode().equalsIgnoreCase(endpoint.getOperator())) {
                        log.debug("Operator name: " + endpoint.getOperator());
                        SBSubscribeRequest sbSubsrequst = new SBSubscribeRequest();
                        Subscription sbrequest = new Subscription();
                        CallbackReference callbackReference = new CallbackReference();

                        callbackReference.setCallbackData(nbSubsrequst.getSubscription().getCallbackReference().getCallbackData());
                        callbackReference.setNotifyURL(subsEndpoint);
                        sbrequest.setCallbackReference(callbackReference);
                        sbrequest.setClientCorrelator(orgclientcl + ":" + requestid);
                        sbrequest.setNotificationFormat(nbSubsrequst.getSubscription().getNotificationFormat());
                        sbrequest.setCriteria(destinationAddresses[i].getCriteria());
                        sbrequest.setDestinationAddress(destinationAddresses[i].getDestinationAddress());
                        sbSubsrequst.setSubscription(sbrequest);

                        String sbRequestBody = removeResourceURL(gson.toJson(sbSubsrequst));
                        log.debug("Subscription southbound request body of " + endpoint.getOperator() + " operator: " + sbRequestBody);

                        String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), sbRequestBody, true, context);

                        log.debug("Subscription southbound response body of " + endpoint.getOperator() + " operator: " + notifyres);

                        if (notifyres == null) {
                            destinationAddresses[i].setStatus("Failed");
                            /*throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});*/
                        } else {
                            //plugin exception handling
                            sbSubsresponse = gson.fromJson(notifyres, SBSubscribeRequest.class);
                            if (sbSubsresponse.getSubscription() == null) {
                                /*executor.handlePluginException(notifyres);*/
                                destinationAddresses[i].setStatus("NotCreated");
                            } else {
                                domainsubs.add(new Operatorsubs(endpoint.getOperator(), sbSubsresponse.getSubscription().getResourceURL()));
                                destinationAddresses[i].setStatus("Created");
                                //deliveryStatus.add("success");   
                            }
                        }
                        break;
                    }
                }
            }

            boolean issubs = dbservice.operatorsubsEntry(domainsubs, axiataid);

            String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");

            DestinationAddresses[] responseDestinationAddresses = new DestinationAddresses[destinationAddresses.length];
            int destinationAddressesCount = 0;
            int successResultCount = 0;
            for (DestinationAddresses destinationAddressesResult : destinationAddresses) {
                String subscriptionStatus = destinationAddressesResult.getStatus();
                if (subscriptionStatus == null) {
                    destinationAddressesResult.setStatus("Failed");
                } else if (subscriptionStatus.equals("Created")) {
                    successResultCount++;
                }
                responseDestinationAddresses[destinationAddressesCount] = destinationAddressesResult;
                destinationAddressesCount++;
            }

            if (successResultCount == 0) {
                throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
            }

            /*create northbound response*/
            nbSubsrequst.getSubscription().setDestinationAddresses(responseDestinationAddresses);
            nbSubsrequst.getSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            nbSubsrequst.getSubscription().setClientCorrelator(orgclientcl);
            nbSubsrequst.getSubscription().getCallbackReference().setNotifyURL(origNotiUrl);

            String nbResponseBody = gson.toJson(nbSubsrequst);

            log.debug("Subscription northbound response body : " + nbResponseBody);

            executor.removeHeaders(context);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            executor.setResponse(context, nbResponseBody.toString());
        }
        return true;
    }

    private boolean deleteSubscriptions(MessageContext context) throws Exception {
        String requestPath = executor.getSubResourcePath();
        String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);

        String requestid = AxiataUID.getUniqueID(AxiataType.DELRETSUB.getCode(), context, executor.getApplicationid());

        List<Operatorsubs> domainsubs = (dbservice.subscriptionQuery(Integer.valueOf(axiataid)));
        if (domainsubs.isEmpty()) {
            throw new AxiataException("POL0001", "", new String[]{"SMS Receipt Subscription Not Found: " + axiataid});
        }

        String resStr = "";
        for (Operatorsubs subs : domainsubs) {
            resStr = executor.makeDeleteRequest(new OperatorEndpoint(new EndpointReference(subs.getDomain()), subs
                    .getOperator()), subs.getDomain(), null, true, context);
        }
        new AxiataDbService().subscriptionDelete(Integer.valueOf(axiataid));

        //JSONObject reply = new JSONObject();
        //reply.put("response", "No content");
        //replyGracefully(reply, context);
        //routeToEndPoint(new OperatorEndpoint(new EndpointReference(sendResponse),null),context, sendResponse, "",
        // "", "");
        executor.removeHeaders(context);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 204);

        return true;
    }

    /*This method add because XL plugin returns invalid response if send a request with resourceURL*/
    private String removeResourceURL(String sbSubsrequst) {
        String sbrequestString = "";
        try {
            JSONObject objJSONObject = new JSONObject(sbSubsrequst);
            JSONObject objSubscriptionRequest = (JSONObject) objJSONObject.get("subscription");
            objSubscriptionRequest.remove("resourceURL");

            sbrequestString = objSubscriptionRequest.toString();
        } catch (JSONException ex) {
            log.error("Error in removeResourceURL" + ex.getMessage());
            throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
        }
        return "{\"subscription\":" + sbrequestString + "}";
    }
}
