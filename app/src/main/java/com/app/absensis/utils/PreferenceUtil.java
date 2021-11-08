package com.app.absensis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceUtil {

    public static final String KEY_SHARED_PREFERENCE = "_session";
    public static final String KEY_ID = "_id";
    public static final String KEY_EXPIRED = "_expired";
    public static final String KEY_TOKEN = "_token";
    public static final String KEY_EMAIL = "_email";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void setID(Context context, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_ID, value);
        editor.apply();
    }

    public static void setEmail(Context context, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EMAIL, value);
        editor.apply();
    }

    public static void setExpired(Context context, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_EXPIRED, value);
        editor.apply();
    }

    public static void setToken(Context context, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_TOKEN, value);
        editor.apply();
    }

    public static String getData(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static boolean checkTokenExpired(Context context) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateExpired = getData(context, KEY_EXPIRED, null);
            if (dateExpired != null) {
                Date expired = sdf.parse(dateExpired);
                Date now = new Date();
                Log.e("TOKEN", "NOW : "+now);
                Log.e("TOKEN", "EXPIRED: "+expired);
                //expired
                if (now.compareTo(expired) > 0) {
                    clearData(context);
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_EXPIRED);
        editor.remove(KEY_ID);
        editor.apply();
    }
}
