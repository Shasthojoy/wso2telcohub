package com.axiata.dialog.mife.mediator.impl.sms;

import java.util.List;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operatorsubs;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.impl.sms.nb.NBOutboundSMSSubscriptionsHandler;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.IServiceValidate;
import com.axiata.dialog.oneapi.validation.impl.ValidateCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateDNCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateDNCancelSubscriptionPlugin;
import com.axiata.dialog.oneapi.validation.impl.sms.nb.ValidateNBOutboundSubscription;

public class StopOutboundSMSSubscriptionsHandler implements SMSHandler {

    private static Log log = LogFactory.getLog(StopOutboundSMSSubscriptionsHandler.class);
    private static final String API_TYPE = "sms";
    private OriginatingCountryCalculatorIDD occi;
    private AxiataDbService dbservice;
    private SMSExecutor executor;

    public StopOutboundSMSSubscriptionsHandler(SMSExecutor executor) {
        this.executor = executor;
        occi = new OriginatingCountryCalculatorIDD();
        dbservice = new AxiataDbService();
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext context) throws Exception {
        IServiceValidate validator;
        if (httpMethod.equalsIgnoreCase("DELETE")) {
            String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);
            String[] params = {axiataid};

            String[] urlElements = requestPath.split("/");
            int elements = urlElements.length;
            if (elements == 5) {
                validator = new ValidateDNCancelSubscriptionPlugin();
                log.debug("Invoke validation - ValidateDNCancelSubscriptionPlugin");
            } else if (elements == 4) {
                validator = new ValidateDNCancelSubscription();
                log.debug("Invoke validation - ValidateDNCancelSubscription");
            } else {
                throw new Exception("requestPath not valid");
            }

            validator.validateUrl(requestPath);
            validator.validate(params);
            return true;
        } else {
            ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 405);
            throw new Exception("Method not allowed");
        }
    }

    @Override
    public boolean handle(MessageContext context) throws Exception {
        if (executor.getHttpMethod().equalsIgnoreCase("DELETE")) {
            return deleteSubscriptions(context);
        }
        return false;
    }

    private boolean deleteSubscriptions(MessageContext context) throws Exception {
        String requestPath = executor.getSubResourcePath();
        String subid = requestPath.substring(requestPath.lastIndexOf("/") + 1);

        String requestid = AxiataUID.getUniqueID(AxiataType.DELRETSUB.getCode(), context, executor.getApplicationid());
        Integer axiataid = Integer.parseInt(subid.replaceFirst("sub", ""));
        List<Operatorsubs> domainsubs = (dbservice.outboudSubscriptionQuery(Integer.valueOf(axiataid)));
        if (domainsubs.isEmpty()) {
            throw new AxiataException("POL0001", "", new String[]{"SMS Receipt Subscription Not Found: " + axiataid});
        }

        String resStr = "";
        for (Operatorsubs subs : domainsubs) {
            resStr = executor.makeDeleteRequest(new OperatorEndpoint(new EndpointReference(subs.getDomain()), subs
                    .getOperator()), subs.getDomain(), null, true, context);
        }
        new AxiataDbService().outboundSubscriptionDelete(Integer.valueOf(axiataid));

        executor.removeHeaders(context);
        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 204);

        return true;
    }
}
