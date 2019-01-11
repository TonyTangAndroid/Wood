package com.ashokvarma.gander;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ashokvarma.gander.internal.data.GanderDatabase;
import com.ashokvarma.gander.internal.data.HttpTransaction;
import com.ashokvarma.gander.internal.support.NotificationHelper;
import com.ashokvarma.gander.internal.support.RetentionManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Executor;

import timber.log.Timber;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 02/06/18
 */
public class GanderInterceptor extends Timber.DebugTree {

    @NonNull
    private static final Period DEFAULT_RETENTION = Period.ONE_WEEK;
    @NonNull
    private final Context mContext;
    @NonNull
    private final GanderDatabase mGanderDatabase;
    private final Executor executor;
    @Nullable
    private NotificationHelper mNotificationHelper;
    @NonNull
    private RetentionManager mRetentionManager;
    private long mMaxContentLength = 250000L;
    @NonNull
    private volatile Set<String> headersToRedact = Collections.emptySet();
    private boolean stickyNotification = false;

    /**
     * @param context The current Context.
     */
    public GanderInterceptor(@NonNull Context context) {
        executor = new JobExecutor();
        this.mContext = context.getApplicationContext();
        mGanderDatabase = GanderDatabase.getInstance(context);
        mRetentionManager = new RetentionManager(this.mContext, DEFAULT_RETENTION);
    }

    /**
     * Control whether a notification is shown while HTTP activity is recorded.
     *
     * @param sticky true to show a sticky notification.
     * @return The {@link GanderInterceptor} instance.
     */
    @NonNull
    public GanderInterceptor showNotification(boolean sticky) {
        this.stickyNotification = sticky;
        mNotificationHelper = new NotificationHelper(this.mContext);
        return this;
    }

    /**
     * Set the retention period for HTTP transaction data captured by this interceptor.
     * The default is one week.
     *
     * @param period the period for which to retain HTTP transaction data.
     * @return The {@link GanderInterceptor} instance.
     */
    @NonNull
    public GanderInterceptor retainDataFor(Period period) {
        mRetentionManager = new RetentionManager(mContext, period);
        return this;
    }

    /**
     * Set the maximum length for request and response content before it is truncated.
     * Warning: setting this value too high may cause unexpected results.
     *
     * @param max the maximum length (in bytes) for request/response content.
     * @return The {@link GanderInterceptor} instance.
     */
    @NonNull
    public GanderInterceptor maxContentLength(long max) {
        this.mMaxContentLength = Math.min(max, 999999L);// close to => 1 MB Max in a BLOB SQLite.
        return this;
    }

    /**
     * Set headers names that shouldn't be stored by gander
     *
     * @param name the name of header to redact
     * @return The {@link GanderInterceptor} instance.
     */
    @NonNull
    public GanderInterceptor redactHeader(String name) {
        Set<String> newHeadersToRedact = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        newHeadersToRedact.addAll(headersToRedact);
        newHeadersToRedact.add(name);
        headersToRedact = newHeadersToRedact;
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
        HttpTransaction transaction = new HttpTransaction();
        transaction.setPriority(priority);
        transaction.setRequestDate(new Date());
        transaction.setMethod(tag);
        if (t != null) {
            transaction.setError(ErrorUtil.asString(t));
        }
        transaction.setRequestContentLength((long) message.length());
        transaction.setRequestBody(message.substring(0, (int) Math.min(message.length(), mMaxContentLength)));
        create(transaction);
    }


    private void create(@NonNull HttpTransaction transaction) {

        long transactionId = mGanderDatabase.httpTransactionDao().insertTransaction(transaction);
        transaction.setId(transactionId);
        if (mNotificationHelper != null) {
            mNotificationHelper.show(transaction, stickyNotification);
        }
        mRetentionManager.doMaintenance();
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
