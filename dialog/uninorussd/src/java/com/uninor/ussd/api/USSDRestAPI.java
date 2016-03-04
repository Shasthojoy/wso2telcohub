package com.uninor.ussd.api;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.core.Application;

public class USSDRestAPI extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public USSDRestAPI(){
	     singletons.add(new USSDHandler());
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
