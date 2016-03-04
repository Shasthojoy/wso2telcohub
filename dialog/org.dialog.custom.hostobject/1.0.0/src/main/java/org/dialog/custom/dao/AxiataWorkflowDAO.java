package org.dialog.custom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;

/**
 * The DAO class to handle Axiata workflow related tasks.
 */
public class AxiataWorkflowDAO {
	
	private static final Log log = LogFactory.getLog(AxiataWorkflowDAO.class);

	/**
	 * Update the tier for the specified subscription.
	 * 
	 * @param subscriptionId
	 * @param tierId
	 * @throws APIManagementException
	 */
    public void updateSubscriptionTier(String subscriptionId, String tierId) throws APIManagementException {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = APIMgtDBUtil.getConnection();
            String query = "UPDATE AM_SUBSCRIPTION SET TIER_ID=?" +
                           " WHERE SUBSCRIPTION_ID=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, tierId);
            ps.setInt(2, Integer.parseInt(subscriptionId));
            ps.executeUpdate();
            
        } catch (SQLException e) {
            handleException("Error in updating subscription tier : " + e.getMessage(), e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }
    }
    
    /**
	 * Update the tier for the specified application.
	 * 
	 * @param applicationId
	 * @param tierId
	 * @throws APIManagementException
	 */
    public void updateApplicationTier(String applicationId, String tierId) throws APIManagementException {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = APIMgtDBUtil.getConnection();
            String query = "UPDATE AM_APPLICATION SET APPLICATION_TIER=?" +
                           " WHERE APPLICATION_ID=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, tierId);
            ps.setInt(2, Integer.parseInt(applicationId));
            ps.executeUpdate();
            
        } catch (SQLException e) {
            handleException("Error in updating application tier : " + e.getMessage(), e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }
    }
    
    /**
     * Handle exception.
     * @param msg
     * @param t
     * @throws APIManagementException
     */
    private static void handleException(String msg, Throwable t) throws APIManagementException {
        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }
}
