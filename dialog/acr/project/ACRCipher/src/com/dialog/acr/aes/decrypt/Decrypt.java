/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.decrypt;

import com.dialog.acr.aes.key.AppSecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

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
public class Decrypt {

    final public static String acrDecrypt(String encryptedACR, String appSaltKey) throws Exception {

        final String cipherType = "AES/ECB/PKCS5PADDING";
        String decryptedACR = null;

        /*System.out.println("Start Decryption \n");
        System.out.println("Salt Key: " + appSaltKey);
        System.out.println("Salt Key Length: " + appSaltKey.length());
        System.out.println("Encrypted ACR: " + encryptedACR);
        System.out.println("Encrypted ACR Length: " + encryptedACR.length());
        System.out.println("\n");*/
        SecretKeySpec appSecretKey = AppSecretKey.createAppSecretKey(appSaltKey);

        Cipher cipher = Cipher.getInstance(cipherType);

        cipher.init(Cipher.DECRYPT_MODE, appSecretKey);
        decryptedACR = new String(cipher.doFinal(Base64.decodeBase64(encryptedACR)));

        /*System.out.println("Decrypted ACR: " + decryptedACR.toString());
        System.out.println("Decrypted ACR Length: " + decryptedACR.length());
        System.out.println("\n");*/

        return decryptedACR;
    }
}
