/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsma.authenticators.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsma.authenticators.DataHolder;
import com.gsma.authenticators.config.MobileConnectConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendSMS {
    private static final Logger LOG = Logger.getLogger(SendSMS.class.getName());
    private MobileConnectConfig.SMSConfig smsConfig;

    protected String sendSMS(String msisdn, String message) throws IOException {
        String returnString = null;
        
        List<String> address = new ArrayList<String>();
        address.add("tel:+" + msisdn);


        OutboundSMSTextMessage messageObj = new OutboundSMSTextMessage();
        messageObj.setMessage(message);
        
        OutboundSMSMessageRequest outbound = new OutboundSMSMessageRequest();
        
        outbound.setOutboundTextMessage(messageObj);
        outbound.setAddress(address);
        outbound.setSenderAddress("26451");
        
        SendSMSRequest req = new SendSMSRequest();
        
        req.setOutboundSMSMessageRequest(outbound);
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        returnString = gson.toJson(req);

        smsConfig = DataHolder.getInstance().getMobileConnectConfig().getSmsConfig();
        postRequest(smsConfig.getEndpoint(),returnString);
        
        return returnString;
        
    }
    
    protected void postRequest(String url, String requestStr) throws IOException {


//        HttpClient client = HttpClientBuilder.create().build();
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(url);
        
        postRequest.addHeader("accept", "application/json");
        postRequest.addHeader("Authorization", "Bearer " + smsConfig.getAuthToken());
       

        StringEntity input = new StringEntity(requestStr);
        input.setContentType("application/json");
        
        postRequest.setEntity(input);

        HttpResponse response = client.execute(postRequest);
        
        if ( (response.getStatusLine().getStatusCode() != 201)){
            LOG.info("Error occured while calling end points");
        }
        else{
            LOG.info("Success Request");
        }
        
    }
    
    
    
}
