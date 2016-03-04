/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.key;

import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Shashika Wijayasekera
 */
public class AppSecretKey {

    final public static SecretKeySpec createAppSecretKey(String appSaltKey) throws Exception {

        final String appSecretKeyType = "AES";
        SecretKeySpec appSecretKey = null;

        //System.out.println("Start App Secret Key Generation \n");
        appSecretKey = new SecretKeySpec(appSaltKey.getBytes(), appSecretKeyType);
        //System.out.println("App Secret Key: " + appSecretKey.getEncoded());
        //System.out.println("App Secret Key Length: " + appSecretKey.getEncoded().length);
        //System.out.println("\n");

        return appSecretKey;
    }
}
