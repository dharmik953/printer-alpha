package com.dharmik953.mynotes_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class addNotes extends AppCompatActivity {

    EditText mTitle, mNote;
    ImageView save;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        // to go tu the back activity..

        save = findViewById(R.id.btn_save);
        mTitle = findViewById(R.id.et_title);
        mNote = findViewById(R.id.et_note);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        save.setOnClickListener(v -> {

            if (mNote.getText().toString().isEmpty() || mTitle.getText().toString().isEmpty()){
                Toast.makeText(addNotes.this, "Both fields are required", Toast.LENGTH_SHORT).show();
            }
            else {
                DocumentReference reference = firestore.collection("notes").document(user.getUid()).collection("myNotes").document();
//                DocumentReference reference = firestore.collection("notes").document(user.getUid()).getParent().document();
                Map<String,Object> note = new HashMap<>();
                note.put("title",mTitle.getText().toString());
                note.put("content",mNote.getText().toString());

                reference.set(note).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Note created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addNotes.this,HomePage.class));
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to add Notes", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();           //both works same
//            startActivity(new Intent(addNotes.this,HomePage.class));
        }
        return super.onOptionsItemSelected(item);
    }
}