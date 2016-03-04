package org.wso2.carbon.apimgt.axiata.dialog.verifier;

import com.axiata.dialog.mife.southbound.data.publisher.NorthboundDataPublisherClient;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;

/**
 * This is the basic handler class for publishing request and response data to BAM. This publishes basic properties and
 * some new properties
 */
public class DialogAPIRequestHandler extends AbstractHandler {

    private static final Log log   = LogFactory.getLog(DialogAPIRequestHandler.class);
    private NorthboundDataPublisherClient dataPublisherClient;

    public boolean handleRequest(MessageContext messageContext) {

        if (dataPublisherClient == null) {
            dataPublisherClient = new NorthboundDataPublisherClient();
        }
        String jsonBody = JsonUtil.jsonPayloadToString(((Axis2MessageContext) messageContext).getAxis2MessageContext());
        dataPublisherClient.publishRequest(messageContext , jsonBody);
        return true;

}

    public boolean handleResponse(MessageContext messageContext) {
        String jsonBody = JsonUtil.jsonPayloadToString(((Axis2MessageContext) messageContext).getAxis2MessageContext());

        messageContext.setProperty(SouthboundPublisherConstants.MSISDN, messageContext.getProperty("UserMSISDN"));
        dataPublisherClient.publishResponse(messageContext, jsonBody);
        return true; // Should never stop the message flow
    }



}
