package com.app.absensis.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.model.employee.Employee;
import com.app.absensis.model.level.Level;
import com.app.absensis.network.ViewModelErrorListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmployeeActivity extends BaseActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView rvData;
    private EmployeeAdapter mAdapter;
    private EmployeeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);
        initUI();
        initEvent();
    }

    private void initUI() {
        fabAdd = findViewById(R.id.fab_add);
        rvData = findViewById(R.id.rv_data);
        rvData.setHasFixedSize(true);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initEvent() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeActivity.this, CreateEmployeeActivity.class);
                i.putExtra("is_update", false);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDefaultLoading();
        initRecycler();
    }

    private void getEmployees() {
        viewModel = new EmployeeViewModel();
        viewModel.getEmployees(this, new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                dismissLoading();
                showSimpleDialog("Error", message);
            }
        }).observe(this, new Observer<ArrayList<Employee>>() {
            @Override
            public void onChanged(ArrayList<Employee> employees) {
                dismissLoading();
                mAdapter.setList(employees);
            }
        });
    }

    private void initRecycler() {
        getEmployees();
        mAdapter = new EmployeeAdapter(this, new EmployeeAdapter.EmployeeAdapterInterface() {
            @Override
            public void OnClickListener(Employee employee) {
                Intent i = new Intent(EmployeeActivity.this, CreateEmployeeActivity.class);
                i.putExtra("is_update", true);
                i.putExtra("employee", employee);
                startActivity(i);
            }
        });
        rvData.setAdapter(mAdapter);
    }
}