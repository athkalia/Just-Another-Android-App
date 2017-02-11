package com.example.tools.dagger.components;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import com.example.App;
import com.example.dagger.modules.BuildTypeAwareModule;
import com.example.networking.BaseUrlInterceptor;
import com.example.networking.RestService;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.tools.dagger.modules.AndroidModule;
import com.example.tools.dagger.modules.ApplicationModule;
import com.example.tools.dagger.modules.NetworkModule;
import com.example.tools.images.ImageLoader;
import com.example.util.rx.RxSchedulers;
import dagger.Component;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, AndroidModule.class, BuildTypeAwareModule.class})
public interface ApplicationComponent {

    RestService restService();

    RxSchedulers rxSchedulers();

    AnalyticsHelper analyticsHelper();

    App application();

    ImageLoader imageLoader();

    OkHttpClient okHttpClient();

    CountingIdlingResource countingIdlingResource();

    void inject(App application);

    @VisibleForTesting
    BaseUrlInterceptor baseUrlInterceptor();

}
