package com.example.util;

import com.example.BuildConfig;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * A {@link RobolectricTestRunner} that allows us to set robolectric configuration in one place instead of setting it in each test class
 * via {@link Config}.
 */
public class PreconfiguredRobolectricTestRunner extends RobolectricTestRunner {

    private static final int SDK_API_LEVEL_TO_EMULATE = 23;

    public PreconfiguredRobolectricTestRunner(Class<?> klass) throws InitializationError {

        super(klass);
    }

    @Override
    protected Config buildGlobalConfig() {
        return new Config.Builder()
                .setSdk(SDK_API_LEVEL_TO_EMULATE)
                .setConstants(BuildConfig.class)
                .build();
    }

}
