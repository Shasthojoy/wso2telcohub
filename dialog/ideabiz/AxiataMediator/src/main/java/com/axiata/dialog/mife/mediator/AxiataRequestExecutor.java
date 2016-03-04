/*
 * AxitaRequestFactory.java
 * Aug 19, 2013  9:36:54 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */
package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operator;
import com.axiata.dialog.mife.events.data.publisher.EventsDataPublisherClient;
import com.axiata.dialog.mife.southbound.data.publisher.DataPublisherClient;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ResponseError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <TO-DO>
 * <code>AxitaRequestFactory</code>
 *
 * @version $Id: AxitaRequestFactory.java,v 1.00.000
 */
public abstract class AxiataRequestExecutor {

    private static Log log = LogFactory.getLog(AxiataRequestExecutor.class);
    List<Operator> validoperators = null;
    private String httpMethod;
    private JSONObject jsonBody;
    private String subResourcePath;
    //others methods to be implementd
    //<TO-DO>
    private String strErr;
    private DataPublisherClient publisherClient;
    private EventsDataPublisherClient eventsPublisherClient;

    public String getStrErr() {
        return strErr;
    }

    public void setStrErr(String strErr) {
        this.strErr = strErr;
    }

    protected String getAccessToken(String operator) throws Exception {
        Operator op = null;
        String token = null;

        if (operator == null) {
            return token;
        }

        for (Operator d : validoperators) {
            if (d.getOperatorname() != null && d.getOperatorname().contains(operator)) {
                op = d;
                break;
            }
        }
        //
        long timeexpires = (long) (op.getTokentime() + (op.getTokenvalidity() * 1000));
        long currtime = new Date().getTime();

        if (timeexpires > currtime) {
            token = op.getToken();
        } else {

            String Strtoken = makeTokenrequest(op.getTokenurl(), "?grant_type=refresh_token&refresh_token=" + op.getRefreshtoken() + "&scope=PRODUCTION", ("" + op.getTokenauth()));
            JSONObject jsontoken = new JSONObject(Strtoken);
            token = jsontoken.getString("access_token");
            new AxiataDbService().tokenUpdate(op.getOperatorid(), jsontoken.getString("refresh_token"), Long.parseLong(jsontoken.getString("expires_in")), new Date().getTime(), token);
        }
        //something here

        return token;
    }
    protected String resourceUrl;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
    String applicationid;

    public String getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(String applicationid) {
        this.applicationid = applicationid;
    }

    public List<Operator> getValidoperators() {
        return validoperators;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public JSONObject getJsonBody() {
        return jsonBody;
    }

    public String getSubResourcePath() {
        return subResourcePath;
    }

    public boolean initialize(MessageContext context) throws Exception {
        //Get valid operators
        String applicationid = getApplicationid();
        AxiataDbService dbservice = new AxiataDbService();
        if (applicationid == null) {
            throw new AxiataException("SVC0001", "",new String[]{"Requested service is not provisioned"});
        }
        validoperators = dbservice.applicationOperators(Integer.valueOf(applicationid));
        if (validoperators.isEmpty()) {
            throw new AxiataException("SVC0001", "",new String[]{"Requested service is not provisioned"});
        }

        subResourcePath = (String) context.getProperty("REST_SUB_REQUEST_PATH");
        resourceUrl = (String) context.getProperty("REST_FULL_REQUEST_PATH");
        httpMethod = (String) context.getProperty("REST_METHOD");

        String jsonPayloadToString = JsonUtil.jsonPayloadToString(((Axis2MessageContext) context).getAxis2MessageContext());
        jsonBody = new JSONObject(jsonPayloadToString);

        return true;
    }

    /*
     * Dediate exata route request
     */
    public abstract boolean execute(MessageContext context) throws AxiataException, AxisFault, Exception;

    public abstract boolean validateRequest(String httpMethod, String requestPath, JSONObject jsonBody,
                                            MessageContext context) throws Exception;


    public void setResponse(MessageContext mc, String responseStr) throws AxisFault {
        JsonUtil.newJsonPayload(((Axis2MessageContext) mc).getAxis2MessageContext(), responseStr, true, true);
    }

    public void removeHeaders(MessageContext context) {
        Object headers = ((Axis2MessageContext) context).getAxis2MessageContext().getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
        if (headers != null && headers instanceof Map) {
            Map headersMap = (Map) headers;
            headersMap.clear();
        }
    }

    public void handlePluginException(String errResp) throws AxiataException {

        Gson gson = new GsonBuilder().serializeNulls().create();
        ResponseError responseError = gson.fromJson(errResp, ResponseError.class);
        if (responseError == null) {
            return;
        }
        RequestError reqerror = responseError.getRequestError();
        if (reqerror == null) {
            return;
        }

        String messagid = null;
        String variables = null;
        if (reqerror.getPolicyException() != null) {
            messagid = reqerror.getPolicyException().getMessageId();
            variables = reqerror.getPolicyException().getVariables();
        } else if (reqerror.getServiceException() != null) {
            messagid = reqerror.getServiceException().getMessageId();
            variables = reqerror.getServiceException().getVariables();
        } else {
            return;
        }
        throw new AxiataException(messagid, "", new String[]{variables});
    }

    /**
     *
     * @param url
     * @param requestStr
     * @param messageContext
     * @return
     */
    public String makeRequest(OperatorEndpoint operatorendpoint, String url, String requestStr, boolean auth,
                              MessageContext messageContext) {

        publishRequestData(operatorendpoint, url, requestStr, messageContext);

        ICallresponse icallresponse = null;
        String retStr = "";
        int statusCode = 0;

        URL neturl;
        HttpURLConnection connection = null;

        try {
            //String Authtoken = AccessToken; //FileUtil.getApplicationProperty("wow.api.bearer.token");

            //String encodeurl = URLEncoder.encode(url, "UTF-8");
            neturl = new URL(url);
            connection = (HttpURLConnection) neturl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if (auth) {
                connection.setRequestProperty("Authorization", "Bearer " + getAccessToken(operatorendpoint.getOperator()));

                //Add JWT token header
                org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                        .getAxis2MessageContext();
                Object headers = axis2MessageContext.getProperty(org.apache.axis2.context.MessageContext
                        .TRANSPORT_HEADERS);
                if (headers != null && headers instanceof Map) {
                    Map headersMap = (Map) headers;
                    String jwtparam = (String) headersMap.get("x-jwt-assertion");
                    if (jwtparam != null) {
                        connection.setRequestProperty("x-jwt-assertion", jwtparam);
                    }
                }
            }
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (log.isDebugEnabled()) {
                log.debug("Southbound Request URL: " + connection.getRequestMethod() + " " + connection.getURL());
                log.debug("Southbound Request Headers: " + connection.getRequestProperties());
                log.debug("Southbound Request Body: " + requestStr);
            }
            
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(requestStr);
            wr.flush();
            wr.close();

            statusCode = connection.getResponseCode();
            if ((statusCode != 200) && (statusCode != 201) && (statusCode != 400) && (statusCode != 401)) {
                throw new RuntimeException("Failed : HTTP error code : " + statusCode);
            }
            
            InputStream is = null;            
            if ((statusCode == 200) || (statusCode == 201)) {
                is = connection.getInputStream();           
            } else {
                is = connection.getErrorStream();           
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            br.close();

            if (log.isDebugEnabled()) {
                log.debug("Southbound Response Status: " + statusCode + " " + connection.getResponseMessage());
                log.debug("Southbound Response Headers: " + connection.getHeaderFields());
                log.debug("Southbound Response Body: " + retStr);
            }
        } catch (Exception e) {
            log.error("[WSRequestService ], makerequest, " + e.getMessage(), e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            publishResponseData(statusCode, retStr, messageContext);
        }
        
        return retStr;
    }

    protected String makeTokenrequest(String url, String requestStr, String authheader) {
        ICallresponse icallresponse = null;
        String retStr = "";

        URL neturl;
        HttpURLConnection connection = null;
        
        try {
            //String Authtoken = AccessToken; //FileUtil.getApplicationProperty("wow.api.bearer.token");

            String encurl = (requestStr != null) ? url + requestStr : url;
            //String encodeurl = URLEncoder.encode(url, "UTF-8");
            neturl = new URL(encurl);
            connection = (HttpURLConnection) neturl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", authheader);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            
           connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(requestStr);
            wr.flush();
            wr.close();

            if ((connection.getResponseCode() != 200) && (connection.getResponseCode() != 201)
                    && (connection.getResponseCode() != 400) && (connection.getResponseCode() != 401)) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }
            
            InputStream is = null;            
            if ((connection.getResponseCode() == 200) || (connection.getResponseCode() == 201)) {
                is = connection.getInputStream();           
            } else {
                is = connection.getErrorStream();           
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            br.close();
        } catch (Exception e) {
            log.error("[WSRequestService ], makerequest, " + e.getMessage(), e);
            return null;
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        } 
        return retStr;
    }

    public String makeGetRequest(OperatorEndpoint operatorendpoint, String url, String requestStr, boolean auth,
                                 MessageContext messageContext) {

        publishRequestData(operatorendpoint, url, null, messageContext);
        int statusCode = 0;
        String retStr = "";
        URL neturl;
        HttpURLConnection connection = null; 
        
        try {

            //String Authtoken = AccessToken; //FileUtil.getApplicationProperty("wow.api.bearer.token");
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            String encurl = (requestStr != null) ? url + requestStr : url; 
            neturl = new URL(encurl);
            connection = (HttpURLConnection) neturl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            
            if (auth) {
                connection.setRequestProperty("Authorization", "Bearer " + getAccessToken(operatorendpoint.getOperator()));

                //Add JWT token header
                org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                        .getAxis2MessageContext();
                Object headers = axis2MessageContext.getProperty(org.apache.axis2.context.MessageContext
                        .TRANSPORT_HEADERS);
                if (headers != null && headers instanceof Map) {
                    Map headersMap = (Map) headers;
                    String jwtparam = (String) headersMap.get("x-jwt-assertion");
                    if (jwtparam != null) {
                        connection.setRequestProperty("x-jwt-assertion", jwtparam);
                    }
                }
            }
            connection.setUseCaches(false);

            if (log.isDebugEnabled()) {
                log.debug("Southbound Request URL: " + connection.getRequestMethod() + " " + connection.getURL());
                log.debug("Southbound Request Headers: " + connection.getRequestProperties());
                log.debug("Southbound Request Body: " + requestStr);
            }

            statusCode = connection.getResponseCode();
            if ((statusCode != 200) && (statusCode != 201) && (statusCode != 400) && (statusCode != 401)) {
                throw new RuntimeException("Failed : HTTP error code : " + statusCode);
            }
            
            InputStream is = null;            
            if ((statusCode == 200) || (statusCode == 201)) {
                is = connection.getInputStream();           
            } else {
                is = connection.getErrorStream();           
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            br.close();

            if (log.isDebugEnabled()) {
                log.debug("Southbound Response Status: " + statusCode + " " + connection.getResponseMessage());
                log.debug("Southbound Response Headers: " + connection.getHeaderFields());
                log.debug("Southbound Response Body: " + retStr);
            }
        } catch (Exception e) {
            log.error("[WSRequestService ], makerequest, " + e.getMessage(), e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            publishResponseData(statusCode, retStr, messageContext);
        }
        return retStr;
    }

    public String makeDeleteRequest(OperatorEndpoint operatorendpoint, String url, String requestStr, boolean auth,
                                    MessageContext messageContext) {

        publishRequestData(operatorendpoint, url, requestStr, messageContext);
        int statusCode = 0;
        String retStr = "";
        URL neturl;
        HttpURLConnection connection = null; 
        
        try {

            //String Authtoken = AccessToken; //FileUtil.getApplicationProperty("wow.api.bearer.token");
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            
            String encurl = (requestStr != null) ? url + requestStr : url;            
            neturl = new URL(encurl);
            connection = (HttpURLConnection) neturl.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            
            if (auth) {
                connection.setRequestProperty("Authorization", "Bearer " + getAccessToken(operatorendpoint.getOperator()));

                //Add JWT token header
                org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                        .getAxis2MessageContext();
                Object headers = axis2MessageContext.getProperty(org.apache.axis2.context.MessageContext
                        .TRANSPORT_HEADERS);
                if (headers != null && headers instanceof Map) {
                    Map headersMap = (Map) headers;
                    String jwtparam = (String) headersMap.get("x-jwt-assertion");
                    if (jwtparam != null) {
                        connection.setRequestProperty("x-jwt-assertion", jwtparam);
                    }
                }
            }
            connection.setUseCaches(false);

            if (log.isDebugEnabled()) {
                log.debug("Southbound Request URL: " + connection.getRequestMethod() + " " + connection.getURL());
                log.debug("Southbound Request Headers: " + connection.getRequestProperties());
                log.debug("Southbound Request Body: " + requestStr);
            }

            if (requestStr != null) {
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(requestStr);
                wr.flush();
                wr.close();
            }

            statusCode = connection.getResponseCode();
            System.out.println("response code: "+ statusCode);
            
            if ((statusCode != 200) && (statusCode != 201) && (statusCode != 400) && (statusCode != 401)) {
                throw new RuntimeException("Failed : HTTP error code : " + statusCode);
            }
            
            InputStream is = null;            
            if ((statusCode == 200) || (statusCode == 201)) {
                is = connection.getInputStream();           
            } else {
                is = connection.getErrorStream();           
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String output;
            while ((output = br.readLine()) != null) {
                retStr += output;
            }
            br.close();

            if (log.isDebugEnabled()) {
                log.debug("Southbound Response Status: " + statusCode + " " + connection.getResponseMessage());
                log.debug("Southbound Response Headers: " + connection.getHeaderFields());
                log.debug("Southbound Response Body: " + retStr);
            }
        } catch (Exception e) {
            log.error("[WSRequestService ], makerequest, " + e.getMessage(), e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            publishResponseData(statusCode, retStr, messageContext);
        }
        return retStr;
    }

    private void publishRequestData(OperatorEndpoint operatorendpoint, String url, String requestStr,
            MessageContext messageContext) {
        //set properties for request data publisher
        messageContext.setProperty(SouthboundPublisherConstants.OPERATOR_ID, operatorendpoint.getOperator());
        messageContext.setProperty(SouthboundPublisherConstants.SB_ENDPOINT, url);

        if (requestStr != null) {
            //get chargeAmount property for payment API request
            JSONObject paymentReq = null;
            try {
                paymentReq = new JSONObject(requestStr).optJSONObject("amountTransaction");
                if (paymentReq != null) {
                    String chargeAmount = paymentReq.getJSONObject("paymentAmount").getJSONObject("chargingInformation")
                            .optString("amount");
                    messageContext.setProperty(SouthboundPublisherConstants.CHARGE_AMOUNT, chargeAmount);
                    String payCategory = paymentReq.getJSONObject("paymentAmount").getJSONObject("chargingMetaData")
                            .optString("purchaseCategoryCode");
                    messageContext.setProperty(SouthboundPublisherConstants.PAY_CATEGORY, payCategory);
                }
            } catch (JSONException e) {
                log.error("Error in converting request to json. " + e.getMessage(), e);
            }
        }

        //publish data
        if (publisherClient == null) {
            publisherClient = new DataPublisherClient();
        }
        publisherClient.publishRequest(messageContext, requestStr);
    }

    private void publishResponseData(int statusCode, String retStr, MessageContext messageContext) {
        //set properties for response data publisher
        messageContext.setProperty(SouthboundPublisherConstants.RESPONSE_CODE, Integer.toString(statusCode));
        messageContext.setProperty(SouthboundPublisherConstants.MSISDN, messageContext.getProperty(AxiataConstants.USER_MSISDN));

        boolean isPaymentReq = false;

        if (retStr != null && !retStr.isEmpty()) {
            //get serverReferenceCode property for payment API response
            JSONObject paymentRes = null;
            //get exception property for exception response
            JSONObject exception = null;
            try {
                JSONObject response = new JSONObject(retStr);
                paymentRes = response.optJSONObject("amountTransaction");
                if (paymentRes != null) {
                    messageContext.setProperty(SouthboundPublisherConstants.OPERATOR_REF,
                            paymentRes.optString("serverReferenceCode"));
                    isPaymentReq = true;
                }

                exception = response.optJSONObject("requestError");
                if (exception != null) {
                    JSONObject exception_body = exception.optJSONObject("serviceException");
                    if (exception_body == null) {
                        exception_body = exception.optJSONObject("policyException");
                    }
                    
                    if (exception_body != null) {
                        log.info("exception id: " + exception_body.optString("messageId"));
                        log.info("exception message: " + exception_body.optString("text"));
                        messageContext.setProperty(SouthboundPublisherConstants.EXCEPTION_ID,
                                exception_body.optString("messageId"));
                        messageContext.setProperty(SouthboundPublisherConstants.EXCEPTION_MESSAGE,
                                exception_body.optString("text"));
                    }
                }
            } catch (JSONException e) {
                log.error("Error in converting response to json. " + e.getMessage(), e);
            }
        }

        //publish data to BAM
        publisherClient.publishResponse(messageContext, retStr);

        //publish to CEP only the successful payment requests
        if (isPaymentReq && statusCode >= 200 && statusCode < 300) {
            publishToCEP(messageContext);
        }
    }

    private void publishToCEP(MessageContext messageContext) {
        if (eventsPublisherClient == null) {
            eventsPublisherClient = new EventsDataPublisherClient();
        }
        eventsPublisherClient.publishEvent(messageContext);
    }

}
