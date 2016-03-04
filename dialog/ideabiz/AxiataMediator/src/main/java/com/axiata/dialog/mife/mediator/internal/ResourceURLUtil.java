package com.axiata.dialog.mife.mediator.internal;

import com.axiata.dialog.oneapi.validation.AxiataException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class ResourceURLUtil {

    private String apiType = "";
    private String processClass = "";

    public String getProcessClass(String ResourceURL) {

        String apiType = this.findAPIType(ResourceURL);

        if (apiType.equals("send_sms")) {
            processClass = "Route";
        } else if (apiType.equals("start_outbound_subscription")) {
            //TODO
            //processClass = "Forward";
        } else if (apiType.equals("stop_outbound_subscription")) {
            //TODO
            //processClass = "Forward";
        } else if (apiType.equals("query_sms")) {
            //TODO
            //processClass = "";
        } else if (apiType.equals("retrive_sms")) {
            processClass = "Forward";
        } else if (apiType.equals("retrive_sms_subscriptions")) {
            processClass = "Forward";
        } else if (apiType.equals("payment")) {
            processClass = "Redirect";
        } else if (apiType.equals("location")) {
            processClass = "Redirect";
        } else if (apiType.equals("sms_inbound_notifications")) {
            processClass = "Forward";
        } else {
            throw new AxiataException("SVC0002", "",new String[]{null});
        }

        return processClass;
    }

    public String getAPIType(String ResourceURL) {

        String apiType = this.findAPIType(ResourceURL);


        return apiType;
    }

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

    private String findAPIType(String ResourceURL) {

        String paymentKeyString = "transactions";
        String sendSMSkeyString = "outbound";
        String sendSMSkeyStringRequest = "requests";
        String retriveSMSString = "inbound";
        String subscriptionKeyString = "subscriptions";
        String regKeyString = "registrations";
        String delivaryInfoKeyString = "deliveryInfos";
        String delivaryNotifyString = "DeliveryInfoNotification";
        String locationString = "location";


        String lastWord = ResourceURL.substring(ResourceURL.lastIndexOf("/") + 1);

        if (ResourceURL.toLowerCase().contains(sendSMSkeyString.toLowerCase())
                && ResourceURL.toLowerCase().contains(sendSMSkeyStringRequest.toLowerCase())
                && (!lastWord.equals(delivaryInfoKeyString))) {
            apiType = "send_sms";
        } else if (ResourceURL.toLowerCase().contains(sendSMSkeyString.toLowerCase())
                && lastWord.equals(subscriptionKeyString)) {
            apiType = "start_outbound_subscription";
        } else if (ResourceURL.toLowerCase().contains(sendSMSkeyString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())
                && (!lastWord.equals(subscriptionKeyString))) {
            apiType = "stop_outbound_subscription";
        } else if (lastWord.equals(delivaryInfoKeyString)) {
            apiType = "query_sms";
        } else if (ResourceURL.toLowerCase().contains(retriveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(regKeyString.toLowerCase())) {
            apiType = "retrive_sms";
        } else if (ResourceURL.toLowerCase().contains(retriveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())) {
            apiType = "retrive_sms_subscriptions";
        } else if (ResourceURL.toLowerCase().contains(paymentKeyString.toLowerCase())) {
            apiType = "payment";
        } else if (ResourceURL.toLowerCase().contains(delivaryNotifyString.toLowerCase())) {
            apiType = "sms_inbound_notifications";        
        } else if (ResourceURL.toLowerCase().contains(locationString.toLowerCase())) {
            apiType = "location";
        } else {
            throw new AxiataException("SVC0002", "",new String[]{null});
        }

        return apiType;
    }
    public String[] getParamValues(String url) throws UnsupportedEncodingException {
        
       List<String> paramlist  = new ArrayList();
        
        String[] urlParts = url.split("\\?");
        if (urlParts.length < 2) {
            paramlist.add(URLDecoder.decode(urlParts[1].split("=")[1],"UTF-8"));            
        } else {
            String query = urlParts[1];
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String value = "";
                if (pair.length > 1) {
                    paramlist.add(URLDecoder.decode(pair[1],"UTF-8"));
                }
            }
        }
        
        return paramlist.toArray(new String[paramlist.size()]);
    }
   
}
