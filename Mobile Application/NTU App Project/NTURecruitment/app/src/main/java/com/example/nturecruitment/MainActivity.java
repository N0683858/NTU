package com.example.nturecruitment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.nturecruitment.ui.home.myViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;

import static android.content.res.ColorStateList.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView mRecyclerView;

    private FirebaseRecyclerOptions<JobsOppModel> jobOptions;
    private FirebaseRecyclerAdapter<JobsOppModel, myViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send, R.id.nav_qr_scanner)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        toolbar.setTitle("Job Opportunities");
        toolbar.setBackgroundColor(getResources().getColor(R.color.ntuColorAccent));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        drawer.setStatusBarBackgroundColor(getResources().getColor(R.color.ntuColorAccent));

//        // Fire-base
//        FirebaseDatabase mFirebaseDatabse = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabaseReference = mFirebaseDatabse.getReference().child("Jobs Opp");
//
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        jobOptions = new FirebaseRecyclerOptions.Builder<JobsOppModel>().setQuery(mDatabaseReference,JobsOppModel.class).build();
//        adapter = new FirebaseRecyclerAdapter<JobsOppModel, myViewHolder>(jobOptions) {
//            @Override
//            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull JobsOppModel model) {
//                holder.textViewTitle.setText("" + model.getJob_title());
//                holder.textViewCompName.setText(""+model.getCompany_name());
//                holder.textViewJobDesc.setText("" + model.getJob_desc());
//                holder.textViewLocation.setText("" + model.getLocation());
//
//            }
//
//            @NonNull
//            @Override
//            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//              View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
//                return new myViewHolder(v);
//            }
//        };
//
//        adapter.startListening();
//        mRecyclerView.setAdapter(adapter);

//        // Fire-base
//        FirebaseDatabase mFirebaseDatabse = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabaseReference = mFirebaseDatabse.getReference();

//        mDatabaseReference.child("Jobs Opp").child("IT Tech").child("job_title").setValue("IT Technician");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech").child("company_name").setValue("NTU");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech").child("job_desc").setValue("IT Technician is a wonder oppuertinaf adfa ddsdfs sdfssssssff sssdfsdfsdfwqerdf");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech").child("location").setValue("NTU, NO12 5RF");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech2").child("job_title").setValue("IT Technician2");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech2").child("company_name").setValue("NTU");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech2").child("job_desc").setValue("IT Technician is a wonder oppuertinaf adfa ddsdfs sdfssssssff sssdfsdfsdfwqerdf");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech2").child("location").setValue("NTU, NO12 5RF");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech3").child("job_title").setValue("IT Technician2");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech3").child("company_name").setValue("NTU");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech3").child("job_desc").setValue("IT Technician is a wonder oppuertinaf adfa ddsdfs sdfssssssff sssdfsdfsdfwqerdf");
//        mDatabaseReference.child("Jobs Opp").child("IT Tech3").child("location").setValue("NTU, NO12 5RF");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
