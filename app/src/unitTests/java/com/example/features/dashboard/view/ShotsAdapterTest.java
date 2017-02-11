package com.example.features.dashboard.view;

import com.example.model.Shot;
import com.example.util.PreconfiguredRobolectricTestRunner;
import com.example.util.dummy.DummyDataProvider;
import com.example.util.other.ViewHolderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class ShotsAdapterTest {

    @Mock private ViewHolderFactory mockViewHolderFactory;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    private ShotsAdapter shotsAdapter;

    @Before
    public void setUp() {
        shotsAdapter = new ShotsAdapter(mockViewHolderFactory);
    }

    @Test
    public void item_count() {
        // Arrange
        List<Shot> shots = dummyDataProvider.shots().getShotList();

        // Act
        shotsAdapter.setData(shots);

        // Assert
        assertThat(shotsAdapter.getItemCount()).isEqualTo(2);
    }

    @Test
    public void set_data_test() {
        // Arrange
        List<Shot> shots = dummyDataProvider.shots().getShotList();

        // Act
        shotsAdapter.setData(shots);

        // Assert
        assertThat(shotsAdapter.getData()).isEqualTo(shots);
    }

}
