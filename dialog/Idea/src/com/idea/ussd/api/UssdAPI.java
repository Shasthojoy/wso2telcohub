package com.idea.ussd.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.idea.ussd.ctrl.RequestManager;
import com.idea.ussd.ctrl.UrlProvisionEntity;
import com.idea.ussd.dto.Application;
import com.idea.ussd.dto.InboundMessage;
import com.idea.ussd.dto.InboundUSSDMessageRequest;
import com.idea.ussd.dto.NIMsisdn;
import com.idea.ussd.dto.OutboundMessage;
import com.idea.ussd.dto.OutboundRequest;
import com.idea.ussd.dto.USSDAction;
import com.idea.ussd.dto.VXMLFactory;
import com.idea.ussd.dto.Vxml;
import com.idea.ussd.dto.Vxml.Form;
import com.idea.ussd.dto.Vxml.Form.Block;
import com.idea.ussd.dto.Vxml.Form.Field;
import com.idea.ussd.dto.Vxml.Form.Filled;
import com.idea.ussd.dto.Vxml.Form.Filled.Assign;
import com.idea.ussd.dto.Vxml.Form.Filled.Goto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.log.Log;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.FormParam;

import com.idea.ussd.util.AirTelSSLSocket;
/**
 * @author Charith_02380
 *
 */
@Path("/")
public class UssdAPI {
	final static Logger log = Logger.getLogger(UssdAPI.class);
	private RequestManager manager;
	private Properties settings;
	private Gson gson;

	/**
	 * 
	 */
	public UssdAPI() {
		try {
			gson = new GsonBuilder().serializeNulls().create();
			
			manager = new RequestManager();
			settings = new Properties();
			settings.loadFromXML(UssdAPI.class.getResourceAsStream("settings.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@POST
	@Path("v1/outbound/{senderAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response outboundRequest(@Context HttpServletRequest hsr, @PathParam("senderAddress") String senderAddress, String jsonBody) {
		try {
					
			OutboundMessage outboundMsg = gson.fromJson(jsonBody, OutboundMessage.class);	
			Application application = null;
			
			if(outboundMsg.getOutboundUSSDMessageRequest().getKeyword()==null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length()==0) {
				application = manager.getUniqueApplication(
						outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
			}
			else {
				application = manager.getApplication(
						outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), 
						outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
			}
			if(application == null) {
				throw new Exception("Application does not exist");
			}
			
			OutboundRequest request = new OutboundRequest();
			request.setApplication(application);
			outboundMsg.getOutboundUSSDMessageRequest().setAddress(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replace("tel:", "").replaceAll("\\+", ""));
			request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
			
			Long id = manager.saveRequest(request);
			
			if(id!=null) {
				/**
				 * When an NI USSD is pushed, the gateway calls the registered url for the APP.
				 * Which is ussd/route/{msisdn}
				 * There this key will be used to identify the Application & NI USSD Request
				 * Key format <USSD Action>:<Keyword of the App>:<Request ID>
				 */
				String inputKey =
						USSDAction.mtinit.toString()+":"+
						outboundMsg.getOutboundUSSDMessageRequest().getKeyword()+":"+
						id;
				
                                //Disabling Certificate Test
                          /*      TrustManager[] trustAllCerts = new TrustManager[] { 
                                new X509TrustManager() {     
                                    public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
                                        return new X509Certificate[0];
                                    } 
                                    public void checkClientTrusted( 
                                        java.security.cert.X509Certificate[] certs, String authType) {
                                        } 
                                    public void checkServerTrusted( 
                                        java.security.cert.X509Certificate[] certs, String authType) {
                                    }
                                } 
                                    }; 

                                // Install the all-trusting trust manager
                                try {
                                    SSLContext sc = SSLContext.getInstance("SSL"); 
                                    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
                                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                } catch (GeneralSecurityException e) {
                                } */
                                
                                
                                
                                
                                
				String niURL = settings.getProperty("ni_ussd_url");
				
				String outboundUSSDMessage = URLEncoder.encode(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "UTF-8");
				
				Object[] messageparams = {
						outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),
						outboundUSSDMessage};
				niURL = MessageFormat.format(niURL,messageparams);
				
				HttpGet get = new HttpGet(niURL);
				HttpPost post = new HttpPost(niURL);
				CloseableHttpClient httpclient = null;
				CloseableHttpResponse response = null;
				try {
					//httpclient = HttpClients.createDefault();
					httpclient = getNewHttpClient();
                                        //httpclient = getNewHttpClient();
					//response = httpclient.execute(post);
                                        
                                        log.info("NIURL:" + niURL);
                                        
                                        String path = "/home/ec2-user/client-truststore.jks";

                                        System.setProperty("javax.net.ssl.trustStore", path);

                                        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
                                        
                                        
					response = httpclient.execute(post);
					HttpEntity entity = response.getEntity();
										
					if (response != null) {
	                    InputStream instream = entity.getContent();
	                    try {
	                    	StringWriter writer = new StringWriter();
	                    	IOUtils.copy(new InputStreamReader(instream), writer, 1024);
	                    	String body = writer.toString();
                                
                                log.info(body);
	                    	body ="SENT";
	                    	
	                    	if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	                    		outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("SENT");
	                    		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
	                    	}
	                    	else {
	                    		System.out.println(body);
	                    		outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
	                    		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
	                    	}	                    	
	                    } catch (Exception ex) {
	                    	ex.printStackTrace();
	                    	outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
                    		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
	                    } finally {
	                        instream.close();
	                    }
	                }
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					if(response!=null)
						response.close();
					httpclient.close();
				}
			}
			else {
				outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
        		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
			}
			outboundMsg.getOutboundUSSDMessageRequest().setDeliveryStatus("NOT SENT");
    		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	/**
	 * Default landing page for all USSD Requests, the page displayed will depend on the type of the request
	 * If a previous mtinit request, the request will be fetched and pushed to the phone. 
	 * If a moinit, or a mocont, will be forwarded to the apps notify URL
	 * In the latter scenario, the app is identified by the keyword, which what the user will dial after the short code.
	 * E.g.: #<short code>*<keyword># -> #1721*123#
	 * @return
	 */
	//TODO
	//SHOULD BE RENAMED TO route/{msisdn}
	@GET
	@POST
	@Path("v1/route")
	@Produces("application/json")
	public Response ussdRoute(@Context HttpServletRequest hsr,@Context HttpServletResponse servletResponse,@QueryParam("msisdn") String msisdn, @QueryParam("input") String  userinput, @FormParam("dateformat") String dateformat, @FormParam("sessionid") String sessionid ) {
		
            String freeflowVar=null;
            String message = null;
            
            try {
			
			msisdn = msisdn==null?"":msisdn;
			userinput = userinput==null?"":userinput;
			dateformat = dateformat==null?"":dateformat;
			
			OutboundMessage outboundMsg=null;
			log.info("msisdn: " + msisdn + ", input: " + userinput + ", dateformat: " + dateformat );
			
			String param = msisdn;
			UrlProvisionEntity req;
			if(msisdn!=null) {
				req = manager.getNotifyURLByLastRequest(msisdn);
				log.info("test Notify URL: " + req.getNotifyUrl() );		
				
				InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
				inboundUSSDMessageRequest = manager.getResponseBodyByLastRequest(msisdn);
				String jsonObj = null;
				
				jsonObj = "{" + "\"inboundUSSDMessageRequest\":" + "{"
                   + "\"address\":" + "\"tel:+tel:+"+inboundUSSDMessageRequest.getAddress() + "\","
                   + "\"responseRequest\":" + "{" + "\"notifyURL\":\"" + inboundUSSDMessageRequest.getResponseRequest().getNotifyURL() + "\"," + "\"callbackData\":\"" + 
            								inboundUSSDMessageRequest.getResponseRequest().getCallbackData() + "\"" + "}" + ","
            	   + "\"inboundUSSDMessage\":\"" + userinput + "\"," 
            	   + "\"clientCorrelator\":\"" + inboundUSSDMessageRequest.getClientCorrelator() + "\","
            	   + "\"shortCode\":\"" + inboundUSSDMessageRequest.getShortCode() + "\","
            	   + "\"ussdAction\":\"" + inboundUSSDMessageRequest.getUssdAction() + "\","               
            	   + "\"keyword\":\"" + inboundUSSDMessageRequest.getKeyword() + "\""
            	   + "}}";
				
				String path = "/home/diadmin/key/client-truststore.jks";
				System.setProperty("javax.net.ssl.trustStore", path);
				System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
				
				
				log.info("jsonObj : " + jsonObj);
				
				if (userinput.equalsIgnoreCase("1")||userinput.equalsIgnoreCase("2")) {
					outboundMsg =   postToNotifyURL(jsonObj, settings.getProperty("ni_ussd_notify"));
				}else {
					req = manager.getNotifyURLByLastRequest(msisdn);
					outboundMsg =   postToNotifyURL(jsonObj, req.getNotifyUrl());
				}
				
				
				log.info("outboundMsg Contents 01 : " + outboundMsg.getOutboundUSSDMessageRequest().getAddress());
				log.info("outboundMsg Contents 02 : " + outboundMsg.getOutboundUSSDMessageRequest().getClientCorrelator());
				log.info("outboundMsg Contents 03 : " + outboundMsg.getOutboundUSSDMessageRequest().getDeliveryStatus());
				log.info("outboundMsg Contents 04 : " + outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
				log.info("outboundMsg Contents 05 : " + outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage().getBytes());
				log.info("outboundMsg Contents 06 : " + outboundMsg.getOutboundUSSDMessageRequest().getSessionID());
				log.info("outboundMsg Contents 07 : " + outboundMsg.getOutboundUSSDMessageRequest().getShortCode());
				log.info("outboundMsg Contents 08 : " + outboundMsg.getOutboundUSSDMessageRequest().getId());
				log.info("outboundMsg Contents 09 : " + outboundMsg.getOutboundUSSDMessageRequest().getResponseRequest().getCallbackData());
				log.info("outboundMsg Contents 10 : " + outboundMsg.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL());
				log.info("outboundMsg Contents 11 : " + outboundMsg.getOutboundUSSDMessageRequest().getResponseRequest().getId());
				log.info("outboundMsg Contents 12 : " + outboundMsg.getOutboundUSSDMessageRequest().getUssdAction());
				
				
                        try {
                            
                            OutboundRequest outBoundRequest = new OutboundRequest();
                            outBoundRequest.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());	
                            
                            
                            if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtcont) {
                                freeflowVar="FC";
	                	log.info("USSDAPI Logs --> getOutboundUSSDMessage() : " + outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
	                			
                            }
                            else if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
	                    	freeflowVar="FB";
	                    }
	                   
                            else{
                                log.info("no operation");
                            }	

                            
                            message = outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage();
                            log.info(message);
                           
                            } catch (Exception e) {
                                    e.printStackTrace();
                                    
                            }


			
			
			}} catch (Exception e) {
                            e.printStackTrace();
                        
                        }
            
            
            
			return Response.ok().
				header("Freeflow", freeflowVar).
				header("charge:", "No").
				header("Amount", "0").
				entity(message).build();
		
	}
	
	@POST
	@Path("test/ussd}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testNotifyUrl(String jsonBody) {	
		try {
			String jsonObj = "{\"code\": \"200\",\"message\": \"" +jsonBody+ "\"}";
			log.info(jsonObj);
			return Response.ok().entity(jsonObj).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	//TODO
	//SHOULD BE RENAMED TO default/{msisdn}
//	@GET
//	@Path("mo-init/{msisdn}")
//	@Produces("application/xml")
/**	public Response ussdDefault(@PathParam("msisdn") String msisdn) {
		try {
			String callbackURI = settings.getProperty("ussd_callback");
			Object[] messageparams = {msisdn, String.valueOf(System.currentTimeMillis())};
			callbackURI = MessageFormat.format(callbackURI,messageparams);
			
			String vxml = createVXMLResponsePage("Ideabiz", callbackURI);
			return Response.ok().entity(vxml).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
*/	
	@GET
	@Path("mt-cont/{msisdn}")
	@Produces("application/xml")
	public Response mtContUSSD(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @QueryParam("resp") String resp, @QueryParam("sessionid") String reqID) {
		////=================http://125.18.107.19/airtel/route?MSISDN=$MSISDN&input=$Text&dateformat=$DATEFORMAT&sessionid=$Sessionid	
		try {
			OutboundRequest request = manager.getRequest(Long.valueOf(reqID));
			
			InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
			inboundUSSDMessageRequest.setAddress("tel:+"+msisdn);
			inboundUSSDMessageRequest.setSessionID(request.getOutboundUSSDMessageRequest().getSessionID());
			inboundUSSDMessageRequest.setClientCorrelator(request.getOutboundUSSDMessageRequest().getClientCorrelator());
			inboundUSSDMessageRequest.setInboundUSSDMessage(resp);
			inboundUSSDMessageRequest.setKeyword(request.getOutboundUSSDMessageRequest().getKeyword());
			inboundUSSDMessageRequest.setShortCode(request.getOutboundUSSDMessageRequest().getShortCode());
			inboundUSSDMessageRequest.setResponseRequest(request.getOutboundUSSDMessageRequest().getResponseRequest());
			inboundUSSDMessageRequest.setUssdAction(USSDAction.mtcont);
			
			InboundMessage inboundMessage = new InboundMessage();
			inboundMessage.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);
			
			HttpPost post = new HttpPost(request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL());
			//post.setConfig(requestConfig);
			
			String jsonObj = gson.toJson(inboundMessage);	
			
			CloseableHttpClient httpclient = null;
			CloseableHttpResponse response = null;
			try {
				httpclient = HttpClients.createDefault(); 
				
				/*HttpClients.custom()
		                .setConnectionManager(connectionManager)
		                .build();*/
				
				StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
				strEntity.setContentType("application/json");
				post.setEntity(strEntity);
				
				response = httpclient.execute(post);
				HttpEntity entity = response.getEntity();
				
				String xml = createVXMLFinalPage("Thank You");

				if (entity != null) {
                    InputStream instream = entity.getContent();
                    try {
                    	StringWriter writer = new StringWriter();
                    	IOUtils.copy(new InputStreamReader(instream), writer, 1024);
                    	String body = writer.toString();
                    	OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class); 
                    	
                    	if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtcont) {
                			Application application = null;
                			
                			if(outboundMsg.getOutboundUSSDMessageRequest().getKeyword()==null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length()==0) {
                				application = manager.getUniqueApplication(
                						outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
                			}
                			else {
                				application = manager.getApplication(
                						outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), 
                						outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
                			}
                			
                			if(application == null) {
                				throw new Exception("Application does not exist");
                			}
                			
                			OutboundRequest outBoundRequest = new OutboundRequest();
                			outBoundRequest.setApplication(application);
                			outBoundRequest.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
                			
                			Long id = manager.saveRequest(outBoundRequest);
                    		
                    		if(id!=null) {
                    			String callbackURI = settings.getProperty("ussd_cont");
                				Object[] messageparams = {
                						request.getOutboundUSSDMessageRequest().getAddress(), 
                						String.valueOf(request.getId())};
                				callbackURI = MessageFormat.format(callbackURI,messageparams);
                				
                				
                    			xml = createVXMLResponsePage(outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(),callbackURI);
                    		}                    		
                    	}
                    	if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                    		xml = createVXMLFinalPage(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
                    	}
                    }
                    catch (Exception e) {
                    	e.printStackTrace();
                    }
				}

				return Response.ok().entity(xml).build();
			}
			catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
			finally {
				response.close();
				httpclient.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	/**
	 * 
	 * @param prompt
	 * @param reqID
	 * @param callbackURI
	 * @return
	 */
	private String createVXMLResponsePage(String prompt, String callbackURI) throws JAXBException, PropertyException {
		return createVXMLPage(prompt, callbackURI, "vResponse", "oc_message", "#responseMsg");
	}
	
	/**
	 * 
	 * @param prompt
	 * @param reqID
	 * @param callbackURI
	 * @return
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private String createVXMLFinalPage(String prompt) throws JAXBException, PropertyException {
		/*VXMLFactory factory = new VXMLFactory();
		Vxml vxml = factory.createVxml();
		
		//<field name="oc_final">
		//	<prompt>END</prompt>
		//</field>
		Field field = factory.createVxmlFormField();
		field.setName("oc_final");
		field.setPrompt(prompt);
		
		//<filled>
		//	<assign name="" expr="oc_final"/>
		//	<goto next=""/>
		//</filled>
		Assign assign = factory.createVxmlFormFilledAssign();
		assign.setName("");
		assign.setExpr("oc_final");
		Goto gotoElem = factory.createVxmlFormFilledGoto();
		gotoElem.setNext("");			
		Filled filled = factory.createVxmlFormFilled();
		filled.setAssign(assign);
		filled.setGoto(gotoElem);
		
		//<form id="final" name="Final Form">
		//	<field/>
		//	<filled/>
		//</form>
		Form form1 = factory.createVxmlForm();
		form1.setId("final");
		form1.setName("Final Form");
		form1.setField(field);
		form1.setFilled(filled);

		List<Form> formList = vxml.getForm();
		formList.add(0, form1);

		JAXBContext context = JAXBContext.newInstance(Vxml.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

		StringWriter writer = new StringWriter();
		m.marshal(vxml, writer);

		String xml = writer.toString();*/
		/**
		 * This is a dirty hack
		 * FIX this properly
		 */
		String xml = 
				"<vxml>"+
				"	<form id=\"Menu Item1\" name=\"Menu Item1 ID\">"+
				"		<field name=\"var1\">"+
				"			<prompt><![CDATA["+prompt+"]]></prompt>"+
				"		</field>"+
				"		<property name=\"oc_bIsFinal\" value=\"1\"/>"+
				"	</form>"+
				"</vxml>";
		
		return xml;
	}
	
	/**
	 * @param request
	 * @param prompt 
	 * @param reqID 
	 * @param callbackURI 
	 * @return
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private String createVXMLPage(String prompt, String callbackURI, String filledName, String filledExpr, String gotoURL) throws JAXBException, PropertyException {
		VXMLFactory factory = new VXMLFactory();
		Vxml vxml = factory.createVxml();
		
		//<field name="oc_message">
		//	<prompt>Hello 94777335365!\n1. Continue\n2. Exit</prompt>
		//</field>
		Field field = factory.createVxmlFormField();
		field.setName("oc_message");
		field.setPrompt(prompt);
		
		//<filled>
		//	<assign name="vResponse" expr="oc_message"/>
		//	<goto next="#responseMsg"/>
		//</filled>
		Assign assign = factory.createVxmlFormFilledAssign();
		assign.setName(filledName);// "vResponse");
		assign.setExpr(filledExpr);// "oc_message");
		Goto gotoElem = factory.createVxmlFormFilledGoto();
		gotoElem.setNext(gotoURL);// "#responseMsg");			
		Filled filled = factory.createVxmlFormFilled();
		filled.setAssign(assign);
		filled.setGoto(gotoElem);
		
		//<form id="MenuID Here" name="Menu Name Here">
		//	<field/>
		//	<filled/>
		//</form>
		Form form1 = factory.createVxmlForm();
		form1.setId("MainMenu");
		form1.setName("Main Menu");
		form1.setField(field);
		form1.setFilled(filled);

		//<block name="oc_ActionUrl">
		//	<goto next="http://172.22.163.88:8080/ussd/route/{0}?resp=%vResponse%&reqID=%vReqID%" />
		//</block>
		com.idea.ussd.dto.Vxml.Form.Block.Goto blockGoto = factory.createVxmlFormBlockGoto();
		blockGoto.setNext(callbackURI);
		Block block = factory.createVxmlFormBlock();
		block.setName("oc_ActionUrl");
		block.setGoto(blockGoto);
		
		//<form id="responseMsg" name="responseMsg">
		//<block/>
		//</form>
		Form form2 = factory.createVxmlForm();
		form2.setId("responseMsg");
		form2.setName("responseMsg");
		form2.setBlock(block);
		
		List<Form> formList = vxml.getForm();
		formList.add(0, form1);
		formList.add(1, form2);

		JAXBContext context = JAXBContext.newInstance(Vxml.class);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

		StringWriter writer = new StringWriter();
		m.marshal(vxml, writer);

		String xml = writer.toString();
		return xml;
	}
	
	@SuppressWarnings("deprecation")
	public CloseableHttpClient getNewHttpClient() {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new AirTelSSLSocket(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                return new DefaultHttpClient(ccm, params);
            } catch (Exception e) {
                return new DefaultHttpClient();
            }
        }
	
	 private String createNotifyURLRequest(String reqID, String msisdn, String resp) {
	        OutboundRequest request = manager.getRequest(Long.valueOf(reqID));
	        InboundUSSDMessageRequest inboundUSSDMessageRequest = new InboundUSSDMessageRequest();
	        inboundUSSDMessageRequest.setAddress("tel:+" + msisdn);
	        inboundUSSDMessageRequest.setSessionID(request.getOutboundUSSDMessageRequest().getSessionID());
	        inboundUSSDMessageRequest.setClientCorrelator(request.getOutboundUSSDMessageRequest().getClientCorrelator());
	        inboundUSSDMessageRequest.setInboundUSSDMessage(resp);
	        inboundUSSDMessageRequest.setKeyword(request.getOutboundUSSDMessageRequest().getKeyword());
	        inboundUSSDMessageRequest.setShortCode(request.getOutboundUSSDMessageRequest().getShortCode());
	        inboundUSSDMessageRequest.setResponseRequest(request.getOutboundUSSDMessageRequest().getResponseRequest());
	        inboundUSSDMessageRequest.setUssdAction(USSDAction.mtcont);
	        InboundMessage inboundMessage = new InboundMessage();
	        inboundMessage.setInboundUSSDMessageRequest(inboundUSSDMessageRequest);
	        String jsonObj = gson.toJson(inboundMessage);
	        //log("Post to notifyURL jsonbody = " + jsonObj);
	        return jsonObj;
	    }
	
	//----------------------------------------PRIYANKA_06608----------------------------------------
	
	@POST
	@Path("routeNotifyUrl/{routeNotifyUrl}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response provisionNotifyUrl(@Context HttpServletRequest servletRequest,@PathParam("routeNotifyUrl")String routeNotifyUrl) {	
		try {
			UrlProvisionEntity urlProvisionEntity=gson.fromJson(routeNotifyUrl, UrlProvisionEntity.class);
			String provisionNotifyUrl=manager.provisionNotifyUrl(urlProvisionEntity.getApplication().getId());
			String jsonObj = gson.toJson(provisionNotifyUrl);
			return Response.ok().entity(jsonObj).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	private OutboundMessage postToNotifyURL(String jsonObj, String notifyURL) {
        try {
            HttpPost post = new HttpPost(notifyURL);

            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;

            httpclient = HttpClients.createDefault();/*HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build();*/
            
            log.info("jsonObj"  + jsonObj);

            
            post.addHeader("Authorization", "Bearer " + settings.getProperty("hubKey"));
            StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
            strEntity.setContentType("application/json");

            post.setEntity(strEntity);

            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();

            InputStream instream = entity.getContent();

            StringWriter writer = new StringWriter();
            IOUtils.copy(new InputStreamReader(instream), writer, 1024);
            String body = writer.toString();
            log.info("Return from notifyURL = " + body);

            OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class);

            return outboundMsg;

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
	
	
	
}
