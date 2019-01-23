package com.tonytangandroid.gander;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 02/06/18
 */
@SuppressWarnings("NullableProblems")
public class GanderInterceptor implements Interceptor {

    /**
     * @param context The current Context.
     */
    public GanderInterceptor(Context context) {
    }

    /**
     * Control whether a notification is shown while HTTP activity is recorded.
     *
     * @param sticky true to show a sticky notification.
     * @return The {@link GanderInterceptor} instance.
     */

    public GanderInterceptor showNotification(boolean sticky) {
        return this;
    }

    /**
     * Set the retention period for HTTP transaction data captured by this interceptor.
     * The default is one week.
     *
     * @param period the period for which to retain HTTP transaction data.
     * @return The {@link GanderInterceptor} instance.
     */
    public GanderInterceptor retainDataFor(Period period) {
        return this;
    }

    /**
     * Set the maximum length for request and response content before it is truncated.
     * Warning: setting this value too high may cause unexpected results.
     *
     * @param max the maximum length (in bytes) for request/response content.
     * @return The {@link GanderInterceptor} instance.
     */
    public GanderInterceptor maxContentLength(long max) {
        return this;
    }

    /**
     * Set headers names that shouldn't be stored by gander
     *
     * @param name the name of header to redact
     * @return The {@link GanderInterceptor} instance.
     */
    public GanderInterceptor redactHeader(String name) {
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
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
}
