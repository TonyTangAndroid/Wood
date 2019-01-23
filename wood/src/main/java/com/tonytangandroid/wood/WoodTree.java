package com.tonytangandroid.wood;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.Executor;

import timber.log.Timber;

public class WoodTree extends Timber.DebugTree {

    @NonNull
    private static final Period DEFAULT_RETENTION = Period.ONE_WEEK;
    @NonNull
    private final Context context;
    @NonNull
    private final WoodDatabase woodDatabase;
    private final Executor executor;
    @Nullable
    private NotificationHelper notificationHelper;
    @NonNull
    private RetentionManager retentionManager;
    private int maxContentLength = 250000;
    private boolean stickyNotification = false;

    /**
     * @param context The current Context.
     */
    public WoodTree(@NonNull Context context) {
        executor = new JobExecutor();
        this.context = context.getApplicationContext();
        woodDatabase = WoodDatabase.getInstance(context);
        retentionManager = new RetentionManager(this.context, DEFAULT_RETENTION);
    }

    /**
     * Control whether a notification is shown while Timber log is recorded.
     *
     * @param sticky true to show a sticky notification.
     * @return The {@link WoodTree} instance.
     */
    @NonNull
    public WoodTree showNotification(boolean sticky) {
        this.stickyNotification = sticky;
        notificationHelper = new NotificationHelper(this.context);
        return this;
    }

    /**
     * Set the retention period for Timber log data captured by this interceptor.
     * The default is one week.
     *
     * @param period the period for which to retain Timber log data.
     * @return The {@link WoodTree} instance.
     */
    @NonNull
    public WoodTree retainDataFor(Period period) {
        retentionManager = new RetentionManager(context, period);
        return this;
    }

    /**
     * Set the maximum length for request and response content before it is truncated.
     * Warning: setting this value too high may cause unexpected results.
     *
     * @param max the maximum length (in bytes) for request/response content.
     * @return The {@link WoodTree} instance.
     */
    @NonNull
    public WoodTree maxLength(int max) {
        this.maxContentLength = Math.min(max, 999999);// close to => 1 MB Max in a BLOB SQLite.
        return this;
    }

    @Override
    protected void log(final int priority, final String tag, final @NonNull String message, final Throwable t) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                doLog(priority, tag, message, t);
            }
        });
    }

    private void doLog(int priority, String tag, @NonNull String message, Throwable t) {
        Leaf transaction = new Leaf();
        transaction.setPriority(priority);
        transaction.setDate(new Date());
        transaction.setTag(tag);
        transaction.setLength(message.length());
        if (t != null) {
            message = message + "\n" + t.getMessage() + "\n" + ErrorUtil.asString(t);
        }
        transaction.setBody(message.substring(0, Math.min(message.length(), maxContentLength)) + "");
        create(transaction);
    }


    private void create(@NonNull Leaf transaction) {
        long transactionId = woodDatabase.leafDao().insertTransaction(transaction);
        transaction.setId(transactionId);
        if (notificationHelper != null) {
            notificationHelper.show(transaction, stickyNotification);
        }
        retentionManager.doMaintenance();
    }


    public enum Period {
        /**
         * Retain data for the last hour.
         */
        ONE_HOUR,
        /**
         * Retain data for the last day.
         */
        ONE_DAY,
        /**
         * Retain data for the last week.
         */
        ONE_WEEK,
        /**
         * Retain data forever.
         */
        FOREVER
    }

    /**
     * From https://stackoverflow.com/a/1149712/4068957
     */
    static class ErrorUtil {
        public static String asString(Throwable throwable) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            return sw.toString(); // stack trace as a string
        }
    }
}
