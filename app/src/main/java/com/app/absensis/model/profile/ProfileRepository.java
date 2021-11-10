package com.app.absensis.model.profile;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;

import org.json.JSONObject;

public class ProfileRepository {

    private ViewModelErrorListener listener;

    public ProfileRepository(ViewModelErrorListener listener) {
        this.listener = listener;
    }

    public MutableLiveData<Profile> requestProfile(Context context) {
        final MutableLiveData<Profile> data = new MutableLiveData<>();
        VolleyUtil.getProfile(context, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                listener.OnErrorListener(error);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response.getJSONObject("data");
                    Profile profile = new Profile();
                    profile.setEmployeeName(obj.getString("employee_name"));
                    profile.setEmployeeAddress(obj.getString("employee_address"));
                    profile.setEmployeePhone(obj.getString("employee_phone"));
                    profile.setEmployeeEmail(obj.getString("employee_email"));
                    profile.setEmployeeEmail(obj.getString("employee_email"));
                    profile.setLevelName(obj.getString("level_name"));
                    profile.setDivisionName(obj.getString("division_name"));
                    data.setValue(profile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return data;
    }
}
