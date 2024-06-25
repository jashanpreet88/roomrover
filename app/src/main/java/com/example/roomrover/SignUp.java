package com.example.roomrover;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    android.widget.TextView username,name, password, emailid;


    android.widget.Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(com.example.roomrover.R.id.username);
        name = findViewById(com.example.roomrover.R.id.name);
        password = findViewById(com.example.roomrover.R.id.password1);
        emailid = findViewById(R.id.email);


        register = findViewById(R.id.SignOut);


        register.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                android.content.Intent intent = new android.content.Intent(SignUp.this,
                        LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
