package com.hamidraza.nturec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tiper.MaterialSpinner;

import java.util.HashMap;


public class AddNewEntryFragment extends Fragment {

    private static final int REQUEST_CODE_IMAGE = 101;
    private MaterialSpinner mMaterialSpinner;
    private String[] dropdownOptions;
    private ImageView mImageView;
    private EditText titleEt, companyNameEt, locationEt;
    private TextView jobDescTv;
    private Button uploadBtn;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;

    Uri imageUri;
    boolean isImageSelected = false;

    public AddNewEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_entry, container, false);

        //
        mImageView = view.findViewById(R.id.upload_img);
        titleEt = view.findViewById(R.id.title_etv);
        companyNameEt = view.findViewById(R.id.compName_etv);
        locationEt = view.findViewById(R.id.location_etv);
        jobDescTv = view.findViewById(R.id.jobDec_etv);
        uploadBtn = view.findViewById(R.id.upload_btn);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs Opp");
        mStorageReference = FirebaseStorage.getInstance().getReference().child("JobOppIMG");

        // Drop down menu
        dropdownOptions = getResources().getStringArray(R.array.dropdown_options);
        mMaterialSpinner = view.findViewById(R.id.material_spinner2);
        mMaterialSpinner.setHintAnimationEnabled(false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, dropdownOptions);
        mMaterialSpinner.setAdapter(arrayAdapter);
        mMaterialSpinner.setSelection(0);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imageTitle = titleEt.getText().toString();
                if (isImageSelected != false && imageTitle != null)
                {
                    uploadIMG(imageTitle);
                    uploadJob();
                }
            }
        });


        return view;
    }

    private void uploadJob() {

        // Fire-base
        String key = mDatabaseReference.push().getKey();

        mDatabaseReference.child(key).child("job_title").setValue(titleEt.getText().toString());
        mDatabaseReference.child(key).child("company_name").setValue(companyNameEt.getText().toString());
        mDatabaseReference.child(key).child("department").setValue(mMaterialSpinner.getSelectedItem().toString());
        mDatabaseReference.child(key).child("job_desc").setValue(jobDescTv.getText().toString());
        mDatabaseReference.child(key).child("location").setValue(locationEt.getText().toString());

        Toast.makeText(getContext(), "Job Opportunity Created!", Toast.LENGTH_SHORT).show();
        HomeFragment homeFragment = new HomeFragment();
        getFragmentManager().beginTransaction().replace(R.id.navHostFragment, homeFragment).commit();

    }

    private void uploadIMG(final String imageTitle) {

        final String key = mDatabaseReference.push().getKey();
        mStorageReference.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // Successfully selecting the image
                mStorageReference.child(key + "jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("JobImgTitle", imageTitle);
                        hashMap.put("ImageUrl", uri.toString());

                        // Add the image to the storage database
                        mDatabaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getContext(),"New Job Opportunity Created!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null)
        {
            imageUri = data.getData();
            isImageSelected = true;
            mImageView.setImageURI(imageUri);
        }
    }
}