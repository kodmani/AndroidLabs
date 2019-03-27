package com.example.androidlabs;

import android.content.ClipData;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class TestToolbar extends AppCompatActivity {

    android.support.v7.widget.Toolbar myToolbar;
    String newMessage = "This is the initial message";
    Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        myToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, newMessage, Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                alertExample();
                break;
            case R.id.item3:
                Snackbar sb = Snackbar.make(myToolbar, "Go Back?", Snackbar.LENGTH_LONG)
                .setAction("Action text", e -> finish());
                sb.show();
                break;
            case R.id.item4:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;

    }

    public void alertExample(){

        View middle = getLayoutInflater().inflate(R.layout.activity_new_message, null);
        EditText newMessageET = middle.findViewById(R.id.newMessageET);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        newMessage = newMessageET.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();

    }
}
