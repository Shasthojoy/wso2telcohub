package com.axiata.dialog.util;

import org.apache.log4j.Logger;
import org.wso2.carbon.utils.CarbonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by nilan on 11/24/14.
 */
public class Config {

    Properties prop = new Properties();

    private static Config instance;
    private final static Logger log = Logger.getLogger(Config.class);

    private  Config(){

        try {
            InputStream input = null;
            String filename = CarbonUtils.getCarbonConfigDirPath() + File.separator + "config.properties";
            input = new FileInputStream(filename);

            prop.load(input);

        } catch (IOException e) {
            log.error("IOException :"+e);

        }catch(Exception e){
            log.error("Exception :"+e);
        }
    }

    public static synchronized Config getInstance()
    {
        if (instance == null)
            instance = new Config();

        return instance;
    }

    public String getProperty(String propertyName){

       return prop.getProperty(propertyName);

    }

}
