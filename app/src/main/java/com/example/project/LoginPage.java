package com.example.project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
public class LoginPage extends AppCompatActivity {

    android.widget.EditText loginusername, loginpass;
    android.widget.Button loginbtn;
    android.widget.TextView singuplink;

    // login activity change
    // logo changed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        loginusername = findViewById(com.example.project.R.id.usernamelogin);
        loginpass = findViewById(com.example.project.R.id.passwordlogin);
        loginbtn = findViewById(com.example.project.R.id.login);
        singuplink = findViewById(com.example.project.R.id.signuplink);

        singuplink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(LoginPage.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });


        loginbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                checkuser();
            }
        });
    }

    public void checkuser(){
        String usernameLogin = loginusername.getText().toString().trim();
        String passLogin = loginpass.getText().toString().trim();
        com.google.firebase.database.DatabaseReference reference = com.google.firebase.database.FirebaseDatabase.getInstance()
                .getReference("users");
        com.google.firebase.database.Query checkuserData = reference.orderByChild("username")
                .equalTo(usernameLogin);
        checkuserData.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull com.google.firebase.database.DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passDB = snapshot.child(usernameLogin).child("password")
                            .getValue(String.class);
                    if (passDB.equals(passLogin)) {
                        String usernameDB = snapshot.child(usernameLogin).child("username")
                                .getValue(String.class);
                        String passDBl = snapshot.child(usernameLogin).child("password")
                                .getValue(String.class);
                        String nameDB = snapshot.child(usernameLogin).child("name")
                                .getValue(String.class);
                        String emailDB = snapshot.child(usernameLogin).child("email")
                                .getValue(String.class);
                        android.content.Intent intent = new android.content.Intent(com.example.project.LoginPage.this,
                                com.example.project.MainActivity.class);
                        startActivity(intent);
                        finish();
                        intent.putExtra("name", nameDB);
                        intent.putExtra("email", emailDB);
                        intent.putExtra("password", passDBl);
                        intent.putExtra("username", usernameDB);
                        startActivity(intent);
                        finish();
                    } else {
                        android.widget.Toast.makeText(com.example.project.LoginPage.this, "Invalid Password", android.widget.Toast.LENGTH_SHORT).show();
                    }
                } else {
                    android.widget.Toast.makeText(com.example.project.LoginPage.this, "user is not exists", android.widget.Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {

            }
        });



    }
}