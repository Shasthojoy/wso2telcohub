package com.dialog.util;
import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;


public class DBOracleControllerTomcatPool extends DBController{

    private DataSource s_ds = null;
    private String m_jndiName = "";
    
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
                SystemLog.getInstance().getErrorLog().error("ERR,DBTomCTRL,getInit-JNDI," + e, e);
            }
        }
        
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			DriverManager.setLoginTimeout(5);
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		}
    }

    /**
    * From the connection pool the Connections are extracted using this method
    */
    public synchronized Connection getConnection() throws DBException {
        Connection con = null;
        
        if (s_ds != null) {
            try {
                con = s_ds.getConnection();
            } catch (Exception e) {
                SystemLog.getInstance().getErrorLog().error("ERR,DBTomCTRL,getCon-JNDI," + e
                , e);
            }
        } 
        // If null try another method
        if ( con == null ){
            
            try {
                con = DriverManager.getConnection(m_dbURL , m_userName , m_password);                
            } catch (Exception e) {
                SystemLog.getInstance().getErrorLog().error("ERR,DBTomCTRL,getConNorm," + e, e);
            }						
        }
        return con;
    }
       
    public void close(){
		s_ds = null;
    }
}