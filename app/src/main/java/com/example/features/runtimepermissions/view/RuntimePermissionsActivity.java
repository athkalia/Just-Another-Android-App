package com.example.features.runtimepermissions.view;

import android.Manifest;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import butterknife.OnClick;
import com.example.R;
import com.example.util.mvp.base.BaseActivity;
import com.example.util.mvp.noop.NoOpPresenter;
import com.example.util.mvp.noop.NoOpView;
import com.example.util.mvp.noop.NoOpViewState;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class RuntimePermissionsActivity
        extends BaseActivity<NoOpView, NoOpPresenter, NoOpViewState<NoOpView>> // I am just lazy, in a normal project you wouldn't have these no-ops
        implements NoOpView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_runtime_permissions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RuntimePermissionsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_CALENDAR, Manifest.permission.READ_PHONE_STATE})
    void askPermission() {
        Toast.makeText(getApplicationContext(), "Ta-daaaa permission granted", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.READ_CALENDAR, Manifest.permission.READ_PHONE_STATE})
    void onShowRationale(PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.runtime_permission_rationale_message)
                .setPositiveButton(R.string.runtime_permission_rationale_button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.runtime_permission_rationale_button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CALENDAR, Manifest.permission.READ_PHONE_STATE})
    void onPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.READ_CALENDAR, Manifest.permission.READ_PHONE_STATE})
    void onPermissionDeniedPermanently() {
        Toast.makeText(getApplicationContext(), "Permission denied permanently", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ask_permission_button)
    void onRuntimePermissionRequestClicked() {
        RuntimePermissionsActivityPermissionsDispatcher.askPermissionWithCheck(this);
    }

}
