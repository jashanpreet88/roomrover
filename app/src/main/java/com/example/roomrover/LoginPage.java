package com.example.roomrover;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginPage extends AppCompatActivity {

    EditText loginusername, loginpass;
    Button loginbtn;
    TextView singuplink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginusername = findViewById(R.id.usernamelogin);
        loginpass = findViewById(R.id.passwordlogin);
        loginbtn = findViewById(R.id.login);
        singuplink = findViewById(R.id.signuplink);


       singuplink.setOnClickListener(new android.view.View.OnClickListener() {
           @Override
           public void onClick(android.view.View v) {
               android.content.Intent intent = new android.content.Intent(LoginPage.this,SignUp.class);
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
                            android.content.Intent intent = new android.content.Intent(LoginPage.this,
                                    MainActivity.class);
                            intent.putExtra("name", nameDB);
                            intent.putExtra("email", emailDB);
                            intent.putExtra("password", passDBl);
                            intent.putExtra("username", usernameDB);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginPage.this, "pass is bad", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginPage.this, "user is not exists", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull com.google.firebase.database.DatabaseError error) {

                }
            });


        }
    }


