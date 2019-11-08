package com.harman.borsuki.carpooling;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.harman.borsuki.carpooling.models.BorsukiRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPremissions();
        checkGoogleServices();
        getLocationByCityName("Warszawa");
    }

    private LatLng getLocationByCityName(String cityName) {
        LatLng coords = null;
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(this);
                Address addresses = gc.getFromLocationName(cityName, 5).get(0);

                if (addresses.hasLatitude() && addresses.hasLongitude()) {
                    coords = new LatLng(addresses.getLatitude(), addresses.getLongitude());
                }
                Log.d(TAG, "found city:" + coords.toString() + "\n\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return coords;
    }


public LatLng getDeviceLocation(){
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    LatLng currentLocation = new LatLng(0,0);
    try {
        if (mLocationPermissionGranted) {
            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d("Maps", "Found location");
                        Location currentLocation = (Location) task.getResult();
                        LatLng temp = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, 15f));
                        mMap.setMyLocationEnabled(true);
                        currentLocation.setLatitude(temp.latitude);
                        currentLocation.setLongitude(temp.longitude);

                    }else {
                        Log.d( "Maps", "Current location is null");
                    }
                }
            });
        }
    }catch (SecurityException e) {
        Log.e("Maps", e.getMessage());
    }
    return currentLocation;
}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        if (mLocationPermissionGranted) {
            getDeviceLocation();
        }
    }

    private List<LatLng> getRoute(BorsukiRoute borsukiRoute) {
        LatLng origin = borsukiRoute.getRoute().get(0);
        LatLng dest = borsukiRoute.getRoute().get(borsukiRoute.getRoute().size()-1);

        mMap.addMarker(new MarkerOptions().position(origin).title(borsukiRoute.getStartingName()));
        mMap.addMarker(new MarkerOptions().position(dest).title(borsukiRoute.getDestinationName()));

        List<LatLng> path = new ArrayList();


        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDk47IpF95u-ZtElNyByroR3l5BmZXcWrI")
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, borsukiRoute.coordinationToString(origin), borsukiRoute.coordinationToString(dest));
        try {
            DirectionsResult res = req.await();
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }

        return path;

    }

    private void checkGoogleServices() {
        int maps_available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (maps_available == ConnectionResult.SUCCESS)
        {
            Log.d("Maps","its fine");
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(maps_available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, maps_available, 9001);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Maps not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1:{
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
    private void getLocationPremissions(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (float) (earthRadius * c);
    }
}
