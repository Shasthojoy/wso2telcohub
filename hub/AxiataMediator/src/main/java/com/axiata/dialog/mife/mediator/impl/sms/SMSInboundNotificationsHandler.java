package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.InboundRequest;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mnc.resolver.MNCQueryClient;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsagePublisherConstants;

public class SMSInboundNotificationsHandler implements SMSHandler {

    private AxiataDbService dbservice;
    private SMSExecutor executor;
    MNCQueryClient mncQueryclient = null;

    public SMSInboundNotificationsHandler(SMSExecutor executor) {
        this.executor = executor;
        dbservice = new AxiataDbService();
        mncQueryclient = new MNCQueryClient();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {

        String requestid = AxiataUID.getUniqueID(AxiataType.ALERTINBOUND.getCode(), context, executor
                .getApplicationid());

        String requestPath = executor.getSubResourcePath();
        String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);

        HashMap<String, String> subscriptionDetails = dbservice.subscriptionNotifi(Integer.valueOf(axiataid));
        String notifyurl = subscriptionDetails.get("notifyurl");
        String serviceProvider = subscriptionDetails.get("serviceProvider");
        
        String notifyurlRoute = notifyurl;
        Util.getPropertyFile();
        String requestRouterUrl = Util.getApplicationProperty("requestRouterUrl");
        if (requestRouterUrl != null) {
            notifyurlRoute = requestRouterUrl + notifyurlRoute;
        }

        //Date Time issue
        Gson gson = new GsonBuilder().serializeNulls().create();
        InboundRequest inboundRequest = gson.fromJson(executor.getJsonBody().toString(), InboundRequest.class);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String formattedDate = currentDate.replace(' ', 'T');

        inboundRequest.getInboundSMSMessageRequest().getInboundSMSMessage().setdateTime(formattedDate);
        String formattedString = gson.toJson(inboundRequest);
        String mcc = null;
        String operatormar = "+";
        String operator = mncQueryclient.QueryNetwork(mcc, operatormar.concat(inboundRequest.getInboundSMSMessageRequest().getInboundSMSMessage().getSenderAddress()));
        context.setProperty(SouthboundPublisherConstants.MSISDN, inboundRequest.getInboundSMSMessageRequest().getInboundSMSMessage().getSenderAddress());
        context.setProperty(SouthboundPublisherConstants.OPERATOR_ID, operator);
        context.setProperty(APIMgtUsagePublisherConstants.USER_ID, serviceProvider);

        int notifyret = executor.makeNorthBoundRequest(new OperatorEndpoint(new EndpointReference(notifyurl), null), notifyurlRoute,
                formattedString, true, context, false);

        executor.removeHeaders(context);

        if (notifyret == 0) {
            throw new AxiataException("SVC1000", "", new String[]{null});
        }

        ((Axis2MessageContext) context).getAxis2MessageContext().setProperty("HTTP_SC", 200);
        /*executor.setResponse(context, new JSONObject(notifyret).toString());*/

        return true;
    }

    @Override
    public boolean validate(String httpMethod, String requestPath, JSONObject jsonBody, MessageContext
            context) throws Exception {
        context.setProperty("mife.prop.operationType", 207);
        return true;
    }
}
