package com.app.absensis.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.app.absensis.R;

public class LoadingUtil {

    private static LoadingUtil loadingUtil;
    private Dialog dialog;

    public static LoadingUtil getInstance() {
        if (loadingUtil == null)
            loadingUtil = new LoadingUtil();
        return loadingUtil;
    }

    public void showLoading(Context context, String message, boolean cancelable) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.view_loading);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(cancelable);
            dialog.setCancelable(cancelable);

            TextView tvLoading = dialog.findViewById(R.id.tv_loading);
            tvLoading.setText(message);
            dialog.show();
        }
    }

    public void dismissLoading() {
        if (dialog != null | dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
