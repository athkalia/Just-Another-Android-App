package JustAnotherAndroidApp.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaDuplicates
import jetbrains.buildServer.configs.kotlin.v10.IdeaDuplicates.*
import jetbrains.buildServer.configs.kotlin.v10.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object JustAnotherAndroidApp_ReleaseBranchToPlaystoreBeta : BuildType({
    uuid = "436d08fa-9504-45a3-9f14-49cb8c2f7783"
    extId = "JustAnotherAndroidApp_ReleaseBranchToPlaystoreBeta"
    name = "Release branch to Playstore Beta"
    description = "Runs some checks, builds the release APK and deploys to beta testing"

    artifactRules = """
        app\build\outputs\mapping\qa\mapping.txt
        app\build\outputs\mapping\release\mapping.txt
        app\build\outputs\dexcount => app\build\outputs\dexcount
        app\build\outputs\apk => app\build\outputs\apk
        app\build\reports\tests => app\build\reports\tests
        app\build\reports\checkstyle => app\build\reports\checkstyle
        app\build\reports\pmd => app\build\reports\pmd
        app\build\reports\findbugs => app\build\reports\findbugs
        app\build\reports\lint-results.html
        app\build\outputs\apksize\debug\debug.csv => apk_size_reports\debug
        app\build\outputs\apksize\qa\qa.csv => apk_size_reports\qa
        app\build\outputs\apksize\release\release.csv => apk_size_reports\release
    """.trimIndent()

    vcs {
        root("HttpsGithubComAthkaliaJustAnotherAndroidAppGit")

        buildDefaultBranch = false
    }

    steps {
        step {
            name = "Run all static analysis tools"
            type = "JustAnotherAndroidApp_StaticAnalysis"
        }
        step {
            name = "Run all unit tests"
            type = "JustAnotherAndroidApp_UnitTests"
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
            name = "Upload Release branch Debug APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From Release branch %teamcity.build.branch%")
            param("APK_NAME", "app/build/outputs/apk/app-debug.apk")
            param("HOCKEYAPP_APP_ID", "9c6457c31e97406a9994963ab0d537a0")
        }
        step {
            name = "Upload Release branch QA APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From Release branch %teamcity.build.branch%")
            param("APK_NAME", "app/build/outputs/apk/app-qa.apk")
            param("HOCKEYAPP_APP_ID", "d50d17dbae194accb61286c74c1095a7")
        }
        step {
            name = "Upload Release branch Release APK to HockeyApp"
            type = "JustAnotherAndroidApp_UploadApkToHockeyApp"
            param("NOTES", "From Release branch %teamcity.build.branch%")
            param("APK_NAME", "app/build/outputs/apk/app-release.apk")
            param("HOCKEYAPP_APP_ID", "94bbf83b6dfc440dbd0a52795a2e60f6")
        }
        step {
            name = "Upload Nightly Release APK to beta channel in Playstore"
            type = "JustAnotherAndroidApp_UploadApkToPlaystore"
            param("Playstore service account key location", "/opt/teamcity/playstoreServiceAccountKey.p12")
            param("Playstore Release Track", "beta")
        }
    }

    triggers {
        vcs {
            quietPeriodMode = VcsTrigger.QuietPeriodMode.USE_CUSTOM
            quietPeriod = 28800
            branchFilter = """
                -:*
                +:release/*
            """.trimIndent()
        }
    }
})
