package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxataDBUtilException;
import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operator;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.ResponseHandler;
import com.axiata.dialog.mife.mediator.entity.SendSMSRequest;
import com.axiata.dialog.mife.mediator.entity.SendSMSResponse;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendSMSHandler implements SMSHandler {

    private Log log = LogFactory.getLog(SendSMSHandler.class);
    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private ResponseHandler responseHandler;
    private SMSExecutor executor;
    private AxiataDbService dbservice;

    public SendSMSHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        responseHandler = new ResponseHandler();
        dbservice = new AxiataDbService();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {
        String requestid = AxiataUID.getUniqueID(AxiataType.SMSSEND.getCode(), context, executor.getApplicationid());
        //append request id to client correlator
        JSONObject jsonBody = executor.getJsonBody();
        JSONObject clientclr = jsonBody.getJSONObject("outboundSMSMessageRequest");
        clientclr.put("clientCorrelator", clientclr.getString("clientCorrelator") + ":" + requestid);

        Gson gson = new GsonBuilder().serializeNulls().create();
        SendSMSRequest subsrequest = gson.fromJson(jsonBody.toString(), SendSMSRequest.class);
        String senderAddress = subsrequest.getOutboundSMSMessageRequest().getSenderAddress();

        if (!ValidatorUtils.getValidatorForSubscription(context).validate(context)) {
            throw new AxiataException("SVC0001", "", new String[]{"Subscription Validation Unsuccessful"});
        }
        int smsCount = getSMSMessageCount(subsrequest.getOutboundSMSMessageRequest().getOutboundTextMessage().getMessage());
        context.setProperty(SouthboundPublisherConstants.RESPONSE, String.valueOf(smsCount));

        Map<String, SendSMSResponse> smsResponses = smssendmulti(context, subsrequest, jsonBody.getJSONObject("outboundSMSMessageRequest").getJSONArray("address"), API_TYPE, executor.getValidoperators());
        if (Util.isAllNull(smsResponses.values())) {
            throw new AxiataException("POL0257", "Message not delivered %1", new String[]{"Request failed. Errors "
                + "occurred while sending the request for all the destinations."});
        }
//NB publish
        executor.removeHeaders(context);
        String resPayload = responseHandler.makeSmsSendResponse(context, jsonBody.toString(), smsResponses, requestid);
        storeRequestIDs(requestid, senderAddress, smsResponses);
        executor.setResponse(context, resPayload);
        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {

        if (!httpMethod.equalsIgnoreCase("POST")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }

        context.setProperty("mife.prop.operationType", 200);

        IServiceValidate validator = new ValidateSendSms();
        validator.validateUrl(requestPath);
        validator.validate(jsonBody.toString());

        String senderName = jsonBody.getJSONObject("outboundSMSMessageRequest").optString("senderName");

        if (senderName.equals("") || senderName == null || senderName.length() < 10) {
            context.setProperty("mife.prop.merchantId", "");
            context.setProperty("mife.prop.category", "");
            context.setProperty("mife.prop.subCategory", "");
        } else {
            if (senderName.substring(0, 3).equals("000")) {
                context.setProperty("mife.prop.merchantId", "");
            } else {
                context.setProperty("mife.prop.merchantId", senderName.substring(0, 3));
            }

            if (senderName.substring(3, 6).equals("000")) {
                context.setProperty("mife.prop.category", "");
            } else {
                context.setProperty("mife.prop.category", senderName.substring(3, 6));
            }

            if (senderName.substring(6, 9).equals("000")) {
                context.setProperty("mife.prop.subCategory", "");
            } else {
                context.setProperty("mife.prop.subCategory", senderName.substring(6, 9));
            }
        }

        return true;
    }

    private Map<String, SendSMSResponse> smssendmulti(MessageContext smsmc, SendSMSRequest sendreq, JSONArray listaddr,
            String apitype, List<Operator> operators) throws Exception {

        OperatorEndpoint endpoint = null;
        String jsonStr;
        String address;
        Map<String, SendSMSResponse> smsResponses = new HashMap<String, SendSMSResponse>();
        for (int i = 0; i < listaddr.length(); i++) {

            SendSMSResponse sendSMSResponse = null;
            address = listaddr.getString(i);
            //     try {
            log.info("id : " + address);
            smsmc.setProperty(AxiataConstants.USER_MSISDN, address.substring(5));
            endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), apitype, executor
                    .getSubResourcePath(), false, operators);   //smsSend;

            List<String> sendAdr = new ArrayList<String>();
            sendAdr.add(address);
            sendreq.getOutboundSMSMessageRequest().setAddress(sendAdr);
            jsonStr = new Gson().toJson(sendreq);
            String sending_add = endpoint.getEndpointref().getAddress();
            log.info("sending endpoint found: " + sending_add);

            String responseStr = executor.makeRequest(endpoint, sending_add, jsonStr, true, smsmc);
            sendSMSResponse = parseJsonResponse(responseStr);
       //     } catch (Exception e) {
            //        log.error(e.getMessage(), e);
            //    }
            smsResponses.put(address, sendSMSResponse);
        }
        return smsResponses;
    }

    private SendSMSResponse parseJsonResponse(String responseString) {

        Gson gson = new GsonBuilder().create();
        SendSMSResponse smsResponse;
        try {
            smsResponse = gson.fromJson(responseString, SendSMSResponse.class);
            if (smsResponse.getOutboundSMSMessageRequest() == null) {
                return null;
            }
        } catch (JsonSyntaxException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return smsResponse;
    }

    private void storeRequestIDs(String requestID, String senderAddress, Map<String, SendSMSResponse> smsResponses)
            throws AxataDBUtilException {
        Map<String, String> reqIdMap = new HashMap<String, String>(smsResponses.size());
        for (Map.Entry<String, SendSMSResponse> entry : smsResponses.entrySet()) {
            SendSMSResponse smsResponse = entry.getValue();
            String pluginReqId = null;
            if (smsResponse != null) {
                String resourceURL = smsResponse.getOutboundSMSMessageRequest().getResourceURL().trim();
                String[] segments = resourceURL.split("/");
                pluginReqId = segments[segments.length - 1];
            }
            reqIdMap.put(entry.getKey(), pluginReqId);
        }
        dbservice.insertSmsRequestIds(requestID, senderAddress, reqIdMap);
    }

    private int getSMSMessageCount(String textMessage) {

        int smsCount = 0;
        try {
            int count = textMessage.length();
            log.debug("Character count of text message : " + count);
            if (count > 0) {
                int tempSMSCount = count / 160;

                int tempRem = count % 160;

                if (tempRem > 0) {
                    tempSMSCount++;
                }
                smsCount = tempSMSCount;

            }
        } catch (Exception e) {
            log.error("error in getSMSMessageCharacterCount : " + e.getMessage());
            return 0;
        }

        log.debug("SMS count : " + smsCount);
        return smsCount;
    }
}
