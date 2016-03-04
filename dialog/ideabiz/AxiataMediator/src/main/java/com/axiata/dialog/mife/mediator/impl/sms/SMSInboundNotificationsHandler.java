package com.axiata.dialog.mife.mediator.impl.sms;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.entity.InboundRequest;
import com.axiata.dialog.mife.mediator.internal.AxiataType;
import com.axiata.dialog.mife.mediator.internal.AxiataUID;
import com.axiata.dialog.mife.mediator.mediationrule.OriginatingCountryCalculatorIDD;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSInboundNotificationsHandler implements SMSHandler {

    private AxiataDbService dbservice;
    private SMSExecutor executor;

    public SMSInboundNotificationsHandler(SMSExecutor executor) {
        this.executor = executor;
        dbservice = new AxiataDbService();
    }

    @Override
    public boolean handle(MessageContext context) throws AxiataException, AxisFault, Exception {

        String requestid = AxiataUID.getUniqueID(AxiataType.ALERTINBOUND.getCode(), context, executor
                .getApplicationid());

        String requestPath = executor.getSubResourcePath();
        String axiataid = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        String notifyurl = dbservice.subscriptionNotifi(Integer.valueOf(axiataid));

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
        String notifyret = executor.makeRequest(new OperatorEndpoint(new EndpointReference(notifyurl), null), notifyurl,
                formattedString, true, context);

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
