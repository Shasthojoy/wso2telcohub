package com.axiata.dialog.mife.mediator.internal;

import org.json.JSONObject;

/**
 * @author Charith_02380
 *
 */
public class APICall {
	private String uri = "";
	private JSONObject body;

	/**
	 * 
	 */
	public APICall() {
	}
	
	/**
	 * 
	 * @param uri
	 * @param body
	 */
	public APICall(String uri, JSONObject body) {
		super();
		this.uri = uri;
		this.body = body;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body) {
		this.body = body;
	}
}
