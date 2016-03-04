/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dialog.acr.aes.key;

import javax.crypto.spec.SecretKeySpec;
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
public class AppSecretKeyTest {
    
    public AppSecretKeyTest() {
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
     * Test of createAppSecretKey method, of class AppSecretKey.
     * Pass 16 digit string as salt key
     */
    @Test
    public void testCreateAppSecretKey() throws Exception {
        System.out.println("createAppSecretKey");
        String appSaltKey = "MYsU1QSd54562h3F";
        SecretKeySpec result = AppSecretKey.createAppSecretKey(appSaltKey);
        assertNotNull(result);
    }   
}
