package wood;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Matcher;

public class ColorAssertionMatcher {

  public static Matcher<View> withTextColor(@ColorInt final int color) {
    return new BoundedMatcher<View, TextView>(TextView.class) {
      @Override
      public void describeTo(org.hamcrest.Description description) {
        description.appendText("with text color: ");
      }

      @Override
      public boolean matchesSafely(TextView textView) {
        return color == textView.getCurrentTextColor();
      }
    };
  }

  public static Matcher<View> withTextColorResId(@ColorRes final int colorResId) {
    @ColorInt
    int color = ContextCompat.getColor(ApplicationProvider.getApplicationContext(), colorResId);
    return withTextColor(color);
  }
}
