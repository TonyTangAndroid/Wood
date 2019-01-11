package com.ashokvarma.gander.internal.ui.list;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            holder.host.setText(getHighlightedText(transaction.getMethod()));
            holder.start.setText(transaction.getRequestDate().toString());
            holder.ssl.setVisibility(View.GONE);
            holder.code.setText(getHighlightedText(String.valueOf(transaction.getPriority())));
            holder.duration.setText(getHighlightedText(String.valueOf(transaction.getRequestBody())));
            holder.host.setText(getHighlightedText(String.valueOf(transaction.getError())));
            holder.size.setText(String.valueOf(transaction.getRequestContentLength()));
            int color = mColorUtil.getTransactionColor(transaction, true);
            holder.path.setTextColor(color);
            holder.code.setTextColor(color);
        }
        // null no changes

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
        final View view;
        final TextView code;
        final TextView path;
        final TextView host;
        final TextView start;
        final TextView duration;
        final TextView size;
        final ImageView ssl;

        TransactionViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
            code = view.findViewById(R.id.gander_list_code);
            path = view.findViewById(R.id.gander_list_path);
            host = view.findViewById(R.id.gander_list_host);
            start = view.findViewById(R.id.gander_list_start);
            duration = view.findViewById(R.id.gander_list_duration);
            size = view.findViewById(R.id.gander_list_size);
            ssl = view.findViewById(R.id.gander_list_ssl);

            view.setOnClickListener(new View.OnClickListener() {
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
