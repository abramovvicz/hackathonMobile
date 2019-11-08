package com.harman.borsuki.carpooling.services;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.harman.borsuki.carpooling.models.BorsukiRoute;

import java.util.ArrayList;
import java.util.List;

public class RouteService {

    public static List<LatLng> getRoute(BorsukiRoute borsukiRoute) {
        LatLng origin = borsukiRoute.getRoute().get(0);
        LatLng dest = borsukiRoute.getRoute().get(borsukiRoute.getRoute().size()-1);

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
            Log.e("Exception", ex.getLocalizedMessage());
        }

        return path;

    }

}
