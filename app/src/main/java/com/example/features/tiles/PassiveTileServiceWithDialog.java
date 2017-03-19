package com.example.features.tiles;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.TileService;
import timber.log.Timber;

/**
 * See an overview of the quick settings tile feature here: https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8
 */
@TargetApi(Build.VERSION_CODES.N)
public class PassiveTileServiceWithDialog extends TileService {

    @Override
    public void onClick() {
        QuickSettingsDialog dialog = new QuickSettingsDialog(getApplicationContext(), dialog1 -> Timber.d("Done button clicked."));
        unlockAndRun(() -> showDialog(dialog.onCreateDialog(null)));
    }

    @Override
    public void onTileAdded() {
        Timber.d("Dialog tile added to user quick settings.");
    }

    @Override
    public void onTileRemoved() {
        Timber.d("Dialog tile removed from user quick settings.");
    }

}
