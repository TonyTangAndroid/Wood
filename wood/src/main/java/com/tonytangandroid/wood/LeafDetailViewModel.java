package com.tonytangandroid.wood;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Flowable;

public class LeafDetailViewModel extends AndroidViewModel {
    private final LeafDao leafDao;

    public LeafDetailViewModel(Application application) {
        super(application);
        leafDao = WoodDatabase.getInstance(application).leafDao();
    }

    public Flowable<Leaf> getTransactionWithId(long id) {
        return leafDao.getTransactionsWithId(id);
    }
}
