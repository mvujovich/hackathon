package com.example.hackathonapp;

public class RecyclerBin extends Bin {

    boolean Plastic;
    boolean Metal;
    boolean Paper;
    boolean Glass;

    public RecyclerBin(int longitude, int latitude, boolean plastic, boolean metal, boolean paper,
                       boolean glass) {
        super(longitude, latitude);
        this.Plastic = plastic;
        this.Metal = metal;
        this.Paper = paper;
        this.Glass = glass;
    }

    public boolean isPlastic() {
        return Plastic;
    }

    public void setPlastic(boolean plastic) {
        Plastic = plastic;
    }

    public boolean isMetal() {
        return Metal;
    }

    public void setMetal(boolean metal) {
        Metal = metal;
    }

    public boolean isPaper() {
        return Paper;
    }

    public void setPaper(boolean paper) {
        Paper = paper;
    }

    public boolean isGlass() {
        return Glass;
    }

    public void setGlass(boolean glass) {
        Glass = glass;
    }
}
