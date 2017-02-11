package com.example.features.dashboard.view;

import com.example.util.dummy.DummyDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewStateTest {

    @Mock MainView mockMainView;

    private MainActivityViewState mainActivityViewState;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    @Before
    public void setUp() {
        mainActivityViewState = new MainActivityViewState();
    }

    @Test
    public void applying_view_state_sets_the_view() {
        mainActivityViewState.saveShots(dummyDataProvider.shots().getShotList());
        mainActivityViewState.apply(mockMainView, false);

        verify(mockMainView).displayShotsList(dummyDataProvider.shots().getShotList());
    }

    @Test
    public void applying_empty_view_state_does_nothing() {
        mainActivityViewState.apply(mockMainView, false);

        verifyZeroInteractions(mockMainView);
    }

}
