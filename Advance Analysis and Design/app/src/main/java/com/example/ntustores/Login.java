package com.example.ntustores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    AutoCompleteTextView userName;
    EditText password;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);

    }

    public void attemptLogin(View view){
        Intent intent = new Intent(Login.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
