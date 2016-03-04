package com.dialog.util;
/**
 * Title		:Entertaintment Platform Version 3
 * Description	:General XML Parser to HashMap convertor
 * 				 The elements are accessed using the dot convention
 * 				 In case of multiple elements it should have a name
 * 				 Eg: <app>TEST1<id>2</id></app>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Apr 18, 2003
 * @author 	:chandimal_ibu
 * @version 	:3.0
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfig {
	
	private String 	m_fileName;    
	private HashMap m_configData;        
	private char 	m_character='.';	
	private String 	m_nodeName = "";
	
	public static final String DEFAULT_FILENAME="config.xml";
	
	/**
	 * Constructor : Taking the XML file as a parameter
	 */
	public XMLConfig(String fileName) throws XMLConfigException {
		this(fileName,'.');
	}
	
	public XMLConfig(String fileName, char seperator) throws XMLConfigException {	  
		
		this.m_character = seperator;
		
		m_fileName = (fileName == null ? XMLConfig.DEFAULT_FILENAME : fileName) ;
		
		File f = new File(m_fileName);
		if ( !f.exists() ){
			f = new File("./"+m_fileName);
			if ( !f.exists() ){
				f = new File("./conf/"+m_fileName);
				if ( !f.exists() ){
					throw new XMLConfigException("XML Config file not found");
				}
			}
		}
		m_configData = new HashMap();
		
		try {
			// With parser - OLD version
			// parse file
			//SDOMParser dom = new SDOMParser();
			//dom.parse(new FileReader(fileName));
			
			// Get document obj
			//Document doc = dom.getDocument();		
			//Document doc = Parser.parse(fileName);
			
			// Java 1.4 and above
			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setIgnoringComments(true);
			//Document doc = factory.newDocumentBuilder().parse(new File(m_fileName));
			
			// Using thermopylae.jar
			// DocumentImpl doc =DocumentImpl.wrapper(Parser.parse( "1" , new FileReader(m_fileName)));			
			// Get doc element
			
			//Java 5.0
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File(m_fileName));
			Element docElement = doc.getDocumentElement();
			m_nodeName = docElement.getNodeName();
			// Recursive adding
			addNodeList( "", docElement.getTagName() , docElement.getChildNodes() );
		}catch(Exception e){        	
			e.printStackTrace(System.err);
			throw new XMLConfigException("Error loading configuration file :"+this+":"+e);
		}
	}
	
	/**
	 * Recursive method to transverse the DOM tree structure
	 */		
	public void addNodeList(String lastKey, String parent, NodeList nodelist){
		
		for( int x=0; x < nodelist.getLength(); x++ ){
			Node node = nodelist.item(x);
			
			if ( node.getNodeType() == Node.ELEMENT_NODE ){
				// Get the last key and keep on adding
				lastKey = parent + m_character + node.getNodeName();
				
				Element e = (Element)node;
				// All again                
				addNodeList(lastKey, parent + m_character+ node.getNodeName(), e.getChildNodes());
			}else{
				
				// If both lastkey and node value not empty
				if (!lastKey.equals("") && !node.getNodeValue().trim().equals("") ){
					
					// add to hash map                  
					m_configData.put(lastKey.toUpperCase(), node.getNodeValue().trim());
					lastKey ="";        // reset lastkey                	             
					
					// Add node name - if multiple elements
					if ( !node.getNodeValue().trim().equals("") ){
						parent = parent + m_character+ node.getNodeValue().trim();                	
					}
				}
				
			} // End if
		}// End for
	}
	
	public Vector getNextKeys(String key){
		key = getKey(key);
		Set kset = m_configData.keySet();
		String mkey = key.toUpperCase() + m_character;
		
		Vector  vec = new Vector();
		
		for( Iterator i = kset.iterator(); i.hasNext(); ){
			String tkey = (String)i.next();        	
			if ( tkey.startsWith(mkey) ){     
				String subs = tkey.substring(key.length()+1 , tkey.indexOf( m_character , mkey.length()));
				if ( !vec.contains(subs) ){
					vec.add(subs);
				}
			}
			
		}        
		return vec;
	}
	
	/**
	 * Returns the <B>immediate</B> (one level) child element names of the provided key
	 * @param key the parent key of who's children will be searched.<br/> 
	 * The key should be passed as delimited (.) string representing the hierarchy of the element.<br/><br/>
	 * Example:<br/>Consider the following XML extract<br/><br/>
	 * <div>&lt;organization&gt;<br/>&nbsp;&nbsp;&lt;division&gt;<br />&nbsp;&nbsp;&nbsp;&nbsp;&lt;unit&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;employee&gt;<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name&gt;&lt;/name&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;empid&gt;&lt;/empid&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/employee&gt;<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/unit&gt;<br />&nbsp;&nbsp;&lt;/division&gt;<br/>&lt;/organization&gt;</p></div><br/><br/>
	 * searching for the children of employee will require a key string like this to be used<br/>
	 * <b>organization.division.unit.employee</b><br/><br/>
	 * The output will be a vector with values name & empid
	 * @return a {@link Vector} with the child names
	 */
	public Vector getChildElementNames(String key){
        key = getKey(key);
        Set kset = m_configData.keySet();
        String mkey = key.toUpperCase() + m_character;
        
        Vector  vec = new Vector();
        
        for( Iterator i = kset.iterator(); i.hasNext(); ){
            String tkey = (String)i.next();         
            if( (tkey.startsWith(mkey)) && (tkey.length()>mkey.trim().length()) ){
                int index = tkey.indexOf( m_character , mkey.length());
                String subs = "";
                if(index!=-1){
                    subs = tkey.substring(key.length()+1 , index);
                } else {
                    subs = tkey.substring(key.length()+1);
                }
                if ( !vec.contains(subs) ){
                    vec.add(subs);
                }
            }
            
        }        
        return vec;
    }
	
	/** 
	 * Get multiple elements from the configuration
	 */	
	public Vector getElements(String key){
		key = getKey(key);
		Set kset = m_configData.keySet();
		key = key.toUpperCase()+m_character;
		
		Vector  vec = new Vector();
		
		for( Iterator i = kset.iterator(); i.hasNext(); ){
			String tkey = (String)i.next();
			
			if ( tkey.startsWith(key) ){     
				String subs = tkey.substring(0, tkey.indexOf( m_character, key.length()));
				if ( !vec.contains(subs) ){
					vec.add(subs);
				}
			}
			
		}        
		return vec;
	}
	
	/**
	 * Get string value according to the key
	 */
	public String getStr(String key){
		key = getKey(key);
		return (String)m_configData.get(key.toUpperCase());
	}
	
	/**
	 * Get integer value 
	 */
	public int getInt(String key){
		int tmp=0;
		try{
			tmp = Integer.parseInt( getStr(key) );
		}catch(Exception e){
			tmp = -1;
		}
		return tmp;
	}   
	
	public double getDouble(String key){
		double tmp=0;
		try{
			tmp = Double.parseDouble(getStr(key) );
		}catch(Exception e){
			tmp = -1;
		}
		return tmp;
	}   
	
	private String getKey(String key){
		// Adjust the key
		if ( key.indexOf(this.m_character) == -1 ){
			// Convert the key.....
			key = key.replace('.',this.m_character);
		}
		return key;
	}	
	/**
	 * Print content to screen
	 */	
	public String toString(){
		StringBuffer l_tmp=new StringBuffer("{SystemConfig:"+this.m_fileName+",\n");
		
		Set e = m_configData.keySet();
		for( Iterator i = e.iterator() ; i.hasNext(); ){
			String l_str= (String)i.next();
			l_tmp.append("\t"+l_str+"="+m_configData.get(l_str)+"\n");
		}
		return l_tmp.append("}").toString();
	}    
	/**
	 * @return
	 */
	public String getNodeName() {
		return m_nodeName;
	}
	/**
	 * convert the path seperated by dots and return the value of the node  
	 * @param path the path as a string, Elements seperated by dots 
	 * @return the value of the resulting node
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static String getValue(String xmlFile, String path) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = builder.parse(new File(xmlFile));
		
		path = path.replace('.', ':');
		String[] elems = path.split(":");
		Node elem = document.getDocumentElement();
		for (int i = 1; i < elems.length; i++) {
			NodeList list = elem.getChildNodes();
			for(int j=0; j<list.getLength(); j++) {
				Node node = list.item(j);
				if(node.getNodeName().trim().equalsIgnoreCase(elems[i].trim())) {
					elem = node;
				}
			}			
		}
		if(elem == null)
			throw new NullPointerException("Element Not Found!");
		return elem.getNodeValue();
		//return elem.getTextContent();
	}
}