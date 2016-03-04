package com.dialog.mife.ussd.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundException implements ExceptionMapper<org.jboss.resteasy.spi.NotFoundException> {

	public NotFoundException() {
	}

	@Override
	public Response toResponse(org.jboss.resteasy.spi.NotFoundException arg0) {
		System.out.println(arg0.getMessage());
		String xml = "<xml><status>My custom Error</status></xml>";
		return Response.status(404).entity(xml).build();
	}

}
