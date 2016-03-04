package com.dialog.mife.ussd.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.dialog.mife.ussd.ctrl.RequestManager;
import com.dialog.mife.ussd.dto.Application;
import com.dialog.mife.ussd.dto.InboundMessage;
import com.dialog.mife.ussd.dto.InboundUSSDMessageRequest;
import com.dialog.mife.ussd.dto.OutboundMessage;
import com.dialog.mife.ussd.dto.OutboundRequest;
import com.dialog.mife.ussd.dto.USSDAction;
import com.dialog.mife.ussd.dto.NIMsisdn;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;


/**
 * @author Charith_02380
 *
 */
@Path("/")
public class UssdAPI {
        private static final String userDirectory =  System.getProperty("user.dir");
        private static final File file = new File("/home/dialog"+ "/mediator.log");

        private static Log log = LogFactory.getLog(UssdAPI.class);
        final static Logger logger = Logger.getLogger(UssdAPI.class);
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
	     log("outboundRequest");	
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
			request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
			
			Long id = manager.saveRequest(request);
                        
                        //Temporary save NI msisdns.
                        log("object exists check");
                        
                        NIMsisdn msisdnObj = manager.getMSISDN(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""));
                        
                        log("object is retrived");
                        if (msisdnObj != null){
                            log("object exists");
                            manager.deleteMSISDN(msisdnObj);
                        }
                        
                        NIMsisdn msisdnObjSave = new NIMsisdn();
                        msisdnObjSave.setMSISDN(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""));
                        
                        log("saving MSISDN");
                       
			manager.saveMSISDN(msisdnObjSave);
                        
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
				
				String niURL = settings.getProperty("ni_ussd_url");
				Object[] messageparams = {
						outboundMsg.getOutboundUSSDMessageRequest().getAddress().replaceAll("tel:+", "").replaceAll("\\+", ""),
						outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replaceAll("tel:", "").replaceAll("\\+", ""),
						inputKey};
				niURL = MessageFormat.format(niURL,messageparams);
				HttpGet get = new HttpGet(niURL);
				
				CloseableHttpClient httpclient = null;
				CloseableHttpResponse response = null;
				try {
					httpclient = HttpClients.createDefault();/*HttpClients.custom()
			                .setConnectionManager(connectionManager)
			                .build();*/
					
					response = httpclient.execute(get);
					HttpEntity entity = response.getEntity();
					
					if (entity != null) {
	                    InputStream instream = entity.getContent();
	                    try {
	                    	StringWriter writer = new StringWriter();
	                    	IOUtils.copy(new InputStreamReader(instream), writer, 1024);
	                    	String body = writer.toString();
	                    	
	                    	if(body.equals("SENT")) {
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
	@Path("route/{msisdn}")
	@Produces("application/xml")
	public Response ussdRoute(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @QueryParam("resp") String resp, @QueryParam("reqID") String reqID) {
            log("ussdRoute");
            //log("Response 1 = " + resp);
            try {
			String pattern = settings.getProperty("ussd_ni_match");
                        
			String callbackURI = settings.getProperty("ussd_cont");
                        
                        
			if(resp.matches(pattern)) {//Is a call from platform for a previous ni ussd
                                
                              //
                                log("NI USSD MSISDN = " + msisdn);
                                NIMsisdn msisdnObj = new NIMsisdn();
                                msisdnObj.setMSISDN(msisdn);
                                manager.deleteMSISDN(msisdnObj);
                                
				String params[] = resp.split(":");
				OutboundRequest request = manager.getRequest(Long.parseLong(params[2]));
				
				
				Object[] messageparams = {
						request.getOutboundUSSDMessageRequest().getAddress(), 
						String.valueOf(request.getId())};
				callbackURI = MessageFormat.format(callbackURI,messageparams);
				
				request.getOutboundUSSDMessageRequest().setSessionID(hsr.getSession().getId());
				manager.updateRequest(request);
				
				String xml = createVXMLResponsePage(
						request.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(),
						callbackURI);
				System.out.println("ResponseNormal=" + resp);
				return Response.ok().entity(xml).build();
			}
			else {
                            //User entered value comes here
                            //It should be redirected to user's notify URL
                            //Then user's display message should be taken here.
                            //Then should return xml to USSD gateway with callback URL as mt-cont URL. 
                            //Also Check Whether it is mt-cont,mt-fin
                            log("User Response = " + resp);
                            log("Request ID = " + reqID);
                            String xml = null;
                            OutboundRequest request = manager.getRequest(Long.valueOf(reqID));
                            
                            String jsonObj = createNotifyURLRequest(reqID,msisdn,resp);
                            
                            
                            
                            OutboundMessage outboundmessage = postToNotifyURL(jsonObj,request.getOutboundUSSDMessageRequest().getResponseRequest().getNotifyURL());
                            
                            if(outboundmessage.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                    		xml = createVXMLFinalPage(outboundmessage.getOutboundUSSDMessageRequest().getOutboundUSSDMessage());
                    	    }
                            
                            else{
                                Object[] messageparams = {
                                                    request.getOutboundUSSDMessageRequest().getAddress(), 
                                                    String.valueOf(request.getId())};

                                callbackURI = MessageFormat.format(callbackURI,messageparams);

                                xml = createVXMLResponsePage(
                                                    outboundmessage.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(),
                                                    callbackURI);

                                System.out.println("Response=" + resp);
                                
                            }
                            return Response.ok().entity(xml).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	//TODO
	//SHOULD BE RENAMED TO default/{msisdn}
	@GET
	@Path("mo-init/{msisdn}")
	@Produces("application/xml")
	public Response ussdDefault(@PathParam("msisdn") String msisdn) {
            log("ussdDefault");
		try {
			String callbackURI = settings.getProperty("ussd_callback");
			//Object[] messageparams = {msisdn, String.valueOf(System.currentTimeMillis())};
			//callbackURI = MessageFormat.format(callbackURI,messageparams);
			
                        // Forward request to Application Notify URL
                        
                        NIMsisdn msisdnObj = manager.getMSISDN(msisdn);
                        String vxml = null;
                        
                        if(msisdnObj != null){
                            Object[] messageparams = {msisdn, String.valueOf(System.currentTimeMillis())};
                            callbackURI = MessageFormat.format(callbackURI,messageparams);
			    log("MSISDN is not null");
                            manager.deleteMSISDN(msisdnObj);
                            vxml = createVXMLResponsePage("Ideabiz", callbackURI);
                        }
                        
                        else{
                            log("MSISDN is null - Mobile originated");
                            
                            String notifyUrl = "http://10.62.96.187:9764/mavenproject1-1.0-SNAPSHOT/webresources/endpoint/ussd/init" +"?msisdn="+ msisdn;
                            
                            log("notifyURL = " + notifyUrl);
                                    
                            OutboundMessage outboundMsg = notifyApp(notifyUrl,msisdn);

                            OutboundRequest request = new OutboundRequest();

                            Application application = null;
                            
                            //Saving the Request
                            if(outboundMsg.getOutboundUSSDMessageRequest().getKeyword()==null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length()==0) {
                                    application = manager.getUniqueApplication(
                                                    outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
                            }
                            else {
                                    application = manager.getApplication(
                                                    outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""), 
                                                    outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
                            }

                            //Save outbound Request
                            request.setApplication(application);
                            request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());

                            Long id = manager.saveRequest(request);


                            //Call USSD Gateway
                            String reqID = id.toString();

                            Object[] messageparams = {msisdn, reqID};
                            callbackURI = MessageFormat.format(callbackURI,messageparams);

                            log("Callback URL: " + callbackURI);
                            String message = "";

                            message = request.getOutboundUSSDMessageRequest().getOutboundUSSDMessage();
                            vxml = createVXMLResponsePage(message , callbackURI);
                        }
			return Response.ok().entity(vxml).build();
                        
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("mt-cont/{msisdn}")
	@Produces("application/xml")
	public Response mtContUSSD(@Context HttpServletRequest hsr, @PathParam("msisdn") String msisdn, @QueryParam("resp") String resp, @QueryParam("reqID") String reqID) {
	log("mtContUSSD");
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
				httpclient = HttpClients.createDefault();/*HttpClients.custom()
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
	
        
        private String createNotifyURLRequest(String reqID,String msisdn,String resp){
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
            
            String jsonObj = gson.toJson(inboundMessage);	
            
            log("Post to notifyURL jsonbody = " + jsonObj);
            
            return jsonObj;
        }
        
     private OutboundMessage postToNotifyURL(String jsonObj,String notifyURL){
        try {            
            HttpPost post = new HttpPost(notifyURL);
            
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;
			
            httpclient = HttpClients.createDefault();/*HttpClients.custom()
            .setConnectionManager(connectionManager)
            .build();*/

            StringEntity strEntity = new StringEntity(jsonObj.toString(), "UTF-8");
            strEntity.setContentType("application/json");
            post.setEntity(strEntity);

            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            
            InputStream instream = entity.getContent();
            
            StringWriter writer = new StringWriter();
            IOUtils.copy(new InputStreamReader(instream), writer, 1024);
            String body = writer.toString();
            log("Return from notifyURL = " + body);
            
            OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class); 
            
            return outboundMsg;
            
         }
         catch (IOException ex) {
            java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
             return null;
            } 
            
        }   
        
       
      private OutboundMessage notifyApp(String notifyURL,String msisdn) throws IOException{
          
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse response = null;

            httpclient = HttpClients.createDefault();
            String body  = null;
            //notify URL should be retrived from APP
           // String appNotifyURL = "" + msisdn;

            HttpGet get = new HttpGet(notifyURL);
        
            try {

                response = httpclient.execute(get);
             
                HttpEntity entity = response.getEntity();

                

                if (entity != null) {
                    InputStream instream = entity.getContent();

                    StringWriter writer = new StringWriter();

                    IOUtils.copy(new InputStreamReader(instream), writer, 1024);
                    body = writer.toString();
                }
            }
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
            } 
            finally{
              try {
                  response.close();
                  httpclient.close();
              } catch (IOException ex) {
                  java.util.logging.Logger.getLogger(UssdAPI.class.getName()).log(Level.SEVERE, null, ex);
                  return null;
              }
                
            }
            
          log("Json Body from APP = " + body);
          OutboundMessage outboundMsg = gson.fromJson(body, OutboundMessage.class); 
          
            
          return outboundMsg;
      }
     
        private static void log(String text) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                out.println(text);
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                if (out != null) {
                    out.close();
                }
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
	

	//----------------------------------------------------------------PRIYANKA_06608----------------------------------------------------------------
		@SuppressWarnings("unused")
		@POST
		@Path("v1/inbound/subscriptions")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response subscriptionsRequest(@Context HttpServletRequest hsr, String jsonBody) {
			try {
				UssdSubscription subscription = gson.fromJson(jsonBody, UssdSubscription.class);	
				Long generatedId= null;
				if	(
						(subscription.getSubscription().getClientCorrelator()!=null || subscription.getSubscription().getClientCorrelator().trim().length()!=0)
						&& 
						(subscription.getSubscription().getDestinationAddress()!=null || subscription.getSubscription().getDestinationAddress().trim().length()!=0) 
						&&  
						(subscription.getSubscription().getCallbackReference().getNotifyURL()!=null || subscription.getSubscription().getDestinationAddress().trim().length()!=0)
						){
							Application application = null;
							UrlProvisionEntity entity=new UrlProvisionEntity();
							application = manager.getNotifyUrlByApplication(subscription.getSubscription().getDestinationAddress());
							entity.setApplication(application);
							entity.setKeyword(subscription.getSubscription().getClientCorrelator());
							entity.setNotifyUrl(subscription.getSubscription().getCallbackReference().getNotifyURL());
							generatedId=manager.provisionUrl(entity);
							return Response.created(URI.create(hsr.getPathInfo())).entity(jsonBody).build();
				}
				if(generatedId == null) {
					throw new Exception("Application does not exist");
				}			
			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
			return null;
		}	
	
	
}
