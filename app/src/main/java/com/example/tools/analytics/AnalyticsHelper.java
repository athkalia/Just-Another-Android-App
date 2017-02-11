package com.example.tools.analytics;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class AnalyticsHelper {

    private final Answers answers;

    public AnalyticsHelper(Answers answers) {

        this.answers = answers;
    }

    public void logEvent(CustomEvent customEvent) {

        answers.logCustom(customEvent);
    }

}
