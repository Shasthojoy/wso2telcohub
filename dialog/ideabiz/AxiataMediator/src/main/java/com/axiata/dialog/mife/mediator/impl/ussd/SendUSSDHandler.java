package com.axiata.dialog.mife.mediator.impl.ussd;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;


/**
 * Created by wasanthah on 3/31/15.
 */

public class SendUSSDHandler implements USSDHandler {

    private Log log = LogFactory.getLog(SendUSSDHandler.class);

    private static final String API_TYPE = "ussd";
    private OriginatingCountryCalculatorIDD occi;
    private USSDExecutor executor;
    private AxiataDbService dbservice;

    public SendUSSDHandler(USSDExecutor executor) {
        occi = new OriginatingCountryCalculatorIDD();
        this.executor = executor;
        dbservice = new AxiataDbService();
    }


    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {

        JSONObject jsonBody = executor.getJsonBody();
        String address = jsonBody.getJSONObject("outboundUSSDMessageRequest").getString("address");
        String notifyUrl = jsonBody.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").getString("notifyURL");
        String msisdn = address.substring(5);

        Integer axiataid = dbservice.ussdRequestEntry(notifyUrl);

        String subsEndpoint = Util.getApplicationProperty("ussdGatewayEndpoint") + "/ussd/v1/inbound/"+axiataid;
        jsonBody.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", subsEndpoint);

        context.setProperty(AxiataConstants.USER_MSISDN, msisdn);
        OperatorEndpoint endpoint = null;
        if (ValidatorUtils.getValidatorForSubscription(context).validate(context)) {
            endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), API_TYPE, executor.getSubResourcePath(), false,
                    executor.getValidoperators());
        }
        String sending_add = endpoint.getEndpointref().getAddress();
        log.info("sending endpoint found: " + sending_add);

        executor.removeHeaders(context);

        String responseStr = executor.makeRequest(endpoint, sending_add, jsonBody.toString(), true, context);
        executor.handlePluginException(responseStr);

        executor.setResponse(context, responseStr);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");

        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {

        if (!httpMethod.equalsIgnoreCase("POST")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }

        //ValidateUssdSend validator = new ValidateUssdSend();
        //validator.validateUrl(requestPath);
        //validator.validate(jsonBody.toString());

        return true;
    }
}
