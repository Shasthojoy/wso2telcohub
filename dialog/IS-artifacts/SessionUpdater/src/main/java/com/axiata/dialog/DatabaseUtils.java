package com.axiata.dialog;

import com.axiata.dialog.entity.LoginHistory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;



/**
 * Created with IntelliJ IDEA.
 * User: Tharanga Ranaweera
 * Date: 8/07/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseUtils {

    private static volatile DataSource ussdDatasource = null;
    
   // private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Endpoints.class.getName());

    private static Log log = LogFactory.getLog(DatabaseUtils.class);

    public static void initializeDataSource() throws NamingException {
        if (ussdDatasource != null ) {
            return;
        }

        String statdataSourceName = "jdbc/CONNECT_DB";

        if (statdataSourceName != null) {
            try {
                Context ctx = new InitialContext();
                ussdDatasource = (DataSource) ctx.lookup(statdataSourceName);
            } catch (NamingException e) {
               //log.error(e);
               throw e;
            }

        }
    }
   
     public static void updateUSerStatus(String sessionID, String status) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO `clientstatus` (`SessionID`, `Status`) VALUES (?, ?);";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setString(1, sessionID);
                ps.setString(2, status);

                // LOG.info(sql);
                ps.execute();
                        
		} catch (NamingException ex) {
                 //Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
             } 
            catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();			
		}
            
    }
     
    public static void updateStatus(String sessionID, String status) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql =
		             "update `clientstatus` set "
		                     + "Status=? where " 
                                     + "SessionID=?;" ;
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setString(1, status);
                ps.setString(2, sessionID);

                ps.execute();
             
                        
            } catch (NamingException ex) {
                // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
             } 
            catch (SQLException e) {
                System.out.print(e.getMessage());
            } finally {
                connection.close();			
            }
            
    }
    
    public static void updatePinStatus(String sessionID, String status, String userpin) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql =
		             "update `clientstatus` set "
		                     + "Status=? , pin = ? where " 
                                     + "SessionID=?;" ;
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setString(1, status);
                ps.setString(2, userpin);
                ps.setString(3, sessionID);

                ps.execute();
             
                        
            } catch (NamingException ex) {
                // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
             } 
            catch (SQLException e) {
                System.out.print(e.getMessage());
            } finally {
                connection.close();			
            }
            
    }
    
     
    public static String getUSerStatus(String sessionID) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        String userStatus = null; 
        ResultSet rs = null;
        
        String sql =
		             "select Status "
		                     + "from `clientstatus` where " + "SessionID=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, sessionID);

                rs = ps.executeQuery();
            
                while (rs.next()) {
                    userStatus = rs.getString("Status");
                }
                        
		} catch (NamingException ex) {
                   // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            return userStatus;
     }
    

    
    public static Connection getUssdDBConnection() throws SQLException,NamingException {
        initializeDataSource();
        if (ussdDatasource != null) {
            return ussdDatasource.getConnection();
        } else {
            throw new SQLException("USSD Datasource not initialized properly");
        }
    }
    public static int readMultiplePasswordNoOfAttempts(String username) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        int noOfAttempts = 0;
        ResultSet rs = null;

        String sql = "select attempts from `multiplepasswords` where " + "username=?;";
        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                noOfAttempts = rs.getInt("attempts");
            }
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
        }
        return noOfAttempts;
    }

    private static boolean isFirstPinRequest(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        int count = 0;
        ResultSet rs = null;

        String sql = "select count(*) as total from `multiplepasswords` where " + "username=?;";
        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
        }
        return count == 0;
    }
    
    public static void updateMultiplePasswordNoOfAttempts(String username, int attempts) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        String sql = null;
        boolean isFirstPinRequest = isFirstPinRequest(username);
        if(isFirstPinRequest) {
            sql = "INSERT INTO `multiplepasswords` set " +
                    "attempts=?, " +
                    "username=?;"; 
        } else {
            sql = "update `multiplepasswords` set "
                + "attempts=? where "
                + "username=?;";
        }
        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, attempts);
            ps.setString(2, username);
            ps.execute();
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
        }
    }
    
    public static void deleteUser(String username) throws SQLException {

        Connection connection = null;
        String sql = "delete from `multiplepasswords` where " + "username=?;";
        try {
            connection = getUssdDBConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.execute();
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
        }
    }
	
	   public static List<LoginHistory> getLoginHistory(String userId, String application, Date fromDate, Date toDate) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        String userStatus = null;
        ResultSet rs = null;
        
        if (application.equalsIgnoreCase("__ALL__")) {
            application = "%";
        }
        
        List<LoginHistory> loghistory = new ArrayList();
        
        String sql = "SELECT id, reqtype, application_id, authenticated_user, isauthenticated, authenticators, ipaddress, created_date "
                + "FROM sp_login_history "
                + "WHERE application_id like ? "
                + "AND authenticated_user = ? "
                + "AND created_date between ? and ? "
                + "order by id desc";
        
        if ((application == null) || (application.isEmpty())) {
            application = "%";
        }
        
        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, application);
            ps.setString(2, userId);
            ps.setTimestamp(3, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(4, getEndOfDay(toDate));
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                 SimpleDateFormat sdf = new SimpleDateFormat("yyy-MMM-dd HH:mm:ss z");
                 sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                 String creationDate = sdf.format(rs.getTimestamp("created_date"));
                loghistory.add(new LoginHistory(rs.getString("application_id"), rs.getString("authenticated_user"),
                        (rs.getInt("isauthenticated") == 1) ? "SUCCESS" : "FAILED", rs.getString("ipaddress"),creationDate ));
            }
            
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
            
        }
        
        return loghistory;
    }
    
    public static List<String> getLoginApps(String userId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        List<String> apps = new ArrayList();
        ResultSet rs = null;
        
        String sql = "SELECT distinct application_id "
                + "FROM sp_login_history "
                + "WHERE authenticated_user = ?";
        
        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                apps.add(rs.getString(1));
            }
            
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            connection.close();
            
        }
        
        return apps;
    }
	
    public static Timestamp getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new java.sql.Timestamp(calendar.getTime().getTime());
    }

    public static String getMePinSessionID(String transactionId) throws SQLException{
        String sessionID=null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT session_id FROM mepin_transactions WHERE transaction_id = ?";

        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, transactionId);
            rs = ps.executeQuery();

            while (rs.next()) {
                sessionID= rs.getString(1);
            }

        } catch (NamingException ex) {
            log.error("Error while connecting to DB", ex);
        } catch (SQLException e) {
            log.error("Error in querying DB", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return sessionID;
    }
}
