/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.controller;

import com.dialog.acr.model.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class Validations {

    public static boolean isRequired(String param) {
        boolean pass = false;
        if (!param.isEmpty()) {
            pass = true;
        }
        return pass;
    }

    public static boolean isAppNameExists(String applicationName) {
        Session s = HibernateUtil.getSession();
        boolean isExist = false;
        try {
            String appNameChkQuery = "SELECT application.appName FROM Application AS application WHERE application.appName = :appname";
            Query q = s.createQuery(appNameChkQuery);
            q.setString("appname", applicationName);
            
            List appNameList = q.list();
            if(appNameList.size() > 0){
                isExist = true;
            }
        } catch (Exception e) {
            System.out.println("isAppNameExists: " + e);
        }finally{
            s.close();
        }
        return isExist;
    }
    
    
    public static boolean isMSISDNExistForAppId(int appId, String msisdn) {
        Session s = HibernateUtil.getSession();
        boolean msisdnExistForAppId = false;
        try {
            String isMSISDNExistForAppIdString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.application.appId = :appId AND acr.msisdn = :msisdn AND acr.status != 101";
            Query isMSISDNExistForAppIdQuery = s.createQuery(isMSISDNExistForAppIdString);
            isMSISDNExistForAppIdQuery.setInteger("appId", appId);
            isMSISDNExistForAppIdQuery.setString("msisdn", msisdn);

            List acrIdList = isMSISDNExistForAppIdQuery.list();
            if (acrIdList.size() > 0) {
                msisdnExistForAppId = true;
            }
        } catch (Exception e) {
            System.out.println("isMSISDNExistForAppId: " + e);
        }finally{
            s.close();
        }
        return msisdnExistForAppId;
    }
    
    public static boolean isACRExistForAppAndMsisdn(int appId, String msisdn) {
        boolean isExists = false;
        
        Session s = HibernateUtil.getSession();
        try {
            String queryString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.application.appId = :operatorId AND acr.msisdn = :msisdn AND acr.status != 101";
            Query query = s.createQuery(queryString);
            query.setInteger("operatorId", appId);
            query.setString("msisdn", msisdn);

            List acrIdList = query.list();
            if (acrIdList.size() > 0) {
                isExists = true;
            }
        } catch (Exception e) {
            System.out.println("isACRExistForOperatorAndMsisdn: " + e);
        }finally{
            s.close();
        }
        return isExists;
    }

    public static boolean isACRIdExistForAppIdAndACR(int appId, String acr) {
        Session s = HibernateUtil.getSession();
        boolean acrIdExistForAppIdAndACR = false;
        try {
            String isACRIdExistForAppIdAndACRString = "SELECT acr.acrId FROM ACR AS acr WHERE acr.application.appId = :appId AND acr.acr = :acr";
            Query isACRIdExistForAppIdAndACRQuery = s.createQuery(isACRIdExistForAppIdAndACRString);
            isACRIdExistForAppIdAndACRQuery.setInteger("appId", appId);
            isACRIdExistForAppIdAndACRQuery.setString("acr", acr);

            List acrIdList = isACRIdExistForAppIdAndACRQuery.list();
            if (acrIdList.size() > 0) {
                acrIdExistForAppIdAndACR = true;
            }
        } catch (Exception e) {

            System.out.println("isACRIdExistForAppIdAndACR: " + e);
        }finally{
            s.close();
        }
        return acrIdExistForAppIdAndACR;
    }
    
    public static int getProviderIDFromAppID(int appId) {

        int providerId = 0;
        Session s = null;

        try {
            s = HibernateUtil.getSession();

            String appIDExistString = "SELECT application.provider.providerId FROM Application AS application WHERE application.appId = :appId";
            Query appIDExistQuery = s.createQuery(appIDExistString);
            appIDExistQuery.setInteger("appId", appId);

            List providerIDList = appIDExistQuery.list();
            Iterator iter = providerIDList.iterator();

            if (iter.hasNext()) {
                providerId = Integer.parseInt(iter.next().toString());
            }
        } catch (Exception e) {
            System.out.println("getProviderIDFromAppID: " + e);
        }finally{
            s.close();
        }
        return providerId;
    }
}
