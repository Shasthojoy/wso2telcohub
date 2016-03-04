package com.dialog.util;

import java.util.*;
import java.sql.*;

import com.dialog.util.DBController;
import com.dialog.util.Utility;

/**
 * System DB Holder class contains all the DBControllers defined in the configuration file
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
public class SystemDB {

	private static final SystemDB instance_s = new SystemDB();
	static Hashtable s_dbctrls;
	
	/**
	 * Constract DBControllers and initialize the controllers
	 */
	private SystemDB() {
	    init();
	}// End contr
	
	public static void init(){
		// Perform initialisation of the class here...
		s_dbctrls= new Hashtable();
		SystemConfig s_config = SystemConfig.getInstance();
		
		String nodeName = SystemConfig.getInstance().getNodeName();
		
		Vector vec = s_config.getNextKeys(nodeName+".db");
		for( int x=0 ; x < vec.size() ; x++ ){
			try {			
			
			Object obj;
			obj = Class.forName(s_config.getStr(nodeName + ".db." + vec.get(x) + ".className"))
						.newInstance();
			DBController dbc = (DBController)obj;
			
			dbc.Initialize(s_config.getStr(nodeName + ".db."+vec.get(x)+".url"), 
									 s_config.getStr(nodeName + ".db."+vec.get(x)+".userName"),
									 s_config.getStr(nodeName + ".db."+vec.get(x)+".password"),
									 s_config.getInt(nodeName + ".db."+vec.get(x)+".maxConnections"));

			Connection con = dbc.getConnection();
			con.close();				
			s_dbctrls.put((String)vec.get(x), dbc);

			System.out.println(Utility.formatToScreen("Initializing database:"+vec.get(x))+"[OK]");
			} catch (Exception e) {
				System.out.println(Utility.formatToScreen("Initializing database:"+vec.get(x))+"[ERR]");
				System.out.println("Initialization failed for: "+vec.get(x));
				e.printStackTrace(System.out);
			}			
		}// End for
	}
	
	public static SystemDB getInstance() {
		return instance_s;
	}	
	
	/**
	 * Get the default DBController.
	 * @return
	 */
	public DBController getDBController(){
			return (DBController) s_dbctrls.get("DEFAULT");
	}
	
	/**
	 * Get DBController with the name
	 * @param name
	 * @return DBController
	 */
	public DBController getDBController(String name){
			return (DBController) s_dbctrls.get(name);
	}
	
	public Vector getDBNameList(){
		return new Vector(s_dbctrls.keySet());	
	}	
}
