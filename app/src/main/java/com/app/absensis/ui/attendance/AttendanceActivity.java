package com.app.absensis.ui.attendance;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.ui.attendance.cico.CicoActivity;
import com.app.absensis.utils.LocationUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AttendanceActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationUtil locationUtil;
    private ImageView ivCi, ivCo;

    private Circle mCircle;
    private LatLng office = new LatLng(-6.1958089, 106.825477);
    private Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGpsActive()) {
            initUI();
            initLocation();
        } else {
            showDialogConfirm("GPS",
                    "GPS Harus Diaktifkan",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
        }
    }

    private boolean isGpsActive() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean retval = false;

        try {
            retval = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }

    private void initUI() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ivCi = findViewById(R.id.iv_check_in);
        ivCo = findViewById(R.id.iv_check_out);
    }

    private void initLocation() {
        locationUtil = new LocationUtil(this, new LocationUtil.LocationUtilInterface() {
            @Override
            public void onLocationChanged(Location location) {
//                updateCamera(location);
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        });
        locationUtil.requestLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setTrafficEnabled(false);
        mMap.setBuildingsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        drawMarkerWithCircle(office);
        updateCamera(office);

    }

    private void updateCamera(LatLng location) {
        LatLng currentLocation = new LatLng(location.latitude, location.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationUtil != null) {
            locationUtil.removeLocation();
            mMap.clear();
        }
    }

    public void gotoCico(View view) {
        inArea(view);
    }

    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 20.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMap.addMarker(markerOptions);
    }

    private void inArea(View view) {
        float[] distance = new float[2];
        Location.distanceBetween(lat, lng,
                mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);

        if(distance[0] > mCircle.getRadius()){
            showSimpleDialog("Error", "Jarak kantor lebih dari 20 meter");
        } else {
            if (view.getId() == R.id.iv_check_in) {
                Intent i = new Intent(this, CicoActivity.class);
                i.putExtra("is_checkin", true);
                startActivity(i);
            } else {
                Intent i = new Intent(this, CicoActivity.class);
                i.putExtra("is_checkin", false);
                startActivity(i);
            }
        }
    }

}