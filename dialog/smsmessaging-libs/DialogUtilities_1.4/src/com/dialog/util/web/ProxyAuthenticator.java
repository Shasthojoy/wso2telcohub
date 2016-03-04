package com.dialog.util.web;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Title		:TestWebServices
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Jul 5, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

public class ProxyAuthenticator  extends Authenticator{
	String m_userName = "";
	String m_password = "";
	public ProxyAuthenticator(String userName,String password){
		this.m_userName = userName;
		this.m_password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(m_userName , m_password.toCharArray() );
	}
}
