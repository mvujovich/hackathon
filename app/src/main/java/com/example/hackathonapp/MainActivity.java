package com.example.hackathonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recView;
    LocationRecyclerViewAdapter myAdapter;
    Context context = this;

    ArrayList<Bin> allLocations = new ArrayList<Bin>();

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getListItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView = findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new LocationRecyclerViewAdapter(allLocations, context);
        recView.setAdapter(myAdapter);
        getLocationPermission();
        getDeviceLocation();

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

    private void getListItems()
    {
        db.collection("bins").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
                {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        if (!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> docsList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : docsList)
                            {
                                Bin b = d.toObject(Bin.class);
                                allLocations.add(b);
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
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

    public Location getDeviceLocation() {
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
                                Log.d("Main Activity", "Current location is not null.");
                            }
                        } else {
                            Log.d("Main Activity", "Current location is null. Using defaults.");
                            Log.e("Main Activity", "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
        return lastKnownLocation;
    }

}