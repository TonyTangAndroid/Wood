package com.tonytangandroid.gander.internal.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tonytangandroid.gander.internal.data.GanderDatabase;
import com.tonytangandroid.gander.internal.data.HttpTransaction;
import com.tonytangandroid.gander.internal.data.TransactionDao;

public class TransactionDetailViewModel extends AndroidViewModel {
    private final TransactionDao mTransactionDao;

    public TransactionDetailViewModel(Application application) {
        super(application);
        mTransactionDao = GanderDatabase.getInstance(application).httpTransactionDao();
    }

    public LiveData<HttpTransaction> getTransactionWithId(long id) {
        return mTransactionDao.getTransactionsWithId(id);
    }
}
