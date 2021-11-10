package com.app.absensis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.absensis.utils.LoadingUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BaseActivity extends AppCompatActivity {

    private MaterialAlertDialogBuilder dialogBuilder;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
}
