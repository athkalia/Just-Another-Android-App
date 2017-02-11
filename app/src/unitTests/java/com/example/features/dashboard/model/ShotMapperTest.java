package com.example.features.dashboard.model;

import com.example.util.dummy.DummyDataProvider;
import com.example.model.Shot;
import com.example.model.api.ShotResponse;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class ShotMapperTest {

    private ShotMapper shotMapper;

    private final DummyDataProvider dummyDataProvider = new DummyDataProvider();

    @Before
    public void setUp() throws Exception {
        shotMapper = new ShotMapper();
    }

    @Test
    public void map_test() {
        // Arrange
        ShotResponse shotResponse = dummyDataProvider.shots().getShotsResponse("title 1", "url 1");

        // Act
        Shot shot = shotMapper.map(shotResponse);

        // Assert
        assertThat(shot.getUrl()).isEqualTo("url 1");
        assertThat(shot.getTitle()).isEqualTo("title 1");
    }

    @Test
    public void map_null_title() {
        // Arrange
        ShotResponse shotResponse = dummyDataProvider.shots().getShotsResponse(null, "url 1");

        // Act
        Shot shot = shotMapper.map(shotResponse);

        // Assert
        assertThat(shot.getUrl()).isEqualTo("url 1");
        assertThat(shot.getTitle()).isNull();
    }

    @Test
    public void map_null_url() {
        // Arrange
        ShotResponse shotResponse = dummyDataProvider.shots().getShotsResponse("title 1", null);

        // Act
        try {
            shotMapper.map(shotResponse);
            failBecauseExceptionWasNotThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            // Assert
            assertThat(e).hasMessage("Missing required properties: url");
        }
    }

}
