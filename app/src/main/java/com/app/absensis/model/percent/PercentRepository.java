package com.app.absensis.model.percent;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;

import org.json.JSONObject;

public class PercentRepository {

    private ViewModelErrorListener listener;

    public PercentRepository(ViewModelErrorListener listener) {
        this.listener = listener;
    }

    public MutableLiveData<Percent> getPercent(Context context) {
        final MutableLiveData<Percent> data = new MutableLiveData<>();
        VolleyUtil.getPercent(context, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                listener.OnErrorListener(error);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("data");
                    Percent percent = new Percent();
                    percent.setPercentation(obj.getString("percentation"));
                    percent.setAttendance(obj.getInt("attendance"));
                    percent.setWork(obj.getInt("work"));
                    data.setValue(percent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return data;
    }
}
