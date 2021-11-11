package com.app.absensis.ui.attendance.cico;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.Executor;

public class CicoActivity extends BaseActivity {

    private MaterialButton btnAuth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cico);
        initUI();
        initEvent();
        initFingerPrint();
    }

    private void initUI() {
        btnAuth = findViewById(R.id.btn_auth);
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

                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Autentikasi Fingerprint")
                    .setSubtitle("Gunakan fingerprint untuk autentikasi absen.")
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