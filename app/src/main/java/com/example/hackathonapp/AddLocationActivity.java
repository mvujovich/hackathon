package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private GoogleMap addMap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewAddFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        addMap = googleMap;

        // Add a marker and move the camera
        LatLng hkg = new LatLng(22.3193, 114.1694);
        addMap.addMarker(new MarkerOptions().position(hkg).title("Hong Kong").draggable(true));
        addMap.moveCamera(CameraUpdateFactory.newLatLng(hkg));
    }

    //
}