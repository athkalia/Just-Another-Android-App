package com.example.features.dashboard.analytics;

import com.crashlytics.android.answers.CustomEvent;

public class FetchShotsEvent extends CustomEvent {

    private static final String EVENT_NAME = "Network request to fetch shots triggered";

    public FetchShotsEvent() {

        super(EVENT_NAME);
    }

}
