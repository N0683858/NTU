package com.hamidraza.nturec;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tiper.MaterialSpinner;


public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerOptions<JobsModel> jobOptions;
    private FirebaseRecyclerAdapter<JobsModel, myViewHolder> adapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;
    private EditText inputSearchTxt;
    private MaterialSpinner mMaterialSpinner;
    private String[] dropdownOptions;
    private FloatingActionButton mFloatingActionButton;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Fire-base
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabse.getReference().child("Jobs Opp");

        // Recycler View
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Dropdown list spinner
        dropdownOptions = getResources().getStringArray(R.array.dropdown_options);
        mMaterialSpinner = view.findViewById(R.id.material_spinner2);
        mMaterialSpinner.setHintAnimationEnabled(false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, dropdownOptions);
        mMaterialSpinner.setAdapter(arrayAdapter);
        mMaterialSpinner.setSelection(0);

        //Loads all the data from the database and orders it by the title (Works)
        LoadData("","job_title");

        mMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                // filter the results depending on which course is selected in the spinner
                if(materialSpinner.getSelectedItem().toString().equals("All Courses"))
                {
                    // The toast message works
                    Toast.makeText(getContext(), "all courses selected", Toast.LENGTH_SHORT).show();

                    //This method doesn't run even though I've used the same method elsewhere
                    LoadData("","job_title"); //Load data
                }

                // This works all fine
                //Load data and only displays data which matches the item selected in spinner
                LoadData(materialSpinner.getSelectedItem().toString(),"department");
            }

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }
        });


        // Search input text
        inputSearchTxt = view.findViewById(R.id.searchView);


        inputSearchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!=null)
                {
                    LoadData(editable.toString(), "job_title");
                }
                else
                {
                    LoadData("", "job_title");
                }
            }
        });

        mFloatingActionButton = view.findViewById(R.id.floating_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = user.getDisplayName();
        Log.i("floatBtn", "user logged in is: " + currentUser);
         if(currentUser.equals("admin"))
         {
             mFloatingActionButton.setVisibility(View.VISIBLE);
         }

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button Clicked!", Toast.LENGTH_SHORT).show();
                AddNewEntryFragment addNewEntryFragment = new AddNewEntryFragment();
                getFragmentManager().beginTransaction().replace(R.id.navHostFragment, addNewEntryFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void LoadData(String viewData, String sortBy)
    {
        Query query = mDatabaseReference.orderByChild(sortBy).startAt(viewData).endAt(viewData+"\uf8ff");

        jobOptions = new FirebaseRecyclerOptions.Builder<JobsModel>().setQuery(query,JobsModel.class).build();
        adapter = new FirebaseRecyclerAdapter<JobsModel, myViewHolder>(jobOptions) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull JobsModel model) {
                //First get the data which will be passed through an intent to be displayed in the detailed view when that item is clicked
                final String title = model.getJob_title();
                final String compName = model.getCompany_name();
                final String jobDesc = model.getJob_desc();
                final String location = model.getLocation();

                holder.view.setOnClickListener(new View.OnClickListener() { // What happens when someone clicks the cardview in the recyclerview
                    @Override
                    public void onClick(View view) { //sending the data to the detailedview fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("title",title);
                        bundle.putString("compName",compName);
                        bundle.putString("jobDesc",jobDesc);
                        bundle.putString("location",location);
                        DetailedViewFragment detailedViewFragment = new DetailedViewFragment();
                        detailedViewFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.navHostFragment, detailedViewFragment).addToBackStack(null).commit();

                    }
                });

                //getting the data and setting the values to be displayed in the cardview
                holder.textViewTitle.setText("" + model.getJob_title());
                holder.textViewCompName.setText(""+model.getCompany_name());
                //holder.textViewJobDesc.setText("" + model.getJob_desc());
                holder.textViewLocation.setText("" + model.getLocation());

            }

            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_jobs_view,parent,false);
                return new myViewHolder(v);
            }
        };

        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
    }

//    public void LogOut(View view)
//    {
//        mAuth.signOut();
//        Intent intent = new Intent(getActivity(),SignIn.class);
//        startActivity(intent);
//    }

}