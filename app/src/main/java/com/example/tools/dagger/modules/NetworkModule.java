package com.example.tools.dagger.modules;

import com.example.BuildConfig;
import com.example.networking.AuthenticationInterceptor;
import com.example.networking.BaseUrlInterceptor;
import com.example.util.other.PropertiesManager;
import com.example.util.testing.ForTestingPurposes;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.List;

@Module
public final class NetworkModule {

    private NetworkModule() {

        throw new AssertionError();
    }

    @Provides
    @Singleton
    public static HttpLoggingInterceptor providesHttpLoggingInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(PropertiesManager propertiesManager, HttpLoggingInterceptor httpLoggingInterceptor,
                                                   List<Interceptor> networkInterceptors, BaseUrlInterceptor baseUrlInterceptor) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        // Logs network calls for debug builds
        okHttpBuilder.addInterceptor(httpLoggingInterceptor);

        // Adds authentication headers when required in network calls
        okHttpBuilder.addInterceptor(new AuthenticationInterceptor(propertiesManager));

        // Helps with changing base url of network calls in espresso tests to the MockWebServer base url.
        okHttpBuilder.addInterceptor(baseUrlInterceptor);

        // For release builds nothing is added, the list is empty. For debug builds Stetho interceptor is added.
        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    public static Retrofit providesRetrofit(OkHttpClient okHttpClient, PropertiesManager propertiesManager) {

        return new Retrofit.Builder()
                .baseUrl(propertiesManager.getBaseUrl())
                .validateEagerly(BuildConfig.DEBUG)// Fail early: check Retrofit configuration at creation time in Debug build.
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @ForTestingPurposes
    public static BaseUrlInterceptor providesBaseUrlInterceptor(PropertiesManager propertiesManager) {
        return new BaseUrlInterceptor(propertiesManager.getBaseUrl());
    }

}
