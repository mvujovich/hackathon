package com.example.hackathonapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathonapp.LocationViewHolder;
import com.example.hackathonapp.MapsActivity;
import com.example.hackathonapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder>
{
    ArrayList<Bin> myData;
    private Context myContext;

    public LocationRecyclerViewAdapter(ArrayList<Bin> data, Context context)
    {
        myData = data;
        this.myContext = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_row_view,
                parent, false);

        LocationViewHolder holder = new LocationViewHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position)
    {
        final int positionPass = position;
        final String locationName = myData.get(position).getLocationName();
        final String binType = myData.get(position).getBinType();

        String typeName = "";
        if (binType.equals("Trash bin"))
        {
            typeName = "Trash bin";
        }
        else if (binType.equals("Recycling bin"))
        {
            typeName = "Recycling bin";
        }
        else if (binType.equals("Clothes recycling bin"))
        {
            typeName = "Clothes recycling bin";
        }
        holder.nameText.setText(locationName);
        holder.typeText.setText(typeName);
        holder.kmsText.setText("10");

        holder.getLayout().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onClickChangeActivity(positionPass);
            }
        });
    }


    /**
     * This method allows for the data of the listing pressed by the user to be transferred so that
     * the view will change to ListingsInfoActivity and display the correct information.
     * @param position This parameter represents which item of the ArrayList of listings is to be
     *                 accessed.
     */
    public void onClickChangeActivity(int position)
    {
        Intent intent = new Intent(myContext, MapsActivity.class);
        final double binLatitude = myData.get(position).getLlat();
        final double binLongitude = myData.get(position).getLlong();
        final String binLocation = myData.get(position).getLocationName();
        final String binType = myData.get(position).getBinType();
        intent.putExtra("lat", binLatitude);
        intent.putExtra("lon", binLongitude);
        intent.putExtra("name", binLocation);
        if (binType.equals("Recycling bin"))
        {
            intent.putExtra("type", "Recycling bin");
        }
        else if (binType.equals("Clothes recycling bin"))
        {
            intent.putExtra("type", "Clothes recycling bin");
        }

        else
        {
            intent.putExtra("type", "Trash bin");
        }
        myContext.startActivity(intent);
    }

    @Override
    public int getItemCount()
    {
        return myData.size();
    }

}

/*
from https://stackoverflow.com/questions/28515049/android-content-context-getpackagename-on-a-null-object-reference
Intent mIntent = new Intent(getActivity(),MusicHome.class);
        mIntent.putExtra("SigninFragment.user_details", bundle);
        startActivity(mIntent);

        VS

        @Override
public void onAttach(Activity activity){
    this.activity = activity;
    Intent mIntent = new Intent(activity, MusicHome.class);

 */