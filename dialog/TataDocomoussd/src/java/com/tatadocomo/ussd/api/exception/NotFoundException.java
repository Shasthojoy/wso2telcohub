/*
 * NotFoundException.java
 * Jul 25, 2014  10:56:39 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.tatadocomo.ussd.api.exception;

/**
 * <TO-DO>
 * <code>NotFoundException</code>
 *
 * @version $Id: NotFoundException.java,v 1.00.000
 */
import com.google.gson.Gson;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidatePaymentCharge;
import com.axiata.dialog.oneapi.validation.impl.ValidateRetrieveSms;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.tatadocomo.ussd.util.ResourceURLUtil;

@Provider
@Produces("application/json")
public class NotFoundException implements ExceptionMapper<org.jboss.resteasy.spi.NotFoundException> {

    public NotFoundException() {
    }

    @Override
    public Response toResponse(org.jboss.resteasy.spi.NotFoundException arg0) {
        String jsonreturn = null;
        RequestError requesterror = new RequestError();
        try {

            //String _apitype = new ResourceURLUtil().getAPIType(arg0.getNotFoundUri().toString());

            System.out.println(arg0.getMessage());
            String fullpath = arg0.getMessage().split("full path: ")[1];
            String requesturl = arg0.getMessage().split("full path: ")[1].split("smsmessaging")[1];
            String apitype = new ResourceURLUtil().getAPIType(fullpath);

           /* if (apitype.equalsIgnoreCase("send_sms")) {
                new ValidateSendSms().validateUrl(requesturl);
            } else if (apitype.equalsIgnoreCase("retrive_sms_subscriptions")) {
                throw new AxiataException("SVC0002", "Invalid input value for message part %1", new String[]{"subscriptions at component [1]"});
            } else if (apitype.equalsIgnoreCase("retrive_sms")) {
                new ValidateRetrieveSms().validateUrl(requesturl);
            }
            //new ValidatePaymentCharge().validateUrl(arg0.getNotFoundUri().toString());
           */

        } catch (AxiataException e) {

            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                jsonreturn = new Gson().toJson(requesterror);
                return Response.status(403).header("Content-Type", "application/json").entity(jsonreturn).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                jsonreturn = new Gson().toJson(requesterror);
                
                return Response.status(400).header("Content-Type", "application/json").entity(jsonreturn).build();
            }
        }

        requesterror.setServiceException(new ServiceException("SVC0002", "Request is missing required URI components", ""));
        jsonreturn = new Gson().toJson(requesterror);
        return Response.status(400).header("Content-Type", "application/json").entity(jsonreturn).build();
    }
}
