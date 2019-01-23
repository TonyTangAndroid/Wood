package com.tonytangandroid.wood.sample;

import android.app.Application;

import com.tonytangandroid.wood.WoodTree;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new WoodTree(this)
                .retainDataFor(WoodTree.Period.FOREVER)
                .maxLength(100000).showNotification(true));

    }

}
