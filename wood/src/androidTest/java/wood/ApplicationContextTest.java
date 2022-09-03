package wood;

import static com.google.common.truth.Truth.*;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationContextTest {

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertThat(appContext.getPackageName()).isEqualTo("io.paging");

    Context applicationContext = ApplicationProvider.getApplicationContext();
    assertThat(applicationContext.getPackageName()).isEqualTo(appContext.getPackageName());
  }
}
