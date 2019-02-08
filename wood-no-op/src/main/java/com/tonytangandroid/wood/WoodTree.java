package com.tonytangandroid.wood;

import android.content.Context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class WoodTree extends Timber.Tree {

    public WoodTree(Context context) {
    }


    public WoodTree showNotification(boolean sticky) {
        return this;
    }


    public WoodTree retainDataFor(Period period) {
        return this;
    }

    public WoodTree maxLength(int max) {
        return this;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t) {

    }


    public enum Period {
        ONE_HOUR,
        ONE_DAY,
        ONE_WEEK,
        FOREVER
    }
}
