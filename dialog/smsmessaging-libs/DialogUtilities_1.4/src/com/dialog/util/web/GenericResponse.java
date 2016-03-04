package com.dialog.util.web;

import java.util.ArrayList;

/**
 * Title		: DialogUtilities_1.4	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 16, 2009
 * @author 		: charith
 * Comments		: 
 */
public class GenericResponse {

    private String m_serviceResponseName  = "";
    private String m_requestID            = "";
    private String m_requestType          = "";
    private String m_timestamp            = "";
    private String m_serviceNumber        = "";    
    private String m_executionResult      = "";    
    private ArrayList m_responseParams    = null;
    
    /**
     * 
     */
    public GenericResponse() {
    }
    
    /**
     * @return the m_serviceResponseName
     */
    public String getServiceResponseName() {
        return m_serviceResponseName;
    }
    /**
     * @param m_serviceResponseName the m_serviceResponseName to set
     */
    public void setServiceResponseName(String serviceResponseName) {
        this.m_serviceResponseName = serviceResponseName;
    }
    /**
     * @return the m_requestType
     */
    public String getRequestType() {
        return m_requestType;
    }
    /**
     * @param m_requestType the m_requestType to set
     */
    public void setRequestID(String requestID) {
        this.m_requestID = requestID;
    }
    /**
     * @return the m_requestType
     */
    public String getRequestID() {
        return m_requestID;
    }
    /**
     * @param m_requestType the m_requestType to set
     */
    public void setRequestType(String requestType) {
        this.m_requestType = requestType;
    }
    /**
     * @return the m_timestamp
     */
    public String getTimestamp() {
        return m_timestamp;
    }
    /**
     * @param m_timestamp the m_timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.m_timestamp = timestamp;
    }
    /**
     * @return the m_serviceNumber
     */
    public String getServiceNumber() {
        return m_serviceNumber;
    }
    /**
     * @param m_serviceNumber the m_serviceNumber to set
     */
    public void setServiceNumber(String serviceNumber) {
        this.m_serviceNumber = serviceNumber;
    }
    /**
     * @return the m_executionResult
     */
    public String getExecutionResult() {
        return m_executionResult;
    }
    /**
     * @param m_executionResult the m_executionResult to set
     */
    public void setExecutionResult(String executionResult) {
        this.m_executionResult = executionResult;
    }
    /**
     * @return the m_responseParams
     */
    public ArrayList getResponseParams() {
        return m_responseParams;
    }
    /**
     * @param m_responseParams the m_responseParams to set
     */
    public void setResponseParams(ArrayList responseParams) {
        this.m_responseParams = responseParams;
    }
    
    /***
     * 
     */
    public String toString() {  
        String ret = m_serviceResponseName+","+m_requestID+","+m_requestType+","+m_timestamp+","+m_serviceNumber+","+
                     m_executionResult+","+(m_responseParams==null?"":m_responseParams.toString());
        return ret;
    }
}
