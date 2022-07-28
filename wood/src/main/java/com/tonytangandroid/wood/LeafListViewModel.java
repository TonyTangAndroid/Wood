package com.tonytangandroid.wood;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import android.os.AsyncTask;

@SuppressWarnings("deprecation")
public class LeafListViewModel extends AndroidViewModel {
    private final static PagedList.Config config
            = new PagedList.Config.Builder()
            .setPageSize(15) // page size
            .setInitialLoadSizeHint(30)// items to fetch on first load
            .setPrefetchDistance(10)// trigger when to fetch a page
            .setEnablePlaceholders(true)
            .build();
    private final LeafDao leafDao;
    private final LiveData<PagedList<Leaf>> transactions;

    public LeafListViewModel(Application application) {
        super(application);
        leafDao = WoodDatabase.getInstance(application).leafDao();
        transactions = new LivePagedListBuilder<>(leafDao.getAllTransactions(), config).build();
    }

    LiveData<PagedList<Leaf>> getTransactions(String key) {
        if (key == null || key.trim().length() == 0) {
            return transactions;
        } else {
            DataSource.Factory<Integer, Leaf> factory = leafDao.getAllTransactionsWith(key, LeafDao.SEARCH_DEFAULT);
            return new LivePagedListBuilder<>(factory, config).build();
        }
    }

    public void deleteItem(Leaf transaction) {
        new DeleteAsyncTask(leafDao).execute(transaction);
    }

    void clearAll() {
        new ClearAsyncTask(leafDao).execute();
    }

    private static class DeleteAsyncTask extends AsyncTask<Leaf, Void, Integer> {

        private final LeafDao leafDao;

        DeleteAsyncTask(LeafDao leafDao) {
            this.leafDao = leafDao;
        }

        @Override
        protected Integer doInBackground(final Leaf... params) {
            return leafDao.deleteTransactions(params);
        }

    }

    private static class ClearAsyncTask extends AsyncTask<Leaf, Void, Integer> {

        private final LeafDao leafDao;

        ClearAsyncTask(LeafDao leafDao) {
            this.leafDao = leafDao;
        }

        @Override
        protected Integer doInBackground(final Leaf... params) {
            return leafDao.clearAll();
        }

    }
}
