package com.example.features.tiles;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.TileService;
import com.example.features.dashboard.view.MainActivity;
import timber.log.Timber;

/**
 * See an overview of the quick settings tile feature here: https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8
 */
@TargetApi(Build.VERSION_CODES.N)
public class PassiveTileServiceToLaunchActivity extends TileService {

    @Override
    public void onTileAdded() {
        Timber.d("Launch activity tile added to user quick settings.");
    }

    @Override
    public void onTileRemoved() {
        Timber.d("Launch activity tile removed from user quick settings.");
    }

    @Override
    public void onClick() {
        Timber.i("Launch activity tile was just toggled by user.");
        unlockAndRun(() -> startActivityAndCollapse(new Intent(getApplicationContext(), MainActivity.class)));
    }

}
