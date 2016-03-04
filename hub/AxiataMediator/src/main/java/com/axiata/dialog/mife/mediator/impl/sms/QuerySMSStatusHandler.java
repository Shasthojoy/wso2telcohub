package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.ResponseHandler;
import com.axiata.dialog.mife.mediator.entity.DeliveryInfo;
import com.axiata.dialog.mife.mediator.entity.QuerySMSStatusResponse;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateDeliveryStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuerySMSStatusHandler implements SMSHandler {

    private Log log = LogFactory.getLog(QuerySMSStatusHandler.class);

    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private SMSExecutor executor;
    private AxiataDbService dbservice;
    private ResponseHandler responseHandler;

    private String senderAddress = null;
    private String requestId = null;

    public QuerySMSStatusHandler(SMSExecutor executor) {
        occi = new OriginatingCountryCalculatorIDD();
        this.executor = executor;
        dbservice = new AxiataDbService();
        responseHandler = new ResponseHandler();
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        context.setProperty("mife.prop.operationType", 202);
        if (!httpMethod.equalsIgnoreCase("GET")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }
        IServiceValidate validator = new ValidateDeliveryStatus();
        validator.validateUrl(requestPath);

        loadRequestParams();
        validator.validate(new String[]{senderAddress, requestId});

        return true;
    }

    @Override
    public boolean handle(MessageContext context) throws Exception {
//        String axiataReqid = AxiataUID.getUniqueID(AxiataType..getCode(), context, executor.getApplicationid());


        Map<String, String> requestIdMap = dbservice.getSmsRequestIds(requestId, senderAddress);
        Map<String, QuerySMSStatusResponse> responseMap = sendStatusQueries(context,
                requestIdMap, senderAddress);
        if (Util.isAllNull(responseMap.values())) {
            throw new AxiataException("SVC0001", "", new String[]{"Could not complete querying SMS statuses"});
        }
        executor.removeHeaders(context);
        String responsePayload = responseHandler.makeQuerySmsStatusResponse(context, senderAddress, requestId, responseMap);
        executor.setResponse(context, responsePayload);

        return true;
    }

    private Map<String, QuerySMSStatusResponse> sendStatusQueries(MessageContext context, Map<String, String>
            requestIdMap, String senderAddr) throws Exception {

        String resourcePathPrefix = "/outbound/" + URLEncoder.encode(senderAddr, "UTF-8") + "/requests/";
        Map<String, QuerySMSStatusResponse> statusResponses = new HashMap<String, QuerySMSStatusResponse>();
        for (Map.Entry<String, String> entry : requestIdMap.entrySet()) {
            String address = entry.getKey();
            String reqId = entry.getValue();

            if (reqId != null) {
                context.setProperty(AxiataConstants.USER_MSISDN, address.substring(5));
                OperatorEndpoint endpoint = null;
                String resourcePath = resourcePathPrefix + reqId + "/deliveryInfos";
                if (ValidatorUtils.getValidatorForSubscription(context).validate(context)) {
                    endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), API_TYPE, resourcePath,
                            true, executor.getValidoperators());
                }
                String sending_add = endpoint.getEndpointref().getAddress();
                log.info("sending endpoint found: " + sending_add);

                String responseStr = executor.makeGetRequest(endpoint, sending_add, resourcePath, true, context);
                QuerySMSStatusResponse statusResponse = parseJsonResponse(responseStr);
                statusResponses.put(address, statusResponse);
            } else {
                statusResponses.put(address, null);
            }
        }
        return statusResponses;
    }

    private QuerySMSStatusResponse parseJsonResponse(String responseString) {

        Gson gson = new GsonBuilder().create();
        QuerySMSStatusResponse response;
        try {
            response = gson.fromJson(responseString, QuerySMSStatusResponse.class);
            if (response.getDeliveryInfoList() == null) {
                return null;
            }
        } catch (JsonSyntaxException e) {
            return null;
        }
        return response;
    }

    private void loadRequestParams() throws UnsupportedEncodingException {
        String reqPath = URLDecoder.decode(executor.getSubResourcePath().replace("+","%2B"), "UTF-8");
        Pattern pattern = Pattern.compile("outbound/(.+?)/requests/(.+?)/");
        Matcher matcher = pattern.matcher(reqPath);
        while (matcher.find()) {
            senderAddress = matcher.group(1);
            requestId = matcher.group(2);
        }
    }
}
