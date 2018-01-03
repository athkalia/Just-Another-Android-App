package JustAnotherAndroidApp.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.schedule

object JustAnotherAndroidApp_DevelopBranchNightlyPipeline : BuildType({
    uuid = "27e996fd-e80a-42cd-b6cd-5794cc45e3cf"
    id = "JustAnotherAndroidApp_DevelopBranchNightlyPipeline"
    name = "Develop Branch Nightly Pipeline"

    enablePersonalBuilds = false
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

    }

    steps {
        step {
            name = "Run all unit tests with coverage"
            type = "JustAnotherAndroidApp_UnitTestsWithCoverage"
        }
        step {
            name = "Build APKs"
            type = "JustAnotherAndroidApp_BuildAPKs"
        }
        step {
            name = "Run all espresso tests"
            type = "JustAnotherAndroidApp_RunEspressoTestsInFirebase"
            param("RELATIVE_PATH_INSTRUMENTATION_APK_NAME", "app/build/outputs/apk/app-debug-androidTest.apk")
            param("RELATIVE_PATH_APP_APK_NAME", "app/build/outputs/apk/app-debug.apk")
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
            name = "Upload Nightly Debug APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "Last commit in this nightly build: %build.vcs.number.HttpsGithubComAthkaliaJustAnotherAndroidAppGit%")
            param("APK_NAME", "app/build/outputs/apk/app-debug.apk")
            param("HOCKEYAPP_APP_ID", "9037a30d86714c4baa869321725d7854")
        }
        step {
            name = "Upload Nightly Qa APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "Last commit in this nightly build: %build.vcs.number.HttpsGithubComAthkaliaJustAnotherAndroidAppGit%")
            param("APK_NAME", "app/build/outputs/apk/app-qa.apk")
            param("HOCKEYAPP_APP_ID", "6f4918dfffbd41b3b8ba55a3fde1f221")
        }
        step {
            name = "Upload Nightly Release APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "Last commit in this nightly build: %build.vcs.number.HttpsGithubComAthkaliaJustAnotherAndroidAppGit%")
            param("APK_NAME", "app/build/outputs/apk/app-release.apk")
            param("HOCKEYAPP_APP_ID", "0f7e1016fd844b5a840b4960df6d89a4")
        }
        step {
            name = "Upload Nightly Release APK to alpha channel in Playstore"
            type = "JustAnotherAndroidApp_UploadApkToPlaystore"
            param("Playstore service account key location", "/opt/teamcity/playstoreServiceAccountKey.p12")
            param("Playstore Release Track", "alpha")
        }
    }

    triggers {
        schedule {
            schedulingPolicy = daily {
                hour = 0
            }
            branchFilter = """
                -:*
                +:<default>
            """.trimIndent()
            triggerBuild = always()
            param("revisionRule", "lastSuccessful")
            param("cronExpression_dw", "*")
            param("revisionRuleBuildBranch", "develop")
            param("cronExpression_min", "*")
            param("dayOfWeek", "Sunday")
            param("revisionRuleDependsOn", "JustAnotherAndroidApp_BuildAPKs")
        }
    }
})
