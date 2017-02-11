package com.example.util;

import android.support.test.espresso.contrib.AccessibilityChecks;
import android.support.test.runner.AndroidJUnitRunner;

public class EspressoTestRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        // Run some automated accessibility checks. See https://google.github.io/android-testing-support-library/docs/accesibility-checking
        AccessibilityChecks.enable();
        super.onStart();
    }

}
