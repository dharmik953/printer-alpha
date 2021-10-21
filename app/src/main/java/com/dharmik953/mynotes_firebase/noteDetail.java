package com.dharmik953.mynotes_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class noteDetail extends AppCompatActivity {

    TextView title, note;
    FloatingActionButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Detailed Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        // to go tu the back activity..

        title = findViewById(R.id.note_title_detail);
        note = findViewById(R.id.note_description_detail);
        editButton = findViewById(R.id.edit_button);

        Intent data = getIntent();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),editNote.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("note",data.getStringExtra("note"));
//                intent.putExtra("noteId",data.getStringExtra("noteId"));
            }
        });

        title.setText(data.getStringExtra("title_"));
        note.setText(data.getStringExtra("note_"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}