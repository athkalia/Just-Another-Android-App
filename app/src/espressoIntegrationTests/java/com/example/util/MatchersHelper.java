package com.example.util;

import android.support.annotation.IdRes;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.v7.widget.RecyclerView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public final class MatchersHelper {

    private MatchersHelper() {
        throw new AssertionError();
    }

    public static ViewAssertion recyclerViewShouldHaveItemCount(int count) {
        return (view, noViewFoundException) -> {
            final RecyclerView recyclerView = (RecyclerView) view;
            final int actualCount = recyclerView.getAdapter().getItemCount();
            if (actualCount != count) {
                throw new AssertionError("RecyclerView has " + actualCount + " while expected " + count);
            }
        };
    }

    public static ViewInteraction onViewWithId(@IdRes int resourceId) {
        return onView(withId(resourceId));
    }

}
