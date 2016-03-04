package org.dialog.custom.hostobjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dialog.custom.hostobjects.internal.HostObjectComponent;
import org.dialog.custom.hostobjects.util.ChargingPlanType;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.APIManager;
import org.wso2.carbon.apimgt.api.model.*;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.AbstractAPIManager;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsagePublisherConstants;

import com.sun.jna.Native;

public class HostObjectUtils {

    private static final Log log = LogFactory.getLog(HostObjectUtils.class);
    private static final String ALL_SUBSCRIBERS_KEYWORD = "__ALL__";
    private static final String ALL_APPLICATIONS_KEYWORD = "__ALL__";

    protected static boolean checkDataPublishingEnabled() {
//		APIManagerConfiguration configuration =
//		                                        ServiceReferenceHolder.getInstance()
//		                                                              .getAPIManagerConfigurationService()
//		                                                              .getAPIManagerConfiguration();

        APIManagerConfiguration configuration = HostObjectComponent.getAPIManagerConfiguration();
        String enabledStr =
                configuration.getFirstProperty(APIMgtUsagePublisherConstants.API_USAGE_ENABLED);
        return enabledStr != null && Boolean.parseBoolean(enabledStr);
    }

    public static Map<Application, Set<BillingSubscription>> getBillingSubscriptionsForUser(String username)
            throws APIManagementException {
        Subscriber subscriber = new Subscriber(username);
        ApiMgtDAO apiMgtDAO = new ApiMgtDAO();
        Set<SubscribedAPI> apis = apiMgtDAO.getSubscribedAPIs(subscriber);
        Map<Application, Set<BillingSubscription>> billingDetails = null;
        if (apis != null) {
            billingDetails = new HashMap<Application, Set<BillingSubscription>>();
            log.info("::: Subscribed APIs not null :::");
            for (SubscribedAPI api : apis) {
                log.info("::: Subscribed API : " + api.getApplication().getName() + " , "
                        + api.getTier().getName() + " , " + api.getApiId());
                if (billingDetails.containsKey(api.getApplication())) {
                    billingDetails.get(api.getApplication()).add(new BillingSubscription(api));
                } else {
                    Set<BillingSubscription> set = new HashSet<BillingSubscription>();
                    set.add(new BillingSubscription(api));
                    billingDetails.put(api.getApplication(), set);
                }
            }
        } else {
            log.info("::: Subscribed APIS null");
        }

        return billingDetails;
    }

    public static APIKey getAppKey(Application app, String keyType) {
        List<APIKey> apiKeys = app.getKeys();
        return getKeyOfType(apiKeys, keyType);
    }

    public static APIKey getKeyOfType(List<APIKey> apiKeys, String keyType) {
        for (APIKey key : apiKeys) {
            if (keyType.equals(key.getType())) {
                return key;
            }
        }
        return null;
    }

    public static void applyChargingPlan(Map<Application, Set<BillingSubscription>> billingSubs,
            Map<String, Float> chargingPlan, Map<String, String> currencyType) throws APIMgtUsageQueryServiceClientException, APIManagementException {

        Set<Map.Entry<Application, Set<BillingSubscription>>> entries = billingSubs.entrySet();

        for (Map.Entry<Application, Set<BillingSubscription>> entry : entries) {
            Set<BillingSubscription> subscriptions = entry.getValue();
            for (BillingSubscription subscription : subscriptions) {

                String appName = subscription.getApplication().getName();

                String subscriber = subscription.getSubscriber().getName();
                String apiName = subscription.getApiId().getApiName();

                log.info(apiName);

                int noOfSubscribers = 0;

                String subscriptionName = subscription.getTier().getName();


                if (apiName.equals("payment")) {

                    Set<PaymentRequestDTO> paymentRequestSet = BillingDataAccessObject.getPaymentAmounts(subscriber, appName, subscription.getYear(), subscription.getMonth());

                    applyChargesForPaymentApi(subscription, paymentRequestSet);

//                                    int totalAmount = BillingDataAccessObject.getPaymentAmounts(subscriber, appName);
//                                    subscription.setPrice((totalAmount * 80)/100);
                } else {
                    //Unlimited Tier
                    if (subscription.getTier().getName().equals(ChargingPlanType.UNLIMITED.getValue())) {
                        //subscription.setPrice(subscription.getCount() *
                        // chargingPlan.get(ChargingPlanType.UNLIMITED.getValue()));
                        float maxCount = BillingHostObject.getTierMaximumCount().get(ChargingPlanType.UNLIMITED.getValue());
                        float unitTime = BillingHostObject.getTierUnitTime().get(ChargingPlanType.UNLIMITED.getValue());
                        subscription.setPrice(chargingPlan.get(ChargingPlanType.UNLIMITED.getValue()));
                        subscription.setCurrencyType(currencyType.get(ChargingPlanType.UNLIMITED.getValue()));

                        subscription.setPrice(chargingPlan.get(ChargingPlanType.UNLIMITED
                                .getValue()));
                        applyTaxForBlockCharging(subscription);
                    } //Subscription Tier
                    else if (subscription.getTier().getName().equals(ChargingPlanType.SUBSCRIPTION.getValue())) {
                        noOfSubscribers = BillingDataAccessObject.getNoOfSubscribers(subscriber, appName, apiName);

                        subscription.setPrice(chargingPlan.get(ChargingPlanType.SUBSCRIPTION.getValue()) * noOfSubscribers);
                        applyTaxForBlockCharging(subscription);
                    } //Gold Tier
                    else if (subscription.getTier().getName()
                            .equals(ChargingPlanType.GOLD.getValue())) {

                        float maxCount = BillingHostObject.getTierMaximumCount().get(ChargingPlanType.GOLD.getValue());
                        int actualCount = (int) maxCount;

                        if (subscription.getCount() > actualCount) {
                            int excess = subscription.getCount() - actualCount;

                            float totalCharge = (10 * excess) + chargingPlan.get(ChargingPlanType.GOLD.getValue());
                            subscription.setPrice(totalCharge);
                        } else {
                            subscription.setPrice(chargingPlan.get(ChargingPlanType.GOLD.getValue()));
                            subscription.setCurrencyType(currencyType.get(ChargingPlanType.GOLD.getValue()));
                        }
                        applyTaxForBlockCharging(subscription);

                    } //Silver Tier
                    else if (subscription.getTier().getName()
                            .equals(ChargingPlanType.SILVER.getValue())) {
                        subscription.setPrice(subscription.getCount()
                                * chargingPlan.get(ChargingPlanType.SILVER.getValue()));
                        applyTaxForBlockCharging(subscription);

                    } //Bronze Tier
                    else if (subscription.getTier().getName()
                            .equals(ChargingPlanType.BRONZE.getValue())) {
                        subscription.setPrice(subscription.getCount()
                                * chargingPlan.get(ChargingPlanType.BRONZE.getValue()));
                        applyTaxForBlockCharging(subscription);
                    } //Premium Tier
                    else if (subscription.getTier().getName()
                            .equals(ChargingPlanType.PREMIUM.getValue())) {
                        subscription.setPrice(chargingPlan.get(ChargingPlanType.PREMIUM.getValue()));
                        applyTaxForBlockCharging(subscription);
                    } //Requestbased Tier
                    else if (subscription.getTier().getName().equals(ChargingPlanType.REQUEST_BASED
                            .getValue())) {

                        applyChargesWithTax(subscription, chargingPlan.get(ChargingPlanType.REQUEST_BASED.getValue()));
                    }
                }

            }
        }
    }

    private static void applyChargesForPaymentApi(BillingSubscription subscription, Set<PaymentRequestDTO> paymentRequestSet) throws APIManagementException, APIMgtUsageQueryServiceClientException {

        int applicationId = subscription.getApplication().getId();
        int apiId = BillingDataAccessObject.getApiId(subscription.getApiId());

        List<Tax> taxList = TaxDataAccessObject.getTaxesForSubscription(applicationId, apiId);
        BigDecimal totalCharge = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        for (PaymentRequestDTO paymentRequest : paymentRequestSet) {
            totalCharge = totalCharge.add(paymentRequest.getAmount());
            Date date = paymentRequest.getDate();
            for (Tax tax : taxList) {
                //check if the date of payment request falls between this tax validity period
                if (!date.before(tax.getEffective_from()) && !date.after(tax.getEffective_to())) {
                    // totalTax += taxFraction x paymentAmount
                    totalTax = totalTax.add(tax.getValue().multiply(paymentRequest.getAmount()));
                }
            }
        }

        // only 80% is charged
        subscription.setPrice((totalCharge.floatValue() * 80) / 100);
        subscription.setTaxValue(totalTax.multiply(new BigDecimal("0.80")));
    }

    private static void applyChargesWithTax(BillingSubscription subscription,
            float chargeRate) throws APIManagementException,
            APIMgtUsageQueryServiceClientException {
        String month = subscription.getMonth();
        String year = subscription.getYear();

        Application application = subscription.getApplication();
        if (application == null) {
            throw new APIManagementException("no key generated for this api");
        }
        APIKey prodKey = getAppKey(application, APIConstants.API_KEY_TYPE_PRODUCTION);

        Set<APIRequestDTO> requestTimes = new HashSet<APIRequestDTO>();
        if (prodKey != null) {
            requestTimes = TaxDataAccessObject.getAPIRequestTimesForSubscription(subscription.getApiId().getApiName(), subscription.getApiId().getVersion(), prodKey.getConsumerKey(), Short.valueOf(year),
                    Short.valueOf(month), subscription.getSubscriber().getName());
        }

        int applicationId = subscription.getApplication().getId();
        int apiId = BillingDataAccessObject.getApiId(subscription.getApiId());

        List<Tax> taxList = TaxDataAccessObject.getTaxesForSubscription(applicationId, apiId);
        float totalCharge = 0f;
        BigDecimal totalTax = BigDecimal.ZERO;

        for (APIRequestDTO req : requestTimes) {
            float charge = chargeRate * req.getRequestCount();
            totalCharge += charge;

            Date date = req.getDate();
            for (Tax tax : taxList) {
                //check if the date of payment request falls between this tax validity period
                if (!date.before(tax.getEffective_from()) && !date.after(tax.getEffective_to())) {
                    // totalTax += taxFraction x charge
                    totalTax = totalTax.add(tax.getValue().multiply(BigDecimal.valueOf(charge)));
                }
            }
        }

        subscription.setPrice(totalCharge);
        subscription.setTaxValue(totalTax);

    }

    private static void applyTaxForBlockCharging(BillingSubscription subscription) throws APIManagementException,
            APIMgtUsageQueryServiceClientException {
        String month = subscription.getMonth();
        String year = subscription.getYear();

        int applicationId = subscription.getApplication().getId();
        int apiId = BillingDataAccessObject.getApiId(subscription.getApiId());

        List<Tax> taxList = TaxDataAccessObject.getTaxesForSubscription(applicationId, apiId);

        BigDecimal totalTax = BigDecimal.ZERO;
        Date billingDate = Date.valueOf(year + "-" + month + "-01");    //start of the month

        for (Tax tax : taxList) {
            //select the taxes applicable at the billing date
            if (!billingDate.before(tax.getEffective_from()) && !billingDate.after(tax.getEffective_to())) {
                // totalTax += taxFraction x charge
                totalTax = totalTax.add(tax.getValue().multiply(BigDecimal.valueOf(subscription.getPrice())));
            }
        }

        subscription.setTaxValue(totalTax);
    }

    public static Map<Application, Set<BillingSubscription>> chargeSubscriberForMonth(String subscriber,
            String year,
            String month,
            Map<String, Float> chargingPlan, Map<String, String> currencyType)
            throws APIManagementException,
            APIMgtUsageQueryServiceClientException,
            SQLException {
        Map<Application, Set<BillingSubscription>> billingSubs =
                getBillingSubscriptionsForUser(subscriber);
        populateAPICounts(billingSubs, year, month, subscriber);
        applyChargingPlan(billingSubs, chargingPlan, currencyType);
//                BillingDataAccessObject.printSouthboundTraffic();

        return billingSubs;
    }

    public static Map<String, String> getResponseTimesForSubscriber(String username) throws APIManagementException,
            APIMgtUsageQueryServiceClientException {
        log.debug("Starting getResponseTimesForSubscriber funtion with name username " + username);
        Subscriber subscriber = new Subscriber(username);
        Map<String, String> responseTimes = new HashMap<String, String>();
        ApiMgtDAO apiMgtDAO = new ApiMgtDAO();
        Set<SubscribedAPI> apis = apiMgtDAO.getSubscribedAPIs(subscriber);
        log.debug("apis count, " + apis.size());
        String api_version = null;
        for (SubscribedAPI api : apis) {
            log.debug("api_name " + api.getApiId().getApiName());
            log.debug("api_version " + api.getApiId().getVersion());
            api_version = api.getApiId().getApiName() + ":v" + api.getApiId().getVersion();
            log.debug("api_version " + api_version);
            String ResponseTimeForAPI = BillingDataAccessObject.getResponseTimeForAPI(api_version);
            log.debug("ResponseTimeForAPI: " + ResponseTimeForAPI);
            if (ResponseTimeForAPI != null) {
                log.debug("ResponseTimeForAPI was updated ");
                responseTimes.put(api_version, BillingDataAccessObject.getResponseTimeForAPI(api_version));
            }
        }

        return responseTimes;
    }

    public static Map<String, List<APIResponseDTO>> getAllResponseTimes(String username, String application,
            String fromDate, String toDate)
            throws APIManagementException, APIMgtUsageQueryServiceClientException {
        log.debug("Starting getAllResponseTimes function with username " + username + " for app " + application
                + " from " + fromDate + " to " + toDate);
        Map<String, List<APIResponseDTO>> responseTimes = new HashMap<String, List<APIResponseDTO>>();
        ApiMgtDAO apiMgtDAO = new ApiMgtDAO();

        if (username.equals(ALL_SUBSCRIBERS_KEYWORD)) {
            List<API> allAPIs = APIManagerFactory.getInstance().getAPIConsumer().getAllAPIs();
            for (API api : allAPIs) {
                String nameVersion = api.getId().getApiName() + ":v" + api.getId().getVersion();
                List<APIResponseDTO> responseDTOs = BillingDataAccessObject.getAllResponseTimesForAPI(nameVersion, fromDate, toDate);
                responseTimes.put(nameVersion, responseDTOs);
            }
        } else if (application.equals(ALL_APPLICATIONS_KEYWORD)) {
            Subscriber subscriber = new Subscriber(username);
            Set<SubscribedAPI> subscribedAPIs = apiMgtDAO.getSubscribedAPIs(subscriber);
            for (SubscribedAPI api : subscribedAPIs) {
                String nameVersion = api.getApiId().getApiName() + ":v" + api.getApiId().getVersion();
                List<APIResponseDTO> responseDTOs = BillingDataAccessObject.getAllResponseTimesForAPI(nameVersion, fromDate, toDate);
                responseTimes.put(nameVersion, responseDTOs);
            }
        } else {
            Subscriber subscriber = new Subscriber(username);
            Set<SubscribedAPI> subscribedAPIs = apiMgtDAO.getSubscribedAPIs(subscriber, application);
            for (SubscribedAPI api : subscribedAPIs) {
                String nameVersion = api.getApiId().getApiName() + ":v" + api.getApiId().getVersion();
                List<APIResponseDTO> responseDTOs = BillingDataAccessObject.getAllResponseTimesForAPI(nameVersion, fromDate, toDate);
                responseTimes.put(nameVersion, responseDTOs);
            }
        }
        //TODO: filter with provider or context for APIs

        return responseTimes;
    }

    public static FileWriter getReportLocation(String subscriber, String period) throws IOException {
        String year = null;
        String month = null;
        if (period != null) {
            String[] periodArray = period.split("-");
            year = periodArray[0];
            month = periodArray[1];
        }
        String carbonHome = System.getProperty("carbon.home");
        String fileName = subscriber + "-" + period + ".csv";
        File reports = new File(carbonHome + "/reports/");

        if (!reports.exists()) {
            reports.mkdirs();
            reports = new File(reports.getAbsoluteFile() + "/" + fileName);
            reports.createNewFile();
        } else {
            reports = new File(reports.getAbsoluteFile() + "/" + fileName);
            if (!reports.exists()) {
                reports.createNewFile();
            }
        }
        return new FileWriter(reports.getAbsolutePath());
    }

    private static File getCSVFile(String subscriber, String period) throws IOException {

        String carbonHome = System.getProperty("carbon.home");
        String fileName = subscriber + "-" + period + ".csv";
        File reports = new File(carbonHome + "/reports/");

        if (!reports.exists()) {
            reports.mkdirs();
            reports = new File(reports.getAbsoluteFile() + "/" + fileName);
            reports.createNewFile();
        } else {
            reports = new File(reports.getAbsoluteFile() + "/" + fileName);
            if (!reports.exists()) {
                reports.createNewFile();
            }
        }
        return reports;
    }

    public static NativeArray generateReportofSubscriber(boolean isPersistReport,
            String subscriberName, String period,
            Map<String, Float> chargingPlan, Map<String, String> currencyType)
            throws IOException,
            SQLException,
            APIMgtUsageQueryServiceClientException,
            APIManagementException {

        String year = null;
        String month = null;
        if (period != null) {
            String[] periodArray = period.split("-");
            year = periodArray[0];
            month = periodArray[1];
        }
        FileWriter fileWriter = getReportLocation(subscriberName, period);

        if (isPersistReport) {
            fileWriter.write(String.format("%s, %s, %s, %s, %s, %s,%s, %s %n", "Subscriber",
                    "Application", "API", "Version", "Plan", "Count",
                    "Usage Charge", "Tax"));
        }

        NativeArray subscribers = new NativeArray(0);

        if (subscriberName.equals(ALL_SUBSCRIBERS_KEYWORD)) {
            List<String> subscriberNames = HostObjectUtils.getAllSubscribers();

            for (String sub : subscriberNames) {
                Map<Application, Set<BillingSubscription>> billingSubs =
                        HostObjectUtils.chargeSubscriberForMonth(sub,
                        year,
                        month,
                        chargingPlan,
                        currencyType);

                NativeObject subscriber = new NativeObject();

                NativeArray totCurrArray = new NativeArray(0);
                NativeArray taxCurrArray = new NativeArray(0);
                NativeArray grandCurrArray = new NativeArray(0);
                subscriber.put("subscriber", subscriber, sub);
                NativeArray applications = new NativeArray(0);

                //Priyan CODE
                Map<String, Float> totalMap = new HashMap<String, Float>();
                Map<String, Float> taxMap = new HashMap<String, Float>();
                Map<String, Float> grandTotalMap = new HashMap<String, Float>();
                Map<String, Float> grandTaxMap = new HashMap<String, Float>();
                Set<String> currencySet = new HashSet<String>();


                if (isPersistReport) {
                    fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", sub, "", "",
                            "", "", "", "", ""));
                }

                for (Map.Entry<Application, Set<BillingSubscription>> billingEntries : billingSubs.entrySet()) {

                    NativeObject application = new NativeObject();
                    String applicationName = "";

                    NativeArray subscriptionAPIs = new NativeArray(0);

                    int a = 0;
                    for (BillingSubscription billingSubscription : billingEntries.getValue()) {

                        NativeObject subscriptionAPI = new NativeObject();
                        subscriptionAPI.put("subscriptionapi", subscriptionAPI,
                                billingSubscription.getApiId().getApiName());
                        subscriptionAPI.put("subscriptionapiversion", subscriptionAPI,
                                billingSubscription.getApiId().getVersion());
                        subscriptionAPI.put("charginplan", subscriptionAPI,
                                billingSubscription.getTier().getName());
                        subscriptionAPI.put("count", subscriptionAPI,
                                billingSubscription.getCount());
                        subscriptionAPI.put("price", subscriptionAPI,
                                billingSubscription.getPrice());
                        subscriptionAPI.put("tax", subscriptionAPI, billingSubscription.getTaxValue());

                        applicationName = billingEntries.getKey().getName();
                        subscriptionAPI.put("currencytype", subscriptionAPI, billingSubscription.getCurrencyType());
                        subscriptionAPIs.put(subscriptionAPIs.size(), subscriptionAPIs, subscriptionAPI);


                        //Priyan CODE START		- USAGE CHARGE			TAX

                        String currencyTypeForReference = billingSubscription.getCurrencyType();
                        if (currencySet.contains(currencyTypeForReference)) {
                            Float pricePerCurrency = totalMap.get(currencyTypeForReference);
                            pricePerCurrency += billingSubscription.getPrice();

                            Float taxPerCurrency = taxMap.get(currencyTypeForReference);
                            taxPerCurrency += billingSubscription.getTaxValue().floatValue();

                            taxMap.put(currencyTypeForReference, taxPerCurrency);
                            totalMap.put(currencyTypeForReference, pricePerCurrency);
                        } else {
                            currencySet.add(currencyTypeForReference);
                            totalMap.put(currencyTypeForReference, billingSubscription.getPrice());
                            taxMap.put(currencyTypeForReference, billingSubscription.getTaxValue().floatValue());
                        }

                        grandTotalMap.putAll(totalMap);
                        grandTaxMap.putAll(taxMap);

                        Set<String> grandTotalSet = new HashSet<String>();
                        for (Map.Entry<String, Float> entry : grandTotalMap.entrySet()) {
                            String currencyTypeForRefer = entry.getKey();
                            if (grandTotalSet.contains(currencyTypeForRefer)) {
                                Float pricePerCurrency = grandTaxMap.get(currencyTypeForRefer);
                                pricePerCurrency += entry.getValue();
                                grandTaxMap.put(currencyTypeForRefer, pricePerCurrency);
                            } else {
                                grandTotalSet.add(currencyTypeForRefer);
                                Float remainBalance = grandTaxMap.get(currencyTypeForRefer);
                                grandTaxMap.put(currencyTypeForRefer, remainBalance + entry.getValue());

                            }
                        }

                        //Priyan CODE END									
                        if (a == 0 && isPersistReport) {
                            fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", "",
                                    applicationName, "", "", "", "", "", ""));
                        }

                        if (isPersistReport) {
                            fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", "", "",
                                    billingSubscription.getApiId()
                                    .getApiName(),
                                    billingSubscription.getApiId()
                                    .getVersion(),
                                    billingSubscription.getTier().getName(),
                                    billingSubscription.getCount(),
                                    billingSubscription.getPrice(),
                                    billingSubscription.getTaxValue()));
                        }

                        a++;

                    }
                    application.put("applicationname", application, applicationName);
                    application.put("subscriptions", application, subscriptionAPIs);
                    applications.put(applications.size(), applications, application);
                }

                //Priyan CODE START			

                for (Map.Entry<String, Float> entry : totalMap.entrySet()) {
                    NativeObject totCurrObj = new NativeObject();
                    totCurrObj.put("totCurrObjects", totCurrObj, entry.getKey() + " " + entry.getValue());
                    totCurrArray.put(totCurrArray.size(), totCurrArray, totCurrObj);
                }
                subscriber.put("currencytotalmap", subscriber, totCurrArray);

                for (Map.Entry<String, Float> entry : taxMap.entrySet()) {
                    NativeObject taxCurrObj = new NativeObject();
                    taxCurrObj.put("taxCurrObjects", taxCurrObj, entry.getKey() + " " + entry.getValue());
                    taxCurrArray.put(taxCurrArray.size(), taxCurrArray, taxCurrObj);
                }
                subscriber.put("currencytaxmap", subscriber, taxCurrArray);

                for (Map.Entry<String, Float> entry : grandTaxMap.entrySet()) {
                    NativeObject grandCurrObj = new NativeObject();
                    grandCurrObj.put("grandCurrObjects", grandCurrObj, entry.getKey() + " " + entry.getValue());
                    grandCurrArray.put(grandCurrArray.size(), grandCurrArray, grandCurrObj);
                }
                subscriber.put("currencygrandmap", subscriber, grandCurrArray);



                //Priyan CODE END

                subscriber.put("applications", subscriber, applications);
                subscribers.put(subscribers.size(), subscribers, subscriber);

            }
        } else {

            NativeObject subscriber = new NativeObject();
            subscriber.put("subscriber", subscriber, subscriberName);
            NativeArray applications = new NativeArray(0);




            NativeArray totCurrArray = new NativeArray(0);
            NativeArray taxCurrArray = new NativeArray(0);
            NativeArray grandCurrArray = new NativeArray(0);

            //Priyan CODE
            Map<String, Float> totalMap = new HashMap<String, Float>();
            Map<String, Float> taxMap = new HashMap<String, Float>();
            Map<String, Float> grandTotalMap = new HashMap<String, Float>();
            Map<String, Float> grandTaxMap = new HashMap<String, Float>();
            Set<String> currencySet = new HashSet<String>();


            Map<Application, Set<BillingSubscription>> billingSubs =
                    HostObjectUtils.chargeSubscriberForMonth(subscriberName,
                    year,
                    month,
                    chargingPlan,
                    currencyType);
            if (isPersistReport) {
                fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", subscriberName, "",
                        "", "", "", "", "", ""));
            }

            for (Map.Entry<Application, Set<BillingSubscription>> billingEntries : billingSubs.entrySet()) {

                NativeObject application = new NativeObject();
                String applicationName = "";

                NativeArray subscriptionAPIs = new NativeArray(0);

                fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", "",
                        billingEntries.getKey().getName(), "", "", "", "", "", ""));

                for (BillingSubscription billingSubscription : billingEntries.getValue()) {
                    if (isPersistReport) {
                        fileWriter.write(String.format("%s, %s, %s, %s, %s, %s, %s, %s %n", "", "",
                                billingSubscription.getApiId().getApiName(),
                                billingSubscription.getApiId().getVersion(),
                                billingSubscription.getTier().getName(),
                                billingSubscription.getCount(),
                                billingSubscription.getPrice(),
                                billingSubscription.getTaxValue()));
                    }

                    NativeObject subscriptionAPI = new NativeObject();
                    subscriptionAPI.put("subscriptionapi", subscriptionAPI,
                            billingSubscription.getApiId().getApiName());
                    subscriptionAPI.put("subscriptionapiversion", subscriptionAPI,
                            billingSubscription.getApiId().getVersion());
                    subscriptionAPI.put("charginplan", subscriptionAPI,
                            billingSubscription.getTier().getName());
                    subscriptionAPI.put("count", subscriptionAPI, billingSubscription.getCount());
                    subscriptionAPI.put("price", subscriptionAPI,
                            billingSubscription.getPrice());
                    subscriptionAPI.put("tax", subscriptionAPI, billingSubscription.getTaxValue());
                    subscriptionAPI.put("currencytype", subscriptionAPI, billingSubscription.getCurrencyType());
                    applicationName = billingEntries.getKey().getName();

                    subscriptionAPIs.put(subscriptionAPIs.size(), subscriptionAPIs, subscriptionAPI);



                    //Priyan CODE START		- USAGE CHARGE			TAX

                    String currencyTypeForReference = billingSubscription.getCurrencyType();
                    if (currencySet.contains(currencyTypeForReference)) {
                        Float pricePerCurrency = totalMap.get(currencyTypeForReference);
                        pricePerCurrency += billingSubscription.getPrice();

                        Float taxPerCurrency = taxMap.get(currencyTypeForReference);
                        taxPerCurrency += billingSubscription.getTaxValue().floatValue();

                        taxMap.put(currencyTypeForReference, taxPerCurrency);
                        totalMap.put(currencyTypeForReference, pricePerCurrency);
                    } else {
                        currencySet.add(currencyTypeForReference);
                        totalMap.put(currencyTypeForReference, billingSubscription.getPrice());
                        taxMap.put(currencyTypeForReference, billingSubscription.getTaxValue().floatValue());
                    }

                    grandTotalMap.putAll(totalMap);
                    grandTaxMap.putAll(taxMap);

                    Set<String> grandTotalSet = new HashSet<String>();
                    for (Map.Entry<String, Float> entry : grandTotalMap.entrySet()) {
                        String currencyTypeForRefer = entry.getKey();
                        if (grandTotalSet.contains(currencyTypeForRefer)) {
                            Float pricePerCurrency = grandTaxMap.get(currencyTypeForRefer);
                            pricePerCurrency += entry.getValue();
                            grandTaxMap.put(currencyTypeForRefer, pricePerCurrency);
                        } else {
                            grandTotalSet.add(currencyTypeForRefer);
                            Float remainBalance = grandTaxMap.get(currencyTypeForRefer);
                            grandTaxMap.put(currencyTypeForRefer, remainBalance + entry.getValue());

                        }

                    }

                    //Priyan CODE END			




                }

                application.put("applicationname", application, applicationName);
                application.put("subscriptions", application, subscriptionAPIs);

                applications.put(applications.size(), applications, application);

            }



            //Priyan CODE START			

            for (Map.Entry<String, Float> entry : totalMap.entrySet()) {
                NativeObject totCurrObj = new NativeObject();
                totCurrObj.put("totCurrObjects", totCurrObj, entry.getKey() + " " + entry.getValue());
                totCurrArray.put(totCurrArray.size(), totCurrArray, totCurrObj);
            }
            subscriber.put("currencytotalmap", subscriber, totCurrArray);

            for (Map.Entry<String, Float> entry : taxMap.entrySet()) {
                NativeObject taxCurrObj = new NativeObject();
                taxCurrObj.put("taxCurrObjects", taxCurrObj, entry.getKey() + " " + entry.getValue());
                taxCurrArray.put(taxCurrArray.size(), taxCurrArray, taxCurrObj);
            }
            subscriber.put("currencytaxmap", subscriber, taxCurrArray);

            for (Map.Entry<String, Float> entry : grandTaxMap.entrySet()) {
                NativeObject grandCurrObj = new NativeObject();
                grandCurrObj.put("grandCurrObjects", grandCurrObj, entry.getKey() + " " + entry.getValue());
                grandCurrArray.put(grandCurrArray.size(), grandCurrArray, grandCurrObj);
            }
            subscriber.put("currencygrandmap", subscriber, grandCurrArray);



            //Priyan CODE END			


            subscriber.put("applications", subscriber, applications);
            subscribers.put(subscribers.size(), subscribers, subscriber);

        }

        fileWriter.close();

        return subscribers;
    }

    public static boolean isUserAdminUser(String username) {
        String adminUserName =
                HostObjectComponent.getRealmService()
                .getBootstrapRealmConfiguration()
                .getAdminUserName();
        log.info("::: AdminUser Name ::: " + adminUserName);
        return adminUserName.equals(username);
    }

    public static List<String> getAllSubscribers() throws SQLException,
            APIMgtUsageQueryServiceClientException {
        List<String> subscriptions = BillingDataAccessObject.getAllSubscriptions();
        Collections.sort(subscriptions, String.CASE_INSENSITIVE_ORDER);
        return subscriptions;
    }

    public static void populateAPICounts(Map<Application, Set<BillingSubscription>> billingSubs,
            String year, String month, String subscriber)
            throws APIManagementException,
            APIMgtUsageQueryServiceClientException {
        Set<Map.Entry<Application, Set<BillingSubscription>>> entries = billingSubs.entrySet();
        for (Map.Entry<Application, Set<BillingSubscription>> mapEntry : entries) {
            Application application = mapEntry.getKey();
            if (application == null) {
                throw new APIManagementException("no key genenarted for this api");
            }
            APIKey prodKey = getAppKey(application, APIConstants.API_KEY_TYPE_PRODUCTION);
            if (month != null && month.length() == 1) {
                month = "0" + month;
            }
            Map<String, Integer> usageCounts = new HashMap<String, Integer>();
            if (prodKey != null) {
                usageCounts = BillingDataAccessObject.getAPICountsForApplication(prodKey.getConsumerKey(), year,
                        month, subscriber);
            }
            for (BillingSubscription bill : mapEntry.getValue()) {
                String apiKey = bill.getApiId().getApiName() + ":v" + bill.getApiId().getVersion();
                if (usageCounts.containsKey(apiKey)) {
                    bill.setCount(usageCounts.get(apiKey));
                }
                bill.setYear(year);
                bill.setMonth(month);
            }
        }

    }

//    public static void applyTaxValues(Map<Application, Set<BillingSubscription>> billingSubs, String year,
//                                      String month, String subscriber) throws APIManagementException,
//            APIMgtUsageQueryServiceClientException {
//        Set<Map.Entry<Application, Set<BillingSubscription>>> entries = billingSubs.entrySet();
//
//        for (Map.Entry<Application, Set<BillingSubscription>> mapEntry : entries) {
//            Application application = mapEntry.getKey();
//            if (application == null) {
//                throw new APIManagementException("no key generated for this api");
//            }
//            APIKey prodKey = getAppKey(application, APIConstants.API_KEY_TYPE_PRODUCTION);
//            if (month != null && month.length() == 1) {
//                month = "0" + month;
//            }
//            Map<String, List<APIRequestDTO>> requestTimesForApp = TaxDataAccessObject.getAPIRequestTimesForApplication
//                    (prodKey.getConsumerKey(), Short.valueOf(year), Short.valueOf(month), subscriber);
//            for (BillingSubscription bill : mapEntry.getValue()) {
//                int subscriptionId = BillingDataAccessObject.getSubscriptionIdForApplicationAPI
//                        (application.getId(), bill.getApiId());
//                List<Tax> taxList = TaxDataAccessObject.getTaxesForSubscription(subscriptionId);
//                   String apiKey = bill.getApiId().getApiName() + ":v" + bill.getApiId().getVersion();
//				if (requestTimes.containsKey(apiKey)) {
//                      requestTimes.get(apiKey)
//				}
//                bill.getApiId().getProviderName()
//            }
//        }
//    }
//
//    private static float calculateTaxCharge(BillingSubscription bill, List<APIRequestDTO> requestTimes, List<Tax> taxList){
//            float taxCharge = 0f;
//          for (APIRequestDTO request : requestTimes){
//              for (Tax tax : taxList){
//                  taxCharge += bill.get //divide totalPrice/no.ofCalls = unitPrice??
//              }
//          }
//    }
    /**
     *
     * Reading report file from file system
     *
     * @param suscriber
     * @param period
     * @return
     */
    public static String getReport(String suscriber, String period) {
        String fileContent = "";
        BufferedReader bufferedReader = null;
        try {
            File file = getCSVFile(suscriber, period);
            bufferedReader = new BufferedReader(new FileReader(file));

            StringBuilder fileAppender = new StringBuilder();

            int c = -1;

            while ((c = bufferedReader.read()) != -1) {
                char ch = (char) c;
                fileAppender.append(ch);
            }

            fileContent = fileAppender.toString();

        } catch (IOException e) {
            log.error("report file reading error : " + e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                log.error("Buuffered Reader closing error : " + e.getMessage());
            }
        }
        return fileContent;
    }

}
