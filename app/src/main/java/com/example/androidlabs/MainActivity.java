package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.pm.SharedLibraryInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    EditText emailET;
    Button loginBtn;
    public static final String ACTIVITY_NAME = "activity_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //layout launch
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);

        sp = getSharedPreferences("emailET", Context.MODE_PRIVATE);
        String found = sp.getString("emailTyped", "not found");

        //login button
        loginBtn = (Button) findViewById(R.id.loginBtn);

        emailET = (EditText) findViewById(R.id.emailET);

        emailET.setText(found);

        //log in button intent
         loginBtn.setOnClickListener( (btn)->{

            Intent nextPage = new Intent(MainActivity.this, ProfileActivity.class);
            nextPage.putExtra("emailTyped", emailET.getText().toString());

            startActivityForResult(nextPage, 345);

        });

    }

    @Override
    protected void onPause() {

        super.onPause();


        SharedPreferences.Editor editor = sp.edit();

        String whatWasTyped = emailET.getText().toString();
        editor.putString("emailTyped", whatWasTyped);
        editor.commit();

        Toast.makeText(this, "SAVED !", Toast.LENGTH_LONG).show();

        Log.e(ACTIVITY_NAME, "In function: onPause()");


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }
}
