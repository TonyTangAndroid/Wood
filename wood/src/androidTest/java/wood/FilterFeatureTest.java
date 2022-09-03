// package wood;
//
// import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
// import static com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotExist;
// import static
// com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItemChild;
//
// import androidx.test.ext.junit.rules.ActivityScenarioRule;
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.filters.LargeTest;
// import io.paging.demo.R;
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import paging.wrapper.ui.MainActivity;
//
// @org.junit.Ignore("x")
// @RunWith(AndroidJUnit4.class)
// @LargeTest
// public class FilterFeatureTest {
//
//  @Rule
//  public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);
//
//  @Test
//  public void case_1_byDefaultAllFilterAreCaseSensitive() {
//    assertDisplayed("ALL");
//    assertNotExist("all");
//  }
//
//  @Test
//  public void case_2_filterWithSpaceWillNotBeFound() {
//    assertNotExist("ALL ");
//  }
//
//  @Test
//  public void case_3_byDefaultAllFilterOptionsAreDisplayed() {
//    assertDisplayed("ALL");
//    assertDisplayed("83 WITH A");
//    assertDisplayed("83 WITH A");
//    assertDisplayed("16 WITH B");
//    assertDisplayed("12 WITH EE");
//    assertDisplayed("1 IVY");
//    assertDisplayed("EMPTY");
//    assertDisplayed("INITIAL_LOAD_ERROR");
//    assertDisplayed("LOAD_MORE_ERROR");
//  }
//
//  @Test
//  public void case_4_1_selectFilterColorShouldBeHighlighted() {
//    clickListItemChild(R.id.rv_query, 0, R.id.tv_query);
//    QueryStateAsserter.assertQueryTextColor(0, R.color.colorAccent);
//    QueryStateAsserter.assertQueryTextColor(1, android.R.color.black);
//  }
// }
