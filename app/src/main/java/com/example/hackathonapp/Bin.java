package com.example.hackathonapp;

public class Bin {
    double llong;
    double llat;
    String locationName;

    public Bin(double longitude, double latitude, String loc){
        this.llong = longitude;
        this.llat = latitude;
        this.locationName = loc;
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

    public void setLlat(int llat) {
        this.llat = llat;
    }

    public void setLlong(int llong) {
        this.llong = llong;
    }

    public void setLocationName(String loc)
    {
        this.locationName = loc;
    }
}
