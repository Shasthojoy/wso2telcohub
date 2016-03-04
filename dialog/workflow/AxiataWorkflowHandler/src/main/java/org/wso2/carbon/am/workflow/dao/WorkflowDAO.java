package org.wso2.carbon.am.workflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;
import org.wso2.carbon.am.axiata.workflow.internal.WorkflowComponent;
import org.wso2.carbon.am.axiata.workflow.model.ApplicationApprovalAuditRecord;
import org.wso2.carbon.am.axiata.workflow.model.SubscriptionApprovalAuditRecord;

/**
 * The DAO class to handle Axiata workflow related tasks.
 */
public class WorkflowDAO {
	
	private static final Log log = LogFactory.getLog(WorkflowDAO.class);
	
	private static final String API_USAGE_TRACKING = "APIUsageTracking.";
	private static final String STAT_SOURCE_NAME = API_USAGE_TRACKING + "DataSourceName";
	private static volatile DataSource statDatasource = null;

	public static void initializeDataSource() throws APIMgtUsageQueryServiceClientException {
		if (statDatasource != null) {
			return;
		}
		APIManagerConfiguration config = WorkflowComponent.getAPIManagerConfiguration();
		String statdataSourceName = config.getFirstProperty(STAT_SOURCE_NAME);

		if (statdataSourceName != null) {
			try {
				Context ctx = new InitialContext();
				statDatasource = (DataSource) ctx.lookup(statdataSourceName);
			} catch (NamingException e) {
				throw new APIMgtUsageQueryServiceClientException(
				                                                 "Error while looking up the data " +
				                                                         "source: " +
				                                                         statdataSourceName);
			}

		}
	}
	
	public static Connection getStatsDBConnection() throws SQLException, 
															APIMgtUsageQueryServiceClientException {
		initializeDataSource();
		if (statDatasource != null) {
			return statDatasource.getConnection();
		} else {
			throw new SQLException(
					"Statistics Datasource not initialized properly.");
		}
	}
	
	public static void insertAppApprovalAuditRecord(ApplicationApprovalAuditRecord record) throws APIManagementException,
															APIMgtUsageQueryServiceClientException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getStatsDBConnection();
            String query = "INSERT INTO app_approval_audit (APP_NAME, APP_CREATOR, APP_STATUS, "
            		+ "APP_APPROVAL_TYPE, COMPLETED_BY_ROLE, COMPLETED_BY_USER) " 
            		+ "VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, record.getAppName());
            ps.setString(2, record.getAppCreator());
            ps.setString(3, record.getAppStatus());
            ps.setString(4, record.getAppApprovalType());
            ps.setString(5, record.getCompletedByRole());
            ps.setString(6, record.getCompletedByUser());
            ps.execute();
            
        } catch (SQLException e) {
            handleException("Error in inserting application approval audit record : " + e.getMessage(), e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, null);
        }
	}
	
	public static void insertSubApprovalAuditRecord(SubscriptionApprovalAuditRecord record) throws APIManagementException,
															APIMgtUsageQueryServiceClientException {
		
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getStatsDBConnection();
            String query = "INSERT INTO sub_approval_audit (API_PROVIDER, API_NAME, API_VERSION, APP_ID, "
            		+ "SUB_STATUS, SUB_APPROVAL_TYPE, COMPLETED_BY_ROLE, COMPLETED_BY_USER) " 
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, record.getApiProvider());
            ps.setString(2, record.getApiName());
            ps.setString(3, record.getApiVersion());
            ps.setInt(4, record.getAppId());
            ps.setString(5, record.getSubStatus());
            ps.setString(6, record.getSubApprovalType());
            ps.setString(7, record.getCompletedByRole());
            ps.setString(8, record.getCompletedByUser());
            ps.execute();
            
        } catch (SQLException e) {
            handleException("Error in inserting subscription approval audit record : " + e.getMessage(), e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, null);
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
