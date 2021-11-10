package com.app.absensis.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.absensis.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyUtil {

    public static void getReportAttendance(Context context, VolleyResponseListener listener) {
        try {
            String url = Route.URL_REPORT_ATTENDANCE;
            JSONObject object = new JSONObject();
            object.put("date_start", "2021-11-01");
            object.put("date_end", "2021-11-30");
            sendPostRequest(context, url, object, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendLogin(Context context, String email, String password, VolleyResponseListener listener) {
        try {
            JSONObject object = new JSONObject();
            object.put("email", email);
            object.put("password", password);
            object.put("password_confirmation", password);
            sendPostRequest(context, Route.URL_LOGIN, object, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getProfile(Context context, VolleyResponseListener listener) {
        sendPostRequest(context, Route.URL_PROFILE, null, listener);
    }

    private static void sendPostRequest(Context context,
                                        String url,
                                        JSONObject json,
                                        final VolleyResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Route.BASE_URL + url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Log.d("API", Route.BASE_URL + url + " => " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            String data = new String(networkResponse.data);
                            try {
                                String message = new JSONObject(data).getString("message");
                                listener.onError(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.e("API", Route.BASE_URL + url + " => " + data);
                        } else {
                            try {
                                listener.onError(error.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String accessToken = PreferenceUtil.getData(context, PreferenceUtil.KEY_TOKEN, null);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application-json");
                if (accessToken != null) headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);

    }

    private static void sendGetRequest(Context context,
                                       String url,
                                       String param,
                                       final VolleyResponseListener listener) {
        String finalUrl = url + "?" + param;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Route.BASE_URL + url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                        Log.d("API", Route.BASE_URL + url + " => " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            String data = new String(networkResponse.data);
                            try {
                                String message = new JSONObject(data).getString("message");
                                listener.onError(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.e("API", finalUrl);
                        } else {
                            try {
                                listener.onError(error.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String accessToken = PreferenceUtil.getData(context, PreferenceUtil.KEY_TOKEN, null);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application-json");
                if (accessToken != null) headers.put("Authorization", "Bearer " + accessToken);
                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
