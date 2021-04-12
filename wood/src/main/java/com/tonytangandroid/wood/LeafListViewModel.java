package com.tonytangandroid.wood;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import timber.log.Timber;


public class LeafListViewModel extends AndroidViewModel {
    private final static String TAG = "LeafListViewModel";

    private final static PagedList.Config config
            = new PagedList.Config.Builder()
            .setPageSize(15) // page size
            .setInitialLoadSizeHint(30)// items to fetch on first load
            .setPrefetchDistance(10)// trigger when to fetch a page
            .setEnablePlaceholders(true)
            .build();
    private final LeafDao mLeafDao;
    private final LiveData<PagedList<Leaf>> mTransactions;

    public LeafListViewModel(Application application) {
        super(application);
        mLeafDao = WoodDatabase.getInstance(application).leafDao();
        DataSource.Factory<Integer, Leaf> factory = mLeafDao.getPagedTransactions();
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

    BuildAsText getShareTransactions(){
        return new BuildAsText(mLeafDao);
    }

    public static class BuildAsText extends AsyncTask<Void, Void, CharSequence> {
        private final LeafDao leafDao;
        private Callback<CharSequence> callback;

        public BuildAsText(LeafDao leafDao) {
            this.leafDao = leafDao;
        }

        public void execute(Callback<CharSequence> callback){
            this.callback = callback;
            execute();
        }

        @Override
        protected CharSequence doInBackground(Void... v) {
            Timber.tag(TAG).d("Getting records");

            final SpannableStringBuilder sb = new SpannableStringBuilder();

            try (Cursor cursor = leafDao.getAllTransactions()) {
                Leaf leaf = new Leaf();
                if (cursor.isAfterLast()) return "No data";
                int cCreateAt = cursor.getColumnIndex("createAt");
                int cTag = cursor.getColumnIndex("tag");
                int cPriority = cursor.getColumnIndex("priority");
                int cBody = cursor.getColumnIndex("body");
                while (cursor.moveToNext()) {
                    leaf.setCreateAt(cursor.getLong(cCreateAt));
                    leaf.setTag(cursor.getString(cTag));
                    leaf.setPriority(cursor.getInt(cPriority));
                    leaf.setBody(cursor.getString(cBody));

                    sb.append(FormatUtils.getShareTextFull(leaf));
                    sb.append("\n");
                }
            }

            return sb;
        }

        @Override
        protected void onPostExecute(CharSequence charSequence) {
            super.onPostExecute(charSequence);
            if (callback != null) {
                callback.onEmit(charSequence);
            }
        }
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
