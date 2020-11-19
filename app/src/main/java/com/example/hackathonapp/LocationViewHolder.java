package com.example.hackathonapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationViewHolder extends RecyclerView.ViewHolder
{
    protected TextView nameText;
    protected TextView typeText;
    protected TextView kmsText;
    protected ConstraintLayout layout;

    public LocationViewHolder(@NonNull View itemView)
    {
        super(itemView);

        nameText = itemView.findViewById(R.id.locationName);
        typeText = itemView.findViewById(R.id.locationTypeBottom);
        kmsText = itemView.findViewById(R.id.kmAway);

        this.layout = itemView.findViewById(R.id.parent_layout);
    }

    public ConstraintLayout getLayout()
    {
        return layout;
    }
    
}
