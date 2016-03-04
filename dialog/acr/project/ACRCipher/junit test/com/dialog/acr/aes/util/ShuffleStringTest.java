/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dialog.acr.aes.util;

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
public class ShuffleStringTest {
    
    public ShuffleStringTest() {
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
     * Test of getShuffleString method, of class ShuffleString.
     * Pass 16 digit string as salt key text 
     */
    @Test
    public void testGetShuffleString1() throws Exception {
        System.out.println("getShuffleString");
        String appSaltKeyText = "1234564IWCwFSOTf";
        int expResult = 16;
        String result = ShuffleString.getShuffleString(appSaltKeyText);
        assertEquals(expResult, result.length());
    }   
}
