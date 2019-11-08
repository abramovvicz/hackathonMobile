package com.harman.borsuki.carpooling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import com.harman.borsuki.carpooling.models.BorsukiRoute;
import com.harman.borsuki.carpooling.services.RouteService;

import java.util.List;

public class RouteDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BorsukiRoute borsukiRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_detail);
        mapFragment.getMapAsync(this);

        initialize();
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
        }
    }
}
//        if (path.size() > 0) {
//            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
//            mMap.addPolyline(opts);
//        }
//
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zaragoza, 6));
