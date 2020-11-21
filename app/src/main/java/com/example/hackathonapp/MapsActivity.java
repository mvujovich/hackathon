package com.example.hackathonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


//        libraries.places.api.net.PlacesClient;


import java.util.ArrayList;
import java.util.List;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mainMap;
    private ImageView mainImage;

    double lat;
    double lon;
    private boolean gps_enable = false;
    private boolean network_enable = false;

    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    LatLng userLatLong;

    Geocoder geocoder;
    List<Address> myAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mainImage = findViewById(R.id.imageView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewFragment);
        mapFragment.getMapAsync(this);
        mainImage.setImageResource(R.drawable.trashcan1);


        // Prompt the user for permission.
        checkUserPermission();
        getMyLocation();
        // Get location
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
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
        System.out.println("lat: " + lat + "; lon: " + lon);
        mainMap.addMarker(new MarkerOptions().position(current).title("CURRENT"));
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

    // get location of the user
    public void getMyLocation(){
        if (checkUserPermission()){
            try{
                gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
            catch(Exception err){

            }
            try{
                network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
            catch(Exception err){

            }
            if (!gps_enable && !network_enable){
                // alert the user to allow location to be seen
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Location is not enabled.");
                builder.create().show();

            }
            if (gps_enable)
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
            }
            if (network_enable)
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
            }
        }
    }

    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location !=null){
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    private boolean checkUserPermission(){
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission = new ArrayList<>();

        if(location != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(location2 != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if(!listPermission.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]),
                    1);
        }

        return true;
    }


}