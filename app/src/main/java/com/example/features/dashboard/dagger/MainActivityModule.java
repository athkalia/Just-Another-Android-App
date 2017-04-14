package com.example.features.dashboard.dagger;

import com.example.features.dashboard.model.ShotMapper;
import com.example.features.dashboard.presenter.MainPresenter;
import com.example.features.dashboard.view.MainActivityViewState;
import com.example.features.dashboard.view.ShotViewHolder;
import com.example.features.dashboard.view.ShotViewHolderFactory;
import com.example.features.dashboard.view.ShotsAdapter;
import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.tools.images.ImageLoader;
import com.example.util.mvp.base.Mapper;
import com.example.util.view.ViewHolderFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public MainActivityViewState providesMainActivityViewState() {
        return new MainActivityViewState();
    }

    @Provides
    public ShotsAdapter providesShotsAdapter(ViewHolderFactory<ShotViewHolder> shotViewHolderFactory) {
        return new ShotsAdapter(shotViewHolderFactory);
    }

    @Provides
    public MainPresenter providesMainPresenter(RestService restService, AnalyticsHelper analyticsHelper,
                                               Mapper<ShotResponse, Shot> shotMapper) {
        return new MainPresenter(restService, analyticsHelper, shotMapper);
    }

    @Provides
    public Mapper<ShotResponse, Shot> providesShotMapper() {
        return new ShotMapper();
    }

    @Provides
    public ViewHolderFactory<ShotViewHolder> providesViewHolderFactory(ImageLoader imageLoader) {
        return new ShotViewHolderFactory(imageLoader);
    }

}
