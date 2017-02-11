package com.example.dagger.modules;

import com.example.mock.MockRestService;
import com.example.networking.RestService;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Module
public final class BuildTypeAwareModule {

    private static final boolean MOCK_MODE_ENABLED = false;

    private BuildTypeAwareModule() {

        throw new AssertionError();
    }

    /**
     * Provides OkHttp interceptors for debug build. Allows us to inspect rest calls with Stetho. See https://github.com/facebook/stetho
     * for more info.
     */
    @Provides
    public static List<Interceptor> provideOkHttpNetworkInterceptors() {

        return Collections.singletonList(new StethoInterceptor());
    }

    @Provides
    @Singleton
    public static MockRetrofit providesMockRetrofit(@Nonnull Retrofit retrofit, @Nonnull NetworkBehavior behavior) {

        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Provides
    @Singleton
    public static MockRestService providesMockRestService(@Nonnull MockRetrofit mockRetrofit) {

        return new MockRestService(mockRetrofit);
    }

    @Provides
    @Singleton
    @SuppressWarnings("checkstyle:magicnumber")
    public static NetworkBehavior providesNetworkBehavior() {

        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setDelay(1, TimeUnit.SECONDS);
        networkBehavior.setVariancePercent(50); // Delay can vary by 50%.
        networkBehavior.setFailurePercent(20); // 20% of all calls are failing.
        return networkBehavior;
    }

    @Provides
    @Singleton
    public static RestService providesRestService(@Nonnull Retrofit retrofit, @Nonnull MockRestService mockRestService) {

        if (MOCK_MODE_ENABLED) {
            return mockRestService;
        } else {
            return retrofit.create(RestService.class);
        }
    }

}
