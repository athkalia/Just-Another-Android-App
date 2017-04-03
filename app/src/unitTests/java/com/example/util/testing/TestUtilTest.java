package com.example.util.testing;

import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class TestUtilTest {

    @Test
    public void should_know_that_espresso_tests_are_not_running_when_unit_tests_are_running() {
        assertThat(TestUtil.areEspressoTestsRunning()).isEqualTo(false);
    }

}
