package com.app.absensis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.absensis.utils.LoadingUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BaseActivity extends AppCompatActivity {

    private LoadingUtil loadingUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        loadingUtil = LoadingUtil.getInstance(this);
    }

    public void showLoading(String message) {
        loadingUtil.showLoading(message, false);
    }

    public void dismissLoading() {
        loadingUtil.dismissLoading();
    }

    public void showSimpleDialog(String title, String message) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);
        dialogBuilder.setTitle(title)
                .setMessage(message)
                .show();
    }
}
