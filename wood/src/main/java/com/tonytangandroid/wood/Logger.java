package com.tonytangandroid.wood;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("LogNotTimber")
class Logger {
  private static final String LOG_TAG = "WoodTree";

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
