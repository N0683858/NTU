package com.hamidraza.nturec;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tiper.MaterialSpinner;


public class AddNewEntryFragment extends Fragment {

    private MaterialSpinner mMaterialSpinner;
    private String[] dropdownOptions;

    public AddNewEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_entry, container, false);

        dropdownOptions = getResources().getStringArray(R.array.dropdown_options);
        mMaterialSpinner = view.findViewById(R.id.material_spinner2);
        mMaterialSpinner.setHintAnimationEnabled(false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, dropdownOptions);
        mMaterialSpinner.setAdapter(arrayAdapter);
        mMaterialSpinner.setSelection(0);

        return view;
    }
}