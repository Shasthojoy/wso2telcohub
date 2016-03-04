/*
 *  Copyright WSO2 Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.apimgt.axiata.dialog.verifier;

import com.axiata.dialog.mife.southbound.data.publisher.NorthboundDataPublisherClient;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.apache.synapse.transport.passthru.util.RelayUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.wso2.carbon.apimgt.gateway.handlers.Utils;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityConstants;

import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//This is the Handler class for Blacklist Numbers Module
//Handlers should extend AbstractHandler Class
public class DialogBlacklistHandler extends AbstractHandler implements ManagedLifecycle {

    private static final Log log = LogFactory.getLog(DialogBlacklistHandler.class);
    private static boolean isInitialized = false;
    private List<String> blacklistNumbers;
    private List<String> subscriptionList;
    private NorthboundDataPublisherClient dataPublisherClient;
    
   
    //Initialize BlackList Numbers
    //Read Blacklist Numbers from database and store in memory
    //Initialization happens only once
    public void intialize() throws SQLException{
        
        
    }
    
    //Entry point for the blacklist Module
    public boolean handleRequest(MessageContext messageContext) {
        
        String resourceUrl=(String) messageContext.getProperty("REST_FULL_REQUEST_PATH");
        
        String acr = null;

        String api = APIUtil.getAPI(messageContext);

        if (api.equals(APINameConstant.PAYMENT)){
           acr = str_piece(resourceUrl,'/',4); 

        }
        
	
        else if(api.equals(APINameConstant.LOCATION)){

            //Retriveing MSISDN from the incoming request
        acr = str_piece(str_piece(resourceUrl, '=', 2),'&',1);

        }
       
        else{
            //nop
        }
        
 
        String userMSISDN = ACRModule.getMSISDNFromACR(acr);
        
        log.info(userMSISDN);
        
        try {
            //If blacklisted number error response is sent in the response


            if( isBlackListNumber(userMSISDN,api) ){
                hadleBlakListResponse(messageContext);
            }else{
                //This will let the request to go further down to backends
                //This will let the request to go further down to backends
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    //Sending the Response back to Client 
    private void hadleBlakListResponse(MessageContext messageContext) {
        messageContext.setProperty(SynapseConstants.ERROR_CODE, "500");
        messageContext.setProperty(SynapseConstants.ERROR_MESSAGE, "Internal Server Error. Blacklisted Number");
        int status = 500;
        OMElement faultPayload = getFaultPayload();

        org.apache.axis2.context.MessageContext axis2MC = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        try {
            RelayUtils.buildMessage(axis2MC);
        } catch (IOException e) {
            log.error("Error occurred while building the message", e);
        } catch (XMLStreamException e) {
            log.error("Error occurred while building the message", e);
        }
        axis2MC.setProperty(Constants.Configuration.MESSAGE_TYPE, "application/soap+xml");
        if (messageContext.isDoingPOX() || messageContext.isDoingGET()) {
            Utils.setFaultPayload(messageContext, faultPayload);
        } else {
            Utils.setSOAPFault(messageContext, "Client", "Authentication Failure", "Not a whitelisted Number");
        }

        messageContext.setProperty("error_message_type", "application/json");
        publishResponseData(messageContext, faultPayload);
        Utils.sendFault(messageContext, status);
    }

    private boolean isBlackListNumber(String msisdn,String apiName) throws SQLException, NamingException{       
    	if (blacklistNumbers!=null) {
    		blacklistNumbers.clear();
		}
    	
    	blacklistNumbers = DatabaseUtils.ReadBlacklistNumbers(apiName);

        return  blacklistNumbers.contains(msisdn);
    }

    //Constructing the Payload
    private OMElement getFaultPayload() {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace ns = fac.createOMNamespace(APISecurityConstants.API_SECURITY_NS,
                APISecurityConstants.API_SECURITY_NS_PREFIX);
        OMElement payload = fac.createOMElement("fault", ns);

        OMElement errorCode = fac.createOMElement("code", ns);
        errorCode.setText("500");
        OMElement errorMessage = fac.createOMElement("message", ns);
        errorMessage.setText("Blacklisted Number");
        OMElement errorDetail = fac.createOMElement("description", ns);
        errorDetail.setText("Blacklisted Number");

        payload.addChild(errorCode);
        payload.addChild(errorMessage);
        payload.addChild(errorDetail);
        return payload;
    }
    public boolean handleResponse(MessageContext messageContext) {
        
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

    private void publishResponseData(MessageContext messageContext, OMElement faultPayload) {
        if (dataPublisherClient == null) {
            dataPublisherClient = new NorthboundDataPublisherClient();
        }
        String xmlPayload = faultPayload.toString();
        String jsonBody = "";
        //convert xml response to json
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlPayload);
            jsonBody = xmlJSONObj.toString(2);
        } catch (JSONException je) {
            log.info(je.toString());
        }
        //publish data to BAM
        messageContext.setProperty(SouthboundPublisherConstants.MSISDN, messageContext.getProperty("UserMSISDN"));
        dataPublisherClient.publishResponse(messageContext, jsonBody);
    }
    

    public void init(SynapseEnvironment synapseEnvironment) {
        
        
    }

    public void destroy() {
        
    }

}
