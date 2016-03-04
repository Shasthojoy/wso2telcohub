package com.axiata.dialog.mife.mediator.impl.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.DeliveryReceiptSubscriptionRequest;
import com.axiata.dialog.mife.mediator.internal.ApiUtils;
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
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OutboundSMSSubscriptionsHandler implements SMSHandler {

    private static Log log = LogFactory.getLog(OutboundSMSSubscriptionsHandler.class);
    private static final String API_TYPE = "smsmessaging";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;
    private ApiUtils apiUtils;

    public OutboundSMSSubscriptionsHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
        apiUtils = new ApiUtils();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {
        if (executor.getHttpMethod().equalsIgnoreCase("POST")) {
            return createSubscriptions(context);
        } /*else if (executor.getHttpMethod().equalsIgnoreCase("DELETE")) {
            return deleteSubscriptions(context);
        }*/
        return false;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        //context.setProperty("mife.prop.operationType", 205);
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

        HashMap<String, String> jwtDetails = apiUtils.getJwtTokenDetails(context);
        JSONObject jsonBody = executor.getJsonBody();
        JSONObject jsondstaddr = jsonBody.getJSONObject("deliveryReceiptSubscription");
        String orgclientcl = "";
        if (!jsondstaddr.isNull("clientCorrelator")) {
            orgclientcl = jsondstaddr.getString("clientCorrelator");
        }
        
        String serviceProvider = jwtDetails.get("subscriber");
        log.debug("Subscriber Name : " + serviceProvider);

        if (!jsondstaddr.isNull("filterCriteria")) {
            DeliveryReceiptSubscriptionRequest subsrequst = gson.fromJson(jsonBody.toString(), DeliveryReceiptSubscriptionRequest.class);
            String origNotiUrl = subsrequst.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL();
            subsrequst.getDeliveryReceiptSubscription().setClientCorrelator(orgclientcl + ":" + requestid);

            List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(), executor.getValidoperators());

            Integer axiataid = dbservice.outboundSubscriptionEntry(subsrequst.getDeliveryReceiptSubscription().getCallbackReference().getNotifyURL(), serviceProvider);
            Util.getPropertyFile();
            String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
            jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);
            subsrequst.getDeliveryReceiptSubscription().getCallbackReference().setNotifyURL(subsEndpoint);
                    
            String sbRequestBody = removeResourceURL(gson.toJson(subsrequst));
            List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
            DeliveryReceiptSubscriptionRequest subsresponse = null;
            for (OperatorEndpoint endpoint : endpoints) {

                String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), sbRequestBody, true, context);
                if (notifyres == null) {
                    throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
                } else {
                    subsresponse = gson.fromJson(notifyres, DeliveryReceiptSubscriptionRequest.class);
                    if (subsrequst.getDeliveryReceiptSubscription() == null) {
                        executor.handlePluginException(notifyres);
                    }
                    domainsubs.add(new Operatorsubs(endpoint.getOperator(), subsresponse.getDeliveryReceiptSubscription().getResourceURL()));
                }
            }

            boolean issubs = dbservice.outboundOperatorsubsEntry(domainsubs, axiataid);
            String ResourceUrlPrefix = Util.getApplicationProperty("hubGateway");
            subsresponse.getDeliveryReceiptSubscription().setResourceURL(ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            JSONObject replyobj = new JSONObject(subsresponse);
            JSONObject replysubs = replyobj.getJSONObject("deliveryReceiptSubscription");
            replysubs.put("clientCorrelator", orgclientcl);
            //String replydest = replysubs.getString("destinationAddress");

            /*JSONArray arradd = new JSONArray();
             arradd.put(replydest);
             replysubs.put("destinationAddress", arradd);*/
            replysubs.getJSONObject("callbackReference").put("notifyURL", origNotiUrl);
            replysubs.put("resourceURL", ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
            /*jsondstaddr.put("resourceURL", ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);*/

            executor.removeHeaders(context);
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
            executor.setResponse(context, replyobj.toString());
        } 
        return true;
    }

    /*This method add because XL plugin returns invalid response if send a request with resourceURL*/
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

    /*private boolean deleteSubscriptions(MessageContext context) throws Exception {
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
                    .getOperator()), subs.getDomain(), null, true, context,false);
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
    }*/
}
