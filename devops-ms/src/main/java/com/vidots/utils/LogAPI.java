package com.vidots.utils;

import java.util.Arrays;

public class LogAPI {

    private static boolean debug = true;

    public static void debug(Object... args) {
        if (debug) {
            System.out.println(Arrays.toString(args).replace("[", "").replace("]", ""));
        }
    }

    public static void println(Object... args) {
        System.out.println(Arrays.toString(args).replace("[", "").replace("]", ""));
    }

    public static void err(Object... args) {
        System.err.println(Arrays.toString(args).replace("[", "").replace("]", ""));
    }

    public static void setDebug(boolean debug) {
        LogAPI.debug = debug;
    }
}
