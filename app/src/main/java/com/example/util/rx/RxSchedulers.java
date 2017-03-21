package com.example.util.rx;

import io.reactivex.Scheduler;

public class RxSchedulers {

    /**
     * A {@link Scheduler} intended for IO-bound work. The implementation is backed by an {@link java.util.concurrent.Executor} thread-pool
     * that will grow as needed. This can be used for asynchronously performing blocking IO.
     */
    private final Scheduler ioScheduler;

    /**
     * A {@link Scheduler} intended for computational work. This can be used for event-loops, processing callbacks and other computational
     * work. Unhandled errors will be delivered to the scheduler Thread's {@link Thread.UncaughtExceptionHandler}.
     **/
    private final Scheduler computationScheduler;

    /**
     * A {@link Scheduler} that queues work on the current thread to be executed after the current work completes.
     */
    private final Scheduler trampolineScheduler;

    /**
     * A {@link Scheduler} which executes actions on the Android UI thread.
     */
    private final Scheduler androidMainThreadScheduler;

    public RxSchedulers(Scheduler ioScheduler, Scheduler computationScheduler, Scheduler trampolineScheduler, Scheduler androidMainThreadScheduler) {

        this.ioScheduler = ioScheduler;
        this.computationScheduler = computationScheduler;
        this.trampolineScheduler = trampolineScheduler;
        this.androidMainThreadScheduler = androidMainThreadScheduler;
    }

    public Scheduler getIoScheduler() {

        return ioScheduler;
    }

    public Scheduler getComputationScheduler() {

        return computationScheduler;
    }

    public Scheduler getTrampolineScheduler() {
        return trampolineScheduler;
    }

    public Scheduler getAndroidMainThreadScheduler() {

        return androidMainThreadScheduler;
    }

}
