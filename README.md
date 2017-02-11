# Android Sample app with MVP architecture


Sample project that I created in the last couple of months so that I don't need to rewrite the same piece of code over and over again.
Demonstrates some cool stuff that you can do with the modern libraries and tooling in Android these days.

#### libraries
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

#### Static analysis
* PMD
* Checkstyle
* Lint
* Findbugs
* Jacoco code coverage that can generate reports for unit tests, espresso tests or the combination of the two
* A set of custom IDE inspection rules
* A module with a sample custom lint rule

#### Testing
* Espresso tests with and without mock web server
* Mock web server tests that loads responses from json files
* Robolectric tests
* Normal unit tests
* Ok http interceptor for changing the base url in tests
* Idling resources

#### Other
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

 ..and all sorts of other goodies!