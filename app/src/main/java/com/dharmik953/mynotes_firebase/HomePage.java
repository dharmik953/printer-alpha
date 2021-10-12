package com.dharmik953.mynotes_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;;
import com.dharmik953.mynotes_firebase.databinding.ActivityHomePageBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomePage extends AppCompatActivity {

    FloatingActionButton addNotes;
    FirebaseAuth mAuth;

    ActivityHomePageBinding binding;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager layoutManager;

    myadapter adapter;
    FirebaseFirestore firestore;
    ArrayList<firebaseModel> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        datalist=new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        firestore.collection("myNotes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list)
                {
                    firebaseModel obj=d.toObject(firebaseModel.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });

        firestore = FirebaseFirestore.getInstance();

        addNotes = findViewById(R.id.add_button);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notes");

        addNotes.setOnClickListener(v -> startActivity(new Intent(HomePage.this,addNotes.class)));

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull @NotNull Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout:
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(HomePage.this,MainActivity.class));

            case R.id.delete_all_notes:

        }

        return super.onOptionsItemSelected(item);
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (adapter!=null){
//            adapter.stopListening();
//        }
//    }
}