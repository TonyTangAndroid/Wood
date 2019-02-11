package com.tonytangandroid.wood;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Wood {

    /**
     * Get an Intent to launch the Wood UI directly.
     *
     * @param context A Context.
     * @return An Intent for the main Wood Activity that can be started with {@link Context#startActivity(Intent)}.
     */
    public static Intent getLaunchIntent(Context context) {
        return new Intent(context,LeafListActivity.class);
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @SuppressWarnings("WeakerAccess")
    public static String addAppShortcut(Context context) {
        return null;
    }
}
