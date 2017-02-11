package com.example.features.dashboard.view;

import com.example.model.Shot;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface MainView extends MvpView {

    void displayShotsList(List<Shot> shots);

    void showLoadingBar();

    void showLoadingFailureError();

}
