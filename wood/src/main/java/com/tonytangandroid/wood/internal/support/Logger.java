package com.tonytangandroid.wood.internal.support;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("LogNotTimber")
public class Logger {
    private static final String LOG_TAG = "WoodInterceptor";

    public static void i(String message) {
        Log.i(LOG_TAG, message);
    }

    public static void w(String message) {
        Log.w(LOG_TAG, message);
    }

    public static void e(String message, Exception e) {
        Log.e(LOG_TAG, message, e);
    }
}
