package com.dialog.psi.api;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class SMSRestAPI extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public SMSRestAPI(){
	     singletons.add(new SMSHandler());
	}
	@Override
	public Set<Class<?>> getClasses() {
	     return empty;
	}
	@Override
	public Set<Object> getSingletons() {
	     return singletons;
	}
}
