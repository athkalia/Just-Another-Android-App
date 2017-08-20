package com.example.features.dashboard.dagger;

import com.example.features.dashboard.model.ShotMapper;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public MainPresenter providesMainPresenter(RestService restService, AnalyticsHelper analyticsHelper, ShotMapper shotMapper) {
        return new MainPresenter(restService, analyticsHelper, shotMapper);
    }

}
