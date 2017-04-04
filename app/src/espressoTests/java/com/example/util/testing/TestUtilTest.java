package com.example.util.testing;

import android.support.test.runner.AndroidJUnit4;
import com.example.util.EspressoTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class TestUtilTest extends EspressoTestHelper {

    @Test
    public void should_know_that_espresso_tests_are_running() {
        assertThat(TestUtil.areEspressoTestsRunning()).isTrue();
    }

}
