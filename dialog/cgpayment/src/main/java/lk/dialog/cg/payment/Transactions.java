/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.cg.payment;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.dialog.cg.payment.balanceInquiry.BalanceInquiry;
import lk.dialog.cg.payment.provision.Provisionreq;
import lk.dialog.cg.payment.provision.Provisionservice;
import lk.dialog.cg.service.BalResponse;
import lk.dialog.cg.service.PaymentService;
import lk.dialog.cg.service.pool.PaymentSessionPool;
import lk.dialog.cg.ws.messages.jaws.ChargeBySpecialDebitResponse;
import lk.dialog.cg.ws.messages.jaws.ChargedToBillResponse;
import lk.dialog.ideabiz.logger.DatabaseLibrary;
import lk.dialog.ideabiz.logger.DirectLogger;
import lk.dialog.ideabiz.logger.model.impl.BalanceCheck;
import lk.dialog.ideabiz.logger.model.impl.Payment;
import lk.dialog.utils.Base64Coder;
import lk.dialog.utils.Errorreturn;
import lk.dialog.utils.FileUtil;
import lk.dialog.utils.RequestError;
import org.apache.log4j.Logger;
import org.json.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import static lk.dialog.utils.NumberFormater.getLocalNumber;

//import org.codehaus.jettison.json.JSONObject;

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


    /*
    Logger
     */
    DirectLogger directLogger = null;
    DatabaseLibrary databaseLibrary;

    /**
     * Creates a new instance of TransactionsResource
     */
    public Transactions() {
        directLogger = new DirectLogger();
    }

    @POST
    @Path("{senderAddress}/transactions/amount/special")
    @Consumes("application/json")
    @Produces("application/json")
    public Response amountspecial(@PathParam("senderAddress") String senderAddress, String content, @HeaderParam("x-jwt-assertion") String jwttoken) throws Exception {

        PaymentSessionPool sessionpool = null;
        PaymentService paymentservice = null;
        String jsonreturn = null;

        senderAddress = getLocalNumber(senderAddress, "94");
        LOG.debug("Amount Special Request ------------------------->");
        LOG.info("SPECIAL : URL " + senderAddress);
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();

            if (jwttoken == null) {
                LOG.debug("Amount Special Request error : jwttoken is null");
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt = jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            LOG.debug(jwtbody);

            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            Integer appIdInt = null;
            try {
                appIdInt = Integer.parseInt(appid);
            } catch (Exception et) {
            }
            String version = jwtobj.getString("http://wso2.org/claims/version");
            String api = jwtobj.getString("http://wso2.org/claims/apicontext");

            String application = new Provisionservice().queryapp(appid);
            // String application = new Provisionservice().queryapp("9");

            LOG.info("SPECIAL App " + appid);
            if (application.isEmpty()) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }

            String[] appdata = application.split(":");
            String reasoncode = appdata[0];
            Double appamount = Double.parseDouble(appdata[1]);

            LOG.info("SPECIAL Reasoncode " + reasoncode);

            PaymentRequest chargerequest = new Gson().fromJson(content, PaymentRequest.class);

            org.json.JSONObject jsonObj = new org.json.JSONObject(content);
            String transactionOperationStatus = chargerequest.getAmountTransaction().getTransactionOperationStatus();
            String referenceCode = chargerequest.getAmountTransaction().getReferenceCode();
            String currency = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getCurrency();
            String channel = chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getChannel();
            Double taxAmount = 0.0;
            try {
                taxAmount = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getTaxAmount());
            } catch (Exception et) {
            }
            String clientCorrelator = chargerequest.getAmountTransaction().getClientCorrelator();
            String description = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getDescription();
            String msisdn = chargerequest.getAmountTransaction().getEndUserId();
            msisdn = getLocalNumber(msisdn, "94");
            String refno = DialogRefID.getUniqueID();   //"DLG-" + NumberGenerator.next();

            boolean chargestatus = false;

            LOG.debug("Charge request: " + content);


            double chargeamt = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount());

            /* special only*/
            boolean taxable = true;

            String strTaxable = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getTaxable();
            if ((strTaxable != null) && (strTaxable.equalsIgnoreCase("N") || strTaxable.equalsIgnoreCase("NO") || strTaxable.equalsIgnoreCase("false"))) {
                taxable = false;
            } else if (taxAmount == 0) {
                LOG.info("SPECIAL TAX =" + taxAmount + " so taxable set to N");
                taxable = false;
            }
            /**/

            LOG.debug("SPECIAL : MSISDN BODY " + msisdn);
            if (getLocalNumber(msisdn, "94").equalsIgnoreCase(getLocalNumber(senderAddress, "94"))) {
                LOG.warn("SPECIAL URL SenderAddress not match with BODY MSISDN");
            }

            /*
             Get Reason code from amountTransaction->paymentAmount->chargingMetaData->serviceID
             */
            try {
                if (chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID() != null && !chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID().isEmpty()) {
                    reasoncode = chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID();
                    LOG.info("SPECIAL getting reason code from JSON : " + reasoncode);
                }
            } catch (Exception xx) {
                LOG.error("SPECIAL reason code form DB used " + xx.getMessage());
            }

            //validate payment cap
            if (appamount <= (chargeamt)) {
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "SPECIAL", reasoncode, refno, referenceCode, null, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "ERROR", "LIMIT_EXCEEDED"));
                throw new AxiataException("POL1001", new String[]{"%1 – the time period for which the charging limit has been reached"});
            }


            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);

            if (sessionpool == null) {
                LOG.debug("SPECIAL session pool empty for " + reasoncode);
            }

            paymentservice = sessionpool.getPaymentsession();

            ChargeBySpecialDebitResponse responsetxt = paymentservice.chargeSpecial(msisdn, chargeamt, true, taxable, description);
            if (responsetxt == null) {
                LOG.info("PAYMENTSTATUS:SP|" + appid + "|" + msisdn + "|" + chargeamt + "|" + reasoncode + "|ERROR");
                directLogger.addLog(new Payment(version, appIdInt, msisdn,"SPECIAL", reasoncode, refno, referenceCode, null, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "ERROR", "NOT_APPLIED"));
                LOG.debug("Amount Special Request error : responsetxt is null");
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }


            String result = responsetxt.getTransResult();
            LOG.info("PAYMENTSTATUS:SP|" + appid + "|" + msisdn + "|" + chargeamt + "|" + reasoncode + "|DONE|" + result + "|SVRREF:" + refno + "|CGREF:" + responsetxt.getTransactionID() + "|REQREF:" + chargerequest.getAmountTransaction().getReferenceCode() + "|DES:" + description);

            String cgRef = null;
            if (result != null)
                cgRef = responsetxt.getTransactionID();

            if (result != null && result.equals("0")) {
                chargestatus = true;
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "SPECIAL", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "SUCCESS", ""));

            } else if (result != null && result.equals("8")) {
                directLogger.addLog(new Payment(version, appIdInt, msisdn,"SPECIAL", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "FAILED", "NO_CREDIT"));
                throw new AxiataException("POL1000", new String[]{"User has insufficient credit for transaction"});
            } else {
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "SPECIAL", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "FAILED", "NOT_APPLIED"));
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }

            //taxAmount = responsetxt.  2014/03/18 -Not posible to retrive the taxed amount from cg at the moment hence texamount will be always 0
            if (!chargestatus) {
                chargerequest.getAmountTransaction().setTransactionOperationStatus("Failed");
                chargeamt = 0;
                taxAmount = 0.0;
            }

            chargerequest.getAmountTransaction().getPaymentAmount().setTotalAmountCharged(String.valueOf(chargeamt));
            chargerequest.getAmountTransaction().setServerReferenceCode((refno));

            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            chargerequest.getAmountTransaction().setResourceURL(resourceURL + senderAddress + "/transactions/amount/" + refno);

            jsonreturn = new Gson().toJson(chargerequest);
            //return Response.created(context.getAbsolutePath()).build();

            LOG.debug("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();
        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            com.axiata.dialog.oneapi.validation.RequestError requesterror = new com.axiata.dialog.oneapi.validation.RequestError();
            String retstr = "";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(401).entity("{\"requestError\":" + retstr + "}").build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity("{\"requestError\":" + retstr + "}").build();
            }
        } catch (Exception e) {
            LOG.error("amount special request error : " + e.getMessage());
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Charging operation failed"})));
            return Response.status(400).entity("{\"requestError\":" + jsonreturn + "}").build();
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

        senderAddress = getLocalNumber(senderAddress, "94");
        LOG.debug("Amount Request ------------------------->");
        LOG.info("DEBIT : URL " + senderAddress);

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            if (jwttoken == null) {
                LOG.debug("Amount Request error : jwttoken is null");
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt = jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            LOG.debug(jwtbody);

            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            Integer appIdInt = null;
            try {
                appIdInt = Integer.parseInt(appid);
            } catch (Exception et) {
            }
            String version = jwtobj.getString("http://wso2.org/claims/version");
            String api = jwtobj.getString("http://wso2.org/claims/apicontext");

            String application = new Provisionservice().queryapp(appid);

            LOG.info("DEBIT app " + appid);
            if (application.isEmpty()) {
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }

            String[] appdata = application.split(":");
            String reasoncode = appdata[0];
            Double appamount = Double.parseDouble(appdata[1]);

            LOG.info("DEBIT Reasoncode " + reasoncode);

            PaymentRequest chargerequest = new Gson().fromJson(content, PaymentRequest.class);

            org.json.JSONObject jsonObj = new org.json.JSONObject(content);
            String transactionOperationStatus = chargerequest.getAmountTransaction().getTransactionOperationStatus();
            String referenceCode = chargerequest.getAmountTransaction().getReferenceCode();
            String currency = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getCurrency();
            String channel = chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getChannel();
            Double taxAmount = 0.0;
            try {
                taxAmount = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getTaxAmount());
            } catch (Exception et) {
            }
            String clientCorrelator = chargerequest.getAmountTransaction().getClientCorrelator();
            String description = chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getDescription();
            String msisdn = chargerequest.getAmountTransaction().getEndUserId();
            msisdn = getLocalNumber(msisdn, "94");
            String refno = DialogRefID.getUniqueID();   //"DLG-" + NumberGenerator.next();

            boolean chargestatus = false;

            LOG.debug("Charge request: " + content);

            double chargeamt = Double.parseDouble(chargerequest.getAmountTransaction().getPaymentAmount().getChargingInformation().getAmount());

            LOG.debug("DEBIT : MSISDN BODY " + msisdn);

            if (getLocalNumber(msisdn, "94").equalsIgnoreCase(getLocalNumber(senderAddress, "94"))) {
                LOG.warn("DEBIT URL SenderAddress not match with BODY MSISDN");
            }

             /*
             Get Reason code from amountTransaction->paymentAmount->chargingMetaData->serviceID
             */
            try {
                if (chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID() != null && !chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID().isEmpty()) {
                    reasoncode = chargerequest.getAmountTransaction().getPaymentAmount().getChargingMetaData().getServiceID();
                    LOG.info("DEBIT getting reason code from JSON : " + reasoncode);
                }
            } catch (Exception xx) {
                LOG.error("DEBIT reason code form DB used " + xx.getMessage());
            }

            //validate payment cap
            if (appamount <= (chargeamt)) {
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "DEBIT", reasoncode, refno, referenceCode, null, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "ERROR", "LIMIT_EXCEEDED"));
                throw new AxiataException("POL1001", new String[]{"%1 – the time period for which the charging limit has been reached"});
            }


            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);

            if (sessionpool == null) {
                LOG.debug("DEBIT session pool empty for " + reasoncode);
            }

            paymentservice = sessionpool.getPaymentsession();

            ChargedToBillResponse responsetxt = paymentservice.chargeUser(msisdn, chargeamt, true);  //addtoBill(msisdn, chargeamt, reasoncode);
            //number | amount | reason | status
            if (responsetxt == null) {
                LOG.info("PAYMENTSTATUS:DEBIT|" + appid + "|" + msisdn + "|" + chargeamt + "|" + reasoncode + "|ERROR|");
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "DEBIT", reasoncode, refno, referenceCode, null, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "ERROR", "NOT_APPLIED"));
                LOG.debug("Amount Request error : responsetxt is empty");
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }


            String result = responsetxt.getTransResult();
            LOG.info("PAYMENTSTATUS:DEBIT|" + appid + "|" + msisdn + "|" + chargeamt + "|" + reasoncode + "|DONE|" + result + "|SVRREF:" + refno + "|CGREF:" + responsetxt.getTransactionID() + "|REQREF:" + chargerequest.getAmountTransaction().getReferenceCode());

            String cgRef = null;
            if (result != null)
                cgRef = responsetxt.getTransactionID();

            if (result != null && result.equals("0")) {
                chargestatus = true;
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "DEBIT", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "SUCCESS", ""));
            } else if (result != null && result.equals("8")) {
                directLogger.addLog(new Payment(version, appIdInt, msisdn,"DEBIT", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "FAILED", "NO_CREDIT"));
                throw new AxiataException("POL1000", new String[]{"User has insufficient credit for transaction"});
            } else {
                directLogger.addLog(new Payment(version, appIdInt,msisdn, "DEBIT", reasoncode, refno, referenceCode, cgRef, chargeamt, taxAmount, currency, description, channel, clientCorrelator, transactionOperationStatus, "FAILED", "NOT_APPLIED"));
                throw new AxiataException("SVC0270", new String[]{"Charging operation failed, the charge was not applied"});
            }

            chargerequest.getAmountTransaction().getPaymentAmount().setTotalAmountCharged(String.valueOf(chargeamt));
            chargerequest.getAmountTransaction().setServerReferenceCode((refno));

            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            chargerequest.getAmountTransaction().setResourceURL(resourceURL + senderAddress + "/transactions/amount/" + refno);

            jsonreturn = new Gson().toJson(chargerequest);
            //return Response.created(context.getAbsolutePath()).build();

            LOG.debug("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();

        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            //jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            //return Response.status(400).entity(jsonreturn).build();
            com.axiata.dialog.oneapi.validation.RequestError requesterror = new com.axiata.dialog.oneapi.validation.RequestError();
            String retstr = "";
            if (e.getErrcode().substring(0, 2).equals("PO")) {
                requesterror.setPolicyException(new PolicyException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(401).entity("{\"requestError\":" + retstr + "}").build();
            } else {
                requesterror.setServiceException(new ServiceException(e.getErrcode(), e.getErrmsg(), e.getErrvar()[0]));
                retstr = new Gson().toJson(requesterror);
                return Response.status(400).entity("{\"requestError\":" + retstr + "}").build();
            }
        } catch (Exception e) {
            LOG.error("amount request error : " + e.getMessage(), e);

            //jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Charging operation failed"})));
            //return Response.status(400).entity(jsonreturn).build();
            com.axiata.dialog.oneapi.validation.RequestError requesterror = new com.axiata.dialog.oneapi.validation.RequestError();
            requesterror.setServiceException(new ServiceException("SVC0001", "Internal Server Error", ""));
            jsonreturn = new Gson().toJson(requesterror);
            return Response.status(400).entity("{\"requestError\":" + jsonreturn + "}").build();
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
            LOG.error("Register App Request error : " + e.getMessage());
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("POL0299", new String[]{e.getMessage()})));
            return Response.status(400).entity(jsonreturn).build();
        }
        LOG.debug("json response: " + jsonreturn);
        return Response.status(201).build();
    }

    @GET
    @Path("{senderAddress}/transactions/amount/balance")
    //@Consumes("application/json")
    @Produces("application/json")
    public Response querybalance(@PathParam("senderAddress") String senderAddress, @HeaderParam("x-jwt-assertion") String jwttoken) {
        PaymentSessionPool sessionpool = null;
        PaymentService paymentservice = null;
        String jsonreturn = null;

        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            //temp
            if (jwttoken == null) {
                LOG.debug("Query Balance Request error : jwttoken is null");
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }
            String[] jwt = jwttoken.split("\\.");
            String jwtbody = Base64Coder.decodeString(jwt[1]);
            LOG.debug(jwtbody);

            JSONObject jwtobj = new JSONObject(jwtbody);
            String appid = jwtobj.getString("http://wso2.org/claims/applicationid");
            Integer appIdInt = Integer.parseInt(appid);
            String application = new Provisionservice().queryapp(appid);
            if (application.isEmpty()) {
                LOG.debug("Query Balance Request error : application is empty");
                throw new AxiataException("POL1009", new String[]{"Payment Service"});
            }

            String[] appdata = application.split(":");
            String reasoncode = appdata[0];

            boolean chargestatus = false;

            sessionpool = PaymentServer.getInstance().getCGPayment(reasoncode, true);
            paymentservice = sessionpool.getPaymentsession();

            senderAddress = senderAddress.replace("tel:+94", "");
            senderAddress = senderAddress.replace("tel:94", "");

            if (senderAddress.startsWith("94"))
                senderAddress = senderAddress.substring(2);
            if (senderAddress.startsWith("+94"))
                senderAddress = senderAddress.substring(3);

            BalResponse balresponse = paymentservice.getAvailableBalance(senderAddress);  //addtoBill(msisdn, chargeamt, reasoncode);
            double userbalance = balresponse.getAvailablebal();
            String acctype = (balresponse.getAccountType().equalsIgnoreCase("IN") ? "PREPAID" : balresponse.getAccountType());
            String accstat = balresponse.getAccountStatus().equalsIgnoreCase("0") ? "ACTIVE" : "NOT_ACTIVE";
            double creditLimit = balresponse.getCreditLimit();
            double outstanding = balresponse.getOutstanding();
            if (Double.isNaN(userbalance)) {
                directLogger.addLog(new BalanceCheck("2", appIdInt, senderAddress, "ERROR"));
                LOG.debug("Query Balance Request error : Query balance operation failed");
                throw new Exception("Query balance operation failed");
            }
            //sender address URL encode
            try {
                senderAddress = URLEncoder.encode(senderAddress, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                LOG.error("[Transactions ], amount, " + ex.getMessage());
            }

            String ref = DialogRefID.getUniqueID();

            BalanceInquiry balanceInquiry = new BalanceInquiry();
            balanceInquiry.setEndUserId(senderAddress);
            balanceInquiry.setReferenceCode(ref);
            balanceInquiry.getAccountInfo().setAccountStatus(accstat);
            balanceInquiry.getAccountInfo().setAccountType(acctype);
            balanceInquiry.getAccountInfo().setBalance(Math.round((creditLimit) * 100.0) / 100.0);
            balanceInquiry.getAccountInfo().setCreditLimit(Math.round((creditLimit) * 100.0) / 100.0);

            LOG.info("BALANCECHECK:" + appid + "|" + senderAddress + "|" + accstat + "|" + acctype + "|DONE|" + userbalance + "|SVRREF:" + ref + "|" + Math.round((creditLimit) * 100.0) / 100.0 + "|");
            directLogger.addLog(new BalanceCheck("2", appIdInt, senderAddress, "SUCCESS"));

//            jsonreturn =
//              "{"
//             + "\"chargeableBalance\":\""+userbalance + "\","
//             + "\"statusCode\":\"S1000\","
//             + "\"statusDetail\":\"Success\","
//             + "\"accountStatus\":\""+accstat+"\","
//             + "\"accountType\":\""+acctype+"\""                        
//             + "}";

            //return Response.created(context.getAbsolutePath()).build();
            jsonreturn = new Gson().toJson(balanceInquiry);
            LOG.debug("json response: " + jsonreturn);
            return Response.status(200).entity(jsonreturn).build();

        } catch (AxiataException e) {
            LOG.error("error charging user: " + senderAddress);
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn(e.getErrcode(), e.getErrvar())));
            return Response.status(400).entity(jsonreturn).build();
        } catch (Exception e) {
            LOG.error("query balance request error : " + e.getMessage());
            jsonreturn = new Gson().toJson(new RequestError(new Errorreturn("SVC0270", new String[]{"Query balance operation failed"})));
            return Response.status(400).entity(jsonreturn).build();
        } finally {
            if (sessionpool != null) {
                sessionpool.free(paymentservice);
            }
        }
    }

}
