package com.tonytangandroid.wood;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class DismissNotificationService extends IntentService {

    public DismissNotificationService() {
        super("Wood-DismissNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.dismiss();
    }
}