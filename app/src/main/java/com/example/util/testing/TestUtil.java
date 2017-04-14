package com.example.util.testing;

@ForTestingPurposes
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
            Class.forName("com.example.util.runner.EspressoTestRunner");
            testsAreRunning = true;
        } catch (ClassNotFoundException ignored) {
            testsAreRunning = false;
        }
        return testsAreRunning;
    }

    /**
     * Hacky way to be able to say whether espresso tests are running or not. We are checking for one of the espresso classes in the vm.
     * If it's not loaded it means that we are not running espresso tests.
     */
    public static boolean areRobolectricTestsRunning() {
        boolean testsAreRunning = false;
        try {
            Class.forName("com.example.util.PreconfiguredRobolectricTestRunner");
            testsAreRunning = true;
        } catch (ClassNotFoundException ignored) {
            testsAreRunning = false;
        }
        return testsAreRunning;
    }

}
