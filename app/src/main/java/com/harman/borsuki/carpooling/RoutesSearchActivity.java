package com.harman.borsuki.carpooling;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.harman.borsuki.carpooling.adapter.BorsukiRouteAdapter;
import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.services.ApiClient;
import com.harman.borsuki.carpooling.services.ApiInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RoutesSearchActivity extends AppCompatActivity {

    private final String TAG = "RoutesSearchActivity";
    private ListView listView;
    List<BorsukiRoute> list;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_search);

        try {
            initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initialize() throws IOException {
        listView = findViewById(R.id.lv_routes);

        List<BorsukiRoute> arrayList;


        listView.setOnItemClickListener((parent, view, position, id) -> {
            BorsukiRoute clickedItem = (BorsukiRoute) listView.getItemAtPosition(position);
            Intent intent = new Intent(this, RouteDetailActivity.class);
            Gson gson = new Gson();
            String temp = gson.toJson(clickedItem);
            intent.putExtra("temp", temp);
            startActivity(intent);
        });

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<BorsukiRoute>> call = apiInterface.getAllRoutes();
        call.enqueue(new Callback<List<BorsukiRoute>>() {
            @Override
            public void onResponse(Call<List<BorsukiRoute>> call, Response<List<BorsukiRoute>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listView.setAdapter(new BorsukiRouteAdapter(context, list));
                }
            }

            @Override
            public void onFailure(Call<List<BorsukiRoute>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

    }
}
