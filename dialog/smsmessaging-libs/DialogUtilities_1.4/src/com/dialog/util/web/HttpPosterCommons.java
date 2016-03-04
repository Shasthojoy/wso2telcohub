package com.dialog.util.web;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.jdom.Document;

import com.dialog.util.SystemLog;
import com.dialog.util.Utility;

/**
 * Title 		: Dialog Util - Dialog R&D Utility library
 * Description	: Use to make http POST METHOD requests
 * Copyright	: Copyright (c) 2008
 * Company		: Dialog Telekom PLC
 * Department	: Group Technology R&D
 * Created On	: 01-08-2008
 * @author 		: Charith De Silva, Lakthinda Ranasinghe
 * Comments     : Modified by Charith on 18-03-2009 to support generic HTTP request & response and including logging 
 */
public class HttpPosterCommons {    
    public static final String REQUEST_CONTENT_TYPE = "text/xml";
    public static final String REQUEST_CHAR_SET     = "utf-8";
    public static final int GET_METHOD              = 1;
    public static final int POST_METHOD             = 2;
    
    public static final String GET_RESPONSE_CONTENT     = "response";
    public static final String GET_RESPONSE_ERR_CONTENT = "errorResponse";
    public static final String GET_RESPONSE_STATUS_CODE = "statusCode";
    public static final String GET_RESPONSE_TIME        = "responseTime";
    
	public static String XML_API_PATH				= "";	
	public static String REQ_LOG_NAME               = "HTTP_POSTER_REQ";
	public static String RES_LOG_NAME               = "HTTP_POSTER_RES";
	public static String TXN_LOG_NAME               = "HTTP_POSTER_TXN";
	public static String EVENT_LOG_NAME             = "EVENT_LOG";
	private static HttpPosterCommons s_instance		= null;
	private static HttpClient s_client				= null;
	private static HttpClient s_client_noproxy      = null;
	
	//----------------- Proxy Settings ---------------------
	private static String host								= "";
	private static String port 							= "";
	private static String username							= "";
	private static String password 						= "";
	
	//----------------- Http Connection Settings -----------------
	public static int defaultMaxConnPerHost			= 30;
	public static int connPerTimeOut				= 10000;
	public static int maxTotalConn					= 30;
	private boolean staleCheckingEnabled			= true;
	private HashMap parameterMap	= null;
	
	private HttpPosterCommons() throws Exception{
		parameterMap	= new HashMap();
		parameterMap.put("Content-Type", "text/xml; charset=utf-8");
		setHttpConnectionSettings(defaultMaxConnPerHost, connPerTimeOut, maxTotalConn, staleCheckingEnabled, parameterMap);
	}
	/**
	 * 
	 * @return HttpPosterCommons instance
	 * @throws Exception
	 */
	public static HttpPosterCommons getInstance() throws Exception {
		if(s_instance == null){
			s_instance = new HttpPosterCommons();			
		}
		return s_instance;
	}
	/**
	 * 
	 * @param defaultMaxConnPerHost
	 * @param connPerTimeOut
	 * @param maxTotalConn
	 * @param staleCheckingEnabled
	 * @param parameterMap
	 * @throws Exception
	 */
	public void setHttpConnectionSettings(int defaultMaxConnPerHost,int connPerTimeOut,int maxTotalConn,boolean staleCheckingEnabled,HashMap parameterMap ) throws Exception{
		try {
		    HttpPosterCommons.defaultMaxConnPerHost = (defaultMaxConnPerHost != -1)? defaultMaxConnPerHost:HttpPosterCommons.defaultMaxConnPerHost;
		    HttpPosterCommons.connPerTimeOut        = (connPerTimeOut != -1)? connPerTimeOut:HttpPosterCommons.connPerTimeOut;
		    HttpPosterCommons.maxTotalConn          = (maxTotalConn != -1)? maxTotalConn:HttpPosterCommons.maxTotalConn;
            this.staleCheckingEnabled  = staleCheckingEnabled;
            
		    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	        HttpConnectionManagerParams p = new HttpConnectionManagerParams();        
	        p.setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
	        p.setConnectionTimeout(connPerTimeOut);
	        p.setMaxTotalConnections(maxTotalConn);
	        p.setStaleCheckingEnabled(true);
	        
	        if(parameterMap != null){
                this.parameterMap   = parameterMap;             
                Iterator iterator   = parameterMap.keySet().iterator();
                String key = "";                
                while(iterator.hasNext()){
                    key = (String)iterator.next();
                    p.setParameter(key, parameterMap.get(key));
                }
            }
	            
	        connectionManager.setParams(p);

	        s_client_noproxy = new HttpClient(connectionManager);
			s_client         = new HttpClient(connectionManager);   
            if(!host.equals("")) {
                s_client.getHostConfiguration().setProxy(host, Integer.parseInt(port));
                Credentials defCred = new UsernamePasswordCredentials(username,password);
                s_client.getState().setProxyCredentials(new AuthScope(host, Integer.parseInt(port)), defCred);            
            }
            System.out.println(Utility.formatToScreen("CommonsHTTPConnection Initialized"));            
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).info("HttpPosterCommons,setConnectionSettings,OK,"+defaultMaxConnPerHost+","+connPerTimeOut+","+maxTotalConn+","+staleCheckingEnabled+","+parameterMap);
        } catch (Exception e) {
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,setConnectionSettings,ERR,"+defaultMaxConnPerHost+","+connPerTimeOut+","+maxTotalConn+","+staleCheckingEnabled+","+parameterMap,e);
        	throw new Exception("HttpPosterCommons setConnectionSettings error",e);
        }		
        
	}
	
	/**
	 * If a proxy is needed to make the connection it should be set here.
	 * @param host proxy host
	 * @param port proxy port
	 * @param username if the porxy server needs authntication set the user name using this 
	 *        or if the server does not need authentication leave this as <b>null</b>
	 * @param password to authenticate the user name to the proxy server. if the account has no 
	 *        password then leave it as <b>null</b>.<br/><br/> <b>NOTE:</b> if the username or password
	 *        is set to null then it will be intepretted as an empty string
	 */	
	public static void updateProxySettings(String host,String port,String username,String password){
		HttpPosterCommons.host			= (!host.equals(""))?host:HttpPosterCommons.host;
		HttpPosterCommons.port 			= (!port.equals(""))?port:HttpPosterCommons.port;
		HttpPosterCommons.username		= (!username.equals(""))?username:HttpPosterCommons.username;
		HttpPosterCommons.password 		= (!password.equals(""))?password:HttpPosterCommons.password;		
	}
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public void setProxySettings(String host,String port,String username,String password){
        this.host          = (!host.equals(""))?host:this.host;
        this.port          = (!port.equals(""))?port:this.port;
        this.username      = (!username.equals(""))?username:this.username;
        this.password      = (!password.equals(""))?password:this.password;       
    }	
	/**
	 * Executes the provided request string as a HTTP <b>post</b> request.
	 * @param URL the request will be made to this URL
	 * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
	 * @param request the request string 
	 * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
	 */
	public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,String request, HashMap headerParams) throws Exception {
	    return executeHttp(URL, reqParameterMap, request, "", "", headerParams ,true);
	}
	/**
     * Executes the provided request string as a HTTP <b>post</b> request.
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string 
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @param useProxy
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,String request, HashMap headerParams, boolean useProxy) throws Exception {
        return executeHttp(URL, reqParameterMap, request, "", "", headerParams ,useProxy);
    }
	/**
     * Executes the provided request string as a HTTP <b>post</b> or <b>get</b> request.
     * this is the recomended method when getting feeds (RSS/ATOM)
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param requestType {@link HttpPosterCommons}.GET_METHOD or {@link HttpPosterCommons}.POST_METHOD
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,int requestType) throws Exception {
	    if(requestType==POST_METHOD)
	        return executeHttp(URL, reqParameterMap, null, "", "", null ,true);
	    if(requestType==GET_METHOD)
	        return executeGetRequest(URL, reqParameterMap);
	    else
	        throw new Exception("Invalid request method type.");	    
    }
	/**
     * Executes the provided request string as a HTTP <b>post</b> or <b>get</b> request.
     * this is the recomended method when getting feeds (RSS/ATOM)
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param requestType {@link HttpPosterCommons}.GET_METHOD or {@link HttpPosterCommons}.POST_METHOD
     * @param responceCharset the character of the response. if this parameter is empty or set to null the default characterr set of the response will be used.
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,int requestType, String responceCharset) throws Exception {
        if(requestType==POST_METHOD)
            return executeHttp(URL, reqParameterMap, null, "", "", null ,true, responceCharset);
        if(requestType==GET_METHOD)
            return executeGetRequest(URL, reqParameterMap, true, responceCharset, null, false);
        else
            throw new Exception("Invalid request method type.");        
    }
	/**
     * Executes the provided request string as a HTTP <b>post</b> or <b>get</b> request.
     * this is the recomended method when getting feeds (RSS/ATOM)
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param requestType {@link HttpPosterCommons}.GET_METHOD or {@link HttpPosterCommons}.POST_METHOD
     * @param responceCharset the character of the response. if this parameter is empty or set to null the default characterr set of the response will be used.
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
    public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,int requestType, String responceCharset, HashMap headerParams) throws Exception {
        if(requestType==POST_METHOD)
            return executeHttp(URL, reqParameterMap, null, "", "", headerParams ,true, responceCharset);
        if(requestType==GET_METHOD)
            return executeGetRequest(URL, reqParameterMap, true, responceCharset, headerParams, false);
        else
            throw new Exception("Invalid request method type.");        
    }
	/**
     * Executes the provided request string as a HTTP <b>post</b> or <b>get</b> request.
     * this is the recomended method when getting feeds (RSS/ATOM)
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param requestType {@link HttpPosterCommons}.GET_METHOD or {@link HttpPosterCommons}.POST_METHOD
     * @param useProxy if to use proxy
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>     * 
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table>  
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeAsHttpRequest(String URL,HashMap reqParameterMap,int requestType, boolean useProxy) throws Exception {
        if(requestType==POST_METHOD)
            return executeHttp(URL, reqParameterMap, null, "", "", null ,useProxy);
        if(requestType==GET_METHOD)
            return executeGetRequest(URL, reqParameterMap, useProxy);
        else
            throw new Exception("Invalid request method type.");        
    }
	/**
	 * Executes the provided request string as a WEB Service request.
	 * @param URL the request will be made to this URL
	 * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
	 * @param request the request string
	 * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
	 * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
	 * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
	 * @return a {@link HashMap} containing the following values<br/>
	 * <table width="300" border="1" cellspacing="0" cellpadding="2">
	 * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
	 * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
	 * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
	 * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
	 * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
	 * 
	 * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
	 */
	public HashMap executeAsWebService(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, HashMap headerParams) throws Exception {
	    return executeHttp(URL, reqParameterMap, request, contentType, charSet, headerParams ,true);
	}
	/**
     * Executes the provided request string as a WEB Service request.
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeAsWebService(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, HashMap headerParams, boolean useProxy) throws Exception {
        return executeHttp(URL, reqParameterMap, request, contentType, charSet, headerParams ,useProxy);
    }
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeHttp(String URL,HashMap reqParameterMap,String request,String contentType,String charSet) throws Exception {
        return executeHttp(URL, reqParameterMap, request, contentType, charSet, null ,true);
    }
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeHttp(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, boolean useProxy) throws Exception {
        return executeHttp(URL, reqParameterMap, request, contentType, charSet, null ,useProxy);
    }
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeHttp(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, HashMap headerParams) throws Exception {
	    return executeHttp(URL,reqParameterMap,request,contentType,charSet,headerParams,true);
	}	
	
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeHttp(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, HashMap headerParams, boolean useProxy) throws Exception {
	    return executeHttp(URL, reqParameterMap, request, contentType, charSet, headerParams, useProxy, null);
	}
	/**
	 * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
	 * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
	 * the request content will be sent as a <b>RequestEntity</b> 
	 * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param contentType The prefered content type of the reqest. {@link HttpPosterCommons#REQUEST_CONTENT_TYPE}
     * @param charSet The prefered charset of the reqest. {@link HttpPosterCommons#REQUEST_CHAR_SET}
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @param useProxy if or not to use proxy authentication to make the request
     * @param responceCharset the character of the response. if this parameter is empty or set to null the default characterr set of the response will be used.
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
	 */
	public HashMap executeHttp(String URL,HashMap reqParameterMap,String request,String contentType,String charSet, HashMap headerParams, boolean useProxy, String responseCharset) throws Exception {
		String retStr 		= "";
		long responseTime 	= 0;
		int statusCode 		= 0;
	    String respStr 		= "";
	    HashMap responseMap = new HashMap();
	    PostMethod method 	= new PostMethod(URL);
	    long reqCode        = System.currentTimeMillis(); 
	    try {
	        SystemLog.getInstance().getLogger(EVENT_LOG_NAME).info("HttpPosterCommons,executeHttp,"+reqCode+","+URL+","+reqParameterMap+",\""+Utility.removeNewLines(request)+"\","+contentType+","+charSet+","+headerParams);
	        
	        //Set header parameters
	        if(headerParams!=null) {
	            Iterator iterator = headerParams.keySet().iterator();
	            while (iterator.hasNext()) {
	                String key = (String)iterator.next();
	                method.setRequestHeader(key, (String)headerParams.get(key));
	            }
	        }
	        
    	    // set request entities
    	    if( Utility.isValid(contentType) && Utility.isValid(charSet) ){
    			try {
    				RequestEntity entity = new StringRequestEntity(request, contentType, charSet);
    				method.setRequestEntity(entity);								
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    	    }
    	    
    	    // add request parameters
    	    if(reqParameterMap != null){
    	    	Iterator iterator		= reqParameterMap.keySet().iterator();
    			String key = "";
    			NameValuePair nameValuePair	= null;
    			
    			while(iterator.hasNext()){
    				key = (String)iterator.next();
    				nameValuePair = new NameValuePair();
    				nameValuePair.setName(key);
    				nameValuePair.setValue((String)reqParameterMap.get(key));
    				method.addParameter(nameValuePair);			
    			}
    	    }
    	    SystemLog.getInstance().getLogger(REQ_LOG_NAME).info(reqCode+","+URL+","+reqParameterMap+",\""+Utility.removeNewLines(request)+"\","+contentType+","+charSet+","+headerParams);
    	    try {
            	responseTime = System.currentTimeMillis();
            	if(useProxy)
            	    statusCode = s_client.executeMethod(method);
            	else
            	    statusCode = s_client_noproxy.executeMethod(method);
                if (statusCode == HttpStatus.SC_OK) {
                    if(Utility.isValid(responseCharset)) {
                        byte[] buf = method.getResponseBody();
                        retStr = new String(buf, responseCharset);
                    } else {
                        retStr = method.getResponseBodyAsString();                        
                    }
                    respStr = retStr;
                }else{
                    retStr = null;
                    respStr = method.getResponseBodyAsString();                
                }            
                responseTime = System.currentTimeMillis() - responseTime; 
                
            } catch (Exception e) {
                SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
                SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeHttp,"+reqCode+","+URL+","+reqParameterMap+",\""+Utility.removeNewLines(request)+"\","+contentType+","+charSet+","+headerParams,e);
            	throw new Exception("HttpPosterCommons execute error",e);
            }finally{
                method.releaseConnection();
            }        
            responseMap.put("response", retStr);
            responseMap.put("responseTime",responseTime+"");
            responseMap.put("statusCode",statusCode+"");        
            responseMap.put("errorResponse",respStr);
            
            SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"OK"+","+"\""+Utility.removeNewLines(retStr)+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\"");
            SystemLog.getInstance().getLogger(TXN_LOG_NAME).info("REQ="+"\"["+reqCode+","+URL+","+reqParameterMap+",\""+Utility.removeNewLines(request)+"\","+contentType+","+charSet+","+headerParams+"]\""+","+
                                                                 "RES="+"\"["+reqCode+","+"OK"+","+"\""+Utility.removeNewLines(retStr)+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\""+"]\"");        
            return responseMap;
	    }catch (Exception e) {
	        SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeHttp,"+reqCode+","+URL+","+reqParameterMap+",\""+Utility.removeNewLines(request)+"\","+contentType+","+charSet,e);
            throw e;
        }
	}
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeGetRequest(String URL,HashMap reqParameterMap) throws Exception {
	    return executeGetRequest(URL, reqParameterMap,true);
	}
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeGetRequest(String URL,HashMap reqParameterMap, boolean useProxy) throws Exception {
	    return executeGetRequest(URL, reqParameterMap, useProxy, null, null, false);
	}
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeGetRequestAsStream(String URL,HashMap reqParameterMap, boolean useProxy) throws Exception {
        return executeGetRequest(URL, reqParameterMap, useProxy, null, null, true);
    }
	/**
     * Executes the provided request string as a WEB Service request or a HTTP <b>post</b> request.
     * if the contentType & charSet parameters are set to null then a HTTP post request will be made else 
     * the request content will be sent as a <b>RequestEntity</b> 
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param request the request string
     * @param useProxy if or not to use proxy authentication to make the request
     * @param responceCharset the character of the response. if this parameter is empty or set to null the default characterr set of the response will be used.
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeGetRequest(String URL,HashMap reqParameterMap, boolean useProxy, String responseCharset, HashMap headerParams, boolean responseAsByteArray) throws Exception {
        String retStr           = "";
        long responseTime       = 0;
        int statusCode          = 0;
        String respStr          = "";
        byte[] respStream       = null;
        HashMap responseMap     = new HashMap();
        GetMethod method        = null;
        long reqCode            = System.currentTimeMillis(); 
        try {
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).info("HttpPosterCommons,executeGetRequest,"+reqCode+","+URL+","+reqParameterMap+","+headerParams);            
            
            // add request parameters
            String params = "";
            if(reqParameterMap != null){                
                Iterator iterator = reqParameterMap.keySet().iterator();
                while(iterator.hasNext()){
                    if(params.trim().length()>0) {
                        params = params+"&";
                    }
                    String key   = (String)iterator.next();
                    String value = ((String)reqParameterMap.get(key));
                    params = params+key+"="+value;
                }
            }
            if(params.trim().length()>0) {
                URL = URL+((URL.indexOf("?")==-1)?"?":"&")+params;
            }                          
            method = new GetMethod(URL);
            
            //Set header parameters
            if(headerParams!=null) {
                Iterator iterator = headerParams.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String)iterator.next();
                    method.setRequestHeader(key, (String)headerParams.get(key));
                }
            }
            
            SystemLog.getInstance().getLogger(REQ_LOG_NAME).info(reqCode+","+URL+","+reqParameterMap+","+headerParams);
            try {
                responseTime = System.currentTimeMillis();   
                if(useProxy)
                    statusCode = s_client.executeMethod(method);
                else 
                    statusCode = s_client_noproxy.executeMethod(method);
                if (statusCode == HttpStatus.SC_OK) {
                    if(responseAsByteArray) {
                        respStream = method.getResponseBody();
                    }
                    else {
                        if(Utility.isValid(responseCharset)) {
                            byte[] buf = method.getResponseBody();
                            retStr = new String(buf, responseCharset);
                        } else {
                            retStr = method.getResponseBodyAsString();                        
                        }
                        respStr = retStr;
                    }
                }else{
                    retStr = null;                    
                    respStr = method.getResponseBodyAsString();                
                }            
                responseTime = System.currentTimeMillis() - responseTime; 
                
            } catch (Exception e) {
                SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
                SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeGetRequest,"+reqCode+","+URL+","+reqParameterMap+","+headerParams,e);
                throw new Exception("HttpPosterCommons execute error",e);
            }finally{
                method.releaseConnection();
            }        
            if(responseAsByteArray) {
                responseMap.put("response", respStream);
            }
            else {
                responseMap.put("response", retStr);    
            }            
            responseMap.put("responseTime",responseTime+"");
            responseMap.put("statusCode",statusCode+"");        
            responseMap.put("errorResponse",respStr);
            
            SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"OK"+","+"\""+Utility.removeNewLines(retStr)+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\"");
            SystemLog.getInstance().getLogger(TXN_LOG_NAME).info("REQ="+"\"["+reqCode+","+URL+","+reqParameterMap+"]\""+","+
                                                                 "RES="+"\"["+reqCode+","+"OK"+","+"\""+Utility.removeNewLines(retStr)+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\""+"]\"");        
            return responseMap;
        }catch (Exception e) {
            SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeHttp,"+reqCode+","+URL+","+reqParameterMap+","+headerParams,e);
            throw e;
        }
    }
	/**
     * Executes the provided request string as a Multi Part HTTP <b>post</b> request.
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
	public HashMap executeHttpMultiPart(String URL,HashMap parameterMap, HashMap headerParams, boolean useProxy) throws Exception {
	    return executeHttpMultiPart(URL, parameterMap, headerParams, useProxy, null);
	}
	/**
     * Executes the provided request string as a Multi Part HTTP <b>post</b> request.
     * 
     * @param URL the request will be made to this URL
     * @param reqParameterMap this {@link HashMap} will contain the list of parameters to send with the request
     * @param headerParams if any header params should be set those values shoud be set using this map. if not set to <b>null</b>
     * @param useProxy if or not to use proxy authentication to make the request
     * @param responceCharset the character of the response. if this parameter is empty or set to null the default characterr set of the response will be used.
     * @return a {@link HashMap} containing the following values<br/>
     * <table width="300" border="1" cellspacing="0" cellpadding="2">
     * <tr bgcolor="#CCCCCC"><td width="104"><b>Key</b></td><td width="190"><b>Description</b></td>
     * </tr><tr><td>response</td><td>The response content returned for the request</td></tr>
     * <tr><td>responseTime</td><td>Time duration between request &amp; response</td></tr>
     * <tr><td>statusCode</td><td>HTTP Status code</td></tr><tr><td>errorResponse</td>
     * <td>if error occoured during the process this field will contain the error code.</td></tr></table> 
     * 
     * @throws Exception if an error occoured during the process the resulting exception will be returned to the caller
     */
    public HashMap executeHttpMultiPart(String URL,HashMap parameterMap, HashMap headerParams, boolean useProxy, String responseCharset) throws Exception {
        String retStr       = "";
        long responseTime   = 0;
        int statusCode      = 0;
        String respStr      = "";
        HashMap responseMap = new HashMap();
        PostMethod method   = new PostMethod(URL);
        long reqCode        = System.currentTimeMillis(); 
        try {
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).info("HttpPosterCommons,executeHttp,"+reqCode+","+URL+","+parameterMap+",\""+headerParams);
            
            //Set header parameters
            if(headerParams!=null) {
                Iterator iterator = headerParams.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String)iterator.next();
                    method.setRequestHeader(key, (String)headerParams.get(key));
                }
            }
            
            // add request parameters
            if(parameterMap != null) {
                Part[] parts = new Part[parameterMap.keySet().size()];                
                MultipartRequestEntity req  = null;
                Iterator iterator           = parameterMap.keySet().iterator();                
                int i=0;
                while(iterator.hasNext()){                    
                    String key = (String)iterator.next();
                    Object value = parameterMap.get(key);
                    
                    if(value instanceof File) {
                        FilePart fPart = new FilePart(key, (File)value);
                        parts[i] = fPart;
                    } 
                    if(value instanceof FilePart) {                        
                        parts[i] = ((FilePart)(value));
                    } 
                    else {
                        StringPart stringPart = new StringPart(key, (String)value);   
                        parts[i] = stringPart;
                    }
                    i++;
                }
                
                req=new MultipartRequestEntity(parts,method.getParams());
                method.setRequestEntity(req);
            }
            SystemLog.getInstance().getLogger(REQ_LOG_NAME).info(reqCode+","+URL+","+parameterMap+",\""+headerParams);
            try {
                responseTime = System.currentTimeMillis();
                if(useProxy)
                    statusCode = s_client.executeMethod(method);
                else
                    statusCode = s_client_noproxy.executeMethod(method);
                if (statusCode == HttpStatus.SC_OK) {               
                    if(Utility.isValid(responseCharset)) {
                        byte[] buf = method.getResponseBody();
                        retStr = new String(buf, responseCharset);
                    } else {
                        retStr = method.getResponseBodyAsString();                        
                    }
                    respStr = retStr;
                }else{
                    retStr = null;
                    respStr = method.getResponseBodyAsString();                
                }            
                responseTime = System.currentTimeMillis() - responseTime; 
                
            } catch (Exception e) {
                SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
                SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeHttpMultiPart,"+reqCode+","+URL+","+parameterMap+",\""+headerParams,e);
                throw new Exception("HttpPosterCommons execute error",e);
            }finally{
                method.releaseConnection();
            }        
            responseMap.put("response", retStr);
            responseMap.put("responseTime",responseTime+"");
            responseMap.put("statusCode",statusCode+"");        
            responseMap.put("errorResponse",respStr);
            
            SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"OK"+","+"\""+retStr+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\"");
            SystemLog.getInstance().getLogger(TXN_LOG_NAME).info("REQ="+"\"["+reqCode+","+URL+","+parameterMap+",\""+headerParams+"]\""+","+
                                                                 "RES="+"\"["+reqCode+","+"OK"+","+"\""+Utility.removeNewLines(retStr)+"\""+responseTime+","+statusCode+"\""+Utility.removeNewLines(respStr)+"\""+"]\"");        
            return responseMap;
        }catch (Exception e) {
            SystemLog.getInstance().getLogger(RES_LOG_NAME).info(reqCode+","+"ERR"+","+e.getMessage());
            SystemLog.getInstance().getLogger(EVENT_LOG_NAME).error("HttpPosterCommons,executeHttpMultiPart,"+reqCode+","+URL+","+parameterMap+",\"",e);
            throw e;
        }
    }
	/**
	 * 
	 */
	public void resetProxy() {
	   s_client.setHostConfiguration(new HostConfiguration()); 
	}
	
	public static void main(String[] args) {
	    //String XML_API_PATH	= "http://172.26.19.4:8289/CBLInterface/Login";		
		//XML_API_PATH = "http://www.zensslk.com/Default.aspx?xml=";
		/*String reqXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
        "<soapenv:Body><ns1:orderTone soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns1=\"http://usertonemanage.ivas.huawei.com\">" +
        "<event href=\"#id0\"/></ns1:orderTone><multiRef id=\"id0\" soapenc:root=\"0\" " +
        "soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"ns2:OrderToneEvt\" " +
        "xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:ns2=\"http://event.usertonemanage.ivas.huawei.com\">" +
        "<moduleCode xsi:type=\"soapenc:string\" xsi:nil=\"true\"/><phoneNumber xsi:type=\"soapenc:string\">777334123</phoneNumber>" +
        "<portalAccount xsi:type=\"soapenc:string\">admin</portalAccount><portalPwd xsi:type=\"soapenc:string\">admin</portalPwd>" +
        "<portalType xsi:type=\"soapenc:string\">2</portalType><rankType xsi:type=\"soapenc:string\" xsi:nil=\"true\"/>" +
        "<resourceCode xsi:type=\"soapenc:string\"/><resourceID xsi:type=\"soapenc:string\">47805</resourceID>" +
        "<resourceType xsi:type=\"soapenc:string\">1</resourceType><role xsi:type=\"soapenc:string\">1</role>" +
        "<roleCode xsi:type=\"soapenc:string\">777334123</roleCode></multiRef></soapenv:Body></soapenv:Envelope>";*/

		//String reqXML = "<service-request name=\"registration\"><tracking><request-id>1234</request-id><request-type>VOICE</request-type><timestamp>20090218174568</timestamp><serviceNo>777335365</serviceNo></tracking><parameters><parameter name=\"cxname\">test user</parameter><parameter name=\"dateTime\">3/25/2009 12:00:00 AM</parameter><parameter name=\"birthplace\">03</parameter><parameter name=\"uid\">03</parameter></parameters></service-request>";
				
		//String reqXML = "<service-request name=\"dailyhoroscope\"><tracking><request-id>1234</request-id><request-type>VOICE</request-type><timestamp>20090218174568</timestamp><serviceNo>777335365</serviceNo></tracking><parameters><parameter name =\"dateTime\">22/10/1990|04:34:AM</parameter>><parameter name =\"birthplace\">Colombo</parameter><parameter name=\"uid\">6546554</parameter></parameters></service-request>";
		//String reqXML  	= "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CBL_REQUEST><HEADER><USERID>DTL</USERID><PASSWORD>DTL</PASSWORD><METHOD_NAME>CBL_LOGIN</METHOD_NAME></HEADER></CBL_REQUEST>";
		//String reqXML = "<CBL_REQUEST><HEADER><SESSION_ID>$session</SESSION_ID><METHOD_NAME>FUNC_GET_ALL_ACCOUNTS</METHOD_NAME></HEADER></CBL_REQUEST>";
		//HashMap<String,String> responseMap = HttpPosterCommons.getInstance().executeHttp(XML_API_PATH, null, "?source="+reqXML, "text/xml", "utf-8");
		HashMap responseMap;
		try {
		    HashMap reqparams = new HashMap(0);
            reqparams.put("msisdn", "94777335365");
            
            responseMap = HttpPosterCommons.getInstance().executeGetRequestAsStream("http://192.168.71.225/interimbillpdfext.php", reqparams, false);
		    
            byte[] buf = (byte[])responseMap.get("response");
            String statusCode = (String)responseMap.get("statusCode");
		    
            System.out.println(statusCode);
            System.out.println();
            System.out.println(buf);
            
		    /*HashMap reqparams = new HashMap(0);
		    reqparams.put("xml", reqXML);*/
		    //HashMap headerParams = new HashMap(0);
		    //headerParams.put("SOAPAction", "http://tempuri.org/Sell_Product");
		    /*HashMap paraMap = new HashMap();
		    paraMap.put("xml", reqXML);*/
			/*responseMap = HttpPosterCommons.getInstance().executeAsWebService(XML_API_PATH, null, reqXML, 
			                            HttpPosterCommons.REQUEST_CONTENT_TYPE, HttpPosterCommons.REQUEST_CHAR_SET,
			                            headerParams);*/	    
		    //HttpPosterCommons.getInstance().setProxySettings("192.168.95.2", "8080", "charith_02380", "m1n1st1r1th");
		    //HttpPosterCommons.getInstance().setHttpConnectionSettings(30, 10000, 30, true, null);
		    //responseMap = HttpPosterCommons.getInstance().executeAsHttpRequest(XML_API_PATH, null, reqXML, null);
		    
		    /*HashMap loginPara = new HashMap();
		    loginPara.put("action", "login");
		    loginPara.put("username", "test");
		    loginPara.put("password", "test");
		    loginPara.put("fmt", "xml");
		    responseMap = HttpPosterCommons.getInstance().executeAsHttpRequest(
		            "http://172.22.6.64:7070/corporate/ctrl/mylogin/login.jsp", 
		            loginPara, null, null);		    
			String res = (String)responseMap.get("response");
			System.out.println("respXML : "+res);*/
		    
		    /*HttpPosterCommons.updateProxySettings("192.168.95.2", "8080", "charith_02380", "xxx");
		    HashMap response = HttpPosterCommons.getInstance().executeAsHttpRequest("http://www.defence.lk/news/rss20.xml", null, HttpPosterCommons.GET_METHOD);
		    if(response.get(HttpPosterCommons.GET_RESPONSE_STATUS_CODE).toString().trim().equalsIgnoreCase("200")) {
		        System.out.println(response.get(HttpPosterCommons.GET_RESPONSE_ERR_CONTENT).toString());
		    } else {
		        System.out.println(response.get(HttpPosterCommons.GET_RESPONSE_CONTENT).toString());
		    }*/
		    
		    /*String reqXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                            "<soap:Body>"+
                            "<Sell_Product xmlns=\"http://tempuri.org/\">"+
                            "<ezMARTUserName>princess_leia</ezMARTUserName>"+
                            "<encryptedKey>string</encryptedKey>"+
                            "<itemID>TEST0001</itemID>"+
                            "<Price>10</Price>"+
                            "<PriceRange>10</PriceRange>"+
                            "<QuantityMin>1</QuantityMin>"+
                            "<QuantityMax>10</QuantityMax>"+
                            "<ValidityFrom>20100924</ValidityFrom>"+
                            "<ValidityTo>20100930</ValidityTo>"+
                            "<SpecialTerms>Testing</SpecialTerms>"+
                            "<SendAlerts>0</SendAlerts>"+
                            "</Sell_Product>"+
                            "</soap:Body>"+
                            "</soap:Envelope>";
		    
		    HttpPosterCommons.updateProxySettings("proxy.dialog.dialoggsm.com", "8080", "charith_02380", "m1n1st1r1th!");*/
		    //HashMap response = HttpPosterCommons.getInstance().executeGetRequest("http://www.dinamani.com/edition/rssSectionXml.aspx?SectionId=164", null, true, "UTF-8", null);
		    //HashMap response = HttpPosterCommons.getInstance().executeGetRequest("http://www.jasminenews.com/lankanews/sinhala/feed", null, true, "UTF-8");
		    //HashMap response = HttpPosterCommons.getInstance().executeGetRequest("http://www.jasminenews.com/lankanews/sinhala/feed", null, true);
		    //HashMap response = HttpPosterCommons.getInstance().executeGetRequest("http://www.vimasuma.com/rss/dialog_rss_vimasuma.xml", null, true);
		    /*HashMap response = HttpPosterCommons.getInstance().executeAsWebService(
		            "http://172.26.66.30/Ws_eZMART_Upload/WS_eZMARTUpload.asmx", 
		            null, reqXML, 
                    HttpPosterCommons.REQUEST_CONTENT_TYPE, HttpPosterCommons.REQUEST_CHAR_SET,
                    headerParams);
		    if(response.get(HttpPosterCommons.GET_RESPONSE_STATUS_CODE).toString().trim().equalsIgnoreCase("200")) {
		        String str = response.get(HttpPosterCommons.GET_RESPONSE_ERR_CONTENT).toString();
		        Document doc = Utility.convertXMLStr2DOMDoc(str, "UTF-8");
                System.out.println(doc.getRootElement().getChildren());
		        System.out.println(str);
            } else {
                System.out.println(response.get(HttpPosterCommons.GET_RESPONSE_CONTENT).toString());
            }*/
			
			/*HashMap addPara = new HashMap();
            loginPara.put("action", "AddNewDistCont");
            loginPara.put("distID", "85");
            loginPara.put("distContNo", "777335372");
            loginPara.put("distContDesc", "Praveen Dangalla");
            loginPara.put("fmt", "xml");
			responseMap = HttpPosterCommons.getInstance().executeAsHttpRequest(
			        "http://172.22.6.64:7070/corporate/ctrl/distributions/corpDistCtrl.jsp", 
			        loginPara, null, null);          
            res = (String)responseMap.get("response");
            System.out.println("respXML : "+res);*/
			//GenericResponse genRes = ResponseParser.parse2GenericResponse(res);
			//System.out.println(genRes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
