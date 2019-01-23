package com.tonytangandroid.gander.internal.support;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tonytangandroid.gander.internal.data.GanderDatabase;

public class ClearTransactionsService extends IntentService {

    public ClearTransactionsService() {
        super("Gander-ClearTransactionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int deletedTransactionCount = GanderDatabase.getInstance(this).httpTransactionDao().clearAll();
        Logger.i(deletedTransactionCount + " transactions deleted");
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.dismiss();
    }
}