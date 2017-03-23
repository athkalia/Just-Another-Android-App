package com.example.tools.dagger.modules;

import android.content.res.AssetManager;
import com.crashlytics.android.answers.Answers;
import com.example.App;
import com.example.tools.analytics.AnalyticsHelper;
import com.example.tools.images.ImageLoader;
import com.example.tools.stetho.StethoTool;
import com.example.util.StethoToolImpl;
import com.example.util.other.PropertiesManager;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    public App providesApplication() {
        return app;
    }

    @Provides
    @Singleton
    public static PropertiesManager providesPropertyManager(AssetManager assetManager) {
        return new PropertiesManager(assetManager);
    }

    @Provides
    @Singleton
    public static AnalyticsHelper providesAnalyticsHelper() {
        return new AnalyticsHelper(Answers.getInstance());
    }

    @Provides
    @Singleton
    public static StethoTool providesStetho(App application) {
        return new StethoToolImpl(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public static ImageLoader providesImageLoader(App application) {
        return new ImageLoader(application.getApplicationContext());
    }

}
