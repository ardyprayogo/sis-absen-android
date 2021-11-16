package com.app.absensis.model.division;

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

public class DivisionRepository {

    private ViewModelErrorListener listener;

    public DivisionRepository(ViewModelErrorListener listener) {
        this.listener = listener;
    }

    public MutableLiveData<ArrayList<Division>> getDivisions(Context context, String name) {
        final MutableLiveData<ArrayList<Division>> data = new MutableLiveData<>();
        VolleyUtil.getListDivision(context, name, new VolleyResponseListener() {
            @Override
            public void onError(String error) {
                listener.OnErrorListener(error);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("data");
                    Gson gson = new Gson();
                    Type typeList = new TypeToken<ArrayList<Division>>(){}.getType();
                    ArrayList<Division> divisionList = gson.fromJson(object.toString(), typeList);
                    data.setValue(divisionList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return data;
    }
}
