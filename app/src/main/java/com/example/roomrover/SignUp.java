package com.example.roomrover;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    android.widget.TextView profileusername,profilename,profilepass,profileemail;

    android.widget.TextView titlename,titleusername;

    android.widget.Button register,editprofile,deleteprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        profileusername = findViewById(R.id.profileusername);
        profilename = findViewById(R.id.profilename);
        profilepass= findViewById(R.id.profilepass);
        profileemail = findViewById(R.id.profileemail);

        titlename = findViewById(R.id.titlename);
        titleusername = findViewById(R.id.titleusername);

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

    public void showDatauser(){
        android.content.Intent intent = getIntent();
        titlename.setText("Welcome " + intent.getStringExtra("name"));
        titleusername.setText(intent.getStringExtra("username"));
        profileemail.setText(intent.getStringExtra("email"));
        profilename.setText(intent.getStringExtra("name"));
        profileusername.setText(intent.getStringExtra("username"));
        profilepass.setText(intent.getStringExtra("password"));
    }

}
