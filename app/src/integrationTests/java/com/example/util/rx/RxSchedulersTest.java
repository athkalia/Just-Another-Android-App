package com.example.util.rx;

import com.example.App;
import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class RxSchedulersTest {

    private RxSchedulers rxSchedulers;

    @Before
    public void setUp() throws IOException {
        rxSchedulers = App.getApplicationComponent().rxSchedulers();
    }

    @Test
    public void scheduler_instances_are_valid() throws IOException {
        assertThat(rxSchedulers.getAndroidMainThreadScheduler()).isEqualTo(AndroidSchedulers.mainThread());
        assertThat(rxSchedulers.getComputationScheduler()).isEqualTo(Schedulers.computation());
        assertThat(rxSchedulers.getTrampolineScheduler()).isEqualTo(Schedulers.trampoline());
        assertThat(rxSchedulers.getIoScheduler()).isEqualTo(Schedulers.io());
    }

}
