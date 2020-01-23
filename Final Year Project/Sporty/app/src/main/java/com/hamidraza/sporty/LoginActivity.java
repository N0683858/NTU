package com.hamidraza.sporty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    //member variable
    private FirebaseAuth mAuth;
    // UI interfaces
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextView = findViewById(R.id.register_email);
        passwordTextView = findViewById(R.id.register_password);

        //if user presses enter on their keyboard it should attempt to login
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    public void signInExistingUser(View view) //linked to the sign-in button
    {
        attemptLogin();
    }

    public void registerNewUser(View view)
    {
        // sends user to the register page
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    private void attemptLogin()
    {
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) // check of pass or email is empty
            return;

        // Toast message to show login in process
        Toast.makeText(this, "Login in process...", Toast.LENGTH_SHORT).show();

        // Communicate with firebase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // isSuccessful()will return true is signing in was successful, else false if it wasn't
                Log.d("Login","Login Successful! (signWithEmailPass() onComplete() is: " + task.isSuccessful());

                if (!task.isSuccessful())
                {
                    // getException() will tell the reason why login was not successful
                    Log.d("Login","Login not successful! Problem is: " + task.getException());
                    showErrorDialog("There was an error signing in...");
                }
                else
                {// if its successful navigate the user to the main activity
                    Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });



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
