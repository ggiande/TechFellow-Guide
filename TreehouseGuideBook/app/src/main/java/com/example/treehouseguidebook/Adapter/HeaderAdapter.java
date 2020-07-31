package com.example.treehouseguidebook.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treehouseguidebook.R;
import com.example.treehouseguidebook.Singleton;
import com.example.treehouseguidebook.User;
import com.example.treehouseguidebook.fragments.ChatFragment;
import com.example.treehouseguidebook.fragments.SpecificSectionFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder>{

    private Context mContext;
    private List<String> headers;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    String header;


    public HeaderAdapter(Context mContext, List<String> headers){
        this.mContext = mContext;
        this.headers = headers;
    }

    @NonNull
    @Override
    public HeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.section_guide, parent, false);
        return new HeaderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderAdapter.ViewHolder holder, int position) {
         header = headers.get(position);

        holder.header.setText(header);
//        Log.i("WORKS", header);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();

                ref=database.getReference("Guides");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
//                            Log.i("TAG", d.getKey());
                            for(DataSnapshot d1: d.getChildren()){
//                                Log.i("TAG", d1.getKey());
                                if(d1.getValue().toString().equals(header)){
//                                    Log.i("WORKS", header +  "  = " + d1.getValue());
                                    String section = d.getKey();
                                    Fragment fragment = new SpecificSectionFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("header", header);
                                    bundle.putString("section", section);
                                    fragment.setArguments(bundle);
                                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.flContainer, fragment)
                                            .commit();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                Fragment fragment = new SpecificSectionFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("header", header);
//                fragment.setArguments(bundle);
//                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.flContainer, fragment)
//                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView header;

        public ViewHolder(View itemView){
            super(itemView);

            header = itemView.findViewById(R.id.header);
        }

    }
}