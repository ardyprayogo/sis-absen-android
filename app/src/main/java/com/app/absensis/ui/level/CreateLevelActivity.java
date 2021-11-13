package com.app.absensis.ui.level;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class CreateLevelActivity extends BaseActivity {

    private MaterialButton btnSave, btnDelete;
    private TextInputLayout edtLevel;
    private boolean isUpdate = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_level);
        initUI();
        initEvent();
    }

    private void initUI() {
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setVisibility(View.GONE);
        edtLevel = findViewById(R.id.edt_level);
        isUpdate = getIntent().getBooleanExtra("is_update", false);
        if (isUpdate) {
            String level = getIntent().getStringExtra("level");
            id = getIntent().getIntExtra("id", 0);
            edtLevel.getEditText().setText(level);
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                String level = edtLevel.getEditText().getText().toString();
                if (isUpdate)
                    update(level);
                else
                    create(level);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                delete();
            }
        });
    }

    private void create(String level) {
        VolleyUtil.createLevel(this, level, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Jabatan berhasil ditambah.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void update(String level) {
        VolleyUtil.updateLevel(this, id, level, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Jabatan berhasil diupdate.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void delete() {
        VolleyUtil.deleteLevel(this, id, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Jabatan berhasil dihapus.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
}