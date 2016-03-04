/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.dialog.cg.service;

import lk.dialog.cg.ws.messages.jaws.*;
import org.apache.log4j.Logger;

import java.net.URL;

/**
 * @author roshan_06809
 */
public class PaymentService {
    private static final Logger LOG = Logger.getLogger(PaymentService.class.getName());

    //private static PaymentService s_instance = null;
    private String url;
    private String appID;
    private String usr;
    private String pass;
    private String rcode;
    private String domainID;
    private double defaultChargeAmount;
    private String sessionKey = "";
    private boolean proxy;
    CGWebService service;
    CGWebServiceInterface port;
    //temporary
    final String PROXY_USER = "roshan_06809";
    final String PROXY_PASS = "helloHello99";
    final String PROXY_HOST = "proxy.dialog.dialoggsm.com";
    final String PROXY_PORT = "8080";


    private boolean busy = false;

    /**
     * @return
     */

    public boolean isClosed() {
        if (isBusy()) {
            return false;
        } else {
            return true;
        }
    }

    public void close() {
        this.busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void start() {
        this.busy = true;
    }

    /**
     *
     */
    public String getSessionKey() {
        return port.getSessionKey(appID, pass.getBytes());
    }

    public PaymentService(String url, String appId, String userId, String passwd,
                          String rcode, String domainId, boolean proxy) throws Exception {
        this.url = url;
        this.appID = appId;
        this.usr = userId;
        this.pass = passwd;
        this.rcode = rcode;
        this.domainID = domainId;
        this.proxy = proxy;

        try {
            //URL url = new URL("http://172.26.1.40:8080/CGWebService?wsdl");

            //url = "CGWebService?wsdl";            
            //service = new CGWebService(new URL(url));
//
//            if (proxy) {
//                Authenticator.setDefault(new ProxyAuthenticator(PROXY_USER, PROXY_PASS));
//                System.getProperties().setProperty("http.proxyHost", PROXY_HOST);
//                System.getProperties().setProperty("http.proxyPort", PROXY_PORT);
//                String userid = new sun.misc.BASE64Encoder().encode(PROXY_USER.getBytes());
//                String password = new sun.misc.BASE64Encoder().encode(PROXY_PASS.getBytes());
//                System.getProperties().setProperty("http.proxyUser", userid);
//                System.getProperties().setProperty("http.proxyPassword", password);
//            }

            LOG.info("[PaymentManager] : Init , URL :" + url);
            URL wsdlUrl = PaymentService.class.getResource(url);
            LOG.info("[PaymentManager] : Init , wsdlUrl :" + wsdlUrl.toString());
            service = new CGWebService(wsdlUrl);
            port = service.getCGWebServiceInterfacePort();
            sessionKey = port.getSessionKey(appID, pass.getBytes());

            if ((sessionKey == null)
                    || (sessionKey.equalsIgnoreCase("NOT_AUTHORIZED"))
                    || (sessionKey.equalsIgnoreCase("LOGIN_FAILED"))) {
                throw new Exception("Login failed to charging gateway");
            }
            LOG.info("[PaymentManager] : init, sessionkey :" + sessionKey);

        } catch (Exception ex) {
            LOG.error("[PaymentManager] : Init Error ," + ex.getMessage());
            throw new Exception(ex.getMessage());
        }

    }

    /**
     * @param msisdn
     * @param amount
     * @return
     */
    public boolean chargeUser(String msisdn, double amount) {
        boolean chargestatus = false;
        //start();
        try {
            ChargedToBillRequest request = new ChargedToBillRequest();
            request.setAmount(amount);
            request.setAppID(appID);
            request.setAuthKey(sessionKey);
            request.setDomainID(domainID);
            request.setReasonCode(rcode);
            request.setRefAccount(msisdn);
            request.setTaxable(true);
            request.setTransactionID(String.valueOf(System.nanoTime()));

            ChargedToBillResponse response = null;
            try {
                response = port.chargeToBill(request);
            } catch (Exception e) {
                if (e.getMessage().equals("EXPIRED_TOKEN")) {
                    sessionKey = port.getSessionKey(appID, pass.getBytes());
                    request.setAuthKey(sessionKey);
                    response = port.chargeToBill(request);
                } else {
                    throw e;
                }
            }

            String result = response.getTransResult();

            if (result != null && result.equals("0")) {
                chargestatus = true;
                LOG.info("[PaymentManager],ChargeUser," + msisdn + ":" + amount);
            }
        } catch (Exception e) {
            LOG.error("[PaymentManager],ChargeUser," + e);
            return false;
        }
        return chargestatus;
    }

    /**
     * @param msisdn
     * @param amount
     * @return
     */
    public ChargedToBillResponse chargeUser(String msisdn, double amount, boolean isdetail) {
        boolean chargestatus = false;
        String txnid = String.valueOf(System.nanoTime());
        ChargedToBillResponse response = null;
        try {
            ChargedToBillRequest request = new ChargedToBillRequest();
            request.setAmount(amount);
            request.setAppID(appID);
            request.setAuthKey(sessionKey);
            request.setDomainID(domainID);
            request.setReasonCode(rcode);
            request.setRefAccount(msisdn);
            request.setTaxable(true);
            request.setTransactionID(txnid);

            LOG.info("[PaymentManager],ChargeUser," + amount + " :" + appID + " :" + sessionKey + " :" + domainID + " :" + rcode + " :" + msisdn + " :" + txnid);

            try {
                response = port.chargeToBill(request);
            } catch (Exception e) {
                if (e.getMessage().equals("EXPIRED_TOKEN")) {
                    sessionKey = port.getSessionKey(appID, pass.getBytes());
                    request.setAuthKey(sessionKey);
                    response = port.chargeToBill(request);
                } else {
                    throw e;
                }
            }
            String result = response.getTransResult();

            if (result != null && result.equals("0")) {
                chargestatus = true;
                LOG.info("[PaymentManager],ChargeUser," + msisdn + ":" + amount);
            }
        } catch (Exception e) {
            LOG.error("[PaymentManager],ChargeUser," + e);
            return null;
        }
        return response;
    }

    /**
     * @param msisdn
     * @param amount
     * @return
     */
    public ChargeBySpecialDebitResponse chargeSpecial(String msisdn, double amount, boolean isdetail, boolean taxable, String billDesc) {

        boolean chargestatus = false;
        ChargeBySpecialDebitResponse response = null;
        String txnid = String.valueOf(System.nanoTime());
        try {
            ChargeBySpecialDebitRequest request = new ChargeBySpecialDebitRequest();
            request.setAmount(amount);
            request.setAppID(appID);
            request.setAuthKey(sessionKey);
            request.setDomainID(domainID);
            request.setReasonCode(rcode);
            request.setRefAccount(msisdn);
            request.setTaxable(taxable);
            request.setBillDesc(billDesc);
            request.setTransactionID(txnid);

            LOG.info("[PaymentManager],ChargeSpecial," + amount + " :" + appID + " :" + sessionKey + " :" + domainID + " :" + rcode + " :" + msisdn + " :" + taxable + " :" + billDesc + " :" + txnid);

            try {
                response = port.chargeBySpecialDebit(request);
            } catch (Exception e) {
                if (e.getMessage().equals("EXPIRED_TOKEN")) {
                    sessionKey = port.getSessionKey(appID, pass.getBytes());
                    request.setAuthKey(sessionKey);
                    response = port.chargeBySpecialDebit(request);
                } else {
                    throw e;
                }
            }
            String result = response.getTransResult();

            if (result != null && result.equals("0")) {
                chargestatus = true;
                LOG.info("[PaymentManager],ChargeUser," + msisdn + ":" + amount);
            }
        } catch (Exception e) {
            LOG.error("[PaymentManager],ChargeUser," + e);
            return null;
        }
        return response;
    }

    /**
     * @param msisdn
     * @return
     */
    public BalResponse getAvailableBalance(String msisdn) {
        double availableBalance = 0;
        String accountType = null;
        String accountStat = null;
        double creditLimit = 0;
        double creditBalance = 0;
        try {
            CreditCheckRequest request = new CreditCheckRequest();
            request.setAppID(appID);
            request.setDomainID(domainID);
            request.setRefAccount(msisdn);
            request.setTransactionID(String.valueOf(System.nanoTime()));
            request.setAuthKey(sessionKey);

            CreditCheckResponse response = null;
            try {
                response = port.performCreditCheck(request);
            } catch (Exception e) {
                if (e.getMessage().equals("EXPIRED_TOKEN")) {
                    sessionKey = port.getSessionKey(appID, pass.getBytes());
                    request.setAuthKey(sessionKey);
                    response = port.performCreditCheck(request);
                } else {
                    throw e;
                }
            }

            LOG.info("[PaymentManager],getBalance,response :" + response);
            String result = response.getTransResult();

            if (result != null && result.equals("0")) {
                creditBalance = response.getOutStanding();
                creditLimit =  Math.round(( response.getCreditlimit()) * 100.0) / 100.0;
                accountType = response.getAccountType();
                accountStat = response.getAccountStatus();
                availableBalance = Math.round((creditLimit - creditBalance) * 100.0) / 100.0;
            } else {
                throw new Exception("Balance operation failed");
            }

//            creditBalance = response.getOutStanding();
//            creditLimit = response.getCreditlimit();

            LOG.info("[PaymentManager],getBalance," + msisdn + " ,creditbal : "
                    + creditBalance + ",creditlimit :" + creditLimit);

//            availableBalance = creditLimit - creditBalance;
            LOG.info("[PaymentManager],getBalance," + availableBalance);
        } catch (Exception e) {
            LOG.error("[PaymentManager],getBalance," + e, e);
            return new BalResponse(Double.NaN, "", "", Double.NaN, Double.NaN);
        }

        return new BalResponse( availableBalance , accountType, accountStat, creditLimit, creditBalance);
    }

    public static void main(String arg[]) {

        String url = "/src/main/wsdl/CGWebService.wsdl";
        //PaymentService ps = PaymentService.getInstance(url, "CLICKCALL", "test", "cL1K2Ka11", "657", "dialog",false);
        //System.out.println(ps.getSessionKey());
        //System.out.println(ps.getAvailableBalance("0777335365"));
    }
}
