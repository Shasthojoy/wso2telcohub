/**
 * Title		:Billing Mediation Interface
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:May 18, 2005
 * @author 		:Administrator
 * @version 	:1.0
 * 				: Changed on 30-12-2008 to facilitate CG-Webservice interface by Lakthinda Ranasinghe
 */

package com.dialog.util.bgw;

public class BillGWResult {
	private String appID			= "";
	private String domainID			= "";
	private String accType			= "";
	private String accStatus		= "";
	private String transactionResult= "";
	private String dateTime			= "";
	private String creditLimit		= "";	
	private String ammountDue		= "";
	private String transactionID	= "";
	private String tokenID			= "";
	private String referenceID		= "";
  
    public BillGWResult() {

    }
    
    // Login request - START
    public String getAppID(){        
        return this.appID;
    }
    
    public String getStatus(){        
        return this.accStatus;
    }

    public String getToken(){        
        return this.tokenID;
    }

    public String getDateTime(){        
        return this.dateTime;
    }
  
    public String getTransID(){        
        return this.transactionID;
    }

    public String getDomain(){        
        return this.domainID;
    }
    // Replacing with TransID
    public String getRefID(){        
        return this.transactionID;
    }
    
    public String getResult(){ // Status in the New CG        
        return this.transactionResult;
    }
    
    public String getCreditLimit(){        
        return this.creditLimit;
    }
    
    public String getAmountDue(){        
        return this.ammountDue;
    }
    
    public String getAccStatus(){        
        return this.accStatus;
    }

    public String getBalChk_AccType(){        
        return this.accType;
    }
    public String getBalChk_DateTime(){        
        return this.dateTime;
    }
    
    public String getAccAdj_AccType(){        
        return this.accType;
    }

    public String getAccAdj_DateTime(){        
        return this.dateTime;
    }    
    
    public String toString(){    	
        return appID+"|"+transactionID+"|"+domainID+"|"+transactionResult+"|"+referenceID+"|"+accStatus+"|"+accType+"|"+dateTime+"|"+creditLimit+"|"+ammountDue;
    }

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public void setAmmountDue(String ammountDue) {
		this.ammountDue = ammountDue;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}

	public void setTransID(String transactionID) {
		this.transactionID = transactionID;
	}

	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
	}

	public void setToken(String token) {
		this.tokenID = token;
	}

	public void setRefID(String referenceID) {
		this.referenceID = referenceID;
	}

	

}

