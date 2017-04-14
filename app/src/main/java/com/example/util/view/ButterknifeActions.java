package com.example.util.view;

import android.view.View;
import butterknife.ButterKnife;

/**
 * In this file we store butterknife actions, that allow us to perform operations on a list of views.
 * See http://jakewharton.github.io/butterknife/ for more details.
 */
public final class ButterknifeActions {

    public static final ButterKnife.Action<View> SET_VISIBILITY_TO_GONE = (view, index) -> view.setVisibility(View.GONE);

    public static final ButterKnife.Action<View> SET_VISIBILITY_TO_VISIBLE = (view, index) -> view.setVisibility(View.VISIBLE);

    private ButterknifeActions() {
        throw new AssertionError();
    }

}
