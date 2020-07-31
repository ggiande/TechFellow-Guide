package com.example.treehouseguidebook.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Section;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private Context mContext;
    private List<Section> mSection;
    public User current_user;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    public boolean marked;


    public SectionAdapter(Context context, List<Section> mSection){
        this.mContext = context;
        this.mSection = mSection;
        this.marked=false;

        current_user= Singleton.getExisting_user();
        //connect to database
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("schools").child(current_user.getUni()).child(current_user.getRole()).child(current_user.getUsername());
    }

    @NonNull
    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.specific_section, parent, false);
        return new SectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.ViewHolder holder, int position) {
        final Section section = mSection.get(position);

        holder.description.setText(section.getDesc());
        holder.link.setText(section.getLink());
        myRef.child("bookmarks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(section.getDesc())) {
                    marked=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView description;
        public TextView link;
        public ImageButton btnBookmark;


        public ViewHolder(View itemView){
            super(itemView);


            description = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            btnBookmark=itemView.findViewById(R.id.btnBookmark);
            link.setMovementMethod(LinkMovementMethod.getInstance());

            if(marked){
                btnBookmark.setColorFilter(Color.RED);
            }



            btnBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String desc = description.getText().toString();
                    String guide_link = link.getText().toString();
                    if(marked==false)
                    {

                       // Section sec = new Section(description.getText().toString(), link.getText().toString());
                        myRef.child("bookmarks").child(desc).setValue(guide_link); //description is key and link is value
                        btnBookmark.setColorFilter(Color.RED);
                        marked = true;
                        Toast.makeText(mContext,"Guide Added to Bookmarks",Toast.LENGTH_SHORT).show();

                    }

                    else{
                        myRef.child("bookmarks").child(desc).removeValue();
                        marked=false;
                        btnBookmark.setColorFilter(Color.WHITE);
                        Toast.makeText(mContext,"Guide Removed from Bookmarks",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
