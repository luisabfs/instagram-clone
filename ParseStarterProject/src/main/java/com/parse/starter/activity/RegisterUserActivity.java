package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;

public class RegisterUserActivity extends AppCompatActivity {
    //layout
    private EditText edit_username;
    private EditText edit_email;
    private EditText edit_password;
    private Button bt_register;

    //constants
    private static final String TAG = "RegisterUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        edit_username = findViewById(R.id.edit_username_register);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password_register);
        bt_register = findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        //creating user object
        ParseUser user = new ParseUser();
        user.setUsername(edit_username.getText().toString());
        user.setEmail(edit_email.getText().toString());
        user.setPassword(edit_password.getText().toString());

        //saving user data
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(RegisterUserActivity.this, "User successfully registered!", Toast.LENGTH_LONG).show();
                    openLoginUser();
                }else{
                    Toast.makeText(RegisterUserActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG,"error SignUpCallback: " + e.getMessage());
                }
            }
        });
    }

    public void openLoginUser(View view){
        Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openLoginUser(){
        Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
