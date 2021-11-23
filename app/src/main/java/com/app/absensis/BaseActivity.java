package com.app.absensis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.absensis.utils.LocationUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BaseActivity extends AppCompatActivity {

    private MaterialAlertDialogBuilder dialogBuilder;
    private AlertDialog progressDialog = null;
    private LocationUtil locationUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        locationUtil = LocationUtil.getInstance(this);
    }

    public void showLoading(String message, boolean cancelable) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_loading, null, false);
            TextView tvLoading = view.findViewById(R.id.tv_loading);
            tvLoading.setText(message);
            dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setView(view).setCancelable(cancelable);
            progressDialog = dialogBuilder.create();
            progressDialog.show();
        }
    }

    public void showDefaultLoading() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_loading, null, false);
            TextView tvLoading = view.findViewById(R.id.tv_loading);
            tvLoading.setText(getString(R.string.loading));
            dialogBuilder = new MaterialAlertDialogBuilder(this);
            dialogBuilder.setView(view).setCancelable(false);
            progressDialog = dialogBuilder.create();
            progressDialog.show();
        }
    }

    public void dismissLoading() {
        if (progressDialog != null || progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showSimpleDialog(String title, String message) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setTitle(title)
                .setMessage(message)
                .show();
    }

    public void hideActionBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    public void showActionBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void showDialogConfirm(String title, String message, DialogInterface.OnClickListener listener) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .show();
    }

    public double getLat() {
        return locationUtil.getLatitude();
    }

    public double getLng() {
        return locationUtil.getLongitude();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGpsActive()) {
            showDialogConfirm("GPS",
                    "GPS Harus Diaktifkan",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
        }
    }

    private boolean isGpsActive() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean retval = false;

        try {
            retval = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }

}
