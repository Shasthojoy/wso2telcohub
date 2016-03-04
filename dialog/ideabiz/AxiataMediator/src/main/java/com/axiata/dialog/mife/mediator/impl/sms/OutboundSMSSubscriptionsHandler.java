package com.axiata.dialog.mife.mediator.impl.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.CallbackReference;
import com.axiata.dialog.mife.mediator.entity.DestinationAddresses;
import com.axiata.dialog.mife.mediator.entity.NBSubscribeRequest;
import com.axiata.dialog.mife.mediator.entity.SBOutboundSubscriptionRequest;
import com.axiata.dialog.mife.mediator.entity.Subscription;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateOutboundSubscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutboundSMSSubscriptionsHandler implements SMSHandler {

    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;

    public OutboundSMSSubscriptionsHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
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
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        context.setProperty("mife.prop.operationType", 205);
        IServiceValidate validator;
        if (httpMethod.equalsIgnoreCase("POST")) {
            validator = new ValidateOutboundSubscription();
            validator.validateUrl(requestPath);
            validator.validate(jsonBody.toString());
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

        JSONObject jsonBody = executor.getJsonBody();
        JSONObject jsondstaddr = jsonBody.getJSONObject("deliveryReceiptSubscription");
        

        if (!jsondstaddr.isNull("filterCriteria")) {
        	SBOutboundSubscriptionRequest subsrequst = gson.fromJson(jsonBody.toString(), SBOutboundSubscriptionRequest.class);
            String origNotiUrl = subsrequst.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL();

            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),executor.getValidoperators());

            Integer axiataid = dbservice.subscriptionEntry(subsrequst.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL());
            Util.getPropertyFile();
            String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
            jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

            String sbRequestBody = removeResourceURL(gson.toJson(subsrequst));

            List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
            SBOutboundSubscriptionRequest subsresponse = null;
            for (OperatorEndpoint endpoint : endpoints) {

                //String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), jsonBody.toString(), true, context);
                String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), sbRequestBody, true, context);
                if (notifyres == null) {
                    throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
                } else {
                    subsresponse = gson.fromJson(notifyres, SBOutboundSubscriptionRequest.class);
                    if (subsrequst.getDeliveryReceiptSubscription() == null) {
                        executor.handlePluginException(notifyres);
                    }
                    domainsubs.add(new Operatorsubs(endpoint.getOperator(), subsrequst.getDeliveryReceiptSubscription().getResourceURL()));
                }
            }

            boolean issubs = dbservice.operatorsubsEntry(domainsubs, axiataid);
            String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");
            subsrequst.getDeliveryReceiptSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            JSONObject replyobj = new JSONObject(subsresponse);
            JSONObject replysubs = replyobj.getJSONObject("deliveryReceiptSubscription");
            //String replydest = replysubs.getString("destinationAddress");

            /*JSONArray arradd = new JSONArray();
            arradd.put(replydest);
            replysubs.put("destinationAddress", arradd);*/
            

            replysubs.getJSONObject("callbackReference").put("notifyURL", origNotiUrl);
            jsondstaddr.put("resourceURL", ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);

            executor.removeHeaders(context);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            executor.setResponse(context, replyobj.toString());
        } else {/*
            NBSubscribeRequest nbSubsrequst = gson.fromJson(jsonBody.toString(), NBSubscribeRequest.class);
            String origNotiUrl = nbSubsrequst.getSubscription().getCallbackReference().getNotifyURL();

            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),executor.getValidoperators());

            Integer axiataid = dbservice.subscriptionEntry(nbSubsrequst.getSubscription().getCallbackReference().getNotifyURL());
            Util.getPropertyFile();
            String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
            jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

            
            List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
            SBOutboundSubscriptionRequest sbSubsresponse = null;
            DestinationAddresses[] destinationAddresses = nbSubsrequst.getSubscription().getDestinationAddresses();

            for (OperatorEndpoint endpoint : endpoints) {

                for (int i = 0; i < destinationAddresses.length; i++) {
                    if (destinationAddresses[i].getOperatorCode().equalsIgnoreCase(endpoint.getOperator())) {
                    	SBOutboundSubscriptionRequest sbSubsrequst = new SBOutboundSubscriptionRequest();
                        Subscription sbrequest = new Subscription();
                        CallbackReference callbackReference = new CallbackReference();

                        callbackReference.setCallbackData(nbSubsrequst.getSubscription().getCallbackReference().getCallbackData());
                        callbackReference.setNotifyURL(subsEndpoint);
                        sbrequest.setCallbackReference(callbackReference);
                        
                        sbrequest.setNotificationFormat(nbSubsrequst.getSubscription().getNotificationFormat());
                        sbrequest.setCriteria(destinationAddresses[i].getCriteria());
                        sbrequest.setDestinationAddress(destinationAddresses[i].getDestinationAddress());
                        sbSubsrequst.setSubscription(sbrequest);
                        String sbRequestBody = removeResourceURL(gson.toJson(sbSubsrequst));
                        String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), sbRequestBody, true, context);
                        if (notifyres == null) {
                            destinationAddresses[i].setStatus("Failed");
                        } else {
                            sbSubsresponse = gson.fromJson(notifyres, SBOutboundSubscriptionRequest.class);
                            if (sbSubsresponse.getSubscription() == null) {
                                destinationAddresses[i].setStatus("NotCreated");
                            } else {
                                domainsubs.add(new Operatorsubs(endpoint.getOperator(), sbSubsresponse.getSubscription().getResourceURL()));
                                destinationAddresses[i].setStatus("Created");
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

            nbSubsrequst.getSubscription().setDestinationAddresses(responseDestinationAddresses);
            nbSubsrequst.getSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            
            nbSubsrequst.getSubscription().getCallbackReference().setNotifyURL(origNotiUrl);

            String nbResponseBody = gson.toJson(nbSubsrequst);
            executor.removeHeaders(context);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            executor.setResponse(context, nbResponseBody.toString());
        */}
        return true;
    }
    
    private String removeResourceURL(String sbSubsrequst) {
        String sbDeliveryNotificationrequestString = "";
        try {
            JSONObject objJSONObject = new JSONObject(sbSubsrequst);
            JSONObject objDeliveryNotificationRequest = (JSONObject) objJSONObject.get("deliveryReceiptSubscription");
            objDeliveryNotificationRequest.remove("resourceURL");

            sbDeliveryNotificationrequestString = objDeliveryNotificationRequest.toString();
        } catch (JSONException ex) {
            //log.error("Error in removeResourceURL" + ex.getMessage());
            throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
        }
        return "{\"deliveryReceiptSubscription\":" + sbDeliveryNotificationrequestString + "}";
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
                    .getOperator
                            ()), subs.getDomain(), null, true, context);
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
}
