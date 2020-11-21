package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recView;
    LocationRecyclerViewAdapter myAdapter;
    Context context = this;
    ArrayList<Bin> allLocations = new ArrayList<Bin>();

    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ClothesBin bin1 = new ClothesBin(33, 33, "Sai Wan Ho MTR station");
        RecyclerBin bin2 = new RecyclerBin(33, 33, "CIS atrium", true,
                true, false, true);
        allLocations.add(bin1);
        allLocations.add(bin2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView = findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new LocationRecyclerViewAdapter(allLocations, context);
        recView.setAdapter(myAdapter);
    }

    public void goMap(View v)
    {
        Intent nextScreen = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(nextScreen);
    }

    public void goAdd(View v)
    {
        Intent nextScreen = new Intent(getBaseContext(), AddLocationActivity.class);
        startActivity(nextScreen);
    }

}