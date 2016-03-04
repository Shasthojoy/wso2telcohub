package com.axiata.dialog.entity;


public class CurrentLocation {

    public CurrentLocation(String accuracy, String altitude, String latitude, String longitude) {
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;        
    }
 
    public CurrentLocation() {}    
    
    private String accuracy;

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
    private String altitude;

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
    private String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    private String longitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}