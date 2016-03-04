/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.cg.payment;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import lk.dialog.cg.payment.provision.Provisionreq;
import lk.dialog.cg.payment.provision.Provisionservice;
import lk.dialog.cg.service.PaymentService;
import lk.dialog.cg.service.pool.PaymentSessionPool;
import lk.dialog.cg.ws.messages.jaws.ChargedToBillResponse;
import lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse;
import lk.dialog.utils.Base64Coder;
import lk.dialog.utils.Errorreturn;
import lk.dialog.utils.FileUtil;
import lk.dialog.utils.NumberGenerator;
import lk.dialog.utils.RequestError;
import org.apache.log4j.Logger;
//import org.codehaus.jettison.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author roshan_06809
 */
@Path("/")
public class Transactions {

    private static final Logger LOG = Logger.getLogger(Transactions.class.getName());
    @Context
    private UriInfo context;
    //private String ivrurl = FileUtil.getApplicationProperty("ivr.url}");
    //private String ivragent = FileUtil.getApplicationProperty("ivr.agentid");
    private String resourceURL = FileUtil.getApplicationProperty("resourceURL");
    //int initialConnections = Integer.parseInt(FileUtil.getApplicationProperty("initconnections"));
    //int maxConnections = Integer.parseInt(FileUtil.getApplicationProperty("maxConnections"));

    /**
     * Creates a new instance of TransactionsResource
     */
    public Transactions() {
    }

    @POST
    @Path("{senderAddress}/transactions/amount/special")
    @Consumes("application/json")
    @Produces("application/json")
    public Response amountspecial(@PathParam("senderAddress") String senderAddress, String content, @HeaderParam("x-jwt-assertion") String jwttoken) throws Exception {

        PaymentSessionPool sessionpool = null;
        PaymentService paymentservice = null;
        String jsonreturn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            //temp
            if (jwttoken == null) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt= jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            System.out.println(jwtbody);
            
            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            
            String application = new Provisionservice().queryapp(appid);
            if (application.isEmpty()) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
                        
            String[] appdata = application.split(":");
            String reasoncode = appdata[0];
            Double appamount = Double.parseDouble(appdata[1]);
            
            org.json.JSONObject jsonObj = new org.json.JSONObject(content);

            org.json.JSONObject jsonDesc = jsonObj.getJSONObject("amountTransaction").getJSONObject("paymentAmount").getJSONObject("chargingInformation");
            String addr = jsonDesc.getString("description");
            if (!addr.startsWith("[")) {
                org.json.JSONArray arradd = new org.json.JSONArray();
                arradd.put(addr);
                jsonDesc.put("description", arradd);
            }

            content = jsonObj.toString();

            boolean chargestatus = false;
            PaymentRequest chargerequest = new Gson().fromJson(content, PaymentRequest.class);
            LOG.info("Charge request: " + content);

            //validate payment cap
            if (appamount <= ( Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount())) ) {
                throw new AxiataException("POL1001", new String[]{"%1 – the time period for which the charging limit has been reached"});
            }
            
            double chargeamt = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount());
            double taxAmount = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getTaxAmount());
            String billdescription = (chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getDescription()).get(0);
            boolean taxable = true;
            
            String strTaxable = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getTaxable();
            if ( (strTaxable != null) && (strTaxable.equalsIgnoreCase("N")) ) {
                taxable = false;            
            }
            
            String msisdn = chargerequest.getAmountTransaction().getEndUserId().replace("tel:+94", "");
            //ChargedToBillResponse responsetxt = paymentservice.chargeUser(msisdn, chargeamt, true);  //addtoBill(msisdn, chargeamt, reasoncode);
            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);
            paymentservice = sessionpool.getPaymentsession();
            
            ChargeBySpecialDebitResponse responsetxt = paymentservice.chargeSpecial(msisdn, chargeamt, true,taxable,billdescription);
            if (responsetxt == null) {
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }

            String result = responsetxt.getTransResult();
            if (result != null && result.equals("0")) {
                chargestatus = true;
            }
            
            //taxAmount = responsetxt.  2014/03/18 -Not posible to retrive the taxed amount from cg at the moment hence texamount will be always 0
                        
            if (!chargestatus) {
                chargerequest.getAmountTransaction().setTransactionOperationStatus("Failed");
                chargeamt = 0;
                taxAmount = 0;
            }            

            chargerequest.getAmountTransaction().getPaymentAmount().setTotalAmountCharged(String.valueOf(chargeamt));
            chargerequest.getAmountTransaction().getPaymentAmount().setTotalAmountCharged(String.valueOf(chargeamt));
            String refno = "DLG-" + NumberGenerator.next();
            chargerequest.getAmountTransaction().setServerReferenceCode((refno));

            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            chargerequest.getAmountTransaction().setResourceURL(resourceURL + senderAddress + "/transactions/amount/" + NumberGenerator.current());

            jsonreturn = new Gson().toJson(chargerequest);
            //return Response.created(context.getAbsolutePath()).build();

            LOG.info("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();

        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            return Response.status(400).entity(jsonreturn).build();
        } catch (Exception e) {
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Charging operation failed"})));
            return Response.status(400).entity(jsonreturn).build();
        } finally {
            if (sessionpool != null) {
                sessionpool.free(paymentservice);
            }
        }
    }
    
    /**
     * POST method for creating an instance of TransactionResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Path("{senderAddress}/transactions/amount")
    @Consumes("application/json")
    @Produces("application/json")
    public Response amount(@PathParam("senderAddress") String senderAddress, String content, @HeaderParam("x-jwt-assertion") String jwttoken) throws Exception {

        PaymentSessionPool sessionpool = null;
        PaymentService paymentservice = null;
        String jsonreturn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            //temp
            if (jwttoken == null) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt= jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            System.out.println(jwtbody);
            
            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            
            String application = new Provisionservice().queryapp(appid);
            if (application.isEmpty()) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
                        
            String[] appdata = application.split(":");
            String reasoncode = appdata[0];
            Double appamount = Double.parseDouble(appdata[1]);
            
            org.json.JSONObject jsonObj = new org.json.JSONObject(content);

            org.json.JSONObject jsonDesc = jsonObj.getJSONObject("amountTransaction").getJSONObject("paymentAmount").getJSONObject("chargingInformation");
            String addr = jsonDesc.getString("description");
            if (!addr.startsWith("[")) {
                org.json.JSONArray arradd = new org.json.JSONArray();
                arradd.put(addr);
                jsonDesc.put("description", arradd);
            }

            content = jsonObj.toString();

            boolean chargestatus = false;
            PaymentRequest chargerequest = new Gson().fromJson(content, PaymentRequest.class);
            LOG.info("Charge request: " + content);

            //validate payment cap
            if (appamount <= ( Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount())) ) {
                throw new AxiataException("POL1001", new String[]{"%1 - the time period for which the charging limit has been reached"});
            }
            
            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);
            paymentservice = sessionpool.getPaymentsession();

            double chargeamt = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount());
            String msisdn = chargerequest.getAmountTransaction().getEndUserId().replace("tel:+94", "");
            ChargedToBillResponse responsetxt = paymentservice.chargeUser(msisdn, chargeamt, true);  //addtoBill(msisdn, chargeamt, reasoncode);

            if (responsetxt == null) {
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }

            String result = responsetxt.getTransResult();
            if (result != null && result.equals("0")) {
                chargestatus = true;
            }

            if (!chargestatus) {
                chargerequest.getAmountTransaction().setTransactionOperationStatus("Failed");
                chargeamt = 0;
            }

            chargerequest.getAmountTransaction().getPaymentAmount().setTotalAmountCharged(String.valueOf(chargeamt));
            String refno = "DLG-" + NumberGenerator.next();
            chargerequest.getAmountTransaction().setServerReferenceCode((refno));

            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            chargerequest.getAmountTransaction().setResourceURL(resourceURL + senderAddress + "/transactions/amount/" + NumberGenerator.current());

            jsonreturn = new Gson().toJson(chargerequest);
            //return Response.created(context.getAbsolutePath()).build();

            LOG.info("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();

        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            return Response.status(400).entity(jsonreturn).build();
        } catch (Exception e) {
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Charging operation failed"})));
            return Response.status(400).entity(jsonreturn).build();
        } finally {
            if (sessionpool != null) {
                sessionpool.free(paymentservice);
            }
        }
    }
//    
//    public ChargedToBillResponse addtoBill(String msisdn, double amount,String rtcode) {
//        PaymentSessionPool sessionpool = null;
//         
//        try {
//            sessionpool = getCGPayment(configpath, rtcode, true);
//            PaymentService paymentservice = sessionpool.getPaymentsession();
//            ChargedToBillResponse chargebillresponse = paymentservice.chargeUser(msisdn, amount, true);
//            sessionpool.free(paymentservice);
//            return chargebillresponse;
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.error("[WSRequestService ], sendCAASDebit, " + e.getMessage());
//            return null;
//        }
//    }

    /*  public Double sendCAASBalance(String msisdn) {
        
     try {
     return CGPayment().getAvailableBalance(msisdn);
     } catch (Exception e) {
     LOG.error("[WSRequestService ], sendCAASBalance, " + e.getMessage());
     return null;
     }
     }    
     */
    @POST
    @Path("registration")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registerapp(String content) throws Exception {

        String jsonreturn = null;

        try {

            Provisionreq provisionreq = new Gson().fromJson(content, Provisionreq.class);
            LOG.info("Charge request: " + content);

            /*if (responsetxt == null) {
             throw new AxiataException("POL1009", new String[]{"Payment Service"});
             }*/
            new Provisionservice().provisionapp(provisionreq);

        } catch (AxiataException e) {
            LOG.error("error provision user: ");
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            return Response.status(400).entity(jsonreturn).build();
        } catch (Exception e) {
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("POL0299", new String[]{e.getMessage()})));
            return Response.status(400).entity(jsonreturn).build();
        }

        LOG.info("json response: " + jsonreturn);
        return Response.status(201).build();
    }

    @GET
    @Path("{senderAddress}/transactions/amount/balance")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response querybalance(@PathParam("senderAddress") String senderAddress,@HeaderParam("x-jwt-assertion") String jwttoken) {
        PaymentSessionPool sessionpool = null;
        PaymentService paymentservice = null;
        String jsonreturn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            //temp
            if (jwttoken == null) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt= jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            System.out.println(jwtbody);
            
            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            
            String application = new Provisionservice().queryapp(appid);
            if (application.isEmpty()) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
                        
            String[] appdata = application.split(":");
            String reasoncode = appdata[0];
            
            boolean chargestatus = false;
            
            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);
            paymentservice = sessionpool.getPaymentsession();

            double userbalance = paymentservice.getAvailableBalance(senderAddress.replace("tel:+94", ""));  //addtoBill(msisdn, chargeamt, reasoncode);
            if (Double.isNaN(userbalance)) {
                throw new Exception("Query balance operation failed");
            }
            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            jsonreturn =
              "{"
             + "\"chargeableBalance\":\""+userbalance + "\","
             + "\"statusCode\":\"S1000\","
             + "\"statusDetail\":\"Success\","
             + "\"accountStatus\":\"Active\""                        
             + "}";

            //return Response.created(context.getAbsolutePath()).build();

            LOG.info("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();

        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            return Response.status(400).entity(jsonreturn).build();
        } catch (Exception e) {
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Query balance operation failed"})));
            return Response.status(400).entity(jsonreturn).build();
        } finally {
            if (sessionpool != null) {
                sessionpool.free(paymentservice);
            }
        }
    }
    
        
    
}
