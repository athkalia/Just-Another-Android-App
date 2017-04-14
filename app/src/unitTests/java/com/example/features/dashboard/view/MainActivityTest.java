package com.example.features.dashboard.view;

import android.content.res.Configuration;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import com.example.App;
import com.example.R;
import com.example.features.dashboard.dagger.MainActivityComponent;
import com.example.features.dashboard.dagger.MainActivityModule;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.tools.dagger.components.ApplicationComponent;
import com.example.tools.dagger.modules.ApplicationModule;
import com.example.util.PreconfiguredRobolectricTestRunner;
import com.example.util.dummy.DummyDataProvider;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class MainActivityTest {

    @Rule public DaggerMockRule<MainActivityComponent> mockitoRule =
            new DaggerMockRule<>(MainActivityComponent.class, new MainActivityModule())
                    .addComponentDependency(ApplicationComponent.class, new ApplicationModule((App) RuntimeEnvironment.application))
                    .set(ComponentFactory::setMainActivityComponent);

    private ProgressBar progressBar;
    @Mock MainPresenter mockMainPresenter;
    private ActivityController<MainActivity> mainActivityController;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    @Before
    public void setUp() {
        mainActivityController = Robolectric.buildActivity(MainActivity.class);
    }

    @After
    public void afterEveryTest() {
        Mockito.reset(mockMainPresenter);
        mainActivityController.destroy();
    }

    @Test
    public void activity_not_null() {
        //when
        MainActivity mainActivity = mainActivityController.setup().get();

        //then
        assertThat(mainActivity).isNotNull();
    }

    @Test
    public void display_shots() {
        // given
        MainActivity mainActivity = mainActivityController.setup().get();
        progressBar = ButterKnife.findById(mainActivity, R.id.main_activity_progress_bar);

        // when
        mainActivity.showLoadingFailureError();

        // then
        assertThat(progressBar.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void display_shots_list() {
        //given
        MainActivity mainActivity = mainActivityController.setup().get();
        progressBar = ButterKnife.findById(mainActivity, R.id.main_activity_progress_bar);

        //when
        mainActivity.displayShotsList(dummyDataProvider.shots().getShotList());

        //then
        assertThat(progressBar.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void configuration_change_does_not_fetch_shots_again() {
        // Given
        mainActivityController.setup();

        // When
        Configuration configuration = new Configuration();
        configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
        mainActivityController.configurationChange(configuration);

        // Then
        verify(mockMainPresenter, times(1)).fetchShots();
    }

    @Test
    public void should_trigger_presenter_when_update_active_tile_menu_item_is_clicked() {
        // Given
        MainActivity mainActivity = mainActivityController.setup().get();

        // When
        shadowOf(mainActivity).clickMenuItem(R.id.menu_item_update_active_tile);

        // Then
        verify(mockMainPresenter).onUpdateTileMenuItemClicked();
    }

}
