package com.example.util;

public final class TestUtil {

    private TestUtil() {

        throw new AssertionError();
    }

    /**
     * Hacky way to be able to say whether espresso tests are running or not. We are checking for one of the espresso classes in the vm.
     * If it's not loaded it means that we are not running espresso tests.
     */
    public static boolean areEspressoTestsRunning() {

        boolean testsAreRunning = false;
        try {
            Class.forName("com.example.util.EspressoTestRunner");
            testsAreRunning = true;
        } catch (ClassNotFoundException e) {
            testsAreRunning = false;
        }
        return testsAreRunning;
    }

}
