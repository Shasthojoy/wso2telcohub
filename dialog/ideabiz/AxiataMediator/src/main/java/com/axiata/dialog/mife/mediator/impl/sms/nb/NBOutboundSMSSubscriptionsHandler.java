/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.mife.mediator.impl.sms.nb;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.CallbackReference;
import com.axiata.dialog.mife.mediator.entity.nb.NBDeliveryReceiptSubscriptionRequest;
import com.axiata.dialog.mife.mediator.entity.nb.SenderAddresses;
import com.axiata.dialog.mife.mediator.entity.sb.DeliveryReceiptSubscription;
import com.axiata.dialog.mife.mediator.entity.sb.SBDeliveryReceiptSubscriptionRequest;
import com.axiata.dialog.mife.mediator.impl.sms.SMSExecutor;
import com.axiata.dialog.mife.mediator.impl.sms.SMSHandler;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.sms.nb.ValidateNBOutboundSubscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;


public class NBOutboundSMSSubscriptionsHandler implements SMSHandler {

    private static Log log = LogFactory.getLog(NBOutboundSMSSubscriptionsHandler.class);
    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;

    public NBOutboundSMSSubscriptionsHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        IServiceValidate validator;
        if (httpMethod.equalsIgnoreCase("POST")) {
            validator = new ValidateNBOutboundSubscription();
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

    @Override
    public boolean handle(MessageContext context) throws Exception {
        if (executor.getHttpMethod().equalsIgnoreCase("POST")) {
            return createSubscriptions(context);
        } else if (executor.getHttpMethod().equalsIgnoreCase("DELETE")) {
            return deleteSubscriptions(context);
        }
        return false;
    }

    private boolean createSubscriptions(MessageContext context) throws Exception {

        String requestid = AxiataUID.getUniqueID(AxiataType.RETRIVSUB.getCode(), context, executor.getApplicationid());
        Gson gson = new GsonBuilder().serializeNulls().create();

        JSONObject jsonBody = executor.getJsonBody();

        NBDeliveryReceiptSubscriptionRequest nbDeliveryReceiptSubscriptionRequest = gson.fromJson(jsonBody.toString(), NBDeliveryReceiptSubscriptionRequest.class);
        String orgclientcl = nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getClientCorrelator();
        //JSONObject jsondstaddr = jsonBody.getJSONObject("deliveryReceiptSubscription");

        //SBOutboundSubscriptionRequest subsrequst = gson.fromJson(jsonBody.toString(), SBOutboundSubscriptionRequest.class);
        //String origNotiUrl = subsrequst.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL();
        String origNotiUrl = nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL();

        List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(), executor.getValidoperators());

        Integer axiataid = dbservice.subscriptionEntry(nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL());
        Util.getPropertyFile();
        String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
        nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getCallbackReference().setNotifyURL(subsEndpoint);
        //jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

        log.debug("Delivery notification subscription northbound request body : " + gson.toJson(nbDeliveryReceiptSubscriptionRequest));

        SenderAddresses[] senderAddresses = nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getSenderAddresses();

        List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
        SBDeliveryReceiptSubscriptionRequest sbDeliveryReceiptSubscriptionResponse = null;
        //SBOutboundSubscriptionRequest subsresponse = null;
        for (OperatorEndpoint endpoint : endpoints) {

            /*create delivery notification subscription for southbound*/
            for (int i = 0; i < senderAddresses.length; i++) {
                if (senderAddresses[i].getOperatorCode().equalsIgnoreCase(endpoint.getOperator())) {
                    log.debug("Operator name: " + endpoint.getOperator());
                    SBDeliveryReceiptSubscriptionRequest sbDeliveryReceiptSubscriptionRequest = new SBDeliveryReceiptSubscriptionRequest();
                    DeliveryReceiptSubscription deliveryReceiptSubscriptionRequest = new DeliveryReceiptSubscription();
                    CallbackReference callbackReference = new CallbackReference();

                    callbackReference.setCallbackData(nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getCallbackReference().getCallbackData());
                    callbackReference.setNotifyURL(subsEndpoint);
                    deliveryReceiptSubscriptionRequest.setCallbackReference(callbackReference);
                    deliveryReceiptSubscriptionRequest.setClientCorrelator(orgclientcl + ":" + requestid);
                    deliveryReceiptSubscriptionRequest.setOperatorCode(senderAddresses[i].getOperatorCode());
                    deliveryReceiptSubscriptionRequest.setFilterCriteria(senderAddresses[i].getFilterCriteria());
                    sbDeliveryReceiptSubscriptionRequest.setDeliveryReceiptSubscription(deliveryReceiptSubscriptionRequest);
                    String sbRequestBody = removeResourceURL(gson.toJson(sbDeliveryReceiptSubscriptionRequest));
                    log.debug("Delivery notification southbound request body of " + endpoint.getOperator() + " operator: " + sbRequestBody);

                    /*Create southbound request URL*/
                    String url = endpoint.getEndpointref().getAddress();
                    String southboundURLPart = "/" + senderAddresses[i].getSenderAddress() + "/subscriptions";
                    url = url.replace("/subscriptions", southboundURLPart);
                    log.debug("Delivery notification southbound request url of " + endpoint.getOperator() + " operator: " + url);

                    String notifyres = executor.makeRequest(endpoint, url, jsonBody.toString(), true, context);

                    log.debug("Delivery notification southbound response body of " + endpoint.getOperator() + " operator: " + notifyres);

                    if (notifyres == null) {
                        senderAddresses[i].setStatus("Failed");
                        /*throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});*/
                    } else {
                        //plugin exception handling
                        sbDeliveryReceiptSubscriptionResponse = gson.fromJson(notifyres, SBDeliveryReceiptSubscriptionRequest.class);
                        if (sbDeliveryReceiptSubscriptionResponse.getDeliveryReceiptSubscription() == null) {
                            senderAddresses[i].setStatus("NotCreated");
                        } else {
                            domainsubs.add(new Operatorsubs(endpoint.getOperator(), sbDeliveryReceiptSubscriptionResponse.getDeliveryReceiptSubscription().getResourceURL()));
                            senderAddresses[i].setStatus("Created");
                        }
                    }
                    break;
                }
            }
        }

        boolean issubs = dbservice.operatorsubsEntry(domainsubs, axiataid);
        String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");

        SenderAddresses[] responseSenderAddresses = new SenderAddresses[senderAddresses.length];
        int senderAddressesCount = 0;
        int successResultCount = 0;
        for (SenderAddresses sendernAddressesResult : senderAddresses) {
            String deliverySubscriptionStatus = sendernAddressesResult.getStatus();
            if (deliverySubscriptionStatus == null) {
                sendernAddressesResult.setStatus("Failed");
            } else if (deliverySubscriptionStatus.equals("Created")) {
                successResultCount++;
            }
            responseSenderAddresses[senderAddressesCount] = sendernAddressesResult;
            senderAddressesCount++;
        }

        if (successResultCount == 0) {
            throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
        }

        /*create delivery notification northbound response*/
        nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().setSenderAddresses(responseSenderAddresses);
        nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
        nbDeliveryReceiptSubscriptionRequest.getDeliveryReceiptSubscription().getCallbackReference().setNotifyURL(origNotiUrl);

        String nbDeliveryReceiptSubscriptionResponseBody = gson.toJson(nbDeliveryReceiptSubscriptionRequest);
        log.debug("Delivery notification subscription northbound response body : " + nbDeliveryReceiptSubscriptionResponseBody);

        executor.removeHeaders(context);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
        executor.setResponse(context, nbDeliveryReceiptSubscriptionResponseBody);

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
            log.error("Error in removeResourceURL" + ex.getMessage());
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
                    .getOperator()), subs.getDomain(), null, true, context);
        }
        new AxiataDbService().subscriptionDelete(Integer.valueOf(axiataid));

        executor.removeHeaders(context);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 204);

        return true;
    }
}
