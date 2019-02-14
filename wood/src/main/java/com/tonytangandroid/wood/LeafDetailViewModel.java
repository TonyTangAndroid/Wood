package com.tonytangandroid.wood;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class LeafDetailViewModel extends AndroidViewModel {
    private final LeafDao mLeafDao;

    public LeafDetailViewModel(Application application) {
        super(application);
        mLeafDao = WoodDatabase.getInstance(application).leafDao();
    }

    public LiveData<Leaf> getTransactionWithId(long id) {
        return mLeafDao.getTransactionsWithId(id);
    }
}
