/*
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.dialog.custom.hostobjects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.*;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Tier;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.usage.client.dto.APIVersionUserUsageDTO;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;

public class BillingHostObject extends ScriptableObject {

	private static final Log log = LogFactory.getLog(BillingHostObject.class);
	private String hostobjectName = "DialogBilling";
	private APIConsumer apiConsumer;
	private static Map<String, Float> tierPricing = new HashMap<String, Float>();
	private static Map<String, Integer> tierMaximumCount = new HashMap<String, Integer>();
	private static Map<String, Integer> tierUnitTime = new HashMap<String, Integer>();
	private static Map<String, String> currencyType = new HashMap<String, String>();
    public static Map<String, String> getCurrencyType() {
			return currencyType;
	}

	public static void setCurrencyType(Map<String, String> currencyType) {
		BillingHostObject.currencyType = currencyType;
	}
	public static Map<String, Float> getTierPricing() {
		return tierPricing;
	}

	public static Map<String, Integer> getTierMaximumCount() {
		return tierMaximumCount;
	}

	public static Map<String, Integer> getTierUnitTime() {
		return tierUnitTime;
	}

	public String getUsername() {
		return username;
	}

	private String username;

	@Override
	public String getClassName() {
		return hostobjectName;
	}

	public BillingHostObject(String username) throws APIManagementException {
		log.info("::: Initialized HostObject for : " + username);
		if (username != null) {
			this.username = username;
			apiConsumer = APIManagerFactory.getInstance().getAPIConsumer(username);
		} else {
			apiConsumer = APIManagerFactory.getInstance().getAPIConsumer();
		}
	}

	public BillingHostObject() {
		log.info("::: Initialized HostObject ");
	}

	public APIConsumer getApiConsumer() {
		return apiConsumer;
	}

	private static APIConsumer getAPIConsumer(Scriptable thisObj) {
		return ((BillingHostObject) thisObj).getApiConsumer();
	}

	public static String jsFunction_getReportFileContent(Context cx, Scriptable thisObj,
                                                         Object[] args, Function funObj)
                                                                 throws APIManagementException {
		if (args == null || args.length == 0) {
			handleException("Invalid number of parameters.");
		}

		String subscriberName = (String) args[0];
		String period = (String) args[1];
		
		generateReport(subscriberName,period);
		
		String fileContent = HostObjectUtils.getReport(subscriberName, period);
		return fileContent;
	}
	
	public static NativeArray jsFunction_getAPIUsageforSubscriber(Context cx, Scriptable thisObj,
	                                                              Object[] args, Function funObj)
	                                                                                             throws APIManagementException {
		List<APIVersionUserUsageDTO> list = null;
		if (args == null || args.length == 0) {
			handleException("Invalid number of parameters.");
		}
		NativeArray myn = new NativeArray(0);
		if (!HostObjectUtils.checkDataPublishingEnabled()) {
			return myn;
		}
		String subscriberName = (String) args[0];
		String period = (String) args[1];

		NativeArray ret = generateReport(subscriberName,period);
		
		return ret;

	}
	
	private static NativeArray generateReport(String subscriberName,String period) throws APIManagementException{
		
		createTierPricingMap();

		NativeArray ret = null;
		try {
			ret =
			      HostObjectUtils.generateReportofSubscriber(true, subscriberName, period,
			                                                 tierPricing,currencyType);
		} catch (APIMgtUsageQueryServiceClientException e) {
			handleException("Error occurred while executing the dummyQuery.", e);
		} catch (SQLException e) {
			handleException("Error occurred while retrieving data.", e);
		} catch (IOException e) {
			handleException("Error occurred while generating report.", e);
		}
		return ret;
	}

    public static NativeArray jsFunction_getResponseTimeData(Context cx, Scriptable thisObj,
                                                                  Object[] args, Function funObj)
            throws APIManagementException {
        String subscriberName = (String) args[0];
        NativeArray nativeArray = null;
        log.debug("Starting getResponseTimeData funtion with "+subscriberName);
        try {
            Map<String,String> responseTimes = HostObjectUtils.getResponseTimesForSubscriber(subscriberName);
            short i = 0;
            log.debug(subscriberName+", responseTimes "+responseTimes);
            if(responseTimes != null){
                nativeArray = new NativeArray(0);
            }
            for(Map.Entry<String,String> timeEntry : responseTimes.entrySet()) {
                NativeObject row = new NativeObject();
                log.debug(subscriberName+", timeEntry key "+timeEntry.getKey());
                log.debug(subscriberName+", timeEntry value"+timeEntry.getValue());
                row.put("apiName",row,timeEntry.getKey().toString());
                row.put("responseTime",row,timeEntry.getValue().toString());
                nativeArray.put(i,nativeArray,row);
                i++;
            }

        } catch (Exception e) {
        	log.error("Error occured getResponseTimeData ");        	
        	log.error(e.getStackTrace());
            handleException("Error occurred while populating Response Time graph.", e);
        }
        log.info("End of getResponseTimeData");
        return nativeArray;
    }

    public static NativeArray jsFunction_getAllResponseTimes(Context cx, Scriptable thisObj,
                                                             Object[] args, Function funObj)
            throws APIManagementException {
        String subscriberName = (String) args[0];
        String appName = (String) args[1];
        String fromDate = (String) args[2];
        String toDate = (String) args[3];

        NativeArray apis = new NativeArray(0);
        log.debug("Starting getAllResponseTimes function for user- " + subscriberName + " app- " + appName);
        try {
            Map<String, List<APIResponseDTO>> responseMap = HostObjectUtils.getAllResponseTimes(subscriberName,
                    appName, fromDate, toDate);
            short i = 0;
            log.debug(subscriberName + ", responseMap " + responseMap);

            for (Map.Entry<String, List<APIResponseDTO>> timeEntry : responseMap.entrySet()) {

                NativeObject api = new NativeObject();
                api.put("apiName", api, timeEntry.getKey());

                NativeArray responseTimes = new NativeArray(0);
                for (APIResponseDTO dto : timeEntry.getValue()) {
                    NativeObject responseData = new NativeObject();
                    responseData.put("serviceTime", responseData, dto.getServiceTime());
                    responseData.put("responseCount", responseData, dto.getResponseCount());
                    responseData.put("date", responseData, dto.getDate().toString());
                    responseTimes.put(responseTimes.size(), responseTimes, responseData);
                }
                api.put("responseData", api, responseTimes);
                apis.put(i, apis, api);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured getAllResponseTimes ");
            log.error(e.getStackTrace());
            handleException("Error occurred while populating Response Time graph.", e);
        }

        return apis;
    }

    public static NativeArray jsFunction_getAllSubscribers(Context cx, Scriptable thisObj, Object[] args,
                                                           Function funObj) throws APIManagementException {
        NativeArray nativeArray = new NativeArray(0);

        try {
            List<String> subscribers = HostObjectUtils.getAllSubscribers();

            if (subscribers != null) {
                int i = 0;
                for (String subscriber : subscribers) {
                    nativeArray.put(i, nativeArray, subscriber);
                    i++;
                }
            }

        } catch (Exception e) {
            log.error("Error occurred getAllSubscribers");
            log.error(e.getStackTrace());
            handleException("Error occurred while getting the subscribers", e);
        }
        return nativeArray;
    }

	private static int getMaxCount(Tier tier) throws XMLStreamException {
		OMElement policy = AXIOMUtil.stringToOM(new String(tier.getPolicyContent()));
		OMElement maxCount =
		                     policy.getFirstChildWithName(APIConstants.POLICY_ELEMENT)
		                           .getFirstChildWithName(APIConstants.THROTTLE_CONTROL_ELEMENT)
		                           .getFirstChildWithName(APIConstants.POLICY_ELEMENT)
		                           .getFirstChildWithName(APIConstants.THROTTLE_MAXIMUM_COUNT_ELEMENT);
		if (maxCount != null)
			return Integer.parseInt(maxCount.getText());

		return -1;
	}

	private static int getTimeUnit(Tier tier) throws XMLStreamException {
		OMElement policy = AXIOMUtil.stringToOM(new String(tier.getPolicyContent()));
		OMElement timeUnit =
		                     policy.getFirstChildWithName(APIConstants.POLICY_ELEMENT)
		                           .getFirstChildWithName(APIConstants.THROTTLE_CONTROL_ELEMENT)
		                           .getFirstChildWithName(APIConstants.POLICY_ELEMENT)
		                           .getFirstChildWithName(APIConstants.THROTTLE_UNIT_TIME_ELEMENT);
		if (timeUnit != null)
			return Integer.parseInt(timeUnit.getText());

		return -1;
	}

	private static void createTierPricingMap() throws APIManagementException {
		Map<String, Tier> tierMap = APIUtil.getTiers();
		for (Map.Entry<String, Tier> entry : tierMap.entrySet()) {
			Map<String, Object> attributes = entry.getValue().getTierAttributes();
			if (attributes != null && attributes.containsKey("Rate")) {
				tierPricing.put(entry.getKey(), Float.parseFloat(attributes.get("Rate").toString()));
			} else {
				tierPricing.put(entry.getKey(), 0f);
			}
			
			if (attributes != null && attributes.containsKey("CurrencyType")) {
				currencyType.put(entry.getKey(), attributes.get("CurrencyType").toString());
			} else {
				currencyType.put(entry.getKey(), "");
			}
			try {
	            int maxCount = getMaxCount(entry.getValue());
	            tierMaximumCount.put(entry.getKey(), maxCount);
            } catch (XMLStreamException e) {
	            
            }
			
			
			try {
	            int unitTime = getTimeUnit(entry.getValue());
	            tierUnitTime.put(entry.getKey(), unitTime);
            } catch (XMLStreamException e) {
	            
            }

		}
	}

	private static void printTierPricing() throws APIManagementException {
		createTierPricingMap();
		System.out.println("Print Tier Pricings");
		for (Map.Entry<String, Float> pricing : tierPricing.entrySet()) {
			System.out.println("Price for Tier : " + pricing.getKey() + " = " + pricing.getValue());
		}
	}

	private static void handleException(String msg) throws APIManagementException {
		log.error(msg);
		throw new APIManagementException(msg);
	}

	private static void handleException(String msg, Throwable t) throws APIManagementException {
		log.error(msg, t);
		throw new APIManagementException(msg, t);
	}
}
