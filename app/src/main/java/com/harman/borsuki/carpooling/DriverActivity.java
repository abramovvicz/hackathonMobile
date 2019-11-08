package com.harman.borsuki.carpooling;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    private FirebaseUser user;
    private EditText destinationText;
    private EditText startingPlaceText;
    private EditText phoneText;
    private EditText dateText;
    private Button buttonSendRoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initialize() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        destinationText = findViewById(R.id.destinationText);
        startingPlaceText = findViewById(R.id.startingPlaceText);
        phoneText = findViewById(R.id.phoneNumberText);
        dateText = findViewById(R.id.dateText);
        Button buttonSendRoad = findViewById(R.id.sendRoadButton);

        buttonSendRoad.setOnClickListener(getSendRoad());

    }

    private View.OnClickListener getSendRoad(){
//        String destination = destinationText.getText().toString();
//        String startingPlace = startingPlaceText.getText().toString();
//        String userName = user.getDisplayName();
//        String phoneNumber = phoneText.getText().toString();
//        //LocalDateTime date = dateText.getText().toString();
//        Log.d("XD", "getSendRoad: " + dateText.getText().toString());
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = destinationText.getText().toString();
                String startingPlace = startingPlaceText.getText().toString();
                String userName = user.getDisplayName();
                String phoneNumber = phoneText.getText().toString();
                //LocalDateTime date = dateText.getText().toString();
                Log.d("XD", "getSendRoad: " + dateText.getText().toString());
                Log.d("XD", "getSendRoad: " + destination + " lat " + getLoation(destination));
                //startActivity(new Intent(UserHomepageActivity.this, MapsActivity.class));
            }
        };
    }

    private LatLng getLoation(String cityName) {
        if(Geocoder.isPresent()){
            try {
                String location = cityName;
                Geocoder gc = new Geocoder(this);
                List<Address> addresses= gc.getFromLocationName(location, 5); // get the found Address Objects

                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                System.out.println("Before addresses\n\n");
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        return ll.get(0);
                    }
                }
            } catch (IOException e) {
                // handle the exception
            }
        }
        return null;
    }

}
