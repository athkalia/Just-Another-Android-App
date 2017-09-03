package com.example.features.dashboard.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.App;
import com.example.R;
import com.example.util.EspressoTestHelper;
import okreplay.AndroidTapeRoot;
import okreplay.OkReplay;
import okreplay.OkReplayConfig;
import okreplay.OkReplayRuleChain;
import okreplay.TapeMode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static com.example.util.MatchersHelper.onViewWithId;
import static com.example.util.MatchersHelper.recyclerViewShouldHaveItemCount;

/**
 * Test created as an example of the OkReplay (https://github.com/airbnb/okreplay) library. By annotating a test with {@link OkReplay}
 * the library intercepts the request and creates a copy of it in yaml format that is stored on the external storage of the device.
 * Then by running the gradle task 'pullOkReplayTapes', the yaml file is copied over to the espresso test asset folder. After this the
 * test no longer hits the server, but fetches the requests from that asset file instead.
 * Note: To record new server requests/responses the EXTERNAL STORAGE permission is required.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityOkReplayEspressoTest extends EspressoTestHelper {

    private final ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false);

    private final OkReplayConfig configuration = new OkReplayConfig.Builder()
            .tapeRoot(new AndroidTapeRoot(getContext(), getClass()))
            .defaultMode(TapeMode.READ_ONLY) // or 'TapeMode.WRITE' to create the tapes. 'WRITE' requires external_storage permission.
            .sslEnabled(true)
            .interceptor(App.getApplicationComponent().okReplayInterceptor())
            .build();

    @Rule public final TestRule testRule = new OkReplayRuleChain(configuration, activityTestRule).get();

    @Test
    @OkReplay
    public void fetch_shots() {
        // Assert
        checkViewIsNotVisible(R.id.activity_main__shots_reload__button);
        onViewWithId(R.id.recycler_view)
                .check(recyclerViewShouldHaveItemCount(12));
        takeScreenshot("All_shots_should_be_loaded_and_visible_via_OkReplay_library", activityTestRule.getActivity());
    }

}
