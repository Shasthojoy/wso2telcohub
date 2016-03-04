/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.decrypt;

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
public class DecryptTest {

    public DecryptTest() {
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
     * Test of acrDecrypt method, of class Decrypt. Pass encrypted ACR and
     * correct salt key for decrypt the encrypted ACR
     */
    @Test
    public void testAcrDecrypt() throws Exception {
        System.out.println("acrDecrypt");
        String encryptedACR = "6vb2a4O5KvdG5uET4LP0s+NguFG669nt7mQ1Yp8MClhpfVSo5Bzy+VD37mOSSkPOJjuNh7CNgOC14+1/p1rlBg==";
        String appSaltKey = "z6u56I2kO34Aap16";
        String expResult = "123456|MIFE01|0775566288|2014-03-28 11:27:49.611";
        String result = Decrypt.acrDecrypt(encryptedACR, appSaltKey);
        assertEquals(expResult, result);
    }
}
