package com.hamidraza.sporty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //member variables
    private FirebaseAuth mAuth;
    // UI interfaces
    private AutoCompleteTextView emailView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailView = findViewById(R.id.register_email);
        passwordView = findViewById(R.id.register_password);

        //if user presses enter on their keyboard it should attempt to login
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                Log.d("Login", "Enter pressed");
                attemptLogin();

                return false;
            }
        });

        // get instance from fire-base
        mAuth = FirebaseAuth.getInstance();


    }

    public void signInExistingUser(View view)
    {

    }

    public void registerNewUser(View view)
    {
        // sends user to the register page
        Intent intent = new Intent(this, SignUpActivity.class);
        finish();
        startActivity(intent);

    }

    private void attemptLogin()
    {
        Log.d("Login", "Enter pressed");


    }

    private void showErrorDialog(String message)
    {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
