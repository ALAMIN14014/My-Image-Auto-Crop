package com.example.check;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;


public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 2;
    EditText image_uri;
    ImageView imageView;
    Button addImage, captureImage;
    private static int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize views
        imageView = findViewById(R.id.image_display);
        image_uri = findViewById(R.id.image_name);
        addImage = findViewById(R.id.add_image);
        captureImage = findViewById(R.id.captureImageId);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);


            }
        });

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageURI = data.getData();
                image_uri.setText(selectedImageURI.toString());
                //   Picasso.with(MainActivity.this).load(selectedImageURI).noPlaceholder().centerCrop().fit()
                //         .into((ImageView) findViewById(R.id.image_display));


                Picasso.get().load(selectedImageURI).transform(new CropTransformation(0, 1200))
                        .transform(new CropCircleTransformation())
                        .into(imageView);

            }

        }

        if (requestCode == 100) {

            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
             imageView.setImageBitmap(captureImage);
            Uri selectedImageURI = data.getData();
            image_uri.setText(selectedImageURI.toString());

            Picasso.get().load(selectedImageURI).transform(new CropTransformation(0, 1200))
                    .transform(new CropCircleTransformation())
                    .into(imageView);



        }


    }
}