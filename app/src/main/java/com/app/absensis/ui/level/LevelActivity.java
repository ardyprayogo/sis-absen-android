package com.app.absensis.ui.level;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.model.level.Level;
import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.ui.division.CreateDivisionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LevelActivity extends BaseActivity {

    private LevelViewModel viewModel;
    private FloatingActionButton fabAdd;
    private RecyclerView rvData;
    private LevelAdapter adapter;
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
                Intent i = new Intent(LevelActivity.this, CreateLevelActivity.class);
                i.putExtra("is_update", false);
                startActivity(i);
            }
        });
        edtName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDefaultLoading();
                String name = edtName.getEditText().getText().toString();
                getLevels(name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDefaultLoading();
        initRecycler();
    }

    private void getLevels(String name) {
        viewModel = new LevelViewModel();
        viewModel.getLevels(this, name, new ViewModelErrorListener() {
            @Override
            public void OnErrorListener(String message) {
                dismissLoading();
                showSimpleDialog("Error", message);
            }
        }).observe(this, new Observer<ArrayList<Level>>() {
            @Override
            public void onChanged(ArrayList<Level> levels) {
                dismissLoading();
                adapter.setmList(levels);
            }
        });
    }

    private void initRecycler() {
        getLevels(null);
        adapter = new LevelAdapter(this, new LevelAdapter.LevelAdapterInterface() {
            @Override
            public void OnClickListener(Level level) {
                Intent i = new Intent(LevelActivity.this, CreateLevelActivity.class);
                i.putExtra("is_update", true);
                i.putExtra("id", level.getId());
                i.putExtra("level", level.getLevelName());
                startActivity(i);
            }
        });
        rvData.setAdapter(adapter);
    }
}