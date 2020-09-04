package com.hamidraza.nturec;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle,textViewCompName,textViewJobDesc,textViewLocation;
    View view;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.job_title_txtView);
        textViewCompName = itemView.findViewById(R.id.company_name_txtView);
        //textViewJobDesc = itemView.findViewById(R.id.);
        textViewLocation = itemView.findViewById(R.id.location_txtView);
        view = itemView;
    }
}
