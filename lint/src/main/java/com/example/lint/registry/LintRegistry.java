package com.example.lint.registry;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.example.lint.detectors.AndroidLogDetector;
import com.example.lint.detectors.HardcodedColorsDetector;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class LintRegistry extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return ImmutableList.of(AndroidLogDetector.ISSUE,
                HardcodedColorsDetector.ISSUE);
    }

}
