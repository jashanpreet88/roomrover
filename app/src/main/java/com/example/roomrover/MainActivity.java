package com.example.roomrover;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(MainActivity.this,LoginPage.class);
                startActivity(intent);
            }
        },DELAY);


    }
}