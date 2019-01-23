package com.tonytangandroid.wood.sample;

import android.app.Application;

import com.tonytangandroid.wood.WoodInterceptor;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new WoodInterceptor(this)
                .retainDataFor(WoodInterceptor.Period.FOREVER)
                .maxContentLength(100000).showNotification(true));

    }

}
