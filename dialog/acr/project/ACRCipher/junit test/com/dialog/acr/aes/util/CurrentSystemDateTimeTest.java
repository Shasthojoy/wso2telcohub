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
public class CurrentSystemDateTimeTest {

    public CurrentSystemDateTimeTest() {
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
     * Test of getCurrentSystemDateTime method, of class CurrentSystemDateTime.
     * This method will return current date and time
     */
    @Test
    public void testGetCurrentSystemDateTime() {
        System.out.println("getCurrentSystemDateTime");
        String result = CurrentSystemDateTime.getCurrentSystemDateTime();
        System.out.println("Current Date and Time: " + result + "\n");
        assertNotNull(result);
    }

    /**
     * Test of getCurrentSystemDateTimeAsSingleString method, of class
     * CurrentSystemDateTime. This method will return current date and time as a
     * single string without characters such as :, -, . and spaces
     */
    @Test
    public void testGetCurrentSystemDateTimeAsSingleString() {
        System.out.println("getCurrentSystemDateTimeAsSingleString");
        String result = CurrentSystemDateTime.getCurrentSystemDateTimeAsSingleString();
        System.out.println("Current Date and Time: " + result);
        assertNotNull(result);
    }
}
