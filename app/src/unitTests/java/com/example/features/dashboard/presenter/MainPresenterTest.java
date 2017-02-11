package com.example.features.dashboard.presenter;

import android.support.test.espresso.idling.CountingIdlingResource;
import com.example.features.dashboard.analytics.FetchShotsEvent;
import com.example.features.dashboard.analytics.ShotFetchingFailureEvent;
import com.example.features.dashboard.model.ShotMapper;
import com.example.features.dashboard.view.MainView;
import com.example.model.Shot;
import com.example.model.api.ImagesData;
import com.example.model.api.ShotResponse;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.util.PreconfiguredRobolectricTestRunner;
import com.example.util.dummy.DummyDataProvider;
import com.example.util.rx.RxSchedulers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Single;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class MainPresenterTest {

    @Mock private RestService mockRestService;
    @Mock private AnalyticsHelper mockAnalyticsHelper;
    @Mock private MainView mockMainView;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxSchedulers rxSchedulers = new RxSchedulers(Schedulers.immediate(), Schedulers.immediate(), Schedulers.immediate(), Schedulers.immediate());
        mainPresenter = new MainPresenter(mockRestService, rxSchedulers, mockAnalyticsHelper, new ShotMapper(), new CountingIdlingResource("test"));
        mainPresenter.attachView(mockMainView);
    }

    @Test
    public void load_shots() {
        // Arrange
        when(mockRestService.getShots()).thenReturn(Single.just(dummyDataProvider.shots().getShotResponsesList(3)));

        // Act
        mainPresenter.fetchShots();

        // Assert
        verify(mockMainView).showLoadingBar();
        verify(mockMainView).displayShotsList(dummyDataProvider.shots().getShotList(3));
        verifyNoMoreInteractions(mockMainView);
    }

    @Test
    public void fail_to_load_shots_updates_view() {
        // Arrange
        when(mockRestService.getShots()).thenReturn(Single.error(new RuntimeException("error")));

        // Act
        mainPresenter.fetchShots();

        // Assert
        verify(mockMainView).showLoadingBar();
        verify(mockMainView).showLoadingFailureError();
        verifyNoMoreInteractions(mockMainView);
    }

    @Test
    public void null_shots_are_filtered_before_returned() {
        // Arrange
        List<ShotResponse> shotResponseList = new ArrayList<>();
        shotResponseList.add(null);
        shotResponseList.add(new ShotResponse(null, new ImagesData("teaser url")));
        shotResponseList.add(new ShotResponse("title", null));
        shotResponseList.add(new ShotResponse("title", new ImagesData(null)));
        when(mockRestService.getShots()).thenReturn(Single.just(shotResponseList));

        // Act
        mainPresenter.fetchShots();

        // Assert
        List<Shot> expectedShotList = new ArrayList<>();
        expectedShotList.add(Shot.builder().setTitle(null).setUrl("teaser url").build());
        verify(mockMainView).showLoadingBar();
        verify(mockMainView).displayShotsList(expectedShotList);
        verifyNoMoreInteractions(mockMainView);
    }

    @Test
    public void analytics_events_triggered_when_failure_occurs() {
        // Arrange
        when(mockRestService.getShots()).thenReturn(Single.error(new RuntimeException("error")));

        // Act
        mainPresenter.fetchShots();

        // Assert
        verify(mockAnalyticsHelper).logEvent(any(FetchShotsEvent.class));
        verify(mockAnalyticsHelper).logEvent(any(ShotFetchingFailureEvent.class));
        verifyNoMoreInteractions(mockAnalyticsHelper);
    }

    @Test
    public void analytics_events_triggered_when_fetching_shots() {
        // Arrange
        when(mockRestService.getShots()).thenReturn(Single.just(dummyDataProvider.shots().getShotResponsesList(3)));

        // Act
        mainPresenter.fetchShots();

        // Assert
        verify(mockAnalyticsHelper).logEvent(any(FetchShotsEvent.class));
        verifyNoMoreInteractions(mockAnalyticsHelper);
    }

}
