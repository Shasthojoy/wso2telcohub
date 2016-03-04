/**
 * 
 */
/**
 * @author Dialog_Axiata
 *
 */
package com.axiata.dialog.mife.mediator;



import com.axiata.dialog.mife.mediator.internal.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.api.Claim;



public class ProfileAttributeHandler {
	
	 private static final String CURRENT_LOCATION_CLAIM = "http://wso2.org/claims/currentlocation";
	 private static final String MSISDN_CLAIM = "http://wso2.org/claims/msisdn";
	 
	 private static Log log = LogFactory.getLog(ProfileAttributeHandler.class);
	 
	 /**
     * getting the Location of the sender
     * 
     * @param userName
     * @return user's current location
     */
	 public String getUserCurrentLocation(String userName) {
	        UserRealm realm;
	        try {
	           realm = Util.getRealmService().getTenantUserRealm(-1234);
	           UserStoreManager um = realm.getUserStoreManager();
	           String currentLocation = um.getUserClaimValue(userName, CURRENT_LOCATION_CLAIM, null);
	        	
	            return currentLocation;
	        } catch (UserStoreException e) {
	            String errorMsg = "Error while getting Current Location of the user, " + e.getMessage();
	            log.error(errorMsg, e);
	        }
	        return null;

	    }
	 
	 /**
     * getting the MSISDN of the sender
     * 
     * @param userName
     * @return user's MSISDN value
     */
	 public String getUserMSISDN(String userName) {
	        UserRealm realm;
	        try {
	           realm = Util.getRealmService().getTenantUserRealm(-1234);
	           UserStoreManager um = realm.getUserStoreManager();
	           String msisdn = um.getUserClaimValue(userName, MSISDN_CLAIM, null);
	        	
	            return msisdn;
	        } catch (UserStoreException e) {
	            String errorMsg = "Error while getting MSISDN of the user, " + e.getMessage();
	            log.error(errorMsg, e);
	        }
	        return null;

	    }
	 
	 /**
     * Checking Whether a valid user
     * 
     * @param userName
     * @return true if valid user else false
     */
	 public boolean isExistingUser(String userName) {
		 UserRealm realm;
		 boolean isUser = false; 
		 try {
	           realm = Util.getRealmService().getTenantUserRealm(-1234);
	           UserStoreManager um = realm.getUserStoreManager();
	           isUser = um.isExistingUser(userName);
	        	
	            return isUser;
	        } catch (UserStoreException e) {
	            String errorMsg = "Error while getting MSISDN of the user, " + e.getMessage();
	            log.error(errorMsg, e);
	        }
		 return false;
	 
	 }
	 
	 /**
     * getting the profile attributes of the sender
     * 
     * @param userName
     * @return claim values array
     */
	 public Claim[] getAllPropertityValues(String userName) {
		 UserRealm realm;
		 Claim [] allAttributes = {};
		 
		 try {
	           realm = Util.getRealmService().getTenantUserRealm(-1234);
	           UserStoreManager um = realm.getUserStoreManager();
	           allAttributes = um.getUserClaimValues(userName, null);
	           return allAttributes;	
	           
	        } catch (UserStoreException e) {
	            String errorMsg = "Error while getting Attributes of the user, " + e.getMessage();
	            log.error(errorMsg, e);
	        }
		 return null;
	 
	 }
	 
	 
	 
}