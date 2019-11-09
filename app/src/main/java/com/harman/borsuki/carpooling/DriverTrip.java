package com.harman.borsuki.carpooling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.models.Passenger;
import com.harman.borsuki.carpooling.services.ApiClient;
import com.harman.borsuki.carpooling.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverTrip extends AppCompatActivity {
    private TextView name;
    private TextView start;
    private TextView destination;
    private Button refresh;
    private Button accept;
    private String driverId;
    private Passenger passengerRequest;
    BorsukiRoute borsukiRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trip);

        name = findViewById(R.id.nameTextView);
        destination = findViewById(R.id.destinationText);
        refresh = findViewById(R.id.refreshButton);
        accept = findViewById(R.id.acceptButton);

        initialize();

        Intent intent = getIntent();
        Log.e("XD", "onCreate: "+ intent.getExtras().getString("driverId"));
        driverId = intent.getExtras().getString("driverId");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BorsukiRoute> call = apiInterface.getRoute(driverId);
        call.enqueue(new Callback<BorsukiRoute>() {
            @Override
            public void onResponse(Call<BorsukiRoute> call, Response<BorsukiRoute> response) {
                borsukiRoute = response.body();
                try{
                    String temp = name.getText().toString() + ": "+ borsukiRoute.getDriverName();
                    name.setText(temp);
                    temp = start.getText().toString() + ": " + borsukiRoute.getStartingName();
                    start.setText(temp);
                    temp = destination.getText().toString() + ": " + borsukiRoute.getDestinationName();
                    destination.setText(temp);
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<BorsukiRoute> call, Throwable t) {

            }
        });
    }

    public void initialize() {
        refresh.setOnClickListener(getRefresh());
        accept.setOnClickListener(getAccept());

    }

    private View.OnClickListener getRefresh() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Passenger> call = apiInterface.getPassanger(driverId);
                Log.e("XD", "onResponse: " + call.request().toString() +" " + call.request().url()+ " id= "+ driverId);
                call.enqueue(new Callback<Passenger>() {
                    @Override
                    public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                        if(response.isSuccessful()){
                            passengerRequest = response.body();
                            accept.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Passenger> call, Throwable t) {
                        Log.e("Error: ", t.getMessage());
                    }
                });

            }

        };
    }

    private View.OnClickListener getAccept() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://maps.google.com/maps?saddr=" + passengerRequest.getDriverStart().latitude + "," + passengerRequest.getDriverStart().longitude  + "&daddr=" + passengerRequest.getStart().latitude + "," + passengerRequest.getStart().longitude + "+to:" + passengerRequest.getDestination().latitude +","+ passengerRequest.getDestination().longitude + "+to:" + passengerRequest.getDriverDestination().latitude + "," + passengerRequest.getDriverDestination().longitude);
                Log.e("DEBUG", "XD"+ uri.toString() );
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }

        };
    }


}
