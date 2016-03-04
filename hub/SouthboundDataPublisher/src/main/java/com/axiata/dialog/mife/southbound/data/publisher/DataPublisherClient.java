package com.axiata.dialog.mife.southbound.data.publisher;

import com.axiata.dialog.mife.southbound.data.publisher.dto.NorthboundResponsePublisherDTO;
import com.axiata.dialog.mife.southbound.data.publisher.dto.SouthboundRequestPublisherDTO;
import com.axiata.dialog.mife.southbound.data.publisher.dto.SouthboundResponsePublisherDTO;
import com.axiata.dialog.mife.southbound.data.publisher.internal.SouthboundDataComponent;
import org.apache.axis2.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.rest.RESTConstants;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.AuthenticationContext;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsagePublisherConstants;
import org.wso2.carbon.apimgt.usage.publisher.DataPublisherUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataPublisherClient {

    private static final Log log = LogFactory.getLog(DataPublisherClient.class);

    private boolean enabled = SouthboundDataComponent.getApiMgtConfigReaderService().isEnabled();
    private volatile SouthboundDataPublisher publisher;
    private String publisherClass = "com.axiata.dialog.mife.southbound.data.publisher.SouthboundDataPublisher";

    public void publishRequest(MessageContext mc, String jsonBody) {

        if (!enabled) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (publisher == null) {
            synchronized (this) {
                if (publisher == null) {
                    log.debug("Instantiating Data Publisher");
                    publisher = new SouthboundDataPublisher();
                    publisher.init();
                }
            }
        }
        AuthenticationContext authContext = APISecurityUtils.getAuthenticationContext(mc);
        String consumerKey = "";
        String username = "";
        String applicationName = "";
        String applicationId = "";
        if (authContext != null) {
            consumerKey = authContext.getConsumerKey();
            username = authContext.getUsername();
            applicationName = authContext.getApplicationName();
            applicationId = authContext.getApplicationId();
        }
        String hostName = DataPublisherUtil.getHostAddress();
        String context = (String) mc.getProperty(RESTConstants.REST_API_CONTEXT);
        String api_version = (String) mc.getProperty(RESTConstants.SYNAPSE_REST_API);
        String fullRequestPath = (String) mc.getProperty(RESTConstants.REST_FULL_REQUEST_PATH);

        int tenantDomainIndex = fullRequestPath.indexOf("/t/");
        String apiPublisher = MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
        if (tenantDomainIndex != -1) {
            String temp = fullRequestPath.substring(tenantDomainIndex + 3, fullRequestPath.length());
            apiPublisher = temp.substring(0, temp.indexOf("/"));
        }

        int index = api_version.indexOf("--");
        if (index != -1) {
            api_version = api_version.substring(index + 2);
        }

        String api = api_version.split(":")[0];
        index = api.indexOf("--");
        if (index != -1) {
            api = api.substring(index + 2);
        }
        String version = (String) mc.getProperty(RESTConstants.SYNAPSE_REST_API_VERSION);
        String resource = extractResource(mc);
        String method = (String) ((Axis2MessageContext) mc).getAxis2MessageContext().getProperty(
                Constants.Configuration.HTTP_METHOD);
        String tenantDomain = MultitenantUtils.getTenantDomain(username);
//        int tenantId = ServiceReferenceHolder.getInstance().getRealmService().getTenantManager().getTenantId
// (tenantDomain);

        SouthboundRequestPublisherDTO requestPublisherDTO = new SouthboundRequestPublisherDTO();
        requestPublisherDTO.setConsumerKey(consumerKey);
        requestPublisherDTO.setContext(context);
        requestPublisherDTO.setApi_version(api_version);
        requestPublisherDTO.setApi(api);
        requestPublisherDTO.setVersion(version);
        requestPublisherDTO.setResourcePath(resource);
        requestPublisherDTO.setMethod(method);
        requestPublisherDTO.setRequestTime(currentTime);
        if (mc.getProperty(APIMgtUsagePublisherConstants.USER_ID) != null) {
            String serviceProvider = (String) mc.getProperty(APIMgtUsagePublisherConstants.USER_ID);
            requestPublisherDTO.setUsername(serviceProvider);
        } else {
            requestPublisherDTO.setUsername(username);
            mc.setProperty(APIMgtUsagePublisherConstants.USER_ID, username);
        }
        requestPublisherDTO.setTenantDomain(tenantDomain);
        requestPublisherDTO.setHostName(hostName);
        requestPublisherDTO.setApiPublisher(apiPublisher);
        requestPublisherDTO.setApplicationName(applicationName);
        requestPublisherDTO.setApplicationId(applicationId);

        requestPublisherDTO.setRequestId((String) mc.getProperty(SouthboundPublisherConstants.REQUEST_ID));
        requestPublisherDTO.setOperatorId((String) mc.getProperty(SouthboundPublisherConstants.OPERATOR_ID));
        requestPublisherDTO.setSbEndpoint((String) mc.getProperty(SouthboundPublisherConstants.SB_ENDPOINT));
        requestPublisherDTO.setChargeAmount((String) mc.getProperty(SouthboundPublisherConstants.CHARGE_AMOUNT));
        requestPublisherDTO.setPurchaseCategoryCode((String) mc.getProperty(SouthboundPublisherConstants.PAY_CATEGORY));
        requestPublisherDTO.setJsonBody(jsonBody);

        //Hira added to get Subscriber in end User request scienario 
        String userIdToPublish = requestPublisherDTO.getUsername();
        if (userIdToPublish != null && userIdToPublish.contains("@")) {
            String[] userIdArray = userIdToPublish.split("@");
            userIdToPublish = userIdArray[0];
            requestPublisherDTO.setUsername(userIdToPublish);
        }

        publisher.publishEvent(requestPublisherDTO);

        mc.setProperty(APIMgtUsagePublisherConstants.CONSUMER_KEY, consumerKey);
        mc.setProperty(APIMgtUsagePublisherConstants.CONTEXT, context);
        mc.setProperty(APIMgtUsagePublisherConstants.API_VERSION, api_version);
        mc.setProperty(APIMgtUsagePublisherConstants.API, api);
        mc.setProperty(APIMgtUsagePublisherConstants.VERSION, version);
        mc.setProperty(APIMgtUsagePublisherConstants.RESOURCE, resource);
        mc.setProperty(APIMgtUsagePublisherConstants.HTTP_METHOD, method);
        //using new property name for southbound request time
        mc.setProperty(SouthboundPublisherConstants.REQUEST_TIME, currentTime);
        //mc.setProperty(APIMgtUsagePublisherConstants.USER_ID, username);
        mc.setProperty(APIMgtUsagePublisherConstants.HOST_NAME, hostName);
        mc.setProperty(APIMgtUsagePublisherConstants.API_PUBLISHER, apiPublisher);
        mc.setProperty(APIMgtUsagePublisherConstants.APPLICATION_NAME, applicationName);
        mc.setProperty(APIMgtUsagePublisherConstants.APPLICATION_ID, applicationId);
    }

    public void publishResponse(MessageContext mc, String jsonBody) {
        if (!enabled) {
            return;
        }

        Long currentTime = System.currentTimeMillis();

        Long serviceTime = currentTime - (Long) mc.getProperty(SouthboundPublisherConstants.REQUEST_TIME);

        SouthboundResponsePublisherDTO responsePublisherDTO = new SouthboundResponsePublisherDTO();
        responsePublisherDTO.setConsumerKey((String) mc.getProperty(APIMgtUsagePublisherConstants.CONSUMER_KEY));
        responsePublisherDTO.setUsername((String) mc.getProperty(APIMgtUsagePublisherConstants.USER_ID));
        responsePublisherDTO.setTenantDomain(MultitenantUtils.getTenantDomain(responsePublisherDTO.getUsername()));
        responsePublisherDTO.setContext((String) mc.getProperty(APIMgtUsagePublisherConstants.CONTEXT));
        responsePublisherDTO.setApi_version((String) mc.getProperty(APIMgtUsagePublisherConstants.API_VERSION));
        responsePublisherDTO.setApi((String) mc.getProperty(APIMgtUsagePublisherConstants.API));
        responsePublisherDTO.setVersion((String) mc.getProperty(APIMgtUsagePublisherConstants.VERSION));
        responsePublisherDTO.setResourcePath((String) mc.getProperty(APIMgtUsagePublisherConstants.RESOURCE));
        responsePublisherDTO.setMethod((String) mc.getProperty(APIMgtUsagePublisherConstants.HTTP_METHOD));
        responsePublisherDTO.setResponseTime(currentTime);
        responsePublisherDTO.setServiceTime(serviceTime);
        responsePublisherDTO.setHostName((String) mc.getProperty(APIMgtUsagePublisherConstants.HOST_NAME));
        responsePublisherDTO.setApiPublisher((String) mc.getProperty(APIMgtUsagePublisherConstants.API_PUBLISHER));
        responsePublisherDTO.setApplicationName((String) mc.getProperty(APIMgtUsagePublisherConstants.APPLICATION_NAME));
        responsePublisherDTO.setApplicationId((String) mc.getProperty(APIMgtUsagePublisherConstants.APPLICATION_ID));

        responsePublisherDTO.setRequestId((String) mc.getProperty(SouthboundPublisherConstants.REQUEST_ID));
        responsePublisherDTO.setOperatorId((String) mc.getProperty(SouthboundPublisherConstants.OPERATOR_ID));
        responsePublisherDTO.setResponseCode((String) mc.getProperty(SouthboundPublisherConstants.RESPONSE_CODE));
        responsePublisherDTO.setMsisdn((String) mc.getProperty(SouthboundPublisherConstants.MSISDN));
        responsePublisherDTO.setChargeAmount((String) mc.getProperty(SouthboundPublisherConstants.CHARGE_AMOUNT));
        responsePublisherDTO.setPurchaseCategoryCode((String) mc.getProperty(SouthboundPublisherConstants.PAY_CATEGORY));
        responsePublisherDTO.setOperatorRef((String) mc.getProperty(SouthboundPublisherConstants.OPERATOR_REF));
        responsePublisherDTO.setExceptionId((String) mc.getProperty(SouthboundPublisherConstants.EXCEPTION_ID));
        responsePublisherDTO.setExceptionMessage((String) mc.getProperty(SouthboundPublisherConstants.EXCEPTION_MESSAGE));
        responsePublisherDTO.setJsonBody(jsonBody);
        if (mc.getProperty(SouthboundPublisherConstants.RESPONSE) != null) {
            responsePublisherDTO.setResponse(Integer.parseInt((String) mc.getProperty(SouthboundPublisherConstants.RESPONSE)));
        } else {
            responsePublisherDTO.setResponse(1);
        }

        responsePublisherDTO.setOperationType((Integer) mc.getProperty(SouthboundPublisherConstants.OPERATION_TYPE));
        responsePublisherDTO.setMerchantId((String) mc.getProperty(SouthboundPublisherConstants.MERCHANT_ID));
        responsePublisherDTO.setCategory((String) mc.getProperty(SouthboundPublisherConstants.CATEGORY));
        responsePublisherDTO.setSubCategory((String) mc.getProperty(SouthboundPublisherConstants.SUB_CATEGORY));

        //Hira added to get Subscriber in end User request scienario 
        String userIdToPublish = responsePublisherDTO.getUsername();
        if (userIdToPublish != null && userIdToPublish.contains("@")) {
            String[] userIdArray = userIdToPublish.split("@");
            userIdToPublish = userIdArray[0];
            responsePublisherDTO.setUsername(userIdToPublish);
        }

        publisher.publishEvent(responsePublisherDTO);
    }

    private String extractResource(MessageContext mc) {
        String resource = "/";
        Pattern pattern = Pattern.compile("^/.+?/.+?([/?].+)$");
        Matcher matcher = pattern.matcher((String) mc.getProperty(RESTConstants.REST_FULL_REQUEST_PATH));
        if (matcher.find()) {
            resource = matcher.group(1);
        }
        return resource;
    }
}
