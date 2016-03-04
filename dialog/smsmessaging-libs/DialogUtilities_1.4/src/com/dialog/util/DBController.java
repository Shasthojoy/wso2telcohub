/**
 * Title		:Rating
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Jun 1, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

package com.dialog.util;

import java.sql.*;

/**
 * The general abstract class for all the DB connection handlers.
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public abstract class DBController {
    
	protected String	m_dbURL;
	protected String 	m_userName;
	protected String 	m_password;
	protected int		m_maxConnections = 1;
    
	/**
	 * Initialize the DBController. Create database connection pools if neccessary
	 * @throws DBException
	 */
	public abstract void Initialize() throws DBException;

	/**
	 * Initialize DBController with connection parameters
	 * @param dbURL
	 * @param userName
	 * @param password
	 * @param maxCon
	 * @throws DBException
	 */
	public final void Initialize(String dbURL, String userName,
								String password, int maxCon) throws DBException{
		this.m_dbURL =  dbURL;
		this.m_userName = userName;
		this.m_password = password;
		this.m_maxConnections = maxCon;
		Initialize();
	}

	/**
	 * From the connection pool the Connections are extracted using this method
	 * @return Connection object
	 * @throws DBException
	 */
	public abstract Connection getConnection() throws DBException;
 
	/**
	 * Close DBController and release resources used
	 * @throws DBException
	 */
	public abstract void close() throws DBException;
}
