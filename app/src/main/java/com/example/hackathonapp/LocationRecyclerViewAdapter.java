package com.example.hackathonapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathonapp.LocationViewHolder;
import com.example.hackathonapp.MapsActivity;
import com.example.hackathonapp.R;

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

        //holder.basePrice.setText(myData.get(position).getBasePriceString());

        final int positionPass = position;
        final String locationName = myData.get(position).getLocationName();
        String typeName = "";
        if (myData.get(position) instanceof TrashBin)
        {
            typeName = "Trash bin";
        }
        else if (myData.get(position) instanceof RecyclerBin)
        {
            typeName = "Recycling bin";
        }
        else if (myData.get(position) instanceof ClothesBin)
        {
            typeName = "Clothes recycling bin";
        }
        holder.nameText.setText(locationName);
        holder.typeText.setText(typeName);
        holder.kmsText.setText("20");

        //final String basePriceMessage = myData.get(position).getBasePriceString();

        holder.getLayout().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onClickChangeActivity(positionPass);
                Intent intent = new Intent(myContext, MapsActivity.class);
                //intent.putExtra("model", modelMessage);
                myContext.startActivity(intent);
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
        //final String modelMessage = myData.get(position).getModel();
        //final String ownerMessage = myData.get(position).getOwner();
        //final String basePriceMessage = myData.get(position).getBasePriceString();
        Intent intent = new Intent(myContext, MapsActivity.class);
        //intent.putExtra("model", modelMessage);
        //intent.putExtra("owner", ownerMessage);
        //intent.putExtra("base price", basePriceMessage);
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