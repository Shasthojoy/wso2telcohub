package com.axiata.dialog.mife.mediator;

import com.axiata.dialog.mife.events.data.publisher.EventsDataPublisherClient;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundDataPublisher;
import com.axiata.dialog.mife.southbound.data.publisher.SouthboundPublisherConstants;
import com.axiata.dialog.mife.southbound.data.publisher.dto.NorthboundResponsePublisherDTO;
import com.axiata.dialog.mife.southbound.data.publisher.internal.SouthboundDataComponent;
import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.oneapi.validation.PolicyException;
import com.axiata.dialog.oneapi.validation.RequestError;
import com.axiata.dialog.oneapi.validation.ServiceException;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.apimgt.usage.publisher.APIMgtUsagePublisherConstants;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

/**
 *
 * @author Hiranya
 */
public class NorthboundPublisher {

    private static final Log log = LogFactory.getLog(NorthboundPublisher.class);
    private boolean enabled = true;//SouthboundDataComponent.getApiMgtConfigReaderService().isEnabled();
    private volatile SouthboundDataPublisher publisher;
    private String publisherClass = "com.axiata.dialog.mife.southbound.data.publisher.SouthboundDataPublisher";
    private EventsDataPublisherClient eventsPublisherClient;

    public void publishNBErrorResponseData(AxiataException ax, String retStr, MessageContext messageContext) {
        //set properties for response data publisher
        //messageContext.setProperty(SouthboundPublisherConstants.RESPONSE_CODE, Integer.toString(statusCode));
        //messageContext.setProperty(SouthboundPublisherConstants.MSISDN, messageContext.getProperty(AxiataConstants.USER_MSISDN));

        boolean isPaymentReq = false;

        if (retStr != null && !retStr.isEmpty()) {
            //get serverReferenceCode property for payment API response
            JSONObject paymentRes = null;
            //get exception property for exception response
            JSONObject exception = null;
            try {
                JSONObject response = new JSONObject(retStr);
                paymentRes = response.optJSONObject("amountTransaction");
                if (paymentRes != null) {
                    if(paymentRes.has("serverReferenceCode")){
                        messageContext.setProperty(SouthboundPublisherConstants.OPERATOR_REF, paymentRes.optString("serverReferenceCode"));
                    } else if (paymentRes.has("originalServerReferenceCode")){
                        messageContext.setProperty(SouthboundPublisherConstants.OPERATOR_REF, paymentRes.optString("originalServerReferenceCode"));
                    }
                    isPaymentReq = true;
                }


            } catch (JSONException e) {
                log.error("Error in converting response to json. " + e.getMessage(), e);
            }
        }
        
        String exMsg = ax.getErrmsg()+" "+Arrays.toString(ax.getErrvar()).replace("[", "").replace("]", "");
        log.info("exception id: " + ax.getErrcode());
        log.info("exception message: " + exMsg);
        messageContext.setProperty(SouthboundPublisherConstants.EXCEPTION_ID, ax.getErrcode());
        messageContext.setProperty(SouthboundPublisherConstants.EXCEPTION_MESSAGE, exMsg);

        String buildedExeption = buildErrorResponse(ax); 
        
        //publish data to BAM
        publishNBResponse(messageContext, buildedExeption);

    }
    
    private String buildErrorResponse(AxiataException ax){
        String exceptionString = ""; //Build axiata exeption to normal exception here
        if(ax.getErrcode().length() > 3){
            if(ax.getErrcode().substring(0, 3).equals("POL")){
                //Policy Exception
                PolicyException pol = new PolicyException(ax.getErrcode(), ax.getErrmsg(), Arrays.toString(ax.getErrvar()).replace("[", "").replace("]", ""));
                RequestError error = new RequestError();
                error.setPolicyException(pol);
                exceptionString = getErrorJSONString(error);
            } else if (ax.getErrcode().substring(0, 3).equals("SVC")){
                //Service Exception
                ServiceException svc = new ServiceException(ax.getErrcode(), ax.getErrmsg(), Arrays.toString(ax.getErrvar()).replace("[", "").replace("]", ""));
                RequestError error = new RequestError();
                error.setServiceException(svc);
                exceptionString = getErrorJSONString(error);
            } else {
                exceptionString = ax.toString();
            }
        }
        return exceptionString;
    }
    
    private static String getErrorJSONString(RequestError error) {

        String errorObjectString = "";
        try {
            errorObjectString = new JSONObject(error).toString();
            //JSONObject j = new JSONObject(errorObjectString);
            JSONObject innerObj = new JSONObject(error);
            if (innerObj.isNull("serviceException")) {
                //.out.println("serviceException is null");
                innerObj.remove("serviceException");
            } else if (innerObj.isNull("policyException")) {
                //System.out.println("serviceException is null");
                innerObj.remove("policyException");
            }
            //System.out.println(j.toJSONString());
            errorObjectString = innerObj.toString();

        } catch (Exception ex) {
            System.out.println("NortboundPublisher>getErrorJSONString: " + ex);
        }
        return errorObjectString;
    }

    private void publishNBResponse(MessageContext mc, String jsonBody) {
        if (!enabled) {
            return;
        }

        if (publisher == null) {
            synchronized (this) {
                if (publisher == null) {
                    log.debug("Instantiating Data Publisher");
                    publisher = new SouthboundDataPublisher();
                    publisher.init();
                }
            }
        }
        
        Long currentTime = System.currentTimeMillis();
        Long serviceTime = currentTime - (Long) mc.getProperty(SouthboundPublisherConstants.REQUEST_TIME);

        NorthboundResponsePublisherDTO nbResponseDTO = new NorthboundResponsePublisherDTO();
        nbResponseDTO.setConsumerKey((String) mc.getProperty(APIMgtUsagePublisherConstants.CONSUMER_KEY));
        nbResponseDTO.setUsername((String) mc.getProperty(APIMgtUsagePublisherConstants.USER_ID));
        nbResponseDTO.setTenantDomain(MultitenantUtils.getTenantDomain(nbResponseDTO.getUsername()));
        nbResponseDTO.setContext((String) mc.getProperty(APIMgtUsagePublisherConstants.CONTEXT));
        nbResponseDTO.setApi_version((String) mc.getProperty(APIMgtUsagePublisherConstants.API_VERSION));
        nbResponseDTO.setApi((String) mc.getProperty(APIMgtUsagePublisherConstants.API));
        nbResponseDTO.setVersion((String) mc.getProperty(APIMgtUsagePublisherConstants.VERSION));
        nbResponseDTO.setResourcePath((String) mc.getProperty(APIMgtUsagePublisherConstants.RESOURCE));
        nbResponseDTO.setMethod((String) mc.getProperty(APIMgtUsagePublisherConstants.HTTP_METHOD));
        nbResponseDTO.setResponseTime(currentTime);
        nbResponseDTO.setServiceTime(serviceTime);
        nbResponseDTO.setHostName((String) mc.getProperty(APIMgtUsagePublisherConstants.HOST_NAME));
        nbResponseDTO.setApiPublisher((String) mc.getProperty(APIMgtUsagePublisherConstants.API_PUBLISHER));
        nbResponseDTO.setApplicationName((String) mc.getProperty(APIMgtUsagePublisherConstants.APPLICATION_NAME));
        nbResponseDTO.setApplicationId((String) mc.getProperty(APIMgtUsagePublisherConstants.APPLICATION_ID));

        nbResponseDTO.setRequestId((String) mc.getProperty(SouthboundPublisherConstants.REQUEST_ID));
        nbResponseDTO.setResponseCode((String) mc.getProperty(SouthboundPublisherConstants.RESPONSE_CODE));
        nbResponseDTO.setMsisdn((String) mc.getProperty(SouthboundPublisherConstants.MSISDN));
        nbResponseDTO.setChargeAmount((String) mc.getProperty(SouthboundPublisherConstants.CHARGE_AMOUNT));
        nbResponseDTO.setPurchaseCategoryCode((String) mc.getProperty(SouthboundPublisherConstants.PAY_CATEGORY));
        nbResponseDTO.setOperatorRef((String) mc.getProperty(SouthboundPublisherConstants.OPERATOR_REF));
        nbResponseDTO.setExceptionId((String) mc.getProperty(SouthboundPublisherConstants.EXCEPTION_ID));
        nbResponseDTO.setExceptionMessage((String) mc.getProperty(SouthboundPublisherConstants.EXCEPTION_MESSAGE));
        nbResponseDTO.setJsonBody(jsonBody);

        nbResponseDTO.setOperationType((Integer) mc.getProperty(SouthboundPublisherConstants.OPERATION_TYPE));
        nbResponseDTO.setMerchantId((String) mc.getProperty(SouthboundPublisherConstants.MERCHANT_ID));
        nbResponseDTO.setCategory((String) mc.getProperty(SouthboundPublisherConstants.CATEGORY));
        nbResponseDTO.setSubCategory((String) mc.getProperty(SouthboundPublisherConstants.SUB_CATEGORY));
        
        
        //Hira added to get Subscriber in end User request scienario 
        String userIdToPublish = nbResponseDTO.getUsername();
        if(userIdToPublish != null && userIdToPublish.contains("@")){
            String[] userIdArray = userIdToPublish.split("@");
            userIdToPublish = userIdArray[0];
            nbResponseDTO.setUsername(userIdToPublish);
        }


        publisher.publishEvent(nbResponseDTO);
    }

    private void publishToCEP(MessageContext messageContext) {
        if (eventsPublisherClient == null) {
            eventsPublisherClient = new EventsDataPublisherClient();
        }
        eventsPublisherClient.publishEvent(messageContext);
    }
}
