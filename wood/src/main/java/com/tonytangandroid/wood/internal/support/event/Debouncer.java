package com.tonytangandroid.wood.internal.support.event;


import android.os.Handler;
import android.support.annotation.NonNull;


public class Debouncer<V> {

    private final int mInterval;
    private final Callback<V> mCallback;
    private final Handler mHandler;

    public Debouncer(int intervalInMills, @NonNull Callback<V> callback) {
        mInterval = intervalInMills;
        mCallback = callback;
        mHandler = new Handler();
    }

    public void consume(V event) {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Counter<>(event, mCallback), mInterval);
    }

    public static class Counter<T> implements Runnable {
        private final T mEvent;
        private final Callback<T> mCallback;

        Counter(T event, Callback<T> callback) {
            mEvent = event;
            mCallback = callback;
        }

        @Override
        public void run() {
            mCallback.onEmit(mEvent);
        }
    }
}
