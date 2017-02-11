package com.example.util;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import com.example.App;
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

    @BeforeClass
    public static void setUpForClass() {
        Espresso.registerIdlingResources(App.getApplicationComponent().countingIdlingResource());
    }

    @AfterClass
    public static void tearDownForClass() {
        Espresso.unregisterIdlingResources(App.getApplicationComponent().countingIdlingResource());
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
