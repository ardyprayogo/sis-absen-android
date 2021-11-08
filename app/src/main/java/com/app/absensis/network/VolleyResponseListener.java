package com.app.absensis.network;

import org.json.JSONObject;

public interface VolleyResponseListener {
    void onError(String error);
    void onResponse(JSONObject response);
}
