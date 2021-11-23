package com.app.absensis.ui.attendance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.app.absensis.BaseActivity;
import com.app.absensis.R;
import com.app.absensis.ui.attendance.cico.CicoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class AttendanceActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Circle mCircle;
    private final LatLng office = new LatLng(-6.2506412, 106.6932953);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        initUI();
    }

    private void initUI() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        updateCamera();
    }

    private void updateCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(office);
        builder.include(new LatLng(getLat(), getLng()));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(office));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
            }
        });

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
        Location.distanceBetween(getLat(), getLng(),
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