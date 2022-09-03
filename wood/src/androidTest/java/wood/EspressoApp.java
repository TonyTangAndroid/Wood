package wood;

import android.app.Application;
import android.util.Log;
import timber.log.Timber;

public class EspressoApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initWood(this);
    Timber.i("test123");
  }

  private static void initWood(Application application) {
    timber.log.Timber.plant(
        new com.tonytangandroid.wood.WoodTree(application)
            .retainDataFor(com.tonytangandroid.wood.WoodTree.Period.FOREVER)
            .logLevel(Log.VERBOSE)
            .autoScroll(false)
            .maxLength(100000)
            .showNotification(true));
  }
}
