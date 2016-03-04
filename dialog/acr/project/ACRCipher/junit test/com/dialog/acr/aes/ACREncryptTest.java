/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes;

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
public class ACREncryptTest {

    public ACREncryptTest() {
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
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 6 digit string
     * as appID, serviceProviderID and valid msisdn with a 16 digit string as
     * salt key
     */
    @Test
    public void testGetEncryptedACR() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String result = instance.getEncryptedACR();
        assertNotNull(result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass empty strings
     * for appID, serviceProviderID, msisdn and salt key
     */
    @Test
    public void testGetEncryptedACR1() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "";
        String serviceProviderID = "";
        String msisdn = "";
        String appSaltKey = "";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass empty string
     * for appID
     */
    @Test
    public void testGetEncryptedACR2() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass empty string
     * for serviceProviderID
     */
    @Test
    public void testGetEncryptedACR3() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass empty string
     * for msisdn
     */
    @Test
    public void testGetEncryptedACR4() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass empty string
     * for salt key
     */
    @Test
    public void testGetEncryptedACR5() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass null value for
     * appID
     */
    @Test
    public void testGetEncryptedACR6() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = null;
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass null value for
     * serviceProviderID
     */
    @Test
    public void testGetEncryptedACR7() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = null;
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass null value for
     * msisdn
     */
    @Test
    public void testGetEncryptedACR8() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = null;
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass null value for
     * salt key
     */
    @Test
    public void testGetEncryptedACR9() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = null;
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass null values for
     * appID, serviceProviderID, msisdn and salt key
     */
    @Test
    public void testGetEncryptedACR10() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = null;
        String serviceProviderID = null;
        String msisdn = null;
        String appSaltKey = null;
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 5 digit string
     * as appID
     */
    @Test
    public void testGetEncryptedACR11() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App00";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 7 digit string
     * as appID
     */
    @Test
    public void testGetEncryptedACR12() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App0011";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 5 digit string
     * as serviceProvideID
     */
    @Test
    public void testGetEncryptedACR13() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE0";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 7 digit string
     * as serviceProvideID
     */
    @Test
    public void testGetEncryptedACR14() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE011";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 15 digit string
     * as salt key
     */
    @Test
    public void testGetEncryptedACR15() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEncryptedACR method, of class ACREncrypt. Pass 17 digit string
     * as salt key
     */
    @Test
    public void testGetEncryptedACR16() throws Exception {
        System.out.println("getEncryptedACR");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3FF";
        ACREncrypt instance = new ACREncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        String expResult = null;
        String result = instance.getEncryptedACR();
        assertEquals(expResult, result);
    }
}
