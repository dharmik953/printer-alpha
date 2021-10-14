package com.dharmik953.mynotes_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUp extends AppCompatActivity {

    TextView backToLogIn;
    EditText mEmail, mPassword, reEnterPassword;
    Button signUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backToLogIn = findViewById(R.id.back_to_login_page);
        mEmail = findViewById(R.id.sign_up_email);
        mPassword = findViewById(R.id.sign_up_password);
        signUp = findViewById(R.id.btn_signUP);
        reEnterPassword = findViewById(R.id.re_enter_password);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(v -> {
            if (!mPassword.getText().toString().trim().equals(reEnterPassword.getText().toString().trim())){
                Toast.makeText(signUp.this, "Both password must be same", Toast.LENGTH_SHORT).show();
            }
            else  if (mPassword.getText().toString().trim().isEmpty() || mEmail.getText().toString().isEmpty()){
                Toast.makeText(signUp.this, "Enter email and password", Toast.LENGTH_SHORT).show();
            }
            else if (mPassword.length() <8){
                Toast.makeText(signUp.this, "Password should be grate than 8 Digits", Toast.LENGTH_SHORT).show();
            }else {
                //   finally process for email sign in

                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
//                                    updateUI(user);
                                sendVarificationLink();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(signUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                            }
                        });
            }
        });

        backToLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(signUp.this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void sendVarificationLink() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(task -> {

                Toast.makeText(signUp.this, "Versification Email sent,Verify and Log In again", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                startActivity(new Intent(signUp.this,MainActivity.class));
            });
        }else Toast.makeText(this, "Failed to send Email", Toast.LENGTH_SHORT).show();
    }

}