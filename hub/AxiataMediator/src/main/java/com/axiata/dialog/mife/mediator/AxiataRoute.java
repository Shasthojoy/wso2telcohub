/*
 * AxiataRoute.java
 * Aug 19, 2013  9:32:17 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.dbutils.AxataDBUtilException;
import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operator;
import com.axiata.dialog.mife.events.data.handler.SpendLimitHandler;
import com.axiata.dialog.mife.mediator.internal.Base64Coder;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.Validation;
import com.axiata.dialog.oneapi.validation.ValidationRule;
import com.axiata.dialog.oneapi.validation.impl.ValidatePaymentCharge;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.axis2.addressing.EndpointReference;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import org.apache.axis2.AxisFault;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import com.axiata.dialog.mife.mediator.entity.SendSMSRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;

import java.io.InputStream;


/**
 * <TO-DO>
 * <code>AxiataRoute</code>
 *
 * @version $Id: AxiataRoute.java,v 1.00.000
 */
import org.apache.synapse.commons.json.JsonUtil;
import com.axiata.dialog.mife.mediator.internal.AggregatorValidator;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.ResourceURLUtil;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.AuthenticationContext;

public class AxiataRoute /*extends AxiataRequestExecutor*/ {

    private static Log log = LogFactory.getLog(AxiataRoute.class);
    //Get the end point location from mediation rule handler
    private static final String smsDummyurl = "http://localhost:18080/MediationTest/tnspoints/enpoint/dummysend";
    private static final String sendError = "http://localhost:18080/MediationTest/tnspoints/enpoint/RequestError/error";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private boolean validate = true;
    private ResponseHandler responseHandler;
    private String subscriber;
    private boolean isaggrigator = false;

    public AxiataRoute() {
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
        responseHandler = new ResponseHandler();
    }
    /*
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws AxiataException {

        SendSMSRequest smsreq = (SendSMSRequest) data;
        List lstaddr = smsreq.getOutboundSMSMessageRequest().getAddress();
        String[] addresses = (smsreq.getOutboundSMSMessageRequest().getAddress()).toArray(new String[lstaddr.size()]); // .getParameterValues("address");	// Note there can be multiple addresses specified
        String senderAddress = smsreq.getOutboundSMSMessageRequest().getSenderAddress();
        String message = smsreq.getOutboundSMSMessageRequest().getOutboundTextMessage().getMessage();
        String clientCorrelator = smsreq.getOutboundSMSMessageRequest().getClientCorrelator();
        String notifyURL = smsreq.getOutboundSMSMessageRequest().getReceiptRequest().getNotifyURL();
        String callbackData = smsreq.getOutboundSMSMessageRequest().getCallbackData();
        String senderName = smsreq.getOutboundSMSMessageRequest().getSenderName();

        String resourceURL = null;

        ValidationRule[] rules = {
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "senderAddress", senderAddress),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "address", addresses),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "message", message),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "senderName", senderName),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_URL, "notifyURL", notifyURL),
            new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),};

        return Validation.checkRequestParameters(rules);
    }

    public boolean execute(MessageContext mc) throws AxiataException, AxisFault, Exception {

        //OMElement soapbody = mc.getEnvelope().getBody();
        //SOAPBody jsonBody = mc.getEnvelope().getBody();

        InputStream is = null;
        try {

            //Get valid operators
             String applicationid = getApplicationid();
            subscriber = storeSubscription(mc);            
            isaggrigator = isAggregator(mc);

            String requestid = null;

            if (applicationid == null) {
                throw new AxiataException("SVC0001", "", new String[]{"Requested service is not provisioned"});
            }
            validoperators = dbservice.applicationOperators(Integer.valueOf(applicationid));
            if (validoperators.isEmpty()) {
                throw new AxiataException("SVC0001", "", new String[]{"Requested service is not provisioned"});
            }
            JSONObject jsonBody = null; //jsonObj.getJSONObject("soapenv:Body");
            String httpMethod = (String) mc.getProperty("REST_METHOD");

            mc.setProperty("soapEnv", mc.getEnvelope());

            org.apache.axis2.context.MessageContext ctx = ((Axis2MessageContext) mc).getAxis2MessageContext();


            Gson gson = new GsonBuilder().serializeNulls().create();
            //         log.info("jsonBody of soap ::" + jsonBody);

            String jsonPayloadToString = JsonUtil.jsonPayloadToString(((Axis2MessageContext) mc).getAxis2MessageContext());
            jsonBody = new JSONObject(jsonPayloadToString);
            if (apitype.equalsIgnoreCase("send_sms")) {
                if (!httpMethod.equalsIgnoreCase("POST")) {
                    ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("HTTP_SC", 405);
                    throw new Exception("Method not allowed");
                }

                requestid = AxiataUID.getUniqueID(AxiataType.SMSSEND.getCode(), mc, applicationid);

                if (validate) {
                    ValidateSendSms validator = new ValidateSendSms();
                    validator.validateUrl((String) mc.getProperty("REST_SUB_REQUEST_PATH"));
                    validator.validate(jsonPayloadToString);
                }

                //apend request id to client co-relator
                JSONObject clientclr = jsonBody.getJSONObject("outboundSMSMessageRequest");
                clientclr.put("clientCorrelator", clientclr.getString("clientCorrelator") + ":" + requestid);

                SendSMSRequest subsrequst = gson.fromJson(jsonBody.toString(), SendSMSRequest.class);
                smssendmulti(mc, subsrequst, jsonBody.getJSONObject("outboundSMSMessageRequest").getJSONArray("address"), apitype, validoperators);
//                routeToEndpoint(mc, smsDummyurl);
                String resPayload = responseHandler.makeSmsSendResponse(mc, jsonPayloadToString, requestid);
                setResponse(mc, resPayload);
                //   routeToError(mc,sendError,"SVC0001","Internal Server Error" );
            } else if (apitype.equalsIgnoreCase("payment")) {

                if (!httpMethod.equalsIgnoreCase("POST")) {
                    ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("HTTP_SC", 405);
                    throw new Exception("Method not allowed");
                }

                requestid = AxiataUID.getUniqueID(AxiataType.PAYMENT.getCode(), mc, applicationid);

                if (validate) {
                    ValidatePaymentCharge validator = new ValidatePaymentCharge();
                    validator.validateUrl((String) mc.getProperty("REST_SUB_REQUEST_PATH"));
                    validator.validate(jsonPayloadToString);
                }
                String endUserId = jsonBody.getJSONObject("amountTransaction").getString("endUserId");
                String msisdn = endUserId.substring(5);
                mc.setProperty(AxiataConstants.USER_MSISDN, msisdn);
                OperatorEndpoint endpoint = null;
                if (ValidatorUtils.getValidatorForSubscription(mc).validate(mc)) {
                    endpoint = occi.getAPIEndpointsByMSISDN(endUserId.replace("tel:", ""), apitype, getResourceUrl(), false, validoperators);
                }
                String sending_add = endpoint.getEndpointref().getAddress();
                log.info("sending endpoint found: " + sending_add);

                // Check if Spend Limits are exceeded
                checkSpendLimit(msisdn, endpoint.getOperator(), mc);

//                routeToEndpoint(endpoint, mc, sending_add);
                //apend request id to client co-relator
                JSONObject clientclr = jsonBody.getJSONObject("amountTransaction");
                clientclr.put("clientCorrelator", clientclr.getString("clientCorrelator") + ":" + requestid);

                JSONObject chargingdmeta = clientclr.getJSONObject("paymentAmount").getJSONObject("chargingMetaData");
                
                if (isaggrigator) {

                    //JSONObject chargingdmeta = clientclr.getJSONObject("paymentAmount").getJSONObject("chargingMetaData");
                    if (!chargingdmeta.isNull("onBehalfOf")) {
                        new AggregatorValidator().validateMerchant(Integer.valueOf(applicationid), endpoint.getOperator(), subscriber, chargingdmeta.getString("onBehalfOf"));
                    }
                }
                
                //validate payment categoreis
                List<String> validCategoris = dbservice.getValidPayCategories();
                ValidDatePaymentCategory(chargingdmeta, validCategoris);
                
                String responseStr = makeRequest(endpoint, sending_add, jsonBody.toString(), true, mc);

                // Payment Error Exception Correction
                String base = str_piece(str_piece(responseStr, '{', 2), ':', 1);

                String errorReturn = "\"" + "requestError" + "\"";


                if (base.equals(errorReturn)) {


                    handlePluginException(responseStr);
                }

                responseStr = responseHandler.makePaymentResponse(responseStr, requestid);

                //set response re-applied
                setResponse(mc, responseStr);


//                OMElement responseElement = AXIOMUtil.stringToOM("");
//                mc.getEnvelope().discard();
//                SOAP11Factory f = new SOAP11Factory();
//                mc.setEnvelope(f.createSOAPEnvelope());
//                OMElement responseElement = AXIOMUtil.stringToOM("<soapenv:Body xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"></soapenv:Body>");
//                OMElement responseElement = AXIOMUtil.stringToOM("");
//                mc.getEnvelope().getBody().addChild(responseElement);
//                mc.getEnvelope().addChild(responseElement);
//                log.info("========================");
//                log.info("=========" + mc.getEnvelope());

//                setResponse(mc, responseStr);
            } else if (apitype.equalsIgnoreCase("location")) {

                if (!httpMethod.equalsIgnoreCase("GET")) {
                    ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("HTTP_SC", 405);
                    throw new Exception("Method not allowed");
                }

                requestid = AxiataUID.getUniqueID(AxiataType.LOCREQ.getCode(), mc, applicationid);
                String[] params = new ResourceURLUtil().getParamValues((String) mc.getProperty("REST_SUB_REQUEST_PATH"));
                if (validate) {
//                //get registrationId from URL eg- https://host/smsmessaging/v1/inbound/registrations/{registrationId}/messages?maxBatchSize=2
//                int lastSlash = getResourceUrl().lastIndexOf('/');
//                String registrationId = getResourceUrl().substring(getResourceUrl().lastIndexOf('/', lastSlash-1)+1, lastSlash);

                    ValidateLocation validator = new ValidateLocation();
                    validator.validateUrl((String) mc.getProperty("REST_SUB_REQUEST_PATH"));
                    validator.validate(params);
                }

                mc.setProperty(AxiataConstants.USER_MSISDN, params[0].substring(5));
                OperatorEndpoint endpoint = null;
                if (ValidatorUtils.getValidatorForSubscription(mc).validate(mc)) {
                    endpoint = occi.getAPIEndpointsByMSISDN(params[0].replace("tel:", ""), apitype, getResourceUrl(), true, validoperators);
                }
                String sending_add = endpoint.getEndpointref().getAddress();

                String responseStr = makeGetRequest(endpoint, sending_add, (String) mc.getProperty("REST_SUB_REQUEST_PATH"), true, mc);

                handlePluginException(responseStr);
                //set response re-applied
                setResponse(mc, responseStr);
                ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("messageType", "application/json");
                ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("ContentType", "application/json");
            }

        } catch (JSONException e) {
            //LOG.error("[Inbound Request], Error + " + "Retrive location " + e.getMessage());
            //RequestError requesterror = new RequestError();
            //requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            //jsonreturn = new Gson().toJson(requesterror);            
            //return jsonreturn;
            e.printStackTrace();
            log.error(e.getMessage());
            throw new AxiataException("SVC0001", "", new String[]{"Request is missing required URI components"});
        }

        return true;

    }

    /**
     * Route the message to a dynamic endpoint depending on the location of the
     * user
     *
     * @param context
     * @param location
     */
    /*private void routeToEndpoint(OperatorEndpoint operatorendpoint, MessageContext context, String EndpointRef) throws Exception {

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            headersMap.put("Authorization", "Bearer " + getAccessToken(operatorendpoint.getOperator()));
        }
        EndpointReference epr = new EndpointReference(EndpointRef);

        // Setting the end point of the message context
        context.setTo(epr);
//            
//            Collection<?> keys = headersMap.keySet();
//            for (Object key : keys) {
//                System.out.println("header Key " + key);
//                System.out.println("header Value " + headersMap.get(key));
//            }

        log.info("Message routed to new end point ::" + epr.getAddress());
        //  log.info("Message routed get to ::" + context.getTo());
        //      }
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

    private boolean smssendmulti(MessageContext smsmc, SendSMSRequest sendreq, JSONArray listaddr, String apitype, List<Operator> operators) throws AxisFault {

        try {

            List<String> adlist = sendreq.getOutboundSMSMessageRequest().getAddress();
            OperatorEndpoint endpoint = null;
            String jsonStr = null;
            String[] addresses = adlist.toArray(new String[adlist.size()]);

            String address = null;
            for (int i = 0; i < listaddr.length(); i++) {

                address = listaddr.getString(i).toString();
                log.info("id : " + address);
                smsmc.setProperty(AxiataConstants.USER_MSISDN, address.substring(5));
                if (ValidatorUtils.getValidatorForSubscription(smsmc).validate(smsmc)) {
                    endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), apitype, getResourceUrl(), false, operators);   //smsSend;
                }

                List<String> sendAdr = new ArrayList();
                sendAdr.add(address);
                sendreq.getOutboundSMSMessageRequest().setAddress(sendAdr);
                jsonStr = new Gson().toJson(sendreq);
                String sending_add = endpoint.getEndpointref().getAddress();
                log.info("sending endpoint found: " + sending_add);

                sendsingle(endpoint, sending_add, jsonStr, true, smsmc);

            }
        } catch (Exception ex) {
            throw new AxisFault("Error json object");
        }

        return true;
    }

    private boolean sendsingle(OperatorEndpoint operatorendpoint, String url, String strMsg, boolean auth, MessageContext mc) {

        if (makeRequest(operatorendpoint, url, strMsg, auth, mc) != null) {
            return false;
        }
        return true;
    }

//    private void setResponse(MessageContext mc, String responseStr) throws AxisFault {
////        InputStream responseIs = new ByteArrayInputStream(responseStr.getBytes());
////        ctx.setProperty("JSON_STREAM", responseIs);
//
////        SOAPEnvelope responseSOAPEnv = (SOAPEnvelope) new JSONBuilder().processDocument(responseIs, "application/json", ((Axis2MessageContext) mc).getAxis2MessageContext());
//        JsonUtil.newJsonPayload(((Axis2MessageContext) mc).getAxis2MessageContext(), responseStr, true, true);
////        mc.setEnvelope(responseSOAPEnv);
//    }

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

    public void handlePluginException(String requestErr) throws AxiataException {

        Gson gson = new GsonBuilder().serializeNulls().create();
        RequestError reqerror = gson.fromJson(requestErr, RequestError.class);
        if (reqerror == null) {
            return;
        }

        String messagid = null;
        String variables = null;
        if (reqerror.getPolicyException() != null) {
            messagid = reqerror.getPolicyException().getMessageId();
            variables = reqerror.getPolicyException().getVariables();
        } else if (reqerror.getPolicyException() != null) {
            messagid = reqerror.getServiceException().getMessageId();
            variables = reqerror.getServiceException().getVariables();
        } else {
            return;
        }
        throw new AxiataException(messagid, "", new String[]{variables});

    }

    private String storeSubscription(MessageContext context) throws AxisFault {
        String subscription = null;

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            try {
                Map headersMap = (Map) headers;
                String jwtparam = (String) headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                subscription = jwtobj.getString("http://wso2.org/claims/subscriber");

            } catch (JSONException ex) {
                throw new AxisFault("Error retriving application id");
            }
        }

        return subscription;
    }

    private boolean isAggregator(MessageContext context) throws AxisFault {
        boolean aggregator = false;

        try {
            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
            Object headers = axis2MessageContext.getProperty(
                    org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
            if (headers != null && headers instanceof Map) {
                Map headersMap = (Map) headers;
                String jwtparam = (String) headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                String claimaggr = jwtobj.getString("http://wso2.org/claims/role");
                if (claimaggr != null) {
                    String[] allowedRoles = claimaggr.split(",");
                    for (int i = 0; i < allowedRoles.length; i++) {
                        if (allowedRoles[i].contains(AxiataConstants.AGGRIGATOR_ROLE)) {
                            aggregator = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("Error retrive aggregator");
        }

        return aggregator;
    }
    
    private void ValidDatePaymentCategory(JSONObject chargingdmeta, List<String> lstCategories) throws JSONException {
        boolean isvalid = false;
        String chargeCategory = "";
        if ( (!chargingdmeta.isNull("purchaseCategoryCode")) && (!chargingdmeta.getString("purchaseCategoryCode").isEmpty()) ) {
            
             chargeCategory = chargingdmeta.getString("purchaseCategoryCode");
            for (String d : lstCategories) {
                if (d.equalsIgnoreCase(chargeCategory)) {
                    isvalid = true;
                    break;
                }
            }
        } else {
            isvalid = true;
        }
        
        if (!isvalid) {
            throw new AxiataException("POL0001", "A policy error occurred. Error code is %1", new String[]{"Invalid purchaseCategoryCode : "+chargeCategory});
        }
        
    }

    private boolean checkSpendLimit(String msisdn, String operator, MessageContext mc) throws AxataDBUtilException {
        AuthenticationContext authContext = APISecurityUtils.getAuthenticationContext(mc);
        String consumerKey = "";
        if (authContext != null) {
            consumerKey = authContext.getConsumerKey();
        }

        SpendLimitHandler spendLimitHandler = new SpendLimitHandler();
        if(spendLimitHandler.isMSISDNSpendLimitExceeded(msisdn)){
            throw new AxiataException("POL1001", "The %1 charging limit for this user has been exceeded", new String[]{"daily"});
        } else if(spendLimitHandler.isApplicationSpendLimitExceeded(consumerKey)){
            throw new AxiataException("POL1001", "The %1 charging limit for this application has been exceeded", new String[]{"daily"});
        } else if(spendLimitHandler.isOperatorSpendLimitExceeded(operator)){
            throw new AxiataException("POL1001", "The %1 charging limit for this operator has been exceeded", new String[]{"daily"});
        }

        return true;

    }       */
}