package com.harman.borsuki.carpooling;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harman.borsuki.carpooling.Services.DeviceLocationService;
import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.services.RouteService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    private FirebaseUser user;
    private EditText destinationText;
    private EditText startingPlaceText;
    private EditText phoneText;
    private EditText dateText;
    private EditText distanceText;
    private Button buttonSendRoad;
    private DateTimeFormatter formatter;
    private com.harman.borsuki.carpooling.Services.DeviceLocationService deviceLocationService;
    private LatLng actualPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
    }

    private void initialize() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        destinationText = findViewById(R.id.destinationText);
        startingPlaceText = findViewById(R.id.startingPlaceText);
        phoneText = findViewById(R.id.phoneNumberText);
        dateText = findViewById(R.id.dateText);
        distanceText = findViewById(R.id.distanceText);
        buttonSendRoad = findViewById(R.id.sendRoadButton);
        buttonSendRoad.setOnClickListener(getSendRoad());
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        deviceLocationService = new DeviceLocationService();
        actualPlace = deviceLocationService.getDeviceLocation(this);

    }

    private View.OnClickListener getSendRoad(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorsukiRoute borsukiRoute = new BorsukiRoute();
                borsukiRoute.setDestinationName(destinationText.getText().toString());
                borsukiRoute.setStartingName(startingPlaceText.getText().toString());
                borsukiRoute.setDriverName(user.getDisplayName());
                borsukiRoute.setPhoneNumber(phoneText.getText().toString());
                borsukiRoute.setDistance(Double.valueOf(distanceText.getText().toString()));
                borsukiRoute.setDateTime(getDate(dateText.getText().toString()));
                List<LatLng> latLngList = new ArrayList<>();

                LatLng destinationLatLng = getLocationByCityName(destinationText.getText().toString());
                LatLng startingPlaceLatLng = getLocationByCityName(startingPlaceText.getText().toString());

                latLngList.add(startingPlaceLatLng);
                latLngList.add(destinationLatLng);

                borsukiRoute.setRoute(latLngList);
                borsukiRoute.setRoute(RouteService.getRoute(borsukiRoute));

                getActualLocation();

                Log.d("Debug", "onClick: " + borsukiRoute.toString());
                Uri gmmIntentUri = Uri.parse("https://maps.google.com/maps?saddr=" + startingPlaceLatLng.latitude +","+ startingPlaceLatLng.longitude + "&daddr=" +destinationLatLng.latitude + "," + destinationLatLng.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        };
    }

    private void getActualLocation(){
        actualPlace = deviceLocationService.getDeviceLocation(this);
    }

    private LocalDateTime getDate(String time){
        return LocalDateTime.parse(time, formatter);
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
                Log.d("DEBUG", "found city:" + coords.toString() + "\n\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return coords;
    }

}
