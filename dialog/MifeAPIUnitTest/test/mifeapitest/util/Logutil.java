/*
 * Logutil.java
 * Jun 17, 2014  10:25:46 AM
 * Roshan.Saputhanthri
 *
 * Copyright (C) Dialog Axiata PLC. All Rights Reserved.
 */

package mifeapitest.util;

import java.io.IOException;  
import java.util.logging.FileHandler;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import java.util.logging.SimpleFormatter; 

/**
 * <TO-DO> <code>Logutil</code>
 * @version $Id: Logutil.java,v 1.00.000
 */
public class Logutil {

    static Logger logger;
    public static void init() {
    logger = Logger.getLogger("Logutil");  
        FileHandler fh;  
          
        try { 
              
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./trace.log");  
            logger.addHandler(fh);  
            //logger.setLevel(Level.ALL);  
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
              
            // the following statement is used to log any messages  
            logger.info("Log initialized");  
              
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        logger.info("Hi How r u?");  
    }   
    
    public static Logger getlogger() {
        return logger;
    }
    
}
