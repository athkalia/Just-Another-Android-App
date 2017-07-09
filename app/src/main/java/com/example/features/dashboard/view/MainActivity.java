package com.example.features.dashboard.view;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.airbnb.lottie.LottieAnimationView;
import com.example.R;
import com.example.features.dashboard.dagger.MainActivityComponent;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.features.tiles.ActiveTileService;
import com.example.model.Shot;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.util.mvp.base.BaseActivity;
import com.example.util.other.ToastDuration;
import shortbread.Shortcut;
import timber.log.Timber;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.util.view.ButterknifeActions.SET_VISIBILITY_TO_GONE;
import static com.example.util.view.ButterknifeActions.SET_VISIBILITY_TO_VISIBLE;

@Shortcut(
        id = "3",
        icon = R.drawable.app_shortcut_library_icon,
        shortLabelRes = R.string.shortcuts__sample_three__short_label,
        longLabelRes = R.string.shortcuts__sample_three__long_label,
        rank = 2,
        disabledMessageRes = R.string.shortcuts__sample_three__disabled_message,
        backStack = {MainActivity.class, MainActivity.class},
        activity = MainActivity.class,
        action = Intent.ACTION_VIEW
)
public class MainActivity extends BaseActivity<MainActivityComponent, MainView, MainPresenter, MainActivityViewState> implements MainView {

    private static final int RECYCLER_VIEW_SPAN_COUNT = 2;

    @BindViews({R.id.activity_main__shots_reload__button, R.id.activity_main__shots_reload__text_view_label,
            R.id.lottie_animation_view}) List<View> errorViews;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.main_activity_progress_bar) ProgressBar progressBar;
    @BindView(R.id.lottie_animation_view) LottieAnimationView lottieAnimationView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject AnalyticsHelper analyticsHelper;
    @Inject ShotsAdapter shotsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityComponent constructComponent() {
        return ComponentFactory.getMainActivityComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_report_a_problem:
                Toast.makeText(this, "Report a problem clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item_update_active_tile:
                getPresenter().onUpdateTileMenuItemClicked();
                return true;
            default:
                throw new AssertionError("Every menu item should be explicitly handled in the switch statement.");
        }
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), RECYCLER_VIEW_SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(shotsAdapter);
    }

    @Override
    protected void onFirstCreate() {
        Timber.d("Initializing activity for the first time - view state is currently empty.");
        getPresenter().fetchShots();
    }

    @Override
    public void displayShotsList(List<Shot> shots) {
        Timber.i("Displaying shots: %s", shots);
        getViewState().saveShots(shots);
        progressBar.setVisibility(GONE);
        shotsAdapter.setData(shots);
    }

    @Override
    public void showLoadingBar() {
        Timber.v("Showing loading bar");
        progressBar.setVisibility(VISIBLE);
        ButterKnife.apply(errorViews, SET_VISIBILITY_TO_GONE);
    }

    @Override
    public void showLoadingFailureError() {
        Timber.v("Showing loading failure layouts.");
        progressBar.setVisibility(GONE);
        lottieAnimationView.playAnimation();
        ButterKnife.apply(errorViews, SET_VISIBILITY_TO_VISIBLE);
    }

    @Override
    public void showToast(String toastMessage, @ToastDuration int toastDuration) {
        Toast.makeText(getApplicationContext(), toastMessage, toastDuration).show();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestActiveTileUpdate() {
        TileService.requestListeningState(getApplicationContext(), new ComponentName(getApplicationContext(), ActiveTileService.class));
    }

    @OnClick(R.id.activity_main__shots_reload__button)
    void reloadShotsButtonClicked() {
        Timber.i("Reload shots button clicked - reloading shots.");
        lottieAnimationView.cancelAnimation();
        getPresenter().fetchShots();
    }

}
