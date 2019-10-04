package com.tonytangandroid.wood.sample;

import android.app.Application;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initWood(this);

    }

    private static void initWood(Application application) {
        timber.log.Timber.plant(new com.tonytangandroid.wood.WoodTree(application)
                .retainDataFor(com.tonytangandroid.wood.WoodTree.Period.FOREVER)
                .autoScroll(false)
                .maxLength(100000).showNotification(true));

    }

}
