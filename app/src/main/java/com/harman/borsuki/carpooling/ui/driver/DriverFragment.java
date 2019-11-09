package com.harman.borsuki.carpooling.ui.driver;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harman.borsuki.carpooling.AfterLogin;
import com.harman.borsuki.carpooling.DriverActivity;
import com.harman.borsuki.carpooling.DriverTrip;
import com.harman.borsuki.carpooling.R;
import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.services.ApiClient;
import com.harman.borsuki.carpooling.services.ApiInterface;
import com.harman.borsuki.carpooling.services.DeviceLocationService;
import com.harman.borsuki.carpooling.services.RouteService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverFragment extends Fragment {

    private DriverViewModel dashboardViewModel;
    private FirebaseUser user;
    private EditText destinationText;
    private EditText startingPlaceText;
    private EditText phoneText;
    private EditText dateText;
    private EditText distanceText;
    private Button buttonSendRoad;
    private DateTimeFormatter formatter;
    private com.harman.borsuki.carpooling.services.DeviceLocationService deviceLocationService;
    private LatLng actualPlace;
    private BorsukiRoute borsukiRoute;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DriverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_driver, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        destinationText = root.findViewById(R.id.destinationText);
        startingPlaceText = root.findViewById(R.id.startingPlaceText);
        phoneText = root.findViewById(R.id.phoneNumberText);
        dateText = root.findViewById(R.id.dateText);
        distanceText = root.findViewById(R.id.distanceText);
        buttonSendRoad = root.findViewById(R.id.sendRoadButton);
        buttonSendRoad.setOnClickListener(getSendRoad());
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        deviceLocationService = new DeviceLocationService();
        actualPlace = deviceLocationService.getDeviceLocation(this.getActivity());

        return root;
    }

    private View.OnClickListener getSendRoad(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borsukiRoute = new BorsukiRoute();
                borsukiRoute.setDestinationName(destinationText.getText().toString());
                borsukiRoute.setStartingName(startingPlaceText.getText().toString());
                borsukiRoute.setDriverName(user.getDisplayName());
                borsukiRoute.setPhoneNumber(phoneText.getText().toString());
                borsukiRoute.setDistance(Double.valueOf(distanceText.getText().toString()));
                borsukiRoute.setDateTime(dateText.getText().toString());
                List<LatLng> latLngList = new ArrayList<>();

                LatLng destinationLatLng = getLocationByCityName(destinationText.getText().toString());
                LatLng startingPlaceLatLng = getLocationByCityName(startingPlaceText.getText().toString());

                latLngList.add(startingPlaceLatLng);
                latLngList.add(destinationLatLng);

                borsukiRoute.setRoute(latLngList);
                borsukiRoute.setRoute(RouteService.getRoute(borsukiRoute));

                getActualLocation();

                Log.d("Debug", "onClick: " + borsukiRoute.toString());

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<BorsukiRoute> call = apiInterface.addRoute(borsukiRoute);
                call.enqueue(new Callback<BorsukiRoute>() {
                    @Override
                    public void onResponse(Call<BorsukiRoute> call, Response<BorsukiRoute> response) {
                        if(response.isSuccessful()){
                            borsukiRoute = response.body();
                            Log.e("XD", "onResponse: " + borsukiRoute.getId());
                            generateTrip(borsukiRoute.getId());
                        }
                    }

                    @Override
                    public void onFailure(Call<BorsukiRoute> call, Throwable t) {
                        Log.e("Error: ", t.getMessage());
                    }
                });

            }
        };
    }

    private void generateTrip(String id){
        Intent i = new Intent(this.getContext(), DriverTrip.class);
        i.putExtra("driverId", id);
        startActivity(i);
    }

    private void getActualLocation(){
        actualPlace = deviceLocationService.getDeviceLocation(this.getActivity());
    }

    private LocalDateTime getDate(String time){
        return LocalDateTime.parse(time, formatter);
    }

    private LatLng getLocationByCityName(String cityName) {
        LatLng coords = null;
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(this.getContext());
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