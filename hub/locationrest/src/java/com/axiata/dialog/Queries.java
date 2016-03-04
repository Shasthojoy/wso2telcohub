/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.entity.CurrentLocation;
import com.axiata.dialog.entity.LocationRequest;
import com.axiata.dialog.entity.TerminalLocation;
import com.axiata.dialog.entity.TerminalLocationList;
import com.google.gson.Gson;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/queries")
public class Queries {

    private static final Logger LOG = Logger.getLogger(Queries.class.getName());
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QueriesResource
     */
    public Queries() {
    }

    /**
     * GET method for creating an instance of QueriesResource
     *
     * @param address,requestedAccuracy representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @GET
    @Path("/location")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response location(@QueryParam("address") String address, @QueryParam("requestedAccuracy") String requestedAccuracy) {

        String jsonreturn = "";

        try {

            LOG.info("Charge request: " + "address:" + address + "  requestedAccuracy:" + requestedAccuracy);

            LocationRequest locationrequet = new Lbservice().retriveLbs(address, requestedAccuracy);
            jsonreturn = new Gson().toJson(locationrequet);
            //return Response.created(context.getAbsolutePath()).build();

        } catch (Exception e) {
            LOG.error("[location Request], Error + " + "Retrive location " + e.getMessage());
            e.printStackTrace();
        }

        LOG.info("json response: " + jsonreturn);
        return Response.status(200).entity(jsonreturn).build();
    }
}
