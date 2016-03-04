package com.aircel.ussd.util;

import java.util.Random;


public class NumberGenerator {

    private static int currentNo = 0;

    /**
     *
     */
    public NumberGenerator() {
    }

    /**
     *
     * @return
     */
    public static int next() {
        if (currentNo == Integer.MAX_VALUE) {
            currentNo = 0;
        }
        return currentNo++;
    }

    public static byte getDialogId() {
        byte[] tmpbit = new byte[1];
        new Random().nextBytes(tmpbit);
        return tmpbit[0];
    }
}
