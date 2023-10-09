package com.mytrip.util;

public class ConversionUtils {
    /**
     * Return the boolean equivalent to a byte, boolean false for byte 0, otherwise boolean true
     * @param input
     * @return
     */
    public static boolean byteToBooleanConverter(final Byte input) {
        return input != null && input != 0;
    }
}
