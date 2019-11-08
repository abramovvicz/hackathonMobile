package com.harman.borsuki.carpooling;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.harman.borsuki.carpooling.adapter.BorsukiRouteAdapter;
import com.harman.borsuki.carpooling.models.BorsukiRoute;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


public class RoutesSearchActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_search);

        initialize();
    }
    LatLng temp = new LatLng(12.12,13.13);
    LatLng temp2 = new LatLng(14.12,14.13);
    String str = "2016-03-04 11:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    BorsukiRoute borsukiRoute = new BorsukiRoute(Arrays.asList(temp, temp2), "Lodz", "Gdansk", "Mikolaj", "570-572-282", dateTime, 123.123);
    BorsukiRoute borsukiRoute2 = new BorsukiRoute(Arrays.asList(temp, temp2), "Lodz", "Warszawa", "Mikolaj", "570-572-282", dateTime, 123.123);
    BorsukiRoute borsukiRoute3 = new BorsukiRoute(Arrays.asList(temp, temp2), "Lodz", "Krakow", "Mikolaj", "570-572-282", dateTime, 123.123);
    private void initialize() {
        listView = findViewById(R.id.lv_routes);
        ArrayList<BorsukiRoute> arrayList = new ArrayList<>();
        arrayList.add(borsukiRoute);
        arrayList.add(borsukiRoute2);
        arrayList.add(borsukiRoute3);
        BorsukiRouteAdapter borsukiRouteAdapter = new BorsukiRouteAdapter(this, arrayList);
        listView.setAdapter(borsukiRouteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BorsukiRoute clickedItem = (BorsukiRoute) listView.getItemAtPosition(position);
                Toast.makeText(RoutesSearchActivity.this,clickedItem.getDestinationName(),Toast.LENGTH_LONG).show();
            }

        });
    }
}
