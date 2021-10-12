package com.dharmik953.mynotes_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {

    TextView backToLogin;
    Button submit;
    EditText email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backToLogin = findViewById(R.id.go_to_login_page);
        submit = findViewById(R.id.submit);
        email = findViewById(R.id.forgotpassword_email);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty()){
                Toast.makeText(forgot_password.this, "Email must be entered", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Toast.makeText(forgot_password.this, "Mail sent, you can recover your password", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(forgot_password.this,MainActivity.class));
                    }
                    else Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                });
            }
        });

        backToLogin.setOnClickListener(v -> {
            Intent intent3 = new Intent(forgot_password.this,MainActivity.class);
            startActivity(intent3);
        });
    }
}