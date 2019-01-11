package com.ashokvarma.gander.sample;

import android.app.Application;

import com.ashokvarma.gander.GanderInterceptor;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new GanderInterceptor(this).showNotification(true));

    }

}
