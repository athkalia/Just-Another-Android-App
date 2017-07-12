package com.example.util;

import com.squareup.burst.BurstJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BurstJUnit4.class)
public class StringUtilsTest {

    enum IsEmptyTestCases {
        CASE_1("", true),
        CASE_2("    ", false),
        CASE_3("ertert", false),
        CASE_4(null, true);

        final String stringToCheck;
        final boolean expectedResult;

        IsEmptyTestCases(String s, boolean expectedResult) {
            this.stringToCheck = s;
            this.expectedResult = expectedResult;
        }
    }

    @Test
    public void test_is_empty(IsEmptyTestCases testCase) {
        // When
        boolean result = StringUtils.isEmpty(testCase.stringToCheck);

        // Then
        assertThat(result).isEqualTo(testCase.expectedResult);
    }

}
