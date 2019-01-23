package com.tonytangandroid.wood.internal.support;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tonytangandroid.wood.internal.data.WoodDatabase;

public class ClearTransactionsService extends IntentService {

    public ClearTransactionsService() {
        super("Wood-ClearTransactionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int deletedTransactionCount = WoodDatabase.getInstance(this).httpTransactionDao().clearAll();
        Logger.i(deletedTransactionCount + " transactions deleted");
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.dismiss();
    }
}