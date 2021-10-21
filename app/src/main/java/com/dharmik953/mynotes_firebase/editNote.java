package com.dharmik953.mynotes_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class editNote extends AppCompatActivity {

    FloatingActionButton updateNote;
    EditText title, note;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("myNotes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        // to go tu the back activity...

        updateNote = findViewById(R.id.btn_update);
        title = findViewById(R.id.et_title_edit);
        note = findViewById(R.id.et_note_edit);

//        String title_ =

        updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String noteTitle = title.getText().toString();
                String noteDescription = note.getText().toString();
                
                updaateNote(noteTitle , noteDescription);
            }
        });

    }

    private void updaateNote(String noteTitle, String noteDescription) {

        HashMap userMap = new HashMap();

                userMap.put("title" , title.getText().toString());
                userMap.put("content" , note.getText().toString());

        root.child(noteTitle).updateChildren(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                title.setText(noteTitle);
                note.setText(noteDescription);
                startActivity(new Intent(editNote.this,HomePage.class));
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