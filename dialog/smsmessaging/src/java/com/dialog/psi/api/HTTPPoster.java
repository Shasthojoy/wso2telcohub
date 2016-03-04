package com.dialog.psi.api;

import com.dialog.psi.util.FileUtil;
import com.dialog.util.SystemLog;
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
            return response.toString();

        } catch (Exception e) {
             //LOG.error("[HTTPPoster], Error + " + "excutePost " + e.getMessage());
            //SystemLog.getInstance().getLogger("smscomp_event").error("Error + " + "excutePost :" + urlParameters + ":" + e.getMessage());
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
