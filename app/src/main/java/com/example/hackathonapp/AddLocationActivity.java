package com.example.hackathonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner typeSpinner;
    private CheckBox glassCheck;
    private CheckBox metalCheck;
    private CheckBox paperCheck;
    private CheckBox plasticCheck;
    boolean glass = false;
    boolean metal = false;
    boolean paper = false;
    boolean plastic = false;

    //CHANGE THESE TO USERS LOCATION
    LatLng hkg = new LatLng(22.3193, 114.1694);
    double thisLatitude = 0;
    double thisLongitude = 0;
    MarkerOptions marker = new MarkerOptions().position(hkg).title("Hong Kong").draggable(true);

    private GoogleMap addMap;
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

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        addMap = googleMap;
        addMap.addMarker(marker);
        addMap.moveCamera(CameraUpdateFactory.newLatLng(hkg));
        addMap.moveCamera(CameraUpdateFactory.zoomTo(15));

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
            }
        });
    }

    public void zoomIn (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOut (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void saveLocation(View v)
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
        String selectedBinType = typeSpinner.getSelectedItem().toString().toLowerCase();

        if (selectedBinType.equals("recycling bin"))
        {
                RecyclerBin thisBin = new RecyclerBin(thisLongitude, thisLatitude,
                        "Random place", glass, metal, paper, plastic);
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
            TrashBin thisBin = new TrashBin(thisLongitude, thisLatitude, "Random place 2");
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
                    "Random place");
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
            //Toast: please select a valid bin type
        }
    }

}
