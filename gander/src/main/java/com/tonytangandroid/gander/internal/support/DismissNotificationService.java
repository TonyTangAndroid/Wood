package com.tonytangandroid.gander.internal.support;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class DismissNotificationService extends IntentService {

    public DismissNotificationService() {
        super("Gander-DismissNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.dismiss();
    }
}