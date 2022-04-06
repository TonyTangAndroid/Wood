package com.tonytangandroid.wood;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.LongSparseArray;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

import com.tony.tang.safe.pending.intent.sdk.SafePendingIntent;

class NotificationHelper {

    private static final String CHANNEL_ID = "wood_notification_log_channel";
    private static final int BUFFER_SIZE = 10;

    private static final LongSparseArray<Leaf> TRANSACTION_BUFFER = new LongSparseArray<>();
    private static int TRANSACTION_COUNT;

    private final Context mContext;
    private final NotificationManager mNotificationManager;
    private final WoodColorUtil mColorUtil;

    public NotificationHelper(Context context) {
        this.mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mColorUtil = WoodColorUtil.getInstance(context);
        setUpChannelIfNecessary();
    }

    public static synchronized void clearBuffer() {
        TRANSACTION_BUFFER.clear();
        TRANSACTION_COUNT = 0;
    }

    private static synchronized void addToBuffer(Leaf transaction) {
        TRANSACTION_COUNT++;
        TRANSACTION_BUFFER.put(transaction.getId(), transaction);
        if (TRANSACTION_BUFFER.size() > BUFFER_SIZE) {
            TRANSACTION_BUFFER.removeAt(0);
        }
    }

    private void setUpChannelIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, mContext.getString(R.string.wood_notification_category), NotificationManager.IMPORTANCE_LOW);
            channel.setShowBadge(false);

            mNotificationManager.createNotificationChannel(channel);
        }
    }

    public synchronized void show(Leaf transaction, boolean stickyNotification) {
        addToBuffer(transaction);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setContentIntent(SafePendingIntent.getActivity(mContext, 0, Wood.getLaunchIntent(mContext), 0))
                .setLocalOnly(true)
                .setSmallIcon(R.drawable.wood_icon)
                .setColor(ContextCompat.getColor(mContext, R.color.wood_colorPrimary))
                .setOngoing(stickyNotification)
                .setContentTitle(mContext.getString(R.string.wood_notification_title));
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        int count = 0;
        for (int i = TRANSACTION_BUFFER.size() - 1; i >= 0; i--) {
            if (count < BUFFER_SIZE) {
                if (count == 0) {
                    builder.setContentText(getNotificationText(TRANSACTION_BUFFER.valueAt(i)));
                }
                inboxStyle.addLine(getNotificationText(TRANSACTION_BUFFER.valueAt(i)));
            }
            count++;
        }
        builder.setAutoCancel(true);
        builder.setStyle(inboxStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setSubText(String.valueOf(TRANSACTION_COUNT));
        } else {
            builder.setNumber(TRANSACTION_COUNT);
        }
        builder.addAction(getDismissAction());
        builder.addAction(getClearAction());
        mNotificationManager.notify(CHANNEL_ID.hashCode(), builder.build());
    }

    private CharSequence getNotificationText(Leaf transaction) {
        int color = mColorUtil.getTransactionColor(transaction);
        String text = transaction.body();
        // Simple span no Truss required
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, text.length(), SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @NonNull
    private NotificationCompat.Action getClearAction() {
        CharSequence clearTitle = mContext.getString(R.string.wood_clear);
        Intent deleteIntent = new Intent(mContext, ClearTransactionsService.class);
        PendingIntent intent = SafePendingIntent.getService(mContext, 11, deleteIntent, PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Action(R.drawable.wood_ic_delete_white_24dp, clearTitle, intent);
    }

    @NonNull
    private NotificationCompat.Action getDismissAction() {
        CharSequence dismissTitle = mContext.getString(R.string.wood_dismiss);
        Intent dismissIntent = new Intent(mContext, DismissNotificationService.class);
        PendingIntent intent = SafePendingIntent.getService(mContext, 12, dismissIntent, PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Action(0, dismissTitle, intent);
    }

    public void dismiss() {
        mNotificationManager.cancel(CHANNEL_ID.hashCode());
    }
}