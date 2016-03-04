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
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
public class ACRDecodeServlet extends ACRServer {

    static Logger logger = Logger.getLogger(ACRDecodeServlet.class);

    public void init() throws ServletException {
        logger.debug("ACRDecodeServlet initialised");
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
                JSONObject refreshAcrRequest = (JSONObject) refreshJsonObj.get("decodeAcrRequest");

                if (requestParts[2] != null) {
                    appCode = requestParts[2]; //refreshAcrRequest.get("appID").toString();
                }
                if (requestParts[1] != null) {
                    developerCode = requestParts[1];
                }
                if (refreshAcrRequest.get("acr") != null) {
                    acr = refreshAcrRequest.get("acr").toString();
                }                
            } catch (Exception e) {
                logger.debug("Manipulating request JSON: " + e);
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
                            if (Getters.getACRStatus(acr) == 1) {
                                String responseJson = ACRController.decodeACR(String.valueOf(applicationId), acr);
                                String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(appCode);
                                sendJSONResponse(response, responseJson, ACCEPTED, resourceURL);
                            } else {
                                logger.debug("ACR is not valid");
                                System.out.println("ACR is not valid");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX06", "ACR is not valid", acr);
                            }
                        } else {
                            logger.debug("ACR not found for relevent app");
                            sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX02", "Requested ACR is invalid", acr);
                        }
                    } else {
                        logger.debug("App is not provisioned to use Provider");
                        System.out.println("App is not provisioned to use Provider");
                        sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX07", "App is not provisioned to use Provider", appCode);
                    }
                } catch (Exception e) {
                    logger.debug("ACRDeactivateServlet: " + e);
                }
            }
        }
    }
}
