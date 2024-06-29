package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import android.content.Intent;

public class LoginPage extends AppCompatActivity {

    android.widget.EditText loginusername, loginpass;
    android.widget.Button loginbtn;
    android.widget.TextView singuplink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        loginusername = findViewById(com.example.project.R.id.usernamelogin);
        loginpass = findViewById(com.example.project.R.id.passwordlogin);
        loginbtn = findViewById(com.example.project.R.id.login);
        singuplink = findViewById(com.example.project.R.id.signuplink);

        // Sign up link
        singuplink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(LoginPage.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        // Login button
        loginbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                checkuser();
            }
        });
    }

    public void checkuser() {
        String email = loginusername.getText().toString().trim();
        String password = loginpass.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginusername.setError("Valid email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            loginpass.setError("Password is required");
            return;
        }

        // Authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.getEmail().equals("manager@gmail.com")) {
                                Intent intent = new Intent(LoginPage.this, ManagerActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            // Login failed
                            String errorMessage = "Authentication failed.";
                            try {
                                throw task.getException();
                            } catch (com.google.firebase.auth.FirebaseAuthInvalidUserException e) {
                                errorMessage = "User does not exist.";
                            } catch (com.google.firebase.auth.FirebaseAuthInvalidCredentialsException e) {
                                errorMessage = "Invalid password.";
                            } catch (Exception e) {
                                errorMessage = e.getMessage();
                            }
                            Toast.makeText(LoginPage.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
