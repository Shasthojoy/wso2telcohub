package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.mife.mediator.entity.DeliveryInfo;
import com.axiata.dialog.mife.mediator.entity.DeliveryInfoList;
import com.axiata.dialog.mife.mediator.entity.QuerySMSStatusResponse;
import com.axiata.dialog.mife.mediator.entity.SendSMSResponse;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class ResponseHandler {
    private Log log = LogFactory.getLog(ResponseHandler.class);

    public String makeSmsSendResponse(MessageContext mc, String requestBody, Map<String, SendSMSResponse>
            responseMap, String requestid) {
        log.debug("Building Send SMS Response: " + requestBody);

        Gson gson = new GsonBuilder().create();
        SendSMSResponse finalResponse = gson.fromJson(requestBody, SendSMSResponse.class);

        DeliveryInfoList deliveryInfoListObj = new DeliveryInfoList();
        List<DeliveryInfo> deliveryInfoList = new ArrayList<DeliveryInfo>(responseMap.size());

        for (Map.Entry<String, SendSMSResponse> entry : responseMap.entrySet()) {
            SendSMSResponse smsResponse = entry.getValue();
            if (smsResponse != null) {
                DeliveryInfoList resDeliveryInfoList = smsResponse.getOutboundSMSMessageRequest().getDeliveryInfoList();
                DeliveryInfo[] resDeliveryInfos = resDeliveryInfoList.getDeliveryInfo();
                Collections.addAll(deliveryInfoList, resDeliveryInfos);
            } else {
                DeliveryInfo deliveryInfo = new DeliveryInfo();
                deliveryInfo.setAddress(entry.getKey());
                deliveryInfo.setDeliveryStatus("DeliveryImpossible");
                deliveryInfoList.add(deliveryInfo);
            }
        }
        deliveryInfoListObj.setDeliveryInfo(deliveryInfoList.toArray(new DeliveryInfo[deliveryInfoList.size()]));

        String senderAddress = finalResponse.getOutboundSMSMessageRequest().getSenderAddress();
        try {
            senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        String resourceURL = getResourceURL(mc, senderAddress) +"/requests/"+ requestid;

        deliveryInfoListObj.setResourceURL(resourceURL + "/deliveryInfos");
        finalResponse.getOutboundSMSMessageRequest().setDeliveryInfoList(deliveryInfoListObj);
        finalResponse.getOutboundSMSMessageRequest().setResourceURL(resourceURL);

        ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("HTTP_SC", 201);

        //Set Location Header
        Object headers = ((Axis2MessageContext) mc).getAxis2MessageContext().getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            headersMap.put("Location", resourceURL);
        }

        return gson.toJson(finalResponse);
    }

    private String getResourceURL(MessageContext mc, String senderAddress) {
        Util.getPropertyFile();
        String resourceURL = Util.getApplicationProperty("sendSMSResourceURL");
        if (resourceURL != null && !resourceURL.isEmpty()) {
            resourceURL = resourceURL.substring(1, resourceURL.length() - 1) + senderAddress;
        } else {
            resourceURL = (String) mc.getProperty("REST_URL_PREFIX") + mc.getProperty("REST_FULL_REQUEST_PATH");
            resourceURL = resourceURL.substring(0, resourceURL.indexOf("/requests"));
        }
        return resourceURL;
    }

    public String makePaymentResponse(String jsonBody, String requestid) throws JSONException {

        String jsonPayload = null;

        Gson gson = new GsonBuilder().serializeNulls().create();
        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        JSONObject objPay = jsonObj.getJSONObject("amountTransaction");
        try {            
            objPay.put("clientCorrelator", objPay.get("clientCorrelator").toString().split(":")[0] );
            String resourceUrl = objPay.getString("resourceUrl");
            objPay.put("resourceUrl", AxiataUID.resourceURL(resourceUrl, requestid));
            
        } catch (Exception e) {
        }
        return jsonObj.toString();
    }

    public String makeQuerySmsStatusResponse(MessageContext mc, String senderAddress, String requestid, Map<String,
            QuerySMSStatusResponse> responseMap) {
        log.debug("Building Query SMS Status Response");

        Gson gson = new GsonBuilder().create();
        QuerySMSStatusResponse finalResponse = new QuerySMSStatusResponse();

        DeliveryInfoList deliveryInfoListObj = new DeliveryInfoList();
        List<DeliveryInfo> deliveryInfoList = new ArrayList<DeliveryInfo>(responseMap.size());

        for (Map.Entry<String, QuerySMSStatusResponse> entry : responseMap.entrySet()) {
            QuerySMSStatusResponse statusResponse = entry.getValue();
            if (statusResponse != null) {
                DeliveryInfo[] resDeliveryInfos = statusResponse.getDeliveryInfoList().getDeliveryInfo();
                Collections.addAll(deliveryInfoList, resDeliveryInfos);
            } else {
                DeliveryInfo deliveryInfo = new DeliveryInfo();
                deliveryInfo.setAddress(entry.getKey());
                deliveryInfo.setDeliveryStatus("DeliveryImpossible");
                deliveryInfoList.add(deliveryInfo);
            }
        }
        deliveryInfoListObj.setDeliveryInfo(deliveryInfoList.toArray(new DeliveryInfo[deliveryInfoList.size()]));
        try {
            senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        String resourceURL = getResourceURL(mc, senderAddress) + "/requests/" + requestid + "/deliveryInfos";
        deliveryInfoListObj.setResourceURL(resourceURL);

        finalResponse.setDeliveryInfoList(deliveryInfoListObj);

        ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("HTTP_SC", 200);
        ((Axis2MessageContext) mc).getAxis2MessageContext().setProperty("messageType", "application/json");
        return gson.toJson(finalResponse);
    }
    
}
