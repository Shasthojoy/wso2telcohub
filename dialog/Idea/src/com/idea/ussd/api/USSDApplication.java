package com.idea.ussd.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class USSDApplication extends Application {
	
	HashSet<Object> hashSet = new HashSet<Object>();

	public USSDApplication() {
		hashSet.add(new UssdAPI());
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return hashSet;
	}
}
