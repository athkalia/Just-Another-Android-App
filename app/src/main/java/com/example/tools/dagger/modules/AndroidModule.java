package com.example.tools.dagger.modules;

import android.content.res.AssetManager;
import android.content.res.Resources;
import com.example.App;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public final class AndroidModule {

    private AndroidModule() {
        throw new AssertionError();
    }

    @Provides
    @Singleton
    public static Resources providesResources(App application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    public static AssetManager providesAssetManager(Resources resources) {
        return resources.getAssets();
    }

}
