/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.controller.Getters;
import com.dialog.acr.model.entities.ApplicationKey;
import java.util.Date;
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
public class ApplicationKeyFunctionsTest {
    
    public ApplicationKeyFunctionsTest() {
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
     * Test of saveApplicationKey method, of class ApplicationKeyFunctions.
     */
    @Test
    public void testSaveApplicationKey() {
        System.out.println("saveApplicationKey");
        
        ApplicationKey key = new ApplicationKey();
        key.setAppKey("TESTKEY");
        key.setApplication(Getters.getApplicationObj(5));
        key.setCreatedOn(new Date());
        key.setDuration("31556952000");
        key.setVersion("1.0");
        
        boolean expResult = true;
        boolean result = ApplicationKeyFunctions.saveApplicationKey(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateApplicationKey method, of class ApplicationKeyFunctions.
     */
    @Test
    public void testUpdateApplicationKey() {
        System.out.println("updateApplicationKey");
        
        ApplicationKey key = Getters.getApplicationKeyObj(7);
        key.setAppKey("TestUpdated");
        
        boolean expResult = true;
        
        boolean result = ApplicationKeyFunctions.updateApplicationKey(key);
        assertEquals(expResult, result);
    }
}
