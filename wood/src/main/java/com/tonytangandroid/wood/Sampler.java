package com.tonytangandroid.wood;


import android.os.Handler;
import androidx.annotation.NonNull;

class Sampler<V> {

    private final int interval;
    private final Callback<V> callback;
    private final Handler handler;

    private Counter<V> currentRunnable;

    public Sampler(int intervalInMills, @NonNull Callback<V> callback) {
        interval = intervalInMills;
        this.callback = callback;
        handler = new Handler();
    }

    public void consume(V event) {
        if (currentRunnable == null) {
            // first runnable
            currentRunnable = new Counter<>(event, callback);
            handler.postDelayed(currentRunnable, interval);
        } else {
            if (currentRunnable.state == Counter.STATE_CREATED || currentRunnable.state == Counter.STATE_QUEUED) {
                //  yet to emit (with in an interval)
                currentRunnable.updateEvent(event);
            } else if (currentRunnable.state == Counter.STATE_RUNNING || currentRunnable.state == Counter.STATE_FINISHED) {
                // interval finished. open new batch
                currentRunnable = new Counter<>(event, callback);
                handler.postDelayed(currentRunnable, interval);
            }
        }
    }

    public static class Counter<T> implements Runnable {
        static final int STATE_CREATED = 1;
        static final int STATE_QUEUED = 2;
        static final int STATE_RUNNING = 3;
        static final int STATE_FINISHED = 4;
        private final Callback<T> mCallback;
        int state;
        private T mEvent;

        Counter(T event, Callback<T> callback) {
            mEvent = event;
            mCallback = callback;
            state = STATE_CREATED;
        }

        void updateEvent(T deliverable) {
            this.mEvent = deliverable;
        }

        @Override
        public void run() {
            state = STATE_RUNNING;
            mCallback.onEmit(mEvent);
            state = STATE_FINISHED;
        }
    }


}
