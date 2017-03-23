package com.example.features.dashboard.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import com.example.model.Shot;
import com.example.util.other.ToastDuration;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

public interface MainView extends MvpView {

    void displayShotsList(List<Shot> shots);

    void showLoadingBar();

    void showLoadingFailureError();

    void showToast(String toastMessage, @ToastDuration int toastDuration);

    @RequiresApi(api = Build.VERSION_CODES.N)
    void requestActiveTileUpdate();
}
