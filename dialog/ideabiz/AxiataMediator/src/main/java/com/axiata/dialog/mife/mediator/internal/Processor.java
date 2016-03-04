package com.axiata.dialog.mife.mediator.internal;

import java.util.ArrayList;

import org.apache.synapse.MessageContext;
import org.json.JSONArray;
import org.json.JSONObject;

public interface Processor {
	public String getAppID(MessageContext context); //throws Exception;
	public JSONArray getResultList(String response); //throws Exception;
	public APICall setBatchSize(String uri, String body , int batchSize); //throws Exception;
	public JSONObject generateResponse(MessageContext context, JSONArray results, String resourceURL, ArrayList<String> responses); //throws Exception;
}
