/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.encrypt;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class EncryptTest {

    public EncryptTest() {
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
     * Test of acrEncrypt method, of class Encrypt.Pass 6 digit string as appID,
     * serviceProviderID and valid msisdn with a 16 digit string as salt key
     * method will return the above values as a single encrypted string
     */
    @Test
    public void testAcrEncrypt() throws Exception {
        System.out.println("acrEncrypt");
        String appID = "App001";
        String serviceProviderID = "MIFE01";
        String msisdn = "0775566288";
        String appSaltKey = "MYsU1QSd54562h3F";
        String result = Encrypt.acrEncrypt(appID, serviceProviderID, msisdn, appSaltKey);
        System.out.println("Encrypted ACR: " + result);
        assertNotNull(result);
    }
}
