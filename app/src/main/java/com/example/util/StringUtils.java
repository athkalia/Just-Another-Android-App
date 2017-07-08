package com.example.util;

import javax.annotation.Nullable;

/**
 * Class to be used on code that shouldn't have any android dependencies so that we can run unit tests without Robolectric. Usually one
 * could instead use methods from {@link android.text.TextUtils}.
 */
public final class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(@Nullable CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

}
