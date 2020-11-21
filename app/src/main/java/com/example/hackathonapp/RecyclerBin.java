package com.example.hackathonapp;

import java.util.ArrayList;

public class RecyclerBin extends Bin {
    boolean glass;
    boolean metal;
    boolean paper;
    boolean plastic;

    public RecyclerBin(double longitude, double latitude, String loc,
                       boolean g, boolean met, boolean pap, boolean pla) {
        super(longitude, latitude, loc);
        glass = g;
        metal = met;
        paper = pap;
        plastic = pla;
    }

    public boolean isGlass()
    {
        return glass;
    }

    public void setGlass(boolean glass)
    {
        this.glass = glass;
    }

    public boolean isMetal()
    {
        return metal;
    }

    public void setMetal(boolean metal)
    {
        this.metal = metal;
    }

    public boolean isPaper()
    {
        return paper;
    }

    public void setPaper(boolean paper)
    {
        this.paper = paper;
    }

    public boolean isPlastic()
    {
        return plastic;
    }

    public void setPlastic(boolean plastic)
    {
        this.plastic = plastic;
    }
}
