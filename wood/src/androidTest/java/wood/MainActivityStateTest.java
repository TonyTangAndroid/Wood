package wood;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.tonytangandroid.wood.LeafListActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityStateTest {

  @Rule
  public ActivityScenarioRule<LeafListActivity> rule =
      new ActivityScenarioRule<>(LeafListActivity.class);

  @Test
  public void whenNoItemQueried_shouldShowEmptyContentHint() {
    onView(withText("123")).check(matches(isDisplayed()));
  }
}
