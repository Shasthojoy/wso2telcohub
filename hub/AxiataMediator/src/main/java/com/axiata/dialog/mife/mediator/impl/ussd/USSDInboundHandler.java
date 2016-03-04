package com.axiata.dialog.mife.mediator.impl.ussd;

import com.axiata.dialog.dbutils.AxiataDbService;
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

    private Log log = LogFactory.getLog(USSDInboundHandler.class);
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
        //remove non numeric chars
        axiataid = axiataid.replaceAll("[^\\d.]", "");
        log.debug("axiataId - "+axiataid);
        String notifyurl = dbservice.getUSSDNotify(Integer.valueOf(axiataid));
        log.info("notifyUrl found -  " + notifyurl);

        String carbonHome = System.getProperty("user.dir");
        log.debug("conf carbonHome - " + carbonHome);
        String fileLocation = carbonHome + "/repository/conf/axiataMediator_conf.properties";
        Util.getPropertyFileByPath(fileLocation);

        JSONObject jsonBody = executor.getJsonBody();
        jsonBody.getJSONObject("inboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", notifyurl);

        String notifyret = executor.makeRequest(new OperatorEndpoint(new EndpointReference(notifyurl), null), notifyurl,
                jsonBody.toString(), true, context);

        log.debug(notifyret);
        if (notifyret == null) {
            throw new AxiataException("POL0299", "", new String[]{"Error invoking Endpoint"});
        }

        JSONObject replyobj = new JSONObject(notifyret);
        String action = replyobj.getJSONObject("outboundUSSDMessageRequest").getString("ussdAction");

        if(action.equalsIgnoreCase("mtcont")){

            String subsEndpoint = Util.propMap.get("ussdGatewayEndpoint")+axiataid;
            log.info("Subsendpoint - " +subsEndpoint);
            replyobj.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", subsEndpoint);

        }

        if(action.equalsIgnoreCase("mtfin")){
            String subsEndpoint = Util.propMap.get("ussdGatewayEndpoint")+axiataid;
            log.info("Subsendpoint - " +subsEndpoint);
            replyobj.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").put("notifyURL", subsEndpoint);

            boolean deleted = dbservice.ussdEntryDelete(Integer.valueOf(axiataid));
            log.info("Entry deleted " + deleted);

        }

        executor.removeHeaders(context);

        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 201);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("ContentType", "application/json");
        executor.setResponse(context, replyobj.toString());

        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        context.setProperty("mife.prop.operationType", 407);
        return true;
    }
}
