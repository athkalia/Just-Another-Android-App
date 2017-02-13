package com.example.util;

import android.support.test.espresso.contrib.AccessibilityChecks;
import android.support.test.runner.AndroidJUnitRunner;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

public class EspressoTestRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        enableAccessibilityChecks();
        dismissLockAndTurnScreenOn();
        super.onStart();
    }

    /**
     * Run some automated accessibility checks. See https://google.github.io/android-testing-support-library/docs/accesibility-checking
     */
    private void enableAccessibilityChecks() {
        AccessibilityChecks.enable();
    }

    private void dismissLockAndTurnScreenOn() {
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback((activity, stage) -> {
            if (stage == Stage.PRE_ON_CREATE) {
                activity.getWindow().addFlags(FLAG_DISMISS_KEYGUARD | FLAG_TURN_SCREEN_ON | FLAG_KEEP_SCREEN_ON);
            }
        });
    }

}
