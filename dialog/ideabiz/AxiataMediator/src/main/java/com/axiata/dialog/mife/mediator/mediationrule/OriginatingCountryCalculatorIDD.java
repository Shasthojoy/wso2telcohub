/**
 * @author dialog
 *
 */
package com.axiata.dialog.mife.mediator.mediationrule;

import com.axiata.dialog.dbutils.AxiataDbService;
import com.axiata.dialog.dbutils.Operator;
import com.axiata.dialog.dbutils.Operatorendpoint;
import com.axiata.dialog.mife.mediator.OperatorEndpoint;
import com.axiata.dialog.mife.mediator.internal.Util;
import com.axiata.dialog.mife.mnc.resolver.MNCQueryClient;
import com.axiata.dialog.oneapi.validation.AxiataException;
import org.apache.axis2.addressing.EndpointReference;

import java.util.ArrayList;
import java.util.List;

public class OriginatingCountryCalculatorIDD extends OriginatingCountryCalculator {
    /*
     * API EndPoint map.
     * for the same purpose , database can also be used
     * for the POC property file and hmap is used. In future will shift to database
     */
    //private HashMap<String, EndpointReference> apiEprMap;

    private String ERROR_MESSAGE = "Error.No API endpoint matched your request";
    List<Operatorendpoint> opendpoints;
    MNCQueryClient mncQueryclient = null;

    public void initialize() throws Exception {

        Util.getPropertyFile();
        opendpoints = new AxiataDbService().operatorEndpoints();

        mncQueryclient = new MNCQueryClient();

    }

    /**
     * getting the API end point of the sender
     *
     * @param userMSISDN MSISDN
     * @param apikey Key of the API in endpoints table
     * @param requestPathURL The sub context of API
     * @param isredirect
     * @param operators Operator list
     * @return Southbound endpoint
     * @throws Exception
     */
    public OperatorEndpoint getAPIEndpointsByMSISDN(String userMSISDN, String apikey, String requestPathURL, boolean isredirect, List<Operator> operators) throws Exception {

        String operator;
        String userName = userMSISDN.substring(1);

        //Initialize End points
        initialize();
        
        String mcc = null;        
        //mcc not known in mediator
        operator = mncQueryclient.QueryNetwork(mcc, userMSISDN);
        
        if (operator == null) {
            throw new AxiataException("SVC0001", "", new String[]{"No valid operator found for given MSISDN"});
        }

        //is operator provisioned
        Operator valid = null;
        for (Operator d : operators) {
            if (d.getOperatorname() != null && d.getOperatorname().contains(operator.toUpperCase())) {
                valid = d;
                break;
            }
        }

        if (valid == null) {
            throw new AxiataException("SVC0001", "", new String[]{"Requested service is not provisioned"});
        }

        Operatorendpoint validOperatorendpoint = getValidEndpoints(apikey, operator);
        if (validOperatorendpoint == null) {
            throw new AxiataException("SVC0001", "", new String[]{"Requested service is not provisioned"});
        }
                
        String extremeEndpoint = validOperatorendpoint.getEndpoint();
        if (!isredirect) {
            extremeEndpoint = validOperatorendpoint.getEndpoint() + requestPathURL;
        }
        EndpointReference eprMSISDN = new EndpointReference(extremeEndpoint);

        return new OperatorEndpoint(eprMSISDN, operator.toUpperCase());

    }

    /**
     * getting the API end point of the Application
     * @param apiKey Key of the API in endpoints table
     * @param requestPathURL The sub context of API
     * @param validoperator
     * @return List of southbound endpoints
     * @throws Exception
     */
    public List<OperatorEndpoint> getAPIEndpointsByApp(String apiKey, String requestPathURL, List<Operator> validoperator) throws Exception {

        List<OperatorEndpoint> endpoints = new ArrayList<OperatorEndpoint>();

        Util.getPropertyFile();
        initialize();

        List<Operatorendpoint> validendpoints = getValidEndpoints(apiKey, validoperator);
        String extremeEndpoint;

        for (Operatorendpoint oe : validendpoints) {
//            if (apiType.equals("payment") || (apiType.equals("location"))) {
//                extremeEndpoint = oe.getEndpoint();
//            } else {
                extremeEndpoint = oe.getEndpoint() + requestPathURL;

//            }

            endpoints.add(new OperatorEndpoint(new EndpointReference(extremeEndpoint), oe.getOperatorcode()));
        }

        return endpoints;
    }

    private String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separator) {
                count++;
                if (count == index) {
                    break;
                }
            } else {
                if (count == index - 1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }

    private String getApplicationProperty(String operatorcode, String api) {
        String endpoint = null;
        for (Operatorendpoint d : opendpoints) {
            if ((d.getApi().contains(api)) && (d.getOperatorcode().contains(operatorcode))) {
                endpoint = d.getEndpoint();
                break;
            }
        }
        return endpoint;
    }

    private List<Operatorendpoint> getValidEndpoints(String api, List<Operator> validoperator) {
        String endpoint = null;
        List<String> validlist = new ArrayList();
        List<Operatorendpoint> validoperendpoints = new ArrayList();

        for (Operator op : validoperator) {
            validlist.add(op.getOperatorname());
        }

        for (Operatorendpoint d : opendpoints) {
            if ((d.getApi().contains(api)) && (validlist.contains(d.getOperatorcode()))) {
                validoperendpoints.add(d);
            }
        }
        return validoperendpoints;
    }
    
    private Operatorendpoint getValidEndpoints(String api, String validoperator) {
        String endpoint = null;
        Operatorendpoint validoperendpoint = null;

        for (Operatorendpoint d : opendpoints) {
            if ((d.getApi().equalsIgnoreCase(api)) && (validoperator.equalsIgnoreCase(d.getOperatorcode()) ) ) {
                validoperendpoint  = d;
                break;
            }
        }
        return validoperendpoint;
    }    
    
}
