package com.example.hackathonapp;

public class Bin {
    double llong;
    double llat;
    String locationName;
    String binType;

    public Bin()
    {
        this.llong = 0;
        this.llat = 0;
        this.locationName = "No name provided";
        this.binType = "Trash bin";
    }

    public Bin(double longitude, double latitude, String loc, String type){
        this.llong = longitude;
        this.llat = latitude;
        this.locationName = loc;
        this.binType = type;
    }

    public double getLlat() {
        return llat;
    }

    public double getLlong() {
        return llong;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLlat(double llat) {
        this.llat = llat;
    }

    public void setLlong(double llong) {
        this.llong = llong;
    }

    public void setLocationName(String loc)
    {
        this.locationName = loc;
    }

    public String getBinType()
    {
        return binType;
    }

    public void setBinType(String binType)
    {
        this.binType = binType;
    }
}
