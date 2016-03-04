/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Shashika Wijayasekera
 */
public class RandomString {

    public static String getRandomString(int stringLength, int stringFrom, int stringTo, boolean letters, boolean numbers, String characters) throws Exception {

        String randomString = null;

        /*System.out.println("Random String Generation");
        System.out.println("Required String Length: " + stringLength);
        System.out.println("From: " + stringFrom);
        System.out.println("TO: " + stringTo);
        System.out.println("Is Letters: " + letters);
        System.out.println("Is Numbers: " + numbers);
        System.out.println("Characters: " + characters);*/
        /*random(int count, int start, int end, boolean letters, boolean numbers, char... chars)
         Creates a random string based on a variety of options, using default source of randomness.*/
        randomString = RandomStringUtils.random(stringLength, stringFrom, stringTo, letters, numbers, characters.toCharArray());
        /*System.out.println("Random String: " + randomString);
        System.out.println("Random String Length: " + randomString.length());
        System.out.println("\n");*/

        return randomString;
    }
}
