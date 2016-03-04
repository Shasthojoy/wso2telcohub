package com.mycompany.mavenproject1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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



/**
 * Created with IntelliJ IDEA
 * User: Tharanga Ranaweera
 * Date: 8/07/14
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseUtils {

    private static volatile DataSource ussdDatasource = null;
    
   // private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Endpoints.class.getName());

    //private static Log log = LogFactory.getLog(DatabaseUtils.class);

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
     
    public static String getUSerPIN(String sessionID) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        String pin = null; 
        ResultSet rs = null;
        
        String sql =
		             "select pin "
		                     + "from `pin` where " + "SessionID=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, sessionID);

                rs = ps.executeQuery();
            
                while (rs.next()) {
                    pin = rs.getString("pin");
                }
                        
		} catch (NamingException ex) {
                   // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            return pin;
     }
    
    //insert initial Entry
    public static void insertMultiplePasswordPIN(String username) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
 
        
        String sql = "INSERT INTO `multiplepasswords` (`username`, `attempts`) VALUES (?, ?);";
        try {
            try {
                connection = getUssdDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
                        ps = connection.prepareStatement(sql);
			
			ps.setString(1, username);
                        ps.setInt(2, 1);

			ps.execute();
                        
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		} finally {
			connection.close();
		}
    }

    
    //Update PIN
    public static void updateMultiplePasswordPIN(String username,int pin) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql =
		             "update `multiplepasswords` set "
		                     + "pin=? where " 
                                     + "username=?;" ;
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setInt(1, pin);
                ps.setString(2, username);

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
    
    //Update no of attempts
    public static void updateMultiplePasswordNoOfAttempts(String username,int attempts) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql =
		             "update `multiplepasswords` set "
		                     + "attempts=? where " 
                                     + "username=?;" ;
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setInt(1, attempts);
                ps.setString(2, username);

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
      //Read PIN  
      public static int readMultiplePasswordPIN(String username) throws SQLException{
        
       Connection connection = null;
        PreparedStatement ps = null;
        int pin = 0; 
        ResultSet rs = null;
        
        String sql =
		             "select pin "
		                     + "from `multiplepasswords` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

                rs = ps.executeQuery();
            
                while (rs.next()) {
     
                    pin = rs.getInt("pin");
                }
                        
		} catch (NamingException ex) {
                   // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            return pin;
    }
    
    //Read Attempts
    public static int readMultiplePasswordNoOfAttempts(String username) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        int noOfAttempts = 0; 
        ResultSet rs = null;
        
        String sql =
		             "select attempts "
		                     + "from `multiplepasswords` where " + "username=?;";
       
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
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            return noOfAttempts;
    }
    
    public static boolean isExistingUser(String username) throws SQLException{
        boolean isUser = false;
        
        Connection connection = null;
        PreparedStatement ps = null;
        String usernameDB = "noUser";
        ResultSet rs = null;
        
        String sql =
		             "select username "
		                     + "from `multiplepasswords` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

                rs = ps.executeQuery();
            
                while (rs.next()) {
     
                    usernameDB = rs.getString("username");
                }
                        
		} catch (NamingException ex) {
                   // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            if (usernameDB.equals(username) ){
                
                isUser = true;
            }else{
                isUser = false;
            }
            return isUser;

      }
    
    //Delete Entry
    public static void deleteUser(String username) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        int noOfAttempts = 0; 
        ResultSet rs = null;
        
        String sql =
		             "delete "
		                     + "from `multiplepasswords` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

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
    
    public static String getUSerStatus(String username) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        String userStatus = null; 
        ResultSet rs = null;
        
        String sql =
		             "select status "
		                     + "from `regstatus` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

                rs = ps.executeQuery();
            
                while (rs.next()) {
                    userStatus = rs.getString("status");
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
    
    
        //insert initial Entry
     public static void insertUserStatus(String username,String status) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;
 
        
        String sql = "INSERT INTO `regstatus` (`username`, `status`) VALUES (?, ?);";
        try {
            try {
                connection = getUssdDBConnection();
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
                        ps = connection.prepareStatement(sql);
			
			ps.setString(1, username);
                        ps.setString(2, status);

			ps.execute();
                        
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		} finally {
			connection.close();
		}
       }
     
     
    public static boolean isExistingUserStatus(String username) throws SQLException{
        boolean isUser = false;
        
        Connection connection = null;
        PreparedStatement ps = null;
        String usernameDB = "noUser";
        ResultSet rs = null;
        
        String sql =
		             "select username "
		                     + "from `regstatus` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

                rs = ps.executeQuery();
            
                while (rs.next()) {
     
                    usernameDB = rs.getString("username");
                }
                        
		} catch (NamingException ex) {
                   // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (SQLException e) {
                    System.out.print(e.getMessage());
		} finally {
                        connection.close();
                        
		}
            
            if (usernameDB.equals(username) ){
                
                isUser = true;
            }else{
                isUser = false;
            }
            return isUser;

      }
    
    //Delete Entry
    public static void deleteUserStatus(String username) throws SQLException{
        
        Connection connection = null;
        PreparedStatement ps = null;
        int noOfAttempts = 0; 
        ResultSet rs = null;
        
        String sql =
		             "delete "
		                     + "from `regstatus` where " + "username=?;";
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);
            
                ps.setString(1, username);

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
    
     public static void updateRegStatus(String username, String status) throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        
        String sql =
		             "update `regstatus` set "
		                     + "status=? where " 
                                     + "username=?;" ;
       
            try {
                connection = getUssdDBConnection();
            
                ps = connection.prepareStatement(sql);

                ps.setString(1, status);
                ps.setString(2, username);

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
    
    static Integer getPendingUSSDRequestType(String msisdn) throws SQLException {
        
        Connection connection = null;
        PreparedStatement ps = null;
        Integer requestType = null;

        String sql = "select requesttype"
                        + " from `pendingussd` where msisdn=?;";

        try {
            connection = getUssdDBConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, msisdn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requestType = rs.getInt("requesttype");
                break;
            }
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
//        String json = "{\"requestType\":" + requestType + ",\"sessionId\":\"" + sessionId + "\"}";
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonElement jelem = gson.fromJson(json, JsonElement.class);
//        return jelem.getAsJsonObject();
        return requestType;
    }

    static int saveRequestType(String msisdn, Integer requestType) throws SQLException,NamingException {
        Connection connection = null;
//        String sql = "insert into pendingussd (msisdn, requesttype) values (?,?)";
        String sql = "insert into pendingussd (msisdn, requesttype) values (?,?) ON DUPLICATE KEY UPDATE requesttype=VALUES(requesttype)";
        try {
            connection = getUssdDBConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, msisdn);
            ps.setInt(2, requestType);
            ps.executeUpdate();
            return 1;
        } catch (NamingException ex) {
            // Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
        return -1;
    }

    static void deleteRequestType(String msisdn) throws SQLException {
        Connection connection = null;
        String sql = "delete from pendingussd where msisdn = ?";
        try {
            connection = getUssdDBConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, msisdn);
            ps.executeUpdate();
        } catch (NamingException ex) {
        } finally {
            if(connection != null) {
                connection.close();
            }
        }
    }
    
    public static Connection getUssdDBConnection() throws SQLException,NamingException {
        initializeDataSource();
        if (ussdDatasource != null) {
            return ussdDatasource.getConnection();
        } else {
            throw new SQLException("USSD Datasource not initialized properly");
        }
    }
    
    
}
