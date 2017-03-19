package com.example.features.tiles;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import org.joda.time.DateTime;
import timber.log.Timber;

/**
 * See an overview of the quick settings tile feature here: https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8
 * An active tile only triggers the {@link #onStartListening()} and {@link #onStopListening()} methods either when clicked or when
 * {@link TileService#requestListeningState(Context, ComponentName)} is called. A passive tile on the other hand triggers the {@link
 * #onStartListening()} method when the user pulls the notification dropdown down and makes the tile visible, and the {@link
 * #onStopListening()} method when the user dismisses the notification dropdown and hides the tile.
 */
@TargetApi(Build.VERSION_CODES.N)
public class ActiveTileService extends TileService {

    @Override
    public void onTileAdded() {
        Timber.d("Active tile added to user quick settings.");
    }

    @Override
    public void onTileRemoved() {
        Timber.d("Active tile removed from user quick settings.");
    }

    @Override
    public void onStartListening() {
        Timber.d("Active tile started listening");
        Tile tile = getQsTile();
        tile.setLabel("Time: " + DateTime.now().toLocalTime());
        tile.updateTile();
    }

    @Override
    public void onStopListening() {
        Timber.d("Active tile stopped listening");
    }

}
