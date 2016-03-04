package org.wso2.carbon.apimgt.axiata.dialog.verifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;


/**
 * Created with IntelliJ IDEA.
 * User: Tharanga Ranaweera/
 * Date: 2/25/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseUtils {

    private static volatile DataSource statDatasource = null;
    private static volatile DataSource amDatasource = null;

    private static final Log log = LogFactory.getLog(DatabaseUtils.class);

    //private static HashMap<String, String> msisdnMAP = new HashMap<String, String>();
    private static List<String> msisdn = new ArrayList<String>();
    private static List<String> whitelistedmsisdn = new ArrayList<String>();
    private static List<String> subscriptionList = new ArrayList<String>();
    private static List<String> subscriptionIDs = new ArrayList<String>();

    private static int currentNo = 3;

    public static void initializeDataSource() throws NamingException {
        if (statDatasource != null) {
            return;
        }

        String statdataSourceName = "jdbc/WSO2AM_STATS_DB";

        if (statdataSourceName != null) {
            try {
                Context ctx = new InitialContext();
                statDatasource = (DataSource) ctx.lookup(statdataSourceName);
            } catch (NamingException e) {
                log.error(e);
                throw e;
            }

        }
    }

    public static void initializeAMDataSource() throws NamingException {
        if (amDatasource != null) {
            return;
        }

        String amDataSourceName = "jdbc/WSO2AM_DB";

        if (amDataSourceName != null) {
            try {
                Context ctx = new InitialContext();
                amDatasource = (DataSource) ctx.lookup(amDataSourceName);
            } catch (NamingException e) {
                log.error(e);
                throw e;
            }

        }
    }


    public static String getAPIId(String apiName) throws NamingException, SQLException {

        String apiId = null;

        /// String sql = "select * from am_subscription";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String sql =
                    "select API_ID "
                            + "from AM_API where " + "API_NAME=?;";


            conn = getAMDBConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, apiName);

            rs = ps.executeQuery();

            while (rs.next()) {
                apiId = rs.getString("API_ID");
            }


        } catch (SQLException e) {
            log.error("Error occured while writing southbound record.", e);
            throw e;
        } catch (NamingException e) {
            log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }

        return apiId;
    }


    public static String getSubscriptionId(String apiID, String applicationID) throws NamingException, SQLException {

        String subscriptionId = null;

        // String sql = "select * from am_subscription";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String sql =
                    "select SUBSCRIPTION_ID "
                            + "from AM_SUBSCRIPTION where " + "API_ID=? AND "
                            + "APPLICATION_ID=?;";


            conn = getAMDBConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, apiID);
            ps.setString(2, applicationID);

            rs = ps.executeQuery();

            subscriptionIDs.clear();

            while (rs.next()) {
                subscriptionIDs.add(rs.getString("SUBSCRIPTION_ID"));
            }


        } catch (SQLException e) {
            log.error("Error occured while writing southbound record.", e);
            throw e;
        } catch (NamingException e) {
            log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }

        return subscriptionIDs.get(0);
    }

    public static List<String> ReadBlacklistNumbers(String apiName) throws SQLException, NamingException {

        String sql = "select * from blacklistmsisdn where " + "API_NAME=?;";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getStatsDBConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, apiName);

            rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    String msisdnTable = rs.getString("MSISDN").replace("tel3A+", "");
                    log.debug("msisdn in the table = " + msisdnTable);
                    msisdn.add(msisdnTable);

                }
            }

        } catch (SQLException e) {
            log.error("Error occured while writing southbound record.", e);
            throw e;
        } catch (NamingException e) {
            log.error("Error while finding the Datasource..", e);
            throw e;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }

        return msisdn;


    }

    public static List<String> ReadWhitelistNumbers(String subscriptionID) throws SQLException, NamingException {


        String sql =
                "select msisdn "
                        + "from subscription_WhiteList where " + "subscriptionID=?;";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getStatsDBConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, subscriptionID);

            rs = ps.executeQuery();
            whitelistedmsisdn.clear();
            if (rs != null) {


                while (rs.next()) {
                    String msisdnTable = rs.getString("msisdn").replace("tel3A+", "");
                    log.info("msisdn in the table = " + msisdnTable);
                    whitelistedmsisdn.add(msisdnTable);

                }
            }

        } catch (SQLException e) {
            log.error("Error occured while writing southbound record.", e);
            throw e;
        } catch (NamingException e) {
            log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }
        return whitelistedmsisdn;
    }

    public static WhiteListResult checkWhiteListed(String MSISDN, String applicationId, String subscriptionId, String apiId) throws SQLException, NamingException {

        WhiteListResult whiteListResult = null;


        String sql = "SELECT * FROM `subscription_WhiteList` WHERE \n" +
                //check with all value mean MSISDN to subscription
                "(`subscriptionID` = ? AND `msisdn` = ? AND `api_id` = ? AND `application_id` =  ?) OR \n" +
                //check with out subscription. but match API,MSISDN and ApplicationID
                "(`subscriptionID` IS NULL  AND `msisdn` = ? AND `api_id` = ? AND `application_id` =  ?) OR \n" +
                //check with only subscription ID and MSISDN
                "(`subscriptionID` = ? AND `msisdn` = ? AND `api_id` IS NULL  AND `application_id` IS NULL ) OR \n" +
                //match specific MSISDN to whole application
                "(`subscriptionID` IS NULL  AND `msisdn` = ? AND `api_id` IS NULL  AND `application_id` =  ?) OR \n" +
                //Match applicaiton only. it mean application can use any API, MSISDN without whitelist individual
                "(`subscriptionID` IS NULL  AND `msisdn` IS NULL  AND `api_id` IS NULL  AND `application_id` =  ?) OR \n" +
                //Match application's API only. it mean application can use specific API with any MSISDN without whitelist individual
                "(`subscriptionID` IS NULL  AND `msisdn` IS NULL  AND `api_id` = ? AND `application_id` =  ?)  LIMIT 0,1 ";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            conn = getStatsDBConnection();
            ps = conn.prepareStatement(sql);

            //"(`subscriptionID` = ? AND `msisdn` = ? AND `api_id` = ? AND `application_id` =  >) OR \n" +
            ps.setString(1, subscriptionId);
            ps.setString(2, MSISDN);
            ps.setString(3, apiId);
            ps.setString(4, applicationId);

            //"(`subscriptionID` = null AND `msisdn` = ? AND `api_id` = ? AND `application_id` =  ?) OR\n"
            ps.setString(5, MSISDN);
            ps.setString(6, apiId);
            ps.setString(7, applicationId);

            // "(`subscriptionID` = ? AND `msisdn` = ? AND `api_id` = null AND `application_id` =  null) OR \n" +
            ps.setString(8, subscriptionId);
            ps.setString(9, MSISDN);

            // "(`subscriptionID` = null AND `msisdn` = ? AND `api_id` = null AND `application_id` =  ?) OR \n" +
            ps.setString(10, MSISDN);
            ps.setString(11, applicationId);

            //"(`subscriptionID` = null AND `msisdn` = null AND `api_id` = null AND `application_id` =  ?) OR \n" +
            ps.setString(12, applicationId);

            // "(`subscriptionID` = null AND `msisdn` = null AND `api_id` = ? AND `application_id` =  ?)  ";
            ps.setString(13, apiId);
            ps.setString(14, applicationId);

            rs = ps.executeQuery();
            if (rs != null) {


                while (rs.next()) {

                    whiteListResult = new WhiteListResult();
                    String msisdnTable = rs.getString("msisdn");
                    if (msisdnTable != null) {
                        msisdnTable = msisdnTable.replace("tel3A+", "");
                        msisdnTable = msisdnTable.replace("tel:+", "");
                        msisdnTable = msisdnTable.replace("tel:", "");
                    }

                    whiteListResult.setApi_id(rs.getString("api_id"));
                    whiteListResult.setApplication_id(rs.getString("application_id"));
                    whiteListResult.setSubscriptionID(rs.getString("subscriptionID"));
                    whiteListResult.setMsisdn(msisdnTable);

                }
            }
//            log.info(ps);


        } catch (SQLException e) {
            log.error("Error occured while writing southbound record.", e);
            throw e;
        } catch (NamingException e) {
            log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, conn, rs);
        }

        return whiteListResult;
    }

    public static List<String> ReadSubscriptionNumbers(String subscriber, String app, String api) throws APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql =
                "select MSISDN "
                        + "from subscriptionmsisdn where "
                        + "userID=? AND "
                        + "application=? AND "
                        + "api=?;";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, subscriber);
            ps.setString(2, app);
            ps.setString(3, api);

            results = ps.executeQuery();


            while (results.next()) {
                subscriptionList.add(results.getString("MSISDN"));
            }
        } catch (SQLException e) {
            handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }
        return subscriptionList;
    }


    public static void UpdateSubscriptionNumbers(String subscriber, String app, String api, String updatedSubscriberCount) throws APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql =
                "update subscriptionCount set "
                        + "subscriptionCount=? where "
                        + "userId=? AND "
                        + "api=? AND "
                        + "applicationName=?;";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, updatedSubscriberCount);
            ps.setString(2, subscriber);
            ps.setString(3, api);
            ps.setString(4, app);

            ps.execute();

        } catch (SQLException e) {
            handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

    }

    public static void SubscribeUser(String subscriber, String app, String api, String msisdn) throws APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;


        String sql = "INSERT INTO `dialg_stats`.`subscriptionmsisdn` (`userID`, `api`, `application`, `MSISDN`) VALUES (?, ?, ?, ?);";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);

            ps.setString(1, subscriber);
            ps.setString(2, api);
            ps.setString(3, app);
            ps.setString(4, msisdn);

            ps.execute();

        } catch (SQLException e) {
            handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

    }

    public static void writeAmount(String userID, String application, String amount, String msisdn) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        String sql = "INSERT INTO `dialg_stats`.`payment` (`application`, `amount`, `userID`, `MSISDN`, `Date`) VALUES (?, ?, ?, ?, CURDATE());";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);

            ps.setString(1, application);
            ps.setString(2, amount);
            ps.setString(3, userID);
            ps.setString(4, msisdn);

            ps.execute();

        } catch (SQLException e) {
            //handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

    }

    public static void writeSubscription(String userID, String application, String api) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        String sql = "INSERT INTO `dialg_stats`.`subscriptioncount` (`api`, `subscriptionCount`, `userId`, `applicationName`) VALUES (?, ?, ?, ?);";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);

            ps.setString(1, api);
            ps.setString(2, "1");
            ps.setString(3, userID);
            ps.setString(4, application);

            ps.execute();

        } catch (SQLException e) {
            //handleException("Error occurred while getting Invocation count for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

    }

    public static String getSubscriptionCount(String subscriber, String app, String api) throws APIManagementException, SQLException {
        Connection connection = null;
        String subscriptionCount = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql =
                "select subscriptionCount "
                        + "from subscriptioncount where "
                        + "userId=? AND "
                        + "applicationName=? AND "
                        + "api=?;";
        try {
            try {
                connection = getStatsDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps = connection.prepareStatement(sql);
            ps.setString(1, subscriber);
            ps.setString(2, app);
            ps.setString(3, api);

            results = ps.executeQuery();

            while (results.next()) {
                subscriptionCount = results.getString("subscriptionCount");
            }


        } catch (SQLException e) {
            handleException("Error occurred while getting Invocation count for Application", e);
            return null;
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }
        return subscriptionCount;
    }

    public static int next() {
        if (currentNo == Integer.MAX_VALUE) {
            currentNo = 0;
        }
        return currentNo++;
    }

    public static Connection getStatsDBConnection() throws SQLException, NamingException {
        initializeDataSource();
        if (statDatasource != null) {
            return statDatasource.getConnection();
        } else {
            throw new SQLException("Statistics Datasource not initialized properly");
        }
    }

    public static Connection getAMDBConnection() throws SQLException, NamingException {
        initializeAMDataSource();
        if (amDatasource != null) {
            return amDatasource.getConnection();
        } else {
            throw new SQLException("AM Datasource not initialized properly");
        }
    }

    private static void handleException(String msg, Throwable t) throws APIManagementException {
        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }
}
