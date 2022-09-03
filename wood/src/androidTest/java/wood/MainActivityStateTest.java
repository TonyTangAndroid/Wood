// package wood;
//
// import static androidx.test.espresso.Espresso.onView;
// import static androidx.test.espresso.assertion.ViewAssertions.matches;
// import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
// import static androidx.test.espresso.matcher.ViewMatchers.withText;
//
// import androidx.test.espresso.action.ViewActions;
// import androidx.test.ext.junit.rules.ActivityScenarioRule;
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.filters.LargeTest;
// import org.junit.Ignore;
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import paging.wrapper.ui.MainActivity;
//
// @Ignore("todo")
// @RunWith(AndroidJUnit4.class)
// @LargeTest
// public class MainActivityStateTest {
//
//  @Rule
//  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);
//
//  @Test
//  public void whenNoItemQueried_shouldShowEmptyContentHint() {
//
//    onView(withText("ABC")).perform(ViewActions.click());
//    onView(withText("total:0")).check(matches(isDisplayed()));
//    //    onView(withText("No data at all")).check(matches(isDisplayed()));
//  }
// }
