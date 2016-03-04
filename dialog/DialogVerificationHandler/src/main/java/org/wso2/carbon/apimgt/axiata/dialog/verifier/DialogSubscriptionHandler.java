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

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.gateway.handlers.Utils;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityConstants;

//This is the Handler class for Blacklist Numbers Module
//Handlers should extend AbstractHandler Class
public class DialogSubscriptionHandler extends AbstractHandler implements ManagedLifecycle {

    private static final Log log = LogFactory.getLog(DialogBlacklistHandler.class);
    private List<String> subscriptionList;
    
   
    //Entry point for the Subscription Module
    public boolean handleRequest(MessageContext messageContext) {
        
        
        String resourceUrl=(String) messageContext.getProperty("REST_FULL_REQUEST_PATH");
        
        String api = messageContext.getProperty("api.ut.api").toString();
        String user = messageContext.getProperty("api.ut.userId").toString();
        String application = messageContext.getProperty("api.ut.application.name").toString();
        
        String msisdn = null;
        
        //Retriveing MSISDN from the incoming request
        msisdn = str_piece(str_piece(resourceUrl, '=', 2),'&',1);
        
        String userMSISDN = ACRModule.getMSISDNFromACR(msisdn);
        
        try {
             subscriptionList = DatabaseUtils.ReadSubscriptionNumbers(user,application,api);
        } catch (APIManagementException ex) {
            Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if(isAlreadySubscribed(userMSISDN)){
                //nop
        }
        else{ 
            String subscriptionCount = null;
            int updatedSubscribers = 0;
            
            try {
                
                
                try {  
                    subscriptionCount = DatabaseUtils.getSubscriptionCount(user,application,api);

                } catch (SQLException ex) {
                    Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                if (subscriptionCount != null){
                    //Update with new subscription 
                    updatedSubscribers = Integer.parseInt(subscriptionCount);
                    
                    DatabaseUtils.UpdateSubscriptionNumbers(user,application,api,Integer.toString(updatedSubscribers + 1));
                }

                else{
                    try { 
                        DatabaseUtils.writeSubscription(user,application,api);
                    } catch (SQLException ex) {
                        Logger.getLogger(DialogSubscriptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NamingException ex) {
                        Logger.getLogger(DialogSubscriptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Subscribe new user
                DatabaseUtils.SubscribeUser(user,application,api,userMSISDN);


            } catch (APIManagementException ex) {
                Logger.getLogger(DialogBlacklistHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    

    public boolean handleResponse(MessageContext messageContext) {
        return true;
    }
    
    private boolean isAlreadySubscribed(String msisdn){       
        
       return  subscriptionList.contains(msisdn);
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
    

    public void init(SynapseEnvironment synapseEnvironment) {
        
        
    }

    public void destroy() {
        
    }

}
