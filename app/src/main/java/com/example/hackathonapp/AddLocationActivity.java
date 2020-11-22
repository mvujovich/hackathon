package com.example.hackathonapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback
{
    Geocoder geocoder;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner typeSpinner;
    private CheckBox glassCheck;
    private CheckBox metalCheck;
    private CheckBox paperCheck;
    private CheckBox plasticCheck;
    private String streetName;
    boolean glass = false;
    boolean metal = false;
    boolean paper = false;
    boolean plastic = false;
    List<Address> addresses = null;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap addMap;

    double lat;
    double lon;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    private Location lastKnownLocation;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    public LocationManager locationManager;
    LatLng userLatLong;

    List<Address> myAddress;
    Context context = this;

    //CHANGE THESE TO USERS LOCATION
    LatLng hkg = new LatLng(22.3193, 114.1694);
    double thisLatitude = 0;
    double thisLongitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewAddFragment);
        mapFragment.getMapAsync(this);

        glassCheck = (CheckBox) findViewById(R.id.glassCheck);
        metalCheck = (CheckBox) findViewById(R.id.metalCheck);
        paperCheck = (CheckBox) findViewById(R.id.paperCheck);
        plasticCheck = (CheckBox) findViewById(R.id.plasticCheck);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
        R.array.types_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        addMap = googleMap;
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        addMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDragStart(Marker marker)
            {

            }

            @Override
            public void onMarkerDrag(Marker marker)
            {

            }

            @Override
            public void onMarkerDragEnd(Marker marker)
            {
                thisLatitude = marker.getPosition().latitude;
                thisLongitude = marker.getPosition().longitude;
                //LatLng theseCords = new LatLng(thisLatitude, thisLongitude);
                geocoder = new Geocoder(AddLocationActivity.this, Locale.getDefault());
                try
                {
                    System.out.println("I am trying");
                    addresses = geocoder.getFromLocation(thisLatitude, thisLongitude,1);
                    streetName = addresses.get(0).getAddressLine(0);
                }
                catch (IOException e)
                {
                    System.out.println("Exception time");
                    e.printStackTrace();
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
                                LatLng currLL = new LatLng(lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude());
                                addMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        currLL, DEFAULT_ZOOM));
                                addMap.addMarker(new MarkerOptions()
                                        .position(currLL).title("New bin").draggable(true));
                            }
                        } else {
                            Log.d("Maps Activity", "Current location is null. Using defaults.");
                            Log.e("Maps Activity", "Exception: %s", task.getException());
                            addMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            addMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateLocationUI() {
        if (addMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                addMap.setMyLocationEnabled(true);
                addMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                addMap.setMyLocationEnabled(false);
                addMap.getUiSettings().setMyLocationButtonEnabled(false);
                userLatLong = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void zoomIn (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOut (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomOut());
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

    public void saveLocation(View v)
    {
        String selectedBinType = typeSpinner.getSelectedItem().toString().toLowerCase();

        if (selectedBinType.equals("recycling bin"))
        {
            if (glassCheck.isChecked())
            {
                glass = true;
            }
            if (metalCheck.isChecked())
            {
                metal = true;
            }
            if (paperCheck.isChecked())
            {
                paper = true;
            }
            if (plasticCheck.isChecked())
            {
                plastic = true;
            }
                RecyclerBin thisBin = new RecyclerBin(thisLongitude, thisLatitude,
                        streetName, "Recycling bin", glass, metal, paper, plastic);
                db.collection("bins").add(thisBin)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                        {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                        {
                            Log.d("ADD_BIN", "DocumentSnapshot added with ID: "
                                    + documentReference.getId());
                        }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("ADD_BIN", "Error adding document", e);
                            }
                        });
        }
        else if (selectedBinType.equals("trash bin"))
        {
            TrashBin thisBin = new TrashBin(thisLongitude, thisLatitude, streetName, "Trash bin");
            db.collection("bins").add(thisBin)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                    {
                        @Override
                        public void onSuccess(DocumentReference documentReference)
                        {
                            Log.d("ADD_BIN", "DocumentSnapshot added with ID: "
                                    + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ADD_BIN", "Error adding document", e);
                        }
                    });
        }
        else if (selectedBinType.equals("clothes recycling bin"))
        {
            ClothesBin thisBin = new ClothesBin(thisLongitude, thisLatitude,
                    streetName, "Clothes recycling bin");
            db.collection("bins").add(thisBin)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                    {
                        @Override
                        public void onSuccess(DocumentReference documentReference)
                        {
                            Log.d("ADD_BIN", "DocumentSnapshot added with ID: "
                                    + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ADD_BIN", "Error adding document", e);
                        }
                    });
        }
        else
        {
            Toast toast = Toast.makeText(this,"Please select a valid bin type",
                    Toast.LENGTH_LONG);
            toast.show();

        }
    }

}
