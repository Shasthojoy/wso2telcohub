/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.dbutils.AxataDBUtilException;
import com.axiata.dialog.entity.Errorreturn;
import com.axiata.dialog.entity.RequestError;
import com.axiata.dialog.entity.Provisionreq;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;

/**
 * REST Web Service
 *
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/")
public class Queries {

    private static final Logger LOG = Logger.getLogger(Queries.class.getName());
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QueriesResource
     */
    public Queries() {
    }

    @POST
    @Path("/merchant/blacklist")
    @Consumes("application/json")
    @Produces("application/json")
    public Response merchantinsert(String jsonData) {

        try {

            System.out.println("jsondata: "+jsonData);
            Provisionreq provisionreq = new Gson().fromJson(jsonData, Provisionreq.class);
                       
            new ProvisionService().provisionapp(provisionreq);
            
        } catch (AxataDBUtilException e) {
            jsonData = new Gson().toJson(new RequestError(new Errorreturn("POL0299",new String[]{e.getMessage()})));
            return Response.status(400).entity(jsonData).build();
        } catch (Exception e) {
            jsonData = new Gson().toJson(new RequestError(new Errorreturn("POL0299",new String[]{"Internal Server Error"})));
            return Response.status(400).entity(jsonData).build();
        }

        return Response.status(201).build();
    }
    
}
