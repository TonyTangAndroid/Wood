package com.tonytangandroid.gander.internal.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tonytangandroid.gander.R;
import com.tonytangandroid.gander.internal.data.HttpTransaction;
import com.tonytangandroid.gander.internal.support.NotificationHelper;
import com.tonytangandroid.gander.internal.support.event.Callback;
import com.tonytangandroid.gander.internal.support.event.Debouncer;
import com.tonytangandroid.gander.internal.support.event.Sampler;
import com.tonytangandroid.gander.internal.ui.details.TransactionDetailsActivity;

public class TransactionListActivity extends AppCompatActivity implements TransactionAdapter.Listener, SearchView.OnQueryTextListener {

    private TransactionAdapter adapter;
    private ListDiffUtil listDiffUtil;
    private RecyclerView recyclerView;
    private TransactionListViewModel viewModel;
    private LiveData<PagedList<HttpTransaction>> currentSubscription;

    // 100 mills delay. batch all changes in 100 mills and emit last item at the end of 100 mills
    private Sampler<TransactionListWithSearchKeyModel> transactionSampler = new Sampler<>(100, new Callback<TransactionListWithSearchKeyModel>() {
        @Override
        public void onEmit(TransactionListWithSearchKeyModel event) {
            listDiffUtil.setSearchKey(event.searchKey);
            adapter.setSearchKey(event.searchKey).submitList(event.pagedList);
        }
    });

    // 300 mills delay min. Max no limit
    private Debouncer<String> searchDebouncer = new Debouncer<>(300, new Callback<String>() {
        @Override
        public void onEmit(String event) {
            loadResults(event, viewModel.getTransactions(event));
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gander_act_transaction_list);
        Toolbar toolbar = findViewById(R.id.gander_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getApplicationName());

        recyclerView = findViewById(R.id.gander_transaction_list);
        listDiffUtil = new ListDiffUtil();
        adapter = new TransactionAdapter(this, listDiffUtil, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel.class);
        loadResults(null, viewModel.getTransactions(null));
    }

    private void loadResults(final String searchKey, LiveData<PagedList<HttpTransaction>> pagedListLiveData) {
        if (currentSubscription != null && currentSubscription.hasObservers()) {
            currentSubscription.removeObservers(this);
        }
        currentSubscription = pagedListLiveData;
        currentSubscription.observe(this, list -> consume(list, searchKey));
    }

    private void consume(@Nullable PagedList<HttpTransaction> transactionPagedList, String searchKey) {
        transactionSampler.consume(new TransactionListWithSearchKeyModel(searchKey, transactionPagedList));
    }

    @Override
    public void onTransactionClicked(HttpTransaction transaction) {
        TransactionDetailsActivity.start(this, transaction.getId(), transaction.getPriority());
    }

    @Override
    public void onItemsInserted(int firstInsertedItemPosition) {
        recyclerView.smoothScrollToPosition(firstInsertedItemPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gander_list_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear) {
            viewModel.clearAll();
            NotificationHelper.clearBuffer();
            return true;
        } else if (item.getItemId() == R.id.browse_sql) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchDebouncer.consume(newText);
        return true;
    }

    private String getApplicationName() {
        ApplicationInfo applicationInfo = getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getString(stringId);
    }

    static class TransactionListWithSearchKeyModel {
        final String searchKey;
        final PagedList<HttpTransaction> pagedList;

        TransactionListWithSearchKeyModel(String searchKey, PagedList<HttpTransaction> pagedList) {
            this.searchKey = searchKey;
            this.pagedList = pagedList;
        }
    }
}
