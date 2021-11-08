package com.app.absensis.network;

import org.json.JSONObject;

public interface VolleyResponseListener {
    void onError(JSONObject error);
    void onResponse(JSONObject response);
}
