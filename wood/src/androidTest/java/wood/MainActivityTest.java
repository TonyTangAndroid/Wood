// package wood;
//
// import static androidx.test.espresso.Espresso.onView;
// import static androidx.test.espresso.action.ViewActions.click;
// import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
// import static androidx.test.espresso.assertion.ViewAssertions.matches;
// import static androidx.test.espresso.contrib.RecyclerViewActions.*;
// import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
// import static androidx.test.espresso.matcher.ViewMatchers.withId;
// import static androidx.test.espresso.matcher.ViewMatchers.withText;
// import static paging.wrapper.TestUtils.withRecyclerView;
//
// import androidx.test.espresso.ViewInteraction;
// import androidx.test.espresso.action.ViewActions;
// import androidx.test.ext.junit.rules.ActivityScenarioRule;
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.filters.LargeTest;
// import io.paging.demo.R;
// import org.junit.Ignore;
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import paging.wrapper.ui.MainActivity;
//
// @Ignore("x")
// @RunWith(AndroidJUnit4.class)
// @LargeTest
// public class MainActivityTest {
//
//  @Rule
//  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);
//
//  @Ignore("Flaky due to database mutation")
//  @Test
//  public void onAppStarts_shouldAllData() {
//    ViewInteraction totalViewCount = onView(withText("total:151"));
//    totalViewCount.check(matches(isDisplayed()));
//
//    ViewInteraction absentViewCount = onView(withText("total:152"));
//    absentViewCount.check(doesNotExist());
//  }
//
//  @Test
//  public void onAppStarts_shouldShowAllFilters() {
//    ViewInteraction upperCaseValidFilter = onView(withText("ABC"));
//    upperCaseValidFilter.check(matches(isDisplayed()));
//
//    ViewInteraction lowCaseValidFilter = onView(withText("abc"));
//    lowCaseValidFilter.check(matches(isDisplayed()));
//
//    ViewInteraction invalidFilter = onView(withText("abcd"));
//    invalidFilter.check((doesNotExist()));
//  }
//
//  @Test
//  public void demo_1_whenFullFiltered_shouldShowNoData() {
//    onView(withId(R.id.rv_query)).perform(actionOnItemAtPosition(5, click()));
//    onView(withText("total:0")).check(matches(isDisplayed()));
//  }
//
//  @Test
//  public void demo_2_whenFullFiltered_shouldShowNoData() {
//    ViewInteraction validFilter = onView(withText("ABC"));
//    validFilter.perform(ViewActions.click());
//    onView(withText("total:0")).check(matches(isDisplayed()));
//  }
//
//  @Test
//  public void whenHalfFiltered_shouldShowNoData() {
//    ViewInteraction validFilter = onView(withText("ee"));
//    validFilter.perform(ViewActions.click());
//    onView(withText("total:12")).check(matches(isDisplayed()));
//  }
//
//  @Ignore("Flaky")
//  @Test
//  public void mutateFromZeroToMaxToHalf_shouldShowData() {
//
//    // zero
//    onView(withText("ABC")).perform(ViewActions.click());
//    onView(withText("total:0")).check(matches(isDisplayed()));
//    // max
//    onView(withText("a")).perform(ViewActions.click());
//    onView(withText("total:70")).check(matches(isDisplayed()));
//    // half
//    onView(withText("ee")).perform(ViewActions.click());
//    onView(withText("total:12")).check(matches(isDisplayed()));
//  }
//
//  @Test
//  public void validRecyclerViewItemCorrect() {
//    onView(withText("a")).perform(ViewActions.click());
//    onView(withRecyclerView(R.id.rv_paging_view).atPositionOnView(1, R.id.tv_pokemon))
//        .check(matches(withText("2:Ivysaur")));
//  }
//
//  @Ignore("Flaky")
//  @Test
//  public void scrollToCertainItem_checkItsText() {
//    onView(withText("a")).perform(ViewActions.click());
//    onView(withText("total:70")).check(matches(isDisplayed()));
//    onView(withRecyclerView(R.id.rv_paging_view).atPositionOnView(8, R.id.tv_pokemon))
//        .check(matches(withText("10:Caterpie")));
//  }
//
//  @Test
//  public void itemClicked_shouldShowDetails() {
//    onView(withText("a")).perform(ViewActions.click());
//    onView(withId(R.id.rv_paging_view)).perform(actionOnItemAtPosition(6, click()));
//    onView(withText("8")).check(matches(isDisplayed()));
//    onView(withText("8:Wartortle")).check(matches(isDisplayed()));
//  }
// }
