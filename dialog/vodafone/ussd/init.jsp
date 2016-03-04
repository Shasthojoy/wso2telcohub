<%@page import="com.dialog.mife.ussd.util.HibernateUtil, org.hibernate.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%!
    
    public void jspInit() {
    	Session session = HibernateUtil.getSession();
    	session.close();
    	
    	System.out.println("initialized...........");
    }
%>