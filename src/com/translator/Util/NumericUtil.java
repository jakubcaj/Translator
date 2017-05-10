package com.translator.Util;

/**
 * Created by Kuba on 09.05.2017.
 */
public class NumericUtil {

    public static float getFloat(String value) {
        return Float.valueOf(value.substring(1, value.length()));
    }

    public static int getInt(String value) {
        return Integer.valueOf(value.substring(1,value.length()));
    }
}
