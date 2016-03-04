package com.axiata.dialog.mife.mediator.impl.ussd;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

public class USSDInboundHandler implements USSDHandler {

    private Log log = LogFactory.getLog(SendUSSDHandler.class);
    private static final String API_TYPE = "ussd";
    private AxiataDbService dbservice;
    private USSDExecutor executor;
    private OriginatingCountryCalculatorIDD occi;

    public USSDInboundHandler(USSDExecutor executor) {
        this.executor = executor;
        dbservice = new AxiataDbService();
        occi = new OriginatingCountryCalculatorIDD();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {

        String requestPath = executor.getSubResourcePath();
        String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        String notifyurl = dbservice.getUSSDNotify(Integer.valueOf(axiataid));

        JSONObject jsonBody = executor.getJsonBody();
        jsonBody.getJSONObject("inboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", notifyurl);

        String notifyret = executor.makeRequest(new OperatorEndpoint(new EndpointReference(notifyurl), null), notifyurl,
                jsonBody.toString(), true, context);

        JSONObject replyobj = new JSONObject(notifyret);
        String action = replyobj.getJSONObject("outboundUSSDMessageRequest").getString("ussdAction");

        if(action.equalsIgnoreCase("mtcont")){
            String subsEndpoint = Util.getApplicationProperty("ussdGatewayEndpoint") + "/ussd/v1/inbound/"+axiataid;
            replyobj.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", subsEndpoint);

            String address = replyobj.getJSONObject("outboundUSSDMessageRequest").getString("address");
            String msisdn = address.substring(5);

            context.setProperty(AxiataConstants.USER_MSISDN, msisdn);
            OperatorEndpoint endpoint = null;

            endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), API_TYPE, executor.getSubResourcePath(), false,
                        executor.getValidoperators());

            String sending_add = endpoint.getEndpointref().getAddress();
            log.info("sending endpoint found: " + sending_add);

            String responseStr = executor.makeRequest(endpoint, sending_add, replyobj.toString(), true, context);
            executor.removeHeaders(context);
        }

        if(action.equalsIgnoreCase("mtfin")){
            String subsEndpoint = Util.getApplicationProperty("ussdGatewayEndpoint") + "/ussd/v1/inbound/"+axiataid;
            replyobj.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", subsEndpoint);

            String address = replyobj.getJSONObject("outboundUSSDMessageRequest").getString("address");
            String msisdn = address.substring(5);

            context.setProperty(AxiataConstants.USER_MSISDN, msisdn);
            OperatorEndpoint endpoint = null;

            endpoint = occi.getAPIEndpointsByMSISDN(address.replace("tel:", ""), API_TYPE, executor.getSubResourcePath(), false,
                        executor.getValidoperators());

            String sending_add = endpoint.getEndpointref().getAddress();
            log.info("sending endpoint found: " + sending_add);

            String responseStr = executor.makeRequest(endpoint, sending_add, replyobj.toString(), true, context);
            executor.removeHeaders(context);

            dbservice.ussdEntryDelete(Integer.valueOf(axiataid));

        }

        executor.removeHeaders(context);
        if (notifyret == null) {
            throw new AxiataException("POL0299", "", new String[]{"Error invoking Endpoint"});
        }
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
        executor.setResponse(context, new JSONObject(notifyret).toString());

        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        return true;
    }
}
