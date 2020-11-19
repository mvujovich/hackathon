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
    ArrayList<String> myData;
    private Context myContext;

    public LocationRecyclerViewAdapter(ArrayList<String> data, Context context)
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

    /*
    https://stackoverflow.com/questions/41875008/non-static-method-putextra-and-cannot-find
    -symbol-method
    This question saved me so I thought it only fair to cite!
    https://stackoverflow.com/questions/31059390/android-null-pointer-exception-when-calling-new-
    intent
    I had to adapt this a lot and make a new method but after hours of pain it worked!
    Thank you Morales Batowski! You are my hero :)
    */

    /**
     * This method is used to allow the CISListingsAdapter to change the values of each item in
     * the RecyclerView and helps in setting up the functionality of clicking the listing to
     * open the ListingsInfoActivity view of the listing.
     * @param holder The holder parameter represents the use of the CISListingViewHolder.
     * @param position The position parameter is an integer representing which element of the
     *                 ArrayList of data is to be accessed.
     */
    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position)
    {
        //holder.modelText.setText(myData.get(position).getModel());
        //holder.ownerText.setText(myData.get(position).getOwner());
        //holder.basePrice.setText(myData.get(position).getBasePriceString());

        final int positionPass = position;
        //final String modelMessage = myData.get(position).getModel();
        //final String ownerMessage = myData.get(position).getOwner();
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