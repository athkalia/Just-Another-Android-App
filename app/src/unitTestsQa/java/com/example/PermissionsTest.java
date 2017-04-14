package com.example;

import com.example.util.PreconfiguredRobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PreconfiguredRobolectricTestRunner.class)
public class PermissionsTest {

    @Test
    public void permissions_sanity_check() {
        // Arrange
        AndroidManifest androidManifest = new AndroidManifest(Fs.fileFromPath("build/intermediates/manifests/full/qa/AndroidManifest.xml"), null, null);
        Set<String> permissions = new HashSet<>(androidManifest.getUsedPermissions());

        // Assert
        String[] expectedPermissions = {"android.permission.INTERNET"};
        assertThat(permissions).containsOnly(expectedPermissions);
    }

}
