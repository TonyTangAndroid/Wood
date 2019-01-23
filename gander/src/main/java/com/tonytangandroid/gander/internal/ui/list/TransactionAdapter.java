package com.tonytangandroid.gander.internal.ui.list;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonytangandroid.gander.R;
import com.tonytangandroid.gander.internal.data.HttpTransaction;

import javax.inject.Provider;

class TransactionAdapter extends PagedListAdapter<HttpTransaction, TransactionViewHolder> implements Provider<String> {

    private final Context context;
    private final Listener listener;
    private final LayoutInflater layoutInflater;

    private String searchKey;

    TransactionAdapter(Context context, ListDiffUtil listDiffUtil, Listener listener) {
        super(listDiffUtil);
        this.context = context;
        this.listener = listener;
        layoutInflater = LayoutInflater.from(this.context);
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                // in the database inserts only occur at the top
                TransactionAdapter.this.listener.onItemsInserted(positionStart);
            }
        });
    }


    TransactionAdapter setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        return this;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.gander_list_item_transaction, parent, false);
        return new TransactionViewHolder(itemView, context, this, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder viewHolder, int position) {
        HttpTransaction transaction = getItem(position);
        if (transaction != null) {
            viewHolder.bind(transaction);
        }
    }

    @Override
    public String get() {
        return searchKey;
    }

    interface Listener {
        void onTransactionClicked(HttpTransaction httpTransaction);

        void onItemsInserted(int firstInsertedItemPosition);
    }


}
