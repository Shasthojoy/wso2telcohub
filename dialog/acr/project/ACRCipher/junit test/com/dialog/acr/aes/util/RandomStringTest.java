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
public class RandomStringTest {

    public RandomStringTest() {
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
     * Test of getRandomString method, of class RandomString.
     */
    @Test
    public void testGetRandomString() throws Exception {
        System.out.println("getRandomString");
        int stringLength = 10;
        int stringFrom = 0;
        int stringTo = 61;
        boolean letters = false;
        boolean numbers = false;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int expResult = stringLength;
        String result = RandomString.getRandomString(stringLength, stringFrom, stringTo, letters, numbers, characters);
        assertEquals(expResult, result.length());
    }
}
