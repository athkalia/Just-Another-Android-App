package com.example.util.runner;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.AccessibilityChecks;
import android.support.test.runner.AndroidJUnitRunner;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import com.example.util.rx.RxIdlingScheduler;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

public class EspressoTestRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        enableAccessibilityChecks();
        dismissLockAndTurnScreenOn();
        monitorRxSchedulerForIdleness();
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

    private void monitorRxSchedulerForIdleness() {
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> convertToIdlingScheduler(schedulerCallable.call()));
    }

    private static RxIdlingScheduler convertToIdlingScheduler(Scheduler scheduler) {
        RxIdlingScheduler rxIdlingResource = new RxIdlingScheduler(scheduler);
        Espresso.registerIdlingResources(rxIdlingResource.getCountingIdlingResource());
        return rxIdlingResource;
    }

}
