package com.example.features.dashboard.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.R;
import com.example.util.EspressoTestHelper;
import com.example.util.MockWebServerHelper;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static com.example.util.MatchersHelper.onViewWithId;
import static com.example.util.MatchersHelper.recyclerViewShouldHaveItemCount;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest extends EspressoTestHelper {

    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false, false);

    private final MockWebServerHelper mockWebServerHelper = new MockWebServerHelper();

    private MockWebServer mockWebServer;

    @Before
    public void setUpMockWebServer() throws IOException {
        mockWebServer = mockWebServerHelper.initMockWebServer();
    }

    @After
    public void terminateMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void fetch_shots_reloading() throws IOException {
        // Arrange
        mockWebServerHelper.enqueueErrorResponseForMockWebServer(mockWebServer, 501);
        mockWebServerHelper.enqueueJsonResponseFromFileForMockWebServer(mockWebServer, "shots_response.json");

        // Act
        activityTestRule.launchActivity(NO_INTENT);
        clickOnView(R.id.activity_main_shots_loading_failed__container);

        // Assert
        checkViewIsNotVisible(R.id.activity_main_shots_loading_failed__container);
        onViewWithId(R.id.recycler_view)
                .check(recyclerViewShouldHaveItemCount(1))
                .check(matches(hasDescendant(withContentDescription("Image with title: 2017 Wallpaper"))));
    }

    @Test
    public void shot_request_test() throws IOException {
        // Arrange
        mockWebServerHelper.enqueueJsonResponseFromFileForMockWebServer(mockWebServer, "shots_response.json");

        // Act
        activityTestRule.launchActivity(NO_INTENT);

        // Assert
        onViewWithId(R.id.recycler_view)
                .check(recyclerViewShouldHaveItemCount(1))
                .check(matches(hasDescendant(withContentDescription("Image with title: 2017 Wallpaper"))));
    }

}
