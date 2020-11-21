package com.example.hackathonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback
{

    String type = ConstantsEH.NoType;
    private Spinner spinner1;
    private Button btnSubmit;

    private GoogleMap addMap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapViewAddFragment);
        mapFragment.getMapAsync(this);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        // Construct a GeoDataClient.
        //mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        //mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        //mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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

    public void zoomIn (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOut (View v)
    {
        addMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void addItemsOnSpinner() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Toast.makeText(MyAndroidAppActivity.this,
//                        "OnClickListener : " +
//                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) ,
//                        Toast.LENGTH_SHORT).show();
            }

        });
    }

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//        if (checked) {
//            // Check which radio button was clicked
//            switch (view.getId()) {
//                case R.id.recyclingBin:
//                    if (checked)
//                        type = ConstantsEH.RecycleType;
//                        Log.d("radioButton", ConstantsEH.RecycleType);
//
//                        break;
//                case R.id.trashBin:
//                    if (checked)
//                        type = ConstantsEH.TrashType;
//                        Log.d("radioButton", ConstantsEH.TrashType);
//                        break;
//                case R.id.clothesBin:
//                    if (checked)
//                        type = ConstantsEH.ClothesType;
//                        Log.d("radioButton", ConstantsEH.ClothesType);
//                        break;
//            }
//        }
//    }


}
