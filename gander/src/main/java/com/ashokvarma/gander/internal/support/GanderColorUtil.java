package com.ashokvarma.gander.internal.support;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ashokvarma.gander.R;
import com.ashokvarma.gander.internal.data.HttpTransaction;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 04/06/18
 */
public class GanderColorUtil {

    public static final int SEARCHED_HIGHLIGHT_BACKGROUND_COLOR = Color.parseColor("#FD953F");

    public static final int HIGHLIGHT_BACKGROUND_COLOR = Color.parseColor("#FFFD38");
    public static final int HIGHLIGHT_TEXT_COLOR = 0;//none
    public static final boolean HIGHLIGHT_UNDERLINE = false;
    private static GanderColorUtil TRANSACTION_COLOR_UTIL_INSTANCE;
    private final int mColorDefault;
    private final int mColorDefaultTxt;
    private final int mColorRequested;
    private final int mColorError;
    private final int mColor500;
    private final int mColor400;
    private final int mColor300;

    private GanderColorUtil(Context context) {
        mColorDefault = ContextCompat.getColor(context, R.color.gander_status_default);
        mColorDefaultTxt = ContextCompat.getColor(context, R.color.gander_status_default_txt);
        mColorRequested = ContextCompat.getColor(context, R.color.gander_status_requested);
        mColorError = ContextCompat.getColor(context, R.color.gander_status_error);
        mColor500 = ContextCompat.getColor(context, R.color.gander_status_500);
        mColor400 = ContextCompat.getColor(context, R.color.gander_status_400);
        mColor300 = ContextCompat.getColor(context, R.color.gander_status_300);
    }

    public static GanderColorUtil getInstance(Context context) {
        if (TRANSACTION_COLOR_UTIL_INSTANCE == null) {
            TRANSACTION_COLOR_UTIL_INSTANCE = new GanderColorUtil(context);
        }
        return TRANSACTION_COLOR_UTIL_INSTANCE;
    }

    public int getTransactionColor(HttpTransaction transaction) {
        return getTransactionColor(transaction, false);
    }

    public int getTransactionColor(HttpTransaction transaction, boolean txtColors) {
        return getTransactionColor(transaction.getPriority(), txtColors);
    }


    public int getTransactionColor(int priority, boolean txtColors) {
        if (priority >= Log.ERROR) {
            return mColorError;
        } else if (priority == Log.VERBOSE) {
            return mColorRequested;
        } else if (priority == Log.INFO) {
            return mColor500;
        } else if (priority == Log.WARN) {
            return mColor400;
        } else if (priority == Log.DEBUG) {
            return mColor300;
        } else {
            return txtColors ? mColorDefaultTxt : mColorDefault;
        }
    }
}
