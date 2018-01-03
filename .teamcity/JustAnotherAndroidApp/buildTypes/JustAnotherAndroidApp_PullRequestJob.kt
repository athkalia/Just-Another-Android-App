package JustAnotherAndroidApp.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaDuplicates
import jetbrains.buildServer.configs.kotlin.v10.IdeaDuplicates.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object JustAnotherAndroidApp_PullRequestJob : BuildType({
    uuid = "939302c0-c087-4187-b962-1f1fd01bb54b"
    extId = "JustAnotherAndroidApp_PullRequestJob"
    name = "Pull Request Job"
    description = "Job running on every pull request to verify correctness"

    artifactRules = """
        app\build\outputs\mapping\qa\mapping.txt => mapping_files\qa
        app\build\outputs\mapping\release\mapping.txt => mapping_files\release
        app\build\outputs\dexcount => dex_count
        app\build\outputs\apk => apks
        app\build\reports\tests => test_results
        app\build\reports\jacoco => test_coverage
        app\build\reports\checkstyle => static_analysis\checkstyle
        app\build\reports\pmd => static_analysis\pmd
        app\build\reports\findbugs => static_analysis\findbugs
        app\build\outputs\apksize\debug\debug.csv => apk_size_reports\debug
        app\build\outputs\apksize\qa\qa.csv => apk_size_reports\qa
        app\build\outputs\apksize\release\release.csv => apk_size_reports\release
        app\build\reports\lint-results.html => static_analysis\lint
    """.trimIndent()

    vcs {
        root("HttpsGithubComAthkaliaJustAnotherAndroidAppGit")

        buildDefaultBranch = false
    }

    steps {
        step {
            name = "Run all unit tests with coverage"
            type = "JustAnotherAndroidApp_UnitTestsWithCoverage"
        }
        step {
            name = "Run all static analysis tools"
            type = "JustAnotherAndroidApp_StaticAnalysis"
        }
        step {
            name = "Build APKs"
            type = "JustAnotherAndroidApp_BuildAPKs"
        }
        step {
            name = "Run all espresso tests"
            type = "JustAnotherAndroidApp_RunEspressoTestsInFirebase"
            param("RELATIVE_PATH_APP_APK_NAME", "app/build/outputs/apk/app-debug.apk")
            param("RELATIVE_PATH_INSTRUMENTATION_APK_NAME", "app/build/outputs/apk/app-debug-androidTest.apk")
        }
        step {
            name = "Perform method count for all build types"
            type = "JustAnotherAndroidApp_DexMethodCount"
        }
        ideaDuplicates {
            name = "Check for duplicates"
            pathToProject = "build.gradle"
            jvmArgs = "-Xmx1G -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
            lowerBound = 10
            discardCost = 0
            distinguishMethods = true
            distinguishTypes = true
            distinguishLiterals = true
            extractSubexpressions = true
        }
        step {
            name = "Upload PR Debug APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From [Pull Request %teamcity.build.branch%](https://github.com/athkalia/Just-Another-Android-App/%teamcity.build.branch%)")
            param("APK_NAME", "app/build/outputs/apk/app-debug.apk")
            param("HOCKEYAPP_APP_ID", "d0a502eeeb604ef7aef601f261d60a3b")
        }
        step {
            name = "Upload PR Qa APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From [Pull Request %teamcity.build.branch%](https://github.com/athkalia/Just-Another-Android-App/%teamcity.build.branch%)")
            param("APK_NAME", "app/build/outputs/apk/app-qa.apk")
            param("HOCKEYAPP_APP_ID", "81721a72596949079fbdf663c4862bef")
        }
        step {
            name = "Upload PR Release APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From [Pull Request %teamcity.build.branch%](https://github.com/athkalia/Just-Another-Android-App/%teamcity.build.branch%)")
            param("APK_NAME", "app/build/outputs/apk/app-release.apk")
            param("HOCKEYAPP_APP_ID", "65fe0f7b567a4a9d937f0a7c2d32955b")
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 180 // Wait for 3 mins before running a PR build.
            branchFilter = """
                -:*
                +:pull/*
            """.trimIndent()
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = "HttpsGithubComAthkaliaJustAnotherAndroidAppGit"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "zxx688a918ef67155109118170e6b41b8bbd358523b8b5d726f7f7f622f1c3a1f83701d3a0adc7d6462285c2cfea05493f0d502822302c0240a"
                }
            }
        }
    }
})
