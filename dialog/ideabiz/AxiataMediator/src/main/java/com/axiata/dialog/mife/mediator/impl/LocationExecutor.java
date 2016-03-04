package com.axiata.dialog.mife.mediator.impl;

import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.AxiataRequestExecutor;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.ResourceURLUtil;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.impl.ValidateLocation;
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

public class LocationExecutor extends AxiataRequestExecutor {

    private OriginatingCountryCalculatorIDD occi;

    public LocationExecutor() {
        occi = new OriginatingCountryCalculatorIDD();
    }

    @Override
    public boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception {

        String[] params = new ResourceURLUtil().getParamValues(getSubResourcePath());
        context.setProperty(AxiataConstants.USER_MSISDN, params[0].substring(5));
        OperatorEndpoint endpoint = null;
        if (ValidatorUtils.getValidatorForSubscription(context).validate(context)) {
            endpoint = occi.getAPIEndpointsByMSISDN(params[0].replace("tel:", ""), "location", getSubResourcePath(), true,
                    getValidoperators());
        }
        String sending_add = endpoint.getEndpointref().getAddress();

        String responseStr = makeGetRequest(endpoint, sending_add, getSubResourcePath(), true, context);

        removeHeaders(context);
        handlePluginException(responseStr);
        //set response re-applied
        setResponse(context, responseStr);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("ContentType", "application/json");
        return true;
    }

    @Override
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        if (!httpMethod.equalsIgnoreCase("GET")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }
        String[] params = new ResourceURLUtil().getParamValues(requestPath);
        ValidateLocation validator = new ValidateLocation();
        validator.validateUrl(requestPath);
        validator.validate(params);

        return true;
    }
}
