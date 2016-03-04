/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller;

import com.dialog.acr.model.HibernateUtil;
import com.dialog.acr.model.entities.ACR;
import com.dialog.acr.model.entities.Application;
import com.dialog.acr.model.entities.ApplicationKey;
import com.dialog.acr.model.entities.ServiceProvider;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class Getters {

    public static boolean acrIsExists(String appID, String msisdn) {
        boolean returnvalue = false;
        List acrCount = null;
        Session s = HibernateUtil.getSession();
        try {

            String acrCountString = "FROM ACR AS acr WHERE acr.application.appId = :appId AND acr.msisdn = :msisdn AND acr.status != 101";
            Query acrCountQuery = s.createQuery(acrCountString);
            acrCountQuery.setString("appId", appID);
            acrCountQuery.setString("msisdn", msisdn);
            //s.close();

            acrCount = acrCountQuery.list();
        } catch (Exception e) {
            System.out.println("acrIsExists: " + e);
        } finally{
            s.close();
        }
        if (acrCount.size() > 0) {
            returnvalue = true;
        }
        //s.close();
        return returnvalue;
    }

    public static int getACRStatus(String acr) {
        Session s = HibernateUtil.getSession();
        int acrStatus = 0;
        try {
            String acrStatusString = "SELECT acr.status FROM ACR AS acr WHERE acr.acr = :acr";
            Query acrStatusQuery = s.createQuery(acrStatusString);
            acrStatusQuery.setString("acr", acr);

            List acrStatusList = acrStatusQuery.list();
            Iterator iter = acrStatusList.iterator();
            while (iter.hasNext()) {
                acrStatus = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getACRStatus: " + e);
        }finally{
            s.close();
        }
        return acrStatus;
    }

    public static String getACRIDByACR(String acr) {

        Session s = HibernateUtil.getSession();
        String acrID = null;
        try {


            String acrIDString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.acr = :acr";
            Query acrIDQuery = s.createQuery(acrIDString);
            acrIDQuery.setString("acr", acr);
            //s.close();

            List acrIDList = acrIDQuery.list();

            Iterator iter = acrIDList.iterator();
            while (iter.hasNext()) {
                acrID = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getACRID: " + e);
        }finally{
            s.close();
        }
        //s.close();
        return acrID;
    }

    public static String getACRbyId(int acrId) {

        Session s = HibernateUtil.getSession();
        String acrID = "";
        try {


            String acrString = "SELECT acr.acr FROM ACR AS acr WHERE acr.acrId = :acrId";
            Query acrQuery = s.createQuery(acrString);
            acrQuery.setInteger("acrId", acrId);
            //s.close();

            List acrIDList = acrQuery.list();

            Iterator iter = acrIDList.iterator();
            while (iter.hasNext()) {
                acrID = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getACRbyId: " + e);
        }finally{
            s.close();
        }
        //s.close();
        return acrID;
    }
    
    public static String getServiceProviderSecurityKey(String serviceProviderCode) {
        Session s = HibernateUtil.getSession();
        String serviceProviderSecurityKey = null;
        try {
            String serviceProviderSecurityKeyString = "SELECT serviceProvider.securityKey FROM ServiceProvider AS serviceProvider WHERE serviceProvider.providerCode = :providerCode";
            Query serviceProviderSecurityKeyQuery = s.createQuery(serviceProviderSecurityKeyString);
            serviceProviderSecurityKeyQuery.setString("providerCode", serviceProviderCode);

            List serviceProviderSecurityKeyList = serviceProviderSecurityKeyQuery.list();
            Iterator iter = serviceProviderSecurityKeyList.iterator();
            while (iter.hasNext()) {
                serviceProviderSecurityKey = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getServiceProviderSecurityKey: " + e);
        }finally{
            s.close();
        }
        System.out.println("ServiceProviderSecurityKey: " + serviceProviderSecurityKey);
        return serviceProviderSecurityKey;
    }

    public static int getACRCountByAppID(String appID) {

        List acrCount = null;
        Session s = HibernateUtil.getSession();

        try {
            String acrCountString = "FROM ACR AS acr WHERE acr.application.appId = :appId";
            Query acrCountQuery = s.createQuery(acrCountString);
            acrCountQuery.setString("appId", appID);
            //s.close();

            acrCount = acrCountQuery.list();
        } catch (Exception e) {
            System.out.println("getACRCountByAppID: " + e);
        }finally{
            s.close();
        }
        //s.close();
        return acrCount.size();
    }
    
    public static List getAppIdListByProviderId(int providerId) {
        List appIdList = null;
        Session s = null;
        try {
            s = HibernateUtil.getSession();

            String acrIDListString = "SELECT app.appId FROM Application AS app WHERE app.provider.providerId = :spId";
            Query acrIDListQuery = s.createQuery(acrIDListString);
            acrIDListQuery.setInteger("spId", providerId);
            //s.close();

            appIdList = acrIDListQuery.list();
        } catch (Exception e) {
            System.out.println("getAppIdListBySpId: " + e);
        }finally{
            s.close();
        }
        return appIdList;
    }

    public static List getACRIDListByAppID(String appID) {

        List acrIDList = null;
        Session s = null;
        try {
            s = HibernateUtil.getSession();

            String acrIDListString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.application.appId = :appId";
            Query acrIDListQuery = s.createQuery(acrIDListString);
            acrIDListQuery.setString("appId", appID);
            //s.close();

            acrIDList = acrIDListQuery.list();
        } catch (Exception e) {
            System.out.println("getACRIDListByAppID: " + e);
        }finally{
            s.close();
        }
        return acrIDList;
    }
    
    public static List getACRIDListByAppIdAndMsisdn(int appID, String msisdn) {

        List acrIDList = null;
        Session s = null;
        try {
            s = HibernateUtil.getSession();

            String acrIDListString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.application.appId = :appId AND acr.msisdn = :msisdn";
            Query acrIDListQuery = s.createQuery(acrIDListString);
            acrIDListQuery.setInteger("appId", appID);
            acrIDListQuery.setString("msisdn", msisdn);
            //s.close();

            acrIDList = acrIDListQuery.list();
        } catch (Exception e) {
            System.out.println("getACRIDListByAppIdAndMsisdn: " + e);
        }finally{
            s.close();
        }
        return acrIDList;
    }

    public static String getApplicationKeyByAppID(String appID) {

        String appKey = null;
        Session s = null;

        try {
            s = HibernateUtil.getSession();

            String appKeyString = "SELECT applicationKey.appKey FROM ApplicationKey AS applicationKey WHERE applicationKey.application.appId = :appId";
            Query appKeyQuery = s.createQuery(appKeyString);
            appKeyQuery.setString("appId", appID);
            //s.close();

            List appKeyList = appKeyQuery.list();

            Iterator iter = appKeyList.iterator();
            while (iter.hasNext()) {
                appKey = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getApplicationKeyByAppID: " + e);
        }finally{
            s.close();
        }
        return appKey;
    }

    public static boolean getAppIdIsValid(String appId) {
        boolean isValid = false;
        Session s = null;

        try {
            s = HibernateUtil.getSession();

            String appIDExistString = "SELECT application.appId FROM Application AS application WHERE application.appId = :appId";
            Query appIDExistQuery = s.createQuery(appIDExistString);
            appIDExistQuery.setString("appId", appId);

            List appIDList = appIDExistQuery.list();

            if (appIDList.size() > 0) {
                isValid = true;
            }
        } catch (Exception e) {
            System.out.println("getAppIdIsValid: " + e);
        }finally{
            s.close();
        }
        return isValid;
    }

    public static int getServiceProviderIdByProviderCode(String serviceProviderCode) {
        Session s = HibernateUtil.getSession();
        int serviceProviderId = 0;
        try {
            String serviceProviderIdString = "SELECT serviceProvider.providerId FROM ServiceProvider AS serviceProvider WHERE serviceProvider.providerCode = :providerCode";
            Query serviceProviderIdQuery = s.createQuery(serviceProviderIdString);
            serviceProviderIdQuery.setString("providerCode", serviceProviderCode);

            List serviceProviderSecurityKeyList = serviceProviderIdQuery.list();
            Iterator iter = serviceProviderSecurityKeyList.iterator();
            while (iter.hasNext()) {
                serviceProviderId = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getServiceProviderIdByProviderCode: " + e);
        }finally{
            s.close();
        }
        return serviceProviderId;
    }

    public static boolean getProviderIdIsValid(String serviceProviderId) {
        boolean isValid = false;
        Session s = null;

        try {
            s = HibernateUtil.getSession();

            String serviceProviderIDExistString = "SELECT serviceprovider.providerId FROM ServiceProvider AS serviceprovider WHERE serviceprovider.providerId = :providerId";
            Query serviceProviderIDExistQuery = s.createQuery(serviceProviderIDExistString);
            serviceProviderIDExistQuery.setString("providerId", serviceProviderId);


            List serviceProviderIDList = serviceProviderIDExistQuery.list();

            if (serviceProviderIDList.size() > 0) {
                isValid = true;
            }
        } catch (Exception e) {
            System.out.println("getProviderIdIsValid: " + e);
        }finally{
            s.close();
        }
        //s.close();
        return isValid;
    }

    public static int getAppIdByAppCode(String appCode) {
        Session s = HibernateUtil.getSession();
        int appId = 0;
        try {
            String appIdString = "SELECT application.appId FROM Application AS application WHERE application.appCode = :appCode";
            Query appIdQuery = s.createQuery(appIdString);
            appIdQuery.setString("appCode", appCode);

            List appIdList = appIdQuery.list();
            Iterator iter = appIdList.iterator();
            while (iter.hasNext()) {
                appId = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getAppIdByAppCode: " + e);
        }finally{
            s.close();
        }
        return appId;
    }
    
    public static String getAppCodeByAppId(int appId) {
        Session s = HibernateUtil.getSession();
        String appCode = "";
        try {
            String appIdString = "SELECT application.appCode FROM Application AS application WHERE application.appId = :appId";
            Query appIdQuery = s.createQuery(appIdString);
            appIdQuery.setInteger("appId", appId);

            List appIdList = appIdQuery.list();
            Iterator iter = appIdList.iterator();
            while (iter.hasNext()) {
                appCode = iter.next().toString();
            }
        } catch (Exception e) {
            System.out.println("getAppCodeByAppId: " + e);
        }finally{
            s.close();
        }
        return appCode;
    }
    
    public static int getAppIdByAppName(String appName) {
        Session s = HibernateUtil.getSession();
        int appId = 0;
        try {
            String appIdString = "SELECT application.appId FROM Application AS application WHERE application.appName = :appName";
            Query appIdQuery = s.createQuery(appIdString);
            appIdQuery.setString("appName", appName);

            List appIdList = appIdQuery.list();
            Iterator iter = appIdList.iterator();
            while (iter.hasNext()) {
                appId = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getAppIdByAppCode: " + e);
        }finally{
            s.close();
        }
        return appId;
    }

    public static int getAppStatus(int appId) {
        int appStatus = 0;
        Session s = null;

        try {
            s = HibernateUtil.getSession();

            String appStatusString = "SELECT application.status FROM Application AS application WHERE application.appId = :appId";
            Query appStatusQuery = s.createQuery(appStatusString);
            appStatusQuery.setInteger("appId", appId);

            List appStatusList = appStatusQuery.list();

            Iterator iter = appStatusList.iterator();
            while (iter.hasNext()) {
                appStatus = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getApplicationStatusByAppID: " + e);
        }finally{
            s.close();
        }
        //s.close();
        return appStatus;
    }

    public static boolean isACRExists(String acr) {

        boolean acrExist = false;
        Session s = null;
        try {
            s = HibernateUtil.getSession();

            String acrExistString = "SELECT acr.acr FROM ACR AS acr WHERE acr.acr = :acr";
            Query acrExistQuery = s.createQuery(acrExistString);
            acrExistQuery.setString("acr", acr);

            List acrList = acrExistQuery.list();

            if (acrList.size() > 0) {
                acrExist = true;
            }
        } catch (Exception e) {
            System.out.println("isACRExist: " + e);
        }finally{
            s.close();
        }
        return acrExist;
    }

    public static Application getApplicationObj(int appId) {
        Session s = HibernateUtil.getSession();
        Application a = null;
        try {
            a = (Application) s.get(Application.class, appId);
        } catch (Exception e) {
            System.out.println("getApplicationObj: " + e);
        }finally{
            s.close();
        }
        return a;
    }
    
    public static ServiceProvider getServiceProviderObj(int providerId) {
        Session s = HibernateUtil.getSession();
        ServiceProvider pro = null;
        try {
            pro = (ServiceProvider) s.get(ServiceProvider.class, providerId);
        } catch (Exception e) {
            System.out.println("getApplicationObj: " + e);
        }finally{
            s.close();
        }
        return pro;
    }
    
    public static ApplicationKey getApplicationKeyObj(int appKeyId){
        Session s = HibernateUtil.getSession();
        ApplicationKey x = null;
        try {
            x = (ApplicationKey) s.get(ApplicationKey.class, appKeyId);
        } catch (Exception e) {
            System.out.println("getApplicationKeyObj: " + e);
        }finally{
            s.close();
        }
        return x;
    }

    public static ACR getACRObj(int acrId) {
        Session s = HibernateUtil.getSession();
        ACR acr = null;
        try {
            acr = (ACR) s.get(ACR.class, acrId);
        } catch (Exception e) {
            System.out.println("getACRObj: " + e);
        }finally{
            s.close();
        }
        return acr;
    }
}
