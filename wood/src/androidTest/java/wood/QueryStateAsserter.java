package wood;

import static com.adevinta.android.barista.assertion.BaristaListAssertions.assertCustomAssertionAtPosition;
import static paging.wrapper.ColorAssertionMatcher.withTextColorResId;

import androidx.annotation.ColorRes;
import androidx.test.espresso.assertion.ViewAssertions;
import io.paging.demo.R;

public class QueryStateAsserter {

  public static void assertQueryTextColor(int index, @ColorRes int color) {
    assertCustomAssertionAtPosition(
        R.id.rv_query, index, R.id.tv_query, ViewAssertions.matches(withTextColorResId((color))));
  }
}
