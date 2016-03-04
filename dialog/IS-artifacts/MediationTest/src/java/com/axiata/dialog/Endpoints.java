/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Tharanga.Ranaweera
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog;

import java.sql.Date;
import com.axiata.dialog.entity.LoginHistory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import java.sql.SQLException;
import java.util.logging.Level;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.logging.Logger;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.identity.mgt.stub.UserIdentityManagementAdminServiceIdentityMgtServiceExceptionException;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;




/**
 * REST Web Service
 * Tharanga Ranaweera
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/endpoint")
public class Endpoints {

    //private static final Logger LOG = Logger.getLogger(Endpoints.class.getName());
    @Context
    private UriInfo context;
    SmsService smsservice = null;
    String successResponse = "\"" + "amountTransaction" + "\"";
    String serviceException = "\"" + "serviceException" + "\"";
    String policyException = "\"" + "policyException" + "\"";
    String errorReturn = "\"" + "errorreturn" + "\"";
    private static Log log = LogFactory.getLog(Endpoints.class);
    /**
     * Creates a new instance of QueriesResource
     */
    public Endpoints() {
        smsservice = new SmsService();
    }

    @POST
    @Path("/ussd/receive")
    @Consumes("application/json")
    @Produces("application/json")
    public Response ussdReceive(String jsonBody) throws SQLException, org.codehaus.jettison.json.JSONException, JSONException {

        Gson gson = new GsonBuilder().serializeNulls().create();

        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        
        String message = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("inboundUSSDMessage");
        String sessionID = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("clientCorrelator");
        
        
        int responseCode = 400;
        String responseString = null;
        
        String status = null;
        
        //USSD 1 = YES
        //USSD 2 = NO
        if(message.equals("1")){
            status = "Approved";
            responseCode = 201;
            DatabaseUtils.updateStatus(sessionID, status);
        }
        else if(message.equals("2")){
            status = "Rejected";
            responseCode = 201;
            DatabaseUtils.updateStatus(sessionID, status);
        }
        else{
            responseCode = 400;
            status = "Status not updated";
            //nop
        }
        
        if (responseCode == 400){
            responseString = "{" + "\"requestError\":" + "{"
                    + "\"serviceException\":" + "{" + "\"messageId\":\"" + "SVC0275" + "\"" + "," + "\"text\":\"" + "Internal server Error" + "\"" + "}" 
                    + "}}";
        }
        else{
            responseString = "{" + "\"sessionID\":\"" + sessionID + "\","
                    + "\"status\":\"" + status + "\"" + "}";
        }
        
        return Response.status(responseCode).entity(responseString).build();
    }

    private String validatePIN(String pin, String sessionID, String msisdn)/* throws RemoteUserStoreManagerServiceUserStoreExceptionException*/{
        String responseString = null;
            try {
                    LoginAdminServiceClient lAdmin = new LoginAdminServiceClient(FileUtil.getApplicationProperty("admin_url"));
                    String sessionCookie = lAdmin.authenticate("admin", "admin");
                    ClaimManagementClient claimManager = new ClaimManagementClient(FileUtil.getApplicationProperty("admin_url"),sessionCookie);
                    String profilePin = claimManager.getPIN(msisdn);
                    String hashedUserPin = getHashedPin(pin);
                    if(hashedUserPin != null && profilePin != null && profilePin.equals(hashedUserPin)) {
                        //success
                        return null;
                    } else {
                        Integer noOfAttempts = DatabaseUtils.readMultiplePasswordNoOfAttempts(sessionID);
                        if(noOfAttempts < 2){//resend USSD
                            responseString = SendUSSD.getJsonPayload(msisdn, sessionID,2, "mtcont");//send 2 to show retry_message
                            DatabaseUtils.updateMultiplePasswordNoOfAttempts(sessionID, noOfAttempts + 1);
                        } else {//lock user
                            UserIdentityManagementClient identityClient = new UserIdentityManagementClient(FileUtil.getApplicationProperty("admin_url"), sessionCookie);
                            identityClient.lockUser(msisdn);
                            DatabaseUtils.deleteUser(sessionID);
                        }
                    }
            } catch (AxisFault e) {
                    e.printStackTrace();
            } catch (RemoteException e) {
                    e.printStackTrace();
            } catch (LoginAuthenticationExceptionException e) {
                    e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserIdentityManagementAdminServiceIdentityMgtServiceExceptionException ex) {
                Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteUserStoreManagerServiceUserStoreExceptionException ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseString;
    }
    
    @POST
    @Path("/ussd/pin")
    @Consumes("application/json")
    @Produces("application/json")
    public Response ussdPinReceive(String jsonBody) throws SQLException, org.codehaus.jettison.json.JSONException, JSONException {
        Gson gson = new GsonBuilder().serializeNulls().create();

        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        String message = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("inboundUSSDMessage");
        String sessionID = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("clientCorrelator");
        String msisdn = extractMsisdn(jsonObj);
        System.out.println("message>" + message);
        System.out.println("sessionID>" + sessionID);
        
        
        int responseCode = 400;
//        String responseString = null;
        //validatePIN returns non null value if USSD push should be done again(in case of incorrect PIN)
        String responseString = validatePIN(message, sessionID, msisdn);
        if(responseString != null) {
            return Response.status(201).entity(responseString).build();
        }
        String status = null;
        
        //USSD 1 = YES
        //USSD 2 = NO
        if ( (message != null ) && (!message.isEmpty()) ){
            status = "Approved";
            responseCode = 201;
            DatabaseUtils.updatePinStatus(sessionID, status, message);
        }        
        else{
            responseCode = 400;
            status = "Status not updated";
            //nop
        }
        
        if (responseCode == 400){
            responseString = "{" + "\"requestError\":" + "{"
                    + "\"serviceException\":" + "{" + "\"messageId\":\"" + "SVC0275" + "\"" + "," + "\"text\":\"" + "Internal server Error" + "\"" + "}" 
                    + "}}";
        }
        else{
            responseString = "{" + "\"sessionID\":\"" + sessionID + "\","
                    + "\"status\":\"" + status + "\"" + "}";
        }
        return Response.status(responseCode).entity(responseString).build();
    }
    
    
    @GET
    @Path("/ussd/status")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response userStatus(@QueryParam("sessionID") String sessionID, String jsonBody) throws SQLException {
        
        String userStatus = null;
        String responseString = null;
        
        userStatus = DatabaseUtils.getUSerStatus(sessionID); 
        
        responseString = "{" + "\"sessionID\":\"" + sessionID + "\","
                    + "\"status\":\"" + userStatus + "\"" + "}";
        
               
        return Response.status(200).entity(responseString).build();
    }

    private String extractMsisdn(JSONObject jsonObj) throws JSONException {
        String address = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("address");
        if(address != null) {//tel:+tel:+94773333428
            return address.split(":\\+")[2];
        }
        return null;
    }
    
    private String getHashedPin(String pinvalue){
        String hashString ="";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pinvalue.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            hashString = hexString.toString();        
           
        } catch (UnsupportedEncodingException ex) {
            log.info("Error getHashValue");
        } catch (NoSuchAlgorithmException ex) {
            log.info("Error getHashValue");
        }
        
         return hashString;
        
    }
	
    @GET
    @Path("/login/history")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response loginHistory(@QueryParam("userID") String userID,@QueryParam("appID") String appID, @QueryParam("fromDate") String strfromDate,
                @QueryParam("toDate") String strtoDate ) throws SQLException, ParseException {
        
        String userStatus = null;
        String responseString = null;
        Date fromDate = new java.sql.Date( new SimpleDateFormat("yyyy-MM-dd").parse(strfromDate).getTime());
        Date toDate = new java.sql.Date( new SimpleDateFormat("yyyy-MM-dd").parse(strtoDate).getTime());
        List<LoginHistory> lsthistory = DatabaseUtils.getLoginHistory(userID, appID, fromDate, toDate);
        responseString = new Gson().toJson(lsthistory);       
        return Response.status(200).entity(responseString).build();
    }
    
    @GET
    @Path("/login/applications")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response loginApps(@QueryParam("userID") String userID) throws SQLException, ParseException {
        
        List<String> lsthistory = DatabaseUtils.getLoginApps(userID);
        String responseString = new Gson().toJson(lsthistory);       
        return Response.status(200).entity(responseString).build();
    }

    @GET
    @Path("/sms/response")
   // @Consumes("application/json")
    @Produces("text/plain")
    public Response smsConfirm(@QueryParam("sessionID") String sessionID) throws SQLException {
        String responseString = null;
        DatabaseUtils.updateStatus(sessionID, "APPROVED");
        responseString = " You are successfully authenticated via mobile-connect";
        return Response.status(200).entity(responseString).build();
    }

}
