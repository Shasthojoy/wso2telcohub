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
 * @author User
 */
public class ACRAppKeyTest {

    public ACRAppKeyTest() {
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
     * Test of getAppSaltKey method, of class ACRAppKey. Pass 1 digit integer value as
     * appID
     */
    @Test
    public void testGetAppSaltKey1() throws Exception {
        System.out.println("getAppSaltKey");
        int appID = 1;
        ACRAppKey instance = new ACRAppKey(appID);
        int expResult = 16;
        String result = instance.getAppSaltKey();
        assertEquals(expResult, result.length());
    }

    /**
     * Test of getAppSaltKey method, of class ACRAppKey. Pass 3 digit integer value
     * appID
     */
    @Test
    public void testGetAppSaltKey2() throws Exception {
        System.out.println("getAppSaltKey");
        int appID = 123;
        ACRAppKey instance = new ACRAppKey(appID);
        int expResult = 16;
        String result = instance.getAppSaltKey();
        assertEquals(expResult, result.length());
    }

    /**
     * Test of getAppSaltKey method, of class ACRAppKey. Pass 7 digit integer value
     * appID
     */
    @Test
    public void testGetAppSaltKey3() throws Exception {
        System.out.println("getAppSaltKey");
        int appID = 1234789;
        ACRAppKey instance = new ACRAppKey(appID);
        int expResult = 16;
        String result = instance.getAppSaltKey();
        assertEquals(expResult, result.length());
    }

    /**
     * Test of getAppSaltKey method, of class ACRAppKey. Pass 0 as
     * appID
     */
    @Test
    public void testGetAppSaltKey4() throws Exception {
        System.out.println("getAppSaltKey");
        int appID = 0;
        ACRAppKey instance = new ACRAppKey(appID);
        String expResult = null;
        String result = instance.getAppSaltKey();
        assertEquals(expResult, result);
    }
}
