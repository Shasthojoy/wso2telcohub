/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dialog.acr.aes.util;

/**
 *
 * @author Shashika Wijayasekera
 */
public class SplitString {

    public static String[] getSplittedString(String fullString) throws Exception {

        String[] splittedString = null;

        /*System.out.println("\nStart String splitting");
        System.out.println("String before split: " + fullString);
        System.out.println("String Length before split: " + fullString.length());*/
        splittedString = fullString.split("\\|");
        /*System.out.println("Splitted string array size: " + splittedString.length);
        System.out.println("\n");*/

        return splittedString;
    }
}
