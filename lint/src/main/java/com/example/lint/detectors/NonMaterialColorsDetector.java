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
import org.w3c.dom.Element;

import java.util.Collection;
import java.util.EnumSet;

/**
 * This lint detector stops anyone from using non-material colors in the project. It checks that the file with the allowed colors for the
 * project ("colors__allowed_from_palette.xml") only contains material color references from the "colors__material_design_palette" file.
 */
public class NonMaterialColorsDetector extends ResourceXmlDetector {

    private static final Class<? extends Detector> DETECTOR_CLASS = NonMaterialColorsDetector.class;

    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "NonMaterialColors";
    private static final String ISSUE_DESCRIPTION = "Attempting to use a color reference out of the material colors palette file";
    private static final String ISSUE_EXPLANATION = "All colors in the file of allowed colors (\"colors__allowed_from_palette.xml\") should " +
            "contain a reference to one of the material colors from the palette (\"colors__material_design_palette\" file).";
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

    private static final String ALLOWED_COLORS_FILENAME = "colors__allowed_from_palette.xml";
    private static final String PALETTE_COLOR_PREFIX = "@color/material";

    @Override
    public void visitElement(XmlContext xmlContext, Element element) {
        String fileName = xmlContext.file.getName();
        if (fileName.equals(ALLOWED_COLORS_FILENAME)) {
            String elementValue = element.getFirstChild().getNodeValue();
            if (xmlElementIsNotTheParentResourceTag(elementValue) && !elementValue.startsWith(PALETTE_COLOR_PREFIX)) {
                xmlContext.report(ISSUE, element, xmlContext.getLocation(element), ISSUE.getBriefDescription(TextFormat.TEXT));
            }
        }
    }

    private boolean xmlElementIsNotTheParentResourceTag(String elementValue) {
        return !elementValue.equals("\n" +
                "\n" +
                "    ");
    }

    @Override
    public Collection<String> getApplicableElements() {
        return XmlScanner.ALL;
    }

}
