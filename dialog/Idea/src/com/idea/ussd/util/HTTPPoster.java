package com.idea.ussd.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 
 * @author Charith_02380
 *
 */
public class HTTPPoster {

	public HTTPPoster() {
		PoolingHttpClientConnectionManager connectionManager = 
				new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(10);
		
		CloseableHttpClient httpclient = HttpClients.custom().
				setConnectionManager(connectionManager).build();
		
	}
}
