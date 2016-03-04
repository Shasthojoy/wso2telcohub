/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.key;

import com.dialog.acr.aes.util.RandomString;
import com.dialog.acr.aes.util.ShuffleString;

/**
 *
 * @author Shashika Wijayasekera
 */
public class AppSaltKey {

    final public static String getAppSaltKey() throws Exception {

        final String characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String appSaltKey = null;

        //System.out.println("Salt Key Generation \n");
        appSaltKey = RandomString.getRandomString(16, 0, 61, true, true, characterSet);
        /*System.out.println("Salt Key: " + appSaltKey);
        System.out.println("Salt Key Length: " + appSaltKey.length());
        System.out.println("\n");*/

        return appSaltKey;
    }

    final public static String getAppSaltKey(String appID) throws Exception {

        final String characterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String appSaltKey = null;

        int appIDLength = appID.length();
        int requiredStringLength = 16 - appIDLength;

        //System.out.println("Salt Key Generation \n");
        String randomString = RandomString.getRandomString(requiredStringLength, 0, 61, true, true, characterSet);
        //System.out.println("Random String: " + randomString);
        //System.out.println("Random String Length: " + randomString.length());
        String appSaltKeyText = appID + randomString;
        //System.out.println("App Salt Key Text: " + appSaltKeyText);
        //System.out.println("App Salt Key Text Length: " + appSaltKeyText.length());
        appSaltKey = ShuffleString.getShuffleString(appSaltKeyText);
        //System.out.println("Salt Key: " + appSaltKey);
        //System.out.println("Salt Key Length: " + appSaltKey.length());
        //System.out.println("\n");

        return appSaltKey;
    }
}
