package com.tonytangandroid.wood;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import timber.log.Timber;

public class WoodTree extends Timber.DebugTree {

  @NonNull private static final Period DEFAULT_RETENTION = Period.ONE_WEEK;
  private static final String PREF_WOOD_CONFIG = "pref_wood_config";
  private static final String PREF_KEY_AUTO_SCROLL = "pref_key_auto_scroll";
  @NonNull private final Context context;
  @NonNull private final WoodDatabase woodDatabase;
  private final Executor executor;
  private final String threadTagPrefix;
  @Nullable private NotificationHelper notificationHelper;
  @NonNull private RetentionManager retentionManager;
  private int maxContentLength = 250000;
  private boolean stickyNotification = false;
  private List<String> supportedTaggerList = new ArrayList<>();
  private final SharedPreferences sharedPreferences;
  private int logLevel = Log.DEBUG;

  /** @param context The current Context. */
  public WoodTree(@NonNull Context context) {
    this(context, "");
  }

  /**
   * @param context context
   * @param threadTagPrefix the extra prefix added on the logged message.
   */
  public WoodTree(@NonNull Context context, String threadTagPrefix) {
    this.threadTagPrefix = threadTagPrefix;
    this.executor = new JobExecutor();
    this.context = context.getApplicationContext();
    this.woodDatabase = WoodDatabase.getInstance(context);
    this.retentionManager = new RetentionManager(this.context, DEFAULT_RETENTION);
    this.sharedPreferences = context.getSharedPreferences(PREF_WOOD_CONFIG, Context.MODE_PRIVATE);
  }

  public static boolean autoScroll(Context context) {
    SharedPreferences sharedPreferences =
        context.getSharedPreferences(PREF_WOOD_CONFIG, Context.MODE_PRIVATE);
    return sharedPreferences.getBoolean(PREF_KEY_AUTO_SCROLL, true);
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

  @NonNull
  public WoodTree limitToTheseTaggerList(@NonNull List<String> supportedTaggerList) {
    this.supportedTaggerList = supportedTaggerList;
    return this;
  }

  /**
   * If you want to only log warning or above, pass {@link android.util.Log#WARN}. By default, it
   * will log all debug log {@link android.util.Log#DEBUG} or above
   *
   * @param logLevel the log level value from {@link android.util.Log}
   * @return The {@link WoodTree} instance.
   */
  @NonNull
  public WoodTree logLevel(int logLevel) {
    this.logLevel = logLevel;
    return this;
  }

  /**
   * Set the retention period for Timber log data captured by this interceptor. The default is one
   * week.
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
   * Set the log should auto scroll like Android Logcat console. By default it is false.
   *
   * @param autoScroll true if you want to make the log auto scroll.
   * @return The {@link WoodTree} instance.
   */
  @NonNull
  public WoodTree autoScroll(boolean autoScroll) {
    sharedPreferences.edit().putBoolean(PREF_KEY_AUTO_SCROLL, autoScroll).apply();
    return this;
  }

  /**
   * Set the maximum length for request and response content before it is truncated. Warning:
   * setting this value too high may cause unexpected results.
   *
   * @param max the maximum length (in bytes) for request/response content.
   * @return The {@link WoodTree} instance.
   */
  @NonNull
  public WoodTree maxLength(int max) {
    this.maxContentLength = Math.min(max, 999999); // close to => 1 MB Max in a BLOB SQLite.
    return this;
  }

  @Override
  protected void log(
      final int priority, final String tag, final @NonNull String message, final Throwable t) {
    if (shouldBeLogged(priority, tag)) {
      String assembledMessage = formatThreadTag(message, this.threadTagPrefix);
      executor.execute(() -> doLog(priority, tag, assembledMessage, t));
    }
  }

  private static String formatThreadTag(String message, String threadTagPrefix) {
    return String.format(
        Locale.US, "[%s#%s]:%s", threadTagPrefix, Thread.currentThread().getName(), message);
  }

  private boolean shouldBeLogged(int priority, String tag) {
    if (priority < logLevel) {
      return false;
    }

    if (hasNoTagFilter()) {
      return true;
    }

    return tagIsListedCaseInsensitive(tag);
  }

  private boolean tagIsListedCaseInsensitive(String tag) {
    String toLowerCase = tag.toLowerCase();
    for (String supportedTagger : supportedTaggerList) {
      if (supportedTagger.toLowerCase().contains(toLowerCase)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasNoTagFilter() {
    return supportedTaggerList.size() == 0;
  }

  private void doLog(int priority, String tag, @NonNull String message, Throwable t) {
    Leaf transaction = new Leaf();
    transaction.setPriority(priority);
    transaction.setCreateAt(System.currentTimeMillis());
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
    /** Retain data for the last hour. */
    ONE_HOUR,
    /** Retain data for the last day. */
    ONE_DAY,
    /** Retain data for the last week. */
    ONE_WEEK,
    /** Retain data forever. */
    FOREVER
  }

  /** From https://stackoverflow.com/a/1149712/4068957 */
  static class ErrorUtil {

    public static String asString(Throwable throwable) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      throwable.printStackTrace(pw);
      return sw.toString(); // stack trace as a string
    }
  }
}
