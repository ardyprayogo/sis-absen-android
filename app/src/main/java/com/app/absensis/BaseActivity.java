package com.app.absensis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.absensis.utils.LoadingUtil;

public class BaseActivity extends AppCompatActivity {

    private LoadingUtil loadingUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        loadingUtil = LoadingUtil.getInstance();
    }

    public void showLoading(String message) {
        loadingUtil.showLoading(this, message, false);
    }

    public void dismissLoading() {
        loadingUtil.dismissLoading();
    }
}
