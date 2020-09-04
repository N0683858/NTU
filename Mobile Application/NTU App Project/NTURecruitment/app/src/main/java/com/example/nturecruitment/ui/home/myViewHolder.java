package com.example.nturecruitment.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nturecruitment.R;

public class myViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewTitle,textViewCompName,textViewJobDesc,textViewLocation;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewCompName = itemView.findViewById(R.id.textViewCompName);
        textViewJobDesc = itemView.findViewById(R.id.textViewJobDesc);
        textViewLocation = itemView.findViewById(R.id.textViewLocation);
    }
}
