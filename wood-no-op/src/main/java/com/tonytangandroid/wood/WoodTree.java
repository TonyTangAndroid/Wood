package com.tonytangandroid.wood;

import android.content.Context;

public class WoodTree {

    /**
     * @param context The current Context.
     */
    public WoodTree(Context context) {
    }

    /**
     * Control whether a notification is shown while Timber log activity is recorded.
     *
     * @param sticky true to show a sticky notification.
     * @return The {@link WoodTree} instance.
     */

    public WoodTree showNotification(boolean sticky) {
        return this;
    }

    /**
     * Set the retention period for Timber log data.
     * The default is one week.
     *
     * @param period the period for which to retain Timber log data.
     * @return The {@link WoodTree} instance.
     */
    public WoodTree retainDataFor(Period period) {
        return this;
    }

    /**
     * Set the maximum length for request and response content before it is truncated.
     * Warning: setting this value too high may cause unexpected results.
     *
     * @param max the maximum length (in bytes) for request/response content.
     * @return The {@link WoodTree} instance.
     */
    public WoodTree maxContentLength(long max) {
        return this;
    }

    /**
     * Set headers names that shouldn't be stored by wood
     *
     * @param name the name of header to redact
     * @return The {@link WoodTree} instance.
     */
    public WoodTree redactHeader(String name) {
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
