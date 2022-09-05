package com.tonytangandroid.wood.sample;

import static com.tonytangandroid.wood.sample.HomeActivity.logInBackground;

import android.app.Application;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    WoodIntegrationUtil.initWood(this);
    logInBackground();
  }
}
