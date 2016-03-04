package com.axiata.dialog.mife.mediator.impl.payment;

import com.axiata.dialog.dbutils.AxataDBUtilException;
import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.events.data.handler.SpendLimitHandler;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.AxiataRequestExecutor;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.ResponseHandler;
import com.axiata.dialog.mife.mediator.internal.AggregatorValidator;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Base64Coder;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.impl.ValidatePaymentCharge;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.AuthenticationContext;

import java.util.List;
import java.util.Map;

public class PaymentExecutor extends AxiataRequestExecutor {

    private Log log = LogFactory.getLog(PaymentExecutor.class);

    private static final String API_TYPE = "payment";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private ResponseHandler responseHandler;

    public PaymentExecutor() {
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
        responseHandler = new ResponseHandler();
    }

    @Override
    public boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception {
        try {
            return chargeUserExec(context);
        } catch (JSONException e) {
            log.error(e.getMessage());
            throw new AxiataException("SVC0001", "", new String[]{"Request is missing required URI components"});
        }
//        return false;
    }

    @Override
    public boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        if (!httpMethod.equalsIgnoreCase("POST")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }

        ValidatePaymentCharge validator = new ValidatePaymentCharge();
        validator.validateUrl(requestPath);
        validator.validate(jsonBody.toString());

        return true;
    }

    private boolean chargeUserExec(MessageContext mc) throws Exception {
        String requestid = AxiataUID.getUniqueID(AxiataType.PAYMENT.getCode(), mc, getApplicationid());
        JSONObject jsonBody = getJsonBody();
        String endUserId = jsonBody.getJSONObject("amountTransaction").getString("endUserId");
        String msisdn = endUserId.substring(5);
        mc.setProperty(AxiataConstants.USER_MSISDN, msisdn);
        OperatorEndpoint endpoint = null;
        if (ValidatorUtils.getValidatorForSubscription(mc).validate(mc)) {
            endpoint = occi.getAPIEndpointsByMSISDN(endUserId.replace("tel:", ""), API_TYPE, getSubResourcePath(), false,
                    getValidoperators());
        }
        String sending_add = endpoint.getEndpointref().getAddress();
        log.info("sending endpoint found: " + sending_add);

        // Check if Spend Limits are exceeded
        checkSpendLimit(msisdn, endpoint.getOperator(), mc);

//                routeToEndpoint(endpoint, mc, sending_add);
        //apend request id to client co-relator
        JSONObject clientclr = jsonBody.getJSONObject("amountTransaction");
        clientclr.put("clientCorrelator", clientclr.getString("clientCorrelator") + ":" + requestid);

        JSONObject chargingdmeta = clientclr.getJSONObject("paymentAmount").getJSONObject("chargingMetaData");


        String subscriber = storeSubscription(mc);
        boolean isaggrigator = isAggregator(mc);

        if (isaggrigator) {
            //JSONObject chargingdmeta = clientclr.getJSONObject("paymentAmount").getJSONObject("chargingMetaData");
            if (!chargingdmeta.isNull("onBehalfOf")) {
                new AggregatorValidator().validateMerchant(Integer.valueOf(getApplicationid()), endpoint.getOperator
                        (), subscriber, chargingdmeta.getString("onBehalfOf"));
            }
        }

        //validate payment categoreis
        List<String> validCategoris = dbservice.getValidPayCategories();
        validatePaymentCategory(chargingdmeta, validCategoris);

        String responseStr = makeRequest(endpoint, sending_add, jsonBody.toString(), true, mc);

        // Payment Error Exception Correction
        String base = str_piece(str_piece(responseStr, '{', 2), ':', 1);

        String errorReturn = "\"" + "requestError" + "\"";

        removeHeaders(mc);

        if (base.equals(errorReturn)) {
            handlePluginException(responseStr);
        }

        responseStr = responseHandler.makePaymentResponse(responseStr, requestid);

        //set response re-applied
        setResponse(mc, responseStr);

        return true;
    }

    private boolean checkSpendLimit(String msisdn, String operator, MessageContext mc) throws AxataDBUtilException {
        AuthenticationContext authContext = APISecurityUtils.getAuthenticationContext(mc);
        String consumerKey = "";
        if (authContext != null) {
            consumerKey = authContext.getConsumerKey();
        }

        SpendLimitHandler spendLimitHandler = new SpendLimitHandler();
        if (spendLimitHandler.isMSISDNSpendLimitExceeded(msisdn)) {
            throw new AxiataException("POL1001", "The %1 charging limit for this user has been exceeded", new
                    String[]{"daily"});
        } else if (spendLimitHandler.isApplicationSpendLimitExceeded(consumerKey)) {
            throw new AxiataException("POL1001", "The %1 charging limit for this application has been exceeded", new
                    String[]{"daily"});
        } else if (spendLimitHandler.isOperatorSpendLimitExceeded(operator)) {
            throw new AxiataException("POL1001", "The %1 charging limit for this operator has been exceeded", new
                    String[]{"daily"});
        }
        return true;
    }

    private String storeSubscription(MessageContext context) throws AxisFault {
        String subscription = null;

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context)
                .getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            try {
                Map headersMap = (Map) headers;
                String jwtparam = (String) headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                subscription = jwtobj.getString("http://wso2.org/claims/subscriber");

            } catch (JSONException ex) {
                throw new AxisFault("Error retriving application id");
            }
        }

        return subscription;
    }

    private boolean isAggregator(MessageContext context) throws AxisFault {
        boolean aggregator = false;

        try {
            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context)
                    .getAxis2MessageContext();
            Object headers = axis2MessageContext.getProperty(
                    org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
            if (headers != null && headers instanceof Map) {
                Map headersMap = (Map) headers;
                String jwtparam = (String) headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                String claimaggr = jwtobj.getString("http://wso2.org/claims/role");
                if (claimaggr != null) {
                    String[] allowedRoles = claimaggr.split(",");
                    for (int i = 0; i < allowedRoles.length; i++) {
                        if (allowedRoles[i].contains(AxiataConstants.AGGRIGATOR_ROLE)) {
                            aggregator = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("Error retrive aggregator");
        }

        return aggregator;
    }

    private void validatePaymentCategory(JSONObject chargingdmeta, List<String> lstCategories) throws JSONException {
        boolean isvalid = false;
        String chargeCategory = "";
        if ((!chargingdmeta.isNull("purchaseCategoryCode")) && (!chargingdmeta.getString("purchaseCategoryCode")
                .isEmpty())) {

            chargeCategory = chargingdmeta.getString("purchaseCategoryCode");
            for (String d : lstCategories) {
                if (d.equalsIgnoreCase(chargeCategory)) {
                    isvalid = true;
                    break;
                }
            }
        } else {
            isvalid = true;
        }

        if (!isvalid) {
            throw new AxiataException("POL0001", "A policy error occurred. Error code is %1", new String[]{"Invalid " +
                    "purchaseCategoryCode : " + chargeCategory});
        }
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
}
