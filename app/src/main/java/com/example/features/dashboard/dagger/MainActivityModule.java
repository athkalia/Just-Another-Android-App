package com.example.features.dashboard.dagger;

import android.content.Context;
import com.example.features.dashboard.view.MainActivity;
import com.example.tools.dagger.scopes.ActivityContext;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {

    @Binds
    @ActivityContext
    public abstract Context providesActivityContext(MainActivity mainActivity);

}
