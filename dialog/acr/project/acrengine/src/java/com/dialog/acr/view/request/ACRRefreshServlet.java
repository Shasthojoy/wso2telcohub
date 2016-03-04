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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
@WebServlet(name = "ACRRefreshServlet", urlPatterns = {"/ACRRefreshService"})
public class ACRRefreshServlet extends ACRServer {

    static Logger logger = Logger.getLogger(ACRRefreshServlet.class);

    public void init() throws ServletException {
        logger.debug("ACRRefreshServlet initialised");
    }
    private final String[] validationRules = {"V1", "*", "*"};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dumpRequestDetails(request, logger);
        String[] requestParts = getRequestParts(request);
        logger.debug("Response JSON: " + "before validate");

        if (validateRequest(request, response, requestParts, validationRules)) {

            String appCode = null;
            String developerCode = null;
            String acr = null;

            JSONParser objJSONParser = new JSONParser();
            String requestString = RequestHandler.getRequestJSON(request);

            try {
                JSONObject refreshJsonObj = (JSONObject) objJSONParser.parse(requestString);
                JSONObject refreshAcrRequest = (JSONObject) refreshJsonObj.get("refreshAcrRequest");

                if (requestParts[2] != null) {
                    appCode = requestParts[2]; //refreshAcrRequest.get("appID").toString();
                }
                if (requestParts[1] != null) {
                    developerCode = requestParts[1]; //refreshAcrRequest.get("serviceProviderID").toString();
                }
                if (refreshAcrRequest.get("acr") != null) {
                    acr = refreshAcrRequest.get("acr").toString();
                }              
            } catch (Exception e) {
                logger.debug("Manipulating request JSON: " + e);
                System.out.println("Manipulating received JSON: " + e);
            }

            ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "appID", appCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "serviceProviderID", developerCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "acr", acr),};

            int applicationId = Getters.getAppIdByAppCode(appCode);
            int providerId = Getters.getServiceProviderIdByProviderCode(developerCode);

            if (checkRequestParameters(response, rule)) {
                try {
                    if (Validations.getProviderIDFromAppID(applicationId) == providerId) {
                        if (Validations.isACRIdExistForAppIdAndACR(applicationId, acr)) {
                            if (Getters.getAppStatus(applicationId) == 1) {
                                if (Getters.getACRStatus(acr) == 1) {
                                    String responseJson = ACRController.refreshACR(appCode, developerCode, acr);
                                    String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(developerCode);
                                    if (responseJson == null) {
                                        sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX04", "Requested operaion failed!", null);
                                    } else {
                                        sendJSONResponse(response, responseJson, ACCEPTED, resourceURL);
                                    }
                                } else {
                                    logger.debug("ACR is not valid");
                                    System.out.println("ACR is not valid");
                                    sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX06", "ACR is not valid", acr);
                                }
                            } else {
                                logger.debug("Application is not valid");
                                System.out.println("Application is not valid");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX06", "Application is not valid", appCode);
                            }
                        } else {
                            logger.debug("ACR not found in DB");
                            System.out.println("ACR not found in DB");
                            sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX02", "Requested ACR is invalid", acr);
                        }
                    } else {
                        logger.debug("App is not provisioned to use Provider");
                        System.out.println("App is not provisioned to use Provider");
                        sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX07", "App is not provisioned to use Provider", appCode);
                    }
                } catch (Exception e) {
                    logger.debug("ASRRefreshServlet: " + e);
                    System.out.println("ASRRefreshServlet: " + e);
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
