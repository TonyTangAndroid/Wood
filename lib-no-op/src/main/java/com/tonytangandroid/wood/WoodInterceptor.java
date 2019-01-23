package com.tonytangandroid.wood;

import android.content.Context;

public class WoodInterceptor {

    /**
     * @param context The current Context.
     */
    public WoodInterceptor(Context context) {
    }

    /**
     * Control whether a notification is shown while HTTP activity is recorded.
     *
     * @param sticky true to show a sticky notification.
     * @return The {@link WoodInterceptor} instance.
     */

    public WoodInterceptor showNotification(boolean sticky) {
        return this;
    }

    /**
     * Set the retention period for HTTP transaction data captured by this interceptor.
     * The default is one week.
     *
     * @param period the period for which to retain HTTP transaction data.
     * @return The {@link WoodInterceptor} instance.
     */
    public WoodInterceptor retainDataFor(Period period) {
        return this;
    }

    /**
     * Set the maximum length for request and response content before it is truncated.
     * Warning: setting this value too high may cause unexpected results.
     *
     * @param max the maximum length (in bytes) for request/response content.
     * @return The {@link WoodInterceptor} instance.
     */
    public WoodInterceptor maxContentLength(long max) {
        return this;
    }

    /**
     * Set headers names that shouldn't be stored by wood
     *
     * @param name the name of header to redact
     * @return The {@link WoodInterceptor} instance.
     */
    public WoodInterceptor redactHeader(String name) {
        return this;
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
