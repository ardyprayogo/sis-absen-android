package com.app.absensis.ui.history;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.model.attendance.Attendance;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends BaseActivity {

    private TextInputLayout edtDate;
    private Date dateStart, dateEnd;
    private MaterialButton btnSearch;
    private SimpleDateFormat sdfTv = new SimpleDateFormat("dd/MM/yyyy");
    private RecyclerView rvData;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        initUI();
        initEvent();
        initAdapter();
    }

    private void initUI() {
        edtDate = findViewById(R.id.edt_date);
        btnSearch = findViewById(R.id.btn_search);

        rvData = findViewById(R.id.rv_data);
        rvData.setHasFixedSize(true);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initAdapter() {
        adapter = new HistoryAdapter(this, new HistoryAdapter.HistoryAdapterInterface() {
            @Override
            public void OnClickListener(Attendance attendance) {

            }
        });
        rvData.setAdapter(adapter);
    }

    private void initEvent() {
        edtDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateStart != null && dateEnd != null) {
                    showDefaultLoading();
                    VolleyUtil.getReportAttendance(HistoryActivity.this, dateStart, dateEnd,
                            new VolleyResponseListener() {
                                @Override
                                public void onError(String error) {
                                    dismissLoading();
                                    showSimpleDialog("Error", error);
                                }

                                @Override
                                public void onResponse(JSONObject response) {
                                    dismissLoading();
                                    try {
                                        JSONArray object = response.getJSONArray("data");
                                        Gson gson = new Gson();
                                        Type typeList = new TypeToken<ArrayList<Attendance>>() {}.getType();
                                        ArrayList<Attendance> list = gson.fromJson(object.toString(), typeList);
                                        adapter.setList(list);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    showSimpleDialog("Error", "Silahkan pilih tanggal");
                }
            }
        });
    }

    private void initDatePicker() {
        CalendarConstraints.Builder cb = new CalendarConstraints.Builder();
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder
                .dateRangePicker()
                .setSelection(
                        new Pair(
                                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                MaterialDatePicker.todayInUtcMilliseconds()
                        )
                )
                .setCalendarConstraints(cb.build());
        
        MaterialDatePicker<?> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());
        
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Object>() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Pair<Long, Long> result = (Pair<Long, Long>) selection;
                dateStart = new Date(result.first);
                dateEnd = new Date(result.second);

                String strDateStart = sdfTv.format(dateStart);
                String strDateEnd = sdfTv.format(dateEnd);
                edtDate.getEditText().setText(strDateStart+" - "+strDateEnd);
            }
        });
    }
}