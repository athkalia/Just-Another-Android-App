package com.example.features.dashboard.view;

import android.support.test.rule.ActivityTestRule;
import com.example.R;
import com.example.util.EspressoTestHelper;
import com.example.util.HttpCodes;
import com.example.util.MockWebServerHelper;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MainActivityErrorCodesEspressoTest extends EspressoTestHelper {

    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false, false);

    private final int errorCode;

    private MockWebServer mockWebServer;

    private final MockWebServerHelper mockWebServerHelper = new MockWebServerHelper();

    public MainActivityErrorCodesEspressoTest(int errorCode) {
        this.errorCode = errorCode;
    }

    @Before
    public void beforeEveryTest() throws IOException {
        mockWebServer = mockWebServerHelper.initMockWebServer();
    }

    @After
    public void afterEveryTest() throws IOException {
        mockWebServerHelper.reset(mockWebServer);
    }

    /**
     * Run the test for all http error codes.
     */
    @Parameterized.Parameters
    public static Collection<Integer> data() {
        return HttpCodes.clientAndServerSideErrorCodes();
    }

    @Test
    public void shot_request_failure_for_all_failure_response_codes() {
        // Arrange
        mockWebServerHelper.enqueueErrorResponseForMockWebServer(mockWebServer, errorCode);

        // Act
        activityTestRule.launchActivity(NO_INTENT);

        // Assert
        checkViewIsVisible(R.id.activity_main__shots_reload__button);
        checkViewIsVisible(R.id.activity_main__shots_reload__text_view_label);
    }

}
