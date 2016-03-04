package com.dialog.util;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;

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
public class Utility {
	public static String FS = System.getProperty("file.separator");
	public static HashMap s_moreUserList = new HashMap();
	public static String DEFAULT_PATH = ".reservedNumbers";
	
	public static String getValStr(String str, String key){
		int pos1=0;
		String val="";

		str = str.toUpperCase();
		key = key.toUpperCase();

		if ( (pos1 = str.indexOf(key+"=")) != -1 ){
			int pos2 = str.indexOf(',',pos1);

			if (pos2==-1) pos2 = str.length();
			val = str.substring(pos1+key.length()+1,pos2);
		}
		return val;
	}

	public static String formatStr(String msg, Object[] val){
		MessageFormat mf = new MessageFormat(msg);

		if ( val.length > 0 && val[0] == "" ){
			StringTokenizer st = new StringTokenizer(mf.format(val)," ");
			StringBuffer sb = new StringBuffer("");
			boolean isStart = true;
			while ( st.hasMoreTokens() ){
				// Convert to Sentance case
				if ( isStart ){
					String s = st.nextToken();
					String firstLetter = s.substring(0,1);
						sb.append( firstLetter.toUpperCase());
						sb.append( s.substring(1) );
					isStart = false;
				}else{
					sb.append(st.nextToken());
				}
				sb.append(" ");
			}
			return sb.toString();
		}
		return mf.format(val);
	}

	public static final String more_checkMessageLength(String phoneNo, String message){
		String strRemaining = "";
		if ( message.length() > 160 ){
			s_moreUserList.put(phoneNo,message.substring(150) );
			System.out.println("MORE MSG:"+s_moreUserList.size() );
			return message.substring(0,150)+"..MORE";
		}else{
			return message;
		}
	}

	public static final String more_getMessage(String phoneNo){
		String msg = (String)s_moreUserList.get(phoneNo);
		if ( msg != null ){
			if ( msg.length() > 160 ){
				s_moreUserList.put(phoneNo,msg.substring(150) );
				return msg.substring(0,150)+"..MORE";
			}else{
				s_moreUserList.remove(phoneNo);
				return msg;
			}
		}
		return "";
	}

	public static final void more_clearAll(String phoneNo){
		s_moreUserList.remove(phoneNo);
	}
	public static String getDateStr(){
		String l_str = "";
		GregorianCalendar cal = new GregorianCalendar();
		l_str = cal.get(GregorianCalendar.YEAR) + "_" +
				(cal.get(GregorianCalendar.MONTH)+1) + "_" +
				cal.get(GregorianCalendar.DATE) + "_" +
				cal.get(GregorianCalendar.HOUR) + "_" +
				cal.get(GregorianCalendar.MINUTE);
		return l_str;
	}
	
	public static String getDateTimeStr(long datetime){
		String l_str = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date(datetime));
		l_str = cal.get(GregorianCalendar.YEAR) + "/" +
				(cal.get(GregorianCalendar.MONTH)+1) + "/" +
				cal.get(GregorianCalendar.DATE) + " " +
				cal.get(GregorianCalendar.HOUR) + ":" +
				cal.get(GregorianCalendar.MINUTE);
		return l_str;
	}
	
	public static String getDateStr(long datetime){
		String l_str = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date(datetime));
		l_str = cal.get(GregorianCalendar.YEAR) + "/" +
				(cal.get(GregorianCalendar.MONTH)+1) + "/" +
				cal.get(GregorianCalendar.DATE) ;
		return l_str;
	}

	public static long getSecondsToMidNight(){
	 GregorianCalendar cal = new GregorianCalendar();
		long time = (23 - cal.get(GregorianCalendar.HOUR_OF_DAY))*60*60 +
					(59 - cal.get(GregorianCalendar.MINUTE))*60 ;
		return time;
	}

	/**
	 * Screen format commands
	 */    
	static Stack s_stack = new Stack();
	
	public static String formatToScreen(String msg){
		return formatToScreen(msg, 75);
	}

	public static String formatToScreen(String msg, int scrLen){
		StringBuffer sbuf = new StringBuffer(msg);
		
		sbuf.append(replicateChr('.',scrLen-msg.length()));
		return sbuf.toString();
	}

	public static String centerScreen(String msg){
		int len = ( 80 - msg.length() )/ 2;
		StringBuffer sbuf = new StringBuffer( Utility.replicateChr(' ',len) );
		sbuf.append(msg);
		return sbuf.toString();
	}
	
	public static String replicateChr(char ch,int no){
		StringBuffer sbuf = new StringBuffer();
		for( int x=0 ; x < no; x++ )
			sbuf.append(ch);
		
		return sbuf.toString();
	}

	public static String filterValueString(String settings,String key,char lineSep,char keyvalsep ){
		String retVal = "";
		
		StringTokenizer st = new StringTokenizer(settings,""+lineSep);
		key = key.toLowerCase();		
		try {
			while (st.hasMoreTokens()){
				String keyval = st.nextToken();				
				if ( keyval.toLowerCase().indexOf(key) >= 0){					
					retVal = keyval.substring( keyval.indexOf(keyvalsep)+1 );
					break;				
				}
			}
		} catch (RuntimeException e) {}
		return retVal;		
	}
    
	public ArrayList breakupMessage(String text, String regex, int length){
		int iBrk = length;
		
		ArrayList msgPack = new ArrayList(0);
		
		if (text.length() > iBrk){
			while (text.length() > iBrk){
				String sTemp = text.substring(0,iBrk);
				String sTemp1 = sTemp.substring(0,sTemp.lastIndexOf(regex));
				
				msgPack.add(sTemp.substring(0,sTemp.lastIndexOf(regex)));
				
				if (text.length() > iBrk){
					text = text.substring(iBrk);
					if (sTemp.length() > sTemp1.length()){
						text = sTemp.substring(sTemp.lastIndexOf(regex)) + text ;
					}
				}
				
				text = text.trim();
				if (text.length() < iBrk){
					msgPack.add(text);
				}
			}
			return msgPack;
		}else{
			msgPack.add(text);
			return msgPack;
		}
	}
	
	/**
	 * Check if the parsed string is not null and not empty
	 * @param value
	 * @return
	 */
	public static boolean isValid(String value) {
        if(value==null) return false;
        if(value.trim().equals("")) return false;
        return true;
    }
	
	/**
	 * Converts a provided string value into a {@link Number} of the provided {@link Class}
	 * @param strValue
	 * @param clazz
	 * @param defValue
	 * @return the converted value or the default value if the conversion cannot be done
	 */
	public static Number parseNumber(String strValue, Class clazz, Number defValue) {
	    try {
	        Number defNum = new Float(defValue.floatValue());
	        
	        Constructor defaultConst = clazz.getConstructor(new Class[]{String.class});
            Object obj               = defaultConst.newInstance(new Object[]{strValue});
            return (Number)obj;
	    }
	    catch (Exception e) {}
	    return defValue;
	}
	
	/**
	 * Conterts a XML String to a DOM Document
	 * @param str the XML String to be parsed
	 * @return a {@link Document}
	 * @throws Exception
	 */
	public static org.jdom.Document convertXMLStr2DOMDoc(String str) throws Exception {
        ByteArrayInputStream bIn        = new ByteArrayInputStream(str.getBytes());     
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();                        
        DocumentBuilder builder         = factory.newDocumentBuilder();     
        org.w3c.dom.Document document   = builder.parse(bIn);
        DOMBuilder builder2             = new DOMBuilder();
        
        return builder2.build(document);
    }
	
	/**
     * Conterts a XML String to a DOM Document
     * @param str the XML String to be parsed
     * @param encoding the encoding of the xml document
     * @return a {@link Document}
     * @throws Exception
     */
	public static org.jdom.Document convertXMLStr2DOMDoc(String str, String encoding) throws Exception {
        ByteArrayInputStream bIn        = new ByteArrayInputStream(str.getBytes(encoding));     
        DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();                        
        DocumentBuilder builder         = factory.newDocumentBuilder();     
        org.w3c.dom.Document document   = builder.parse(bIn);
        DOMBuilder builder2             = new DOMBuilder();
        
        return builder2.build(document);
    }	
	/**
	 * Finds the value of a specified element within the provided document. 
	 * @param document the document within which the element will be found
	 * @param path The path to find the document. The path shoud be provided in the following format.<br/><br/>
	 * &ltnamespace&gt:&ltelementName&gt|&ltnamespace&gt:&ltelementName&gt<br/><br/> 
	 * Example:<br/>
	 * ns1:userName|ns2:password<br/><br/>
	 * if no namespace is availabel ignore the element.<br/>
	 * Example:<br/>
     * ns1:userName|password<br/><br/>
	 * @return value of the element
	 */
	public static String findValueInDocument(org.jdom.Document document, String path) {	    
	    StringTokenizer elems = new StringTokenizer(path, "|");        
        Element elem = document.getRootElement();
        while(elems.hasMoreTokens()) {
            String token = elems.nextToken();            
            if(token.indexOf(":")>-1) {
                String[] keyValue = token.split(":");
                elem = elem.getChild(keyValue[1], document.getRootElement().getNamespace(keyValue[0]));
            } else {
                elem = elem.getChild(token);
            }
        }
        if(elem == null)
            throw new NullPointerException("Element Not Found!");
        return elem.getText();
    }
	
	/**
     * Finds the value of a specified element within the provided document. 
     * @param document the document within which the element will be found
     * @param path The path to find the document. The path shoud be provided in the following format.<br/><br/>
     * &ltnamespace&gt:&ltelementName&gt|&ltnamespace&gt:&ltelementName&gt<br/><br/> 
     * Example:<br/>
     * ns1:userName|ns2:password<br/><br/>
     * if no namespace is availabel ignore the element.<br/>
     * Example:<br/>
     * ns1:userName|password<br/><br/>
     * @return value of the element
     */
    public static Object findValueInDocumentV2(List children, String name) {    
        String[] keyValue = name.split(":");
        Iterator iter = children.iterator();
        Object element = null;
        while(iter.hasNext()) {
            Element elem = (Element)iter.next();
            if(elem.getName().equalsIgnoreCase(name)){ 
                element = elem;
                break;
            } else {
                element = findValueInDocumentV2(elem.getChildren(), name);       
            }
        }
        return element;
    }
    
    /**
     * Removes new line charecters in the provided text
     * @param text
     * @return
     */
    public static String removeNewLines(String text) {
        if(text==null) {
            return "";
        }
        return text.replaceAll("\n|\r", "");        
    }
    /**
     * Validate Dialog mobile number by comparing reserved number range 
     * @param num   MSISDN for validation 
     * @param path  The path of the number range of config.xml file 
     * Example:  If config.xml file consists this &ltreservedNumber&gt77,765 &ltreservedNumber&gt
     * then your path should be  reservedNumber
     * @return if MSISDN starts with any of the number range value , then  return true
     */
    public static boolean validateDialogNumber(String num , String path){
    	String range = "";
    	String[] tmp = null;
     	if(path==null){
    		path = SystemConfig.getInstance().getNodeName()+DEFAULT_PATH;
    	}
    	if(num.substring(0,1).equals("0")){
    		num = num.substring(1);
    	}
    	range = SystemConfig.getInstance().getStr(path);
    	tmp = range.split(",");
    	
    	for(int i=0;i<tmp.length;i++) {
    		if (num.startsWith(tmp[i]))
    			return true;
    	}
    	return false;
    }
    
    /**
     * Validate Dialog mobile number by comparing reserved number range 
     * Here  path of the number range of the config.xml file is set to the default path
     * Default path is root.reservedNumber
     * @param num MSISDN  for validation 
     * Example : if config.xml consists number range exactly like this &ltreservedNumber&gt 77,765 &ltreservedNumber&gt
     * then you do not want to specify your path . 
     * @return if MSISDN starts with any of the number range value , then  return true
     */
    public static boolean validateDialogNumber(String num){
    	return validateDialogNumber(num,null);
    }
	
    public static void main(String[] args) {
		try {
            //Document doc = Utility.convertXMLStr2DOMDoc("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><ns1:queryToneResponse xmlns:ns1=\"http://toneprovide.ivas.huawei.com\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><queryToneReturn href=\"#id0\"/></ns1:queryToneResponse><multiRef xmlns:ns2=\"http://response.toneprovide.ivas.huawei.com\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" id=\"id0\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"ns2:QueryToneResp\"><eventClassName xsi:nil=\"true\" xsi:type=\"xsd:string\"/><operationID xsi:type=\"xsd:long\">0</operationID><queryToneInfos xmlns:ns3=\"http://info.ivas.huawei.com\" soapenc:arrayType=\"ns3:ToneInfo[1]\" xsi:type=\"soapenc:Array\"><item href=\"#id1\"/></queryToneInfos><recordSum xsi:type=\"xsd:string\">-1</recordSum><resultCode xsi:type=\"xsd:int\">0</resultCode><resultInfo xsi:nil=\"true\" xsi:type=\"xsd:string\"/><returnCode xsi:type=\"xsd:string\">000000</returnCode></multiRef><multiRef xmlns:ns4=\"http://info.ivas.huawei.com\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" id=\"id1\" soapenc:root=\"0\" soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xsi:type=\"ns4:ToneInfo\"><askType xsi:nil=\"true\" xsi:type=\"xsd:string\"/><availableDateTime xsi:nil=\"true\" xsi:type=\"xsd:string\"/><category xsi:nil=\"true\" xsi:type=\"xsd:string\"/><categoryName xsi:nil=\"true\" xsi:type=\"xsd:string\"/><checkTime xsi:type=\"xsd:string\">2008-09-30 14:53:34</checkTime><corpCode xsi:nil=\"true\" xsi:type=\"xsd:string\"/><corpName xsi:nil=\"true\" xsi:type=\"xsd:string\"/><cpCode xsi:type=\"xsd:string\">601514</cpCode><cpName xsi:type=\"xsd:string\">CP14</cpName><cutFlag xsi:type=\"xsd:string\">0</cutFlag><deviceAndUrl xsi:type=\"xsd:string\"/><diyUserPhoneNumber xsi:nil=\"true\" xsi:type=\"xsd:string\"/><downTime xsi:nil=\"true\" xsi:type=\"xsd:string\"/><enabledDate xsi:nil=\"true\" xsi:type=\"xsd:string\"/><endOffset xsi:nil=\"true\" xsi:type=\"xsd:string\"/><index xsi:type=\"xsd:string\">181</index><info xsi:nil=\"true\" xsi:type=\"xsd:string\"/><language xsi:type=\"xsd:string\">2</language><modifyID xsi:nil=\"true\" xsi:type=\"xsd:string\"/><musicID xsi:type=\"xsd:string\">000000000000</musicID><offset xsi:nil=\"true\" xsi:type=\"xsd:string\"/><orderInfo xsi:nil=\"true\" xsi:type=\"xsd:string\"/><orderTimes xsi:type=\"xsd:string\">58</orderTimes><personID xsi:nil=\"true\" xsi:type=\"xsd:string\"/><plusUserPhoneNumber xsi:nil=\"true\" xsi:type=\"xsd:string\"/><price xsi:type=\"xsd:string\">2000</price><priceGroupID xsi:type=\"xsd:string\">-1</priceGroupID><reOrderMode xsi:type=\"xsd:string\"/><rejectReason xsi:nil=\"true\" xsi:type=\"xsd:string\"/><relativeTime xsi:type=\"xsd:string\">30</relativeTime><remark xsi:type=\"xsd:string\">0</remark><setTimes xsi:type=\"xsd:string\">58</setTimes><singerName xsi:type=\"xsd:string\">Unknown</singerName><singerNameAddress xsi:nil=\"true\" xsi:type=\"xsd:string\"/><singerNameLetter xsi:type=\"xsd:string\">U</singerNameLetter><singerNamePath xsi:nil=\"true\" xsi:type=\"xsd:string\"/><singerSex xsi:type=\"xsd:string\">1</singerSex><status xsi:type=\"xsd:string\">1</status><tableType xsi:type=\"xsd:string\">2</tableType><tariffPrice xsi:nil=\"true\" xsi:type=\"xsd:string\"/><taxisIndex xsi:nil=\"true\" xsi:type=\"xsd:string\"/><taxisToneDownInfo xsi:nil=\"true\" xsi:type=\"xsd:string\"/><toneAddress xsi:type=\"xsd:string\">http://10.48.209.20:8080/colorring/rl/601/514/0/0000/0000/175.wav</toneAddress><toneCode xsi:type=\"xsd:string\">514175</toneCode><toneCodeLong xsi:type=\"xsd:string\">601514000000000175</toneCodeLong><toneID xsi:type=\"xsd:string\">49568</toneID><toneName xsi:type=\"xsd:string\">Li Maduwe Basa</toneName><toneNameAddress xsi:nil=\"true\" xsi:type=\"xsd:string\"/><toneNameLetter xsi:type=\"xsd:string\">L</toneNameLetter><toneNamePath xsi:nil=\"true\" xsi:type=\"xsd:string\"/><tonePath xsi:type=\"xsd:string\">Y:/ad/rl/601/514/0/0000/0000/175.wav</tonePath><tonePreListenAddress xsi:type=\"xsd:string\">http://10.48.209.20:8080/colorring/al/601/514/0/0000/0000/175.wav</tonePreListenAddress><tonePreListenPath xsi:type=\"xsd:string\">Y:/ad/al/601/514/0/0000/0000/175.wav</tonePreListenPath><toneValidDay xsi:type=\"xsd:string\">2018-09-30 23:59:59</toneValidDay><totalPrice xsi:nil=\"true\" xsi:type=\"xsd:string\"/><updateTime xsi:type=\"xsd:string\">2008-09-30 14:39:29</updateTime><uploadTime xsi:type=\"xsd:string\">2008-09-30 14:39:29</uploadTime><uploadType xsi:type=\"xsd:string\">1</uploadType></multiRef></soapenv:Body></soapenv:Envelope>");
            //Object str = Utility.findValueInDocumentV2(doc.getRootElement().getChildren(), "price");
            //System.out.println(str);
		    // Number val = parseNumber("45.54", Integer.class, new Integer(-1));
		   // System.out.println(val.intValue());
		System.out.println("check "+ SystemConfig.getInstance().getNodeName()+DEFAULT_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}