package com.axiata.dialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


/**
 * Created with IntelliJ IDEA.
 * User: Tharanga Ranaweera
 * Date: 2/25/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseUtils {

    private static volatile DataSource statDatasource = null;
    static final String USER = "root";
    static final String PASS = "root";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private static List<String> msisdn = new ArrayList<String>();
    private static int currentNo = 14;

    public static void initializeDataSource() throws NamingException, SQLException {

            if (statDatasource != null ) {
                return;
            }
            //Connection conn = null;
            Statement stmt = null;
            Connection conn = null;
            
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            
            statDatasource = ds;
    }

    
     public static List<String> WriteBlacklistNumbers(String userMSISDN,String apiID,String apiName, String userID) throws SQLException, NamingException {
        System.out.println((userMSISDN));
        
        String sql = "INSERT INTO `dialg_stats`.`blacklistmsisdn` (`MSISDN`,`API_ID`,`API_NAME`,`USER_ID`) VALUES (?,?,?,?);";
        Connection conn = null;
        PreparedStatement ps = null;
        
       
        try {
            conn = getStatsDBConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1,userMSISDN);
            ps.setString(2,apiID);
            ps.setString(3,apiName);
            ps.setString(4,userID);
            
            ps.execute();
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            throw e;
        } catch (NamingException e) {
          //  log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            if(msisdn.isEmpty()){
                return null;
            }
            else{
                return msisdn;
            }
        }
        
    }
    
    public static List<String> WriteWhitelistNumbers(String userMSISDN,String SubscriptionID, String apiID, String applicationID) throws SQLException, NamingException {
        System.out.println((userMSISDN));
        
        String sql = "INSERT INTO `dialg_stats`.`subscription_whitelist` (`MSISDN`,`subscriptionID`,`api_id`,`application_id`) VALUES (?,?,?,?);";
        Connection conn = null;
        PreparedStatement ps = null;
        
       
        try {
            conn = getStatsDBConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1,userMSISDN);
            ps.setString(2,SubscriptionID);
            ps.setString(3,apiID);
            ps.setString(4,applicationID);
            
            ps.execute();
            
           

        } catch (SQLException e) {
            System.out.println(e.toString());
            throw e;
        } catch (NamingException e) {
          //  log.error("Error while finding the Datasource.", e);
            throw e;
        } finally {
            if(msisdn.isEmpty()){
                return null;
            }
            else{
                return msisdn;
            }
        }
        
    } 
     
    public static Connection getStatsDBConnection() throws SQLException,NamingException {
        initializeDataSource();
        if (statDatasource != null) {
            return statDatasource.getConnection();
        } else {
            throw new SQLException("Statistics Datasource not initialized properly");
        }
    }
    
    public static int next() {
        if(currentNo==Integer.MAX_VALUE) {
            currentNo = 0;
        }
        return currentNo++;
    }
}
