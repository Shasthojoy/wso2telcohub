package com.aircel.ussd.util;

import com.axiata.dialog.oneapi.validation.AxiataException;

public class ResourceURLUtil {

    private String apiType = "";
    private String processClass = "";

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

        String sendSMSkeyString = "outbound";
        String sendSMSkeyStringRequest = "requests";
        String retriveSMSString = "inbound";
        String subscriptionKeyString = "subscriptions";
        String regKeyString = "registrations";
        String delivaryInfoKeyString = "deliveryInfos";
        String delivaryNotifyString = "DeliveryInfoNotification";


        String lastWord = ResourceURL.substring(ResourceURL.lastIndexOf("/") + 1);

        if (ResourceURL.toLowerCase().contains(sendSMSkeyString.toLowerCase())                
                && (!lastWord.equals(delivaryInfoKeyString))) {
            apiType = "ussdreq";
        } else if (ResourceURL.toLowerCase().contains(retriveSMSString.toLowerCase())
                && lastWord.equals(subscriptionKeyString)) {
            apiType = "start_outbound_subscription";
        } else if (ResourceURL.toLowerCase().contains(retriveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())
                && (!lastWord.equals(subscriptionKeyString))) {
            apiType = "stop_outbound_subscription";
        } else {
            throw new AxiataException("SVC0002", "",new String[]{null});
        }

        return apiType;
    }
}
