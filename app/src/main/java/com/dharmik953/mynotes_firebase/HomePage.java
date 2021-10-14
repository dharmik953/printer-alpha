package com.dharmik953.mynotes_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class HomePage extends AppCompatActivity {

    FloatingActionButton addNotes;
    FirebaseAuth mAuth;
    CardView cardView;
    StaggeredGridLayoutManager layoutManager;
    myadapter adapter;

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final DatabaseReference root = db.getReference().child("myNotes");
    private ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cardView = findViewById(R.id.note_card);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new myadapter(this, list);

        recyclerView.setAdapter(adapter);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addNotes = findViewById(R.id.add_button);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notes");

        addNotes.setOnClickListener(v -> startActivity(new Intent(HomePage.this, addNotes.class)));

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull @NotNull Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(HomePage.this, MainActivity.class));

            case R.id.delete_all_notes:

        }

        return super.onOptionsItemSelected(item);
    }
}