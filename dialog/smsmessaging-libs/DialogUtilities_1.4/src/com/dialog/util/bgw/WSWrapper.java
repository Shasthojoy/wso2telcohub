package com.dialog.util.bgw;

import java.text.MessageFormat;
import java.util.HashMap;

import org.jdom.Document;

import com.dialog.util.Base64;
import com.dialog.util.SystemConfig;
import com.dialog.util.SystemLog;
import com.dialog.util.Utility;
import com.dialog.util.web.HttpPosterCommons;

/**
 * Title 		: Dialog Util - Dialog R&D Utility library
 * Description	: Wrapper for new CG webservices
 * Copyright	: Copyright (c) 2008
 * Company		: Dialog Telekom PLC
 * Department	: Group Technology R&D
 * Created On	: 31-12-2008
 * @author 		: Lakthinda Ranasinghe
 * Comments		:
 * 
 */
public class WSWrapper {
	private static HttpPosterCommons posterCommons 		= null;
	private static HashMap responseMap 	                = null;
	private static WSWrapper s_instance					= null;
	private static String URL							= null;
	private static String APP_ID						= null;
	private static String DOMAIN_ID						= null;
	private static String ROOT							= null;
	private static String PASSWORD                      = null;
	
	public static String REQ_GETTXNKEY                  = null;
	public static String REQ_CREDITCHK                  = null;
	public static String REQ_CHRG2BILL                  = null;
	public static String REQ_SPCLDEBT                   = null;
	public static String REQ_DIRECTDEBT                 = null;
	public static boolean USE_PROXY                     = true;
	final private static String SC_OK					= "200";
	final private static String SC_ERROR				= "500";	
	
	public static WSWrapper getInstance(){
		if(s_instance == null){
			s_instance = new WSWrapper();
		}
		return s_instance;
	}
	
	/**
	 * 
	 */
	private WSWrapper(){	    
		ROOT      = (!Utility.isValid(ROOT))?SystemConfig.getInstance().getNodeName():ROOT;		
		URL       = (!Utility.isValid(URL))?SystemConfig.getInstance().getStr(ROOT+".billUrl"):URL;
		APP_ID    = (!Utility.isValid(APP_ID))?SystemConfig.getInstance().getStr(ROOT+".billAppID"):APP_ID;
		DOMAIN_ID = (!Utility.isValid(DOMAIN_ID))?SystemConfig.getInstance().getStr(ROOT+".domainID"):DOMAIN_ID;		
		PASSWORD  = (!Utility.isValid(PASSWORD))?SystemConfig.getInstance().getStr(ROOT+".billPassword"):PASSWORD;
		
		if(ROOT!=null) {
		    REQ_GETTXNKEY  = SystemConfig.getInstance().getStr(ROOT+".requests.req_transactionKey");
		    REQ_CREDITCHK  = SystemConfig.getInstance().getStr(ROOT+".requests.req_creditCheck");
		    REQ_CHRG2BILL  = SystemConfig.getInstance().getStr(ROOT+".requests.req_chargeToBill");
		    REQ_SPCLDEBT   = SystemConfig.getInstance().getStr(ROOT+".requests.req_chargeBySpecDebit");
		    REQ_DIRECTDEBT = SystemConfig.getInstance().getStr(ROOT+".requests.req_chargeByDirectDebit");
		}
	}
	
	/**
	 * Set the parameters used to create a connection with the CGW 
	 * @param url URL of charging gateway web services api
	 * @param appID the application id
	 * @param password password to login to CGW
	 * @param domainID Charging doamin (GSM, PHONE, CDMA)
	 * @param cgwConfigPath the path to cgw config elements in config.xml
	 */
	public static void initCGWParams(String url, String appID, String password, String domainID, String cgwConfigPath) {
	    URL       = url;
	    APP_ID    = appID;
	    PASSWORD  = password;
	    DOMAIN_ID = domainID;
	    ROOT      = cgwConfigPath;
	}
	
	public static void main(String[] args) {
		SystemConfig.DEFAULT_FILE = "D:\\office\\Development\\workspace\\DialogUtilities_1.4\\conf\\config.xml";
		SystemLog.DEFAULT_LOG4J_FILE = "D:\\office\\Development\\workspace\\DialogUtilities_1.4\\conf\\log4j.xml";
		WSWrapper.ROOT = "admin.cgw";
		String sessionID = "";
		try {		    
		    sessionID = WSWrapper.getInstance().getTransactionKey("","").getToken();			
			System.out.println(sessionID);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
//		reason code : 277 
//		WSWrapper.getInstance().creditCheck("777337384","");			
//		WSWrapper.getInstance().payByCash("777337384","",Double.parseDouble("1"), "277");
//		WSWrapper.getInstance().payByCard("777337384","",Double.parseDouble("1"), "277", "HSBC", "10101010", "VISA");
//		WSWrapper.getInstance().payByDirectDebit("777337384","",Double.parseDouble("1"), "277");		
//		WSWrapper.getInstance().chargeToBill("777337384","",Double.parseDouble("1"), "277",true);		
//		WSWrapper.getInstance().chargeBySpecialDebit("777337384","",Double.parseDouble("1"),"Description" ,"277",true);
		try {
		long time1 = System.currentTimeMillis();		
//		resultStr = WSWrapper.getInstance().chargeToBill(resultStr,"","777335365","","",Double.parseDouble("1"), "277",true).toString();
		
		String resultStr1 = WSWrapper.getInstance().creditCheck(sessionID,System.currentTimeMillis()+"","777335365","","").toString();
		
		String resultStr2 = WSWrapper.getInstance().chargeByDirectDebit(sessionID,System.currentTimeMillis()+"","777335365","","",Double.parseDouble("1"), "431").toString();
		long time2 = System.currentTimeMillis();
		
		
		System.out.println("Result 1: "+resultStr1);
        System.out.println("Result 2: "+resultStr2);
        
		System.out.println("Time: "+(time2 - time1));
//		resultStr 	= WSWrapper.getInstance().creditCheck(resultStr,"","777335365","","").toString();
//		resultStr 	= WSWrapper.getInstance().creditCheck("*0O0MZFuxRpCh94n4AgfCZ(uepCFAGJul0iUGssa","","777335365","","").toString();			
//		resultStr 	= WSWrapper.getInstance().payByCash("*0O0ynVHMV1UDao(hd3JnIVwQsRmAGJul0iUGssa","","777337384","","",Double.parseDouble("1"), "277").toString();
//		String resultStr 	= WSWrapper.getInstance().payByCard("*0O0ynVHMV1UDao(hd3JnIVwQsRmAGJul0iUGssa","777337384","",Double.parseDouble("1"), "277", "HSBC", "10101010", "VISA").toString();
//		String resultStr 	= WSWrapper.getInstance().payByDirectDebit("*0O0ynVHMV1UDao(hd3JnIVwQsRmAGJul0iUGssa","777337384","",Double.parseDouble("1"), "277").toString();		
//		resultStr = WSWrapper.getInstance().chargeToBill("*0O0MZFuxRpCh94n4AgfCZ(uepCFAGJul0iUGssa","","777335365","","",Double.parseDouble("1"), "277",true).toString();
//		String resultStr 	= WSWrapper.getInstance().chargeBySpecialDebit("*0O0ynVHMV1UDao(hd3JnIVwQsRmAGJul0iUGssa","777337384","",Double.parseDouble("1"),"Description" ,"277",true).toString();		
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
				
		/*
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, "HASHB");
			
			Key key = new Key("HASHB");
		} catch (NoSuchAlgorithmException e) {
			 
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}*/ 
		
	}
	/**
	 * 
	 * @param applicationID
	 * @param password
	 * @return BillGWResult
	 * @throws Exception
	 */
	protected BillGWResult getTransactionKey(String applicationID,String password) throws Exception{
		String transactionKey 	= "";
		BillGWResult bgResult	= null;
		
		String appID			= (!Utility.isValid(applicationID))?APP_ID:applicationID;		
		String pwd				= (!Utility.isValid(password))?PASSWORD:password;
		pwd                     = new Base64().byteArrayToBase64(PASSWORD.getBytes());
		String soapRequest 		= REQ_GETTXNKEY;
		soapRequest				= MessageFormat.format(soapRequest, new Object[]{appID,pwd });		
		posterCommons 			= HttpPosterCommons.getInstance();		
		responseMap				= posterCommons.executeHttp(URL, null, soapRequest, "text/xml", "utf-8", USE_PROXY);
		String responseSoap 	= (String)responseMap.get("response");
		
		try {
			Document doc 		= Utility.convertXMLStr2DOMDoc(responseSoap);
			transactionKey		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("getSessionKeyResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getText();			
			
			bgResult			= new BillGWResult();
			bgResult.setAppID(appID);			
			bgResult.setToken(transactionKey);
			SystemLog.getInstance().getLogger("billgw").info("[WSWrapper],getTransactionKey,"+transactionKey+","+appID+","+password+","+bgResult);
		} catch (Exception e) {
			SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],getTransactionKey,"+transactionKey+","+appID+","+password+","+bgResult,e);
			throw new Exception("WSWrapper error",e);
		}
		
		return bgResult;
	}
	/**
	 * 	
	 * @param sessionID
	 * @param transID
	 * @param refAccount
	 * @param domainID
	 * @param applicationID
	 * @return
	 * @throws Exception
	 */
	public BillGWResult creditCheck(String sessionID,String transID,String refAccount,String domainID,String applicationID) throws Exception{
		BillGWResult bgResult	= null;
		String transactionID	= (!Utility.isValid(transID))?(System.currentTimeMillis()+""):transID;
		String domain			= (!Utility.isValid(domainID))?DOMAIN_ID:domainID;
		String appID			= (!Utility.isValid(applicationID))?APP_ID:applicationID;
		String soapRequest 		= REQ_CREDITCHK;
		soapRequest				= MessageFormat.format(soapRequest, new Object[]{appID,sessionID,domain,refAccount,transactionID });
		posterCommons 			= HttpPosterCommons.getInstance();
		responseMap				= posterCommons.executeHttp(URL, null, soapRequest, "text/xml", "utf-8", USE_PROXY);
		String responseSoap 	= (String)responseMap.get("response");
		String httpStatus		= (String)responseMap.get("statusCode");
				
		String accType			= "";
		String accStatus		= "";
		String transactionResult= "";		
		String dateTime			= "";
		String creditLimit		= "";
		String outStanding		= "";
		try {
			if(!httpStatus.equals(SC_OK)){
				responseSoap = (String)responseMap.get("errorResponse");				
			}
			
			Document doc 		= Utility.convertXMLStr2DOMDoc(responseSoap);
			bgResult			= new BillGWResult();
			
			if(httpStatus.equals(SC_OK)){
				transactionResult	= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("transResult").getText();			

				if(transactionResult.equals("0")){
					accType 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountType").getText();
					accStatus 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountStatus").getText();
					dateTime 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("dateTime").getText();
					creditLimit		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("creditlimit").getText();
					outStanding		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("performCreditCheckResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("outStanding").getText();				
				}
				
				bgResult.setAppID(appID);			
				bgResult.setTransID(transactionID);
				bgResult.setDomainID(domain);
				bgResult.setTransactionResult(transactionResult);
				bgResult.setCreditLimit(creditLimit);
				bgResult.setAmmountDue(outStanding);
				bgResult.setAccStatus(accStatus);
				bgResult.setAccType(accType);
				bgResult.setDateTime(dateTime);
				
			}else if(httpStatus.equals(SC_ERROR)){
				String faultStr 	= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("Fault",doc.getRootElement().getNamespace("env")).getChild("faultstring").getText();
				if(faultStr.indexOf("EXPIRED_TOKEN")>-1){
					transactionResult = "4";
				}
				bgResult.setTransactionResult(transactionResult);
				SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],creditCheck,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+bgResult);
			}
			
		} catch (Exception e) {
			SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],creditCheck,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+bgResult,e);
			throw new Exception("WSWrapper error",e);
		}
		SystemLog.getInstance().getLogger("billgw").info("[WSWrapper],creditCheck,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+bgResult);
		return bgResult;
	}
	/**
	 * 
	 * @param sessionID
	 * @param transID
	 * @param refAccount
	 * @param domainID
	 * @param applicationID
	 * @param amount
	 * @param reasonCode
	 * @param isTaxable
	 * @return
	 * @throws Exception
	 */
	public BillGWResult chargeToBill(String sessionID,String transID,String refAccount,String domainID,String applicationID,double amount,String reasonCode,boolean isTaxable) throws Exception{
		BillGWResult bgResult	= null;
		String transactionID    = (!Utility.isValid(transID))?(System.currentTimeMillis()+""):transID;
        String domain           = (!Utility.isValid(domainID))?DOMAIN_ID:domainID;
        String appID            = (!Utility.isValid(applicationID))?APP_ID:applicationID;
        String soapRequest      = REQ_CHRG2BILL;	
		soapRequest				= MessageFormat.format(soapRequest, new Object[]{appID,sessionID,domain,refAccount,transactionID,Double.toString(amount),reasonCode,(isTaxable?"true":"false") }); 
		posterCommons 			= HttpPosterCommons.getInstance();
		responseMap				= posterCommons.executeHttp(URL, null, soapRequest, "text/xml", "utf-8", USE_PROXY);
		String responseSoap 	= (String)responseMap.get("response");
		String httpStatus		= (String)responseMap.get("statusCode");
		String accType			= "";
		String accStatus		= "";
		String transactionResult= "";
		String dateTime			= "";
				
		try {
			if(!httpStatus.equals(SC_OK)){
				responseSoap = (String)responseMap.get("errorResponse");				
			}
			
			Document doc 		= Utility.convertXMLStr2DOMDoc(responseSoap);
			bgResult			= new BillGWResult();
			
			if(httpStatus.equals(SC_OK)){
				transactionResult	= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeToBillResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("transResult").getText();
				
				if(transactionResult.equals("0")){
					accType 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeToBillResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountType").getText();
					accStatus 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeToBillResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountStatus").getText();
					dateTime 		= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeToBillResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("dateTime").getText();									
				}			
				bgResult.setAppID(appID);           
                bgResult.setTransID(transactionID);
                bgResult.setDomainID(domain);
				bgResult.setTransactionResult(transactionResult);			
				bgResult.setAccStatus(accStatus);
				bgResult.setAccType(accType);
				bgResult.setDateTime(dateTime);	
			}else if(httpStatus.equals(SC_ERROR)){
				String faultStr 	= doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("Fault",doc.getRootElement().getNamespace("env")).getChild("faultstring").getText();
				if(faultStr.indexOf("EXPIRED_TOKEN")>-1){
					transactionResult = "4";
				}
				bgResult.setTransactionResult(transactionResult);
				SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeToBill,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult);
			}					
		} catch (Exception e) {
			SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeToBill,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult,e);
			throw new Exception("WSWrapper error",e);
		}
		SystemLog.getInstance().getLogger("billgw").info("[WSWrapper],chargeToBill,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult);
		return bgResult;
	}
	
	/**
	 * 
	 * @param sessionID
	 * @param transID
	 * @param refAccount
	 * @param domainID
	 * @param applicationID
	 * @param amount
	 * @param reasonCode
	 * @return
	 */
	public BillGWResult chargeByDirectDebit(String sessionID, String transID, String refAccount, String domainID, String applicationID, double amount, String reasonCode) throws Exception {
	    BillGWResult bgResult   = null;
        String transactionID    = (!Utility.isValid(transID))?(System.currentTimeMillis()+""):transID;
        String domain           = (!Utility.isValid(domainID))?DOMAIN_ID:domainID;
        String appID            = (!Utility.isValid(applicationID))?APP_ID:applicationID;
        String soapRequest      = REQ_DIRECTDEBT;       
        soapRequest             = MessageFormat.format(soapRequest, new Object[]{appID,sessionID,domain,refAccount,transactionID,Double.toString(amount),reasonCode }); 
        posterCommons           = HttpPosterCommons.getInstance();
        responseMap             = posterCommons.executeHttp(URL, null, soapRequest, "text/xml", "utf-8", USE_PROXY);
        String responseSoap     = (String)responseMap.get("response");
        String httpStatus       = (String)responseMap.get("statusCode");        
        String accType          = "";
        String accStatus        = "";
        String transactionResult= "";
        String dateTime         = "";
        String receiptNo        = "";
                
        try {
            if(!httpStatus.equals(SC_OK)){
                responseSoap = (String)responseMap.get("errorResponse");                
            }
                        
            Document doc        = Utility.convertXMLStr2DOMDoc(responseSoap);
            bgResult            = new BillGWResult();
            
            if(httpStatus.equals(SC_OK)){
                transactionResult   = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("payByDirectDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("transResult").getText();
                
                if(transactionResult.equals("0")){
                    accType         = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("payByDirectDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountType").getText();
                    accStatus       = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("payByDirectDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountStatus").getText();
                    dateTime        = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("payByDirectDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("dateTime").getText();                                  
                    receiptNo       = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("payByDirectDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("receiptNo").getText();
                }
                bgResult            = new BillGWResult();
                bgResult.setAppID(appID);           
                bgResult.setTransID(transactionID);
                bgResult.setDomainID(domain);
                bgResult.setTransactionResult(transactionResult);           
                bgResult.setAccStatus(accStatus);
                bgResult.setAccType(accType);
                bgResult.setDateTime(dateTime);
            }else if(httpStatus.equals(SC_ERROR)){
                String faultStr     = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("Fault",doc.getRootElement().getNamespace("env")).getChild("faultstring").getText();
                if(faultStr.indexOf("EXPIRED_TOKEN")>-1){
                    transactionResult = "4";
                }
                bgResult.setTransactionResult(transactionResult);
                SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeByDirectDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+""+","+bgResult);
            }                   
        } catch (Exception e) {
            SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeByDirectDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+""+","+bgResult,e);
            throw new Exception("WSWrapper error",e);
        }
        SystemLog.getInstance().getLogger("billgw").info("[WSWrapper],chargeByDirectDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+""+","+bgResult);
        return bgResult;
	}
	
	/**
	 * 
	 * @param sessionID
	 * @param transID
	 * @param refAccount
	 * @param domainID
	 * @param applicationID
	 * @param amount
	 * @param billDesc
	 * @param reasonCode
	 * @param isTaxable
	 * @return
	 * @throws Exception
	 */
	public BillGWResult chargeBySpecialDebit(String sessionID,String transID,String refAccount,String domainID,String applicationID,double amount,String billDesc, String reasonCode,boolean isTaxable) throws Exception{
	    BillGWResult bgResult   = null;
        String transactionID    = (!Utility.isValid(transID))?(System.currentTimeMillis()+""):transID;
        String domain           = (!Utility.isValid(domainID))?DOMAIN_ID:domainID;
        String appID            = (!Utility.isValid(applicationID))?APP_ID:applicationID;
        String soapRequest      = REQ_SPCLDEBT;       
        soapRequest             = MessageFormat.format(soapRequest, new Object[]{appID,sessionID,domain,refAccount,transactionID,Double.toString(amount),billDesc,reasonCode,(isTaxable?"true":"false") }); 
        posterCommons           = HttpPosterCommons.getInstance();
        responseMap             = posterCommons.executeHttp(URL, null, soapRequest, "text/xml", "utf-8", USE_PROXY);
        String responseSoap     = (String)responseMap.get("response");
        String httpStatus       = (String)responseMap.get("statusCode");        
        String accType          = "";
        String accStatus        = "";
        String transactionResult= "";
        String dateTime         = "";
                
        try {
            if(!httpStatus.equals(SC_OK)){
                responseSoap = (String)responseMap.get("errorResponse");                
            }
                        
            Document doc        = Utility.convertXMLStr2DOMDoc(responseSoap);
            bgResult            = new BillGWResult();
            
            if(httpStatus.equals(SC_OK)){
                transactionResult   = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeBySpecialDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("transResult").getText();
                
                if(transactionResult.equals("0")){
                    accType         = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeBySpecialDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountType").getText();
                    accStatus       = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeBySpecialDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("accountStatus").getText();
                    dateTime        = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("chargeBySpecialDebitResponse",doc.getRootElement().getNamespace("ns1")).getChild("result").getChild("dateTime").getText();                                  
                }
                bgResult            = new BillGWResult();
                bgResult.setAppID(appID);           
                bgResult.setTransID(transactionID);
                bgResult.setDomainID(domain);
                bgResult.setTransactionResult(transactionResult);           
                bgResult.setAccStatus(accStatus);
                bgResult.setAccType(accType);
                bgResult.setDateTime(dateTime);
            }else if(httpStatus.equals(SC_ERROR)){
                String faultStr     = doc.getRootElement().getChild("Body", doc.getRootElement().getNamespace("env")).getChild("Fault",doc.getRootElement().getNamespace("env")).getChild("faultstring").getText();
                if(faultStr.indexOf("EXPIRED_TOKEN")>-1){
                    transactionResult = "4";
                }
                bgResult.setTransactionResult(transactionResult);
                SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeBySpecialDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult);
            }                   
        } catch (Exception e) {
            SystemLog.getInstance().getLogger("billgwerr").error("[WSWrapper],chargeBySpecialDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult,e);
            throw new Exception("WSWrapper error",e);
        }
        SystemLog.getInstance().getLogger("billgw").info("[WSWrapper],chargeBySpecialDebit,"+transactionID+","+refAccount+","+domainID+","+applicationID+","+amount+","+reasonCode+","+isTaxable+","+bgResult);
        return bgResult;
    }
}
