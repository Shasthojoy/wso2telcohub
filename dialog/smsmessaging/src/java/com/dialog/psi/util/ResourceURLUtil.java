package com.dialog.psi.util;

import com.axiata.dialog.oneapi.validation.AxiataException;

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
        } else {
            throw new AxiataException("SVC0002", "",new String[]{null});
        }

        return apiType;
    }
}
