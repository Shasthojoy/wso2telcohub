package com.axiata.dialog.entity;


public class TerminalLocation {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    private CurrentLocation currentLocation;

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }
    private String locationRetrievalStatus;

    public String getLocationRetrievalStatus() {
        return locationRetrievalStatus;
    }

    public void setLocationRetrievalStatus(String locationRetrievalStatus) {
        this.locationRetrievalStatus = locationRetrievalStatus;
    }
}