/*
 * NotFoundException.java
 * Jul 25, 2014  11:03:36 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package lk.dialog.cg.payment;

/**
 * <TO-DO>
 * <code>NotFoundException</code>
 *
 * @version $Id: NotFoundException.java,v 1.00.000
 */
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidatePaymentCharge;
import com.google.gson.Gson;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lk.dialog.utils.ResourceURLUtil;

@Provider
@Produces("application/json")
public class NotFoundException implements ExceptionMapper<com.sun.jersey.api.NotFoundException> {

    public NotFoundException() {
    }


    public Response toResponse(com.sun.jersey.api.NotFoundException arg0) {
        String jsonreturn = null;
        RequestError requesterror = new RequestError();
        try {
            //String _apitype = new ResourceURLUtil().getAPIType(arg0.getNotFoundUri().toString());
            String urlpath = (arg0.getNotFoundUri().getPath());
            new ValidatePaymentCharge().validateUrl(urlpath.split("cgpayment/payment")[1]);

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