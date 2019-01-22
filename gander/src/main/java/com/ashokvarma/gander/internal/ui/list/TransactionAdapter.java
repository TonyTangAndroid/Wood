package com.ashokvarma.gander.internal.ui.list;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashokvarma.gander.R;
import com.ashokvarma.gander.internal.data.HttpTransaction;
import com.ashokvarma.gander.internal.support.FormatUtils;
import com.ashokvarma.gander.internal.support.GanderColorUtil;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 03/06/18
 */
public class TransactionAdapter extends PagedListAdapter<HttpTransaction, RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int TRANSACTION_VIEW = 2;
    private final LayoutInflater mLayoutInflater;
    private final GanderColorUtil mColorUtil;
    private Listener mListener;
    private String mSearchKey;

    TransactionAdapter(Context context, ListDiffUtil listDiffUtil) {
        super(listDiffUtil);

        mLayoutInflater = LayoutInflater.from(context);
        mColorUtil = GanderColorUtil.getInstance(context);

        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                if (mListener != null) {
                    // in the database inserts only occur at the top
                    mListener.onItemsInserted(positionStart);
                }
            }
        });
    }

    TransactionAdapter setListener(Listener listener) {
        this.mListener = listener;
        return this;
    }

    TransactionAdapter setSearchKey(String searchKey) {
        this.mSearchKey = searchKey;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) == null) {
            return EMPTY_VIEW;
        } else {
            return TRANSACTION_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TRANSACTION_VIEW) {
            return new TransactionViewHolder(mLayoutInflater.inflate(R.layout.gander_list_item_transaction, parent, false));
        } else {
            //(viewType == EMPTY_VIEW)
            return new EmptyTransactionViewHolder(mLayoutInflater.inflate(R.layout.gander_list_item_empty_transaction, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder genericHolder, int position) {
        HttpTransaction transaction = getItem(position);
        if (transaction != null) {
            TransactionViewHolder holder = ((TransactionViewHolder) genericHolder);
            holder.tv_tag.setText(getHighlightedText(transaction.getTag()));
            holder.tv_time.setText(transaction.getDate().toString());
            holder.tv_body.setText(getHighlightedText(String.valueOf(transaction.body())));
            holder.tv_size.setText(String.valueOf(transaction.length()));
            int color = mColorUtil.getTransactionColor(transaction);
            holder.tv_tag.setTextColor(color);
        }
    }

    private CharSequence getHighlightedText(String text) {
        return FormatUtils.formatTextHighlight(text, mSearchKey);
    }

    interface Listener {
        void onTransactionClicked(HttpTransaction httpTransaction);

        void onItemsInserted(int firstInsertedItemPosition);
    }

    static class EmptyTransactionViewHolder extends RecyclerView.ViewHolder {
        EmptyTransactionViewHolder(View itemView) {
            super(itemView);
        }
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        final TextView tv_time;
        final TextView tv_tag;
        final TextView tv_size;
        final TextView tv_body;

        TransactionViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_tag = itemView.findViewById(R.id.tv_tag);
            tv_body = itemView.findViewById(R.id.tv_body);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        HttpTransaction transaction = getItem(getAdapterPosition());
                        mListener.onTransactionClicked(transaction);
                    }
                }
            });
        }
    }
}
