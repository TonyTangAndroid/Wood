package wood;

import android.app.Application;
import android.content.Context;
import androidx.test.runner.AndroidJUnitRunner;

// Used in build.gradle
@SuppressWarnings("unused")
public class DemoEspressoApplicationJunitRunner extends AndroidJUnitRunner {

  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, EspressoApp.class.getName(), context);
  }
}
