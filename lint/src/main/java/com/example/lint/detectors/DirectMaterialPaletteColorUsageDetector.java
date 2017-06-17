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

import java.util.Collection;
import java.util.EnumSet;

/**
 * This lint detector stops anyone from using colors from the material palette directly in the project. Instead one should be using
 * references from the list of allowed colors ("colors__allowed_from_palette.xml" file), and the logical groups color file ("colors.xml").
 */
public class DirectMaterialPaletteColorUsageDetector extends ResourceXmlDetector {

    private static final Class<? extends Detector> DETECTOR_CLASS = DirectMaterialPaletteColorUsageDetector.class;

    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.RESOURCE_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "DirectMaterialColorUsage";
    private static final String ISSUE_DESCRIPTION = "Attempting to use a palette color directly.";
    private static final String ISSUE_EXPLANATION = "Cannot directly use a color from the material palette " +
            "(\"colors__material_design_palette\" file). Please use colors from the list of allowed colors " +
            "(\"colors__allowed_from_palette.xml\" file), and the logical groups color file (\"colors.xml\").";
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
    public void visitAttribute(XmlContext xmlContext, Attr attr) {
        String fileName = xmlContext.file.getName();
        if (!fileName.equals(ALLOWED_COLORS_FILENAME)) {
            String value = attr.getValue();
            if (value.startsWith(PALETTE_COLOR_PREFIX)) {
                xmlContext.report(ISSUE, attr, xmlContext.getLocation(attr), ISSUE.getBriefDescription(TextFormat.TEXT));
            }
        }
    }

    @Override
    public Collection<String> getApplicableAttributes() {
        return XmlScanner.ALL;
    }

}
