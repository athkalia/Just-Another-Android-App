# Android Sample app with MVP architecture

Sample project that displays some images from the dribble API.
Demonstrates some cool stuff that you can do with the modern libraries and tooling in Android these days.

As someone said on reddit: "It's not over-engineered, it's just a skyscraper without the skyscraper part, just the foundations :)"


![Demo Screenshot][1]

### Newest additions:
* Added some variations of the quick settings tiles. See https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8
for information on the feature and the com.example.features.tiles.PassiveTileServiceOnlyToggle class for the
implementation (plus there are some more goodies in the same package)
* Added a separate Timber logging tree for crashlytics. See com.example.tools.timber.CrashlyticsTree
* Added automated unlocking the screen for espresso tests, check class com.example.util.EspressoTestRunner

#### libraries:
* RxJava
* Dagger 2 with examples of assisted injection and different modules depending on build type
* Retrofit 2 and Retrofit Mock mode for debug builds
* Mosby MVP with View State support
* Timber
* Auto Value
* Glide with a wrapper
* Butterknife
* AssertJ for fluent assertions
* Fabric (Crashlytics and Answers)
* Retrolambda
* Stetho

#### Static analysis:
* PMD (https://pmd.github.io/ - check file quality.gradle)
* Checkstyle
* Lint
* Findbugs
* Jacoco code coverage that can generate reports for unit tests, espresso tests or the combination of the two
* A set of custom IDE inspection rules
* A module with a sample custom lint rule

#### Testing:
* Espresso tests with and without mock web server
* Mock web server tests that loads responses from json files
* Robolectric tests
* Normal unit tests
* Ok http interceptor for changing the base url in tests
* Idling resources
* Unlocking screen for Espresso tests (check class com.example.util.EspressoTestRunner)

#### Other:
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

..and all sorts of other goodies!

### Roadmap:
 * Proper CI setup (probably Teamcity)
 * Screenshot automation.
 * A debug drawer

Any feedback/pull request is welcome!
You can catch me at www.sakiskaliakoudas.com


  [1]: ./art/screenshot.png
