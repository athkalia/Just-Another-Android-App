package com.example.lint.detectors;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.ResourceXmlDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.TextFormat;
import com.android.tools.lint.detector.api.XmlContext;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;

/**
 * This lint detector stops anyone from using the colors directly as a "#FFFFFFFF" format. Colors should be picked from the
 * "colors__allowed.xml" file instead (which are picked from colors__material_design.xml file themselves).
 */
public class HardcodedColorsDetector extends ResourceXmlDetector {

    private static final Class<? extends Detector> DETECTOR_CLASS = HardcodedColorsDetector.class;

    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "HardcodedColors";
    private static final String ISSUE_DESCRIPTION = "Don't use colors directly, link through colors file";
    private static final String ISSUE_EXPLANATION = "All colors used in the project should be referenced through our colors file, " +
            "'colors__material_design.xml' file instead.";
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    private static final int ISSUE_PRIORITY = 8;
    private static final Severity ISSUE_SEVERITY = Severity.ERROR;

    public static final Issue ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_DESCRIPTION,
            ISSUE_EXPLANATION,
            ISSUE_CATEGORY,
            ISSUE_PRIORITY,
            ISSUE_SEVERITY,
            IMPLEMENTATION
    );

    private static final String ONLY_FILE_ALLOWED_TO_HAVE_HARDCODED_COLORS = "colors__material_design.xml";
    private static final String HARDCODED_COLOR_PREFIX = "#";

    @Override
    public void visitAttribute(@Nonnull XmlContext xmlContext, Attr attr) {
        String fileName = xmlContext.file.getName();
        if (!fileName.equals(ONLY_FILE_ALLOWED_TO_HAVE_HARDCODED_COLORS)) {
            String value = attr.getValue();
            if (value.startsWith(HARDCODED_COLOR_PREFIX)) {
                xmlContext.report(ISSUE, attr, xmlContext.getLocation(attr), ISSUE.getBriefDescription(TextFormat.TEXT));
            }
        }
    }

    @Override
    public Collection<String> getApplicableElements() {
        return XmlScanner.ALL;
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        return XmlScanner.ALL;
    }

}