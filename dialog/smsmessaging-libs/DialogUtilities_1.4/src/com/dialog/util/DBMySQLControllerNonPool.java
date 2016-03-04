package com.dialog.util;
import java.sql.*;

public class DBMySQLControllerNonPool extends DBController{

    /**
    * The database controller is initialized according to the parameters
    * set earlier using the static variables
    */
	public void Initialize() throws DBException {
		//		Load class incase faliure
		 try {
			Class.forName("com.mysql.jdbc.Driver");			
		} catch (ClassNotFoundException e1) {			
			e1.printStackTrace();
		}		
	}

    /**
    * From the connection pool the Connections are extracted using this method
    */
    public synchronized Connection getConnection() throws DBException {
        try{
			//if ( con == null ){
            DriverManager.setLoginTimeout(5);
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
            return con;
        }catch(SQLException e) {
            System.err.println( "DBCON:ERROR: user="+m_userName+":Get Connection from pool");
			throw new DBException("DBCON:ERROR: user="+m_userName+":Get Connection " + e.getMessage());            
        }
    }
       
    public void close(){    	
    }
}