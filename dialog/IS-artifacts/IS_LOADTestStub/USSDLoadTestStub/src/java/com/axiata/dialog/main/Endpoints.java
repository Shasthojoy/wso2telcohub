/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog.main;


import com.axiata.dialog.dto.InboundMessage;
import com.axiata.dialog.dto.InboundUSSDMessageRequest;
import com.axiata.dialog.util.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.StringWriter;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.cxf.helpers.IOUtils;


import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import com.axiata.dialog.dto.USSDRequest;	 
import com.axiata.dialog.dto.ResponseRequest;
import com.axiata.dialog.dto.OutboundUSSDMessageRequest;
import com.axiata.dialog.dto.USSDAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;


/**
 * REST Web Service
 * Dialog Axiata
 * @version $Id: Queries.java,v 1.00.000
 */
@Path("/")
public class Endpoints {

    private static final Logger LOG = Logger.getLogger(Endpoints.class.getName());
    /**
     * Creates a new instance of QueriesResource
     */
    public Endpoints() {
        
    }    
    
    
    @POST
    @Path("v1/outbound/{senderAddress}")
    @Produces("application/json")
    public Response sendUSSDOneAPI(String jsonBody,@PathParam("senderAddress") String senderAddress) throws SQLException, RemoteException, Exception {

        //Simulates user responds delay
        TimeUnit.SECONDS.sleep(Integer.parseInt(FileUtil.getApplicationProperty("delay")));
        
        org.json.JSONObject jsonObj = new org.json.JSONObject(jsonBody);
        
        String notifyURL = jsonObj.getJSONObject("outboundUSSDMessageRequest").getJSONObject("responseRequest").getString("notifyURL");
        
        //Check whether notifyURL is USSDPIN notify URL or USSD OK/Cancel notifyURL 
        String delims = "/";
        String[] tokens = notifyURL.split(delims);
        
        boolean pinAuthenticator = false;
        pinAuthenticator = Arrays.asList(tokens).contains("pin");
        
        String userinput = null;
        
        if(pinAuthenticator){
            userinput = FileUtil.getApplicationProperty("userinput_pin");
        }else{
            userinput = FileUtil.getApplicationProperty("userinput_ok");
        }
        
       senderAddress = senderAddress.replace("tel:+", "");
      
       Gson gson = new GsonBuilder().serializeNulls().create();
          
       
       //Create response sent to notifyURL
       InboundMessage inboundMsg = new InboundMessage();
        
       ResponseRequest inboundRepReq = new  ResponseRequest();
       
       
       org.json.JSONObject outboundUSSDMessageRequest = jsonObj.getJSONObject("outboundUSSDMessageRequest");
       org.json.JSONObject responseRequest = outboundUSSDMessageRequest.getJSONObject("responseRequest");
       
       
       inboundRepReq.setCallbackData(responseRequest.getString("callbackData"));
       inboundRepReq.setNotifyURL(responseRequest.getString("notifyURL"));
        
       
       InboundUSSDMessageRequest inboundReq = new InboundUSSDMessageRequest();
       
       inboundReq.setAddress(senderAddress);
       inboundReq.setShortCode(outboundUSSDMessageRequest.getString("shortCode"));
       inboundReq.setKeyword(outboundUSSDMessageRequest.getString("keyword"));
       inboundReq.setInboundUSSDMessage(userinput);
       inboundReq.setUssdAction(USSDAction.mtcont);
       inboundReq.setClientCorrelator(outboundUSSDMessageRequest.getString("clientCorrelator") );
       inboundReq.setResponseRequest(inboundRepReq);

       inboundMsg.setInboundUSSDMessageRequest(inboundReq);
       
       String jsonObjIDGW = gson.toJson(inboundMsg);
       
       USSDRequest ussdReq = new USSDRequest();	   

       //Building the Response String
       ResponseRequest repReq = new  ResponseRequest();
       
       repReq.setCallbackData(responseRequest.getString("callbackData"));
       repReq.setNotifyURL(responseRequest.getString("notifyURL"));
       
       OutboundUSSDMessageRequest outboundReq = new OutboundUSSDMessageRequest();
       
       outboundReq.setAddress(senderAddress);
       outboundReq.setShortCode(outboundUSSDMessageRequest.getString("shortCode"));
       outboundReq.setKeyword(outboundUSSDMessageRequest.getString("keyword"));
       outboundReq.setOutboundUSSDMessage(outboundUSSDMessageRequest.getString("outboundUSSDMessage"));
       outboundReq.setUssdAction(outboundUSSDMessageRequest.getString("ussdAction"));
       outboundReq.setClientCorrelator(outboundUSSDMessageRequest.getString("clientCorrelator") );
       outboundReq.setDelivaryStatus("SENT");
       outboundReq.setResponseRequest(repReq);
       
       ussdReq.setOutboundUSSDMessageRequest(outboundReq);
       
       
       String responseString = gson.toJson(ussdReq);
   
       
       LOG.info("response String: " + responseString);
       
       String path = FileUtil.getApplicationProperty("keystore_path");
       LOG.info("keystore path: " + path);
       
       System.setProperty("javax.net.ssl.trustStore", path);

       System.setProperty("javax.net.ssl.trustStorePassword", FileUtil.getApplicationProperty("keystore_password"));
       
       //Send Userinput to notifyURL
       postToNotifyURL(jsonObjIDGW,notifyURL);
        
        
       return Response.status(200).entity(responseString).build();
    }

    
    
    private void postToNotifyURL(String jsonObj, String notifyURL) {
        try {
            HttpPost post = new HttpPost(notifyURL);

            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;

            httpclient = HttpClients.createDefault();

            StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
            strEntity.setContentType("application/json");

            post.setEntity(strEntity);
            
            LOG.info("notify URL: " + notifyURL);
            
            response = httpclient.execute(post);
            
            
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(new InputStreamReader(instream), writer, 1024);
            String body = writer.toString();
            
            LOG.info("jsonbody returned: " + body);
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}

