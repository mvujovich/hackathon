package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
{

    private GoogleMap mainMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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