# *Please note that this this project is no longer maintained*

# Android Sample app with MVP architecture

Sample project that displays some images from the dribble API.
Demonstrates some cool stuff that you can do with the modern libraries and tooling in Android these days.

As someone said on reddit: "It's not over-engineered, it's just a skyscraper without the skyscraper part, just the foundations :)"


![1]

##
### Newest additions:
* Upgraded android gradle plugin to v3.0.1
* Added support for Spoon. See https://github.com/square/spoon for more details. Also added the gradle plugin for it.
One can run the tests with 'gradlew spoon' and then open up the generated reports in 'build/spoon' directory.
* Added screenshot grabbing while running espresso tests in Firebase
* Added test coverage for unit tests in teamcity CI Server
* Added a runtime permission example. Uses the PermissionsDispatcher library. See RuntimePermissionsActivity class.
(https://github.com/permissions-dispatcher/PermissionsDispatcher)
* Restructured dependencies a bit, check build.gradle file and dependencies.gradle file
* Added support for dagger android bindings (dagger v2.11)
* Added auto-factory library the little cousin of auto-value (https://github.com/google/auto/tree/master/factory)
into the project to help with dagger assisted injection (this post explains it quite well:
https://stackoverflow.com/questions/22799407/looking-for-an-example-for-dagger-assisted-injection)
* Added an Espresso test using the OkReplay (https://github.com/airbnb/okreplay) library that records and replays server
responses. See com.example.features.dashboard.view.MainActivityOkReplayEspressoTest for more details.
* Added support for Firebase cloud testing (firebase.google.com/docs/test-lab/) through Teamcity! Now every pull request/
nightly build/release build uses the service to run Espresso tests. See JustAnotherAndroidApp_RunEspressoTestsInFirebase.xml
for more details.
* Added support for Burst library (https://github.com/square/burst) for parametrized unit tests (see
com.example.util.StringUtilsTest for more details).
* Added app shortcuts! Static, dynamic, and dynamic used via the https://github.com/MatthiasRobbers/shortbread library! For
details please check bottom of App class, MainActivity @Shortcut declaration and shortcuts.xml file.
* Added 2 more custom lint checks around colors (Check class NonMaterialColorsDetector and DirectMaterialPaletteColorUsageDetector).
* Added a custom lint check for hardcoded colors. (Check class HardcodedColorsDetector)
* Gradle plugin to check APK size and automatically fail the build if the APK size is more than a specific value (check the
build.gradle and gradle.properties file for the configuration and https://github.com/vanniktech/gradle-android-apk-size-plugin
for the actual gradle plugin).
* Added support for Teamcity CI scripts committed in VCS! They are written in Kotlin/xml (Check .teamcity folder or read more
at the bottom of this file)
* Added Sherlock into the project so that developers (and QA) can have easy access to exceptions occurring (and share
them) through the app (see App class and the build.gradle file and https://github.com/ajitsing/Sherlock for the project).
* Added Traceur into the project that allows for displaying more useful stacktraces with RxJava 2 (check TraceurTool class
  and the other related classes or https://github.com/T-Spoon/Traceur for the library).
* Added chuck library for seeing network calls right on the phone. See https://github.com/jgilfelt/chuck for the library
and the NetworkModule class for the added interceptor.
* Disabling animations before espresso tests and re-enabling them afterwards! (See grant_animation_permission.gradle and
EspressoTestHelper class)
* Added Butterknife Actions (See ButterknifeActions class)
* Support for mocking parts of your Dagger graph via DaggerMock library (see MainActivityTest class)
* Added constraint layout! (See activity_main.xml)
* Added an RxJava scheduler that informs Espresso via a CountingIdlingResource on when to pause test execution
 and wait for asynchronous tasks to finish (check com.example.util.rx.RxIdlingScheduler)
* Upgraded project to use the new Mosby MVP v3!! Check https://github.com/sockeqwe/mosby for more details.
* Added support for RxJavaPlugins class, that allows easy overriding of RxJava 2 schedulers in tests.
See setup method of MainPresenterTest class.
* Added some variations of the quick settings tiles. See https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8
for information on the feature and the com.example.features.tiles.PassiveTileServiceOnlyToggle class for the
implementation (plus there are some more goodies in the same package)

##

### Contents:

##### libraries:
* RxJava
* Dagger 2 with examples of assisted injection and different modules depending on build type. Also support for android dagger v2.11
* Retrofit 2 and Retrofit Mock mode for debug builds
* Mosby MVP with View State support (v3!)
* Timber
* Auto Value and Auto Factory
* Glide with a wrapper
* Butterknife
* AssertJ for fluent assertions
* Fabric (Crashlytics and Answers)
* Retrolambda
* Stetho
* Chuck
* ShortBread (https://github.com/MatthiasRobbers/shortbread)
* PermissionsDispatcher for runtime permissions (https://github.com/permissions-dispatcher/PermissionsDispatcher)

##### Static analysis:
* PMD (https://pmd.github.io/ - check file static_analysis_java.gradle)
* Checkstyle (check file static_analysis_java.gradle)
* Lint (check file lint.gradle)
* Findbugs (check file static_analysis_java.gradle)
* Jacoco code coverage that can generate reports for unit tests, espresso tests or the combination of the two
* A set of custom IDE inspection rules
* A module with custom lint rules and tests for them

##### Testing:
* Added support for Spoon. See https://github.com/square/spoon for more details. Also added the gradle plugin for spoon.
One can run the tests with 'gradlew spoon' and then open up the generated reports in 'build/spoon' directory.
* Test coverage running in Teamcity CI server
* Espresso tests with and without mock web server
* Mock web server tests that loads responses from json files
* Robolectric tests
* Normal unit tests
* Ok http interceptor for changing the base url in tests
* Idling resources
* Unlocking screen for Espresso tests (check class com.example.util.EspressoTestRunner)
* Support for RxJavaPlugins class, that allows easy overriding of RxJava 2 schedulers in tests (check MainPresenterTest class)
* Support for an RxJava scheduler that helps with espresso tests and asynchronous code execution.
(check com.example.util.rx.RxIdlingScheduler)
* Support for mocking parts of your Dagger graph via DaggerMock library (see MainActivityTest class)
* Disabling animations before espresso tests and re-enabling them afterwards! (See grant_animation_permission.gradle and
EspressoTestHelper class)
* Added Sherlock into the project so that developers (and QA) can have easy access to exceptions occurring (and share
them) through the app (see App class and the build.gradle file and https://github.com/ajitsing/Sherlock for the project).
* Burst library (https://github.com/square/burst) for parametrized unit tests (see
  com.example.util.StringUtilsTest for more details).
* Added support for Firebase cloud testing (firebase.google.com/docs/test-lab/) through Teamcity! Now every pull request/
nightly build/release build uses the service to run Espresso tests. See JustAnotherAndroidApp_RunEspressoTestsInFirebase.xml
for more details.
* Added an Espresso test using the OkReplay (https://github.com/airbnb/okreplay) library that records and replays server
responses. See com.example.features.dashboard.view.MainActivityOkReplayEspressoTest for more details.

##### View Related:
* Added constraint layout! (See activity_main.xml)
* Added Butterknife Actions (See ButterknifeActions class)

##### Other:
* Gradle plugin to check APK size and automatically fail the build if the APK size is more than a specific value (check the
build.gradle and gradle.properties file for the configuration and https://github.com/vanniktech/gradle-android-apk-size-plugin
for the actual gradle plugin).
* Separate app icons according to build type
* Some advanced source sets configuration for splitting up tests
* Loading some project config from property files in Android Manifest and build.gradle
* Shared folders for some build types or tests
* Working proguard config
* Android Studio external annotations (https://www.jetbrains.com/help/idea/2016.3/external-annotations.html)
* Package level annotations for @Nullable and @NonNull
* OkHttp interceptor for adding auth token to headers easily
* Strict mode
* Plugin to publish app on the playstore
* Dex count plugin for counting the number of methods in the apk
* Separate Timber logging tree for crashlytics. See com.example.tools.timber.CrashlyticsTree
* Quick settings tiles (See com.example.features.tiles.PassiveTileServiceOnlyToggle)
* Added Traceur into the project that allows for displaying more useful stacktraces with RxJava 2 (check TraceurTool class
and the other related classes or https://github.com/T-Spoon/Traceur for the library).
* App shortcuts! Static, dynamic, and dynamic used via the https://github.com/MatthiasRobbers/shortbread library! For
details please check bottom of App class, MainActivity @Shortcut declaration and shortcuts.xml file.

..and all sorts of other goodies!

##

### Teamcity - Continuous Integration

The project benefits from Teamcity's feature of storing the CI server configuration in Kotlin in a Version Control System. See https://confluence.jetbrains.com/display/TCD10/Kotlin+DSL for more
details. The settings can be found under the .teamcity folder in the project.

##### Build Configurations:

There's 3 build configurations:

* _'Pull requests'_ build configuration, triggered on every Pull Request. Verifies correctness of a Pull Request (usually for the
'develop' branch). QA would fetch the relevant APK from HockeyApp created by this build configuration in order to manually test the
 feature/fix that the Pull Request introduces. This build:
  * Runs all static analysis tools.
  * Runs all unit tests for all build types.
  * Perform method count for all build types.
  * Checks for duplicates.
  * Builds APKs.
  * Runs all espresso tests on Firebase Test Cloud.
  * Uploads APKs to HockeyApp.
  * Updates Github with the status of the job (success/failure).
* _'Nightly Builds_' build configuration, triggered every night at midnight on 'develop' branch.
QA would fetch the relevant APK from HockeyApp created by this build config in order to test the integration of
features of the app. This build is also deployed to a closed alpha playstore group for people to test. This build:
  * Runs all unit tests for all build types.
  * Performs method count for all build types.
  * Checks for duplicates.
  * Builds APKs.
  * Runs all espresso tests on Firebase Test Cloud.
  * Uploads APKs to HockeyApp.
  * Uploads Release APK to private alpha channel in Playstore.
* _'Releases'_ build configuration, triggered on every branch matching the 'release/*' logical branch name.
QA would fetch the relevant APK from HockeyApp to perform the final testing before the release.
This build is also deployed to an open beta playstore group for people to test. This build:
  * Runs all static analysis tools.
  * Runs all unit tests for all build types.
  * Performs method count for all build types.
  * Checks for duplicates.
  * Builds APKs.
  * Runs all espresso tests on Firebase Test Cloud.
  * Uploads APKs to HockeyApp.
  * Uploads Release APK to public beta channel in Playstore.


##### Reports:
There's also all sorts of reports available:

* Checkstyle static analysis report shows all the checkstyle warnings in the project. Would usually report
 back empty as there's a zero tolerance policy in the project. On failed builds it pinpoints the issues
 that need to be fixed.

![2]
* Unit test reports for all build types show all the tests that were run along with additional details
and stacktraces should an error occur. 2 dashboards, one is the Teamcity generated Test report, the
other is the original Junit4 html report.

![3]

![11]
* Dex method counter reports for all build types show the method count for every APK, along with an
interesting drill down visualization that makes it very easy to spot libraries that contain too many
methods. As a note, usually release and QA APKs have a smaller method counts in these reports as
Proguard is running in these build types, stripping unused methods.

![4]
* Findbugs static analysis report shows all the findbugs warnings in the project. Would usually report
back empty as there's a zero tolerance policy in the project. On failed builds it pinpoints the issues
that need to be fixed.

![5]
* Lint static analysis report shows all the lint warnings in the project. Would usually report back
empty as there's a zero tolerance policy in the project. On failed builds it pinpoints the issues
that need to be fixed.

![6]
* PMD static analysis report shows all the PMD warnings in the project. Would usually report back
empty as there's a zero tolerance policy in the project. On failed builds it pinpoints the issues
that need to be fixed.

![7]
* There's no report at the moment for the firebase cloud testing. You'll need to go into the build
log and locate the url that points to the Google Cloud Storage Bucket that the results are saved
in. With a few changes one can use a personal (paid) storage bucket and then pull the test results
back into teamcity. This article briefly touches on the topic:
http://building.usebutton.com/testing/cloud/android/ci/2016/04/20/teamcity-google-device-cloud/

![12]


##### Teamcity Plugins:

A couple of Teamcity plugins were used to make my life easier:
* 'Advanced Shared Build Number': See https://java.nicholaswilliams.net/TeamCityPlugins/download
A plugin that allows you to share build counters across builds. As that counter is part of the
 version code of the APK, it's good to keep them in sync.
* 'Slack Notifications plugin': See https://github.com/PeteGoo/tcSlackBuildNotifier
A plugin that allows you to post Build Statuses to Slack.
* 'Chuck Norris Teamcity Plugin': See https://github.com/dbf256/teamcity-chuck-plugin
Not much to say about this one ;-)

##### Notes:

* Public beta channel (https://play.google.com/apps/testing/com.justanotherandroidapp) can then (in theory)
be distributed across the company. One thing I tried was adding the link to an NFC tag and hanging
that on the wall, so that everyone can quickly fetch the beta app!

##

### HockeyApp

I am using HockeyApp to save and (in theory) distribute the APKs to QA or stakeholders. See https://hockeyapp.net
for more details on the product. This is what it looks like in the HockeyApp dashboard:

![8]

As you can see there's different builds for the different CI build configurations and all the build types.
HockeyApp guys were kind enough to provide me with a free account to demonstrate the usage of their tool.
##

### Playstore reviews in slack

Using Review bot for this (https://reviewbot.io/?utm_source=github&utm_medium=athkalia-just-another-android-app)
Not much to say, just a super simple tool that gets the job done.
When a review comes it looks like this:

![13]

### Roadmap
 * Upgrade to latest version of gradle
 * fingerprint authentication
 * Conductor library

### Submitting PRs
Please make sure the command ```gradlew check``` completes successfully before creating the PR. This command
runs all the tests for all the variants, plus the 4 static analysis tools: lint, checsktyle, pmd, findbugs.

###### List of stuff that I won't be adding, feel free to contribute if you like any of these!
* Run test coverage in firebase cloud testing, fetch the reports, (possibly merge them with unit tests coverage reports), and provide
 reporting back to the CI server. This would require paying for an S3 bucket, and I am uncertain about the usefulness of tracking this
 to be honest.
* Automate releases more, like tagging releases, merging back to develop etc. Requirements around this are very project specific, and
 given that there may be conflicts etc, not worth automating for now.
* Automatic screenshots through the "Fastlane - Screengrab" tool (https://github.com/fastlane/fastlane/tree/master/screengrab)
They don't support windows and I don't have a Mac :(


Any feedback/pull request is welcome!

You can catch me at www.sakiskaliakoudas.com


  [1]: ./art/screenshot.png
  [2]: ./art/checkstyle.png
  [3]: ./art/unit_tests_1.png
  [4]: ./art/dex_count.png
  [5]: ./art/findbugs.png
  [6]: ./art/lint.png
  [7]: ./art/pmd.png
  [8]: ./art/hockeyapp.png
  [10]: ./art/reports.png
  [11]: ./art/unit_tests_2.png
  [12]: ./art/firebase.png
  [13]: ./art/playstore-reviews-slack.png
