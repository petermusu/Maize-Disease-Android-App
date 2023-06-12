package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the background color
        View view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.cream_yellow);

        // Delay the transition to the main activity by 3 seconds
        // Animated delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // Delay in milliseconds

        TextView textView = findViewById(R.id.welcometextView);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLACK);
    }
}
