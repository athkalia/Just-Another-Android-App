package com.example;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.example.tools.dagger.components.ApplicationComponent;
import com.example.tools.dagger.components.DaggerApplicationComponent;
import com.example.tools.dagger.modules.ApplicationModule;
import com.example.tools.stetho.StethoTool;
import com.example.tools.timber.CrashlyticsTree;
import com.example.util.testing.TestUtil;
import hu.akarnokd.rxjava2.debug.RxJavaAssemblyTracking;
import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;
import timber.log.Timber;

import javax.inject.Inject;

public class App extends Application {

    private static ApplicationComponent applicationComponent;

    @Inject StethoTool stethoTool;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initTimber();
        initFabric();
        initStetho();
        initStrictMode();
        initJodaTime();
        enableBetterStackTracesForRx();
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    /**
     * Setup Timber. We only enable the timber logcat tree in debug builds. Release builds tend to not have have logs:
     * a) As logs are not accessible to developers.
     * b) For security reasons.
     * c) For performance reasons.
     *
     * Having said that, we plant a second tree that takes {@link Timber#wtf} calls and posts them to crashlytics (but not logcat).
     */
    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }
    }

    /**
     * Setup Fabric. We also set the build time and sha key so that we can easily reproduce bug reports.
     *
     * Note 1: To send an exception to crashlytics use {@link Crashlytics#logException(Throwable)}. It will send a non-fatal exception.
     * This is reported separately in the crashlytics dashboard. See
     * https://docs.fabric.io/android/crashlytics/caught-exceptions.html?caught%20exceptions#caught-exceptions for more details.
     *
     * Note 2: To log a statement in Crashlytics use {@link Crashlytics#log(String)}. This log statement will appear when clicking on a
     * specific crash report. For example if you have a crash that occurred 10 times, one would need to click through all 10 instances of
     * that crash to see the individual log statements for every instance of this crash.
     * See https://docs.fabric.io/android/crashlytics/enhanced-reports.html for more info.
     *
     * Note 3: To log a key-value pair in Crashlytics use {@link Crashlytics#setString(String, String)}. Same concept as logging a
     * statement described in Note 2.
     */
    private void initFabric() {
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled("debug".equals(BuildConfig.BUILD_TYPE)) // 'debug' builds not using fabric, 'qa' and 'release' do.
                .build();

        Crashlytics crashlytics = new Crashlytics.Builder()
                .core(crashlyticsCore)
                .build();

        Answers answers = new Answers();

        final Fabric fabric = new Fabric.Builder(getApplicationContext())
                .kits(crashlytics, answers)
                .debuggable(BuildConfig.DEBUG) // 'debug' and 'qa' build types have extra log statements, 'release' build type doesn't.
                .build();

        Fabric.with(fabric);

        Crashlytics.setString("GIT_SHA_KEY", BuildConfig.GIT_SHA);
        Crashlytics.setString("BUILD_TIME", BuildConfig.BUILD_TIME);
    }

    private void initStetho() {
        stethoTool.init();
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !TestUtil.areEspressoTestsRunning()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectResourceMismatches()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectCleartextNetwork()
                    .detectFileUriExposure()
                    .detectLeakedClosableObjects()
                    .detectLeakedRegistrationObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeathOnCleartextNetwork()
                    .penaltyDeathOnFileUriExposure()
                    .build());
        }
    }

    private void initJodaTime() {
        JodaTimeAndroid.init(this);
    }

    /**
     * From https://github.com/ReactiveX/RxJava/wiki/Plugins :
     * In addition, the RxJavaHooks offers the so-called assembly tracking feature. This shims a custom Observable, Single and Completable
     * into their chains which captures the current stacktrace when those operators were instantiated (assembly-time). Whenever an error is
     * signalled via onError, these middle components attach this assembly-time stacktraces as last causes of that exception. This may help
     * locating the problematic sequence in a codebase where there are too many similar flows and the plain exception itself doesn't tell
     * which one failed in your codebase.
     */
    private void enableBetterStackTracesForRx() {
        if (BuildConfig.DEBUG) {
            RxJavaAssemblyTracking.enable();
        }
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
