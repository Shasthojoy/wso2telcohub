package com.axiata.dialog.mife.mediator.impl.ussd;

import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class USSDHandlerFactory {

    private static Log log = LogFactory.getLog(USSDHandlerFactory.class);
    public static USSDHandler createHandler(String ResourceURL, USSDExecutor executor) {
        String sendUSSDKeyString = "outbound";
        String retrieveUSSDString = "inbound";
        String moUssdSubsciption = "inbound/subscriptions";

        String lastWord = ResourceURL.substring(ResourceURL.lastIndexOf("/") + 1);
        RequestType apiType;
        USSDHandler handler = null;

        if (ResourceURL.toLowerCase().contains(moUssdSubsciption.toLowerCase())) {
             handler = new MOUSSDSubscribeHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(sendUSSDKeyString.toLowerCase())) {
            apiType = RequestType.SEND_USSD;
            handler = new SendUSSDHandler(executor);
        } else if (ResourceURL.toLowerCase().contains(retrieveUSSDString.toLowerCase())) {
            apiType = RequestType.RETRIEVE_USSD;
            handler = new USSDInboundHandler(executor);
        } else {
            throw new AxiataException("SVC0002", "", new String[]{null});
        }
//        return apiType;
        return handler;
    }

    private enum RequestType {
        SEND_USSD,
        RETRIEVE_USSD
    }
}
