package com.erunetimeterror.wai.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.erunetimeterror.wai.MainApplication;
import com.erunetimeterror.wai.R;
import com.erunetimeterror.wai.utils.Statics;
import com.erunetimeterror.wai.utils.Tools;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragments extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap mMap;
    Button btnLocation;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    MarkerOptions userMOptions;
    Marker user = null;
    Bitmap userBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        btnLocation = view.findViewById(R.id.btLoc);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        getContext().registerReceiver(locationReceiver, new IntentFilter(Statics.LOCATION));

        userBitmap = Tools.getBitmap(getContext(),R.drawable.mappin);
        userBitmap = Bitmap.createScaledBitmap(userBitmap, 80, 80, false);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(wayLatitude, wayLongitude), 15.0f));
            }
        });
    }
    BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Statics.LOCATION)){
                wayLatitude = intent.getDoubleExtra("latitude",0);
                wayLongitude = intent.getDoubleExtra("longitude",0);
                setPosition(wayLatitude,wayLongitude);
            }
        }
    };
    private void setPosition(double wayLatitude, double wayLongitude){
        LatLng userPos = new LatLng(wayLatitude, wayLongitude);
        if (user == null){
            userMOptions = new MarkerOptions().position(userPos).icon(BitmapDescriptorFactory.fromBitmap(userBitmap));
            user = mMap.addMarker(userMOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(wayLatitude, wayLongitude), 15.0f));
        }else {
            user.setPosition(userPos);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                        getContext(), R.raw.map_style_json));
        mMap = googleMap;
        MainApplication app = (MainApplication) getContext().getApplicationContext();

        wayLatitude = app.latitude;
        wayLongitude = app.longtitude;
        setPosition(wayLatitude,wayLongitude);
    }
}
