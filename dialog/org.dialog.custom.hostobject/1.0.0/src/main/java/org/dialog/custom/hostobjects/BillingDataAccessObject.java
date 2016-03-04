package org.dialog.custom.hostobjects;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dialog.custom.hostobjects.internal.HostObjectComponent;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;


public class BillingDataAccessObject {

	private static volatile DataSource statDatasource = null;
	private static volatile DataSource apimgtDatasource = null;
	private static final String API_USAGE_TRACKING = "APIUsageTracking.";
	private static final String STAT_SOURCE_NAME = API_USAGE_TRACKING + "DataSourceName";
	private static final String APIMGT_SOURCE_NAME = "DataSourceName";
	private static final Log log = LogFactory.getLog(BillingDataAccessObject.class);

	public static void initializeDataSource() throws APIMgtUsageQueryServiceClientException {
		if (statDatasource != null && apimgtDatasource != null) {
			return;
		}
		APIManagerConfiguration config = HostObjectComponent.getAPIManagerConfiguration();
		String statdataSourceName = config.getFirstProperty(STAT_SOURCE_NAME);
		String apimgtdataSourceName = config.getFirstProperty(APIMGT_SOURCE_NAME);

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

		if (apimgtdataSourceName != null) {
			try {
				Context ctx = new InitialContext();
				apimgtDatasource = (DataSource) ctx.lookup(apimgtdataSourceName);
			} catch (NamingException e) {
				throw new APIMgtUsageQueryServiceClientException(
				                                                 "Error while looking up the data " +
				                                                         "source: " +
				                                                         apimgtdataSourceName);
			}

		}
	}

	public static void printAPIRequestSummary() throws SQLException, APIManagementException,
	                                           APIMgtUsageQueryServiceClientException {
		String sql = "select * from API_REQUEST_SUMMARY";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getStatsDBConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// api| api_version| version | apiPublisher | consumerKey|
				// userId| context | max_request_time |
				// \total_request_count | hostName | year | month | day | time
				System.out.println("=== Results for api :" + rs.getString("api") + " , ck :  " +
				                   rs.getString("consumerKey") + " , count : " +
				                   rs.getInt("total_request_count"));
			}

		} catch (SQLException e) {
			handleException("Error occurred while querying Request Summary", e);
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, conn, rs);
		}

	}
        
        public static void printSouthboundTraffic()throws SQLException, APIManagementException,
	                                           APIMgtUsageQueryServiceClientException {
            String sql = "select * from test_db";
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            
            try{
                conn = getStatsDBConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                log.debug("Print Southbound Reporting");
                while (rs.next()) {
			log.debug("=== Results for southbound Traffic :" + rs.getString("ENDPOINT") + " , endpoint :  " +
				                   rs.getString("CONSUMER_KEY") + " , key : " +
				                   rs.getString("TIME_CREATED"));
                }
                
            }catch (SQLException e) {
			handleException("Error occurred while querying Request Summary", e);
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, conn, rs);
		}
            
         }
        
        

    public static String getResponseTimeForAPI(String apiVersion) throws APIMgtUsageQueryServiceClientException, APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql =
                "select *  from API_RESPONSE_SUMMARY where api_version=? order by time desc limit 1;";
        Map<String, Integer> apiCount = new HashMap<String, Integer>();
        try {
            connection = getStatsDBConnection();
            ps = connection.prepareStatement(sql);
            log.debug("getResponseTimeForAPI for apiVersion---> "+apiVersion);
            ps.setString(1, apiVersion);
            log.debug("SQL (PS) ---> "+ps.toString());
            log.error("SQL (PS) ---> "+ps.toString());
            results = ps.executeQuery();
            while (results.next()) {
                return results.getString("serviceTime");
            }
        } catch (SQLException e) {
        	log.error("SQL Error in getResponseTimeForAPI");
        	log.error(e.getStackTrace());
            handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }
        return null;
    }

    public static List<APIResponseDTO> getAllResponseTimesForAPI(String apiVersion, String fromDate,
                                                                 String toDate) throws APIMgtUsageQueryServiceClientException, APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql =
                "select api_version,total_response_count AS count, serviceTime,STR_TO_DATE(time,'%Y-%m-%d') as date " +
                        "FROM API_RESPONSE_SUMMARY WHERE api_version=? AND (time BETWEEN ? AND ?);";
        List<APIResponseDTO> responseTimes = new ArrayList<APIResponseDTO>();
        try {
            connection = getStatsDBConnection();
            ps = connection.prepareStatement(sql);
            log.debug("getAllResponseTimesForAPI for apiVersion---> "+apiVersion);
            ps.setString(1, apiVersion);
            ps.setString(2, fromDate+" 00:00:00");
            ps.setString(3, toDate+" 23:59:59");
            log.debug("SQL (PS) ---> "+ps.toString());
            results = ps.executeQuery();
            while (results.next()) {
                APIResponseDTO resp = new APIResponseDTO();
                resp.setApiVersion(results.getString("api_version"));
                resp.setResponseCount(results.getInt("count"));
                resp.setServiceTime(results.getInt("serviceTime"));
                resp.setDate(results.getDate("date"));

                responseTimes.add(resp);
            }
        } catch (SQLException e) {
        	log.error("SQL Error in getAllResponseTimesForAPI");
        	log.error(e.getStackTrace());
            handleException("Error occurred while getting all response times for API", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }
        return responseTimes;
    }

	public static Map<String, Integer> getAPICountsForApplication(String consumerKey, String year,
	                                                              String month, String userId)
	                                                                                          throws APIManagementException,
	                                                                                          APIMgtUsageQueryServiceClientException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet results = null;
		String sql =
		             "select api_version,total_request_count as total "
		                     + "from  API_REQUEST_MONTHLY_SUMMARY where " + "month=? AND "
		                     + "consumerKey=?  AND " + "userId=?;";
		Map<String, Integer> apiCount = new HashMap<String, Integer>();
		try {
			connection = getStatsDBConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, year+"-"+month);
			ps.setString(2, consumerKey);
			ps.setString(3, userId);

			results = ps.executeQuery();
			while (results.next()) {
				apiCount.put(results.getString("api_version"), results.getInt("total"));
			}
		} catch (SQLException e) {
			handleException("Error occurred while getting Invocation count for Application", e);
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, connection, results);
		}

		return apiCount;
	}

	public static void printAPISubscriberTable() throws SQLException,
	                                            APIMgtUsageQueryServiceClientException {
		String sql = "select * from AM_SUBSCRIBER";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getApiMgtDBConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// api| api_version| version | apiPublisher | consumerKey|
				// userId| context | max_request_time |
				// \total_request_count | hostName | year | month | day | time
				System.out.println("=== Results for SUBSCRIBER_ID  :" + rs.getInt("SUBSCRIBER_ID") +
				                   " , USER_ID :  " + rs.getString("USER_ID") +
				                   " , DATE_SUBSCRIBED : " + rs.getDate("DATE_SUBSCRIBED"));
			}

		} catch (SQLException e) {
			log.error("Error occured while querying Request Summary", e);
			throw e;
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, conn, rs);
		}

	}

	public static Connection getStatsDBConnection() throws SQLException,
	                                               APIMgtUsageQueryServiceClientException {
		initializeDataSource();
		if (statDatasource != null) {
			return statDatasource.getConnection();
		} else {
			throw new SQLException("Statistics Datasource not initialized properly");
		}
	}

	public static Connection getApiMgtDBConnection() throws SQLException,
	                                                APIMgtUsageQueryServiceClientException {
		initializeDataSource();
		if (apimgtDatasource != null) {
			return apimgtDatasource.getConnection();
		} else {
			throw new SQLException("Statistics Datasource not initialized properly");
		}
	}

	public static List<String> getAllSubscriptions() throws APIMgtUsageQueryServiceClientException,
	                                                SQLException {
		String sql = "select USER_ID from AM_SUBSCRIBER";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> subscriber = new ArrayList<String>();
		try {
			conn = getApiMgtDBConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				subscriber.add(rs.getString("USER_ID"));
			}

		} catch (SQLException e) {
			log.error("Error occured while querying Request Summary", e);
			throw e;
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, conn, rs);
		}
		return subscriber;
	}

	private static void handleException(String msg, Throwable t) throws APIManagementException {
		log.error(msg, t);
		throw new APIManagementException(msg, t);
	}
        
        //Retriving number of Subscribers for each API
        public static int getNoOfSubscribers(String subscriber, String app,String apiName) throws APIMgtUsageQueryServiceClientException, APIManagementException{
            int noOfSubscribers = 0;
            
            Connection connection = null;
		PreparedStatement ps = null;
		ResultSet results = null;
		String sql =
		             "select subscriptionCount "
		                     + "from subscriptioncount where " 
                                     + "userId=? AND "
                                     + "api=? AND "
		                     + "applicationName=?;";
                try {
			connection = getStatsDBConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, subscriber);
                        ps.setString(2, apiName);
			ps.setString(3, app);

			results = ps.executeQuery();
                        
                        
      			while (results.next()) {
                               noOfSubscribers = noOfSubscribers + Integer.parseInt(results.getString("subscriptionCount"));
			}
		} catch (SQLException e) {
			handleException("Error occurred while getting Invocation count for Application", e);
		} finally {
			APIMgtDBUtil.closeAllConnections(ps, connection, results);
		}

                
            
            return noOfSubscribers;
        }

         //Retriving amount charged from end-user through payment API
         public static Set<PaymentRequestDTO> getPaymentAmounts(String subscriber, String app, String year,
                                                                String month) throws APIMgtUsageQueryServiceClientException,
                 APIManagementException {
//             int totalAmount = 0;
             String firstDay = year + "-" + month + "-01";

             Connection connection = null;
             PreparedStatement ps = null;
             ResultSet results = null;
             String sql = "select * from payment where userId=? AND application=? AND (date BETWEEN ? AND " +
                     "LAST_DAY(?));";

             Set<PaymentRequestDTO> requestSet = new HashSet<PaymentRequestDTO>();
             try {
                 connection = getStatsDBConnection();
                 ps = connection.prepareStatement(sql);
                 ps.setString(1, subscriber);
                 ps.setString(2, app);
                 ps.setString(3, firstDay);
                 ps.setString(4, firstDay);

                 results = ps.executeQuery();

                 while (results.next()) {
//                            totalAmount = totalAmount + Integer.parseInt(results.getString("amount"));
                     PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
                     paymentRequest.setUserId(results.getString("userId"));
                     paymentRequest.setApplicationName(results.getString("application"));
                     paymentRequest.setAmount(results.getBigDecimal("amount")); //TODO: make amount column int. add date column
                     paymentRequest.setDate(results.getDate("date"));
                     requestSet.add(paymentRequest);
                 }
             } catch (SQLException e) {
                 handleException("Error occurred while getting Invocation count for Application", e);
             } finally {
                 APIMgtDBUtil.closeAllConnections(ps, connection, results);
             }

             return requestSet;
         }

    public static int getApiId (APIIdentifier apiIdent) throws APIMgtUsageQueryServiceClientException,
            APIManagementException {
        Connection conn = null;
        int apiId = -1;
        try {
            conn = getApiMgtDBConnection();
            apiId = ApiMgtDAO.getAPIID(apiIdent, conn);
        } catch (SQLException e) {
            handleException("Error occured while getting API ID of API: " + apiIdent + " from the database", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(null, conn, null);
        }
        return apiId;
    }

    public static int getSubscriptionIdForApplicationAPI(int appId, APIIdentifier apiIdent) throws
            APIMgtUsageQueryServiceClientException, APIManagementException {
        String sql = "select SUBSCRIPTION_ID from AM_SUBSCRIPTION where APPLICATION_ID=? AND API_ID=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> subscriber = new ArrayList<String>();
        int subscriptionId = -1;
        try {
            conn = getApiMgtDBConnection();
            int apiId = ApiMgtDAO.getAPIID(apiIdent, conn);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, appId);
            ps.setInt(2, apiId);
            rs = ps.executeQuery();
            if (rs.next()) {
                subscriptionId = rs.getInt("SUBSCRIPTION_ID");
            }
            if (subscriptionId == -1) {
                String msg = "Unable to find the subscription ID for API: " + apiIdent + " in the database";
                log.error(msg);
                throw new APIManagementException(msg);
            }

        } catch (SQLException e) {
            handleException("Error occured while getting subscription ID for API: " + apiIdent + " from the database", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }
        return subscriptionId;
    }
        
}
