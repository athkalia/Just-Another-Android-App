package com.example.features.dashboard.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.R;
import com.example.util.EspressoTestHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.util.MatchersHelper.onViewWithId;
import static com.example.util.MatchersHelper.recyclerViewShouldHaveItemCount;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoIntegrationTest extends EspressoTestHelper {

    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false, true);

    @Test
    public void fetch_shots() {
        int visibleChildrenInRecyclerView = 12;
        onViewWithId(R.id.recycler_view).check(recyclerViewShouldHaveItemCount(visibleChildrenInRecyclerView));
        checkViewIsNotVisible(R.id.activity_main__shots_reload__button);
        checkViewIsNotVisible(R.id.activity_main__shots_reload__text_view_label);
        takeScreenshot("All_shots_should_be_loaded_and_visible", activityTestRule.getActivity());
    }

}
