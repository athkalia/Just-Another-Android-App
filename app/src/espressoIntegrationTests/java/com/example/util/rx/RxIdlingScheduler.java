package com.example.util.rx;

import android.support.test.espresso.idling.CountingIdlingResource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Scheduler} that automatically increments and decrements a {@link CountingIdlingResource} so that espresso tests can wait for
 * rxjava asynchronous actions. See https://github.com/ReactiveX/RxAndroid/issues/149 for the bigger discussion and issues with this
 * approach.
 */
public class RxIdlingScheduler extends Scheduler {

    final CountingIdlingResource countingIdlingResource;
    private final Scheduler delegateScheduler;

    public RxIdlingScheduler(Scheduler scheduler) {
        this.delegateScheduler = scheduler;
        String resourceName = scheduler.getClass().getSimpleName() + scheduler.hashCode();
        countingIdlingResource = new CountingIdlingResource(resourceName, true);
    }

    public CountingIdlingResource getCountingIdlingResource() {
        return countingIdlingResource;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new IdlingWorker(delegateScheduler.createWorker());
    }

    private final class IdlingWorker extends Scheduler.Worker {

        private final Scheduler.Worker delegateWorker;
        private boolean recursive;

        private IdlingWorker(Scheduler.Worker worker) {
            this.delegateWorker = worker;
        }

        @Override
        public Disposable schedule(Runnable runnable) {
            return delegateWorker.schedule(recursive ? runnable : decorateAction(runnable));
        }

        @Override
        public Disposable schedule(Runnable runnable, long delay, TimeUnit unit) {
            return delegateWorker.schedule(recursive ? runnable : decorateAction(runnable), delay, unit);
        }

        @Override
        public Disposable schedulePeriodically(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
            recursive = true;
            return delegateWorker.schedulePeriodically(decorateAction(runnable), initialDelay, period, unit);
        }

        @Override
        public void dispose() {
            delegateWorker.dispose();
        }

        @Override
        public boolean isDisposed() {
            return delegateWorker.isDisposed();
        }

        private Runnable decorateAction(Runnable runnable) {
            countingIdlingResource.increment();
            return () -> {
                try {
                    runnable.run();
                } finally {
                    countingIdlingResource.decrement();
                }
            };
        }

    }

}
