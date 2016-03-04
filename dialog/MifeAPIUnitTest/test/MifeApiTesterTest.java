/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import mifeapitest.api.requestbeans.ChargeRequest;
import mifeapitest.services.DispatcherObj;
import mifeapitest.services.Httpposternew;
import mifeapitest.util.FileUtil;
import mifeapitest.util.PaymentFunctions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.*;
import static org.junit.Assert.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;


/**
 *
 * @author User
 */
public class MifeApiTesterTest {

    static String apimgr_url, paymentUrl, retrieveSmsUrl, apiUser, auth;
//    String paymentUrl = "http://localhost:8081/mifeapiserver/AmountChargeService/payment/1/transactions/amount";
//    String retrieveSmsUrl = "http://localhost:8081/mifeapiserver/RetrieveSMSService/smsmessaging/1/inbound/registrations/1234/messages/";
//    String apiUser = "admin";
//    String auth = "ca97d23d334e17226886973562fcda0";

    public MifeApiTesterTest() {
        loadmifeconfig();
        System.out.println();
        System.out.println("MIFE API Tester");
        System.out.println("========================");
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
            apiUser = props.getProperty("apimgr.user");
            auth = props.getProperty("apimgr.auth");
            paymentUrl = apimgr_url+props.getProperty("jsonrequest.url.paymentcharge");
            retrieveSmsUrl = apimgr_url+props.getProperty("jsonrequest.url.retrievesms");
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
    /**
     * Test of test method, of class MifeApiTester.
     */
//    @Test
//    public void testTest() {
//        System.out.println("test");
//        String arv = "";
//        MifeApiTester instance = new MifeApiTester();
//        instance.test(arv);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    @Test
    public void successRequest() throws Exception {
        System.out.println("Testing Payment: Successfull request");
        String paymentJson = FileUtil.ReadFullyIntoVar("./jsons/paymentcharge.json");

        Gson gson = new Gson();
        ChargeRequest r = gson.fromJson(paymentJson, ChargeRequest.class);

        String requestJson = gson.toJson(r);
        System.out.println(paymentUrl);
        DispatcherObj paymentChargeDis = new Httpposternew().makeRequest(paymentUrl, requestJson, apiUser, auth);
        //System.out.println(paymentChargeDis.getResponseJson());
        //ChargeResponse responseObj = gson.fromJson(paymentChargeDis.getResponseJson(), ChargeResponse.class);

        String dumyResponse = PaymentFunctions.getPaymentResponse(r);
        String responseJson = paymentChargeDis.getResponseJson();

        JSONAssert.assertEquals(dumyResponse, responseJson, false);
    }

    @Test
    public void testNonProvisionedNumberRequest() {
        System.out.println("Testing Payment: Non provisioned EndUserId");
        String responseErrorId = "";
        try {
            String paymentJson = FileUtil.ReadFullyIntoVar("./jsons/paymentcharge.json");

            Gson gson = new Gson();
            ChargeRequest r = gson.fromJson(paymentJson, ChargeRequest.class);
            r.getAmountTransaction().setEndUserId("tel:+94777417485");

            String requestJson = gson.toJson(r);
            DispatcherObj paymentChargeDis = new Httpposternew().makeRequest(paymentUrl, requestJson, apiUser, auth);

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(paymentChargeDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("policyException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (Exception e) {
            System.out.println("testNonProvisionedNumberRequest: " + e);
        }
        assertEquals("POL1009", responseErrorId);
    }

    @Test
    public void testInvalidNumberFormat() {
        System.out.println("Testing Payment: Invalid EndUserId format");
        String responseErrorId = "";
        try {
            String paymentJson = FileUtil.ReadFullyIntoVar("./jsons/paymentcharge.json");

            Gson gson = new Gson();
            ChargeRequest r = gson.fromJson(paymentJson, ChargeRequest.class);
            r.getAmountTransaction().setEndUserId("94777417485");

            String requestJson = gson.toJson(r);
            DispatcherObj paymentChargeDis = new Httpposternew().makeRequest(paymentUrl, requestJson, apiUser, auth);
            System.out.println(paymentChargeDis.getResponseJson());

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(paymentChargeDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("serviceException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (Exception e) {
            System.out.println("testInvalidNumberFormat: " + e);
        }

        assertEquals("SVC0271", responseErrorId);
    }

    @Test
    public void testMandatoryParamMissing() {
        System.out.println("Testing Payment: Request without mandatory parameter ReferenceCode");
        String responseErrorId = "";
        try {
            String paymentJson = FileUtil.ReadFullyIntoVar("./jsons/paymentcharge.json");

            Gson gson = new Gson();
            ChargeRequest r = gson.fromJson(paymentJson, ChargeRequest.class);
            r.getAmountTransaction().setReferenceCode("");

            String requestJson = gson.toJson(r);
            DispatcherObj paymentChargeDis = new Httpposternew().makeRequest(paymentUrl, requestJson, apiUser, auth);
            System.out.println(paymentChargeDis.getResponseJson());

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(paymentChargeDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("serviceException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (JsonSyntaxException | ParseException e) {
            System.out.println("testMandatoryParamMissing: " + e);
        }
        assertEquals("SVC0002", responseErrorId);
    }

    @Test
    public void testLargerAmountCharge() {
        System.out.println("Testing Payment: Charging amount > Available Balance");
        String responseErrorId = "";
        try {
            String paymentJson = FileUtil.ReadFullyIntoVar("./jsons/paymentcharge.json");

            Gson gson = new Gson();
            ChargeRequest r = gson.fromJson(paymentJson, ChargeRequest.class);
            r.getAmountTransaction().getPaymentAmount().getChargingInformation().setAmount("600000.00");

            String requestJson = gson.toJson(r);
            DispatcherObj paymentChargeDis = new Httpposternew().makeRequest(paymentUrl, requestJson, apiUser, auth);
            System.out.println(paymentChargeDis.getResponseJson());

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(paymentChargeDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("policyException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (JsonSyntaxException | ParseException e) {
            System.out.println("testLargerAmountCharge: " + e);
        }
        assertEquals("POL0254", responseErrorId);
    }

    @Test
    public void validRetrieveSmsRequest() {
        System.out.println("Testing SMS: Successfull RetrieveSMS request");
        retrieveSmsUrl = retrieveSmsUrl + "?registrationId=3456&maxBatchSize=10";
        boolean parsesuccess = false;
        try {
            DispatcherObj retrieveSmsDis = new Httpposternew().makeGetRequest(paymentUrl, "", apiUser, auth);
            JSONParser jp = new JSONParser();
            JSONObject r = (JSONObject) jp.parse(retrieveSmsDis.getResponseJson()); //gson.fromJson(RetrieveSmsDis.getResponseJson(), RetrieveSmsResponse.class);
            parsesuccess = true;
        } catch (JsonSyntaxException | ParseException e) {
            System.out.println("validRetrieveSmsRequest: " + e);
        }
        assertEquals(true, parsesuccess);
    }

    @Test
    public void nonProvisionedRegId() {
        System.out.println("Testing SMS: RetrieveSMS with non-provisioned RegId");
        retrieveSmsUrl = retrieveSmsUrl + "?registrationId=3456&maxBatchSize=10";
        String responseErrorId = "";
        try {
            DispatcherObj retrieveSmsDis = new Httpposternew().makeGetRequest(paymentUrl, "", apiUser, auth);
            System.out.println(retrieveSmsDis.getResponseJson());

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(retrieveSmsDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("serviceException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (JsonSyntaxException | ParseException e) {
            System.out.println("nonProvisionedRegId: " + e);
        }
        assertEquals("SVC0003", responseErrorId);
    }

    @Test
    public void missingRetrieveMandatoryParam() {
        System.out.println("Testing SMS: Retrieve SMS without mandatory URI parameter RegistrationID");
        retrieveSmsUrl = retrieveSmsUrl + "?maxBatchSize=10";
        String responseErrorId = "";
        try {
            DispatcherObj retrieveSmsDis = new Httpposternew().makeGetRequest(paymentUrl, "", apiUser, auth);
            System.out.println(retrieveSmsDis.getResponseJson());

            JSONParser jp = new JSONParser();
            JSONObject mainJSONObject = (JSONObject) jp.parse(retrieveSmsDis.getResponseJson());
            JSONObject errorObj = (JSONObject) mainJSONObject.get("requestError");
            JSONObject serviceErrorObj = (JSONObject) errorObj.get("serviceException");
            responseErrorId = serviceErrorObj.get("messageId").toString();

        } catch (JsonSyntaxException | ParseException e) {
            System.out.println("missingRetrieveMandatoryParam: " + e);
        }
        assertEquals("SVC0002", responseErrorId);
    }
}
