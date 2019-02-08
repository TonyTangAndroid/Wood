package com.tonytangandroid.wood;

import android.content.Context;

public class WoodTree {

    public WoodTree(Context context) {
    }


    public WoodTree showNotification(boolean sticky) {
        return this;
    }


    public WoodTree retainDataFor(Period period) {
        return this;
    }


    public WoodTree maxContentLength(long max) {
        return this;
    }

    public WoodTree redactHeader(String name) {
        return this;
    }


    public enum Period {
        ONE_HOUR,
        ONE_DAY,
        ONE_WEEK,
        FOREVER
    }
}
