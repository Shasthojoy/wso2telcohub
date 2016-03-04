/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dialog.acr.aes.key;

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
public class AppSaltKeyTest {
    
    public AppSaltKeyTest() {
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
     * Test of getAppSaltKey method, of class AppSaltKey.
     * This method will return 16 digit string as salt key
     */
    @Test
    public void testGetAppSaltKey() throws Exception {
        System.out.println("getAppSaltKey");
        int expResult = 16;
        String result = AppSaltKey.getAppSaltKey();
        assertEquals(expResult, result.length());
    }

    /**
     * Test of getAppSaltKey method, of class AppSaltKey.
     * Assume that caller of the this method will pass 6 digit string as appID
     */
    @Test
    public void testGetAppSaltKey_String1() throws Exception {
        System.out.println("getAppSaltKey");
        String appID = "App001";
        int expResult = 16;
        String result = AppSaltKey.getAppSaltKey(appID);
        assertEquals(expResult, result.length());
    }
}
