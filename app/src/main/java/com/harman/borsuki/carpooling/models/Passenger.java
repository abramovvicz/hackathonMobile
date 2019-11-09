package com.harman.borsuki.carpooling.models;

import com.google.android.gms.maps.model.LatLng;

public class Passenger {
    private String id;
    private String driverId;
    private String driverPhoneNumber;
    private LatLng start;
    private LatLng destination;
    private LatLng driverStart;
    private LatLng driverDestination;
    private boolean isAccepted;

    public Passenger() {

    }

    public Passenger(String driverId, LatLng start, LatLng destination) {
        this.driverId = driverId;
        this.start = start;
        this.destination = destination;
        this.isAccepted = false;
        this.driverPhoneNumber = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public LatLng getDriverStart() {
        return driverStart;
    }

    public void setDriverStart(LatLng driverStart) {
        this.driverStart = driverStart;
    }

    public LatLng getDriverDestination() {
        return driverDestination;
    }

    public void setDriverDestination(LatLng driverDestination) {
        this.driverDestination = driverDestination;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id='" + id + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                ", start=" + start +
                ", destination=" + destination +
                ", driverStart=" + driverStart +
                ", driverDestination=" + driverDestination +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
