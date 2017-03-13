package com.example.networking;

import com.example.App;
import com.example.model.api.ShotResponse;
import com.example.util.MockWebServerHelper;
import com.example.util.PreconfiguredRobolectricTestRunner;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class RestServiceIntegrationTest {

    private MockWebServer mockWebServer;
    private RestService restService;

    private final MockWebServerHelper mockWebServerHelper = new MockWebServerHelper();

    @Before
    public void beforeEveryTest() throws IOException {
        mockWebServer = mockWebServerHelper.initMockWebServer();
        restService = App.getApplicationComponent().restService();
    }

    @After
    public void afterEveryTest() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void shot_request_test_network_layer() throws IOException {
        mockWebServerHelper.enqueueJsonResponseFromFileForMockWebServer(mockWebServer, "shots_response.json");

        List<ShotResponse> shotResponseList = restService.getShots().toObservable().blockingSingle();

        assertThat(shotResponseList.get(0).getTitle()).isEqualTo("2017 Wallpaper");
        assertThat(shotResponseList.get(0).getImagesData().getTeaserImageUrl())
                .isEqualTo("https://d13yacurqjgara.cloudfront.net/users/538946/screenshots/3175656/2017_teaser.png");
    }

}
