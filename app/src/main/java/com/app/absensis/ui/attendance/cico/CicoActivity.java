package com.app.absensis.ui.attendance.cico;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.concurrent.Executor;

public class CicoActivity extends BaseActivity {

    private MaterialButton btnAuth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private boolean isCheckin;
    private ImageView ivAuth;
    private LottieAnimationView lottieAnimationView;
    private TextView tvAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cico);
        hideActionBar();
        initUI();
        initEvent();
        initFingerPrint();
    }

    private void initUI() {
        btnAuth = findViewById(R.id.btn_auth);
        isCheckin = getIntent().getBooleanExtra("is_checkin", false);
        ivAuth = findViewById(R.id.iv_auth);
        lottieAnimationView = findViewById(R.id.animation_done);
        tvAuth = findViewById(R.id.tv_auth);
    }

    private void initEvent() {

    }

    private void initFingerPrint() {
        executor = ContextCompat.getMainExecutor(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt(this,
                    executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    showSimpleDialog("Error", errString.toString());
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    showDefaultLoading();
                    if (isCheckin) {
                        VolleyUtil.sendCheckin(CicoActivity.this, new VolleyResponseListener() {
                            @Override
                            public void onError(String error) {
                                showSimpleDialog("Error", error);
                                dismissLoading();
                            }

                            @Override
                            public void onResponse(JSONObject response) {
                                lottieAnimationView.setVisibility(View.VISIBLE);
                                btnAuth.setVisibility(View.GONE);
                                ivAuth.setVisibility(View.GONE);
                                tvAuth.setVisibility(View.VISIBLE);
                                dismissLoading();
                            }
                        });
                    } else {
                        VolleyUtil.sendCheckout(CicoActivity.this, new VolleyResponseListener() {
                            @Override
                            public void onError(String error) {
                                showSimpleDialog("Error", error);
                                dismissLoading();
                            }

                            @Override
                            public void onResponse(JSONObject response) {
                                lottieAnimationView.setVisibility(View.VISIBLE);
                                btnAuth.setVisibility(View.GONE);
                                ivAuth.setVisibility(View.GONE);
                                tvAuth.setVisibility(View.VISIBLE);
                                dismissLoading();
                            }
                        });
                    }
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Autentikasi Fingerprint")
                    .setSubtitle("Gunakan fingerprint untuk autentikasi absen.")
                    .setNegativeButtonText("Cancel")
                    .build();

            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    biometricPrompt.authenticate(promptInfo);
                }
            });
        }
    }
}