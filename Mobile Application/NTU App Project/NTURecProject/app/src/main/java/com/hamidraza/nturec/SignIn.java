package com.hamidraza.nturec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {

    // Firebase
    private FirebaseAuth mAuth;

    // UI interfaces
    private TextInputLayout emailTextView;
    private TextInputLayout passwordTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // UI Interface
        emailTextView = findViewById(R.id.tv_email);
        passwordTextView = findViewById(R.id.tv_password);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
    }

    public void SignIn(View view)
    {
        validateLogin();
    }

    public void registerNewUser(View view)
    {
        // send user to register page
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void validateLogin()
    {
        final String email = emailTextView.getEditText().getText().toString();
        final String password = passwordTextView.getEditText().getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) //check if the password and email fields are empty
            return;

        // Show message to give user feedback
        Toast.makeText(this, "Loggin in...", Toast.LENGTH_SHORT).show();

        // Communicate with Firebase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // check if login is successful
                Log.d("Login", "Login Successful! (signWithEmailPass() onComplete() is: " + task.isSuccessful());

                if(!task.isSuccessful()) // If login was not successful
                {
                    emailTextView.setError("Username or Password incorrect!");
                    // getException() will tell the reason why login was not successful
                   Log.d("Login","Login not successful! Problem is: " + task.getException());
                }
                else // if its successful
                {
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}