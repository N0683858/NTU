package com.hamidraza.nturec;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class BookmarksFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerOptions<JobsModel> jobOptions;
    private FirebaseRecyclerAdapter<JobsModel, myViewHolder> adapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabse;
    private DatabaseReference mDatabaseReference;

    Boolean isTrue = true;


    public BookmarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabse.getReference().child(getDisplayName());

        //------------- Recycler View -------------//
        mRecyclerView = view.findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Loads all the data from the database and orders it by the title (Works)
        LoadData("","job_title");

        return view;
    }

    private void LoadData(String viewData, String sortBy)
    {
        Query query = mDatabaseReference.orderByChild(sortBy).startAt(viewData).endAt(viewData+"\uf8ff");

        jobOptions = new FirebaseRecyclerOptions.Builder<JobsModel>().setQuery(query,JobsModel.class).build();
        adapter = new FirebaseRecyclerAdapter<JobsModel, myViewHolder>(jobOptions) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull JobsModel model) {
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
                        bundle.putString("BookmarkPosition", getRef(position).getKey()); // to know which one to remove from the database if remove btn clicked
                        bundle.putBoolean("isBookmarked", isTrue); // so I know to make the add bookmark button invisible
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

    private String getDisplayName()
    {
        // getting the current user's name from database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getDisplayName();

        return username;
    }
}