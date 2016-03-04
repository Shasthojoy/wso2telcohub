package com.dialog.mife.ussd.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ResourceURLUtil;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidateReceiveUssd;
import com.axiata.dialog.oneapi.validation.impl.ValidateUssdCancelSubscription;
import com.axiata.dialog.oneapi.validation.impl.ValidateUssdSend;
import com.axiata.dialog.oneapi.validation.impl.ValidateUssdSubscription;
import com.google.gson.Gson;

@Provider
public class NotFoundException implements ExceptionMapper<org.jboss.resteasy.spi.NotFoundException> {

	public NotFoundException() {
	}

	@Override
	public Response toResponse(org.jboss.resteasy.spi.NotFoundException arg0) {
		System.out.println(arg0.getMessage());
		//String xml = "<xml><status>My custom Error</status></xml>";
		//return Response.status(404).entity(xml).build();
		

        String jsonreturn = null;
        RequestError requesterror = new RequestError();
        try {

            //String _apitype = new ResourceURLUtil().getAPIType(arg0.getNotFoundUri().toString());

            System.out.println(arg0.getMessage());
            String fullpath = arg0.getMessage().split("full path: ")[1];
            String requesturl = arg0.getMessage().split("full path: ")[1].split("smsmessaging")[1];
            String apitype = new ResourceURLUtil().getAPIType(fullpath, "");

            if (apitype.equalsIgnoreCase("ussd_send")) {
                new ValidateUssdSend().validateUrl(requesturl);
            } else if (apitype.equalsIgnoreCase("ussd_receive")) {
                new ValidateReceiveUssd().validateUrl(requesturl);
            } else if (apitype.equalsIgnoreCase("ussd_subscription")) {
                new ValidateUssdSubscription().validateUrl(requesturl);
            } else if (apitype.equalsIgnoreCase("stop_ussd_subscription")) {
                new ValidateUssdCancelSubscription().validateUrl(requesturl);
            }
            //new ValidatePaymentCharge().validateUrl(arg0.getNotFoundUri().toString());


        } catch (AxiataException e) {

            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                //jsonreturn = new Gson().toJson(requesterror);
                jsonreturn = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
                return Response.status(403).header("Content-Type", "application/json").entity(jsonreturn).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                //jsonreturn = new Gson().toJson(requesterror);
                jsonreturn = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
                return Response.status(400).header("Content-Type", "application/json").entity(jsonreturn).build();
            }
        }

        requesterror.setServiceException(new ServiceException("SVC0002", "Request is missing required URI components", ""));
        //jsonreturn = new Gson().toJson(requesterror);
        jsonreturn = "{\"requestError\":" + new Gson().toJson(requesterror) + "}";
        return Response.status(400).header("Content-Type", "application/json").entity(jsonreturn).build();
	}

}
