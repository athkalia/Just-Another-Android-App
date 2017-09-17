package com.example.util;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Override RxJava schedulers in tests to avoid threading issues.
 */
public class RxJavaSchedulersOverrideTestRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setComputationSchedulerHandler(__ -> Schedulers.trampoline());
                RxAndroidPlugins.setMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

}
