package com.harman.borsuki.carpooling.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BorsukiRoute implements Parcelable {


    private List<LatLng> route;
    private String id;
    private String startingName;
    private String destinationName;
    private String driverName;
    private String phoneNumber;
    private String dateTime;
    private Double distance;

    public BorsukiRoute(List<LatLng> route, String driverId,String startingName, String destinationName, String driverName, String phoneNumber, String dateTime, Double distance) {
        this.route = route;
        this.id = driverId;
        this.startingName = startingName;
        this.destinationName = destinationName;
        this.driverName = driverName;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.distance = distance;
    }
    public BorsukiRoute() {
    }

    protected BorsukiRoute(Parcel in) {
        route = in.createTypedArrayList(LatLng.CREATOR);
        startingName = in.readString();
        destinationName = in.readString();
        driverName = in.readString();
        phoneNumber = in.readString();
        dateTime = in.readString();
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<BorsukiRoute> CREATOR = new Creator<BorsukiRoute>() {
        @Override
        public BorsukiRoute createFromParcel(Parcel in) {
            return new BorsukiRoute(in);
        }

        @Override
        public BorsukiRoute[] newArray(int size) {
            return new BorsukiRoute[size];
        }
    };

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
        dest.writeTypedList(route);
        dest.writeString(startingName);
        dest.writeString(destinationName);
        dest.writeString(driverName);
        dest.writeString(phoneNumber);
        dest.writeString(dateTime);
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
    }
}
