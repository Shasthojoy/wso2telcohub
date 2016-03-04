/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.controller.Getters;
import com.dialog.acr.model.entities.Application;
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
public class ApplicationFunctionsTest {
    
    public ApplicationFunctionsTest() {
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
     * Test of saveApplication method, of class ApplicationFunctions.
     */
    @Test
    public void testSaveApplication() {
        System.out.println("saveApplication");
        Application application = new Application();
        application.setAppName("JUnit Test App");
        application.setCreater("Hiranya");
        application.setStatus(1);
        application.setAppDescription("Hiranya added this to test functions");
        
        boolean expResult = true;
        boolean result = ApplicationFunctions.saveApplication(application);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateApplication method, of class ApplicationFunctions.
     */
    @Test
    public void testUpdateApplication() {
        System.out.println("updateApplication");
        Application application = Getters.getApplicationObj(3);
        application.setAppDescription("Edited by Hiranya in Testing");
        boolean expResult = true;
        boolean result = ApplicationFunctions.updateApplication(application);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAppStatus method, of class ApplicationFunctions.
     */
    @Test
    public void testUpdateAppStatus() {
        System.out.println("updateAppStatus");
        int appid = 2;
        int status = 0;
        boolean expResult = true;
        boolean result = ApplicationFunctions.updateAppStatus(appid, status);
        assertEquals(expResult, result);
    }
}
