package com.example.lint.detectors;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.TextFormat;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * This lint detector stops anyone from using the android Log classes. We should be using Timber instead.
 */
public class AndroidLogDetector extends Detector implements Detector.JavaPsiScanner {

    private static final Class<? extends Detector> DETECTOR_CLASS = AndroidLogDetector.class;

    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;

    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );

    private static final String ISSUE_ID = "InvalidLogStatement";
    private static final String ISSUE_DESCRIPTION = "Don't use Android Log class methods.";
    private static final String ISSUE_EXPLANATION = "We are using Timber in this project. Please replace android log statements with that. "
            + "For example replace 'Log.d(..)' calls with 'Timber.d(..)' calls";
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    private static final int ISSUE_PRIORITY = 4;
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

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("tag", "format", "v", "d", "i", "w", "e", "wtf");
    }

    @Override
    public void visitMethod(JavaContext javaContext, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        PsiReferenceExpression methodExpression = call.getMethodExpression();
        String fullyQualifiedMethodName = methodExpression.getQualifiedName();
        if (fullyQualifiedMethodName.startsWith("android.util.Log.")) {
            javaContext.report(ISSUE, javaContext.getLocation(methodExpression), ISSUE.getBriefDescription(TextFormat.TEXT));
        }
    }

}
