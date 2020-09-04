package com.hamidraza.nturec;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailedViewFragment extends Fragment {

    TextView titleTV, compNameTV, jobDescTV, locationTV;
    String title, compName, jobDesc, location;

    public DetailedViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        // assigning the textviews
        titleTV = view.findViewById(R.id.display_title_tv);
        compNameTV = view.findViewById(R.id.display_companyName_tv);
        jobDescTV = view.findViewById(R.id.display_discription_tv);
        locationTV = view.findViewById(R.id.display_location_tv);

        // getting data from previous fragment
        Bundle bundle = this.getArguments();
        title = bundle.getString("title");
        compName = bundle.getString("compName");
        jobDesc = bundle.getString("jobDesc");
        location = bundle.getString("location");

        // setting the data received to the be displayed in the view
        titleTV.setText(title);
        compNameTV.setText(compName);
        jobDescTV.setText(jobDesc);
        locationTV.setText(location);

        return view;
    }
}