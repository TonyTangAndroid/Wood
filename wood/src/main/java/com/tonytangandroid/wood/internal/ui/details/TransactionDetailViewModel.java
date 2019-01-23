package com.tonytangandroid.wood.internal.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tonytangandroid.wood.internal.data.WoodDatabase;
import com.tonytangandroid.wood.internal.data.HttpTransaction;
import com.tonytangandroid.wood.internal.data.TransactionDao;

public class TransactionDetailViewModel extends AndroidViewModel {
    private final TransactionDao mTransactionDao;

    public TransactionDetailViewModel(Application application) {
        super(application);
        mTransactionDao = WoodDatabase.getInstance(application).httpTransactionDao();
    }

    public LiveData<HttpTransaction> getTransactionWithId(long id) {
        return mTransactionDao.getTransactionsWithId(id);
    }
}
