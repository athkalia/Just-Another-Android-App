package com.example.features.tiles;

import android.annotation.TargetApi;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import com.example.R;
import timber.log.Timber;

/**
 * See an overview of the quick settings tile feature here: https://medium.com/google-developers/quick-settings-tiles-e3c22daf93a8 .
 */
@TargetApi(Build.VERSION_CODES.N)
public class PassiveTileServiceOnlyToggle extends TileService {

    @Override
    public void onTileAdded() {
        Timber.d("Passive toggle only tile added to user quick settings.");
        setCurrentState(Tile.STATE_INACTIVE);
    }

    @Override
    public void onTileRemoved() {
        Timber.d("Passive toggle only tile removed from user quick settings.");
        setCurrentState(Tile.STATE_INACTIVE);
    }

    @Override
    public void onClick() {
        Timber.i("Passive toggle only tile was just clicked.");
        Tile tile = getQsTile();
        int currentState = tile.getState();
        switch (currentState) {
            case Tile.STATE_INACTIVE:
                setCurrentState(Tile.STATE_ACTIVE);
                break;
            case Tile.STATE_ACTIVE:
                setCurrentState(Tile.STATE_INACTIVE);
                break;
            default:
                throw new AssertionError("Tile was clicked while being at state: " + currentState);
        }
    }

    private void setCurrentState(int newState) {
        Timber.d("Setting tile state to: %d", newState);
        Tile tile = getQsTile();
        tile.setState(newState);
        String newLabel;
        Icon newIcon;
        switch (newState) {
            case Tile.STATE_ACTIVE:
                newLabel = getString(R.string.quick_settings_passive_tile_only_toggle_label_enabled);
                newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.tile_enabled);
                break;
            case Tile.STATE_INACTIVE:
                newLabel = getString(R.string.quick_settings_passive_tile_only_toggle_label_disabled);
                newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.tile_disabled);
                break;
            default:
                throw new AssertionError("Can't set tile state to: " + newState);
        }
        tile.setIcon(newIcon);
        tile.setLabel(newLabel);
        tile.updateTile();
    }

}
