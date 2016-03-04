package com.axiata.dialog.util;

import com.google.gson.Gson;

/**
 * Gson Helper
 * 
 * @author thanuja
 *
 */
public class GsonHelper {
	
	private static GsonHelper helper;
	private static Gson gson;


	private GsonHelper() {
		gson = new Gson();
	}

	public static GsonHelper getInstance() {
		if (helper == null) {
			synchronized (GsonHelper.class) {
				if (helper == null) {
					helper = new GsonHelper();
				}
			}
		}
		return helper;
	}

	public <T> T fromJson(String json, Class<T> type) throws Exception{
		try {
			return gson.fromJson(json, type);
		} catch (Exception ex) {
			throw new Exception("Recived JSON object is invalied. Error Msg: "+ex.getMessage());
		}
	}
	
}
