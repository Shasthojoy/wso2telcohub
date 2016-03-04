/**
 * Title		:DialogUtilities
 * Description	:<description>
 *  				
 * Copyright	:Copyright (c) 2003
 * Company		:MTN Networks (Pvt) Ltd.
 * Created on	:Sep 14, 2004
 * @author 		:chandimal_ibu
 * @version 	:1.0
 */

package com.dialog.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.*;
import java.util.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class LicenseFactory {
	private static LicenseFactory s_instance = new LicenseFactory();
	private String m_keyFile;
	private String m_txtFile;
	
	private Properties m_prop = null;
	private LicenseFactory(){
		m_keyFile = "D:/Office/Development/workspace/DialogUtilities_1.4/License/license.key";
		m_txtFile = "D:/Office/Development/workspace/DialogUtilities_1.4/License/license.lic";
		m_prop = new Properties();
	}
	
	public static LicenseFactory getInstance(){
		return s_instance;
	}
	
	public boolean validate(){
		// load license text
		byte[] buffer;
		try {
			buffer = readFile(m_txtFile);
		} catch (Exception e2) {			
			SystemLog.getInstance().getRootLog().fatal("Error reading License text file");				
			return false;
		}
		
		// load license signature and public keys
		File f = new File(m_keyFile);
		char[] cbuf= new char[(int)f.length()];		
			BufferedReader buf;
			try {
				buf = new BufferedReader(new FileReader(f));
				buf.read(cbuf);
				buf.close();				
			} catch (Exception e) {
				SystemLog.getInstance().getRootLog().fatal("Error reading License key file");				
				return false;
			}
			
		StringTokenizer st = new StringTokenizer(new String(cbuf),"*");
			String pStr = st.nextToken();
			String sStr = st.nextToken();
			BASE64Decoder decode = new BASE64Decoder();
			byte[] publicKeyBytes;
			byte[] sign;
				try {
					publicKeyBytes = decode.decodeBuffer(pStr);
					sign = decode.decodeBuffer(sStr);
				} catch (IOException e1) {
					SystemLog.getInstance().getRootLog().fatal("Error decoding License key file");				
					return false;
				}
			
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory;
			PublicKey publicKey = null;
			
			try {
				keyFactory = KeyFactory.getInstance("DSA");
				publicKey = keyFactory.generatePublic(publicKeySpec);
			} catch (Exception e3) {			
				SystemLog.getInstance().getRootLog().fatal("Error creating License key");				
				return false;
			}
					
			if ( !verifySignature(publicKey,buffer,sign) ){
				SystemLog.getInstance().getRootLog().fatal("Invalid License key !!");
				return false;
			}
		try {			
			m_prop.load(new FileInputStream(m_txtFile));
		} catch (Exception e4) {
			System.out.println(e4);
			SystemLog.getInstance().getRootLog().fatal("Opps ! Err lic file ");
			return false;			
		}
		
		// Check date
		long datetime = getLong("expire.date");		
		if ( datetime < System.currentTimeMillis()){
			SystemLog.getInstance().getRootLog().fatal("License key expired !!");
			return false;
		}
		return true;
	}
	
	public String getStr(String key){
		return m_prop.getProperty(key);
	}
	
	public int getInt(String key){
		String tmp = m_prop.getProperty(key);
		try {
			return Integer.parseInt(tmp);
		} catch (NumberFormatException e) {			
		}		
		return -1;
	}

	public long getLong(String key){
		String tmp = m_prop.getProperty(key);
		try {
			return Long.parseLong(tmp);
		} catch (NumberFormatException e) {			
		}		
		return -1;
	}

	private byte[] readFile(String fileName) throws Exception {
	   File f = new File(fileName);
	   byte ret[] = new byte[(int)f.length()];
	   FileInputStream in = new FileInputStream(f);	 	
	   	in.read(ret);
	   	in.close();
	   return ret;
	}	
	
	private boolean verifySignature(PublicKey key, byte[] buffer, byte[] signature) {
		try {		 	
			Signature sig = Signature.getInstance(key.getAlgorithm());			
			sig.initVerify(key);
			sig.update(buffer, 0, buffer.length);			 
			return sig.verify(signature);
		} catch (Exception e) {
			System.out.println(e);		
		}
		return false;
	}
	/**
	 * @param string
	 */
	public void setKeyFile(String string) {
		m_keyFile = string;
	}

	/**
	 * @param string
	 */
	public void setTxtFile(String string) {
		m_txtFile = string;
	}
	
	public static void main(String[] args) {
	    try {
	        /*Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, 2014);
	        cal.set(Calendar.MONTH, 02);
	        cal.set(Calendar.DAY_OF_MONTH, 26);
	        cal.set(Calendar.HOUR_OF_DAY, 20);
	        cal.set(Calendar.MINUTE, 20);
	        cal.set(Calendar.SECOND, 00);
	        cal.set(Calendar.MILLISECOND, 00);
	        System.out.println(cal.getTimeInMillis());*/	        
	        
	        /*BASE64Encoder enc = new BASE64Encoder();
            LicenseFactory lf = new LicenseFactory();
            byte[] buff = lf.readFile("D:/Office/Development/workspace/DialogUtilities_1.4/License/license.lic");
            Signature sign = Signature.getInstance("DSA");                       
            
            KeyPair kp = KeyPairGenerator.getInstance("DSA").generateKeyPair();

            System.out.println(enc.encode(kp.getPrivate().getEncoded()));
            System.out.println("");
            System.out.println("");
            System.out.println(enc.encode(kp.getPublic().getEncoded()));
            
            sign.initSign(kp.getPrivate());
            sign.update(ByteBuffer.wrap(buff));
            
            byte[] signature = sign.sign();            
            String signatureTxt = enc.encode(signature);
            
            System.out.println("");
            System.out.println("");
            System.out.println(signatureTxt);*/
	        
	        boolean valid = LicenseFactory.getInstance().validate();
	        System.out.println(valid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
