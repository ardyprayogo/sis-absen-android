package com.app.absensis.model.division;
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

public class CreateDivisionActivity extends BaseActivity {

    private MaterialButton btnSave, btnDelete;
    private TextInputLayout edtDivision;
    private boolean isUpdate = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_division);
        initUI();
        initEvent();
    }

    private void initUI() {
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setVisibility(View.GONE);
        edtDivision = findViewById(R.id.edt_division);
        isUpdate = getIntent().getBooleanExtra("is_update", false);
        if (isUpdate) {
            String division = getIntent().getStringExtra("division");
            id = getIntent().getIntExtra("id", 0);
            edtDivision.getEditText().setText(division);
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                String division = edtDivision.getEditText().getText().toString();
                if (isUpdate)
                    update(division);
                else
                    create(division);
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

    private void update(String division) {
        VolleyUtil.updateDivsion(this, id, division, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Divisi berhasil diupdate.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void delete() {
        VolleyUtil.deleteDivsion(this, id, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Divisi berhasil dihapus.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void create(String division) {
        VolleyUtil.createDivsion(this, division, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Divisi berhasil ditambah.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

}