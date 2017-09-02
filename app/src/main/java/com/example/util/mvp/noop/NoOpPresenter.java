package com.example.util.mvp.noop;

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import javax.inject.Inject;

/**
 * An empty presenter for screens that require no presenter. (There should really be no screen like that in a normal project, but this is a
 * test project).
 */

public class NoOpPresenter extends MvpNullObjectBasePresenter<NoOpView> {

    @Inject
    @SuppressWarnings("PMD.UnnecessaryConstructor")
    public NoOpPresenter() {
        // used by dagger
    }

}
