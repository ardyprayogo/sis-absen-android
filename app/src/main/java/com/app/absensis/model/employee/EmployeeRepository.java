package com.app.absensis.model.employee;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.absensis.model.division.Division;
import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EmployeeRepository {

    private ViewModelErrorListener listener;

    public EmployeeRepository(ViewModelErrorListener listener) {
        this.listener = listener;
    }

    public MutableLiveData<ArrayList<Employee>> getEmployees(Context context) {
        final MutableLiveData<ArrayList<Employee>> data = new MutableLiveData<>();
        VolleyUtil.getListEmployee(context, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                listener.OnErrorListener(error);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("data");
                    Gson gson = new Gson();
                    Type typeList = new TypeToken<ArrayList<Employee>>() {
                    }.getType();
                    ArrayList<Employee> employeeList = gson.fromJson(object.toString(), typeList);
                    data.setValue(employeeList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
    }
}
