package com.app.absensis.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.absensis.utils.LoadingUtil;

public class BaseFragment extends Fragment {

    private LoadingUtil loadingUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        loadingUtil = LoadingUtil.getInstance(getContext());
    }

    public void showLoading(String message) {
        loadingUtil.showLoading(message, false);
    }

    public void dismissLoading() {
        loadingUtil.dismissLoading();
    }
}
