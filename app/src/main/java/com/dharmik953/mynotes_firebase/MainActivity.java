package com.dharmik953.mynotes_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    TextView needAccount;
    TextView forgotpassword;
    EditText mEmail, mPassword;
    Button logIn;
    Button facebook_logIn;
    Button google_login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        needAccount = findViewById(R.id.need_account_sign_up);
        forgotpassword = findViewById(R.id.forgot_password);
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        logIn = findViewById(R.id.btn_logIn);
        facebook_logIn = findViewById(R.id.btn_facebook);
        google_login = findViewById(R.id.btn_google);

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,googleSignin.class));
            }
        });

        facebook_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, facebooklogin.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            finish();
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
        
        logIn.setOnClickListener(v -> {
            if (mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Enter E-mail and password", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString() , mPassword.getText().toString()).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        checkVerification();
                    }
                    else Toast.makeText(MainActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                });
            }
        });

        forgotpassword.setOnClickListener(v -> {
            Intent  intent2 = new Intent(MainActivity.this, forgot_password.class);
            startActivity(intent2);
        });

        needAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,signUp.class);
            startActivity(intent);
        });
    }

    private void checkVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()){
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,HomePage.class));
        }
        else {
            Toast.makeText(this, "Verify your email first", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }
}