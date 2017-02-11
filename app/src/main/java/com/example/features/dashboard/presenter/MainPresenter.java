package com.example.features.dashboard.presenter;

import android.support.test.espresso.idling.CountingIdlingResource;
import com.example.features.dashboard.analytics.FetchShotsEvent;
import com.example.features.dashboard.analytics.ShotFetchingFailureEvent;
import com.example.features.dashboard.view.MainView;
import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.util.mvp.base.Mapper;
import com.example.util.mvp.base.MvpNullObjectBasePresenter;
import com.example.util.rx.RxSchedulers;
import rx.Observable;
import timber.log.Timber;

import javax.inject.Inject;

public class MainPresenter extends MvpNullObjectBasePresenter<MainView> {

    private final RestService restService;
    private final RxSchedulers rxSchedulers;
    private final AnalyticsHelper analyticsHelper;
    private final Mapper<ShotResponse, Shot> shotMapper;
    private final CountingIdlingResource countingIdlingResource;

    @Inject
    public MainPresenter(RestService restService, RxSchedulers rxSchedulers, AnalyticsHelper analyticsHelper, Mapper<ShotResponse,
            Shot> shotMapper, CountingIdlingResource countingIdlingResource) {
        this.restService = restService;
        this.rxSchedulers = rxSchedulers;
        this.analyticsHelper = analyticsHelper;
        this.shotMapper = shotMapper;
        this.countingIdlingResource = countingIdlingResource;
    }

    public void fetchShots() {
        Timber.i("Fetching shots..");
        analyticsHelper.logEvent(new FetchShotsEvent());
        getView().showLoadingBar();
        countingIdlingResource.increment();
        restService.getShots()
                .doAfterTerminate(countingIdlingResource::decrement)
                .subscribeOn(rxSchedulers.getIoScheduler())
                .toObservable()
                .flatMap(Observable::from)
                .filter(shot -> shot != null && shot.getImagesData() != null && shot.getImagesData().getTeaserImageUrl() != null)
                .map(shotMapper::map)
                .observeOn(rxSchedulers.getAndroidMainThreadScheduler())
                .toList()
                .subscribe(shots -> {
                    getView().displayShotsList(shots);
                }, throwable -> {
                    analyticsHelper.logEvent(new ShotFetchingFailureEvent());
                    Timber.e(throwable, "An error occurred while fetching shots.");
                    getView().showLoadingFailureError();
                });
    }

}
