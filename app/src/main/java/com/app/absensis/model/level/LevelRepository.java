package com.app.absensis.model.level;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.app.absensis.network.ViewModelErrorListener;
import com.app.absensis.network.VolleyResponseListener;
import com.app.absensis.network.VolleyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LevelRepository {

    private ViewModelErrorListener mListener;

    public LevelRepository(ViewModelErrorListener mListener) {
        this.mListener = mListener;
    }

    public MutableLiveData<ArrayList<Level>> getLevels(Context context, String name) {
        final MutableLiveData<ArrayList<Level>> data = new MutableLiveData<>();
        VolleyUtil.getListLevel(context, name, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                 mListener.OnErrorListener(error);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("data");
                    Gson gson = new Gson();
                    Type typeList = new TypeToken<ArrayList<Level>>(){}.getType();
                    ArrayList<Level> levelList = gson.fromJson(object.toString(), typeList);
                    data.setValue(levelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
    }
}
