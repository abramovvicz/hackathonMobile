package com.harman.borsuki.carpooling.models;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class BorsukiRoute {
    List<LatLng> routeCoords;
    private String originName;
    private String destinationName;
    private String driverName;
    private String phoneNumber;
    private LocalDateTime timeDate;
    private Double distance;

    public BorsukiRoute(List<LatLng> routeCoords, String originName, String destinationName, String driverName, String phoneNumber, LocalDateTime timeDate, Double distance) {
        this.routeCoords = routeCoords;
        this.originName = originName;
        this.destinationName = destinationName;
        this.driverName = driverName;
        this.phoneNumber = phoneNumber;
        this.timeDate = timeDate;
        this.distance = distance;
    }
    public BorsukiRoute() {
    }

    public List<LatLng> getRouteCoords() {
        return routeCoords;
    }

    public void setRouteCoords(List<LatLng> routeCoords) {
        this.routeCoords = routeCoords;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(LocalDateTime timeDate) {
        this.timeDate = timeDate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public LatLng getOrigin() {
        return  routeCoords.get(0);
    }
    public LatLng getDestination() {
        return routeCoords.get(routeCoords.size()-1);
    }
    public String coordinationToString(LatLng coordination) {
        return coordination.latitude + "," + coordination.longitude;
    }

}
