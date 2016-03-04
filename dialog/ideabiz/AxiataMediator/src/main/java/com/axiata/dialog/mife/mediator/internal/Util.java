package com.axiata.dialog.mife.mediator.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.core.service.RealmService;

public class Util {
    private static RealmService realmService;
    private static Properties props = new Properties();
    private static Log log = LogFactory.getLog(Util.class);

    public static RealmService getRealmService() {
        return realmService;
    }
    
    public static synchronized void setRealmService(RealmService realmSer) {

        realmService=realmSer;

   }
    
    public static String getApplicationProperty(String key) {
		return props.getProperty(key);
	} 
    
    public static void getPropertyFile(){
		try {
			props.load(Util.class.getResourceAsStream("/application.properties"));
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			log.info("FileNotFound");
			System.err.println(
					"Check your Property file, it should be in application home dir, Error:"
							+ e.getCause()+ "Cant load APPLICATION.properties");

			//System.exit(-1);
		} catch (IOException e) {
			log.info("IO Error");
			System.err.println(
					"Check your Property file, it should be in application home dir, Error:"
							+ e.getCause()+ "Cant load APPLICATION.properties");
			//System.exit(-1);
		}
	}

	/**
	 * Method to check if all elements in a collection is null
	 * @param list Iterable collection
	 * @return true if all elements are null
	 */
	public static boolean isAllNull(Iterable<?> list) {
		for (Object obj : list) {
			if (obj != null)
				return false;
		}
		return true;
	}

}
