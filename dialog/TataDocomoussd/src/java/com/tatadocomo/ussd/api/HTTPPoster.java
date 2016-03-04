package com.tatadocomo.ussd.api;

import com.tatadocomo.ussd.api.responsebean.ussd.OutboundMessage;
import com.tatadocomo.ussd.api.responsebean.ussd.OutboundRequest;
import com.tatadocomo.ussd.api.responsebean.ussd.USSDAction;
import com.tatadocomo.ussd.api.service.RequestManager;
import com.tatadocomo.ussd.util.FileUtil;
import com.dialog.util.SystemLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tatadocomo.ussd.api.responsebean.ussd.Application;
import com.tatadocomo.ussd.api.responsebean.ussd.ServiceProvider;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.log4j.Logger;

public class HTTPPoster implements Runnable {

    //private static final Logger LOG = Logger.getLogger(HTTPPoster.class.getName());
    private String targetURL;
    private String urlParameters;
    private boolean auth;
    private String senderaddr;
    private Gson gson;
    private RequestManager manager;

    public HTTPPoster(String targetURL, String urlParameters, boolean auth, String senderaddr) {
        this.targetURL = targetURL;
        this.urlParameters = urlParameters;
        this.auth = auth;
        this.senderaddr = senderaddr;
    }

    //public static String excutePost(String targetURL, String urlParameters, boolean auth) {
    public String excutePost() {
        URL url;
        HttpURLConnection connection = null;

        int statusCode = 0;

        try {
            // Create a trust manager that does not validate certificate chains
            //SystemLog.getInstance().getLogger("smscomp_event").error("Send Notify : "+ senderaddr);

            if (targetURL.startsWith("https")) {

                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
                };

                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            }

            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(Integer.parseInt(FileUtil.getApplicationProperty("connTimeout")));

            if (auth) {
                connection.setRequestProperty("Authorization", "Bearer " + FileUtil.getApplicationProperty("ktauth"));
            }
            //connection.setRequestProperty("Content-Length","" + Integer.toString(urlParameters.getBytes().length));
            //connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Guard against "bad hostname" errors during handshake.

            /*connection.setHostnameVerifier(new HostnameVerifier() {
             public boolean verify(String host, SSLSession sess) {
             if (host.equals("localhost")) return true;
             else return false;
             }
             }); */


            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            statusCode = connection.getResponseCode();
            if ((statusCode != 200) && (statusCode != 201)) {
                throw new RuntimeException("Failed : HTTP error code : " + statusCode);
            }

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();



            //==========================================================PRIYANKA_06608==============================================================================================================

            SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### response.toString() ######################  :" + response.toString());

            //String mobileConnectHost=connection.getHeaderField("Host");
            try {
            
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### response.toString() before cast  :" + response.toString()); 
                OutboundMessage outboundMsg = new OutboundMessage();
                
                gson = new GsonBuilder().serializeNulls().create();
                outboundMsg = gson.fromJson(response.toString(), OutboundMessage.class);
                manager = new RequestManager();
                
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 001 ######################  :" + outboundMsg);
            	boolean isRegistered = false;
            	SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 002 ######################  :" + isRegistered);
            	
            	SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 002.1 ######################  :" + outboundMsg.getOutboundUSSDMessageRequest());
                
                Application application = null;
                
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 003 ######################  :" + "outboundMsg.getOutboundUSSDMessageRequest().getShortCode()");
                if (outboundMsg.getOutboundUSSDMessageRequest().getKeyword() == null || outboundMsg.getOutboundUSSDMessageRequest().getKeyword().trim().length() == 0) {
                    application = manager.getUniqueApplication(
                            outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""));
                    SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 003 ######################  :" + "unique");
                } else {
                    application = manager.getApplication(
                            outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", ""),
                            outboundMsg.getOutboundUSSDMessageRequest().getKeyword());
                    SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 003 ######################  :" + "not unique");
                }
                
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 004 ######################  :" + "after app unique");
                
                boolean isEndsession = false;
                if (outboundMsg.getOutboundUSSDMessageRequest().getUssdAction() == USSDAction.mtfin) {
                    isEndsession = true;
                    SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "ismtfin");
                }

                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "after mtfin"+application);
                OutboundRequest request = new OutboundRequest();
                request.setApplication(application);                
                outboundMsg.getOutboundUSSDMessageRequest().setAddress(outboundMsg.getOutboundUSSDMessageRequest().getAddress().replace("tel:", "").replaceAll("\\+", ""));
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "set app"+application);
                request.setOutboundUSSDMessageRequest(outboundMsg.getOutboundUSSDMessageRequest());
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "set outboundussdmsg");
                Long id = manager.saveRequest(request);

                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "after save");
                //workaround for default port
                String sender = outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", "");
                if (application != null) {
                    SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "before sp if app != null");
                    ServiceProvider sp = application.getServiceProvider();
                    SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "after sp if app != null");
                    if ((sp.getDefaultport() == null) || (sp.getDefaultport().isEmpty())) {
                        SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "spgetdefault bef");
                        sender = outboundMsg.getOutboundUSSDMessageRequest().getShortCode().replace("tel:", "").replaceAll("\\+", "");
                        SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "spgetdefault aft");
                    } else {
                        SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "spgetdefault else bef");
                        sender = sp.getDefaultport();
                        SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "spgetdefault else aft");
                    }
                }

                USSDHandler ussdHandler = new USSDHandler();
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "sendmsg bef");
                ussdHandler.sendMessage(sender, outboundMsg.getOutboundUSSDMessageRequest().getAddress(), outboundMsg.getOutboundUSSDMessageRequest().getOutboundUSSDMessage(), false, outboundMsg.getOutboundUSSDMessageRequest().getSessionID(), id.intValue(), isRegistered, isEndsession);
                SystemLog.getInstance().getLogger("smscomp_event").info(" ###################### 005 ######################  :" + "sendmsg aft");
            } catch (Exception e) {
                SystemLog.getInstance().getLogger("smscomp_event").error("Error + " + "################excutePost : " + e.getMessage());
                return null;
            }
            //==========================================================PRIYANKA_06608==============================================================================================================            
            return response.toString();

        } catch (Exception e) {
            //LOG.error("[HTTPPoster], Error + " + "excutePost " + e.getMessage());
            SystemLog.getInstance().getLogger("smscomp_event").error("Error + " + "excutePost :" + urlParameters + ":" + e.getMessage());
            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void run() {
        excutePost();
    }
}
