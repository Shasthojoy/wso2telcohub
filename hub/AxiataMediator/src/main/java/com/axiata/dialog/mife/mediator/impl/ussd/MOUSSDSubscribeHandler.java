package com.axiata.dialog.mife.mediator.impl.ussd;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.AxiataConstants;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.mife.validators.ValidatorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

import java.util.List;

/**
 *
 *
 */
public class MOUSSDSubscribeHandler implements USSDHandler {

    private Log log = LogFactory.getLog(MOUSSDSubscribeHandler.class);
    private static final String API_TYPE ="ussd";
    private OriginatingCountryCalculatorIDD occi;
    private USSDExecutor executor;
    private AxiataDbService dbservice;

    public MOUSSDSubscribeHandler(USSDExecutor ussdExecutor){

        log.info("------------------------MOUSSDSubscribeHandler Constructor-------------------");
        occi = new OriginatingCountryCalculatorIDD();
        this.executor = ussdExecutor;
        dbservice = new AxiataDbService();
    }

    @Override
    public boolean handle(MessageContext context) throws Exception {

        JSONObject jsonBody = executor.getJsonBody();
        String notifyUrl = jsonBody.getJSONObject("subscription").getJSONObject("callbackReference").getString("notifyURL");

        String carbonHome = System.getProperty("user.dir");
        log.debug("conf carbonHome - " + carbonHome);

        String fileLocation = carbonHome + "/repository/conf/axiataMediator_conf.properties";

        Util.getPropertyFileByPath(fileLocation);

        Integer axiataid = dbservice.ussdRequestEntry(notifyUrl);
        log.info("created axiataId  -  " + axiataid);

        String subsEndpoint = Util.propMap.get("ussdGatewayEndpoint")+axiataid;
        log.info("Subsendpoint - " +subsEndpoint);

        jsonBody.getJSONObject("subscription").getJSONObject("callbackReference").put("notifyURL", subsEndpoint);
       // jsonBody.getJSONObject("subscription").getJSONObject("callbackReference").put("notifyURL", "http://localhost:8080/DemoService/Priyan/Root/Service");


        List<OperatorEndpoint> endpoints = occi.getAPIEndpointsByApp(API_TYPE, executor.getSubResourcePath(),
                executor.getValidoperators());

        executor.removeHeaders(context);

        String responseStr ="";
        for (OperatorEndpoint endpoint : endpoints) {

            System.out.println("-------------------------endpoint---------------------------------" + endpoint.getEndpointref().getAddress() );
            responseStr = executor.makeRequest(endpoint, endpoint.getEndpointref().getAddress(), jsonBody.toString(), true, context);
            executor.handlePluginException(responseStr);

        }

        executor.setResponse(context, responseStr);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("messageType", "application/json");


        return true;

    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        return false;
    }
}
