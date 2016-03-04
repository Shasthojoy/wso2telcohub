package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.APICall;
import com.axiata.dialog.mife.mediator.internal.ApiUtils;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateSBRetrieveSms;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axis2.AxisFault;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RetrieveSMSHandler implements SMSHandler {

    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private ApiUtils apiUtil;
    private SMSExecutor executor;

    public RetrieveSMSHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        apiUtil = new ApiUtils();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {

        SOAPBody body = context.getEnvelope().getBody();
        //SOAPHeader soapHeader = context.getEnvelope().getHeader();
        //System.out.println(soapHeader.toString());

        String reqType = "retrive_sms";
        String requestid = AxiataUID.getUniqueID(AxiataType.SMSRETRIVE.getCode(), context, executor.getApplicationid());
//        String appID = apiUtil.getAppID(context, reqType);
        List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),
                executor.getValidoperators());
        Collections.shuffle(endpoints);
        int batchSize = 100;
        int perOpCoLimit = batchSize / (endpoints.size());

        JSONArray results = new JSONArray();

        /**
         * TODO FIX
         */
        int count = 0;
        /**
         * TODO FIX
         */
        ArrayList<String> responses = new ArrayList<String>();
        while (results.length() < batchSize) {
            OperatorEndpoint aEndpoint = endpoints.remove(0);
            endpoints.add(aEndpoint);
            String url = aEndpoint.getEndpointref().getAddress();
            APICall ac = apiUtil.setBatchSize(url, body.toString(), reqType, perOpCoLimit);
            //String url = aEndpoint.getAddress()+getResourceUrl().replace("test_api1/1.0.0/", "");//aEndpoint
            // .getAddress() + ac.getUri();
            JSONObject obj = ac.getBody();
            String retStr = null;
            if (context.isDoingGET()) {
                retStr = executor.makeGetRequest(aEndpoint, ac.getUri(), null, true, context);
            } else {
                retStr = executor.makeRequest(aEndpoint, ac.getUri(), obj.toString(), true, context);
            }
            if (retStr == null) {
                count++;
                if (count == endpoints.size()) {
                    break;
                } else {
                    continue;
                }
            }
            JSONArray resList = apiUtil.getResults(reqType, retStr);
            if (resList != null) {
                for (int i = 0; i < resList.length(); i++) {
                    results.put(resList.get(i));
                }
                responses.add(retStr);
            }

            count++;
            if (count == (endpoints.size() * 2)) {
                break;
            }
        }
        JSONObject paylodObject = apiUtil.generateResponse(context, reqType, results, responses, requestid);
        String strjsonBody = paylodObject.toString();
        executor.removeHeaders(context);
        executor.setResponse(context, strjsonBody);
//                routeToEndPoint(context, sendResponse, "", "", strjsonBody);

        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");
        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        if (!httpMethod.equalsIgnoreCase("GET")) {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }

        IServiceValidate validator;
        String appID = apiUtil.getAppID(context, "retrive_sms");
        String[] params = {appID, ""};
        validator = new ValidateSBRetrieveSms();
        validator.validateUrl(requestPath);
        validator.validate(params);
        return true;
    }
}
