package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import androidx.core.app.ActivityCompat;
import android.hardware.camera2.CameraManager;
import android.hardware.Camera;



import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

        private Button selectbtn, capturebtn, reportbtn;
        private static final int REQUEST_SELECT_IMAGE = 10;
        private static final int REQUEST_CAPTURE_IMAGE = 11;
        private Bitmap imageBitmap;
        private TextView AmrishaMahindi;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            // Find the select and capture buttons by their IDs
            selectbtn = findViewById(R.id.button1);
            capturebtn = findViewById(R.id.button2);
            reportbtn =findViewById(R.id.button3);
            // Set click listeners for the buttons

            reportbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    startActivity(intent);
                }
            });

            selectbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_SELECT_IMAGE);
                }
            });

            capturebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
                }
            });

            // Check if the app has camera permission, request it if not
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURE_IMAGE);
                }
            }

            // Set the background color
            View view = this.getWindow().getDecorView();
            view.setBackgroundResource(R.color.light_green);
        }

        // Handle the result of the image selection/capture
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_SELECT_IMAGE && data != null && data.getData() != null) {
                    // Get the selected image and convert it to a Bitmap
                    Uri selectedImageUri = data.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Open the next activity and send the image Bitmap as an extra
                    openNextActivity();
                } else if (requestCode == REQUEST_CAPTURE_IMAGE && data != null && data.getExtras() != null) {
                    // Get the captured image and convert it to a Bitmap
                    imageBitmap = (Bitmap) data.getExtras().get("data");

                    // Open the next activity and send the image Bitmap as an extra
                    openNextActivity();
                }
            }
        }

        // Request camera permission if not granted
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    getPermission();
                }
            }
        }

        // Get camera permission
        private void getPermission() {
            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURE_IMAGE);
        }

        // Open the next activity and send the image Bitmap as an extra
        private void openNextActivity() {
            Intent intent = new Intent(this, MainActivity3.class);
            intent.putExtra("imageBitmap", imageBitmap);
            startActivity(intent);
        }

    private void setPermission() {
    }
}