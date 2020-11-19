package com.example.hackathonapp;

public class Bin {
    int llong;
    int llat;

    public Bin(int longitude, int latitude){
        this.llong = longitude;
        this.llat = latitude;
    }

    public int getLlat() {
        return llat;
    }

    public int getLlong() {
        return llong;
    }

    public void setLlat(int llat) {
        this.llat = llat;
    }

    public void setLlong(int llong) {
        this.llong = llong;
    }
}
