package com.example.features.dashboard.view;

import com.example.model.Shot;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;
import timber.log.Timber;

import javax.annotation.Nullable;
import java.util.List;

public class MainActivityViewState implements ViewState<MainView> {

    @Nullable private List<Shot> shots;

    @Override
    public void apply(MainView view, boolean retained) {
        Timber.d("Restoring view state");
        if (shots != null) {
            Timber.d("Restoring shots: %s", shots);
            view.displayShotsList(shots);
        }
    }

    public void saveShots(List<Shot> shots) {
        Timber.d("Saving shots: %s", shots);
        this.shots = shots;
    }

}
