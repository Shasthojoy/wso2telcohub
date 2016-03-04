package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.mife.mediator.AxiataRequestExecutor;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.json.JSONException;
import org.json.JSONObject;

public class SMSExecutor extends AxiataRequestExecutor {

    private Log log = LogFactory.getLog(SMSExecutor.class);

    private SMSHandler handler;

    @Override
    public boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception {
        try {
            SMSHandler handler = getSMSHandler(getSubResourcePath());
            return handler.handle(context);

        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AxiataException("SVC0001", "", new String[]{"Request is missing required URI components"});
        }
    }

    @Override
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {

        SMSHandler handler = getSMSHandler(requestPath);
        return handler.validate(httpMethod, requestPath, jsonBody, context);
    }

    private SMSHandler getSMSHandler(String requestPath) {
        if (handler == null) {
            handler = SMSHandlerFactory.createHandler(requestPath, this);
        }
        return handler;
    }
}
