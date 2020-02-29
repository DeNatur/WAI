package com.erunetimeterror.wai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.multidex.MultiDexApplication;

import com.erunetimeterror.wai.utils.Statics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainApplication extends MultiDexApplication {
    static Context context;
    public FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    public double latitude = 0.0, longtitude = 0.0;

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.context = getApplicationContext();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);


    }
    private void createLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    public void setLastLocation(Activity activity){
        fusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                    Intent i = new Intent(Statics.LOCATION);
                    i.putExtra(Statics.LATITUDE, latitude);
                    i.putExtra(Statics.LONGITUDE, longtitude);
                    sendBroadcast(i);
                }
            }
        });
    }
    public void initializeLocation(){
        createLocationRequest();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longtitude = location.getLongitude();
                        Intent i = new Intent(Statics.LOCATION);
                        i.putExtra(Statics.LATITUDE, latitude);
                        i.putExtra(Statics.LONGITUDE, longtitude);
                        sendBroadcast(i);
                    }
                }
            }
        };
    }
}
