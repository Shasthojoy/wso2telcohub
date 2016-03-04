package com.dialog.util.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import com.dialog.util.Utility;

/**
 * 
 * Title		: DialogUtilities_1.4	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 11, 2009
 * @author 		: charith
 * Comments		:
 */
public class SystemError {
    
    private static HashMap errorMap = new HashMap();    
    public static String DEFAULT_ERROR_FILE;
    public static String DEFAULT_ERROR_ATTR = "key";
    public volatile static SystemError s_systemErr;
    
    /**
     * 
     * @return
     */
    public static SystemError getInstance() {        
        if( s_systemErr == null ) {
            synchronized( SystemError.class ) {
                if( s_systemErr == null ){
                    s_systemErr = new SystemError();
                }
            }
        }
        return s_systemErr;        
    }
    /**
     * 
     */
    private SystemError() {
        init();
    }    
    /**
     * 
     */
    public static void init() {
        try {            
            loadFromXML();
            System.out.println(Utility.formatToScreen("Initializing error config")+"[OK]");
        } catch (Exception e) {
            System.out.println(Utility.formatToScreen("Initializing error config")+"[ERR]");
            System.out.println("Error config initialization failed");
            e.printStackTrace(System.out);
        }
    }
    
    /**
     * 
     */
    private static void loadFromXML() throws Exception {
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();            
        DocumentBuilder builder         = factory.newDocumentBuilder();
        org.w3c.dom.Document document   = builder.parse(DEFAULT_ERROR_FILE);        
        DOMBuilder builder2             = new DOMBuilder();
        Document doc                    = builder2.build(document);
        Iterator list                   = doc.getRootElement().getChildren().iterator();
        while(list.hasNext()){
            Element node = (Element)list.next();                
            errorMap.put(node.getAttribute(DEFAULT_ERROR_ATTR).getValue(), node.getText());
        }               
    }    
    /**
     * 
     * @param code
     * @return
     */
    public static String getErrorMessage(String code) {
        return (String)errorMap.get(code);
    }
    
    public static void main(String[] args) {
        DEFAULT_ERROR_FILE = "D:/Office/Development/workspace/HoroscopeDummyAPI/horapi/conf/error.xml";
        SystemError.getInstance();
    }
}