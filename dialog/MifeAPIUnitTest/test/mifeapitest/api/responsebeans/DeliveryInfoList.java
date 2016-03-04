/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mifeapitest.api.responsebeans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shashika Wijayasekera @ 2014-01-16
 */
public class DeliveryInfoList {

    private String resourceURL;
    private List<DeliveryInfo> deliveryInfo = new ArrayList<DeliveryInfo>();

    /**
     * @return the resourceURL
     */
    public String getResourceURL() {
        return resourceURL;
    }

    /**
     * @param resourceURL the resourceURL to set
     */
    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }

    /**
     * @return the deliveryInfo
     */
    public List<DeliveryInfo> getDeliveryInfo() {
        return deliveryInfo;
    }

    /**
     * @param deliveryInfo the deliveryInfo to set
     */
    public void setDeliveryInfo(List<DeliveryInfo> deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
