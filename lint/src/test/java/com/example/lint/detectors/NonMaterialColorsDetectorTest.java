package com.example.lint.detectors;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.example.lint.util.AbstractDetectorTest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NonMaterialColorsDetectorTest extends AbstractDetectorTest {

    @Override
    protected Detector getDetector() {
        return new NonMaterialColorsDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(NonMaterialColorsDetector.ISSUE);
    }

    @Override
    protected String getTestResourceDirectory() {
        return "non_material_color_test_cases";
    }

    @Test
    public void test_should_not_trigger_when_material_color_used_in_allowed_file() throws Exception {
        String file = "layout/colors__allowed_from_palette.xml";
        assertThat(lintFiles(file)).isEqualTo(NO_WARNINGS);
    }

    @Test
    @SuppressWarnings("checkstyle:LineLength")
    public void test_should_trigger_when_other_than_material_color_used_in_allowed_file() throws Exception {
        String file = "values/colors__allowed_from_palette.xml";
        String expectedOutcome = "values/colors__allowed_from_palette.xml:7: Error: Attempting to use a color reference out of the material colors palette file [NonMaterialColors]\n" +
                "    <color name=\"red_400\">@color/red</color>\n" +
                "    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "1 errors, 0 warnings\n";

        String outcome = lintFiles(file);

        // There's a weird invisible char that makes equalTo fail :/
        assertThat(outcome).isEqualToIgnoringWhitespace(expectedOutcome);
    }

}
