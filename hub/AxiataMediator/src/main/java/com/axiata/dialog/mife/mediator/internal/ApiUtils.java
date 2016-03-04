package com.axiata.dialog.mife.mediator.internal;

import com.axiata.dialog.oneapi.validation.AxiataException;
import com.axiata.dialog.mife.mediator.entity.nb.InboundSMSMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Map;

/**
 * @author Charith_02380
 *
 */
public class ApiUtils {

    private Log log = LogFactory.getLog(ApiUtils.class);
    private static Hashtable<String, Processor> processors = null;
    private static Properties prop;

    /**
     *
     */
    public ApiUtils() {
        loadProperties();
        processors = new Hashtable<String, Processor>();
    }

    /**
     *
     */
    public static void loadProperties() {
        try {
            prop = new Properties();
            prop.load(ApiUtils.class.getResourceAsStream("/processors.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param apiType
     * @return
     */
    private Processor getProcessor(String apiType) {
        try {
            Processor p = processors.get(apiType);
            if (p == null) {
                String className = prop.getProperty(apiType);
                @SuppressWarnings("unchecked")
                Class<Processor> clazz = (Class<Processor>) Class.forName(className);
                p = clazz.newInstance();
                processors.put(apiType, p);
            }
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param apiType
     * @return
     */
    public String getAppID(MessageContext context, String apiType) {
        try {
            Processor p = getProcessor(apiType);
            return p.getAppID(context);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param apiType
     * @param batchSize
     * @return
     */
    public APICall setBatchSize(String uri, String body, String apiType, int batchSize) {
        try {
            Processor p = getProcessor(apiType);
            return p.setBatchSize(uri, body, batchSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param apiType
     * @param object
     * @return
     */
    public JSONArray getResults(String apiType, String response) {
        try {
            Processor p = getProcessor(apiType);
            return p.getResultList(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param apiType
     * @param results
     * @param responses
     * @param requestid
     * @return
     */
    public JSONObject generateResponse(MessageContext context, String apiType, JSONArray results, ArrayList<String> responses, String requestid) {
        try {
            String resourceURL = prop.getProperty(apiType + "_resource_url");
            Processor p = getProcessor(apiType);
            return p.generateResponse(context, results, AxiataUID.resourceURLWithappend(resourceURL, requestid, "messages"), responses);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param context
     * @param apiType
     * @param inboundSMSMessageList
     * @param responses
     * @param requestid
     * @return
     */
    public JSONObject generateResponse(MessageContext context, String apiType, List<InboundSMSMessage> inboundSMSMessageList, ArrayList<String> responses, String requestid) {
        try {
            String resourceURL = prop.getProperty(apiType + "_resource_url_post");
            Processor p = getProcessor(apiType);
            return p.generateResponse(context, inboundSMSMessageList, AxiataUID.resourceURLWithappend(resourceURL, requestid, "messages"), responses);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, String> getJwtTokenDetails(MessageContext context) {

        HashMap<String, String> jwtDetails = new HashMap<String, String>();

        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) context)
                .getAxis2MessageContext();
        Object headers = axis2MessageContext.getProperty(
                org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

        if (headers != null && headers instanceof Map) {

            try {

                Map headersMap = (Map) headers;
                String jwtparam = (String) headersMap.get("x-jwt-assertion");
                String[] jwttoken = jwtparam.split("\\.");
                String jwtbody = Base64Coder.decodeString(jwttoken[1]);
                JSONObject jwtobj = new JSONObject(jwtbody);
                jwtDetails.put("applicationid", jwtobj.getString("http://wso2.org/claims/applicationid"));
                jwtDetails.put("subscriber", jwtobj.getString("http://wso2.org/claims/subscriber"));
                jwtDetails.put("version", jwtobj.getString("http://wso2.org/claims/version"));
            } catch (JSONException ex) {

                log.error("Error in getJwtTokenDetails : " + ex.getMessage());
                throw new AxiataException("SVC1000", "", new String[]{null});
            }
        }

        return jwtDetails;
    }
}
