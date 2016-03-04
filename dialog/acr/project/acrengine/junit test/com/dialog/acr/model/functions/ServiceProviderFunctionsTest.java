/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.model.entities.ServiceProvider;
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
public class ServiceProviderFunctionsTest {
    
    public ServiceProviderFunctionsTest() {
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
     * Test of saveServiceProvider method, of class ServiceProviderFunctions.
     */
    @Test
    public void testSaveServiceProvider() {
        System.out.println("saveServiceProvider");
        
        ServiceProvider provider = new ServiceProvider();
        provider.setProviderCode("TESTPro1");
        provider.setProviderName("Testing Provider");
        provider.setSecurityKey("TSTK1");
        
        boolean expResult = true;
        boolean result = ServiceProviderFunctions.saveServiceProvider(provider);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateServiceProvider method, of class ServiceProviderFunctions.
     */
    @Test
    public void testUpdateServiceProvider() {
        System.out.println("updateServiceProvider");
        
        ServiceProvider provider = null;
        
        boolean expResult = false;
        boolean result = ServiceProviderFunctions.updateServiceProvider(provider);
        assertEquals(expResult, result);
    }
}
