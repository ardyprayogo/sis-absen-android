package com.app.absensis.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.app.absensis.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BaseFragment extends Fragment {

    private MaterialAlertDialogBuilder dialogBuilder;
    private AlertDialog progressDialog;

    public void showLoading(String message, boolean cancelable) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null, false);
            TextView tvLoading = view.findViewById(R.id.tv_loading);
            tvLoading.setText(message);
            dialogBuilder = new MaterialAlertDialogBuilder(getContext());
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
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext());
        dialogBuilder.setTitle(title)
                .setMessage(message)
                .show();
    }
}
