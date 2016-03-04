/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Tharanga.Ranaweera
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.entity.CurrentLocation;
import com.axiata.dialog.entity.LocationResponse;
import com.axiata.dialog.entity.TerminalLocation;
import com.axiata.dialog.entity.TerminalLocationList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONException;
import org.json.XML;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    @Path("/{MSISDN}")
    // @Consumes("application/xml")
    @Produces("application/json")
    public Response provisionWhiteListedNumber(@HeaderParam("Acr") String acr, @QueryParam("address") String address, String jsonBody) throws SQLException, IOException, JSONException, org.codehaus.jettison.json.JSONException {

        System.out.println("address= " + address);

        address = address.replace(" ", "+");

        String msisdn = address.replace("tel:+94", "");
        String returnString = null;

        String url = "https://dialoglbs.dialog.lk/gmlc/legacy?classID=web_engine_v2&methodID=Handler&username=IDERTM452&password=HBDHTR654DBVB234&clientcode=PSI&phoneno=" + msisdn;

        //Http Request
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);

        System.out.println("executing request " + httpget.getURI());

        HttpResponse response = client.execute(httpget);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        org.json.JSONObject jsonObj = XML.toJSONObject(result.toString());

        System.out.println("LBS Response:" + jsonObj.toString());

        JSONObject locatonResults = new JSONObject(jsonObj.getJSONObject("lbs-server").getString("location-results"));

        //Location Results Parser
        String locResult = str_piece(locatonResults.getJSONObject("result").toString(), '{', 2);
        locResult = locResult.substring(1, locResult.length() - 1);
        locResult = locResult.substring(0, locResult.length() - 1);

        //Success Retrival of Location Results
        if (!locResult.equals("error")) {

            String longitude = locatonResults.getJSONObject("result").getJSONObject("location").getJSONObject("longitude").getString("content");
            String latitude = locatonResults.getJSONObject("result").getJSONObject("location").getJSONObject("latitude").getString("content");
            String accuracy = locatonResults.getJSONObject("result").getJSONObject("location").getJSONObject("accuracy").getString("content");

            String name = "";
            String swOn = null;
            String direction = "";
            String cellID = "";
            try {
                //Dialog
                name = locatonResults.getJSONObject("result").getJSONObject("location").getString("name");
                swOn = locatonResults.getJSONObject("result").getJSONObject("location").getString("switch-on");
                direction = locatonResults.getJSONObject("result").getJSONObject("location").getString("direction");
                cellID = locatonResults.getJSONObject("result").getJSONObject("location").getString("cell-id");
                //Dialog end
            } catch (Exception e) {
                System.out.println("Dialog LBS Error:" + e.getMessage());

            }


            Gson gson = new GsonBuilder().serializeNulls().create();

            LocationResponse locResp = new LocationResponse();

            CurrentLocation curr = new CurrentLocation();
            curr.setLongitude(longitude);
            curr.setLatitude(latitude);
            curr.setAccuracy(accuracy);

            curr.setName(name);
            curr.setCellID(cellID);
            curr.setDirection(direction);
            curr.setSwitchOn(swOn);

            curr.setAltitude("");


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //get current date time with Date()
            Date date = new Date();

            String currentDate = dateFormat.format(date);

            String formattedDate = currentDate.replace(' ', 'T');

            curr.setTimestamp(formattedDate);

            //Set the Response
            TerminalLocation terminal = new TerminalLocation();
            terminal.setCurrentLocation(curr);
            terminal.setAddress(address);
            terminal.setLocationRetrievalStatus("Retrieved");

            TerminalLocationList terList = new TerminalLocationList();

            terList.setTerminalLocation(terminal);

            locResp.setTerminalLocationList(terList);

            returnString = gson.toJson(locResp);

//            return Response.status(200).entity(jsonObj.toString()).build();
            return Response.status(200).entity(returnString).build();
        }

        //Error in retriving Location Results
        else {

            returnString = "{" + "\"requestError\":" + "{"
                    + "\"serviceException\":" + "{" + "\"messageId\":\"" + "SVC0275" + "\"" + "," + "\"text\":\"" + "Internal server Error" + "\"" + "}"
                    + "}}";
            return Response.status(400).entity(returnString).build();
        }


    }

    private String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                count++;
                if (count == index) {
                    break;
                }
            } else {
                if (count == index - 1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }
}
