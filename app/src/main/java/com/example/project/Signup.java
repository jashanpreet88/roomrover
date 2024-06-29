package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText confirmPasswordEditText;
    EditText passwordEditText;
    Button registerButton;
    FirebaseAuth mAuth;
    DatabaseReference UsersDB;
    android.widget.TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.project.R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        UsersDB = FirebaseDatabase.getInstance().getReference("users");

        nameEditText = findViewById(com.example.project.R.id.name);
        emailEditText = findViewById(com.example.project.R.id.email);
        passwordEditText = findViewById(com.example.project.R.id.password);
        confirmPasswordEditText = findViewById(com.example.project.R.id.confirm_password);
        registerButton = findViewById(com.example.project.R.id.register_button);
        textView = findViewById(com.example.project.R.id.acc1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(Signup.this, LoginPage.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Valid email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Confirm Password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new com.google.android.gms.tasks.OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User registration success
                            com.google.firebase.auth.FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            User newUser = new User(name, email);

                            // Save user data to database
                            UsersDB.child(userId).setValue(newUser)
                                    .addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Signup.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                                startActivity(new android.content.Intent(Signup.this, MainActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(Signup.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // User registration failed
                            String errorMessage = "Authentication failed.";
                            try {
                                throw task.getException();
                            } catch (com.google.firebase.auth.FirebaseAuthWeakPasswordException e) {
                                errorMessage = "Weak password.";
                            } catch (com.google.firebase.auth.FirebaseAuthInvalidCredentialsException e) {
                                errorMessage = "Invalid email.";
                            } catch (com.google.firebase.auth.FirebaseAuthUserCollisionException e) {
                                errorMessage = "User with this email already exists.";
                            } catch (Exception e) {
                                errorMessage = e.getMessage();
                            }
                            Toast.makeText(Signup.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}


