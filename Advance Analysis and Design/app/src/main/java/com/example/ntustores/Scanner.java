package com.example.ntustores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Scanner extends AppCompatActivity {

    CodeScanner mCodeScanner;
    CodeScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        mScannerView = findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(this,mScannerView);


        // Once the QR code has been detected in the scanner view it will decode that
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) { // once the qr code has been decoded it will be stores in the result variable


                runOnUiThread(new Runnable() { // will allow the user to scan multiple times without going off the scannerView
                    @Override
                    public void run() {
                        Log.d("CodeDecoded", "QR Code Decoded Successfully. Result: " + result);
                        Toast.makeText(Scanner.this,"Scanned Successfully!", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        mScannerView.setOnClickListener(new View.OnClickListener() { // when the user clicks on the scannerView (after the scanner finishes taking a qr code) run the following code
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview(); // re-start the qr scanner view to allow user to scan again
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        requestCamera(); // Ask for permission
    }

    private void requestCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() { // if permission is granted run the following code
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mCodeScanner.startPreview(); // start qr scanner view

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) { // run following code if user declines
                Toast.makeText(Scanner.this, "Camera Permission is Required!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();// keep asking for permission until user accepts
            }
        }).check();

    }
}
