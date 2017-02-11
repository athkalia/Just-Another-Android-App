package com.example.util.other;

import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowApplication;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class PropertiesManagerTest {

    private PropertiesManager propertiesManager;

    @Before
    public void setUp() throws IOException {
        propertiesManager = new PropertiesManager(ShadowApplication.getInstance().getApplicationContext().getResources().getAssets());
    }

    @Test
    public void properties_test() {
        assertThat(propertiesManager.getDribleClientAccessToken()).isNotEmpty();
        assertThat(propertiesManager.getBaseUrl()).isNotEmpty();
    }

}
