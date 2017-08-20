package com.example.tools.dagger.components;

import com.example.App;
import com.example.dagger.modules.BuildTypeAwareModule;
import com.example.networking.BaseUrlInterceptor;
import com.example.networking.RestService;
import com.example.tools.dagger.modules.ActivityBindingModule;
import com.example.tools.dagger.modules.AndroidModule;
import com.example.tools.dagger.modules.ApplicationModule;
import com.example.tools.dagger.modules.NetworkModule;
import com.example.util.testing.ForTestingPurposes;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import okreplay.OkReplayInterceptor;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBindingModule.class,
        ApplicationModule.class,
        NetworkModule.class,
        AndroidModule.class,
        BuildTypeAwareModule.class})
public interface ApplicationComponent {

    void inject(App application);

    @ForTestingPurposes
    RestService restService();

    @ForTestingPurposes
    BaseUrlInterceptor baseUrlInterceptor();

    @ForTestingPurposes
    OkReplayInterceptor okReplayInterceptor();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(App app);

        ApplicationComponent build();
    }

}
