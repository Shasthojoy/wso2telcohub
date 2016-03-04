/*
 * Queries.java
 * Apr 2, 2013  11:20:38 AM
 * Dialog Axiata
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.mycompany.mavenproject1;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import java.io.IOException;
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
//import org.json.JSONException;
import java.sql.SQLException;

//import Aloo.AdminServicesInvoker.LoginAdminServiceClient;
import java.rmi.RemoteException;
import org.json.JSONException;
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.axis2.AxisFault;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.wso2.carbon.identity.mgt.stub.UserIdentityManagementAdminServiceIdentityMgtServiceExceptionException;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;
import org.wso2.carbon.user.core.UserCoreConstants;

/**
 * REST Web Service
 * Dialog Axiata
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/endpoint")
public class Endpoints {

    @Context
    private UriInfo context;
   
    String successResponse = "\"" + "amountTransaction" + "\"";
    String serviceException = "\"" + "serviceException" + "\"";
    String policyException = "\"" + "policyException" + "\"";
    String errorReturn = "\"" + "errorreturn" + "\"";

    /**
     * Creates a new instance of QueriesResource
     */
    public Endpoints() {
        
    }


    @POST
    @Path("/ussd/receive")
    @Consumes("application/json")
    @Produces("application/json")
    public Response ussdReceive(String jsonBody) throws SQLException, RemoteException, LoginAuthenticationExceptionException, JSONException, RemoteUserStoreManagerServiceUserStoreExceptionException, FileNotFoundException, IOException, NoSuchAlgorithmException, InterruptedException {
        String responseString = null;
        String msisdn = null;
           
        int responseCode = 201;
        int noOfAttempts = 0;
        
        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        
        //Retrive pin and username
        String message = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("inboundUSSDMessage");
        String sessionID = jsonObj.getJSONObject("inboundUSSDMessageRequest").getString("clientCorrelator");
        
        
        
        if (!(message.matches("[0-9]+") && message.length() > 3 && message.length() < Integer.parseInt(FileUtil.getApplicationProperty("maxlength") ))){
            String notifyUrl = FileUtil.getApplicationProperty("notifyurl");
            responseString = SendUSSD.getJsonPayload(msisdn, sessionID, 4, "mtcont", notifyUrl);
            DatabaseUtils.updateMultiplePasswordNoOfAttempts(sessionID, 1);
            
            return Response.status(responseCode).entity(responseString).build();
            
        }
        
        noOfAttempts = DatabaseUtils.readMultiplePasswordNoOfAttempts(sessionID);
        //Mobile Number = Username
        msisdn = sessionID ;
        
        
        
        SendUSSD ussdPush = new SendUSSD();
        
                
        //First Time PIN Retrival
        if(noOfAttempts == 1 || noOfAttempts == 3 || noOfAttempts == 5){
            if(noOfAttempts == 1) {
                DatabaseUtils.deleteRequestType(msisdn);
            }
            //Update with user entered PIN
            DatabaseUtils.updateMultiplePasswordPIN(sessionID, Integer.parseInt(message));
            //Update user attempts
            DatabaseUtils.updateMultiplePasswordNoOfAttempts(sessionID, noOfAttempts + 1);
            String notifyUrl = FileUtil.getApplicationProperty("notifyurl");
            //Send USSD push to user's mobile
            //Ask User to retype password
            //ussdPush.sendUSSD(msisdn, sessionID,2,"mtcont");
            responseString = SendUSSD.getJsonPayload(msisdn, sessionID, 2, "mtcont", notifyUrl);
       }
        
        else if(noOfAttempts == 2 || noOfAttempts == 4 || noOfAttempts == 6 ){
            if(DatabaseUtils.readMultiplePasswordPIN(sessionID) == Integer.parseInt(message)){
                //update PIN in IS user profile and usr is deleted
                updatePIN(sessionID,message);
                DatabaseUtils.updateRegStatus(sessionID, "Approved");
                
                DatabaseUtils.deleteUser(sessionID);
                
                 //CODE ADDED #PRIYANKA_06608
                LoginAdminServiceClient lAdmin = new LoginAdminServiceClient(FileUtil.getApplicationProperty("isadminurl"));
                String sessionCookie = lAdmin.authenticate(FileUtil.getApplicationProperty("adminusername"), FileUtil.getApplicationProperty("adminpassword"));
                UserIdentityManagementClient identityClient = new UserIdentityManagementClient(FileUtil.getApplicationProperty("isadminurl"), sessionCookie);
                try {
            
                        identityClient.unlockUser(msisdn);
                        //Del.log("unlocked");
                } catch (UserIdentityManagementAdminServiceIdentityMgtServiceExceptionException e) {
                        e.printStackTrace();
                }
                //CODE ADDED #PRIYANKA_06608
                
            }
            else{
                if(noOfAttempts == 6){
                    //Session Terminated saying user has entered incorrect pin three times.
                    ussdPush.sendUSSD(msisdn, sessionID, 4,"mtfin");
                    DatabaseUtils.deleteUser(sessionID);
                }
                else{
                    String notifyUrl = FileUtil.getApplicationProperty("notifyurl");
                    //Start new PIN session
                    responseString = SendUSSD.getJsonPayload(msisdn, sessionID,3, "mtcont", notifyUrl);
                    //ussdPush.sendUSSD(msisdn, sessionID,1,"mtcont");
                    
                    //Update user attempts
                    DatabaseUtils.updateMultiplePasswordNoOfAttempts(sessionID, noOfAttempts + 1);
                    
                    DatabaseUtils.updateMultiplePasswordPIN(sessionID, 0);
                }
            }
            
        }
        
        else{
            //nop
        }
        
        return Response.status(responseCode).entity(responseString).build();
    }



    
        
    @GET
    @Path("/ussd/pin")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response userPIN(@QueryParam("username") String userName, @QueryParam("msisdn") String msisdn, String jsonBody) throws IOException, SQLException   {

 
        String responseString = null;
        
        SendUSSD ussdPush = new SendUSSD();
        
        //Send USSD push to user's mobile
        System.out.println("MSISDN =" + msisdn);
        System.out.println("Username =" + userName);
        
        ussdPush.sendUSSD(msisdn, userName,1,"mtinit");
        
        
        //If user Already Exists delete that record
        if(DatabaseUtils.isExistingUser(userName)){
            DatabaseUtils.deleteUser(userName);
        }
       
            
        
        DatabaseUtils.insertMultiplePasswordPIN(userName);
        
        
        if(DatabaseUtils.isExistingUserStatus(userName)){
            DatabaseUtils.deleteUserStatus(userName);
        }
        
        DatabaseUtils.insertUserStatus(userName, "pending");
      /*  DatabaseUtils.insertMultiplePasswordPIN(userName);
        DatabaseUtils.updateMultiplePasswordNoOfAttempts(userName, 1);
        DatabaseUtils.updateMultiplePasswordPIN(userName, 1111);
        System.out.println("No of Attempts = " + DatabaseUtils.readMultiplePasswordNoOfAttempts(userName));
        System.out.println("User PIN = " + DatabaseUtils.readMultiplePasswordPIN(userName));
        DatabaseUtils.deleteUser("94777335365");*/
        
        return Response.status(200).entity(responseString).build();
    }
    
    
    @GET
    @Path("/ussd/status")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response userStatus(@QueryParam("username") String username, String jsonBody) throws SQLException {
        
        String userStatus = null;
        String responseString = null;
        
        userStatus = DatabaseUtils.getUSerStatus(username); 
		
	if (userStatus.equals("Approved")){
        
            DatabaseUtils.deleteUserStatus(username);
        }
        
        responseString = "{" + "\"username\":\"" + username + "\","
                    + "\"status\":\"" + userStatus + "\"" + "}";
        
               
        return Response.status(200).entity(responseString).build();
    }
    
    
    @GET
    @Path("/sms/send")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response sendSMS(@QueryParam("username") String userName, @QueryParam("msisdn") String msisdn, String jsonBody) throws SQLException {
        
        
        List<String> destinationAddresses = new ArrayList<String>();
        destinationAddresses.add("tel:" + msisdn);
        
        String message = "Please click following link to complete the Registration " + FileUtil.getApplicationProperty("callbackurl") + "?" +"msisdn=" + msisdn;
        
      
        String password = FileUtil.getApplicationProperty("password");
        String applicationId = FileUtil.getApplicationProperty("applicationId");
        
        SendSMS sms = new SendSMS();
        
        sms.setAddress(destinationAddresses);
        sms.setMessage(message);
        sms.setPassword(password);
        sms.setApplicationId(applicationId);
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        String returnString = gson.toJson(sms);
        
        try {
            postRequest(FileUtil.getApplicationProperty("smsendpoint"),returnString);
        } catch (IOException ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(DatabaseUtils.isExistingUserStatus(userName)){
            DatabaseUtils.deleteUserStatus(userName);
        }
        
        DatabaseUtils.insertUserStatus(userName, "pending");
               
        return Response.status(200).entity(returnString).build();
    }
    
    @GET
    @Path("/sms/response")
   // @Consumes("application/json")
    @Produces("text/plain")
    public Response smsConfirm(@QueryParam("msisdn") String msisdn) throws SQLException {
     
        String responseString = null;
        
        DatabaseUtils.updateRegStatus(msisdn, "Approved");
        
        responseString = "Welcome to Mobile Connect !, simple and secure login solution with strong privacy protection";
        
               
        return Response.status(200).entity(responseString).build();
    }

    
    @GET
    @Path("/ussd/hash")
   // @Consumes("application/json")
    @Produces("application/json")
    public Response userHash(@QueryParam("answer") String answer1) throws IOException, NoSuchAlgorithmException  {
        String responseString = null;
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(answer1.getBytes("UTF-8"));
        
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        System.out.println("UserHash");
        
        String hashString = hexString.toString();
        
        responseString = "{" + "\"hash\":\"" + hashString + "\"" + "}";
               
        return Response.status(200).entity(responseString).build();
    }
    
    @GET
    @Path("/ussd/init")
    @Produces("application/json")
    public Response initUSSD(@QueryParam("msisdn") String msisdn) {
        //new SendUSSD().sendUSSD(msisdn, sessionID, 4,"mtfin");
        String responseString = null;
        try {
            Integer requestType = DatabaseUtils.getPendingUSSDRequestType(msisdn);
            
            if(requestType == 1 || requestType == 3) {//Register or PIN reset
                String notifyUrl = FileUtil.getApplicationProperty("notifyurl");
                responseString = SendUSSD.getJsonPayload(msisdn, msisdn, 1, "mtcont", notifyUrl);//notify url ->MediationTest
            } else if(requestType == 2) {//User Login
                String notifyUrl = FileUtil.getApplicationProperty("loginNotifyurl");
                String ussdMessage = FileUtil.getApplicationProperty("loginmessage");
                responseString = SendUSSD.getJsonPayload(msisdn, msisdn, "mtcont", notifyUrl, ussdMessage);//notify url ->MavenProj
            }
            if(DatabaseUtils.isExistingUser(msisdn)){
                DatabaseUtils.deleteUser(msisdn);
            }
            DatabaseUtils.insertMultiplePasswordPIN(msisdn);//set numer of attempt = 1
        } catch (Exception ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
//            responseString = "{\"status\":\"-1\",\"message\":\"error\"}";
            responseString = "{\"error\":\"404\"}";
        }
        //String responseString = "{\"status\":\"success\",\"message\":\"\"PIN reset request sent to mobile phone \" + msisdn + \".\"}";
        return Response.status(200).entity(responseString).build();
    }
    
    @GET
    @Path("/ussd/saverequest")
    @Produces("application/json")
    public Response saveRequestType(@QueryParam("msisdn") String msisdn, @QueryParam("requesttype") Integer requestType) {
        String responseString = null;
        int status = -1;
        String message = "error";
        try {
            status = DatabaseUtils.saveRequestType(msisdn, requestType);
            message = "success";
        } catch (Exception ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        responseString = "{\"status\":\"" + status + "\",\"message\":\"" + message + "\"}";
        return Response.status(200).entity(responseString).build();
    }
    
    private static String getHashValue(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException{

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(value.getBytes("UTF-8"));
        
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        System.out.println("UserHash");
        
        String hashString = hexString.toString();
        
        
        return hashString;
    }
    
    private static void updatePIN(String userName,String pin) throws NoSuchAlgorithmException, UnsupportedEncodingException, AxisFault, RemoteException, LoginAuthenticationExceptionException{
        
        String userDirectory =  System.getProperty("user.dir");
        String adminURL = FileUtil.getApplicationProperty("isadminurl");
        
        //KeyStore Path 
        String path = userDirectory + "/repository/resources/security/" + "wso2carbon.jks";
        System.setProperty("javax.net.ssl.trustStore", path);
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        
        LoginAdminServiceClient lAdmin = new LoginAdminServiceClient(adminURL);
        String sessionCookie = lAdmin.authenticate(FileUtil.getApplicationProperty("adminusername"), FileUtil.getApplicationProperty("adminpassword"));
       
        //Retriving Remote service Admin Client
        RemoteUserStoreServiceAdminClient remoteUserStoreServiceAdminClient = new RemoteUserStoreServiceAdminClient(adminURL, sessionCookie);
        
        //Hashing user PIN
        pin = getHashValue(pin);
        try {
            //User claim update
            remoteUserStoreServiceAdminClient.setUserClaim(userName,"http://wso2.org/claims/pin",pin, UserCoreConstants.DEFAULT_PROFILE);
        } catch (RemoteException ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteUserStoreManagerServiceUserStoreExceptionException ex) {
            Logger.getLogger(Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    protected void postRequest(String url, String requestStr) throws IOException {


//        HttpClient client = HttpClientBuilder.create().build();
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(url);
        
        postRequest.addHeader("accept", "application/json");
        postRequest.addHeader("Authorization", "Bearer " + "BSa1Eutfkw0J77NT4Lw7yQXfCg4a");
       

        StringEntity input = new StringEntity(requestStr);
        input.setContentType("application/json");
        
        postRequest.setEntity(input);

        HttpResponse response = client.execute(postRequest);
        
        if ( (response.getStatusLine().getStatusCode() != 201)){
            //LOG.info("Error occurred while calling end points");
        }
        else{
           // LOG.info("Success Request");
        }
        
    }
    
}
