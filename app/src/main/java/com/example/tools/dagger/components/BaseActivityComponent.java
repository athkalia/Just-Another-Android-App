package com.example.tools.dagger.components;

import android.support.v7.app.AppCompatActivity;

/**
 * Base Component that all components injecting into an activity should extend from.
 */
public interface BaseActivityComponent<ACTIVITY extends AppCompatActivity> {

    void inject(ACTIVITY activity);

}
