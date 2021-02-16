package com.hamidraza.nturec;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class DetailedViewFragment extends Fragment {

    TextView titleTV, compNameTV, jobDescTV, locationTV;
    String title, compName, jobDesc, location;
    Button addBookmarkBtn;
    ImageButton removeBookmarkBtn, delJob;

    // Firebase
    FirebaseDatabase mFirebaseDatabse;
    DatabaseReference mDatabaseReference, DataRef;

    String bookmarkPos;
    Boolean isBookMarked;

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
        bookmarkPos = bundle.getString("BookmarkPosition");
        isBookMarked = bundle.getBoolean("isBookmarked");

        // setting the data received to the be displayed in the view
        titleTV.setText(title);
        compNameTV.setText(compName);
        jobDescTV.setText(jobDesc);
        locationTV.setText(location);

        //------------ bookmark buttons ------------//
        addBookmarkBtn = view.findViewById(R.id.add_bookmark_btn);
        removeBookmarkBtn = view.findViewById(R.id.remove_bookmark_btn);
        delJob = view.findViewById(R.id.del_job_btn);

        //--------- setting visibility ------------//
        if(isBookMarked)
        {
            addBookmarkBtn.setVisibility(View.INVISIBLE);
            removeBookmarkBtn.setVisibility(View.VISIBLE);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = user.getDisplayName();
        Log.i("floatBtn", "user logged in is: " + currentUser);
        if(currentUser.equals("admin"))
        {
            delJob.setVisibility(View.VISIBLE);
        }

        //--------- Add Bookmarks button -------------//
        addBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bookmark Added!", Toast.LENGTH_SHORT).show();
        // Fire-base
        mFirebaseDatabse = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabse.getReference().child(getDisplayName());
        String key = mDatabaseReference.push().getKey();

        mDatabaseReference.child(key).child("job_title").setValue(title);
        mDatabaseReference.child(key).child("company_name").setValue(compName);
//        mDatabaseReference.child(key).child("department").setValue("Unknown");
        mDatabaseReference.child(key).child("job_desc").setValue(jobDesc);
        mDatabaseReference.child(key).child("location").setValue(location);

                addBookmarkBtn.setVisibility(view.INVISIBLE);
                removeBookmarkBtn.setVisibility(view.VISIBLE);
            }
        });

        //------------- Remove Bookmark Button -------------//
        removeBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(getDisplayName());

                Toast.makeText(getContext(), "Bookmark Removed!", Toast.LENGTH_SHORT).show();
                addBookmarkBtn.setVisibility(view.VISIBLE);
                removeBookmarkBtn.setVisibility(view.INVISIBLE);
//                BookmarksFragment bookmarksFragment = new BookmarksFragment();
//                getFragmentManager().beginTransaction().replace(R.id.navHostFragment, bookmarksFragment).commit();
            }
        });

        //------------ Delete Job (admin) ------------//
        delJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData("Jobs Opp");
                Toast.makeText(getContext(), "Job Deleted!", Toast.LENGTH_SHORT).show();
                HomeFragment homeFragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.navHostFragment, homeFragment).commit();
            }
        });

        return view;
    }

    private void removeData(String childName)
    {
        DataRef = FirebaseDatabase.getInstance().getReference();
        Query removeQuery = DataRef.child(childName).orderByChild("job_title").equalTo(title);

        removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot removeSnapshot: dataSnapshot.getChildren()) {
                    removeSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", "onCancelled", databaseError.toException());
            }
        });
    }

    private String getDisplayName()
    {
        // getting the current user's name from database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getDisplayName();

        return username;
    }
}