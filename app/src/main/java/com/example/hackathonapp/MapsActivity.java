package com.example.hackathonapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mainMap;
    private ImageView mainImage;
    private TextView titleView;
    private TextView cordsView;
    private TextView typeView;
    private TextView recyclingTypes;

    double lat;
    double lon;
    double markerLat;
    double markerLon;
    String locationTitle;
    String locationType;

    private boolean gps_enable = false;
    private boolean network_enable = false;
    private Location lastKnownLocation;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    public LocationManager locationManager;
    LatLng userLatLong;
    LatLng binLatLong;

    Geocoder geocoder;
    List<Address> myAddress;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();

        mainImage = findViewById(R.id.imageView);
        titleView = findViewById(R.id.locationTitleView);
        cordsView = findViewById(R.id.cordsView);
        typeView = findViewById(R.id.locationType);
        recyclingTypes = findViewById(R.id.recyclingTypes);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewFragment);
        mapFragment.getMapAsync(this);
        mainImage.setImageResource(R.drawable.trashcan1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();
        markerLat = intent.getDoubleExtra("lat", 0);
        markerLon = intent.getDoubleExtra("lon", 0);
        binLatLong = new LatLng (markerLat, markerLon);
        String cordsText = markerLat + ", " + markerLon;
        locationTitle = intent.getStringExtra("name");
        locationType = intent.getStringExtra("type");

        titleView.setText(locationTitle);
        cordsView.setText(cordsText);
        typeView.setText(locationType);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mainMap = googleMap;

        //current location
        LatLng current = new LatLng(lat, lon);

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        mainMap.addMarker(new MarkerOptions().position(binLatLong).title("Bin"));
        mainMap.moveCamera(CameraUpdateFactory.newLatLng(current));

    }

    public void zoomIn (View v)
    {
        mainMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOut (View v)
    {
        mainMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    private void getLocationPermission()
    {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            locationPermissionGranted = true;
        }
        else
            {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mainMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mainMap.setMyLocationEnabled(true);
                mainMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mainMap.setMyLocationEnabled(false);
                mainMap.getUiSettings().setMyLocationButtonEnabled(false);
                userLatLong = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                Log.d("Maps Activity", "Current location is not null.");
                                mainMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d("Maps Activity", "Current location is null. Using defaults.");
                            Log.e("Maps Activity", "Exception: %s", task.getException());
                            mainMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mainMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

}