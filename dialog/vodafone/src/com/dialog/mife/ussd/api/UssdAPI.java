package com.dialog.mife.ussd.api;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.cxf.helpers.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.dialog.mife.ussd.ctrl.RequestManager;
import com.dialog.mife.ussd.ctrl.UrlProvisionEntity;
import com.dialog.mife.ussd.dto.Application;
import com.dialog.mife.ussd.dto.InboundMessage;
import com.dialog.mife.ussd.dto.InboundUSSDMessageRequest;
import com.dialog.mife.ussd.dto.OutboundMessage;
import com.dialog.mife.ussd.dto.OutboundRequest;
import com.dialog.mife.ussd.dto.USSDAction;
import com.dialog.mife.ussd.dto.VXMLFactory;
import com.dialog.mife.ussd.dto.Vxml;
import com.dialog.mife.ussd.dto.Vxml.Form;
import com.dialog.mife.ussd.dto.Vxml.Form.Block;
import com.dialog.mife.ussd.dto.Vxml.Form.Field;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled.Assign;
import com.dialog.mife.ussd.dto.Vxml.Form.Filled.Goto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.log.Log;

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
					
			log.info("test outboundRequest 1 ");
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
				
				log.info("test outboundRequest 2 ");
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
				
				log.info("test outboundRequest 3 " + inputKey );
				String niURL = settings.getProperty("ni_ussd_url");
				
				String outboundUSSDMessage = URLEncoder.encode(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "UTF-8");
				
				Object[] messageparams = {
						outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),
						outboundUSSDMessage,"2"};
				niURL = MessageFormat.format(niURL,messageparams);
				
				
				log.info("test outboundRequest 3 " + niURL );
				
				//String urlencode = URLEncoder.encode(niURL);
				//String urlencode = niURL;
				
				log.info("test outboundRequest 3 " + niURL );
				HttpGet get = new HttpGet(niURL);
				
				log.info("test outboundRequest 3 " + niURL );
				CloseableHttpClient httpclient = null;
				CloseableHttpResponse response = null;
				try {
					httpclient = HttpClients.createDefault();/*HttpClients.custom()
			                .setConnectionManager(connectionManager)
			                .build();*/
					
					response = httpclient.execute(get);
					HttpEntity entity = response.getEntity();
										
					if (response != null) {
	                    InputStream instream = entity.getContent();
	                    try {
	                    	StringWriter writer = new StringWriter();
	                    	IOUtils.copy(new InputStreamReader(instream), writer, 1024);
	                    	String body = writer.toString();
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
				/*JAXBContext jc = JAXBContext.newInstance(OutboundMessage.class);
				Marshaller m = jc.createMarshaller();
				m.m*/
				
        		return Response.created(URI.create(hsr.getPathInfo())).entity(outboundMsg).build();
				//TODO
				//Handle error
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
	@Path("route")
	@Produces("application/json")
	public Response ussdRoute(@Context HttpServletRequest hsr,@Context HttpServletResponse servletResponse,@QueryParam("msisdn") String msisdn ,@QueryParam("userinput") String  userinput , @QueryParam("ID") String ID ) {
		
		UrlProvisionEntity req;
		OutboundMessage outboundMsg=null;
		String freeflowVar=null;
        String message = null;
		
		try {
			
			if ("POST".equalsIgnoreCase(hsr.getMethod())) {
				InputStream is = hsr.getInputStream(); 
		           
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            String output;
	            String retStr = "";
	            while ((output = br.readLine()) != null) {
	                retStr += output;
	            }
	            br.close();
				
				log.info("test getInputStream 3 " + retStr );
				log.info("test getQueryString 3 " + hsr.getQueryString() );
				
	//====================================================================================================================

				Map<String, String> map = new HashMap<String, String>();
				String url = retStr;
				String[] params = url.split("&");
				
					String name = null;
					String value = null;
					for (String param : params) {
						name = param.split("=")[0];
						value = param.split("=")[1];
						map.put(name, value);
					}
					
					msisdn=map.get("msisdn");
					userinput=map.get("userinput");
					
					log.info("post msisdn : " + msisdn);
					log.info("post userinput : " + userinput);
				
				
			}
	//====================================================================================================================			
				
					log.info("get or post msisdn: " +msisdn);
					log.info("get or post userinput:" + userinput);
						
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
						
						
						
						String path = "/home/ec2-user/client-truststore.jks";

						System.setProperty("javax.net.ssl.trustStore", path);

						System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
						
							//postToNotifyURL(jsonObj, "http://52.2.140.82/vodafone/test/ussd");
						
						if (userinput.equalsIgnoreCase("1")||userinput.equalsIgnoreCase("2")) {
							outboundMsg = postToNotifyURL(jsonObj, settings.getProperty("ni_ussd_notify"));
						}else {
							
							req = manager.getNotifyURLByLastRequest(msisdn);
							log.info("test Notify URL: " + req.getNotifyUrl() );
							outboundMsg =  postToNotifyURL(jsonObj, req.getNotifyUrl());
						}
						
						try {
							
							if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtcont) {
                        		freeflowVar="FC";

                        		OutboundRequest outBoundRequest = new OutboundRequest();
                        		outBoundRequest.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
                        		
                        		
                        		  String niURL = settings.getProperty("ni_ussd_url");
				
                        		  String outboundUSSDMessage = URLEncoder.encode(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), "UTF-8");
				
                        		  Object[] messageparams = {
                        				  outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),
                        				  outboundUSSDMessage,"2"};
                        		  niURL = MessageFormat.format(niURL,messageparams);
                        		 
                        		  
                        			log.info("test outboundRequest 3 " + niURL );
                    				HttpGet get = new HttpGet(niURL);
                    				
                    				log.info("test outboundRequest 3 " + niURL );
                    				CloseableHttpClient httpclient = null;
                    				CloseableHttpResponse response = null;
                    				
                    				try {
                    					httpclient = HttpClients.createDefault();/*HttpClients.custom()
            			                .setConnectionManager(connectionManager)
            			                .build();*/
            					
                    					response = httpclient.execute(get);
                    					HttpEntity entity = response.getEntity();
									} catch (Exception e) {
										e.printStackTrace();
									}
                        		  
                        		  

                        		log.info("USSDAPI Logs --> getOutboundUSSDMessage() : " + outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());

                        		PrintWriter out = servletResponse.getWriter();

                        		out.println(outBoundRequest.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
                        		out.flush();


                        		}
							
                        		if(outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                        		//xml = createVXMLFinalPage(outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
	                        		freeflowVar="FB";
	
	                        		OutboundRequest outBoundRequest = new OutboundRequest();
	                        		outBoundRequest.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
	
	                        		 String niURL = settings.getProperty("session_terminate_url");               				
					
	                       		   Object[] messageparams = {
	                       				  outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),"17"};
	                       		   niURL = MessageFormat.format(niURL,messageparams);
                       		 
                       		  
                       			log.info("test outboundRequest 3 " + niURL );
                   				HttpGet get = new HttpGet(niURL);
                   				
                   				log.info("test outboundRequest 3 " + niURL );
                   				CloseableHttpClient httpclient = null;
                   				CloseableHttpResponse response = null;
                   					

                				try {
                					httpclient = HttpClients.createDefault();/*HttpClients.custom()
        			                .setConnectionManager(connectionManager)
        			                .build();*/
        					
                					response = httpclient.execute(get);
                					HttpEntity entity = response.getEntity();
								} catch (Exception e) {
									e.printStackTrace();
								}
                        	}
                        		
						} catch (Exception e) {
							e.printStackTrace();
						}
						
				

					//return Response.ok().entity("{\"status\":\"ok\", \"message\": \"Success Response\",\"code\":\"200\"}").build();

			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
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
		com.dialog.mife.ussd.dto.Vxml.Form.Block.Goto blockGoto = factory.createVxmlFormBlockGoto();
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
