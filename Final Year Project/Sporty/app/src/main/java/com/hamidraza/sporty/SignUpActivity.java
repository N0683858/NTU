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
    private AutoCompleteTextView userNameTextView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private EditText confirmPasswordTextView;

    // Handles user registration and authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameTextView = findViewById(R.id.register_username);
        emailTextView = findViewById(R.id.register_email);
        passwordTextView = findViewById(R.id.register_password);
        confirmPasswordTextView = findViewById(R.id.register_confirm_password);

        confirmPasswordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        emailTextView.setError(null);
        passwordTextView.setError(null);

        //store values at the time of login
        String email = emailTextView.getText().toString();
        String passwaord = passwordTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // check for a valid password or if the user even entered one
        // TextUtlisisEmpty() makes sure the string is not null & empty.
        // isPasswordValid() is a custom function which makes sure the password is longer than 4 characters long
        if(TextUtils.isEmpty(passwaord) || !isPasswordValid(passwaord))
        {
            passwordTextView.setError("Password too short or does not match!"); //show pop-up error message
            focusView = passwordTextView;
            cancel = true; // dont attempt registration
        }

        // check if the email is valid
        if(TextUtils.isEmpty(email))
        {
            emailTextView.setError("This field cannot be left empty!");
            focusView = emailTextView;
            cancel = true;
        }
        else if (!isEmailValid(email))
        {
            emailTextView.setError("This email address in invalid!");
            focusView = emailTextView;
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
            createFirebaseUser(); // if there was no errors, create user in firebase
        }
    }

    private boolean isEmailValid(String email)
    {
        // will check if the email string contains '@' symbol and return true, else return false
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        String confirmPassword = confirmPasswordTextView.getText().toString();
        // 'confirm password' matches the the password and is more than 4 characters long
        return confirmPassword.equals(password) && password.length() > 4;
    }

    // Create fire-base user
    private void createFirebaseUser()
    {
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        // Saving the details (register them by email and password) of the new created user
        // into the firebase database using mAuth object
        // this method returns a task which can be used to listen to that event which is being created.
        // This listener with onComplete() method will report back if the new user has been successfully
        // been created on the firebase database or not
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Flashchat", "createUser onComplete: " + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.d("Flashchat", "user creation failed" + e.getMessage());
                    // Show error dialog to show the user the registration was not successful
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
        String displayName = userNameTextView.getText().toString();

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
