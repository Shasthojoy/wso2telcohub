package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.mife.mediator.impl.sms.nb.NBOutboundSMSSubscriptionsHandler;
import com.axiata.dialog.mife.mediator.impl.sms.nb.NBRetrieveSMSHandler;
import com.axiata.dialog.mife.mediator.impl.sms.sb.SBOutboundSMSSubscriptionsHandler;
import com.axiata.dialog.mife.mediator.impl.sms.sb.SBRetrieveSMSHandler;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

public class SMSHandlerFactory {

    private static Log log = LogFactory.getLog(SMSHandlerFactory.class);

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
        String httpMethod = executor.getHttpMethod();

        if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase())
                && ResourceURL.toLowerCase().contains(sendSMSKeyStringRequest.toLowerCase())
                && (!lastWord.equals(deliveryInfoKeyString))) {
            apiType = RequestType.SEND_SMS;
            handler = new SendSMSHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(retrieveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(regKeyString.toLowerCase())) {
            apiType = RequestType.RETRIEVE_SMS;
            if (httpMethod.equalsIgnoreCase("post")) {
                handler = new NBRetrieveSMSHandler(executor);
                log.debug("Invoke NBRetrieveSMSHandler");
            } else {
                String[] resourceURLParts = ResourceURL.split("/");
                if (resourceURLParts.length == 6) {
                    handler = new SBRetrieveSMSHandler(executor);
                    log.debug("Invoke SBRetrieveSMSHandler");
                } else if (resourceURLParts.length == 5) {
                    handler = new RetrieveSMSHandler(executor);
                    log.debug("Invoke RetrieveSMSHandler");
                }
            }
            //handler = new RetrieveSMSHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(retrieveSMSString.toLowerCase())
                && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase())) {
            apiType = RequestType.RETRIEVE_SMS_SUBSCRIPTIONS;
            handler = new RetrieveSMSSubscriptionsHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(deliveryNotifyString.toLowerCase())) {
            apiType = RequestType.SMS_INBOUND_NOTIFICATIONS;
            //handler = new SMSInboundNotificationsHandler(executor);

            handler = findSMSInboundNotificationsHandlerType(executor);

        } else if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase())
                && lastWord.equals(subscriptionKeyString)) {
            apiType = RequestType.START_OUTBOUND_SUBSCRIPTION;
            if (ResourceURL.toLowerCase().contains("/outbound/subscriptions")) {
                handler = new NBOutboundSMSSubscriptionsHandler(executor);
                log.debug("Invoke NBOutboundSMSSubscriptionsHandler");
            } else {
                handler = findDeliveryNotificationSubscriptionType(executor);
            }
            //handler = new OutboundSMSSubscriptionsHandler(executor);//ADDED
        } else if (ResourceURL.toLowerCase().contains(sendSMSKeyString.toLowerCase()) && ResourceURL.toLowerCase().contains(subscriptionKeyString.toLowerCase()) && (!lastWord.equals(subscriptionKeyString))) {
            apiType = RequestType.STOP_OUTBOUND_SUBSCRIPTION;
            handler = new StopOutboundSMSSubscriptionsHandler(executor);
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
                log.debug("Invoke SBOutboundSMSSubscriptionsHandler");
            } else {
                handler = new OutboundSMSSubscriptionsHandler(executor);
                log.debug("Invoke OutboundSMSSubscriptionsHandler");
            }
        } catch (Exception e) {
            log.error("Error in findDeliveryNotificationSubscriptionType : " + e.getMessage());
            throw new AxiataException("SVC0002", "", new String[]{null});
        }

        return handler;
    }

    private static SMSHandler findSMSInboundNotificationsHandlerType(SMSExecutor executor) {
        SMSHandler handler = null;
        try {
            JSONObject objJSONObject = executor.getJsonBody();

            if (objJSONObject.isNull("inboundSMSMessageNotification")) {
                handler = new SMSOutboundNotificationsHandler(executor);
            } else if (objJSONObject.isNull("deliveryInfoNotification")) {
                handler = new SMSInboundNotificationsHandler(executor);
            }
            /*JSONObject objInboundNotificationsHandler = objJSONObject.getJSONObject("deliveryInfoNotification");
             if (objInboundNotificationsHandler!=null) {} else {
             handler = new SMSInboundNotificationsHandler(executor); 
             }*/

        } catch (Exception e) {
            throw new AxiataException("SVC0005", "Error when selecting Delivery reciept handler", new String[]{null});
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
