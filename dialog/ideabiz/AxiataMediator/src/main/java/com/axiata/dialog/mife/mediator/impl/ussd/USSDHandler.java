package com.axiata.dialog.mife.mediator.impl.ussd;

import org.apache.synapse.MessageContext;
import org.json.JSONObject;

public interface USSDHandler {

    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception ;

    public boolean handle(MessageContext context) throws Exception;
}
