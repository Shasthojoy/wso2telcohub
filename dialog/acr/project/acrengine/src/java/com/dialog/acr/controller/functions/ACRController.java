/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller.functions;

import com.dialog.acr.aes.ACRDecrypt;
import com.dialog.acr.aes.ACREncrypt;
import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.Getters;
import com.dialog.acr.controller.Validations;
import com.dialog.acr.controller.response.CreateACRResponse;
import com.dialog.acr.controller.response.CreateACRResponse.ACRInfo;
import com.dialog.acr.controller.response.DeactivateACRResponse;
import com.dialog.acr.controller.response.DecodeACRResponse;
import com.dialog.acr.controller.response.DeleteACRBatchResponse;
import com.dialog.acr.controller.response.DeleteACRResponse;
import com.dialog.acr.controller.response.RefreshACRResponse;
import com.dialog.acr.controller.response.RetriveACRResponse;
import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ACR;
import com.dialog.acr.model.entities.Application;
import com.dialog.acr.model.functions.ACRFunctions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class ACRController extends ACRServer {

    public static String saveACR(String appCode, String devCode, List<String> msisdns) throws IOException {

        String responseJsonString = "";
        boolean isException = false;
        String devId = Getters.getServiceProviderIdByProviderCode(devCode) + "";
        String appId = Getters.getAppIdByAppCode(appCode) + "";

        ArrayList<String> numbers = new ArrayList<String>(msisdns);

        Session s = HibernateUtil.getSession();

        Application app = (Application) s.get(Application.class, Integer.valueOf(appId));
        s.close();
        List<ACRInfo> acrArray = new ArrayList<ACRInfo>();
        List<ACR> acrList = new ArrayList<ACR>();

        for (int x = 0; x < numbers.size(); x++) {
            String encriptedACR = "";
            try {
                ACREncrypt objACREncrypt = new ACREncrypt(appCode, devCode, numbers.get(x), Getters.getApplicationKeyByAppID(appId));
                encriptedACR = objACREncrypt.getEncryptedACR();
            } catch (Exception e) {
                System.out.println("Gnerating ACR: " + e);
            }
            ACR acr = new ACR();
            acr.setAcr(encriptedACR);
            acr.setApplication(app);
            acr.setCreatedDate(new Date());
            acr.setExpire_period("31556952000");
            acr.setStatus(1);                       //iniecial status is Active
            acr.setMsisdn(numbers.get(x));
            acr.setVersion("1.0");

            acrList.add(acr);

            ACRInfo acrInfo = new ACRInfo();
            acrInfo.setAcr(encriptedACR);
            acrInfo.setExpiry("31556952000");
            acrInfo.setMsisdn(numbers.get(x));

            acrArray.add(acrInfo);

        }
        if (ACRFunctions.saveACRList(acrList)) {
            CreateACRResponse responseObj = new CreateACRResponse();
            responseObj.setAppID(appCode);
            responseObj.setServiceProviderID(devCode);
            responseObj.setAcrInfo(acrArray);

            ObjectMapper mapper = new ObjectMapper();
            responseJsonString = "{\"createAcrResponse\":" + mapper.writeValueAsString(responseObj) + "}";

        } else {
            //error in saving ACR List
            isException = true;
            System.out.println("Saving ACRs Exception");
        }
        return responseJsonString;

    }

    public static String refreshACR(String appCode, String devCode, String acr) throws IOException {
        String responseJson = null;
        Session s = HibernateUtil.getSession();

        String applicationId = Getters.getAppIdByAppCode(appCode) + "";

        ACR old_acr = (ACR) s.get(ACR.class, Integer.valueOf(Getters.getACRIDByACR(acr)));
        s.close();
        ACREncrypt objACREncrypt;
        String encriptedACR = "";

        try {
            objACREncrypt = new ACREncrypt(appCode, devCode, old_acr.getMsisdn(), Getters.getApplicationKeyByAppID(applicationId));
            encriptedACR = objACREncrypt.getEncryptedACR();
        } catch (Exception e) {
            System.out.println("refreshACR: " + e);
        }

        old_acr.setAcr(encriptedACR);

        if (ACRFunctions.updateACR(old_acr)) {
            RefreshACRResponse r = new RefreshACRResponse();
            r.setAppID(appCode);
            r.setServiceProviderID(devCode);
            r.setAcr(encriptedACR);
            r.setExpiry("31556952000");

            ObjectMapper mapper = new ObjectMapper();
            responseJson = "{\"refreshAcrResponse\":" + mapper.writeValueAsString(r) + "}";
        } else {
            System.out.println("ACR Update not successful");
        }

        return responseJson;
    }

    public static String deleteACR(String appCode, String devCode, String acr) throws IOException {
        final int DELETE_STATUS = 101;
        String responseJson = null;
        boolean isOperationFailed = false;

        DeleteACRResponse res = new DeleteACRResponse();
        res.setAcr(acr);
        res.setServiceProviderID(devCode);
        res.setAppID(appCode);
        if (ACRFunctions.updateACRStatus(Integer.valueOf(Getters.getACRIDByACR(acr)), DELETE_STATUS)) {
            res.setStatus("deleted");
        } else {
            //Error in ACR Deleting process
            isOperationFailed = true;
            res.setStatus("failed");
        }

        ObjectMapper mapper = new ObjectMapper();
        responseJson = "{\"deleteAcrResponse\":" + mapper.writeValueAsString(res) + "}";
        return responseJson;
    }
    
    public static String deleteACRBatch(int providerId, String msisdn, String providerKey) throws IOException {
        final int DELETE_STATUS = 101;
        String responseJson = "";
        
        DeleteACRBatchResponse responseObj = new DeleteACRBatchResponse();
        int batchSize = 0;
        List <DeleteACRBatchResponse.AcrDeleteInfo> infoList = new ArrayList<DeleteACRBatchResponse.AcrDeleteInfo>();
        
        List appList = Getters.getAppIdListByProviderId(providerId);
        for (int x = 0; x < appList.size(); x++) {
            int appId = (int) appList.get(x);
            String appName = Getters.getAppCodeByAppId(appId);
            
            if(Validations.isACRExistForAppAndMsisdn(appId, msisdn)){
                List acrIdList = Getters.getACRIDListByAppIdAndMsisdn(appId, msisdn);
                for (int y = 0; y < acrIdList.size(); y++) {
                    int acrIdTemp = (int) acrIdList.get(y);
                    
                    DeleteACRBatchResponse.AcrDeleteInfo acrInfo = new DeleteACRBatchResponse.AcrDeleteInfo();
                    acrInfo.setServiceProviderID(providerKey);
                    acrInfo.setAppID(appName);
                    acrInfo.setAcr(Getters.getACRbyId(acrIdTemp));
                    if(ACRFunctions.updateACRStatus(acrIdTemp, DELETE_STATUS)){
                        acrInfo.setStatus("Successfully Deleted");
                        batchSize = batchSize+1;
                    } else {
                        acrInfo.setStatus("Failed");
                    } 
                    infoList.add(acrInfo);
                }
            }
        }
        responseObj.setAcrInfo(infoList);
        responseObj.setBatchSize(String.valueOf(batchSize));
        
        ObjectMapper mapper = new ObjectMapper();
        responseJson = "{\"deleteAcrResponse\":" + mapper.writeValueAsString(responseObj) + "}";
        return responseJson;
    }

    public static String deactivateACR(String appCode, String devCode, String acr) throws IOException {
        final int DEACT_STATUS = 0;
        String responseJson = null;
        boolean isOperationFailed = false;

        DeactivateACRResponse res = new DeactivateACRResponse();
        res.setAcr(acr);
        res.setAppID(appCode);
        res.setServiceProviderID(devCode);

        if (ACRFunctions.updateACRStatus(Integer.valueOf(Getters.getACRIDByACR(acr)), DEACT_STATUS)) {
            res.setStatus("Successful");
        } else {
            isOperationFailed = true;
            res.setStatus("Failed");
        }

        ObjectMapper mapper = new ObjectMapper();
        responseJson = "{\"deactivateAcrResponse\":" + mapper.writeValueAsString(res) + "}";

        return responseJson;
    }

    public static String retriveACR(String appCode, String devCode, String msisdn) throws IOException {
        String responseJson = null;
        String applicationId = Getters.getAppIdByAppCode(appCode) + "";

        String acr = "";
        if (Getters.acrIsExists(applicationId, msisdn)) {
            acr = getACRByDetails(applicationId, msisdn);
            RetriveACRResponse r = new RetriveACRResponse();
            r.setAppID(appCode);
            r.setMsisdn(msisdn);
            r.setServiceProviderID(devCode);
            r.setAcr(acr);

            ObjectMapper mapper = new ObjectMapper();
            responseJson = "{\"retriveAcrResponse\":" + mapper.writeValueAsString(r) + "}";
        } else {
            responseJson = null;
        }
        return responseJson;
    }

    private static String getACRByDetails(String appId, String msisdn) {
        String acr = null;
        Session s = HibernateUtil.getSession();
        try {
            String acrString = "SELECT acr.acr FROM ACR AS acr WHERE acr.application.appId = :appId AND acr.msisdn = :msisdn AND acr.status != 101";
            Query acrQuery = s.createQuery(acrString);
            acrQuery.setString("appId", appId);
            acrQuery.setString("msisdn", msisdn);

            List acrList = acrQuery.list();

            Iterator iter = acrList.iterator();
            while (iter.hasNext()) {
                acr = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getACRByDetails: " + e);
        } finally {
            s.close();
        }
        return acr.toString();
    }

    public static String decodeACR(String appId, String acr) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = null;
        DecodeACRResponse re = null;

        try {
            ACRDecrypt decript = new ACRDecrypt(acr, Getters.getApplicationKeyByAppID(appId));

            re = new DecodeACRResponse();
            re.setAppId(decript.getAppID());
            re.setMSISDN(decript.getMsisdn());
            re.setProviderId(decript.getServiceProviderID());
            re.setTimestamp(decript.getAcrCreatedDateTime());

            responseJson = "{\"decodeAcrResponse\":" + mapper.writeValueAsString(re) + "}";
        } catch (Exception e) {
            System.out.println("decodeACR: " + e);
        }
        return responseJson;
    }
}
