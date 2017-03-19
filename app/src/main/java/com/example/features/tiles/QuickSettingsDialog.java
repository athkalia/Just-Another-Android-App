package com.example.features.tiles;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import com.example.R;

import javax.annotation.Nullable;

public class QuickSettingsDialog extends DialogFragment {

    private final Context context;
    private final DialogListener listener;

    public QuickSettingsDialog(Context context, DialogListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Dialog onCreateDialog(@Nullable Bundle savedState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context)
                .setView(R.layout.quick_settings_dialog)
                .setNeutralButton(android.R.string.ok, (dialogInterface, i) -> listener.onDoneClicked(this));
        return alertBuilder.create();
    }

    public interface DialogListener {

        void onDoneClicked(DialogFragment dialog);
    }

}
