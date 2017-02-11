package com.example.features.dashboard.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.App;
import com.example.R;
import com.example.features.dashboard.dagger.DaggerMainActivityComponent;
import com.example.features.dashboard.dagger.MainActivityComponent;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.model.Shot;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.util.mvp.base.BaseActivity;
import timber.log.Timber;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseActivity<MainActivityComponent, MainView, MainPresenter, MainActivityViewState> implements MainView {

    private static final int RECYCLER_VIEW_SPAN_COUNT = 2;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.main_activity_progress_bar) ProgressBar progressBar;
    @BindView(R.id.activity_main_shots_loading_failed__container) ViewGroup shotsFailedActionContainer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject AnalyticsHelper analyticsHelper;
    @Inject ShotsAdapter shotsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityComponent constructComponent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(App.getApplicationComponent())
                .build();
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
        MenuItem reportAProblemMenuItem = menu.findItem(R.id.report_problem_menu_item);
        reportAProblemMenuItem.setOnMenuItemClickListener(menuItem -> {
            Toast.makeText(this, "report a problem clicked", Toast.LENGTH_SHORT).show();
            return true;
        });
        return super.onCreateOptionsMenu(menu);
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
        shotsFailedActionContainer.setVisibility(GONE);
    }

    @Override
    public void showLoadingFailureError() {
        Timber.v("Showing loading failure layouts.");
        progressBar.setVisibility(GONE);
        shotsFailedActionContainer.setVisibility(VISIBLE);
    }

    @OnClick(R.id.activity_main__shots_reload__button)
    void reloadShotsButtonClicked() {
        Timber.i("Reload shots button clicked - reloading shots.");
        getPresenter().fetchShots();
    }

}
