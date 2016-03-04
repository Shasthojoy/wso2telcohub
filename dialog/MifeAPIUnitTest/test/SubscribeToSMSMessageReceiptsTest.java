/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import mifeapitest.api.requestbeans.SubscriptionRequest;
import mifeapitest.api.responsebeans.Subscription;
import mifeapitest.api.responsebeans.SubscriptionResponse;
import mifeapitest.services.DispatcherObj;
import mifeapitest.services.Httpposternew;
import mifeapitest.util.FileUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 *
 * @author Shashika Wijayasekera
 */
public class SubscribeToSMSMessageReceiptsTest {

    static String apimgr_url, apimgr_user, apimgr_auth, subscribeURL;

    public SubscribeToSMSMessageReceiptsTest() {
        loadmifeconfig();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public static void loadmifeconfig() {
        Properties props = new Properties();
        InputStreamReader in = null;

        try {
            in = new InputStreamReader(new FileInputStream("./mifeconfig.properties"), "UTF-8");
            props.load(in);
            apimgr_url = props.getProperty("apimgr.url");
            apimgr_user = props.getProperty("apimgr.user");
            apimgr_auth = props.getProperty("apimgr.auth");
            subscribeURL = apimgr_url+props.getProperty("jsonrequest.url.subscribe");
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            System.err.println(
                    "Check your Property file, it should be in application home dir, Error:"
                    + e.getCause() + "Cant load mifeconfig.properties");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println(
                    "Check your Property file, it should be in application home dir, Error:"
                    + e.getCause() + "Cant load mifeconfig.properties");
            System.exit(-1);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private String createSampleResponse(SubscriptionRequest subscriptionRequest, SubscriptionResponse subscriptionResponse) {

        String sampleJsonResponse = null;
        try {
            Subscription objSubscription = new Subscription();

            Subscription.CallbackReference objCallbackReference = new Subscription.CallbackReference();

            objCallbackReference.setCallbackData(subscriptionRequest.getSubscription().getCallbackReference().getCallbackData());
            objCallbackReference.setNotifyURL(subscriptionRequest.getSubscription().getCallbackReference().getNotifyURL());

            objSubscription.setCallbackReference(objCallbackReference);
            objSubscription.setCriteria(subscriptionRequest.getSubscription().getCriteria());
            objSubscription.setDestinationAddress(subscriptionRequest.getSubscription().getDestinationAddress());
            objSubscription.setNotificationFormat(subscriptionRequest.getSubscription().getNotificationFormat());
            objSubscription.setClientCorrelator(subscriptionRequest.getSubscription().getClientCorrelator());
            objSubscription.setResourceURL(subscriptionResponse.getSubscription().getResourceURL());

            ObjectMapper mapper = new ObjectMapper();

            sampleJsonResponse = "{\"subscription\":" + mapper.writeValueAsString(objSubscription) + "}";
        } catch (Exception e) {
            System.out.println("createSampleResponse: " + e);
        }

        return sampleJsonResponse;
    }

    /**
     * Test of Subscribe To SMSMessage Receipts API with correct parameters
     */
    @Test
    public void testSubscribeToSMSMessageReceiptsAPI1() {
        System.out.println("Test of Subscribe To SMSMessage Receipts API with correct parameters");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/subscribesms.json");
        Gson gson = new Gson();
        SubscriptionRequest objSubscriptionRequest = gson.fromJson(sampleRequestJson, SubscriptionRequest.class);
        String requestJson = gson.toJson(objSubscriptionRequest);

        int expectedStatus = 201;
        DispatcherObj objDispatch = new Httpposternew().makeRequest(subscribeURL, requestJson, apimgr_user, apimgr_auth);
        SubscriptionResponse objSubscriptionResponse = gson.fromJson(objDispatch.getResponseJson().toString(), SubscriptionResponse.class);
        String expectedJsonResponse = createSampleResponse(objSubscriptionRequest, objSubscriptionResponse);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }

    /**
     * Test of Subscribe To SMSMessage Receipts API without mandatory parameters
     */
    @Test
    public void testSubscribeToSMSMessageReceiptsAPI2() {
        System.out.println("Test of Subscribe To SMSMessage Receipts API without mandatory parameters");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/subscribesms.json");
        Gson gson = new Gson();
        SubscriptionRequest objSubscriptionRequest = gson.fromJson(sampleRequestJson, SubscriptionRequest.class);
        objSubscriptionRequest.getSubscription().setDestinationAddress(null);
        objSubscriptionRequest.getSubscription().getCallbackReference().setNotifyURL(null);
        objSubscriptionRequest.getSubscription().getCallbackReference().setCallbackData(null);
        String requestJson = gson.toJson(objSubscriptionRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0002\",\n"
                + "    \"text\" : \"Invalid input value for message part %1\",\n"
                + "    \"variables\" : [ \"Missing mandatory parameter: destinationAddress,notifyURL,callbackData\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(subscribeURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    
    /**
     * Test of Subscribe To SMSMessage Receipts API for destinationAddress with
     * existing criteria
     */
    @Test
    public void testSubscribeToSMSMessageReceiptsAPI3() {
        System.out.println("Test of Subscribe To SMSMessage Receipts API for destinationAddress with existing criteria");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/subscribesms.json");
        Gson gson = new Gson();
        SubscriptionRequest objSubscriptionRequest = gson.fromJson(sampleRequestJson, SubscriptionRequest.class);
        String requestJson = gson.toJson(objSubscriptionRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0008\",\n"
                + "    \"text\" : \"Overlapped criteria %1\",\n"
                + "    \"variables\" : [ \"" + objSubscriptionRequest.getSubscription().getCriteria().toString() + "\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(subscribeURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }

    /**
     * Test of Subscribe To SMSMessage Receipts API for unique
     * destinationAddress with criteria
     */
    @Test
    public void testSubscribeToSMSMessageReceiptsAPI4() {
        System.out.println("Test of Subscribe To SMSMessage Receipts API for unique destinationAddress with criteria");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/subscribesms.json");
        Gson gson = new Gson();
        SubscriptionRequest objSubscriptionRequest = gson.fromJson(sampleRequestJson, SubscriptionRequest.class);
        objSubscriptionRequest.getSubscription().setDestinationAddress("000"); //set unique destinationAddress
        objSubscriptionRequest.getSubscription().setCriteria("Vote");
        String requestJson = gson.toJson(objSubscriptionRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0008\",\n"
                + "    \"text\" : \"Overlapped criteria %1\",\n"
                + "    \"variables\" : [ \""+objSubscriptionRequest.getSubscription().getCriteria().toString()+"\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(subscribeURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    /**
     * Test of Subscribe To SMSMessage Receipts API for un-provisioned
     * destinationAddress
     */
    @Test
    public void testSubscribeToSMSMessageReceiptsAPI5() {
        System.out.println("Test of Subscribe To SMSMessage Receipts API for un-provisioned destinationAddress");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/subscribesms.json");
        Gson gson = new Gson();
        SubscriptionRequest objSubscriptionRequest = gson.fromJson(sampleRequestJson, SubscriptionRequest.class);
        objSubscriptionRequest.getSubscription().setDestinationAddress("001"); //set un-provisioned destinationAddress 
        String requestJson = gson.toJson(objSubscriptionRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0001\",\n"
                + "    \"text\" : \"A service error occurred. Error code is %1\",\n"
                + "    \"variables\" : [ \"" + objSubscriptionRequest.getSubscription().getDestinationAddress().toString() + " Not Provisioned\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(subscribeURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
}
