/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes;

import javax.crypto.BadPaddingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shashika Wijayasekera
 */
public class ACRDecryptTest {

    public ACRDecryptTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * correct salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAllGetters1() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "z6u56I2kO34Aap16";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = "123456";
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = "MIFE01";
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = "0775566288";
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = "2014-03-28 11:27:49.611";
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass empty string as
     * encrypted ACR and valid salt key
     */
    @Test
    public void testAllGetters2() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "";
        String appSaltKey = "z6u56I2kO34Aap16";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass null as encrypted
     * ACR and valid salt key
     */
    @Test
    public void testAllGetters3() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = null;
        String appSaltKey = "z6u56I2kO34Aap16";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * empty string as salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAllGetters4() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * null value as salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAllGetters5() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = null;
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * 15 digit string as salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAllGetters6() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "z6u56I2kO34Aap1";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * 17 digit string as salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAllGetters7() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "z6u56I2kO34Aap16A";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String expAppID = null;
        String resultAppID = instance.getAppID();
        assertEquals(expAppID, resultAppID);

        String expServiceProviderID = null;
        String resultServiceProviderID = instance.getServiceProviderID();
        assertEquals(expServiceProviderID, resultServiceProviderID);

        String expMSISDN = null;
        String resultMSISDN = instance.getMsisdn();
        assertEquals(expMSISDN, resultMSISDN);

        String expCreateDateTime = null;
        String resultCreateDateTime = instance.getAcrCreatedDateTime();
        assertEquals(expCreateDateTime, resultCreateDateTime);
    }

    /**
     * Test of all getter methods, of class ACRDecrypt. Pass encrypted ACR and
     * 16 digit wrong salt key for decrypt the encrypted ACR
     */
    @Test(expected = BadPaddingException.class)
    public void testAllGetters8() throws Exception {
        System.out.println("testAllGetterMethods");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "HtZ364ibph5m2021";
        ACRDecrypt instance = new ACRDecrypt(encryptedACR, appSaltKey);

        String resultAppID = instance.getAppID();

        String resultServiceProviderID = instance.getServiceProviderID();

        String resultMSISDN = instance.getMsisdn();

        String resultCreateDateTime = instance.getAcrCreatedDateTime();
    }
}
