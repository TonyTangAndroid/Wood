package com.tonytangandroid.wood;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class ClearTransactionsService extends IntentService {

    public ClearTransactionsService() {
        super("Wood-ClearTransactionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int deletedTransactionCount = WoodDatabase.getInstance(this).leafDao().clearAll();
        Logger.i(deletedTransactionCount + " transactions deleted");
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.dismiss();
    }
}