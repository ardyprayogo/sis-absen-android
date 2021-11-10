package com.app.absensis.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.absensis.BaseActivity;
import com.app.absensis.MainActivity;
import com.app.absensis.R;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.app.absensis.utils.PreferenceUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private TextInputLayout edtEmail;
    private TextInputLayout edtPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkToken();
        initUI();
        initEvent();
    }

    private void initUI() {
        hideActionBar();
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        String email = PreferenceUtil.getData(this, PreferenceUtil.KEY_EMAIL, null);
        if (email != null) edtEmail.getEditText().setText(email);
    }

    private void initEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getEditText().getText().toString();
                String password = edtPassword.getEditText().getText().toString();
                sendLogin(email, password);
            }
        });
    }

    private void sendLogin(String email, String password) {
        showLoading(getString(R.string.loading), false);
        PreferenceUtil.setEmail(LoginActivity.this, email);
        VolleyUtil.sendLogin(LoginActivity.this, email, password, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                try {
                    dismissLoading();
                    showSimpleDialog("Failed", error);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    dismissLoading();
                    JSONObject data = response.getJSONObject("data");
                    String accessToken = data.getString("access_token");
                    String expired = data.getString("expired");
                    String idUser = data.getJSONObject("employee_info").getString("id");

                    PreferenceUtil.setToken(LoginActivity.this, accessToken);
                    PreferenceUtil.setExpired(LoginActivity.this, expired);
                    PreferenceUtil.setID(LoginActivity.this, idUser);

                    goMain();
                } catch (Exception e) {
                    dismissLoading();
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkToken() {
        if (PreferenceUtil.checkTokenExpired(this))
            goMain();
    }

    private void goMain() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}