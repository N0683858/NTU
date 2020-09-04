package com.hamidraza.nturec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {

    //UI Interface
    private TextInputLayout usernameTxtView,
            emailTxtView,
            passwordTxtView,
            confirmPassTxtView;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameTxtView = findViewById(R.id.register_username);
        emailTxtView = findViewById(R.id.register_email);
        passwordTxtView = findViewById(R.id.register_password);
        confirmPassTxtView = findViewById(R.id.confirm_password);


        // Communicate with firebase to handle user registration and authentication
        mAuth = FirebaseAuth.getInstance();
    }

    public void SignUp(View view)
    {
        attemptRegistration();

    }

    public void attemptRegistration()
    {
        // Reset errors to null
        usernameTxtView.setError(null);
        emailTxtView.setError(null);
        passwordTxtView.setError(null);

        // store the values into the varibles
        String username = usernameTxtView.getEditText().getText().toString();
        String email = emailTxtView.getEditText().getText().toString();
        String password = passwordTxtView.getEditText().getText().toString();

        boolean cancelRegisteration = false;
        View focusView = null;

        if(TextUtils.isEmpty(password) || !isPasswordValid(password))
        {
            passwordTxtView.setError("Password too short or does not match!"); // will show pop up error message
            focusView = passwordTxtView; // focus on the password text view
            cancelRegisteration = true; // dont attept user registeration into firebase database
        }

        // username validation
        if(TextUtils.isEmpty(username))
        {
            usernameTxtView.setError("This field cannot be left empty!");
        }

        // check if email is valid
        if(TextUtils.isEmpty(email)) // if its empty
        {
            emailTxtView.setError("This field cannot be left empty!");
            focusView = emailTxtView;
            cancelRegisteration = true;
        }
        else if(!isEmailValid(email))
        {
            emailTxtView.setError("This email address in invalid!");
            focusView = emailTxtView;
            cancelRegisteration = true;
        }

        if(cancelRegisteration)
        {
            // There was an error and registeration was cancelled
            // focus on the first field in the form
            focusView.requestFocus();
        }
        else
        {
            createFirebaseUser();
        }


    }

    private boolean isEmailValid(String email)
    {
        // will check if the email string contains '@' symbol and return true, else return false
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        String confirmPassword = confirmPassTxtView.getEditText().getText().toString();
        // 'confirm password' matches the the password and is more than 4 characters long
        return confirmPassword.equals(password) && password.length() > 4;
    }


    private void createFirebaseUser()
    {
        String email = emailTxtView.getEditText().getText().toString();
        String password = passwordTxtView.getEditText().getText().toString();

        // Saving the details (register them by email and password) of the new created user
        // into the firebase database using mAuth object
        // this method returns a task which can be used to listen to that event which is being created.
        // This listener with onComplete() method will report back if the new user has been successfully
        // been created on the firebase database or not
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("SignUp", "createUser onComplete: " + task.isSuccessful());

                if(!task.isSuccessful())
                {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.d("SignUp", "user creation failed" + e.getMessage());
                    // Show error dialog to show the user the registration was not successful
                    showErrorDialog("Registration Failed!");
                }
                else
                {
                    Toast.makeText(SignUp.this, "New User Created!", Toast.LENGTH_SHORT).show();
                    saveDisplayName();
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }


    private void saveDisplayName() // setting users username
    {
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = usernameTxtView.getEditText().getText().toString();

        if (user !=null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("SignUp", "User name updated.");
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