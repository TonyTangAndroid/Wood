package com.tonytangandroid.wood.internal.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tonytangandroid.wood.R;
import com.tonytangandroid.wood.internal.data.HttpTransaction;
import com.tonytangandroid.wood.internal.support.FormatUtils;
import com.tonytangandroid.wood.internal.support.WoodColorUtil;

import javax.inject.Provider;

class TransactionViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final Provider<String> searchKey;
    private final TransactionAdapter.Listener listener;


    private final TextView tv_time;
    private final TextView tv_tag;
    private final TextView tv_size;
    private final TextView tv_body;

    TransactionViewHolder(View itemView,
                          Context context,
                          Provider<String> searchKey,
                          TransactionAdapter.Listener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.searchKey = searchKey;
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_size = itemView.findViewById(R.id.tv_size);
        tv_tag = itemView.findViewById(R.id.tv_tag);
        tv_body = itemView.findViewById(R.id.tv_body);
    }


    void bind(HttpTransaction transaction) {
        tv_tag.setText(transaction.getTag());
        tv_time.setText(transaction.getDate().toString());
        tv_body.setText(getHighlightedText(String.valueOf(transaction.body())));
        tv_size.setText(String.valueOf(transaction.length()));
        tv_tag.setTextColor(WoodColorUtil.getInstance(context).getTransactionColor(transaction));
        itemView.setOnClickListener(v -> listener.onTransactionClicked(transaction));
    }

    private CharSequence getHighlightedText(String text) {
        return FormatUtils.formatTextHighlight(text, searchKey.get());
    }

}
