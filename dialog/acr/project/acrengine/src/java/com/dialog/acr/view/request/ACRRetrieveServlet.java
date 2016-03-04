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
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
@WebServlet(name = "ACRRetrieveServlet", urlPatterns = {"/ACRRetrieveService"})
public class ACRRetrieveServlet extends ACRServer {

    static Logger logger = Logger.getLogger(ACRRetrieveServlet.class);

    public void init() throws ServletException {
        logger.debug("ACRRetrieveServlet initialised");
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
            String msisdn = null;

            JSONParser objJSONParser = new JSONParser();
            String requestString = RequestHandler.getRequestJSON(request);
            System.out.println("request: " + requestString);

            try {
                JSONObject refreshJsonObj = (JSONObject) objJSONParser.parse(requestString);
                JSONObject refreshAcrRequest = (JSONObject) refreshJsonObj.get("retriveAcrRequest");

                if (requestParts[2] != null) {
                    appCode = requestParts[2]; //refreshAcrRequest.get("appID").toString();
                }
                if (requestParts[1] != null) {
                    developerCode = requestParts[1]; //refreshAcrRequest.get("serviceProviderID").toString();
                }
                if (refreshAcrRequest.get("MSISDN") != null) {
                    msisdn = refreshAcrRequest.get("MSISDN").toString();
                }
            } catch (Exception e) {
                logger.debug("Manipulating request JSON: " + e);
            }

            System.out.println("appID: " + appCode);
            System.out.println("serviceProviderID: " + developerCode);
            System.out.println("MSISDN: " + msisdn);

            ValidationRule[] rule = {
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "appID", appCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "serviceProviderID", developerCode),
                new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "MSISDN", msisdn),};

            String applicationId = Getters.getAppIdByAppCode(appCode) + "";
            String providerId = Getters.getServiceProviderIdByProviderCode(developerCode) + "";

            if (checkRequestParameters(response, rule)) {
                try {
                    if (Validations.getProviderIDFromAppID(Integer.parseInt(applicationId)) == Integer.parseInt(providerId)) {
                        if (Getters.getAppIdIsValid(applicationId)) {
                            if (Getters.getAppStatus(Integer.parseInt(applicationId)) != 1) {
                                logger.debug("Application is not valid");
                                System.out.println("Application is not valid");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX06", "Application is not valid", appCode);
                            } else if (!Getters.getProviderIdIsValid(providerId)) {
                                logger.debug("ProviderId is not available");
                                System.out.println("ProviderId is not available");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX02", "ProviderId is Invalid", developerCode);
                            } else if (!Validations.isMSISDNExistForAppId(Integer.valueOf(applicationId), msisdn)) {
                                System.out.println("NO ACR available for requested data");
                                sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX03", "ACR Not found for request data", msisdn);
                            } else {
                                String responseJson = ACRController.retriveACR(appCode, developerCode, msisdn);
                                String resourceURL = getRequestHostnameAndContext(request) + request.getServletPath() + "/V1/" + urlEncode(msisdn);
                                if (responseJson == null) {
                                    sendError(response, BAD_REQUEST, ErrorResponse.SERVICEEXCEPTION, "EX03", "ACR Not found for request data", null);
                                } else {
                                    sendJSONResponse(response, responseJson, ACCEPTED, resourceURL);
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
                    logger.debug("ACRRetrieveService: " + e);
                }
            }
        }
    }
}
