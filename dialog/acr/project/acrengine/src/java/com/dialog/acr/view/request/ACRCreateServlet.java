/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.view.request;

import com.dialog.acr.controller.ACRServer;
import com.dialog.acr.controller.Getters;
import com.dialog.acr.controller.ValidationRule;
import com.dialog.acr.controller.Validations;
import com.dialog.acr.controller.functions.ACRController;
import com.dialog.acr.controller.response.ErrorResponse;
import com.dialog.acr.view.RequestHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
@WebServlet(name = "ACRCreateServlet", urlPatterns = {"/ACRCreateService"})
public class ACRCreateServlet extends ACRServer {

    static Logger logger = Logger.getLogger(ACRCreateServlet.class);

    public void init() throws ServletException {
        logger.debug("ACRCreateServlet initialised");
        System.out.println("ACRCreateServlet initialised");
    }
    private final String[] validationRules = {"V1", "*", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");

        if (validateRequest(request, response, requestParts, validationRules)) {
            // if(true){  

            String appCode = null;
            String developerCode = null;
            List<String> msisdn = new ArrayList<String>();

            JSONParser objJSONParser = new JSONParser();
            String requestString = RequestHandler.getRequestJSON(request);

            try {
                JSONObject requestJsonObj = (JSONObject) objJSONParser.parse(requestString);
                JSONObject createAcrRequest = (JSONObject) requestJsonObj.get("createAcrRequest");

                if (requestParts[2] != null) {
                    appCode = requestParts[2]; //createAcrRequest.get("appID").toString();
                }
                if (requestParts[1] != null) {
                    developerCode = requestParts[1]; //createAcrRequest.get("serviceProviderID").toString();
                }
                if (createAcrRequest.get("MSISDN") != null) {
                    JSONArray numberArray = (JSONArray) createAcrRequest.get("MSISDN");
                    Iterator<String> numberIterator = numberArray.iterator();
                    while (numberIterator.hasNext()) {
                        msisdn.add(numberIterator.next());
                    }
                }
            } catch (Exception e) {
                logger.debug("Manipulating received JSON: " + e);
                System.out.println("Manipulating received JSON: " + e);
            }

            System.out.println("appId: " + appCode);
            System.out.println("serviceProviderID: " + developerCode);
            System.out.println("msisdn: " + msisdn.toArray(new String[msisdn.size()]).toString());

            ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "appId", appCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "serviceProviderID", developerCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "MSISDN", msisdn.toArray(new String[msisdn.size()])),};

            String providerId = Getters.getServiceProviderIdByProviderCode(developerCode) + "";
            String applicationId = Getters.getAppIdByAppCode(appCode) + "";

            if (checkRequestParameters(response, rule)) {
                try {
                    if (Validations.getProviderIDFromAppID(Integer.parseInt(applicationId)) == Integer.parseInt(providerId)) {
                        if (Getters.getAppIdIsValid(applicationId)) {
                            //System.out.println("QQQQQQQQQQQQQQQQQ: "+Getters.getAppStatus(Integer.parseInt(applicationId)));
                            if (Getters.getAppStatus(Integer.parseInt(applicationId)) != 1) {
                                logger.debug("Application is not valid");
                                System.out.println("Application is not valid");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX06", "Application is not valid", appCode);
                            } else if (!Getters.getProviderIdIsValid(providerId)) {
                                logger.debug("ProviderId is not available");
                                System.out.println("ProviderId is not available");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX02", "ProviderId is Invalid", developerCode);
                            } else {
                                int duplicates = 0;
                                for (int x = 0; msisdn.size() > x; x++) {
                                    if (Validations.isMSISDNExistForAppId(Integer.parseInt(applicationId), msisdn.get(x))) {
                                        duplicates += 1;
                                    }
                                }
                                if (duplicates == 0) {
                                    String responseJson = ACRController.saveACR(appCode, developerCode, msisdn);
                                    String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(developerCode);
                                    sendJSONResponse(response, responseJson, CREATED, resourceURL);
                                } else {
                                    sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX05", "MSISDN list contains existing numbers", msisdn.toString());
                                }
                            }
                        } else {
                            logger.debug("AppId is not available");
                            System.out.println("AppId is not Available");
                            sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX01", "AppId is Invalid", appCode);
                        }
                    } else {
                        logger.debug("App is not provisioned to use Provider");
                        System.out.println("App is not provisioned to use Provider");
                        sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX07", "App is not provisioned to use Provider", appCode);
                    }
                } catch (Exception e) {
                    logger.debug("ACRCreateServlet: " + e);
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
