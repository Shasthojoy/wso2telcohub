package com.dialog.util;
import java.sql.*;

import oracle.jdbc.pool.*;


public class DBOracleControllerPool extends DBController{

    private OracleConnectionCacheImpl s_ConnectionPool;  // connection pool var.
	
    /**
    * The database controller is initialized according to the parameters
    * set earlier using the static variables
    */
	public void Initialize() throws DBException {
		//		Load class incase faliure
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");			
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		}		
	}
    public void Initialize1() throws DBException {
        try {
						
            OracleConnectionPoolDataSource oracleDataSource =
                                        new OracleConnectionPoolDataSource();
	        // Sets the connection URL
	        oracleDataSource.setURL(m_dbURL);
	        // Sets the user name
	        oracleDataSource.setUser(m_userName);
	        // Sets the password
	        oracleDataSource.setPassword(m_password);
			
	        // Create a  connection  pool
            s_ConnectionPool = new OracleConnectionCacheImpl();
            s_ConnectionPool.setConnectionPoolDataSource(oracleDataSource);
		
            s_ConnectionPool.setMaxLimit(m_maxConnections);
			
            s_ConnectionPool.setCacheScheme( OracleConnectionCacheImpl.FIXED_WAIT_SCHEME);
			s_ConnectionPool.setLoginTimeout(5);
			
            if (s_ConnectionPool != null ){
	            //SystemConfig.getLogger("root").debug("Orcle DB Connection pool created");
            }else{
                throw new DBException("ERROR : Create DB Connection pool");
            }
        }catch(SQLException e){
                throw new DBException("ERROR : Create DB Connection pool" + e.getMessage());
        }
        
		//		Load class incase faliure
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		}
    }

    /**
    * From the connection pool the Connections are extracted using this method
    */
    public synchronized Connection getConnection() throws DBException {
        try{
            //Connection con = s_ConnectionPool.getConnection();

			//if ( con == null ){
			Connection con = DriverManager.getConnection(m_dbURL , m_userName , m_password);
							
			//}
			if ( con == null ){
				System.out.println("DBCON:user="+m_userName+":Connection null !!");
				con = DriverManager.getConnection(m_dbURL , m_userName , m_password);
			}

			if ( con == null ){
				System.out.println("DBCON:user="+m_userName+":Connection null !!");
				con = DriverManager.getConnection(m_dbURL , m_userName , m_password);
			}
			//System.out.println("DBCON:OK");
            return con;
        }catch(SQLException e) {
            System.err.println( "ERROR: user="+m_userName+":Get Connection from pool");
			throw new DBException("ERROR: user="+m_userName+":Get Connection " + e.getMessage());            
        }
    }
       
    public void close(){
    	try {
			s_ConnectionPool.close();
		} catch (SQLException e) {
		}
    }
}