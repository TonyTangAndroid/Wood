package com.tonytangandroid.wood;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.Nullable;

import com.tonytangandroid.wood.internal.ui.list.TransactionListActivity;

import java.util.Collections;

/**
 * Class description
 *
 * @author tonytangandroid
 * @version 1.0
 * @since 03/06/18
 */
public class Wood {

    /**
     * Get an Intent to launch the Wood UI directly.
     *
     * @param context A Context.
     * @return An Intent for the main Wood Activity that can be started with {@link Context#startActivity(Intent)}.
     */
    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, TransactionListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Register an app shortcut to launch the Wood UI directly from the launcher on Android 7.0 and above.
     *
     * @param context A valid {@link Context}
     * @return The id of the added shortcut (<code>null</code> if this feature is not supported on the device).
     * It can be used if you want to remove this shortcut later on.
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @SuppressWarnings("WeakerAccess")
    @Nullable
    public static String addAppShortcut(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            final String id = context.getPackageName() + ".wood_ui";
            final ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            final ShortcutInfo shortcut = new ShortcutInfo.Builder(context, id).setShortLabel("Wood")
                    .setLongLabel("Open Wood")
                    .setIcon(Icon.createWithResource(context, R.drawable.wood_ic_shortcut_primary_24dp))
                    .setIntent(getLaunchIntent(context).setAction(Intent.ACTION_VIEW))
                    .build();
            shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut));
            return id;
        } else {
            return null;
        }

    }
}