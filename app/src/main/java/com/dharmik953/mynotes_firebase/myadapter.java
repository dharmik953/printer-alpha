package com.dharmik953.mynotes_firebase;

import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<Model> datalist;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("myNotes");

    public myadapter(HomePage homePage, ArrayList<Model> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {

        int colorCode = getRandomColorCode();
        holder.cardView.setBackgroundColor(holder.itemView.getResources().getColor(colorCode));

        Model model = datalist.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());

//        String id = myadapter.get

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.setGravity(Gravity.END);

                popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent = new Intent(v.getContext(),editNote.class);
                        intent.putExtra("title_",model.getTitle());
                        intent.putExtra("note_",model.getContent());

                        v.getContext().startActivity(intent);

                        return false;
                    }
                });
                
                popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        return false;
                    }
                });

                popupMenu.show();

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),noteDetail.class);
                v.getContext().startActivity(intent);

            }
        });       
    }

    private int getRandomColorCode() {

        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.green);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.lighgreen);
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random  =  new Random();
        int number = random.nextInt(colorCode.size());

        return colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    static class myviewholder extends RecyclerView.ViewHolder
    {
        private final TextView title;
        private final TextView content;
        LinearLayout layout;
        CardView cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

                cardView = itemView.findViewById(R.id.note_card);
                title = itemView.findViewById(R.id.note_title);
                content = itemView.findViewById(R.id.note_description);
                layout = itemView.findViewById(R.id.Note);
            }
        }
    }

