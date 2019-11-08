package com.harman.borsuki.carpooling.services;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

public class DeviceLocationService {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final static String TAG = "DeviceLocationService";

    public LatLng getDeviceLocation(Activity activity){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        LatLng currentLocation = new LatLng(0,0);
        try {

            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Found location");
                    Location currentLocation1 = (Location) task.getResult();
                    LatLng temp = new LatLng(currentLocation1.getLatitude(), currentLocation1.getLongitude());
                    currentLocation1.setLatitude(temp.latitude);
                    currentLocation1.setLongitude(temp.longitude);

                }else {
                    Log.d( TAG, "Current location is null");
                }
            });
        }catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }
        return currentLocation;
    }
}
