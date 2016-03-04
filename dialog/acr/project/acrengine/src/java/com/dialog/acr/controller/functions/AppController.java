/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller.functions;

import com.dialog.acr.aes.util.RandomString;
import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.Getters;
import com.dialog.acr.controller.response.ProvisionAppResponse;
import com.dialog.acr.model.entities.Application;
import com.dialog.acr.model.entities.ApplicationCoupling;
import com.dialog.acr.model.functions.ApplicationCouplingFunctions;
import com.dialog.acr.model.functions.ApplicationFunctions;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author User
 */
public class AppController extends ACRServer {

    final static String characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String provisionApp(String providerCode, String appName, String providerAppId, String description) throws IOException {
        String responseJson = null;
        int providerId = Getters.getServiceProviderIdByProviderCode(providerCode);

        Application app = new Application();
        app.setAppName(appName);
        app.setServiceProviderAppId(providerAppId);
        app.setProvider(Getters.getServiceProviderObj(providerId));
        app.setAppDescription(description);
        app.setStatus(1);   //Default app status is Active      

        ProvisionAppResponse res = new ProvisionAppResponse();
        res.setAppName(appName);
        res.setServiceProviderAppId(providerAppId);
        res.setDescription(description);
        res.setServiceProviderID(providerCode);

        if (ApplicationFunctions.saveApplication(app)) {
            int appId = app.getAppId();
            String preAppCode = "";
            try {
                preAppCode = RandomString.getRandomString(6, 0, 61, true, true, characterSet);
            } catch (Exception e) {
                System.out.println("preAppKeyGeneration: " + e);
            }
            app.setAppCode(preAppCode + appId);
            if (ApplicationFunctions.updateApplication(app)) {
                res.setAppID(app.getAppCode());
                res.setStatus("Successful");
            }
        } else {
            res.setStatus("Not Successful");
        }

        ObjectMapper mapper = new ObjectMapper();
        responseJson = "{\"provisionAppResponse\":" + mapper.writeValueAsString(res) + "}";

        return responseJson;
    }
    
    public static String getApplicationData(int appid, String spCode) throws IOException{
        String responseJson = null;
        
        Application app = Getters.getApplicationObj(appid);

        ProvisionAppResponse res = new ProvisionAppResponse();
        res.setAppName(app.getAppName());
        res.setServiceProviderAppId(app.getServiceProviderAppId());
        res.setDescription(app.getAppDescription());
        res.setServiceProviderID(spCode);
        res.setAppID(app.getAppCode());
        res.setStatus("Already Exist");

        ObjectMapper mapper = new ObjectMapper();
        responseJson = "{\"provisionAppResponse\":" + mapper.writeValueAsString(res) + "}";

        return responseJson;
    }

    public static String deactiveAppAcr(int acrId, int actStatus) {
        String responseJson = null;
        return responseJson;
    }

    public static boolean coupleApps(int parentAppId, int couplingAppId) {
        boolean res = false;
        Application parentApp = Getters.getApplicationObj(parentAppId);
        Application couplingApp = Getters.getApplicationObj(couplingAppId);
        
        ApplicationCoupling coup = new ApplicationCoupling();
        coup.setCurrentApp(parentApp);
        coup.setCoupleAppId(couplingApp);
        coup.setStatus(1);
        
        if(ApplicationCouplingFunctions.saveAppCoupling(coup)){
            res = true;
        }
        return res;
    }
}
