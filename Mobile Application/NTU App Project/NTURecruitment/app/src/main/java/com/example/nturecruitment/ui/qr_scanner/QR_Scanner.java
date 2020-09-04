package com.example.nturecruitment.ui.qr_scanner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nturecruitment.R;
import com.example.nturecruitment.Scanner;

public class QR_Scanner extends Fragment {

    Button scanBtn;


    public QR_Scanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr__scanner, container, false);

        scanBtn = (Button) view.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Scanner.class);
                startActivity(i);
                (getActivity()).overridePendingTransition(0, 0);
            }
        });

        return view;

    }


}
