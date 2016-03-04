/*
 * AxiataHandlerMediator.java
 * Aug 19, 2013  12:00:07 PM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.mife.mediator.internal.Base64Coder;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * <TO-DO>
 * <code>AxiataHandlerMediator</code>
 *
 * @version $Id: AxiataHandlerMediator.java,v 1.00.000
 */
public class AxiataHandlerMediator extends AbstractMediator {

    @SuppressWarnings("unused")
    
    private static Log log = LogFactory.getLog(AxiataHandlerMediator.class);

    private String executorClass;

    /**
     * This is the main method of the mediator.
     */
    public boolean mediate(MessageContext context) {

        
        try {
            Class clazz = Class.forName(executorClass);
            AxiataRequestExecutor reqHandler = (AxiataRequestExecutor) clazz.newInstance();

            reqHandler.setApplicationid(storeApplication(context));

            reqHandler.initialize(context);
            reqHandler.validateRequest(reqHandler.getHttpMethod(), reqHandler.getSubResourcePath(),
                    reqHandler.getJsonBody(), context);
            reqHandler.execute(context);

        } catch (AxiataException ax) {
            handleAxiataException(ax.getErrcode(),ax.getErrvar(), ax, context);
            
        } catch (Exception axisFault) {            
            handleException("Unexpected error ", axisFault, context);
            return false;
        }

        return true;

    }
    
    public void handleAxiataException(String errcode, String[] errvar, AxiataException ax, MessageContext context) {
        context.setProperty("ERROR_CODE",errcode);
        context.setProperty("errvar",errvar[0]);
        handleException(ax.getErrmsg(), ax,context);
    }
    
    private String storeApplication(MessageContext context) throws AxisFault {
       String applicationid = null;
        
        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context).getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);        
        if (headers != null && headers instanceof Map) {
            try {
                Map headersMap = (Map) headers;
                String jwtparam = (String)headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                applicationid = jwtobj.getString("http://wso2.org/claims/applicationid");
                
            } catch (JSONException ex) {
                throw new AxisFault("Error retriving application id");
            }
        }
        
        return applicationid;
    }

    public String getExecutorClass() {
        return executorClass;
    }

    public void setExecutorClass(String executorClass) {
        this.executorClass = executorClass;
    }
}
