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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Properties;

public class HttpPostorBinary {
	
	private static ProxyAuthenticator pauth = null;
	private String url="";
	private String method="";
	private String parameters="";
	private String fileName = "default";
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
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
	
	public HttpPostorBinary(String url,String method) {
		this.url = url;
		this.method = method;													
	}
	
	public void addParameter(String paraName, String paraValue) throws UnsupportedEncodingException{
		//Deprecated under Java 5.0
		//String tmp = URLEncoder.encode(paraValue);
						
		String tmp = URLEncoder.encode(paraValue,Charset.forName("defaultCharset").name());
		//String tmp = paraValue;
		if ( this.parameters != ""){		
			this.parameters += "&";
		}
		this.parameters += paraName + "=" + tmp;
	}
	
	public String execute() throws Exception{
		String retStr="";
		String strURL = this.url + ( this.method.equals("GET") && this.parameters != "" ? "?" + this.parameters : "" );
		System.out.println("HTTP-POSTOR:URL="+strURL);		
		URL url = new URL(strURL);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//connection.setRequestProperty("host","dialog.lk");
		
		if ( this.method.equals("POST") ){			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			// write output paramaters		
			out.println(this.parameters);
			//out.flush();
			out.close();			
		}
		
		InputStream in = connection.getInputStream();
		FileOutputStream fout = new FileOutputStream(fileName);
		int val = 0;
		//DEBUG
		while ((val = in.read()) != -1) {
			fout.write(val);				
		}	
		in.close();
		fout.flush();
		fout.close();		
		return retStr;
	}
	
	public static void main(String[] args) throws Exception {
		//HttpPostor.setProxy("192.168.95.2","8080","Basic Y2hhbmRpbWFsX2lidTp0aGV0cnV0aA==");
		//HttpPostor.setProxy("192.168.95.2","8080","chandimal_ibu","Silverthorn");
		//HttpPostor h = new HttpPostor("http://202.69.200.242/servlet/cxSubmit","GET");
		//HttpPostor h = new HttpPostor("http://horoscopes.astrology.com/dailyaries.html","GET");
		//HttpPostor h = new HttpPostor("http://www.yahoo.com","GET");
		//h.addParameter("cxno","777331370");
		
		//HttpPostor h = new HttpPostor("http://www.nexus.nationstrust.com/nexus/smspoint.asp","GET");			
		//h.addParameter("msisdn","7331370");
		//h.addParameter("message","1121041800707008");
	}
}
