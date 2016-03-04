package com.axiata.dialog.entity;


public class CurrentLocation {

    public CurrentLocation(String accuracy, String altitude, String latitude, String longitude,String name) {
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;     
        this.name = name;
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
    
     private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
     private String switchOn;

    public String getSwitchOn() {
        return switchOn;
    }

    public void setSwitchOn(String switchOn) {
        this.switchOn = switchOn;
    }  
    
    
    private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }    
    
    private String cellID;

    public String getCellID() {
        return cellID;
    }

    public void setCellID(String cellID) {
        this.cellID = cellID;
    }
}