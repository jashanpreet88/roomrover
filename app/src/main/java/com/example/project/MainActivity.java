package com.example.project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference HotelsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        isUserloged();
        HotelsDB = FirebaseDatabase.getInstance().getReference("hotels");




    }

    private void isUserloged(){
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new android.content.Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    if(firebaseAuth.getCurrentUser().getEmail().equals("manager@gmail.com")){
                        Intent intent = new android.content.Intent(MainActivity.this, ManagerActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        // user
                        }
                }
            }
        });
    }
}