package com.hamidraza.nturec;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AttendanceFragment extends Fragment {

    Button scanBtn;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        scanBtn = view.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(new View.OnClickListener() { //open scanner activity when button pressed
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Scanner.class);
//                Log.i("ScanBtn", "Scan button pressed");
                startActivity(intent);
                (getActivity()).overridePendingTransition(0,0);
            }
        });

        return view;
    }
}