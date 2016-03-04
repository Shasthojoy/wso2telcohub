/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Shashika Wijayasekera
 */
public class CurrentSystemDateTime {

    public static String getCurrentSystemDateTime() {

        String currentSystemDateTime = null;

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date objDate = new Date();
            currentSystemDateTime = dateFormat.format(objDate).toString();
            //System.out.println("Current System Date and Time: " + currentSystemDateTime);
        } catch (Exception e) {

            System.out.println("getCurrentSystemDateTime: " + e);
        }

        return currentSystemDateTime;
    }
    
    public static String getCurrentSystemDateTimeAsSingleString() {

        String currentSystemDateTime = null;

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date objDate = new Date();
            currentSystemDateTime = dateFormat.format(objDate).toString().replace(" ", "").replace("-", "").replace(":", "").replace(".", "");
            System.out.println("Current System Date and Time as Single String: " + currentSystemDateTime);
        } catch (Exception e) {

            System.out.println("getCurrentSystemDateTime: " + e);
        }

        return currentSystemDateTime;
    }
}
