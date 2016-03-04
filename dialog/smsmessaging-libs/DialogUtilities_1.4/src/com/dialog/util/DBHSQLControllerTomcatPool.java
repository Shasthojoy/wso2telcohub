package com.dialog.util;
import java.sql.*;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.dialog.util.DBController;
import com.dialog.util.DBException;
import com.dialog.util.SystemLog;

public class DBHSQLControllerTomcatPool extends DBController {

    private DataSource s_ds = null;
    private String m_jndiName = "";    
    /**
    * The database controller is initialized according to the parameters
    * set earlier using the static variables
    */
	public void Initialize() throws DBException {
        // Divide jndi and url
        StringTokenizer st = new StringTokenizer(m_dbURL, ",");
        m_jndiName 	=  st.nextToken();
        m_dbURL 	=  st.nextToken();
        
        if (m_jndiName != null) {
            try {
                Context ctx = new InitialContext();
                s_ds = (DataSource) ctx.lookup(m_jndiName);                
            } catch (Exception e) {
                System.out.println("err"+e);
                SystemLog.getInstance().getErrorLog().error("ERR,HSQLDBTomCTRL,getInit-JNDI," + e, e);
            }
        }
	    
		//		Load class incase faliure
		 try {
			Class.forName("org.hsqldb.jdbcDriver");			
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		}		
	}

    /**
    * From the connection pool the Connections are extracted using this method
    */
    public synchronized Connection getConnection() throws DBException {
        Connection con = null;
        try{
            if (s_ds != null) {
                try {
                    con = s_ds.getConnection();
                } catch (Exception e) {
                    SystemLog.getInstance().getErrorLog().error("ERR,HSQLDBTomCTRL,getCon-JNDI," + e
                    , e);
                }
            } 
            // If null try another method
            
			if ( con == null ){
	            DriverManager.setLoginTimeout(5);
				con = DriverManager.getConnection(m_dbURL , m_userName , m_password);
				System.out.println("HSQLDB-DBCON:user="+m_userName+":Connection !!");
			}

            return con;
        }catch(SQLException e) {
            System.err.println( "DBCON:ERROR: user="+m_userName+":Get Connection from pool-HSQLDB");
			throw new DBException("DBCON:ERROR: user="+m_userName+":Get Connection HSQLDB:" + e.getMessage());            
        }
    }
       
    public void close(){    	
    }
}