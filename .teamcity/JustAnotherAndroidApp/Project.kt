package JustAnotherAndroidApp

import JustAnotherAndroidApp.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "4327d4b5-598e-47df-9a7a-044d28cd1356"
    id = "JustAnotherAndroidApp"
    parentId = "_Root"
    name = "Just Another Android App"

    buildType(JustAnotherAndroidApp_DevelopBranchNightlyPipeline)
    buildType(JustAnotherAndroidApp_ReleaseBranchToPlaystoreBeta)
    buildType(JustAnotherAndroidApp_PullRequestJob)

    features {
        feature {
            id = "PROJECT_EXT_1"
            type = "ReportTab"
            param("buildTypeId", "JustAnotherAndroidApp_DevelopBranchNightlyPipeline")
            param("startPage", "dex_count/debugChart/index.html")
            param("revisionRuleName", "lastSuccessful")
            param("revisionRuleRevision", "latest.lastSuccessful")
            param("title", "Dex Debug method count")
            param("type", "ProjectReportTab")
        }
        feature {
            id = "PROJECT_EXT_14"
            type = "ReportTab"
            param("startPage", "dex_count/debugChart/index.html")
            param("title", "Dex Debug method count")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_15"
            type = "ReportTab"
            param("startPage", "dex_count/qaChart/index.html")
            param("title", "Dex Qa method count")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_16"
            type = "ReportTab"
            param("startPage", "dex_count/releaseChart/index.html")
            param("title", "Dex Release method count")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_19"
            type = "ReportTab"
            param("startPage", "static_analysis/checkstyle/checkstyle.html")
            param("title", "Checkstyle Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_2"
            type = "ReportTab"
            param("buildTypeId", "JustAnotherAndroidApp_DevelopBranchNightlyPipeline")
            param("startPage", "dex_count/qaChart/index.html")
            param("revisionRuleName", "lastSuccessful")
            param("revisionRuleRevision", "latest.lastSuccessful")
            param("title", "Dex Qa method count")
            param("type", "ProjectReportTab")
        }
        feature {
            id = "PROJECT_EXT_20"
            type = "ReportTab"
            param("startPage", "test_results/testDebugUnitTest/index.html")
            param("title", "Debug Unit Tests Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_21"
            type = "ReportTab"
            param("startPage", "test_results/testQaUnitTest/index.html")
            param("title", "Qa Unit Tests Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_22"
            type = "ReportTab"
            param("startPage", "test_results/testReleaseUnitTest/index.html")
            param("title", "Release Unit Test Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_24"
            type = "ReportTab"
            param("startPage", "static_analysis/findbugs/findbugs.html")
            param("title", "Findbugs Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_25"
            type = "ReportTab"
            param("startPage", "static_analysis/pmd/pmd.html")
            param("title", "PMD Report")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_26"
            type = "ReportTab"
            param("startPage", "static_analysis/lint/lint-results.html")
            param("title", "Lint")
            param("type", "BuildReportTab")
        }
        feature {
            id = "PROJECT_EXT_3"
            type = "ReportTab"
            param("buildTypeId", "JustAnotherAndroidApp_DevelopBranchNightlyPipeline")
            param("startPage", "dex_count/releaseChart/index.html")
            param("revisionRuleName", "lastSuccessful")
            param("revisionRuleRevision", "latest.lastSuccessful")
            param("title", "Dex Release method count")
            param("type", "ProjectReportTab")
        }
        versionedSettings {
            id = "PROJECT_EXT_30"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = "HttpsGithubComAthkaliaJustAnotherAndroidAppGit"
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
        feature {
            id = "PROJECT_EXT_40"
            type = "IssueTracker"
            param("secure:password", "")
            param("name", "athkalia/Just-Another-Android-App")
            param("pattern", """#(\d+)""")
            param("authType", "anonymous")
            param("repository", "https://github.com/athkalia/Just-Another-Android-App")
            param("type", "GithubIssues")
            param("secure:accessToken", "")
            param("username", "")
        }
    }
})
