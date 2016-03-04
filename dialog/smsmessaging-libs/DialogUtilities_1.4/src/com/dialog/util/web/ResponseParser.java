package com.dialog.util.web;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import com.dialog.util.Utility;

/**
 * Title		: DialogUtilities_1.4	
 * Description	: 
 * Copyright	: Copyright(c) 2009
 * Company		: Dialog Telekom PLC.
 * Created on	: Mar 16, 2009
 * @author 		: charith
 * Comments		: 
 */
public class ResponseParser {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ResponseParser parser = new ResponseParser();
        String xml1 =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service-response name=\"careerhoroscope\">" +
        		        "<tracking><request-id>123</request-id><request-type>TEXT</request-type>" +
        		        "<timestamp>2121212121121</timestamp><serviceNo>777335365</serviceNo></tracking>" +
        		        "<execution-result>200</execution-result><response-parameters><param1>1</param1><param2>2</param2>" +
        		        "<param3>3</param3><param4><param4a>4a</param4a><param4b><param4ba>4ba</param4ba>" +
        		        "<param4bc>4bc</param4bc></param4b></param4></response-parameters></service-response>";
        
        String xml2 =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service-response name=\"careerhoroscope\">" +
                        "<tracking><request-id>123</request-id><request-type>TEXT</request-type>" +
                        "<timestamp>2121212121121</timestamp><serviceNo>777335365</serviceNo></tracking>" +
                        "<execution-result>200</execution-result><response-parameters><param1>1</param1><param2>2</param2>" +
                        "<param3>3</param3><param4>4</param4></response-parameters></service-response>";
        
        String xml3 =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service-response name=\"careerhoroscope\">" +
                        "<tracking><request-id>123</request-id><request-type>TEXT</request-type>" +
                        "<timestamp>2121212121121</timestamp><serviceNo>777335365</serviceNo></tracking>" +
                        "<execution-result>200</execution-result><response-parameters><param1>1</param1><param2>2</param2>" +
                        "<param3>3</param3><param4><param4a>4a</param4a>" +
                        "<param4b>4b<param4ba attr1=\"4baAttr1\" attr2=\"4baAttr2\">4ba</param4ba><param4bc>4bc</param4bc>" +
                        "</param4b></param4></response-parameters></service-response>";        
           
        
        String xml4 =   "<service-response name=\"dailyhoroscope\"><tracking><request-id>1234</request-id>" +
        		        "<request-type>VOICE</request-type><timestamp>20090218174568</timestamp><serviceNo>777335365</serviceNo></tracking>" +
        		        "<execution-result>0</execution-result><response-parameters><uid>6546554</uid>" +
        		        "<voice>http://ZenssMAstro/voicefiles/v001.wav</voice>" +
        		        "<voice>http://ZenssMAstro/voicefiles/v002.wav</voice>" +
        		        "<voice>http://ZenssMAstro/voicefiles/v003.wav</voice></response-parameters></service-response>";
              
        ResponseParser.parse2GenericResponse(xml4);
    }
    
    /**
     * 
     * @param xml
     * @return
     */
    public static GenericResponse parse2GenericResponse(String xml) {
        try {
            GenericResponse response = new GenericResponse();
            Document doc = Utility.convertXMLStr2DOMDoc(xml);
            response.setServiceResponseName(doc.getRootElement().getAttributeValue("name"));     
            response.setRequestID(doc.getRootElement().getChild("tracking").getChildText("request-id"));
            response.setRequestType(doc.getRootElement().getChild("tracking").getChildText("request-type"));
            response.setTimestamp(doc.getRootElement().getChild("tracking").getChildText("timestamp"));
            response.setServiceNumber(doc.getRootElement().getChild("tracking").getChildText("serviceNo"));
            response.setExecutionResult(doc.getRootElement().getChildText("execution-result"));
            
            List list = doc.getRootElement().getChild("response-parameters").getChildren();            
            ArrayList params = new ArrayList(0);
            params.ensureCapacity(1);
            createParams(list, params);
            response.setResponseParams(params);
            
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @param list
     * @param map
     */
    public static void createParams(List list, ArrayList map) {
        for(int i=0;i<list.size();i++) {
            Element elem = (Element)list.get(i);
            if(elem.getChildren().size()==0) {
                List attList = elem.getAttributes();
                Hashtable attrTable = new Hashtable(0);
                for(int attListindex=0;attListindex<attList.size();attListindex++) {
                    Attribute attr = (Attribute)attList.get(attListindex);
                    attrTable.put(attr.getName(), attr.getValue());
                }                    
                map.add(new GenericResponseParamElement(elem.getName(), elem.getText(), attrTable, null));
            } else {
                ArrayList tempMap = new ArrayList(0);
                tempMap.ensureCapacity(1);
                createParams(elem.getChildren(), tempMap);
                List attList = elem.getAttributes();
                Hashtable attrTable = new Hashtable(0);
                for(int attListindex=0;attListindex<attList.size();attListindex++) {
                    Attribute attr = (Attribute)attList.get(attListindex);
                    attrTable.put(attr.getName(), attr.getValue());
                }                
                map.add(new GenericResponseParamElement(elem.getName(), elem.getText(), attrTable, tempMap));
            }
        }        
    }
}
