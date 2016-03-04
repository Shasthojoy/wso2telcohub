/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Tharanga.Ranaweera
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import com.axiata.dialog.entity.CurrentLocation;
import com.axiata.dialog.entity.TerminalLocationList;
import com.axiata.dialog.entity.TerminalLocation;
import com.axiata.dialog.entity.LocationResponse;

import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import lk.dialog.ideabiz.library.JWT;
import lk.dialog.ideabiz.logger.DirectLogger;
import lk.dialog.ideabiz.logger.model.impl.Location;
import lk.dialog.ideabiz.model.JWT.JWTTokenInfo;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.ws.rs.HeaderParam;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.XML;


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

    private String url = null;
    DirectLogger directLogger = null;

    /**
     * Creates a new instance of QueriesResource
     */
    public Queries() {
        Properties prop = new Properties();

        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            url = (prop.getProperty("dialoglbsurl").toString());
            LOG.info("Loading URL : " + url);
        } catch (IOException e) {
            LOG.error("Load URL ERRO : ", e);
        }

        try {
            directLogger = new DirectLogger();
        } catch (Exception e) {
            LOG.error("Load Logger : " + e.getMessage(), e);
        }
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
    public Response provisionWhiteListedNumber(@HeaderParam("Acr") String acr, @HeaderParam("X-JWT-Assertion") String jwt, @QueryParam("address") String address, String jsonBody) throws SQLException, IOException, JSONException, org.codehaus.jettison.json.JSONException {

        String returnString = null;
        String appId = "";
        Integer appIdInt = 0;
        String appName = "";

        try {
            JWTTokenInfo jwtInfo = JWT.tokenInfo(jwt);

            if (jwtInfo == null || jwtInfo.getApplicationid() == null) {
                LOG.info("No application found");
            } else {
                appId = jwtInfo.getApplicationid();
                appName = jwtInfo.getApplicationname();
                appIdInt = Integer.parseInt(appId);
            }


        } catch (Exception e) {
            LOG.info("No application found");

        }

        try {


            String msisdn = address.replace(" ", "+");
            msisdn = msisdn.replace("tel:94", "");
            msisdn = msisdn.replace("tel:+94", "");
            msisdn = msisdn.replace("+94", "");

            if (msisdn.startsWith("94"))
                msisdn = msisdn.substring(2);

            LOG.info("LBSQUERY|APP:" + appId + "-" + appName + "|MSISDN:" + address);


            String end = url.replace("##MSISDN##", URLEncoder.encode(msisdn));

            //Http Request
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet httpget = new HttpGet(end);

            //System.out.println("executing request " + httpget.getURI());

            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            LOG.debug("RAW RESULT:" + result);
            org.json.JSONObject jsonObj = XML.toJSONObject(result.toString());
            LOG.debug("JSON RESULT:" + jsonObj.toString());

            LOG.info("LBS Response:" + appId + "|" + jsonObj.toString());

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

                Gson gson = new GsonBuilder().serializeNulls().create();

                LocationResponse locResp = new LocationResponse();

                CurrentLocation curr = new CurrentLocation();
                curr.setLongitude(longitude);
                curr.setLatitude(latitude);
                curr.setAccuracy(accuracy);
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

                Double accDouble = 0.0;
                try {
                    accDouble = Double.parseDouble(accuracy);
                } catch (Exception e) {
                }
                directLogger.addLog(new Location("v1", appIdInt, address, 0, "Retrieved", accDouble, "SUCCCESS"));
                return Response.status(200).entity(returnString).build();
            }

            //Error in retriving Location Results
            else {

                returnString = "{" + "\"requestError\":" + "{"
                        + "\"serviceException\":" + "{" + "\"messageId\":\"" + "SVC0275" + "\"" + "," + "\"text\":\"" + "Internal server Error" + "\"" + "}"
                        + "}}";
                directLogger.addLog(new Location("v1", appIdInt, address, 0, "SVC0275", 0.0, "ERROR"));

                return Response.status(400).entity(returnString).build();
            }
        } catch (Exception e) {
            returnString = "{" + "\"requestError\":" + "{"
                    + "\"serviceException\":" + "{" + "\"messageId\":\"" + "SVC0275" + "\"" + "," + "\"text\":\"" + "Internal server Error" + "\"" + "}"
                    + "}}";

            LOG.error(e.getMessage(), e);
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
