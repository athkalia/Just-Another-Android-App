package com.example.tools.timber;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import timber.log.Timber;

import javax.annotation.Nullable;

/**
 * A logging tree that logs {@link Timber#wtf} methods to crashlytics. This tree does not log to logcat at all.
 */
public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return priority == Log.ASSERT;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (isLoggable(tag, priority)) {
            if (throwable != null) {
                Crashlytics.logException(throwable);
            }
            if (message != null) {
                Crashlytics.log(message);
            }
        }
    }

}
