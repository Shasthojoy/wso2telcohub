package com.dialog.psi.api;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.axiata.dialog.oneapi.validation.impl.ValidateSendSms;
import com.dialog.psi.api.responsebean.sms.SendSMSRequest;
import com.dialog.psi.util.AirTelSSLSocket;
import com.dialog.psi.util.FileUtil;
import com.dialog.psi.util.NumberGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class SMSHandler {

    private static final Logger log = Logger.getLogger(SMSHandler.class);
    private static final org.jboss.logging.Logger LOGGER = org.jboss.logging.Logger.getLogger(SMSHandler.class);
    boolean isvalidate = true;
    @Context
    private UriInfo context;

    //Sending SMS
    @POST
    @Path("outbound/{senderAddress}/requests")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response sendSMS(@PathParam("senderAddress") String senderAddress, String jsonBody) {

//        SystemLog.getInstance().getLogger("smscomp_event").info("send sms :" + jsonBody);
        boolean isRegistered = false;

        try {

            if (isvalidate) {
                new ValidateSendSms().validate(jsonBody);
            }
        Gson gson = new GsonBuilder().serializeNulls().create();

        SendSMSRequest sendReq = gson.fromJson(jsonBody, SendSMSRequest.class);

        // String callbackData = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
        //Retriving outbound text message
        String message = sendReq.getOutboundSMSMessageRequest().getOutboundTextMessage().getMessage();
        String notifyURL;
        String callbackData;
        String receiptRequest = "";

            log.info("################# message " + message);
//            LOGGER.info("################# message " + message);
//            SystemLog.getInstance().getLogger("smscomp_event").info("################# message " + message);

        if (sendReq.getOutboundSMSMessageRequest().getReceiptRequest() != null) {
            Gson gsonRes = new Gson();
            receiptRequest = gsonRes.toJson(sendReq.getOutboundSMSMessageRequest().getReceiptRequest());

            //Retriving NotifyURL
            notifyURL = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getNotifyURL();
            callbackData = sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();

            if (!notifyURL.isEmpty()) {
                isRegistered = true;
            }
        }
        //Retriving Sender Address
        String sender = sendReq.getOutboundSMSMessageRequest().getSenderAddress();
        //Retriving clientCorrelator
        String clientCorrelator = sendReq.getOutboundSMSMessageRequest().getClientCorrelator();
        //Retriving senderName
        String senderName = sendReq.getOutboundSMSMessageRequest().getSenderName();
        //Retriving destinationAddress
        List<String> address = sendReq.getOutboundSMSMessageRequest().getAddress();

            //String sender address reading from sender name
            if (senderName != null && senderName.length() > 0) {
//                SystemLog.getInstance().getLogger("smscomp_event").info("Sender Replace :" + senderName);
                sender = senderName;
            }

        Gson gsonResp = new Gson();
        String resAddress = gsonResp.toJson(sendReq.getOutboundSMSMessageRequest().getAddress());


        int msgNum = NumberGenerator.next();

        String msgId = " ";

        //Send sms
            //try {
            List<String> list = sendReq.getOutboundSMSMessageRequest().getAddress();

            String listPorts = FileUtil.getApplicationProperty("smpp_port_list");
            String port;
            List<String> portList = new ArrayList<String>();

            listPorts = listPorts.substring(1, listPorts.length() - 1);

            int iterator = 1;

            do {
                port = str_piece(listPorts, ';', iterator);
                iterator++;
                if (port.isEmpty() == false) {
                    portList.add(port);
                }

            } while (port.isEmpty() == false);

//            if (sender.startsWith("tel:")) {
//                sender = sender.split(":")[1];
//                sender.replace("+", "").trim();
//            }
            sender = sender.replace("tel:+", "").trim();
            sender = sender.replace("tel:", "").trim();
            sender = sender.replace("+", "").trim();
            
            for (String to : list) {
                to = to.replace("tel:+", "").trim();
                to = to.replace("tel:", "").trim();
                to = to.replace("+", "").trim();

                //Unique Message ID
                msgId = senderAddress + "" + to + "" + msgNum;

//                SystemLog.getInstance().getLogger("smscomp_event").info("Sending : ReqSender " + sender );

                //if (to.length() >= 9 && to.startsWith("9477")) {
                    if (portList.contains(sender)) {
//                    SystemLog.getInstance().getLogger("smscomp_event").info("Sending :" + sender + " > " + to);
                        log.info("Sending :" + sender + " > " + to);
                        LOGGER.info("Sending :" + sender + " > " + to);
                        sendMessage(sender, to, message, false, msgId, msgNum, isRegistered);
                    } else {
                        String portSend = FileUtil.getApplicationProperty("smpp_port_1");
                        portSend = portSend.substring(1, portSend.length() - 1);
//                    SystemLog.getInstance().getLogger("smscomp_event").info("Sending : Default " + portSend + " > " + to);
                        log.info("Sending : Default " + portSend + " > " + to);
                        LOGGER.info("Sending : Default " + portSend + " > " + to);
                        sendMessage(portSend, to, message, false, msgId, msgNum, isRegistered);
                    }
                //}

            }

            //} catch (Exception e) {
            //     //e.printStackTrace();
            //     SystemLog.getInstance().getLogger("smscomp_event").error("[SMSHandler ],sendSMS, " + e.getMessage());
            //    return Response.status(400).entity("{Internal Server Error}").build();
            // }

        String resourceURL = FileUtil.getApplicationProperty("sendSMSResourceURL");
        resourceURL = resourceURL.substring(1, resourceURL.length() - 1);


        String locationHeader = resourceURL + senderAddress + "/requests/" + "D" + msgId;

        //Delivary Info String
        String deliveryInfo = "[";
        for (String temp : address) {
            deliveryInfo = deliveryInfo + "{" + "\"address\":\"" + temp + "\"," + "\"deliveryStatus\":" + "\"MessageWaiting\"" + "},";
        }

        String subDelivary = deliveryInfo.substring(0, deliveryInfo.length() - 1);
        subDelivary = subDelivary + "]";

        String jsonPayload;
        if (sendReq.getOutboundSMSMessageRequest().getReceiptRequest() != null) {
            
                Integer smscRefNumber=msgNum;
                String deliveryStatus="MessageWaiting";
                
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   smscRefNumber :" + smscRefNumber);
                callbackData=sendReq.getOutboundSMSMessageRequest().getReceiptRequest().getcallbackData();
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   callbackData :" + callbackData);
                String senderMsisdn=senderAddress.replace("tel:+", "");
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   senderMsisdn :" + senderMsisdn);
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   deliveryStatus :" + deliveryStatus);
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   resAddress :" + resAddress);
//                SystemLog.getInstance().getLogger("smscomp_event").info("################SMSHANDLER################   clientCorrelator :" + clientCorrelator);
                String notifyUrl="";
                //notifyURL=new SmsService().getNotifyUrlBysenderMsisdn(senderMsisdn);
//                String response=new SmsService().insertSmppSubscriptionIfExists(smscRefNumber,deliveryStatus,resAddress,callbackData,senderMsisdn,clientCorrelator,notifyUrl);
        	
            jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                    + "\"address\":" + resAddress + ","
                    + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "/deliveryInfos" + "\"" + "}" + ","
                    + "\"senderAddress\":\"" + sender + "\","
                    + "\"outboundSMSTextMessage\":" + "{" + "\"message\":\"" +  message + "\"},"
                    + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                    + "\"receiptRequest\":" + receiptRequest + ","
                    + "\"senderName\":\"" + senderName + "\","
                    + "\"resourceURL\":\"" + locationHeader + "\""
                    + "}}";
        } else {
            jsonPayload = "{" + "\"outboundSMSMessageRequest\":" + "{"
                    + "\"address\":" + resAddress + ","
                    + "\"deliveryInfoList\":" + "{" + "\"deliveryInfo\":" + subDelivary + "," + "\"resourceURL\":\"" + locationHeader + "/deliveryInfos" + "\"" + "}" + ","
                    + "\"senderAddress\":\"" + sender + "\","
                    + "\"outboundSMSTextMessage\":" + "{" + "\"message\":\"" +  message + "\"},"
                    + "\"clientCorrelator\":\"" + clientCorrelator + "\","
                    + "\"senderName\":\"" + senderName + "\","
                    + "\"resourceURL\":\"" + locationHeader + "\""
                    + "}}";
        }
        return Response.status(201).header("Content-Type", "application/json").header("Location", locationHeader).entity(jsonPayload).build();
        } catch (AxiataException e) {
            //LOG.error("error charging user: " + senderAddress);
            RequestError requesterror = new RequestError();
            String retstr="";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(403).entity(retstr).build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity(retstr).build();
            }   
        } catch (Exception e) {
            RequestError requesterror = new RequestError();
            requesterror.setServiceException(new com.axiata.dialog.oneapi.validation.ServiceException("SVC0001", "Internal Server Error", ""));
            String jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity(jsonreturn).build();
        }
    }

    /**
     *
     * @param msg
     * @param length
     * @return
     */
    private ArrayList<String> splitMessage(String msg, int length) {
        ArrayList<String> msgs = new ArrayList<String>(0);
        int start = 0;
        while (msg.length() > 0) {
            if (msg.length() > length) {
                msgs.add(msg.substring(start, length));
                msg = msg.substring(length);
            } else {
                msgs.add(msg.substring(start));
                msg = "";
            }
        }
        return msgs;
    }

    private String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                count++;
                if (count == index) {
                    break;
                }
            } else {
                if (count == index - 1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }

    public void sendMessage(String port, String phoneNo, String msg, boolean highPriority, String msgId, int msgRefNum, boolean isRegistered) throws UnsupportedEncodingException {

        try {
            String url = FileUtil.getApplicationProperty("airtelurl");
//            url = URLEncoder.encode(url, "UTF-8");

            String sp = FileUtil.getApplicationProperty("sp");
            String cLink = msg;
            String tid = FileUtil.getApplicationProperty("tid");

            String username = FileUtil.getApplicationProperty("username");
            String password = FileUtil.getApplicationProperty("password");

            HttpClient client = getNewHttpClient();

            Object [] messageparams = {phoneNo,tid, URLEncoder.encode(sp, "UTF-8"),URLEncoder.encode(cLink, "UTF-8")};
            url = MessageFormat.format(url,messageparams);

//            SystemLog.getInstance().getLogger("########## send message ");
            log.info("########## send message ");

            HttpPost post = new HttpPost(url);

            String userInfo = username + ":" + password;
            BASE64Encoder encoder = new BASE64Encoder();
            byte[] userInfoBytes = userInfo.getBytes(); // I18n bug here!
            String authInfo = "Basic " + encoder.encode(userInfoBytes);
            post.addHeader("Authorization", authInfo);

            log.info("########## username + password " + username + ":" + password);
//            SystemLog.getInstance().getLogger("########## username + password " + username + ":" + password);
            // add header
//            post.setHeader("username", username);
//            post.setHeader("password", password);

//            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//            urlParameters.add(new BasicNameValuePair("msisdn", phoneNo));
//            urlParameters.add(new BasicNameValuePair("tid", tid));
//            urlParameters.add(new BasicNameValuePair("sp",  URLEncoder.encode(sp, "UTF-8")));
//            urlParameters.add(new BasicNameValuePair("cLink", URLEncoder.encode(cLink, "UTF-8")));


//            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = client.execute(post);
            log.info("Response Code : ####################### " + response.getStatusLine().getStatusCode());
//            SystemLog.getInstance().getLogger("Response Code : ####################### " + response.getStatusLine().getStatusCode());

//            BufferedReader rd = new BufferedReader(
//                    new InputStreamReader(response.getEntity().getContent()));
//
//            StringBuffer result = new StringBuffer();
//            String line = "";
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }


        } catch (IOException ex) {
            // TODO Auto-generated catch block
            log.info(" IO Exception ************* " );
//            SystemLog.getInstance().getLogger(" IO Exception ************* " );
            ex.printStackTrace();
        }

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

        
}
