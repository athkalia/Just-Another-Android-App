package com.example.features.dashboard.navigation;

import android.content.Context;
import android.content.Intent;
import com.example.features.runtimepermissions.view.RuntimePermissionsActivity;
import com.example.tools.dagger.scopes.ActivityContext;

import javax.inject.Inject;

public class RuntimePermissionsNavigator {

    private final Context context;

    @Inject
    public RuntimePermissionsNavigator(@ActivityContext Context context) {
        this.context = context;
    }

    public void navigate() {
        Intent intent = new Intent(context, RuntimePermissionsActivity.class);
        context.startActivity(intent);
    }

}
