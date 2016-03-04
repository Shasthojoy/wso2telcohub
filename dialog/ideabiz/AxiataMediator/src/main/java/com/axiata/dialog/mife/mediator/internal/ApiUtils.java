package com.axiata.dialog.mife.mediator.internal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.synapse.MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Charith_02380
 *
 */
public class ApiUtils {
	private static Hashtable<String, Processor> processors = null;
	private static Properties prop;

	/**
	 * 
	 */
	public ApiUtils() {
		loadProperties();
                processors = new Hashtable<String, Processor>();
	}
	
	/**
	 * 
	 */
	public static void loadProperties() {
		try {
			prop = new Properties();
			prop.load(ApiUtils.class.getResourceAsStream("/processors.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param apiType
	 * @return
	 */
	private Processor getProcessor(String apiType) {
		try {
			Processor p = processors.get(apiType);
			if(p == null) {				
				String className = prop.getProperty(apiType);
				@SuppressWarnings("unchecked")
				Class<Processor> clazz = (Class<Processor>)Class.forName(className);
				p = clazz.newInstance();
				processors.put(apiType, p);
			}
			return p;	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public String getAppID(MessageContext context, String apiType) {
		try {
			Processor p = getProcessor(apiType);
			return p.getAppID(context);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param apiType
	 * @param batchSize
	 * @return
	 */
	public APICall setBatchSize(String uri, String body, String apiType, int batchSize) {
		try {
			Processor p = getProcessor(apiType);
			return p.setBatchSize(uri, body, batchSize);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param apiType
	 * @param object
	 * @return
	 */
	public JSONArray getResults(String apiType, String response) {
		try {
			Processor p = getProcessor(apiType);
			return p.getResultList(response);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param apiType
	 * @param results
	 * @return
	 */
	public JSONObject generateResponse(MessageContext context, String apiType, JSONArray results, ArrayList<String> responses,String requestid) {
		try {
			String resourceURL = prop.getProperty(apiType+"_resource_url");
			Processor p = getProcessor(apiType);
			return p.generateResponse(context, results, AxiataUID.resourceURLWithappend(resourceURL, requestid, "messages"), responses);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
