package com.example.features.dashboard.view;

import android.support.annotation.VisibleForTesting;
import com.example.App;
import com.example.features.dashboard.dagger.DaggerMainActivityComponent;
import com.example.features.dashboard.dagger.MainActivityComponent;
import com.example.util.testing.ForTestingPurposes;

import javax.annotation.Nullable;

final class ComponentFactory {

    @ForTestingPurposes @Nullable
    private static MainActivityComponent mainActivityComponent;

    private ComponentFactory() {
        throw new AssertionError();
    }

    public static MainActivityComponent getMainActivityComponent() {
        if (mainActivityComponent != null) {
            return mainActivityComponent;
        } else {
            return DaggerMainActivityComponent.builder()
                    .applicationComponent(App.getApplicationComponent())
                    .build();
        }
    }

    @ForTestingPurposes
    @VisibleForTesting
    static void setMainActivityComponent(MainActivityComponent mainActivityComponent) {
        ComponentFactory.mainActivityComponent = mainActivityComponent;
    }

}
