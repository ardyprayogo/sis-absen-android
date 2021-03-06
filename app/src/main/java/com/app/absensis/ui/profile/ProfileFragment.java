package com.app.absensis.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.absensis.R;
import com.app.absensis.model.profile.Profile;
import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.app.absensis.ui.BaseFragment;
import com.app.absensis.ui.LoginActivity;
import com.app.absensis.utils.PreferenceUtil;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

public class ProfileFragment extends BaseFragment {

    private TextView tvName, tvAddress, tvHp, tvEmail, tvDivision, tvLevel;
    private MaterialButton btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initEvent();
        getProfile();
    }

    private void initUI(View view) {
        showLoading(getString(R.string.loading), false);
        tvName = view.findViewById(R.id.tv_name);
        tvAddress = view.findViewById(R.id.tv_address);
        tvHp = view.findViewById(R.id.tv_hp);
        tvEmail = view.findViewById(R.id.tv_email);
        tvDivision = view.findViewById(R.id.tv_division);
        tvLevel = view.findViewById(R.id.tv_level);

        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void initEvent() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyUtil.sendLogout(getContext(), new VolleyResponseListener() {
                    @Override
                    public void onError(String error) {
                        showSimpleDialog("Error", error);
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        PreferenceUtil.clearData(getContext());
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private void getProfile() {
        ProfileViewModel profileViewModel = new ProfileViewModel();
        ProfileViewModel viewModel = ViewModelProviders.of(this).get(profileViewModel.getClass());
        viewModel.getProfile(getContext(), new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                dismissLoading();
                showSimpleDialog("Error", message);
            }
        }).observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                tvName.setText(profile.getEmployeeName());
                tvAddress.setText(profile.getEmployeeAddress());
                tvHp.setText(profile.getEmployeePhone());
                tvEmail.setText(profile.getEmployeeEmail());
                tvDivision.setText(profile.getDivisionName());
                tvLevel.setText(profile.getLevelName());
                dismissLoading();
            }
        });
    }
}
