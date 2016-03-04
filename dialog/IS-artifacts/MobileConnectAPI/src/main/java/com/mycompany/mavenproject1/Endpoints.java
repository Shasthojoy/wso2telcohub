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
import com.mycompany.mavenproject1.services.RemoteUserStoreServiceAdminClient;
import com.mycompany.mavenproject1.utils.FileUtil;
import com.mycompany.mavenproject1.services.LoginAdminServiceClient;
import com.mycompany.mavenproject1.entity.ProvisionAuthenticator;
import com.mycompany.mavenproject1.entity.UserRegistrationStatus;
import com.mycompany.mavenproject1.entity.UserRegistrator;
import com.mycompany.mavenproject1.utils.UserRegistrationData;
import com.mycompany.mavenproject1.utils.AuthenticatorProvisioner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//import org.json.JSONException;
//import Aloo.AdminServicesInvoker.LoginAdminServiceClient;

/**
 * REST Web Service
 * Dialog Axiata
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/endpoint")
public class Endpoints {

    @Context
    private UriInfo context;
    private static Log log = LogFactory.getLog(Endpoints.class);
    private static Map<String, UserRegistrationData> userMap = new HashMap<String, UserRegistrationData>();
   
    String successResponse = "\"" + "amountTransaction" + "\"";
    String serviceException = "\"" + "serviceException" + "\"";
    String policyException = "\"" + "policyException" + "\"";
    String errorReturn = "\"" + "errorreturn" + "\"";

    /**
     * Creates a new instance of QueriesResource
     */
    public Endpoints() {
        
    }    
    
    
    @GET
    @Path("/user/exists/{senderAddress}")
    // @Consumes("application/json")
    @Produces("application/json")
    public Response sendSMSOneAPI(@QueryParam("authenticator") String authenticator,
                                  @QueryParam("callback_url") String callbackURL,
                                  @PathParam("senderAddress") String senderAddress) throws SQLException, RemoteException, Exception {

        String status = null;
        String regURL ="";
        
        senderAddress = senderAddress.replace("tel:+", "");
        
        boolean userExists = isUserExists(senderAddress);
        
        //User is registered in wso2carbon
        if(userExists){
            
            status = "registered";
            
        }
        else{
            status = "notregistered";
        }
        
       UserRegistrationStatus user = new UserRegistrationStatus(); 
       regURL = FileUtil.getApplicationProperty("isadminurl") + "/" + "dashboard/register.jag?authenticator=" + authenticator + "&" + "callback_url=" + callbackURL;
       
       
       user.setAddress(senderAddress);
       user.setAuthenticator(authenticator);
       user.setRegistartionURL(regURL);
       user.setStatus(status);
       
       
       
       UserRegistrator req = new UserRegistrator();
       
       req.setOutboundSMSMessageRequest(user);
       
       Gson gson = new GsonBuilder().serializeNulls().create();
        
       String  returnString = gson.toJson(req);
        
        
       return Response.status(200).entity(returnString).build();
    }

    @POST
    @Path("/user/provision/{senderAddress}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response provisionAuthenticator(@QueryParam("callback_url") String authenticatorCallbackURL,
                                           String jsonBody) throws JSONException{
        
        String returnString = null;
        
        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        
        
        
        String address = jsonObj.getJSONObject("ProvisionAuthenticator").getString("address");
        String authenticator = jsonObj.getJSONObject("ProvisionAuthenticator").getString("authenticator");
        String callbackURL = FileUtil.getApplicationProperty("isadminurl") + "/" + "dashboard/pin_reset/pinreset.jag?callback_url=" 
                             + authenticatorCallbackURL + "&address=" + address.replace("tel:+", "") + "&authenticator=" + authenticator;
        
        
        ProvisionAuthenticator provisionedAuth = new ProvisionAuthenticator();
        provisionedAuth.setAuthentictor(authenticator);
        provisionedAuth.setAddresss(address);
        
        
        AuthenticatorProvisioner auth = new AuthenticatorProvisioner();
        
        auth.setCallbackURL(callbackURL);
        auth.setProvisionedAuthenticator(provisionedAuth);
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        
        return Response.status(201).entity(gson.toJson(auth)).build();
        
        
    }
    
    
    
    
    private boolean isUserExists(String userName) throws SQLException, RemoteException, Exception {
        LoginAdminServiceClient lAdmin = new LoginAdminServiceClient(FileUtil.getApplicationProperty("isadminurl"));
        String sessionCookie = lAdmin.authenticate(FileUtil.getApplicationProperty("adminusername"), FileUtil.getApplicationProperty("adminpassword"));

        RemoteUserStoreServiceAdminClient remoteUserStoreServiceAdminClient =
                new RemoteUserStoreServiceAdminClient(FileUtil.getApplicationProperty("isadminurl"), sessionCookie);
        boolean userExists = false;
        if (remoteUserStoreServiceAdminClient.isExistingUser(userName)) {
            userExists = true;   //user already exists
        }
        return userExists;
    }

}
