/**
 * Title		:DialogUtilities
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:May 26, 2005
 * @author 		:Administrator
 * @version 	:1.0
 * 				:Changed on 31-12-2008 to use with new CG Webservices / WSWrapper class by Lakthinda Ranasinghe
 */

package com.dialog.util.bgw;
import com.dialog.util.web.HttpPostor;

public class BillGWCtrl {
    String m_url 		= "";
    String m_appID 		= "";
    String m_userName	= "";
    String m_password 	= "";
    String m_sessionID 	= "";
    
    // New parameters New CG 2008.03.25
    String m_token 		= "";
    String m_domain 	= "GSM";
    //String m_domain     = "PHONE";
    
    HttpPostor m_post = null;
    /**
     * 
     */
    public BillGWCtrl() {
        
    }
    // New 2008.03.25 - Domain added
    public BillGWCtrl(String url, String appID, String usrName, String password, String domain) {
        this.m_url = url;
        this.m_appID = appID;
        this.m_userName = usrName;
        this.m_password = password;
        this.m_domain = domain;
    }

    public BillGWCtrl(String url, String appID, String usrName, String password) {
        this.m_url = url;
        this.m_appID = appID;
        this.m_userName = usrName;
        this.m_password = password;
    }
    
    public void setAppID(String appID) {
        m_appID = appID;
    }
    public void setPassword(String password) {
        m_password = password;
    }
    public void setUrl(String url) {
        m_url = url;
    }
    public void setUserName(String userName) {
        m_userName = userName;
    }
    // New 2008.03.25
    public void setDomain(String domain) {
        m_domain = domain;
    }
    
    public boolean isLoggedIn(){
    	return !m_token.equals("");
    }
    
    public void login() throws Exception{ 
        BillGWResult res	= WSWrapper.getInstance().getTransactionKey(m_appID,m_password);
    	
    	if ( res == null ){            
            throw new BillGWException("Login Err - null");
        }else if ( res.getToken().equalsIgnoreCase("LOGIN_FAILED")){            
            throw new BillGWException("Login Err - Login Failed");
        }
            	
    	m_token				= res.getToken();
    }
    
    public BillGWResult getBalance(String transID, String mobileNo) throws Exception{        
    	BillGWResult bgResult = getBalanceImpl(transID, mobileNo, "");
    	
    	return bgResult;
    }
    
    public BillGWResult getBalance(String transID, String mobileNo,String domainID) throws Exception{
    	BillGWResult bgResult = getBalanceImpl(transID, mobileNo, domainID);
    	
    	return bgResult;
    }
    
    private BillGWResult getBalanceImpl(String transID, String mobileNo,String domainID) throws Exception{
    	domainID = (domainID.equals("")? m_domain:domainID);
    	if(m_token.equals("")){
    		throw new BillGWException("Error - Invalid Token");
    	}
    	BillGWResult bgResult = WSWrapper.getInstance().creditCheck(m_token,transID, mobileNo, domainID,m_appID);
    	
    	return bgResult;
    }
    
    public BillGWResult getAccAdjMethod(String transID, String mobileNo,String adjType,String adjOpt, String amt, String reasonCode, String template, String rule) throws Exception{        
        BillGWResult bgResult = getAccAdjMethodImpl(transID, mobileNo, adjType, adjOpt, amt, reasonCode, template, rule,"");
        
        return bgResult;  
    }
    
    public BillGWResult getAccAdjMethod(String transID, String mobileNo,String adjType,String adjOpt, String amt, String reasonCode, String template, String rule,String domainID) throws Exception{        
        BillGWResult bgResult = getAccAdjMethodImpl(transID, mobileNo, adjType, adjOpt, amt, reasonCode, template, rule,domainID);
        
        return bgResult;  
    }
    
    /**
     * 
     * @param transID
     * @param mobileNo
     * @param amt
     * @param reasonCode
     * @param domainID
     * @param rule
     * @param billDesc
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    public BillGWResult chargeBySpecialDebit(String transID, String mobileNo, String amt, String reasonCode, String domainID, String rule, String billDesc) throws NumberFormatException, Exception {
        boolean isTaxable = (rule.equalsIgnoreCase("y"))? true : false;
        domainID = (domainID.equals("")? m_domain:domainID);
        BillGWResult bgResult = WSWrapper.getInstance().chargeBySpecialDebit(m_token, transID, mobileNo, domainID, m_appID, Double.parseDouble(amt), billDesc, reasonCode, isTaxable);
        return bgResult;
    }
    
    /**
     * 
     * @param transID
     * @param mobileNo
     * @param amt
     * @param reasonCode
     * @param domainID
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    public BillGWResult chargeByDirectDebit(String transID, String mobileNo, String amt, String reasonCode, String domainID) throws NumberFormatException, Exception {
        domainID = (domainID.equals("")? m_domain:domainID);
        BillGWResult bgResult = WSWrapper.getInstance().chargeByDirectDebit(m_token, transID, mobileNo, domainID, m_appID, Double.parseDouble(amt), reasonCode);
        return bgResult;
    }
    
    private BillGWResult getAccAdjMethodImpl(String transID, String mobileNo,String adjType,String adjOpt, String amt, String reasonCode, String template, String rule,String domainID) throws NumberFormatException, Exception{
    	boolean isTaxable = (rule.equalsIgnoreCase("y"))? true : false;
    	domainID = (domainID.equals("")? m_domain:domainID);
    	//if(m_token.equals("")){
    	//	throw new Exception("Error - Invalid Token");
    	//}
        BillGWResult bgResult = WSWrapper.getInstance().chargeToBill(m_token, transID, mobileNo, domainID,m_appID, Double.parseDouble(amt), reasonCode, isTaxable); 
        
        return bgResult;    	
    }
    
    public static void main(String[] args) throws Exception {
    	/*
    	BillGWCtrl billGWCtrl = new BillGWCtrl();
    	System.out.println(billGWCtrl.m_token);
    	try{
    		billGWCtrl.login();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	System.out.println(billGWCtrl.m_token);*/    
    }
}