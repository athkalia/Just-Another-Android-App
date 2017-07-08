package com.example.util.other;

import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowApplication;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class PropertiesManagerTest {

    private PropertiesManager propertiesManager;

    @Before
    public void setUp() {
        propertiesManager = new PropertiesManager(ShadowApplication.getInstance().getApplicationContext().getResources().getAssets());
    }

    @Test
    public void properties_test() {
        assertThat(propertiesManager.getDribleClientAccessToken()).isNotEmpty();
        assertThat(propertiesManager.getBaseUrl()).isNotEmpty();
    }

}
