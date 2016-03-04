/**
 * Title		:DialogUtilities
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:May 26, 2005
 * @author 		:Administrator
 * @version 	:1.0
 */

package com.dialog.util.bgw;

import com.dialog.util.*;
public class BillGWCtrlPool extends ObjectPool {

    static String s_url 		= "";
    static String s_appID 		= "";
    static String s_userName 	= "";
    static String s_password 	= "";
    static String s_rcode 		= "";
    static String s_domain 		= "GSM";
    
    /**
     * 
     * @param url
     * @param appID
     * @param userName
     * @param password
     * @param rcode
     */
    public static void initObjMembers(String url, String appID, String userName, String password,String rcode){
        s_url = url;
        s_appID = appID;
        s_userName = userName;
        s_password = password;
        s_rcode = rcode;
    }
    
    /**
     * 
     * @param url
     * @param appID
     * @param userName
     * @param password
     * @param rcode
     * @param domain
     */
    public static void initObjMembers(String url, String appID, String userName, String password,String rcode, String domain){
        s_url = url;
        s_appID = appID;
        s_userName = userName;
        s_password = password;
        s_rcode = rcode;
        s_domain = domain;
    }
    
    /**
     * 
     * @param url
     * @param appID
     * @param userName
     * @param password
     * @param rcode
     * @param domain
     * @param cgwConfigPath
     */
    public static void initObjMembers(String url, String appID, String userName, String password,String rcode, String domain, String cgwConfigPath){
        s_url = url;
        s_appID = appID;
        s_userName = userName;
        s_password = password;
        s_rcode = rcode;
        s_domain = domain;        
        WSWrapper.initCGWParams(s_url, s_appID,s_password, s_domain, cgwConfigPath);
    }
    /**
     * @param size
     * @throws Exception
     */
    public BillGWCtrlPool(int size) throws Exception {
        super(size);
    }

    public Object create() {
    	BillGWCtrl bg = new BillGWCtrl();
    	bg.setUrl(s_url);
    	bg.setAppID(s_appID);
    	bg.setUserName(s_userName);
    	bg.setPassword(s_password);
    	bg.setDomain(s_domain);
    	try {
    		bg.login();
    		SystemLog.getInstance().getLogger("billgw").debug("INIT,Obj="+m_index+","+s_url+","+s_appID);
    	} catch (Exception e) {
    		SystemLog.getInstance().getLogger("billgwerr").debug("INIT,Obj="+m_index+","+s_url+","+s_appID);
    	}
    	return bg;
    }
    
    /**
     * 
     * @param ref
     * @param mobno
     * @return
     */
    public BillGWResult checkCredit(String ref,String mobno){
        return checkCredit(ref, mobno, s_domain);
    }
    
    /**
     * 
     * @param ref
     * @param mobno
     * @param domain
     * @return
     */
	public BillGWResult checkCredit(String ref,String mobno, String domain){
		BillGWCtrl bg = (BillGWCtrl)checkOut();
		BillGWResult br = null;		
		
			try{		
				if(!bg.isLoggedIn()){
				    SystemLog.getInstance().getLogger("billgwerr").debug("check,login-err");
					bg = new BillGWCtrl(s_url,s_appID,s_userName,s_password, domain);
					bg.login();					
				}
				
				br = bg.getBalance(ref,mobno,domain);
				if ( br.getResult().equals("4") ){
					bg.login();
					br = bg.getBalance(ref, mobno, domain);					
				}
				if ( br == null ) throw new Exception("No response received");		
				
			}catch(Exception e){				
				SystemLog.getInstance().getLogger("billgwerr").error("check,error,"+ref+","+mobno+","+e,e);				 
			} finally {
				checkIn(bg);
			}
			return br;
    }

	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param type
	 * @param opt
	 * @param template
	 * @param tax
	 * @return
	 * @throws Exception
	 */
	public boolean charge(String ref, String chargeNumber,String amount, String type,String opt,String template,String tax) throws Exception{
	    return chargeForDomain(ref, chargeNumber, amount, type, opt, template, tax, s_domain);
	}
	
	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param type
	 * @param opt
	 * @param template
	 * @param tax
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public boolean chargeForDomain(String ref, String chargeNumber,String amount, String type,String opt,String template,String tax, String domain) throws Exception{
		BillGWCtrl bg = (BillGWCtrl)checkOut();
		BillGWResult br = null;
		try{
		    if(!bg.isLoggedIn()){
			    SystemLog.getInstance().getLogger("billgwerr").debug("check,login-err");
				bg = new BillGWCtrl(s_url,s_appID,s_userName,s_password, domain);
				bg.login();					
			}
			br = bg.getAccAdjMethod(ref,chargeNumber,type,opt,amount,s_rcode,template,tax);
			if ( br == null ) throw new Exception("No response received");
			// Session invalid - relogin again
			if ( br.getResult().equals("4") ){
				bg.login();
				br = bg.getAccAdjMethod(ref,chargeNumber,type,opt,amount,s_rcode,template,tax, domain);
				if ( br == null ) throw new Exception("No response received");
			}
			
			if ( !br.getResult().equals("0") )  throw new Exception("Billing GW Error="+br.getResult());
			return true;
		}catch(Exception e1){
		    SystemLog.getInstance().getLogger("billgwerr").error("charge,error,"+ref+","+chargeNumber+","+e1,e1);		    
		}finally{
			checkIn(bg);
		}
		return false;
	}
	
	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param type
	 * @param opt
	 * @param template
	 * @param tax
	 * @return
	 * @throws Exception
	 */
	public boolean charge(String ref, String chargeNumber,String amount, String type,String opt,String template,String tax, String reasonCode) throws Exception{
	    return charge(ref, chargeNumber, amount, type, opt, template, tax, reasonCode, s_domain);
	}

	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param type
	 * @param opt
	 * @param template
	 * @param tax
	 * @param reasonCode
	 * @return
	 * @throws Exception
	 */
	public boolean charge(String ref, String chargeNumber,String amount, String type,String opt,String template,String tax, String reasonCode, String domain) throws Exception{
        BillGWCtrl bg = (BillGWCtrl)checkOut();
        BillGWResult br = null;
        try{
            if( (reasonCode==null) || (reasonCode.trim().equals("")) ) {
                reasonCode = s_rcode;
            }
            if(!bg.isLoggedIn()){
                SystemLog.getInstance().getLogger("billgwerr").debug("check,login-err");
                bg = new BillGWCtrl(s_url,s_appID,s_userName,s_password, domain);
                bg.login();                 
            }
            br = bg.getAccAdjMethod(ref,chargeNumber,type,opt,amount,reasonCode,template,tax);
            if ( br == null ) throw new Exception("No response received");
            // Session invalid - relogin again
            if ( br.getResult().equals("4") ){
                bg.login();
                br = bg.getAccAdjMethod(ref,chargeNumber,type,opt,amount,reasonCode,template,tax, domain);
                if ( br == null ) throw new Exception("No response received");
            }
            
            if ( !br.getResult().equals("0") )  throw new Exception("Billing GW Error="+br.getResult());
            return true;
        }catch(Exception e1){
            SystemLog.getInstance().getLogger("billgwerr").error("charge,error,"+ref+","+chargeNumber+","+e1,e1);           
        }finally{
            checkIn(bg);
        }
        return false;
    }
	
	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param type
	 * @param opt
	 * @param template
	 * @param tax
	 * @param reasonCode
	 * @return
	 * @throws Exception
	 */
	public boolean chargeBySepcialDebit(String ref, String chargeNumber,String amount, String tax, String reasonCode, String billDesc) throws Exception{
	    return chargeBySepcialDebit(ref, chargeNumber, amount, tax, reasonCode, billDesc, s_domain);
	}
	
	/**
	 * 
	 * @param ref
	 * @param chargeNumber
	 * @param amount
	 * @param tax
	 * @param reasonCode
	 * @param billDesc
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public boolean chargeBySepcialDebit(String ref, String chargeNumber,String amount, String tax, String reasonCode, String billDesc, String domain) throws Exception{    
        BillGWCtrl bg = (BillGWCtrl)checkOut();
        BillGWResult br = null;
        try{
            if( (reasonCode==null) || (reasonCode.trim().equals("")) ) {
                reasonCode = s_rcode;
            }
            if(!bg.isLoggedIn()){
                SystemLog.getInstance().getLogger("billgwerr").debug("check,login-err");
                bg = new BillGWCtrl(s_url,s_appID,s_userName,s_password, domain);
                bg.login();                 
            }
            br = bg.chargeBySpecialDebit(ref,chargeNumber,amount,reasonCode,domain,tax,billDesc);
            if ( br == null ) throw new Exception("No response received");
            // Session invalid - relogin again
            if ( br.getResult().equals("4") ){
                bg.login();
                br = bg.chargeBySpecialDebit(ref,chargeNumber,amount,reasonCode,domain,tax,billDesc);
                if ( br == null ) throw new Exception("No response received");
            }
            
            if ( !br.getResult().equals("0") )  throw new Exception("Billing GW Error="+br.getResult());
            return true;
        }catch(Exception e1){
            SystemLog.getInstance().getLogger("billgwerr").error("chargeBySepcialDebit,error,"+ref+","+chargeNumber+","+e1,e1);           
        }finally{
            checkIn(bg);
        }
        return false;
    }
	
	
	public boolean chargeByDirectDebit(String ref, String chargeNumber,String amount, String tax, String reasonCode, String billDesc, String domain) throws Exception{    
        BillGWCtrl bg = (BillGWCtrl)checkOut();
        BillGWResult br = null;
        try{
            if( (reasonCode==null) || (reasonCode.trim().equals("")) ) {
                reasonCode = s_rcode;
            }
            if(!bg.isLoggedIn()){
                SystemLog.getInstance().getLogger("billgwerr").debug("check,login-err");
                bg = new BillGWCtrl(s_url,s_appID,s_userName,s_password, domain);
                bg.login();                 
            }
            br = bg.chargeByDirectDebit(ref,chargeNumber,amount,reasonCode,domain);
            if ( br == null ) throw new Exception("No response received");
            // Session invalid - relogin again
            if ( br.getResult().equals("4") ){
                bg.login();
                br = bg.chargeByDirectDebit(ref,chargeNumber,amount,reasonCode,domain);
                if ( br == null ) throw new Exception("No response received");
            }
            
            if ( !br.getResult().equals("0") )  throw new Exception("Billing GW Error="+br.getResult());
            return true;
        }catch(Exception e1){
            SystemLog.getInstance().getLogger("billgwerr").error("chargeByDirectDebit,error,"+ref+","+chargeNumber+","+e1,e1);           
        }finally{
            checkIn(bg);
        }
        return false;
    }
	
    public static void main(String[] args) throws Exception {
        //BillGWCtrlPool.initObjMembers("http://172.26.1.45:8989/CGApps/jsp/cgAction.jsp","FUNKEY","FUNKEY","rts","227");
        BillGWCtrlPool.initObjMembers("http://172.26.1.45:8989/CGApps/CGWebService?wsdl","MUSIC_OD","MUSIC_OD","IVRmusiC","431", "GSM");        
        
        BillGWCtrlPool bp = new BillGWCtrlPool(1);
        BillGWCtrl bc = (BillGWCtrl)bp.checkOut();
        bc = (BillGWCtrl)bp.checkOut();
        //bc = (BillGWCtrl)bp.checkOut();
        bc.setDomain("GSM");
        bc.login();
        
        //BillGWResult rs = bc.getBalance("" + System.currentTimeMillis(),"777334123");
        BillGWResult rs = bc.getBalance("" + System.currentTimeMillis(),"777334123");
        System.out.println(rs);
          //System.out.println(bc.getBalance("" + System.currentTimeMillis(),"0777334123"));
        //System.out.println(bc.getBalance("" + System.currentTimeMillis(),"777331370"));
        System.out.println();
        //System.out.println(bp.charge("" + System.currentTimeMillis(),"777334123","1","DEBIT","1","1","Y"));
       // System.out.println(bc.getAccAdjMethod("" + System.currentTimeMillis(),"777385575","DEBIT","1","1","227","FUNKEY","Y"));
        //System.out.println(bc.getAccAdjMethod("" + System.currentTimeMillis(),"777385575","DEBIT","1","1","261","1","Y"));
    }
}
