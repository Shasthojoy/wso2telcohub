/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.view.request;

import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.Getters;
import com.dialog.acr.controller.ValidationRule;
import com.dialog.acr.controller.Validations;
import com.dialog.acr.controller.functions.AppController;
import com.dialog.acr.controller.response.ErrorResponse;
import com.dialog.acr.model.entities.Application;
import com.dialog.acr.view.RequestHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
public class AppProvisionServlet extends ACRServer {

    static Logger logger = Logger.getLogger(AppProvisionServlet.class);

    public void init() throws ServletException {
        logger.debug("AppProvisionServlet initialised");
    }
    private final String[] validationRules = {"V1", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");

        if (validateRequest(request, response, requestParts, validationRules)) {
            //if(true){

            String ProviderCode = null;
            String appName = null;
            String providerAppId = "";
            String description = null;

            JSONParser objJSONParser = new JSONParser();
            String requestString = RequestHandler.getRequestJSON(request);

            try {
                JSONObject requestJsonObj = (JSONObject) objJSONParser.parse(requestString);
                JSONObject createAcrRequest = (JSONObject) requestJsonObj.get("provisionAppRequest");

                if (requestParts[1] != null) {
                    ProviderCode = requestParts[1]; //createAcrRequest.get("serviceProviderID").toString();
                }
                if (createAcrRequest.get("appName") != null) {
                    appName = createAcrRequest.get("appName").toString();
                }
                if (createAcrRequest.get("serviceProviderAppId") != null) {
                    providerAppId = createAcrRequest.get("serviceProviderAppId").toString();
                }
                if (createAcrRequest.get("description") != null) {
                    description = createAcrRequest.get("description").toString();
                }
            } catch (Exception e) {
                logger.debug("Manipulating received JSON: " + e);
            }

            ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "serviceProviderID", ProviderCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "serviceProviderAppId", providerAppId),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "appName", appName),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),};

            if (checkRequestParameters(response, rule)) {
                try {
                    //System.out.println("XXXXXXXXXX: "+serviceProviderId+" : "+appName+" : "+description);
                    if (Validations.isAppNameExists(appName)) {
                        int applicationId = Getters.getAppIdByAppName(appName);
                        String providerId = Getters.getServiceProviderIdByProviderCode(ProviderCode) + "";
                        if (Validations.getProviderIDFromAppID(applicationId) == Integer.parseInt(providerId)) {
                            String responseJson = AppController.getApplicationData(applicationId, ProviderCode);
                            String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(appName);
                            sendJSONResponse(response, responseJson, ACCEPTED, resourceURL);
                        } else {
                            logger.debug("App is not provisioned to use Provider");
                            System.out.println("App is not provisioned to use Provider");
                            sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX07", "App is not provisioned to use Provider", appName);
                        }
                        //sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX18", "AppName already exists", appName);
                    } else {
                        String responseJson = AppController.provisionApp(ProviderCode, appName, providerAppId, description);
                        String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(appName);
                        sendJSONResponse(response, responseJson, CREATED, resourceURL);
                    }
                } catch (Exception e) {
                    logger.debug("AppProvisionService: " + e);
                }
            }

        }
    }
}
