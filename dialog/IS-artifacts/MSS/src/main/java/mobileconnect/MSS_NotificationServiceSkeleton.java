
/**
 * MSS_NotificationServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package mobileconnect;
    /**
     *  MSS_NotificationServiceSkeleton java skeleton for the axisService
     */
    public class MSS_NotificationServiceSkeleton{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param mSS_Notification 
             * @return mSS_NotificationResponse 
         */
        
                 public es.telefonica.mobileconnect.MSS_NotificationResponse mSS_Notification
                  (
                  es.telefonica.mobileconnect.MSS_Notification mSS_Notification
                  )
            {
                //TODO : fill this with the necessary business logic
                	 mSS_Notification.getMSS_StatusResp().getAP_Info().getAP_ID();
                	 System.out.println("====================="+mSS_Notification.getMSS_StatusResp().getAP_Info().getAP_ID());
                throw new  UnsupportedOperationException("Please implement " + this.getClass().getName() + "#mSS_Notification");
        }
     
    }
    