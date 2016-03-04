package com.axiata.dialog.mife.mediator.impl.sms;

import org.json.JSONObject;

import com.axiata.dialog.mife.mediator.impl.sms.nb.NBOutboundSMSSubscriptionsHandler;
import com.axiata.dialog.mife.mediator.impl.sms.sb.SBOutboundSMSSubscriptionsHandler;
import com.axiata.dialog.oneapi.validation.AxiataException;

public class SMSHandlerFactory {

    public static SMSHandler createHandler(String ResourceURL, SMSExecutor executor) {
        String sendSMSKeyString = "outbound";
        String sendSMSKeyStringRequest = "requests";
        String retrieveSMSString = "inbound";
        String subscriptionKeyString = "subscriptions";
        String regKeyString = "registrations";
        String deliveryInfoKeyString = "deliveryInfos";
        String deliveryNotifyString = "DeliveryInfoNotification";

        String lastWord = ResourceURL.substring(ResourceURL.lastIndexOf("/") + 1);
        RequestType apiType;
        SMSHandler handler = null;

        if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase())
                && ResourceURL.toLowerCase().contains(sendSMSKeyStringRequest.toLowerCase())
                && (!lastWord.equals(deliveryInfoKeyString))) {
            apiType = RequestType.SEND_SMS;
            handler = new SendSMSHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(retrieveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(regKeyString.toLowerCase())) {
            apiType = RequestType.RETRIEVE_SMS;
            handler = new RetrieveSMSHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(retrieveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())) {
            apiType = RequestType.RETRIEVE_SMS_SUBSCRIPTIONS;
            handler = new RetrieveSMSSubscriptionsHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(deliveryNotifyString.toLowerCase())) {
            apiType = RequestType.SMS_INBOUND_NOTIFICATIONS;
            handler = new SMSInboundNotificationsHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase())
                && lastWord.equals(subscriptionKeyString)) {
            apiType = RequestType.START_OUTBOUND_SUBSCRIPTION;
            if (ResourceURL.toLowerCase().contains("/outbound/subscriptions")) {
                handler = new NBOutboundSMSSubscriptionsHandler(executor);
                //log.debug("Invoke NBOutboundSMSSubscriptionsHandler");
            } else {
                handler = findDeliveryNotificationSubscriptionType(executor);
            }
        } else if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())
                && (!lastWord.equals(subscriptionKeyString))) {
            apiType = RequestType.STOP_OUTBOUND_SUBSCRIPTION;
        } else if (lastWord.equals(deliveryInfoKeyString)) {
            apiType = RequestType.QUERY_SMS;
            handler = new QuerySMSStatusHandler(executor);
        } else {
            throw new AxiataException("SVC0002", "", new String[]{null});
        }
//        return apiType;
        return handler;
    }
    
    private static SMSHandler findDeliveryNotificationSubscriptionType(SMSExecutor executor) {

        SMSHandler handler = null;
        try {
            JSONObject objJSONObject = executor.getJsonBody();
            JSONObject objDeliveryReceiptSubscription = objJSONObject.getJSONObject("deliveryReceiptSubscription");
            if (!objDeliveryReceiptSubscription.isNull("operatorCode")) {
                handler = new SBOutboundSMSSubscriptionsHandler(executor);
                //log.debug("Invoke SBOutboundSMSSubscriptionsHandler");
            } else {
                handler = new OutboundSMSSubscriptionsHandler(executor);
                //log.debug("Invoke OutboundSMSSubscriptionsHandler");
            }
        } catch (Exception e) {
            //log.error("Error in findDeliveryNotificationSubscriptionType : " + e.getMessage());
            throw new AxiataException("SVC0002", "", new String[]{null});
        }
        
        return handler;
    }
    
    private enum RequestType {
        SEND_SMS,
        RETRIEVE_SMS,
        RETRIEVE_SMS_SUBSCRIPTIONS,
        SMS_INBOUND_NOTIFICATIONS,
        START_OUTBOUND_SUBSCRIPTION,
        STOP_OUTBOUND_SUBSCRIPTION,
        QUERY_SMS
    }
}
