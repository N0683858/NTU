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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //CONSTANTS


    //UI interface variables
    private AutoCompleteTextView userNameTV;
    private AutoCompleteTextView emailTV;
    private EditText passwordTV;
    private EditText confirmPasswordTV;

    //Fire-base Instance variable
    // used to communicate with firebase user login data (authorise user)
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameTV = findViewById(R.id.register_username);
        emailTV = findViewById(R.id.register_email);
        passwordTV = findViewById(R.id.register_password);
        confirmPasswordTV = findViewById(R.id.register_confirm_password);

        confirmPasswordTV.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {// when user presses enter it will run the attemptRegistration() method

                attemptRegisteration();
                return false;
            }
        });


        // get hold of fire-base instance
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view)
    {
        attemptRegisteration();
    }

    private void attemptRegisteration()
    {
        // Reset errors displayed
        emailTV.setError(null);
        passwordTV.setError(null);

        //store values at the time of login
        String email = emailTV.getText().toString();
        String passwaord = passwordTV.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // check for a valid password or if the user even entered one
        // TextUtlisisEmpty() makes sure the string is not null & empty.
        // isPasswordValid() is a custom function which makes sure the password is longer than 4 characters long
        if(TextUtils.isEmpty(passwaord) || !isPasswordValid(passwaord))
        {
            passwordTV.setError("Password too short or does not match!"); //show pop-up error message
            focusView = passwordTV;
            cancel = true; // dont attempt registration
        }

        // check if the email is valid
        if(TextUtils.isEmpty(email))
        {
            emailTV.setError("This field cannot be left empty!");
            focusView = emailTV;
            cancel = true;
        }
        else if (!isEmailValid(email))
        {
            emailTV.setError("This email address in invalid!");
            focusView = emailTV;
            cancel = true;
        }


        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            createFirebaseUser();
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@"); // will check if the email string contains '@' symbol and return true, else return false
    }

    private boolean isPasswordValid(String password)
    {
        String confirmPassword = confirmPasswordTV.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4; // 'confirm password' matches the the password and is more than 4 characters long
    }

    // Create fire-base user
    private void createFirebaseUser()
    {
        String email = emailTV.getText().toString();
        String password = passwordTV.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Flashchat", "createUser onComplete: " + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.d("Flashchat", "user creation failed" + e.getMessage());
                    showErrorDialog("Registration Failed!");
                }
                else
                {
                    saveDisplayName();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

    }

    private void saveDisplayName()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = userNameTV.getText().toString();

        if (user !=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("FlashChat", "User name updated.");
                            }
                        }
                    });

        }

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
