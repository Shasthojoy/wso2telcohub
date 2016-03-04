/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.apimgt.axiata.dialog.verifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import lk.dialog.ideabiz.model.sms.SMSMessagingRequestWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.json.JSONException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;


/**
 *
 * @author tharanga_07219
 */
public class ACRHandler extends AbstractHandler implements ManagedLifecycle {
    
    private static final Log log = LogFactory.getLog(ACRHandler.class);
    
    //Initialize BlackList Numbers
    //Read Blacklist Numbers from database and store in memory
    //Initialization happens only once
    public void intialize() throws SQLException{
        
    }
    
    //Entry point for the blacklist Module
    public boolean handleRequest(MessageContext messageContext) {
        
        try {
            //Retriveing blacklisted MSISDNs and save in the memory
            //Initialization happens once only
            intialize();
        } catch (SQLException ex) {
            Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Read resource URL from context
        //ACR encypted MSISDN is in the Resource URL
        String resourceUrl=(String) messageContext.getProperty("REST_FULL_REQUEST_PATH");
        
        String msisdnAcr = null;

        String api = APIUtil.getAPI(messageContext);
        
        if (api.equals(APINameConstant.PAYMENT)){
           //Retriveing MSISDN from the incoming request
           msisdnAcr = str_piece(resourceUrl,'/',4); 
        }
        
        else if(api.equals(APINameConstant.LOCATION)){
            //Retriveing MSISDN from the incoming request
            msisdnAcr = str_piece(str_piece(resourceUrl, '=', 2),'&',1);
        }
         else if(api.equals(APINameConstant.MESSAGING)){
             Gson gson = new Gson();

            String body = null;
            if (messageContext.getProperty("JSON_STREAM") != null) {
                BufferedReader buf = new BufferedReader(
                        new InputStreamReader((InputStream) messageContext.getProperty("JSON_STREAM")));
                String line;
                try {
                    while ((line = buf.readLine()) != null) {
                        body = line;
                    }
                } catch (IOException e) {

                }
            } else if (messageContext.getProperty("JSON_STRING") != null) {
                body = ((String) messageContext.getProperty("JSON_STRING"));
            }
            SMSMessagingRequestWrap req = gson.fromJson(body,SMSMessagingRequestWrap.class);
            msisdnAcr = req.getOutboundSMSMessageRequest().getSenderAddress();

        }

        else{
            //nop
        }
        
        msisdnAcr = msisdnAcr + "=" + "=";
        
        Gson gson = new GsonBuilder().serializeNulls().create();
        
        //Create json Payload to call local ACR Engine
        DecodeAcrRequest decode= new DecodeAcrRequest();
        decode.setACR(msisdnAcr);
        
        ACREntity acr = new ACREntity();
        acr.setdecodeAcr(decode);
        
        //Local ACR Engine Endpoint.This is constant.
        String url = "http://localhost:8080/acrengine/ACRDecodeService/1/CON001/zpjtQV28"; 
        String requestString = gson.toJson(acr);
        
        String actualMSISDN = null;
        
        try {
            //Call to ACR Engine and decode MSISDN
            actualMSISDN = getMSISDN(url,requestString);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ACRHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ACRHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ACRHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        log.info(" actualMSISDN = " + actualMSISDN);
        
        //Decrypted MSISDN is added to header.
        //Backend Read that header and get decrypted MSISDN
        //It is added as a header value otherwise need to build the message body here. Which would be perfomance impact
        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            headersMap.put("Acr", actualMSISDN);
        }
       
        
        return true;
    }
    

    public boolean handleResponse(MessageContext messageContext) {
      /*  String returnString = null;
        Mediator sequence = messageContext.getSequence("_build_");
        sequence.mediate(messageContext);
        
        org.json.JSONObject jsonObj = null;
        org.json.JSONObject jsonBody = null;
        JSONObject locatonResults = null;
        String firstElement = null;
        
        try {
            jsonObj = XML.toJSONObject(messageContext.getEnvelope().getBody().toString());
            
            //String jsonStr = JsonUtil.jsonPayloadToString(((Axis2MessageContext) messageContext).getAxis2MessageContext());
            
            firstElement = messageContext.getEnvelope().getSOAPBodyFirstElementLocalName();
            //log.info("xml = " + jsonStr);
            
        } catch (org.json.JSONException ex) {
            Logger.getLogger(DialogPaymentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            jsonBody = jsonObj.getJSONObject("soapenv:Body");
           
            if (firstElement.equals("terminalLocationList")){
                
                    String Terminal = jsonBody.getJSONObject("terminalLocationList").toString();
                    
                    Gson gson = new GsonBuilder().serializeNulls().create();

                    TerminalLocationList terminalLocation= gson.fromJson(Terminal, TerminalLocationList.class);
                    
                    terminalLocation.getTerminalLocation().setAddress("lAsy+njKkuVpD19cECNNCgikDWuiBF7gKV20/3NNXLSl3oDd2MkOLVDqMS7LCSnjXxRFTfkkk3fkBtIyG0tFtg==");
                
                    
                   // returnString = "{" + "\"terminalLocationList\":" + gson.toJson(terminalLocation).toString() + "}";
                    
                    //returnString = "{"+ "\"soapenv:Body\":"  + "{" + "\"terminalLocationList\":" + gson.toJson(terminalLocation).toString() + "," + "\"xmlns:soapenv\":\"" + "http://www.w3.org/2003/05/soap-envelope" + "\"" +"}" +"}";
                    
                    returnString = "{" + "\"terminalLocationList\":" + gson.toJson(terminalLocation).toString()  +"}";
                    
                    log.info("Info: " + returnString);
            }
            else{
                
            }
            
            
        } catch (JSONException ex) {
            Logger.getLogger(ACRHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        JsonUtil.newJsonPayload(((Axis2MessageContext) messageContext).getAxis2MessageContext(), returnString , true, true);*/
        
        
        return true;
    }
    
    private static String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == separator) {
                count++;
                if(count == index) {
                    break;
                }
            }
            else {
                if(count == index-1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }
    
    private String getMSISDN(String Url, String Requeststr)throws UnsupportedEncodingException, IOException, JSONException {

        String Authtoken = "con123";
        String retStr = "";
        
        log.info("RequestString =" + Requeststr);
        
        PoolingClientConnectionManager connManager = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
        
        HttpClient client = new DefaultHttpClient(connManager);
        //DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(Url);
        postRequest.addHeader("accept", "application/json");
        postRequest.addHeader("authorization", "ServiceKey " + Authtoken);
        
        StringEntity input = new StringEntity(Requeststr);
        input.setContentType("application/json");
        postRequest.setEntity(input);
        
        HttpResponse response = client.execute(postRequest);
        
        BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

        String output;
        while ((output = br.readLine()) != null) {
            retStr += output;
        }
        
        log.info("ACR Response " + retStr);
        client.getConnectionManager().shutdown();
        
        org.json.JSONObject jsonBody = null;
        jsonBody = new org.json.JSONObject(retStr.toString());
        
        String msisdn = jsonBody.getJSONObject("decodeAcrResponse").getString("msisdn");
        
        return msisdn;
      }   
   
    public void init(SynapseEnvironment synapseEnvironment) {
        
        
    }

    public void destroy() {
        
    }

}

    

