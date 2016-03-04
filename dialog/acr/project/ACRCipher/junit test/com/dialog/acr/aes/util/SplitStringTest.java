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
public class SplitStringTest {
    
    public SplitStringTest() {
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
     * Test of getSplittedString method, of class SplitString.
     * Pass a string which contain | as fullString
     */
    @Test
    public void testGetSplittedString() throws Exception {
        System.out.println("getSplittedString");
        String fullString = "123456|MIFE01|0775566288|2014-03-28 11:27:49.611";
        String[] expResult = {"123456","MIFE01","0775566288","2014-03-28 11:27:49.611"};
        String[] result = SplitString.getSplittedString(fullString);
        assertArrayEquals(expResult, result);
    }
}
