/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shashika Wijayasekera
 */
public class ShuffleString {

    public static String getShuffleString(String appSaltKeyText) throws Exception {

        String shuffledString = null;

        /*System.out.println("Shuffling the String");
        System.out.println("String before shuffle: " + appSaltKeyText);
        System.out.println("String Length before shuffle: " + appSaltKeyText.length());*/

        List<Character> characters = new ArrayList<Character>();
        for (char c : appSaltKeyText.toCharArray()) {

            characters.add(c);
        }

        StringBuilder shuffledAppSaltKeyText = new StringBuilder(appSaltKeyText.length());
        while (characters.size() != 0) {

            int randPicker = (int) (Math.random() * characters.size());
            shuffledAppSaltKeyText.append(characters.remove(randPicker));
        }
        //System.out.println("String after shuffle: " + shuffledAppSaltKeyText.toString());
        //System.out.println("String Length after shuffle: " + shuffledAppSaltKeyText.toString().length());
        shuffledString = shuffledAppSaltKeyText.toString();

        return shuffledString;
    }
}
