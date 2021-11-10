package com.app.absensis.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.app.absensis.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoadingUtil {

    private static LoadingUtil loadingUtil;
    private MaterialAlertDialogBuilder dialogBuilder;
    private Context context;
    private AlertDialog progressDialog;

    public LoadingUtil(Context context) {
        this.context = context;
    }

    public static synchronized LoadingUtil getInstance(Context context) {
        if (loadingUtil == null)
            loadingUtil = new LoadingUtil(context);
        return loadingUtil;
    }

    public void showLoading(String message, boolean cancelable) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null, false);
            TextView tvLoading = view.findViewById(R.id.tv_loading);
            tvLoading.setText(message);
            dialogBuilder = new MaterialAlertDialogBuilder(context);
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

}
