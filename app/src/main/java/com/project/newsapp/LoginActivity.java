package com.project.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.newsapp.activities.MainActivity;

public class LoginActivity<val> extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    TextView havAcc;
    private FirebaseAuth firebaseAuth;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);



        firebaseAuth = FirebaseAuth.getInstance();

        havAcc = findViewById(R.id.haveAcc);
        havAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,0);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(email.isEmpty()&&password.isEmpty()) {
                    Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);
                    emailEditText.startAnimation(animation);
                    passwordEditText.startAnimation(animation);
                }
                else if(email.isEmpty()||password.isEmpty())
                {
                    if(emailEditText.getText().toString().isEmpty()) {
                        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);
                        emailEditText.startAnimation(animation);

                    }else if(passwordEditText.getText().toString().isEmpty())
                    {
                        Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);
                        passwordEditText.startAnimation(animation);
                    }
                    Toast.makeText(LoginActivity.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                }else {
                    // Authenticate the user with Firebase Authentication
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login success
                                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        // Redirect
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in,0);

                                    } else {
                                        if(!task.isSuccessful() && (password!=passwordEditText.getText().toString() && email!=emailEditText.getText().toString()))
                                        {
                                            Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);
                                            emailEditText.startAnimation(animation);
                                            passwordEditText.startAnimation(animation);
                                        }
                                        else if(!task.isSuccessful() && password!=passwordEditText.getText().toString()){
                                            Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);

                                            passwordEditText.startAnimation(animation);
                                        }
                                        else if(!task.isSuccessful() && email!=emailEditText.getText().toString())
                                        {
                                            Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.size);

                                            emailEditText.startAnimation(animation);
                                        }

                                        // Login failed
                                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}