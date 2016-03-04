/**
 * Title		:Rating
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Jun 16, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */
package com.dialog.util;

import java.sql.*;
import org.apache.log4j.Logger;
import com.dialog.util.GenException;

public abstract class StmtHandler {
	protected Connection 			m_con = null;
	protected Logger				m_logErr = null;
	protected boolean				m_isValid = true;
	protected String				m_dbfName = "";
	protected PreparedStatement		m_cst = null;	

	/**
	 * 
	 */
	public StmtHandler(String dbfName, String logName) throws GenException {
		this.m_dbfName = dbfName;
		reInitialize();
		m_logErr = SystemLog.getInstance().getLogger(logName);		 
	}
	
	public void reInitialize() throws GenException{
		initDBConnection();
		initialze();		
	}
	
	public abstract void initialze() throws GenException;		
	public abstract void execute() throws GenException;

	public void clearStmt(){
		if ( m_cst == null ) return;
		try {
			m_cst.clearParameters();
		} catch (SQLException e) {}	
	}	

	private void initDBConnection() throws GenException{
		this.m_con = SystemDB.getInstance().getDBController(this.m_dbfName).getConnection();		
	}
	
	public boolean isVaid() {
		boolean retVal = false;
		try {
			retVal = !m_con.isClosed();
		} catch (SQLException e) {}		 
		return retVal;
	}
	
	public Connection getConnection(){
		return this.m_con;	
	}	

	public void close(){
		if ( m_con == null ) return;
		try {
			m_con.close();
		} catch (SQLException e) {}	
	}	
}
