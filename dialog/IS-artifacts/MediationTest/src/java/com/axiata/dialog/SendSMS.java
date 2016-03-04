/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.axiata.dialog;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.axiata.dialog.entity.outboundSMSTextMessage;
import com.axiata.dialog.entity.outboundSMSMessageRequest;
import com.axiata.dialog.entity.SendSMSRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *
 * @author Tharanga_07219
 */
public class SendSMS {
    
        public SendSMS() {
        }
        private static final Logger LOG = Logger.getLogger(Endpoints.class.getName());
    
        public String sendSMS(String msisdn, String message) throws UnsupportedEncodingException, IOException {
        String returnString = null;
        
        List<String> address = new ArrayList<String>();
        address.add("tel:+" + msisdn);
        
        String url = "https://apistore.dialog.lk/apicall/smsmessaging/1.0/outbound/26451/requests";        
        
        
        outboundSMSTextMessage messageObj = new outboundSMSTextMessage();
        messageObj.setMessage(message);
        
        outboundSMSMessageRequest outbound = new outboundSMSMessageRequest();
        
        outbound.setOutboundTextMessage(messageObj);
        outbound.setAddress(address);
        outbound.setSenderAddress("26451");
        
        SendSMSRequest req = new SendSMSRequest();
        
        req.setOutboundSMSMessageRequest(outbound);
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        returnString = gson.toJson(req);
        
        postRequest(url,returnString);
        
        return returnString;
        
    }
    
    protected void postRequest(String url, String requestStr) throws UnsupportedEncodingException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(url);
        
        postRequest.addHeader("accept", "application/json");

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
