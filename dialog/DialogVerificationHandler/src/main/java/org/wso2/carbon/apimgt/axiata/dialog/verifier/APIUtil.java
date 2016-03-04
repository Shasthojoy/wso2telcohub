/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wso2.carbon.apimgt.axiata.dialog.verifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;

import java.util.logging.Level;

import java.util.logging.Logger;

/**
 *
 * @author tharanga_07219
 */
public class APIUtil {

    private static final Log log = LogFactory.getLog(APIUtil.class);
    
    
    public static String getAPI(MessageContext messageContext){
        
        String resourceUrl=(String) messageContext.getProperty("REST_FULL_REQUEST_PATH");

        String apiContext = (String) messageContext.getProperty("api.ut.context");

        String apiName = apiContext.substring(apiContext.indexOf("/") + 1);

        return apiName;
    }
    
}
