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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import mifeapitest.api.requestbeans.SendSMSRequest;
import mifeapitest.api.responsebeans.DeliveryInfo;
import mifeapitest.api.responsebeans.DeliveryInfoList;
import mifeapitest.api.responsebeans.OutboundSMSMessageRequest;
import mifeapitest.api.responsebeans.SendSMSResponse;
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
public class SendSMSTest {

    static String apimgr_url, apimgr_user, apimgr_auth, sendSmsURL;

    public SendSMSTest() {
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
            sendSmsURL = apimgr_url+props.getProperty("jsonrequest.url.sendsms");
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

    private String createSampleResponse(SendSMSRequest sendSMSRequest, SendSMSResponse sendSMSResponse) {

        String sampleJsonResponse = null;
        try {
            OutboundSMSMessageRequest objOutboundSMSMessageRequest = new OutboundSMSMessageRequest();
            objOutboundSMSMessageRequest.setAddress(sendSMSRequest.getOutboundSMSMessageRequest().getAddress());

            DeliveryInfoList objDeliveryInfoList = new DeliveryInfoList();

            List<DeliveryInfo> deliveryInforArrayList = new ArrayList<DeliveryInfo>();

            Iterator<String> addressReader = sendSMSRequest.getOutboundSMSMessageRequest().getAddress().iterator();
            while (addressReader.hasNext()) {

                DeliveryInfo objDeliveryInfo = new DeliveryInfo();
                objDeliveryInfo.setAddress(addressReader.next());
                objDeliveryInfo.setDeliveryStatus("MessageWaiting");

                deliveryInforArrayList.add(objDeliveryInfo);
            }
            objDeliveryInfoList.setDeliveryInfo(deliveryInforArrayList);
            objDeliveryInfoList.setResourceURL(sendSMSResponse.getOutboundSMSMessageRequest().getDeliveryInfoList().getResourceURL());

            objOutboundSMSMessageRequest.setDeliveryInfoList(objDeliveryInfoList);
            objOutboundSMSMessageRequest.setSenderAddress(sendSMSRequest.getOutboundSMSMessageRequest().getSenderAddress());

            OutboundSMSMessageRequest.OutboundSMSTextMessage objOutboundSMSTextMessage = new OutboundSMSMessageRequest.OutboundSMSTextMessage();
            objOutboundSMSTextMessage.setMessage(sendSMSRequest.getOutboundSMSMessageRequest().getOutboundSMSTextMessage().getMessage());

            objOutboundSMSMessageRequest.setOutboundSMSTextMessage(objOutboundSMSTextMessage);
            objOutboundSMSMessageRequest.setClientCorrelator(sendSMSRequest.getOutboundSMSMessageRequest().getClientCorrelator());

            OutboundSMSMessageRequest.ReceiptRequest objReceiptRequest = new OutboundSMSMessageRequest.ReceiptRequest();
            objReceiptRequest.setNotifyURL(sendSMSRequest.getOutboundSMSMessageRequest().getReceiptRequest().getNotifyURL());
            objReceiptRequest.setCallbackData(sendSMSRequest.getOutboundSMSMessageRequest().getReceiptRequest().getCallbackData());

            objOutboundSMSMessageRequest.setReceiptRequest(objReceiptRequest);
            objOutboundSMSMessageRequest.setSenderName(sendSMSRequest.getOutboundSMSMessageRequest().getSenderName());
            objOutboundSMSMessageRequest.setResourceURL(sendSMSResponse.getOutboundSMSMessageRequest().getResourceURL());

            ObjectMapper mapper = new ObjectMapper();
            sampleJsonResponse = "{\"outboundSMSMessageRequest\":" + mapper.writeValueAsString(objOutboundSMSMessageRequest) + "}";
        } catch (Exception e) {
            System.out.println("createSampleResponse: " + e);
        }
        
        return sampleJsonResponse;
    }

    /**
     * Test of Send SMS API with correct parameters
     */
    @Test
    public void testSendSMSAPI1() {
        System.out.println("Test of Send SMS API with correct parameters");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 201;
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        SendSMSResponse objSendSMSResponse = gson.fromJson(objDispatch.getResponseJson().toString(), SendSMSResponse.class);
        String expectedJsonResponse = createSampleResponse(objSendSMSRequest, objSendSMSResponse);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse.toString(), objDispatch.getResponseJson().toString(), false);
    }
    
    /**
     * Test of Send SMS API with one MSISDN in address array
     */
    @Test
    public void testSendSMSAPI2() {
        System.out.println("Test of Send SMS API with one MSISDN in address array");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        List<String> addresses = new ArrayList<String>();
        addresses.add("tel:+94770000976");
        objSendSMSRequest.getOutboundSMSMessageRequest().setAddress(addresses);
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 201;
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        SendSMSResponse objSendSMSResponse = gson.fromJson(objDispatch.getResponseJson().toString(), SendSMSResponse.class);
        String expectedJsonResponse = createSampleResponse(objSendSMSRequest, objSendSMSResponse);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        JSONAssert.assertEquals(expectedJsonResponse.toString(), objDispatch.getResponseJson().toString(), false);
    }

    /**
     * Test of Send SMS API without mandatory parameters
     */
    @Test
    public void testSendSMSAPI3() {
        System.out.println("Test of Send SMS API without mandatory parameters");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        objSendSMSRequest.getOutboundSMSMessageRequest().setSenderAddress(null);
        objSendSMSRequest.getOutboundSMSMessageRequest().setAddress(null);
        objSendSMSRequest.getOutboundSMSMessageRequest().getOutboundSMSTextMessage().setMessage(null);
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0002\",\n"
                + "    \"text\" : \"Invalid input value for message part %1\",\n"
                + "    \"variables\" : [ \"Missing mandatory parameter: senderAddress,address,message\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        System.out.println(objDispatch.getResponseJson().toString());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    
    /**
     * Test of Send SMS API without tel in senderAddress
     */
    @Test
    public void testSendSMSAPI4() {
        System.out.println("Test of Send SMS API without tel in senderAddress");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        objSendSMSRequest.getOutboundSMSMessageRequest().setSenderAddress("+94773214589");
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0004\",\n"
                + "    \"text\" : \"No valid addresses provided in message part %1\",\n"
                + "    \"variables\" : [ \""+objSendSMSRequest.getOutboundSMSMessageRequest().getSenderAddress()+"\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        System.out.println(objDispatch.getResponseJson().toString());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    
     /**
     * Test of Send SMS API without tel in address
     */
    @Test
    public void testSendSMSAPI5() {
        System.out.println("Test of Send SMS API without tel in address");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        List<String> addresses = new ArrayList<String>();
        addresses.add("+94770000976");
        objSendSMSRequest.getOutboundSMSMessageRequest().setAddress(addresses);
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0004\",\n"
                + "    \"text\" : \"No valid addresses provided in message part %1\",\n"
                + "    \"variables\" : [ \""+addresses.get(0)+"\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        System.out.println(objDispatch.getResponseJson().toString());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    
    /**
     * Test of Send SMS API for un-provisioned senderAddress
     */
    @Test
    public void testSendSMSAPI6() {
        System.out.println("Test of Send SMS API for un-provisioned senderAddress");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        objSendSMSRequest.getOutboundSMSMessageRequest().setSenderAddress("tel:+94770000001"); //set un-provisioned senderAddress
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0001\",\n"
                + "    \"text\" : \"A service error occurred. Error code is %1\",\n"
                + "    \"variables\" : [ \""+objSendSMSRequest.getOutboundSMSMessageRequest().getSenderAddress()+" Not Provisioned\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        System.out.println(objDispatch.getResponseJson().toString());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
    
    /**
     * Test of Send SMS API for un-provisioned address
     */
    @Test
    public void testSendSMSAPI7() {
        System.out.println("Test of Send SMS API for un-provisioned address");

        String sampleRequestJson = FileUtil.ReadFullyIntoVar("./jsons/sendsms.json");
        Gson gson = new Gson();
        SendSMSRequest objSendSMSRequest = gson.fromJson(sampleRequestJson, SendSMSRequest.class);
        List<String> addresses = new ArrayList<String>();
        addresses.add("tel:+94770000900"); //set un-provisioned address
        objSendSMSRequest.getOutboundSMSMessageRequest().setAddress(addresses);
        String requestJson = gson.toJson(objSendSMSRequest);

        int expectedStatus = 400;
        String expectedJsonResponse = "{\"requestError\":{\n"
                + "  \"serviceException\" : {\n"
                + "    \"messageId\" : \"SVC0001\",\n"
                + "    \"text\" : \"A service error occurred. Error code is %1\",\n"
                + "    \"variables\" : [ \""+addresses.get(0)+" Not Whitelisted\" ]\n"
                + "  }\n"
                + "}}";
        DispatcherObj objDispatch = new Httpposternew().makeRequest(sendSmsURL, requestJson, apimgr_user, apimgr_auth);
        assertEquals(expectedStatus, objDispatch.getResponseStatus());
        System.out.println(objDispatch.getResponseJson().toString());
        JSONAssert.assertEquals(expectedJsonResponse, objDispatch.getResponseJson().toString(), false);
    }
}
