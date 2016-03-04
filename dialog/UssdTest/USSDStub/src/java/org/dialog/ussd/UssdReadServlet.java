/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dialog.ussd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.dialog.ussd.dto.UssdResponse;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;
import org.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.dialog.ussd.dto.UssdReceiveResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dialog.ussd.utill.ReadPropertyFile;

/**
 * @author Hiranya
 */
public class UssdReadServlet extends OneAPIServlet {

    static Logger logger = Logger.getLogger(UssdReadServlet.class);
    private final String[] validationRules = {"ussd", "*", "outbound"};
    private final String USER_AGENT = "Mozilla/5.0";
    ReadPropertyFile readFile = new ReadPropertyFile();
    private final String USSD_RECEIVE_RESPONSE_MSG = readFile.getResponseMessage();
    private final int NOTIFICATION_DELAY = readFile.getNotificationDelay();
    private String ussdReceiveResponseString;
    private String notifyURL;

    @Override
    public void init() throws ServletException {
        logger.debug("UssdReadServlet initialised");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);

        if (validateRequest(request, response, requestParts, validationRules)) {
            String address = null;
            String shortCode = null;
            String keyword = null;
            String message = null;
            String clientCorrelator = null;
            String notifyUrl = null;
            String callbackData = null;
            String ussdAction = null;

            String json = getRequestJSON(request);

            try {
                JSONObject objJSONObject = new JSONObject(json);
                JSONObject requestData = objJSONObject.getJSONObject("outboundUSSDMessageRequest");

                if (requestData.has("address")) {
                    address = nullOrTrimmed(requestData.getString("address"));
                }
                if (requestData.has("shortCode")) {
                    shortCode = nullOrTrimmed(requestData.getString("shortCode"));
                }

                if (requestData.has("keyword")) {
                    keyword = nullOrTrimmed(requestData.getString("keyword"));
                }
                if (requestData.has("outboundUSSDMessage")) {
                    message = nullOrTrimmed(requestData.getString("outboundUSSDMessage"));
                }
                if (requestData.has("clientCorrelator")) {
                    clientCorrelator = nullOrTrimmed(requestData.getString("clientCorrelator"));
                }
                if (requestData.has("ussdAction")) {
                    ussdAction = nullOrTrimmed(requestData.getString("ussdAction"));
                }
                if (requestData.has("responseRequest")) {
                    JSONObject responseRequest = requestData.getJSONObject("responseRequest");
                    if (responseRequest.has("notifyURL")) {
                        notifyUrl = nullOrTrimmed(responseRequest.getString("notifyURL"));
                    }
                    if (responseRequest.has("callbackData")) {
                        callbackData = nullOrTrimmed(responseRequest.getString("callbackData"));
                    }
                }

            } catch (Exception e) {
                logger.error("Manipulating recived JSON Object: " + e);
                //sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0002", "Invalid input value for message part %1", "Request JSON format is invalid");
            }

            ValidationRule[] rules = null;

            rules = new ValidationRule[]{
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL_END_USER_ID, "address", address),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "shortCode", shortCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "keyword", keyword),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "outboundUSSDMessage", message),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "clientCorrelator", clientCorrelator),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "ussdAction", ussdAction),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "notifyURL", notifyUrl),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),};

            if (checkRequestParameters(response, rules)) {

                //Post reply to Notify URL
                if (notifyUrl != null) {
                    try {
                        UssdReceiveResponse recRes = new UssdReceiveResponse();
                        recRes.setAddress(address);
                        recRes.setShortCode(shortCode);
                        recRes.setKeyword(keyword);

                        //Ussd Message to post
                        recRes.setInboundUSSDMessage(USSD_RECEIVE_RESPONSE_MSG);

                        recRes.setClientCorrelator(clientCorrelator);
                        recRes.setUssdAction("mtcont");

                        UssdReceiveResponse.ResponseRequest resReqInbound = new UssdReceiveResponse.ResponseRequest();
                        resReqInbound.setCallbackData(callbackData);
                        resReqInbound.setNotifyURL(notifyUrl);

                        recRes.setResponseRequest(resReqInbound);

                        ObjectMapper mapper = new ObjectMapper();
                        ussdReceiveResponseString = "{\"inboundUSSDMessageRequest\":" + mapper.writeValueAsString(recRes) + "}";
                        notifyURL = notifyUrl;

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                System.out.println("Waiting for Notify URL !!");
                                try {
                                    sendPost(notifyURL, ussdReceiveResponseString);
                                } catch (Exception ex) {
                                    System.out.println("Exception in notification timer :: " + ex);
                                }
                            }
                        }, Long.valueOf(NOTIFICATION_DELAY));
                    } catch (Exception ex) {
                        System.out.println("Exception in Posting to Notify URL :: " + ex);
                    }
                }

                String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/ussd/v1/outbound";

                UssdResponse ussdRes = new UssdResponse();
                ussdRes.setAddress(address);
                ussdRes.setShortCode(shortCode);
                ussdRes.setKeyword(keyword);
                ussdRes.setOutboundUSSDMessage(message);
                ussdRes.setClientCorrelator(clientCorrelator);

                ussdRes.setDeliveryStatus("SENT");
                ussdRes.setUssdAction(ussdAction);

                UssdResponse.ResponseRequest ussdResInnerData = new UssdResponse.ResponseRequest();
                ussdResInnerData.setCallbackData(callbackData);
                ussdResInnerData.setNotifyURL(notifyUrl);

                ussdRes.setResponseRequest(ussdResInnerData);

                ObjectMapper mapper = new ObjectMapper();
                String ussdResponseString = "{\"outboundUSSDMessageRequest\":" + mapper.writeValueAsString(ussdRes) + "}";

                sendJSONResponse(response, ussdResponseString, CREATED, resourceURL);

            }
        }
    }

    private static String getRequestJSON(HttpServletRequest request) {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            System.out.println("Error in reading RequestJSON: " + ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    System.out.println("Error in BufferReader: " + ex);
                }
            }
        }
        body = stringBuilder.toString();
        return body;
    }

    private void sendPost(String notifyUrl, String jsonBody) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(notifyUrl);
        StringEntity input = new StringEntity(jsonBody);
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
    }
}
