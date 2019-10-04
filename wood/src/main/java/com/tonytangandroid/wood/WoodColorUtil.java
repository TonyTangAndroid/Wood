package com.tonytangandroid.wood;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.util.Log;


class WoodColorUtil {

    public static final int SEARCHED_HIGHLIGHT_BACKGROUND_COLOR = Color.parseColor("#FD953F");
    public static final int HIGHLIGHT_BACKGROUND_COLOR = Color.parseColor("#FFFD38");
    public static final int HIGHLIGHT_TEXT_COLOR = 0;
    public static final boolean HIGHLIGHT_UNDERLINE = false;
    private static WoodColorUtil TRANSACTION_COLOR_UTIL_INSTANCE;
    private final int mColorDefault;
    private final int mColorVerbose;
    private final int mColorError;
    private final int mColorAssert;
    private final int mColorInfo;
    private final int mColorWarning;
    private final int mColorDebug;

    private WoodColorUtil(Context context) {
        mColorDefault = ContextCompat.getColor(context, R.color.wood_status_default);
        mColorVerbose = ContextCompat.getColor(context, R.color.wood_log_verbose);
        mColorDebug = ContextCompat.getColor(context, R.color.wood_log_debug);
        mColorInfo = ContextCompat.getColor(context, R.color.wood_log_info);
        mColorWarning = ContextCompat.getColor(context, R.color.wood_log_warning);
        mColorError = ContextCompat.getColor(context, R.color.wood_log_error);
        mColorAssert = ContextCompat.getColor(context, R.color.wood_log_assert);
    }

    public static WoodColorUtil getInstance(Context context) {
        if (TRANSACTION_COLOR_UTIL_INSTANCE == null) {
            TRANSACTION_COLOR_UTIL_INSTANCE = new WoodColorUtil(context);
        }
        return TRANSACTION_COLOR_UTIL_INSTANCE;
    }

    public int getTransactionColor(Leaf transaction) {
        return getTransactionColor(transaction.getPriority());
    }


    public int getTransactionColor(int priority) {
        if (priority == Log.VERBOSE) {
            return mColorVerbose;
        } else if (priority == Log.DEBUG) {
            return mColorDebug;
        } else if (priority == Log.INFO) {
            return mColorInfo;
        } else if (priority == Log.WARN) {
            return mColorWarning;
        } else if (priority == Log.ERROR) {
            return mColorError;
        } else if (priority == Log.ASSERT) {
            return mColorAssert;
        } else {
            return mColorDefault;
        }
    }
}
