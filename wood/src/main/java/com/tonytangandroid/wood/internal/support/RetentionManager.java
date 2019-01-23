package com.tonytangandroid.wood.internal.support;

import android.content.Context;
import android.content.SharedPreferences;

import com.tonytangandroid.wood.WoodInterceptor;
import com.tonytangandroid.wood.internal.data.WoodDatabase;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RetentionManager {
    private static final String PREFS_NAME = "wood_preferences";
    private static final String KEY_LAST_CLEANUP = "last_cleanup";

    private static long LAST_CLEAN_UP;

    private final WoodDatabase woodDatabase;
    private final long period;
    private final long cleanupFrequency;
    private final SharedPreferences prefs;

    public RetentionManager(Context context, WoodInterceptor.Period retentionPeriod) {
        this.woodDatabase = WoodDatabase.getInstance(context);
        period = toMillis(retentionPeriod);
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        cleanupFrequency = (retentionPeriod == WoodInterceptor.Period.ONE_HOUR) ?
                TimeUnit.MINUTES.toMillis(30) : TimeUnit.HOURS.toMillis(2);
    }

    public synchronized void doMaintenance() {
        if (period > 0) {
            long now = new Date().getTime();
            if (isCleanupDue(now)) {
                Logger.i("Performing data retention maintenance...");
                deleteSince(getThreshold(now));
                updateLastCleanup(now);
            }
        }
    }

    private long getLastCleanup(long fallback) {
        if (LAST_CLEAN_UP == 0) {
            LAST_CLEAN_UP = prefs.getLong(KEY_LAST_CLEANUP, fallback);
        }
        return LAST_CLEAN_UP;
    }

    private void updateLastCleanup(long time) {
        LAST_CLEAN_UP = time;
        prefs.edit().putLong(KEY_LAST_CLEANUP, time).apply();
    }

    private void deleteSince(long threshold) {
        long rows = woodDatabase.httpTransactionDao().deleteTransactionsBefore(new Date(threshold));
        Logger.i(rows + " transactions deleted");
    }

    private boolean isCleanupDue(long now) {
        return (now - getLastCleanup(now)) > cleanupFrequency;
    }

    private long getThreshold(long now) {
        return (period == 0) ? now : now - period;
    }

    private long toMillis(WoodInterceptor.Period period) {
        switch (period) {
            case ONE_HOUR:
                return TimeUnit.HOURS.toMillis(1);
            case ONE_DAY:
                return TimeUnit.DAYS.toMillis(1);
            case ONE_WEEK:
                return TimeUnit.DAYS.toMillis(7);
            default:
                return 0;
        }
    }
}
