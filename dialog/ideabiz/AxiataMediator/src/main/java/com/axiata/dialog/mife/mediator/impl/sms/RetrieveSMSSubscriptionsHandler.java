package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.SubscribeRequest;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateSBSubscription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RetrieveSMSSubscriptionsHandler implements SMSHandler {

    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;

    public RetrieveSMSSubscriptionsHandler(SMSExecutor executor) {
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

        IServiceValidate validator;
        if (httpMethod.equalsIgnoreCase("POST")) {
            validator = new ValidateSBSubscription();
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
        JSONObject jsondstaddr = jsonBody.getJSONObject("subscription");
        String addr = (String) jsondstaddr.getJSONArray("destinationAddress").get(0);
        String orgclientcl = jsondstaddr.getString("clientCorrelator");

        //if (addr.startsWith("[")) {
        jsondstaddr.put("destinationAddress", addr.replaceAll("\\[|\\]", ""));
        //}
        SubscribeRequest subsrequst = gson.fromJson(jsonBody.toString(), SubscribeRequest.class);
        String origNotiUrl = subsrequst.getSubscription().getCallbackReference().getNotifyURL();

//        String appID = subsrequst.getSubscription().getDestinationAddress();
        List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),
                executor.getValidoperators());

        Integer axiataid = dbservice.subscriptionEntry(subsrequst.getSubscription().getCallbackReference()
                .getNotifyURL());
        Util.getPropertyFile();
        String subsEndpoint = Util.getApplicationProperty("hubSubsGatewayEndpoint") + "/" + axiataid;
        jsondstaddr.getJSONObject("callbackReference").put("notifyURL", subsEndpoint);

        //JSONObject clientclr = jsonBody.getJSONObject("outboundSMSMessageRequest");
        jsondstaddr.put("clientCorrelator", orgclientcl + ":" + requestid);

        List<Operatorsubs> domainsubs = new ArrayList<Operatorsubs>();
        SubscribeRequest subsresponse = null;
        for (OperatorEndpoint endpoint : endpoints) {

            String notifyres = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), jsonBody
                    .toString(), true, context);
            if (notifyres == null) {
                throw new AxiataException("POL0299", "", new String[]{"Error registering subscription"});
            } else {
                //plugin exception handling
                subsresponse = gson.fromJson(notifyres, SubscribeRequest.class);
                if (subsresponse.getSubscription() == null) {
                    executor.handlePluginException(notifyres);
                }
                domainsubs.add(new Operatorsubs(endpoint.getOperator(), subsresponse.getSubscription().getResourceURL
                        ()));
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
        String replydest = replysubs.getString("destinationAddress");

        //if (!addr.startsWith("[")) {
        JSONArray arradd = new JSONArray();
        arradd.put(replydest);
        replysubs.put("destinationAddress", arradd);
        replysubs.put("clientCorrelator", orgclientcl);

        replysubs.getJSONObject("callbackReference").put("notifyURL", origNotiUrl);

        //}
        jsondstaddr.put("resourceURL", ResourceUrlPrefix + executor.getResourceUrl() + "/" + axiataid);
        // routeToEndPoint(new OperatorEndpoint(new EndpointReference(sendResponse),null),context, sendResponse, "",
        // "", replyobj.toString());
        executor.removeHeaders(context);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
        executor.setResponse(context, replyobj.toString());
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
