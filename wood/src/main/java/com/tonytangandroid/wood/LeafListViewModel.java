package com.tonytangandroid.wood;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import android.os.AsyncTask;


public class LeafListViewModel extends AndroidViewModel {
    private final static PagedList.Config config
            = new PagedList.Config.Builder()
            .setPageSize(15) // page size
            .setInitialLoadSizeHint(30)// items to fetch on first load
            .setPrefetchDistance(10)// trigger when to fetch a page
            .setEnablePlaceholders(true)
            .build();
    private final LeafDao mLeafDao;
    private LiveData<PagedList<Leaf>> mTransactions;

    public LeafListViewModel(Application application) {
        super(application);
        mLeafDao = WoodDatabase.getInstance(application).leafDao();
        DataSource.Factory<Integer, Leaf> factory = mLeafDao.getAllTransactions();
        mTransactions = new LivePagedListBuilder<>(factory, config).build();
    }

    LiveData<PagedList<Leaf>> getTransactions(String key) {
        if (key == null || key.trim().length() == 0) {
            return mTransactions;
        } else {
            DataSource.Factory<Integer, Leaf> factory = mLeafDao.getAllTransactionsWith(key, LeafDao.SEARCH_DEFAULT);
            return new LivePagedListBuilder<>(factory, config).build();
        }
    }

    public void deleteItem(Leaf transaction) {
        new deleteAsyncTask(mLeafDao).execute(transaction);
    }

    void clearAll() {
        new clearAsyncTask(mLeafDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Leaf, Void, Integer> {

        private final LeafDao leafDao;

        deleteAsyncTask(LeafDao leafDao) {
            this.leafDao = leafDao;
        }

        @Override
        protected Integer doInBackground(final Leaf... params) {
            return leafDao.deleteTransactions(params);
        }

    }

    private static class clearAsyncTask extends AsyncTask<Leaf, Void, Integer> {

        private final LeafDao leafDao;

        clearAsyncTask(LeafDao leafDao) {
            this.leafDao = leafDao;
        }

        @Override
        protected Integer doInBackground(final Leaf... params) {
            return leafDao.clearAll();
        }

    }
}
