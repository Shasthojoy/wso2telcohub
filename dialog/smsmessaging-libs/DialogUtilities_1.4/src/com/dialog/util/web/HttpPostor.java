package com.dialog.util.web;
/**
 * Title		:HttpPost
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:May 10, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

public class HttpPostor {
	
	private static ProxyAuthenticator pauth = null;
	private String url="";
	private String method="";
	private String parameters="";
	private HashMap map = new HashMap();
	
	public static void setProxy(String proxyIP,String proxyPort,String userName,String password){
		Properties props = System.getProperties();
		props.put("http.proxyHost", proxyIP);
		props.put("http.proxyPort", proxyPort);
		props.put("http.proxyUser", userName);
		props.put("http.proxyPassword", password);
		pauth = new ProxyAuthenticator(userName,password);
		Authenticator.setDefault( pauth );
	}
	
	public static void resetProxy(){
		Properties props = System.getProperties();
		props.put("http.proxyHost", "");
		props.put("http.proxyPort", "");
		Authenticator.setDefault( null );
	}
	/**
	 *  
	 */
	
	public HttpPostor(String url,String method) {
		this.url = url;
		this.method = method;
		HttpURLConnection.setFollowRedirects(true);
	}
	
	public void addParameter(String paraName, String paraValue) throws UnsupportedEncodingException {
		//Deprecated under Java 5.0
		//String tmp = URLEncoder.encode(paraValue);	    
		String csName = null;
		try {
		    csName = Charset.forName("defaultCharset").name();
		} catch (Exception e) {}
		
		if(csName==null) {
		    try {
	            csName = Charset.forName("ISO-8859-1").name();
	        } catch (Exception e) {}
		}	
	    
		String tmp = "";
		if(csName == null)
		    tmp = URLEncoder.encode(paraValue);
		else 
		    tmp = URLEncoder.encode(paraValue,csName);
		if ( this.parameters != ""){		
			this.parameters += "&";
		}
		this.parameters += paraName + "=" + tmp;
	}
	
	public void clearParameters(){
		this.parameters = "";
	}
	
	public String getParameter(){
		return this.parameters;
	}
	
	public void setRequestProperty(String propName, String propValue){
		map.put(propName,propValue);
	}
	
	public void clearRequestProperties(){
		map = new HashMap();
	}
	
	public String execute() throws Exception{
		String retStr = "";
		String strURL = this.url + ( this.method.equals("GET") && this.parameters != "" ? "?" + this.parameters : "" );
		
		URL url = new URL(strURL);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		
		Iterator it = map.keySet().iterator();
		while ( it.hasNext() ){
			String key = (String)it.next();
			String val = (String)map.get(key);
			connection.setRequestProperty(key,val);		    
		}
		
		if ( this.method.equals("POST") ){			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			System.getProperties().setProperty("defaultConnectTimeout", "500");
			System.getProperties().setProperty("defaultReadTimeout","500");
			//System.out.println("1 >>"+System.currentTimeMillis());
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			//System.out.println("2 >>"+System.currentTimeMillis());
			// write output paramaters		
			out.println(this.parameters);
			//out.flush();
			out.close();			
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line =null;
		
		//DEBUG
		while ((line = in.readLine()) != null) {
			//System.out.println(">>"+line);
			retStr += line;				
		}	
		in.close();
		connection.disconnect();
		connection = null;
		return retStr;
	}
	
	public static void main(String[] args) throws Exception {
		//HttpPostor.setProxy("192.168.95.2","8080","Basic Y2hhbmRpbWFsX2lidTp0aGV0cnV0aA==");
		//Basic Y2hhbmRpbWFsX2lidTpEcmFnb25Mb3Jk
		//HttpPostor.setProxy("192.168.95.2","8080","chandimal_ibu","Silverthorn");
		//HttpPostor h = new HttpPostor("http://202.69.200.242/servlet/cxSubmit","GET");
		//HttpPostor h = new HttpPostor("http://horoscopes.astrology.com/dailyaries.html","GET");
		//HttpPostor h = new HttpPostor("http://www.yahoo.com","GET");
		//h.addParameter("cxno","777331370");
		
		//HttpPostor h = new HttpPostor("http://www.nexus.nationstrust.com/nexus/smspoint.asp","GET");			
		//h.addParameter("msisdn","7331370");
		//h.addParameter("message","1121041800707008");
		
		/*
		 HttpPostor h = new HttpPostor("http://dialog-srilanka.smsproxy.airg.ca/dialogMonAmi/","GET");			
		 h.addParameter("msisdn","7331370");
		 h.addParameter("message","MA+HELP");
		 */
		HttpPostor h = new HttpPostor("http://172.26.1.40:8989/CGApps/jsp/cgAction.jsp","GET");
		System.out.println(h.execute());
	}
}
