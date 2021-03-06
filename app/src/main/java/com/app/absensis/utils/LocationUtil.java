package com.app.absensis.utils;

import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationUtil {

    private static LocationUtil mLocationUtil;
    protected LocationRequest mLocationRequest;
    private double longitude, latitude;
    private Context context;
    private FusedLocationProviderClient providerClient;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            onLocationChanged(locationResult.getLastLocation());
        }
    };

    private static final long MIN_TIME_BW_UPDATES = 1000 * 5;

    public static LocationUtil getInstance(Context context) {
        if (mLocationUtil == null) {
            mLocationUtil = new LocationUtil(context);

        }

        return mLocationUtil;
    }

    public LocationUtil(Context context) {
        this.context = context;
        requestLocation();
    }

    private void requestLocation() {
        try {
            mLocationRequest = LocationRequest.create()
                    .setFastestInterval(MIN_TIME_BW_UPDATES)
                    .setInterval(MIN_TIME_BW_UPDATES)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            providerClient = LocationServices.getFusedLocationProviderClient(context);
            providerClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private void onLocationChanged(Location location) {
        setLatitude(location.getLatitude());
        setLongitude(location.getLongitude());
    }

    public void removeLocation() {
        if (providerClient != null)
            providerClient.removeLocationUpdates(mLocationCallback);
    }
}
