/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.model.functions;

import com.dialog.acr.controller.Getters;
import com.dialog.acr.model.entities.ACR;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ACRFunctionsTest {

    public ACRFunctionsTest() {
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
     * Test of saveACR method, of class ACRFunctions.
     */
    @Test
    public void testSaveACR() {
        System.out.println("saveACR");

        ACR acr = new ACR();
        acr.setAcr("TEST123456");
        acr.setApplication(Getters.getApplicationObj(2));
        acr.setExpire_period("450");
        acr.setCreatedDate(new Date());
        acr.setMsisdn("0773123456");
        acr.setStatus(1);
        acr.setVersion("1.5");

        boolean expResult = true;
        boolean result = ACRFunctions.saveACR(acr);
        assertEquals(expResult, result);

    }

    /**
     * Test of saveACRList method, of class ACRFunctions.
     */
    @Test
    public void testSaveACRList() {
        System.out.println("saveACRList");
        List<ACR> acrList = new ArrayList<ACR>();
        
        ACR acr1 = new ACR();
        acr1.setAcr("TESTX1");
        acr1.setApplication(Getters.getApplicationObj(2));
        acr1.setExpire_period("450");
        acr1.setCreatedDate(new Date());
        acr1.setMsisdn("0773321345");
        acr1.setStatus(1);
        acr1.setVersion("1.5");
        
        acrList.add(acr1);
        
        ACR acr2 = new ACR();
        acr2.setAcr("TESTX2");
        acr2.setApplication(Getters.getApplicationObj(2));
        acr2.setExpire_period("450");
        acr2.setCreatedDate(new Date());
        acr2.setMsisdn("0773985632");
        acr2.setStatus(1);
        acr2.setVersion("1.5");
        
        acrList.add(acr2);
        
        boolean expResult = true;
        boolean result = ACRFunctions.saveACRList(acrList);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateACR method, of class ACRFunctions.
     */
    @Test
    public void testUpdateACR() {
        System.out.println("updateACR");
        ACR acr = Getters.getACRObj(9);
        acr.setAcr("UPDATEDXX1");
        boolean expResult = true;
        boolean result = ACRFunctions.updateACR(acr);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateACRStatus method, of class ACRFunctions.
     */
    @Test
    public void testUpdateACRStatus() {
        System.out.println("updateACRStatus");
        int acrId = 8;
        int status = 0;
        boolean expResult = true;
        boolean result = ACRFunctions.updateACRStatus(acrId, status);
        assertEquals(expResult, result);
    }
}
