package com.tonytangandroid.wood;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;


class ListDiffUtil extends DiffUtil.ItemCallback<Leaf> {

    private String mSearchKey;

    private static boolean areEqual(@Nullable Object oldItem, @Nullable Object newItem) {
        if (oldItem == null && newItem == null) {
            // both are null
            return true;
        } else if (oldItem == null || newItem == null) {
            // only one is null => return false
            return false;
        }
        return oldItem.equals(newItem);
    }

    void setSearchKey(String searchKey) {
        this.mSearchKey = searchKey;
    }

    @Override
    public boolean areItemsTheSame(@NonNull Leaf oldItem, @NonNull Leaf newItem) {
        // might not work always due to async nature of Adapter fails in very rare race conditions but increases pref.
        newItem.searchKey = mSearchKey;
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Leaf oldItem, @NonNull Leaf newItem) {
        // both will non null. because of areItemsTheSame logic only non nulls come here
        // comparing only items shown in the list
        return areEqual(oldItem.searchKey, newItem.searchKey);
    }
}
