package com.example.util.mvp.noop;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import javax.inject.Inject;

/**
 * Just a no-op {@link ViewState} for screens that we don't want a view state in.
 */
public class NoOpViewState<VIEW extends MvpView> implements ViewState<VIEW> {

    @Inject
    @SuppressWarnings("PMD.UnnecessaryConstructor")
    public NoOpViewState() {
        // used by dagger
    }

    @Override
    public void apply(VIEW view, boolean retained) {
        // No op
    }

}
