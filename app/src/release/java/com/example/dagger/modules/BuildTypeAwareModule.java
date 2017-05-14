package com.example.dagger.modules;

import com.example.networking.RestService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Module
public final class BuildTypeAwareModule {

    private BuildTypeAwareModule() {
        throw new AssertionError();
    }

    /**
     * For release variant an empty list of interceptors is injected into OkHttp. In debug and qa builds an actual interceptor is injected
     * so that Stetho can work. See the debug version of this file under /debug.
     */
    @Provides
    public static List<Interceptor> provideOkHttpNetworkInterceptors() {
        return Collections.emptyList();
    }

    @Provides
    @Singleton
    public static RestService providesRestService(@Nonnull Retrofit retrofit) {
        return retrofit.create(RestService.class);
    }

}
