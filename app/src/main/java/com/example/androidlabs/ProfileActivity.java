package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "ProfileActivity";

    ImageButton mImageButton;

    public  void takePic (View view){
        dispatchTakePictureIntent();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent fromPrev = getIntent();

        String prevTyped = fromPrev.getStringExtra("emailTyped");

        EditText enterText = (EditText) findViewById(R.id.emailET2);
        enterText.setText(prevTyped);

        mImageButton = findViewById(R.id.mImageButton);
        mImageButton.setOnClickListener(e -> {
            dispatchTakePictureIntent();
        });

        Button gotochat = (Button)findViewById(R.id.chatBtn);
        gotochat.setOnClickListener( c -> {

            Intent nextPage = new Intent(ProfileActivity.this, ChatRoomActivity.class);

            //Now make the transition:
            startActivityForResult( nextPage, 345);
        });

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
}