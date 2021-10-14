package com.dharmik953.mynotes_firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<Model> datalist;

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

        Model model = datalist.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());
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
        public myviewholder(@NonNull View itemView) {
            super(itemView);

                title = itemView.findViewById(R.id.note_title);
                content = itemView.findViewById(R.id.note_description);
                layout = itemView.findViewById(R.id.Note);
            }
        }
    }

