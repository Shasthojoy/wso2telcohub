package org.dialog.custom.hostobjects;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.impl.utils.APIMgtDBUtil;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TaxDataAccessObject {

    private static final Log log = LogFactory.getLog(TaxDataAccessObject.class);

    public static List<Tax> getTaxesForSubscription(int applicationId, int apiId) throws APIManagementException,
            APIMgtUsageQueryServiceClientException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql = "SELECT type,effective_from,effective_to,value FROM tax, subscription_tax " +
                "WHERE subscription_tax.application_id=? AND subscription_tax.api_id=? AND tax.type=subscription_tax.tax_type ";

        List<Tax> taxes = new ArrayList<Tax>();
        try {
            connection = BillingDataAccessObject.getStatsDBConnection();
            ps = connection.prepareStatement(sql);
            log.debug("getTaxesForSubscription for applicationId---> " + applicationId + " apiId--> " + apiId);
            ps.setInt(1, applicationId);
            ps.setInt(2, apiId);
            log.debug("SQL (PS) ---> " + ps.toString());
            results = ps.executeQuery();
            while (results.next()) {
                Tax tax = new Tax();
                tax.setType(results.getString("type"));
                tax.setEffective_from(results.getDate("effective_from"));
                tax.setEffective_to(results.getDate("effective_to"));
                tax.setValue(results.getBigDecimal("value"));
                taxes.add(tax);
            }
        } catch (SQLException e) {
            log.error("SQL Error in getTaxesForSubscription");
            log.error(e.getStackTrace());
            handleException("Error occurred while getting Taxes for Subscription", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }
        return taxes;
    }

    public static Set<APIRequestDTO> getAPIRequestTimesForSubscription(String apiName, String apiVersion,
                                                                       String consumerKey, short year, short month,
                                                                       String userId) throws
            APIMgtUsageQueryServiceClientException, APIManagementException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql = "SELECT api_version,total_request_count AS count,STR_TO_DATE(time," +
                "'%Y-%m-%d') as date FROM API_REQUEST_SUMMARY " +
                "WHERE year=? AND month=? AND consumerKey=? AND api=? AND version=? AND userId=?;";

        Set<APIRequestDTO> apiRequests = new HashSet<APIRequestDTO>();
        try {
            connection = BillingDataAccessObject.getStatsDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setShort(1, year);
            ps.setShort(2, month);
            ps.setString(3, consumerKey);
            ps.setString(4, apiName);
            ps.setString(5, apiVersion);
            ps.setString(6, userId);

            results = ps.executeQuery();
            while (results.next()) {
                APIRequestDTO req = new APIRequestDTO();
                req.setApiVersion(results.getString("api_version"));
                req.setRequestCount(results.getInt("count"));
                req.setDate(results.getDate("date"));

                apiRequests.add(req);
            }
        } catch (SQLException e) {
            handleException("Error occurred while getting Request Times for Subscription", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

        return apiRequests;
    }

    public static Map<String, List<APIRequestDTO>> getAPIRequestTimesForApplication(String consumerKey, short year,
                                                                                 short month, String userId)
            throws APIManagementException, APIMgtUsageQueryServiceClientException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
        String sql = "SELECT api_version,total_request_count AS count,STR_TO_DATE(time,'%Y-%m-%d') as date FROM API_REQUEST_SUMMARY " +
                "WHERE year=? AND month=? AND consumerKey=?  AND userId=?;";

        Map<String, List<APIRequestDTO>> apiRequests = new HashMap<String, List<APIRequestDTO>>();
        try {
            connection = BillingDataAccessObject.getStatsDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setShort(1, year);
            ps.setShort(2, month);
            ps.setString(3, consumerKey);
            ps.setString(4, userId);

            results = ps.executeQuery();
            while (results.next()) {
                APIRequestDTO req = new APIRequestDTO();
                req.setApiVersion(results.getString("api_version"));
                req.setRequestCount(results.getInt("count"));
                req.setDate(results.getDate("date"));
                if (apiRequests.containsKey(req.getApiVersion())) {
                    apiRequests.get(req.getApiVersion()).add(req);
                } else {
                    List<APIRequestDTO> list = new ArrayList<APIRequestDTO>();
                    list.add(req);
                    apiRequests.put(req.getApiVersion(), list);
                }
            }
        } catch (SQLException e) {
            handleException("Error occurred while getting Request Times for Application", e);
        } finally {
            APIMgtDBUtil.closeAllConnections(ps, connection, results);
        }

        return apiRequests;
    }

    private static void handleException(String msg, Throwable t) throws APIManagementException {
        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }

    public static void main(String[] args) {
        try {
            List<Tax> taxList = getTaxesForSubscription(00,25);
            for (int i = 0; i < taxList.size(); i++) {
                Tax tax = taxList.get(i);
                System.out.println(tax.getType() + " ~ " + tax.getEffective_from() + " ~ " + tax.getEffective_to() + " ~ " + tax.getValue());
            }

            Map<String, List<APIRequestDTO>> reqMap = getAPIRequestTimesForApplication("yx1eZTmtbBaYqfIuEYMVgIKonSga", (short)2014, (short)1, "admin");
            System.out.println(reqMap);

        } catch (APIManagementException e) {
            e.printStackTrace();
        } catch (APIMgtUsageQueryServiceClientException e) {
            e.printStackTrace();
        }
    }
}
