package com.axiata.dialog.services;

import com.axiata.dialog.model.MSSRequest;
import com.axiata.dialog.model.MSSResponse;
import com.axiata.dialog.mss.MSSSoapWebService;
import com.axiata.dialog.mss.MSSSoapWebServiceImpl;
import com.axiata.dialog.util.CommonConstant;
import com.axiata.dialog.util.Config;
import com.axiata.dialog.util.GsonHelper;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nilan on 11/17/14.
 */
@Path("/transactions/")
public class Transactions {

    @GET
    @Path("/getname")
    @Produces("application/json")
    public Response getName(@Context HttpServletRequest req) throws Exception {

        String output = "this is a test web app1";
        Gson gson = new Gson();
        return Response.ok(gson.toJson(output), MediaType.APPLICATION_JSON).build();


    }

    @POST
    @Path("/msssignatureservice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Response MSSSignatureService(String frommMssRequest) throws Exception {

        Gson gson = new Gson();
        MSSRequest mssRequest = null;
        try {
            mssRequest = GsonHelper.getInstance().fromJson(frommMssRequest,MSSRequest.class);
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).header(CommonConstant.ERROR_MSG, ex.getMessage()).build();
        }

        MSSSoapWebService mssSoapWebService=new MSSSoapWebServiceImpl();
        MSSResponse response=mssSoapWebService.signatureService(mssRequest);

        if(response.getResponseStatus().equalsIgnoreCase(Config.getInstance().getProperty("mss.signature.service.response.status"))) {
            return Response.ok(gson.toJson(response), MediaType.APPLICATION_JSON).build();
        }
        else{
            return Response.status(Response.Status.BAD_REQUEST).header(CommonConstant.ERROR_MSG,"WRONG_PARAM").build();
        }

    }




}
