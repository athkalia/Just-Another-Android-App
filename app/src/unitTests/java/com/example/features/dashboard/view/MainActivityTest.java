package com.example.features.dashboard.view;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.R;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.util.PreconfiguredRobolectricTestRunner;
import com.example.util.dummy.DummyDataProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class MainActivityTest {

    private ProgressBar progressBar;
    private ViewGroup shotsFailedActionContainer;
    private MainActivity mainActivity;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    @Before
    public void setUp() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.main_activity_progress_bar);
        shotsFailedActionContainer = (ViewGroup) mainActivity.findViewById(R.id.activity_main_shots_loading_failed__container);
    }

    @Test
    public void activity_not_null() {

        assertThat(mainActivity).isNotNull();
    }

    @Test
    public void display_shots() {
        // Act
        mainActivity.showLoadingFailureError();

        // Assert
        assertThat(progressBar.getVisibility()).isEqualTo(View.GONE);
        assertThat(shotsFailedActionContainer.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    @Ignore
    public void display_shots_list() {
        // Act
        mainActivity.displayShotsList(dummyDataProvider.shots().getShotList());

        // Assert
        assertThat(progressBar.getVisibility()).isEqualTo(View.GONE);
        assertThat(shotsFailedActionContainer.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    @Ignore("flaky test")
    public void configuration_change_does_not_fetch_shots_again() {
        // Arrange
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity mainActivity = activityController.get();
        MainPresenter mockMainPresenter = Mockito.mock(MainPresenter.class);
        mainActivity.setPresenter(mockMainPresenter);
        activityController.setup();

        // Act
        Configuration configuration = new Configuration();
        configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
        activityController.configurationChange(configuration);

        // Assert
        verify(mockMainPresenter).fetchShots();
    }

    @Test
    @Ignore("flaky test")
    public void should_trigger_presenter_when_update_active_tile_menu_item_is_clicked() {
        // Given
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity mainActivity = activityController.get();
        MainPresenter mockMainPresenter = Mockito.mock(MainPresenter.class);
        mainActivity.setPresenter(mockMainPresenter);
        activityController.setup();

        // When
        shadowOf(mainActivity).clickMenuItem(R.id.menu_item_update_active_tile);

        // Then
        verify(mockMainPresenter).onUpdateTileMenuItemClicked();
    }

}
