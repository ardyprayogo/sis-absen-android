package com.app.absensis.ui.division;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.model.division.Division;
import com.app.absensis.network.ViewModelErrorListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class DivisionActivity extends BaseActivity {

    private FloatingActionButton fabAdd;
    private RecyclerView rvData;
    private DivisionAdapter mAdapter;
    private DivisionViewModel viewModel;
    private TextInputLayout edtName;

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
        edtName = findViewById(R.id.edt_name);
        rvData.setHasFixedSize(true);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initEvent() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DivisionActivity.this, CreateDivisionActivity.class);
                i.putExtra("is_update", false);
                startActivity(i);
            }
        });
        edtName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                String name = edtName.getEditText().getText().toString();
                getDivisions(name);
            }
        });
    }

    private void getDivisions(String name) {
        viewModel = new DivisionViewModel();
        viewModel.getDivisions(this, name, new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                dismissLoading();
                showSimpleDialog("Error", message);
            }
        }).observe(this, new Observer<ArrayList<Division>>() {
            @Override
            public void onChanged(ArrayList<Division> divisions) {
                dismissLoading();
                mAdapter.setList(divisions);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDefaultLoading();
        initRecycler();
    }

    private void initRecycler() {
        getDivisions(null);
        mAdapter = new DivisionAdapter(DivisionActivity.this, new DivisionAdapter.DivisionAdapterInterface() {
            @Override
            public void OnClick(Division division) {
                Intent i = new Intent(DivisionActivity.this, CreateDivisionActivity.class);
                i.putExtra("is_update", true);
                i.putExtra("id", division.getId());
                i.putExtra("division", division.getDivisionName());
                startActivity(i);

            }
        });
        rvData.setAdapter(mAdapter);
    }
}