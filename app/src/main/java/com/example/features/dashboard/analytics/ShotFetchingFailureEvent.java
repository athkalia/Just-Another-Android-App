package com.example.features.dashboard.analytics;

import com.crashlytics.android.answers.CustomEvent;

public class ShotFetchingFailureEvent extends CustomEvent {

    private static final String EVENT_NAME = "Failed to fetch shots";

    public ShotFetchingFailureEvent() {

        super(EVENT_NAME);
    }

}
