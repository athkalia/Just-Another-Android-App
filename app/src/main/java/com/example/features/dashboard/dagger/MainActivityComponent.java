package com.example.features.dashboard.dagger;

import com.example.features.dashboard.view.MainActivity;
import com.example.tools.dagger.components.ApplicationComponent;
import com.example.tools.dagger.components.BaseActivityComponent;
import com.example.tools.dagger.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent extends BaseActivityComponent<MainActivity> {

}
