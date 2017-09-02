package com.example.tools.dagger.modules;

import com.example.features.dashboard.dagger.MainActivityModule;
import com.example.features.dashboard.view.MainActivity;
import com.example.features.runtimepermissions.view.RuntimePermissionsActivity;
import com.example.tools.dagger.scopes.ActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract RuntimePermissionsActivity contributeRuntimePermissionsActivity();

}
