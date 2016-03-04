package mifeapitest.services;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import mifeapitest.util.Logutil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Httpposternew {

   
    public DispatcherObj makeRequest(String url, String requestStr, String apiuser,String auth) {
    
        
        DispatcherObj d = new DispatcherObj();
        d.setRequestJson(requestStr);
        
        
        String retStr = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("sandbox", apiuser);
            
            if (auth != null) {
                postRequest.addHeader("Authorization", "Bearer " + auth);
            }
            

            StringEntity input = new StringEntity(requestStr);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

           /* if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            } */

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            
            d.setResponseJson(retStr);
            d.setResponseStatus(response.getStatusLine().getStatusCode());

            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            //e.printStackTrace();            
            d.setResponseStatus(0);
            Logutil.getlogger().warning(e.getMessage());
        }
        return d;
    }
    
    public DispatcherObj makeGetRequest(String url, String requestStr, String apiuser,String auth) {
        
        DispatcherObj d = new DispatcherObj();
        d.setRequestJson(requestStr);
        
        
        String retStr = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("accept", "application/json");
            httpget.addHeader("sandbox", apiuser);
            if (auth != null) {
                httpget.addHeader("Authorization", "Bearer " + auth);
            }
            
            //StringEntity input = new StringEntity(requestStr);
            //input.setContentType("application/json");
            //httpget.setEntity(input);

            HttpResponse response = httpClient.execute(httpget);

           /* if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            } */

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            
            d.setResponseJson(retStr);
            d.setResponseStatus(response.getStatusLine().getStatusCode());

            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            //e.printStackTrace();
            d.setResponseStatus(0);
            Logutil.getlogger().warning(e.getMessage());
        }
        return d;
    }
    
    public DispatcherObj makeDeleteRequest(String url, String requestStr, String apiuser, String auth) {
        
        DispatcherObj d = new DispatcherObj();
        d.setRequestJson(requestStr);
        
        
        String retStr = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(url);

            httpDelete.addHeader("Content-Type", "application/json");
            httpDelete.addHeader("accept", "application/json");
            httpDelete.addHeader("sandbox", apiuser);
            if (auth != null) {
                httpDelete.addHeader("Authorization", "Bearer " + auth);
            }

            //StringEntity input = new StringEntity(requestStr);
            //input.setContentType("application/json");
            //httpget.setEntity(input);

            HttpResponse response = httpClient.execute(httpDelete);

           /* if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            } */

            /*BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            } */
            
            d.setResponseJson("NO CONTENT");
            d.setResponseStatus(response.getStatusLine().getStatusCode());

            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            //e.printStackTrace();
            d.setResponseStatus(0);
            Logutil.getlogger().warning(e.getMessage());
        }
        return d;
    }   
   
}
