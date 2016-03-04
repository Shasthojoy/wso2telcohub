package com.axiata.dialog.mife.mediator.impl.sms;

import org.apache.synapse.MessageContext;
import org.json.JSONObject;

public interface SMSHandler {

    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception ;

    public boolean handle(MessageContext context) throws Exception;
}
