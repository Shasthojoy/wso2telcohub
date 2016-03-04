/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.encrypt;

import com.dialog.acr.aes.key.AppSecretKey;
import com.dialog.acr.aes.util.CurrentSystemDateTime;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Shashika Wijayasekera
 *
 * Every implementation of the Java platform is required to support the
 * following standard Cipher transformations with the keysizes in parentheses:
 * AES/CBC/NoPadding (128) AES/CBC/PKCS5Padding (128) AES/ECB/NoPadding (128)
 * AES/ECB/PKCS5Padding (128) DES/CBC/NoPadding (56) DES/CBC/PKCS5Padding (56)
 * DES/ECB/NoPadding (56) DES/ECB/PKCS5Padding (56) DESede/CBC/NoPadding (168)
 * DESede/CBC/PKCS5Padding (168) DESede/ECB/NoPadding (168)
 * DESede/ECB/PKCS5Padding (168) RSA/ECB/PKCS1Padding (1024, 2048)
 * RSA/ECB/OAEPWithSHA-1AndMGF1Padding (1024, 2048)
 * RSA/ECB/OAEPWithSHA-256AndMGF1Padding (1024, 2048)
 */
public class Encrypt {

    final public static String acrEncrypt(String appID, String serviceProviderID, String msisdn, String appSaltKey) throws Exception {

        final String cipherType = "AES/ECB/PKCS5Padding";
        String encryptedACR = null;

        /*System.out.println("Start Encryption \n");
        System.out.println("App ID: " + appID);
        System.out.println("App ID Length: " + appID.length());
        System.out.println("Service Provider ID: " + serviceProviderID);
        System.out.println("Service Provider ID Length: " + serviceProviderID.length());
        System.out.println("MSISDN: " + msisdn);
        System.out.println("MSISDN Length: " + msisdn.length());
        System.out.println("Salt Key: " + appSaltKey);
        System.out.println("Salt Key Length: " + appSaltKey.length());*/

        String currentSystemDateTime = CurrentSystemDateTime.getCurrentSystemDateTime();
        String acr = appID + "|" + serviceProviderID + "|" + msisdn + "|" + currentSystemDateTime;

        /*System.out.println("\nACR String: " + acr);
        System.out.println("ACR String Length: " + acr.length() + "\n");*/

        SecretKeySpec appSecretKey = AppSecretKey.createAppSecretKey(appSaltKey);

        Cipher cipher = Cipher.getInstance(cipherType);

        cipher.init(Cipher.ENCRYPT_MODE, appSecretKey);
        encryptedACR = Base64.encodeBase64String(cipher.doFinal(acr.getBytes()));

        /*System.out.println("Encrypted ACR: " + encryptedACR.toString());
        System.out.println("Encrypted ACR Length: " + encryptedACR.length());
        System.out.println("\n");*/

        return encryptedACR;
    }
}
