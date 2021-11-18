package com.app.absensis.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.app.absensis.BaseActivity;
import com.app.absensis.MainActivity;
import com.app.absensis.R;
import com.app.absensis.utils.PreferenceUtil;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private final int RC_PERM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
    }

    private void initUI() {
        View windowDecorView = getWindow().getDecorView();
        windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                askingPermission();
            }
        }, 2000);
    }

    private void checkToken() {
        if (PreferenceUtil.checkTokenExpired(this))
            goMain();
        else
            goLogin();
    }

    private void goMain() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        checkToken();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
            new AppSettingsDialog.Builder(this).build().show();
        askingPermission();
    }

    private void askingPermission() {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            checkToken();
        } else {
            EasyPermissions.requestPermissions(this,
                    "Akses lokasi dibutuhkan",
                    RC_PERM,
                    perms);
        }
    }


    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {
        finish();
    }
}