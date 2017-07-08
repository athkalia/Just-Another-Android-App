package com.example.features.dashboard.presenter;

import android.os.Build;
import android.widget.Toast;
import com.example.features.dashboard.analytics.FetchShotsEvent;
import com.example.features.dashboard.analytics.ShotFetchingFailureEvent;
import com.example.features.dashboard.view.MainView;
import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.util.mvp.base.Mapper;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import javax.annotation.Nullable;

public class MainPresenter extends MvpNullObjectBasePresenter<MainView> {

    private final RestService restService;
    private final AnalyticsHelper analyticsHelper;
    private final Mapper<ShotResponse, Shot> shotMapper;
    @Nullable private Disposable subscription;

    public MainPresenter(RestService restService, AnalyticsHelper analyticsHelper, Mapper<ShotResponse, Shot> shotMapper) {
        this.restService = restService;
        this.analyticsHelper = analyticsHelper;
        this.shotMapper = shotMapper;
    }

    public void fetchShots() {
        Timber.i("Fetching shots..");
        analyticsHelper.logEvent(new FetchShotsEvent());
        getView().showLoadingBar();
        subscription = restService.getShots()
                .subscribeOn(Schedulers.io())
                .flatMapObservable(Observable::fromIterable)
                .filter(shot -> shot.getImagesData() != null && shot.getImagesData().getTeaserImageUrl() != null)
                .map(shotMapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(shots -> getView().displayShotsList(shots), throwable -> {
                    analyticsHelper.logEvent(new ShotFetchingFailureEvent());
                    Timber.e(throwable, "An error occurred while fetching shots.");
                    getView().showLoadingFailureError();
                });
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance && subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    public void onUpdateTileMenuItemClicked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getView().requestActiveTileUpdate();
            getView().showToast("Active settings tile has been updated with a new value!", Toast.LENGTH_SHORT);
        } else {
            getView().showToast("Tile Service not available before API 24 (Nougat)", Toast.LENGTH_SHORT);
            Timber.i("Active tile update ignored, tile service is not available before API 24 (Nougat).");
        }
    }

}
