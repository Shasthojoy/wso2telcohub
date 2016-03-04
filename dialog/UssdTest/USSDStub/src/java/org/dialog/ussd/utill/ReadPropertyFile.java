package org.dialog.ussd.utill;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Hiranya
 */
public class ReadPropertyFile {
    
    private final String PROP_FILE_NAME = "ussdTest.properties";
    
    private final String NOTIFICATION_DELAY_KEY = "ussd.notificationDelay";
    private final String RESPONSE_MSG_KEY = "connect.replyMessage";

    private String getPropertyValue(String propertyName) throws IOException {

        //this is the default batch size that consider when propery file not found. Change if you want to do so
        String result = "";
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            System.out.println("property file '" + PROP_FILE_NAME + "' not found in the classpath");
        }

        // get the property value and print it out
        String propVal = prop.getProperty(propertyName);
        if (propVal != null) {
            result = propVal;
        }

        System.out.println(result + "\nReading property " + propertyName + " on  " + PROP_FILE_NAME + ". Result = " + result);
        inputStream.close();
        return result;
    }

    public int getNotificationDelay() {
        //this is the default batch size that consider when propery file not found. Change if you want to do so
        int result = 1000;
        
        try {
            String value = getPropertyValue(NOTIFICATION_DELAY_KEY);
            if(!value.equals("")){
                result = Integer.valueOf(value);
            }
        } catch (Exception e) {
            System.out.println("Exception in getNotificationDelay() :: " + e);
            result = 1000;
        }
        return result;
    }
    
    public String getResponseMessage(){
        String msg = "";
        try {
            String value = getPropertyValue(RESPONSE_MSG_KEY);
            if(!value.equals("")){
                msg = value;
            }
        } catch (Exception e) {
            System.out.println("Exception in getResponseMessage() :: " + e);
        }
        return msg;
    }
    
}
