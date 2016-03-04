/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller.functions;

import java.util.List;
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
public class ACRControllerTest {
    
    public ACRControllerTest() {
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
     * Test of saveACR method, of class ACRController.
     */
    @Test
    public void testSaveACR() throws Exception {
        System.out.println("saveACR");
        
        String appCode = "";
        String devCode = "";
        List<String> msisdns = null;
        
        String expResult = "";
        
        String result = ACRController.saveACR(appCode, devCode, msisdns);
        assertEquals(expResult, result);
    }

    /**
     * Test of refreshACR method, of class ACRController.
     */
    @Test
    public void testRefreshACR() throws Exception {
        System.out.println("refreshACR");
        
        String appCode = "";
        String devCode = "";
        String acr = "";
        String expResult = "";
        
        String result = ACRController.refreshACR(appCode, devCode, acr);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteACR method, of class ACRController.
     */
    @Test
    public void testDeleteACR() throws Exception {
        System.out.println("deleteACR");
        String appCode = "";
        String devCode = "";
        String acr = "";
        String expResult = "";
        String result = ACRController.deleteACR(appCode, devCode, acr);
        assertEquals(expResult, result);
    }

    /**
     * Test of deactivateACR method, of class ACRController.
     */
    @Test
    public void testDeactivateACR() throws Exception {
        System.out.println("deactivateACR");
        String appCode = "";
        String devCode = "";
        String acr = "";
        String expResult = "";
        String result = ACRController.deactivateACR(appCode, devCode, acr);
        assertEquals(expResult, result);
    }

    /**
     * Test of retriveACR method, of class ACRController.
     */
    @Test
    public void testRetriveACR() throws Exception {
        System.out.println("retriveACR");
        String appCode = "";
        String devCode = "";
        String msisdn = "";
        String expResult = "";
        String result = ACRController.retriveACR(appCode, devCode, msisdn);
        assertEquals(expResult, result);
    }

    /**
     * Test of decodeACR method, of class ACRController.
     */
    @Test
    public void testDecodeACR() throws Exception {
        System.out.println("decodeACR");
        String appId = "";
        String acr = "";
        String expResult = "";
        String result = ACRController.decodeACR(appId, acr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
