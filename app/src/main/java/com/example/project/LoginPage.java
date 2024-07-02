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

    private android.widget.EditText loginusername, loginpass;
    private android.widget.Button loginbtn, forgotPasswordBtn;
    private android.widget.TextView signuplink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        loginusername = findViewById(R.id.usernamelogin);
        loginpass = findViewById(R.id.passwordlogin);
        loginbtn = findViewById(R.id.login);
        forgotPasswordBtn = findViewById(R.id.forgot_password);
        signuplink = findViewById(R.id.signuplink);

        // Sign up link
        signuplink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, Signup.class);
            startActivity(intent);
            finish();
        });

        // Forgot Password link
        forgotPasswordBtn.setOnClickListener(v -> {
             Intent intent = new Intent(LoginPage.this, ResetPasswordActivity.class);
             startActivity(intent);
             finish();
        });

        // Login button
        loginbtn.setOnClickListener(v -> checkuser());
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
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login success
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent;
                        if (user != null && "jashanchhapa85@gmail.com".equals(user.getEmail())) {
                            intent = new Intent(LoginPage.this, ManagerActivity.class);
                        } else {
                            intent = new Intent(LoginPage.this, MainActivity.class);
                        }
                        startActivity(intent);
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
                });
    }
}
