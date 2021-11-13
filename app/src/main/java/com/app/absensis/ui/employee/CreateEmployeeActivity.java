package com.app.absensis.ui.employee;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.Observer;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.model.division.Division;
import com.app.absensis.model.employee.Employee;
import com.app.absensis.model.level.Level;
import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;

public class CreateEmployeeActivity extends BaseActivity {

    private TextInputLayout edtName, edtEmail, edtPassword, edtPhone, edtAddress;
    private AutoCompleteTextView spDivision, spLevel;
    private Employee mEmployee;
    private boolean isUpdate;
    private MaterialButton btnSave, btnDelete;
    private EmployeeViewModel viewModel;
    private final Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        initUI();
        initEvent();
        initSelect();

        isUpdate = getIntent().getBooleanExtra("is_update", false);
        if (isUpdate) {
            mEmployee = (Employee) getIntent().getSerializableExtra("employee");
            spLevel.setText(mEmployee.getLevelName(), false);
            spDivision.setText(mEmployee.getDivisionName(), false);
            employee.setLevelId(mEmployee.getLevelId());
            employee.setDivisionId(mEmployee.getDivisionId());
            initUpdate();
        }
    }

    private void initUpdate() {
        edtPassword.setVisibility(View.GONE);
        btnDelete.setVisibility(View.VISIBLE);
        edtName.getEditText().setText(mEmployee.getEmployeeName());
        edtEmail.getEditText().setText(mEmployee.getEmployeeEmail());
        edtPhone.getEditText().setText(mEmployee.getEmployeePhone());
        edtAddress.getEditText().setText(mEmployee.getEmployeeAddress());
    }

    private void initUI() {
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);
        spDivision = findViewById(R.id.sp_division);
        spLevel = findViewById(R.id.sp_level);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setVisibility(View.GONE);
    }

    private void initSelect() {
        viewModel = new EmployeeViewModel();
        viewModel.getLevels(this, new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                showSimpleDialog("Error", message);
            }
        }).observe(this, new Observer<ArrayList<Level>>() {
            @Override
            public void onChanged(ArrayList<Level> levels) {
                ArrayAdapter<Level> adapter = new ArrayAdapter<Level>(CreateEmployeeActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        levels);
                spLevel.setAdapter(adapter);
            }
        });

        viewModel.getDivisions(this, new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                showSimpleDialog("Error", message);
            }
        }).observe(this, new Observer<ArrayList<Division>>() {
            @Override
            public void onChanged(ArrayList<Division> divisions) {
                ArrayAdapter<Division> adapter = new ArrayAdapter<Division>(CreateEmployeeActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        divisions);
                spDivision.setAdapter(adapter);
            }
        });
    }

    private void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                employee.setEmployeeEmail(edtEmail.getEditText().getText().toString());
                employee.setEmployeeName(edtName.getEditText().getText().toString());
                employee.setEmployeePhone(edtPhone.getEditText().getText().toString());
                employee.setEmployeeAddress(edtAddress.getEditText().getText().toString());
                if (isUpdate) {
                    employee.setId(mEmployee.getId());
                    update(employee);
                } else {
                    employee.setPassword(edtPassword.getEditText().getText().toString());
                    create(employee);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                delete();
            }
        });

        spLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Level selected = (Level) adapterView.getAdapter().getItem(i);
                employee.setLevelId(selected.getId());
            }
        });
        spDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Division selected = (Division) adapterView.getAdapter().getItem(i);
                employee.setDivisionId(selected.getId());
            }
        });
    }

    private void create(Employee employee) {
        VolleyUtil.createEmployee(this, employee, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Karyawan berhasil ditambah.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void update(Employee employee) {
        VolleyUtil.updateEmployee(this, employee, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Karyawan berhasil diubah.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }

    private void delete() {
        int id = mEmployee.getId();
        VolleyUtil.deleteEmployee(this, id, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                dismissLoading();
                showSimpleDialog("Error", error);
            }

            @Override
            public void onResponse(JSONObject response) {
                dismissLoading();
                showDialogConfirm("Success", "Karyawan berhasil dihapus.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        });
    }
}