package com.example.hackathonapp;

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

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private Spinner typeSpinner;
    private CheckBox glassCheck;
    private CheckBox metalCheck;
    private CheckBox paperCheck;
    private CheckBox plasticCheck;

    //CHANGE THESE TO USERS LOCATION
    LatLng hkg = new LatLng(22.3193, 114.1694);
    LatLng thisLocation = hkg;
    MarkerOptions marker = new MarkerOptions().position(hkg).title("Hong Kong").draggable(true);
    ArrayList<CheckBox> checkedBoxes = new ArrayList<CheckBox>();
    ArrayList<String> recycleTypes = new ArrayList<String>();


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
        checkedBoxes.add(glassCheck);
        metalCheck = (CheckBox) findViewById(R.id.metalCheck);
        checkedBoxes.add(metalCheck);
        paperCheck = (CheckBox) findViewById(R.id.paperCheck);
        checkedBoxes.add(paperCheck);
        plasticCheck = (CheckBox) findViewById(R.id.plasticCheck);
        checkedBoxes.add(plasticCheck);

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
                thisLocation = marker.getPosition();
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
        System.out.println(thisLocation);
        String selectedBinType = typeSpinner.getSelectedItem().toString().toLowerCase();
        System.out.println(selectedBinType);
        for (CheckBox c : checkedBoxes)
        {
            if (c.isChecked() && selectedBinType.equals("recycling bin"))
            {
                String rt = c.getText().toString().toLowerCase();
                recycleTypes.add(rt);
                System.out.println(rt);
            }
        }
    }

}
