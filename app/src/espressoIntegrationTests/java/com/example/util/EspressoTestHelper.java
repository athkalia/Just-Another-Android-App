package com.example.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import com.google.android.libraries.cloudtesting.screenshots.ScreenShotter;
import com.metova.cappuccino.animations.SystemAnimations;
import com.squareup.spoon.Spoon;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

public class EspressoTestHelper {

    protected static final Intent NO_INTENT = null;

    /**
     * Enabling and disabling animations requires a permission that can only be granted via ADB. This is done by the
     * 'grant_animation_permission.gradle' script, but the permission may not be there when testing on firebase cloud services still.
     * In that case we just don't do anything about the animations.
     */
    @BeforeClass
    public static void beforeClass() {
        Context context = InstrumentationRegistry.getContext();
        if (context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE) == PackageManager.PERMISSION_GRANTED) {
            SystemAnimations.disableAll(context);
        }
    }

    /**
     * Enabling and disabling animations requires a permission that can only be granted via ADB. This is done by the
     * 'grant_animation_permission.gradle' script, but the permission may not be there when testing on firebase cloud services still.
     * In that case we just don't do anything about the animations.
     */
    @AfterClass
    public static void enableAnimations() {
        Context context = InstrumentationRegistry.getContext();
        if (context.checkCallingOrSelfPermission(Manifest.permission.SET_ANIMATION_SCALE) == PackageManager.PERMISSION_GRANTED) {
            SystemAnimations.enableAll(context);
        }
    }

    /**
     * Takes a screenshot for spoon and for firebase cloud testing. Firebase screenshots are only available in firebase console.
     * Spoon screenshots are available in generated reports locally, inside 'build/spoon' folder.
     */
    protected void takeScreenshot(String screenshotName, Activity activity) {
        ScreenShotter.takeScreenshot(screenshotName, activity); // firebase cloud testing
//        Spoon.screenshot(activity, screenshotName); // spoon
    }

    protected void checkViewIsNotVisible(@IdRes int resourceId) {
        onView(withId(resourceId))
                .check(matches(not(isDisplayed())));
    }

    protected void checkViewIsVisible(@IdRes int resourceId) {
        onView(withId(resourceId))
                .check(matches(isDisplayed()));
    }

    protected void clickOnView(@IdRes int resourceId) {
        onView(withId(resourceId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

}
