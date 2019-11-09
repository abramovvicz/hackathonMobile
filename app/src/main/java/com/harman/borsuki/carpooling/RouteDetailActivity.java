package com.harman.borsuki.carpooling;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.models.Passenger;
import com.harman.borsuki.carpooling.services.ApiClient;
import com.harman.borsuki.carpooling.services.ApiInterface;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BorsukiRoute borsukiRoute;
    private EditText text_origin;
    private EditText text_destination;

    Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_detail2);
        mapFragment.getMapAsync(this);

        buttonConfirm = findViewById(R.id.button_confirm);
        text_origin = findViewById(R.id.text_origin);
        text_destination = findViewById(R.id.text_destination);

        buttonConfirm.setOnClickListener(getConfirmation());
        initialize();
    }

    private View.OnClickListener getConfirmation() {
        return v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to confirm this trip?")
                    .setIcon(R.drawable.ic_directions_car_black_24dp)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        Toast.makeText(RouteDetailActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                        sendPassengerDetails();
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        };
    }

    private void sendPassengerDetails() {
        LatLng origin = getLocationByCityName(text_origin.getText().toString());
        LatLng destination = getLocationByCityName(text_destination.getText().toString());
        Passenger passenger = new Passenger(borsukiRoute.getId(), origin, destination);
        System.out.println("DriverID: " + passenger.getDriverId());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Passenger> call = apiInterface.addPassenger(passenger);
        call.enqueue(new Callback<Passenger>() {
            @Override
            public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(RouteDetailActivity.this, "Trip not confirmed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Passenger> call, Throwable t) {
                Toast.makeText(RouteDetailActivity.this, "Trip confirmed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initialize() {

        Gson gson = new Gson();
        String temp = getIntent().getStringExtra("temp");
        borsukiRoute = gson.fromJson(temp, BorsukiRoute.class);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LatLng> route = borsukiRoute.getRoute();
        if (route.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(route).width(10);
            mMap.addPolyline(opts);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(route.get(route.size()/2), 7f));
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return coords;
    }
}
