package com.axiata.dialog.mife.mediator.impl;


import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.AxiataRequestExecutor;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultExecutor extends AxiataRequestExecutor {

    private Log log = LogFactory.getLog(DefaultExecutor.class);

    private OriginatingCountryCalculatorIDD occi;

    public DefaultExecutor() {
        occi = new OriginatingCountryCalculatorIDD();
    }

    @Override
    public boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception {
        String msisdn = readMSISDN(context); // with +
        context.setProperty(AxiataConstants.USER_MSISDN, msisdn.substring(1)); // without +
        OperatorEndpoint endpoint = occi.getAPIEndpointsByMSISDN(msisdn, (String) context.getProperty("API_NAME"),
                getSubResourcePath(), false, getValidoperators());
        String sending_add = endpoint.getEndpointref().getAddress();
        log.info("sending endpoint found: " + sending_add);

        String responseString = null;
        if (getHttpMethod().equalsIgnoreCase("POST")) {
            responseString = makeRequest(endpoint, sending_add, getJsonBody().toString(), true, context);
        } else if (getHttpMethod().equalsIgnoreCase("GET")) {
            responseString = makeGetRequest(endpoint, sending_add, null, true, context);
        } else if (getHttpMethod().equalsIgnoreCase("DELETE")) {
            responseString = makeDeleteRequest(endpoint, sending_add, null, true, context);
        }

        removeHeaders(context);

        handlePluginException(responseString);
        setResponse(context, responseString);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");

        return true;
    }

    @Override
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        return true;
    }

    private String readMSISDN(MessageContext context) throws Exception {
        String msisdnLocation = (String) context.getProperty(AxiataConstants.MSISDN_LOCATION_PROPERTY);
        String msisdnRegex = (String) context.getProperty(AxiataConstants.MSISDN_REGEX_PROPERTY);
        String msisdnResource = null;

        if (msisdnLocation == null || msisdnRegex == null) {
            log.error("Missing required properties for DefaultExecutor. Synapse properties default.MSISDN.location " +
                    "and/or default.MSISDN.regex is missing");
            throw new Exception("Missing required properties for DefaultExecutor");
        }

        if (msisdnLocation.equalsIgnoreCase("URL")) {
            msisdnResource = URLDecoder.decode(getSubResourcePath().replace("+","%2B"), "UTF-8");
        } else if (msisdnLocation.equalsIgnoreCase("Body")) {
            msisdnResource = getJsonBody().toString();
        }

        Pattern pattern = Pattern.compile(msisdnRegex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(msisdnResource);

        String result = null;
        while (matcher.find()) {
            result = matcher.group();
            result = result.replace("tel:", "");
        }
        if (log.isDebugEnabled()) {
            log.debug("MSISDN Location - " + msisdnLocation);
            log.debug("MSISDN Regex - " + msisdnRegex);
            log.debug("MSISDN Resource Text - " + msisdnResource);
            log.debug("MSISDN Matched Result - " + result);
        }
        return result;
    }
}
