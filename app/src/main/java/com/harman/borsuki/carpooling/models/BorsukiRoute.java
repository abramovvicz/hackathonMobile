package com.harman.borsuki.carpooling.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class BorsukiRoute implements Parcelable {

    List<LatLng> route;
    private String startingName;
    private String destinationName;
    private String driverName;
    private String phoneNumber;
    private String dateTime;
    private Double distance;

    public BorsukiRoute(List<LatLng> route, String startingName, String destinationName, String driverName, String phoneNumber, String dateTime, Double distance) {
        this.route = route;
        this.startingName = startingName;
        this.destinationName = destinationName;
        this.driverName = driverName;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.distance = distance;
    }
    public BorsukiRoute() {
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
    public LatLng getOrigin() {
        return  route.get(0);
    }
    public LatLng getDestination() {
        return route.get(route.size()-1);
    }
    public String coordinationToString(LatLng coordination) {
        return coordination.latitude + "," + coordination.longitude;

    }

    public List<LatLng> getRoute() {
        return route;
    }

    public void setRoute(List<LatLng> route) {
        this.route = route;
    }

    public String getStartingName() {
        return startingName;
    }

    @Override
    public String toString() {
        return "BorsukiRoute{" +
                "route=" + route +
                ", startingName='" + startingName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", driverName='" + driverName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateTime=" + dateTime +
                ", distance=" + distance +
                '}';
    }

    public void setStartingName(String startingName) {
        this.startingName = startingName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
