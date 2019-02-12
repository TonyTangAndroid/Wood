package com.tonytangandroid.wood.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

public class GeneratingLogActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_generating_log);

    }

    @Override
    protected void onResume() {
        super.onResume();
        generate();
    }

    private void generate() {
        handler.postDelayed(this::log, 1000);
    }

    private void log() {
        for (int i = 0; i < 10; i++) {
            count++;
            Timber.v("generate log :%s", count);
        }
        generate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stop();
    }

    private void stop() {
        handler.removeCallbacksAndMessages(null);
    }


}