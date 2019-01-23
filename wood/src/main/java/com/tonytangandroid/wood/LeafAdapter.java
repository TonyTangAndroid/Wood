package com.tonytangandroid.wood;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Provider;

class LeafAdapter extends PagedListAdapter<Leaf, LeafViewHolder> implements Provider<String> {

    private final Context context;
    private final Listener listener;
    private final LayoutInflater layoutInflater;

    private String searchKey;

    LeafAdapter(Context context, ListDiffUtil listDiffUtil, Listener listener) {
        super(listDiffUtil);
        this.context = context;
        this.listener = listener;
        layoutInflater = LayoutInflater.from(this.context);
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                // in the database inserts only occur at the top
                LeafAdapter.this.listener.onItemsInserted(positionStart);
            }
        });
    }


    LeafAdapter setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        return this;
    }


    @NonNull
    @Override
    public LeafViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.wood_list_item_transaction, parent, false);
        return new LeafViewHolder(itemView, context, this, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LeafViewHolder viewHolder, int position) {
        Leaf transaction = getItem(position);
        if (transaction != null) {
            viewHolder.bind(transaction);
        }
    }

    @Override
    public String get() {
        return searchKey;
    }

    interface Listener {
        void onTransactionClicked(Leaf leaf);

        void onItemsInserted(int firstInsertedItemPosition);
    }


}
