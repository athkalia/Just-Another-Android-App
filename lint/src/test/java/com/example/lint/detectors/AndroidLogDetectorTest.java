package com.example.lint.detectors;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.example.lint.util.AbstractDetectorTest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AndroidLogDetectorTest extends AbstractDetectorTest {

    @Override
    protected Detector getDetector() {
        return new AndroidLogDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(AndroidLogDetector.ISSUE);
    }

    @Override
    protected String getTestResourceDirectory() {
        return "android_log_detector_test_cases";
    }

    @Test
    public void test_detector_not_triggering() throws Exception {
        String file = "AndroidLogNotExistingTestCase.java";
        assertThat(lintFiles(file)).isEqualTo(NO_WARNINGS);
    }

    @Test
    public void test_detector_triggering() throws Exception {
        String file = "AndroidLogExistingTestCase.java";
        String expectedOutcome = "AndroidLogExistingTestCase.java:8: Error: Don't use Android Log class methods. [InvalidLogStatement]\n" +
                "        Log.d(\"tag\", \"log message\");\n" +
                "        ~~~~~\n" +
                "1 errors, 0 warnings\n";
        String outcome = lintFiles(file);

        // There's a weird invisible char that makes equalTo fail :/
        assertThat(outcome).isEqualToIgnoringWhitespace(expectedOutcome);
    }

}
